package cn.itcast.bos.service.take_delivery.impl;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.index.WayBillIndexRepository;
import cn.itcast.bos.service.take_delivery.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
	@Autowired
	private WayBillRepository wayBillRepository;
	@Autowired
	private WayBillIndexRepository wayBillIndexRepository;

	// 运单的添加方法
	@Override
	public void save(WayBill wayBill) {
		WayBill persistWayBill = wayBillRepository.findByWayBillNum(wayBill
				.getWayBillNum());
		if (persistWayBill == null || persistWayBill.getId() == null) {
			// 运单不存在
			wayBill.setSignStatus(1);// 签收状态 1 待发货、 2 派送中、3 已签收、4 异常
			wayBillRepository.save(wayBill);
			// 保存索引
			wayBillIndexRepository.save(wayBill);
		} else {			
			try {
				// 运单存在,判断其是否是待发货
				if(persistWayBill.getSignStatus()==1){
					//更新快照区
					Integer id = persistWayBill.getId();
					BeanUtils.copyProperties(persistWayBill, wayBill);// BeanUtils.copyProperties(dest,orig);
					persistWayBill.setId(id);
					// 保存索引
					wayBillIndexRepository.save(persistWayBill);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}

	}

	@Override
	public Page<WayBill> pageQuery(Pageable pageable, WayBill wayBill) {
		// 判断WayBill 中条件是否成立
		if (StringUtils.isBlank(wayBill.getWayBillNum())
				&& StringUtils.isBlank(wayBill.getSendAddress())
				&& StringUtils.isBlank(wayBill.getRecAddress())
				&& StringUtils.isBlank(wayBill.getSendProNum())
				&& (wayBill.getSignStatus() == null || wayBill.getSignStatus() == 0)) {
			//无条件查询,查询数据库
			return wayBillRepository.findAll(pageable);
		}
		//查询条件
		//must 条件必须成立and
		//must not 条件必须不成立not
		//should 条件可以成立or
		BoolQueryBuilder query = new BoolQueryBuilder();//布尔查询，向组合查询对象添加条件
		if (StringUtils.isNotBlank(wayBill.getWayBillNum())) {
			//运单号查询
			QueryBuilder termQuery = new TermQueryBuilder("wayBillNum", wayBill.getWayBillNum());
			query.must(termQuery);  
		}
		if (StringUtils.isNotBlank(wayBill.getSendAddress())) {
			//情况1：输入“北”是查询词条一部分，使用模糊匹配词条查询
			QueryBuilder wildcardQuery = new WildcardQueryBuilder("sendAddress", "*"+wayBill.getSendAddress()+"*");
			//情况2：输入“北京市海淀区”，是多个词条组合，进行分词后 每个词条匹配
			QueryBuilder queryStringQueryBuilder = new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(Operator.AND);
			//两种关系取or关系
			BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
			boolQueryBuilder.should(wildcardQuery);
			boolQueryBuilder.should(queryStringQueryBuilder);
			
			query.must(boolQueryBuilder);
		}
		if (StringUtils.isNotBlank(wayBill.getRecAddress())) {
			QueryBuilder wildcardQuery = new WildcardQueryBuilder("recAddress", "*"+wayBill.getRecAddress()+"*");
			query.must(wildcardQuery);
		}
		if (StringUtils.isNotBlank(wayBill.getSendProNum())) {
			QueryBuilder termQuery = new TermQueryBuilder("sendProNum", wayBill.getSendProNum());
			query.must(termQuery);  
		}
		if (wayBill.getSignStatus() != null && wayBill.getSignStatus() != 0) {
			QueryBuilder termQuery = new TermQueryBuilder("signStatus", wayBill.getSignStatus());
			query.must(termQuery);  
		}
		SearchQuery searchQuery = new NativeSearchQuery(query);
		//有条件查询，查询索引库
		return wayBillIndexRepository.search(searchQuery);
	}

	@Override
	public WayBill findByWayBillNum(String wayBillNum) {
		return wayBillRepository.findByWayBillNum(wayBillNum);
	}

}
