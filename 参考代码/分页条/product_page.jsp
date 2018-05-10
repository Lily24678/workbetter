<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" width="800">
	<tr>
		<td>序号</td>
		<td>商品名称</td>
		<td>市场价格</td>
		<td>商城价格</td>
		<td>是否热门</td>
		<td>是否下架</td>
		<td>操作</td>
	</tr>
	
	<c:forEach items="${pageBean.list }" var="p" varStatus="status"> 
	<tr>
		<td>${status.count }</td>
		<td>${p.pname }</td>
		<td>${p.market_price }</td>
		<td>${p.shop_price }</td>			
		<td>

			<c:if test="${p.is_hot == 1 }">
			是
			</c:if>
			<c:if test="${p.is_hot == 0 }">
			否
			</c:if>
		</td>
		<td>
			<c:if test="${p.pflag == 1 }">
			已下架
			</c:if>
			<c:if test="${p.pflag == 0 }">
			未下架
			</c:if>
		</td>
		<td>操作</td>
	</tr>
	</c:forEach>
	<tr>
		<!-- 分页显示样式1 -->
		<td colspan="7" align="center"> 
			第${pageBean.currPage }/${pageBean.totalPage }页&nbsp;&nbsp;总记录数:${pageBean.totalCount }&nbsp;&nbsp;每页显示的记录数:${pageBean.size }&nbsp;&nbsp;
		
			<c:if test="${pageBean.currPage != 1 }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=1">[首页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage-1 }">[上一页]</a>
			</c:if>
			&nbsp;&nbsp;
			
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage }">${pageBean.currPage }</a>
			
			&nbsp;&nbsp;
			<c:if test="${pageBean.currPage != pageBean.totalPage }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage+1 }">[下一页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.totalPage }">[尾页]</a>
			</c:if>
			
		<td>
	</tr>
	
	<tr>
		<!-- 分页显示样式2 -->
		<td colspan="7" align="center">	
			<c:if test="${pageBean.currPage != 1 }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=1">[首页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage-1 }">[上一页]</a>
			</c:if>
			&nbsp;&nbsp;
			
			<c:forEach var="i" begin="1" end="${pageBean.totalPage }">
				<c:if test="${ pageBean.currPage != i }">
					<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${i}">${ i }</a>
				</c:if>
				<c:if test="${ pageBean.currPage == i }">
					${ i }
				</c:if>
			</c:forEach>
			
			&nbsp;&nbsp;
			<c:if test="${pageBean.currPage != pageBean.totalPage }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage+1 }">[下一页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.totalPage }">[尾页]</a>
			</c:if>				
		<td>
	</tr>
	
	<tr>
		<!-- 分页显示样式3 -->
		<c:if test="${pageBean.totalPage <= 4 }">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="${pageBean.totalPage }"></c:set>
		</c:if>
		<c:if test="${pageBean.totalPage > 4 }">
			<c:set var="begin" value="${pageBean.currPage-1 }"></c:set>
			<c:set var="end" value="${pageBean.currPage+2 }"></c:set>
		</c:if>	
		<!-- 头溢出 -->
		<c:if test="${begin <= 0 }">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="${pageBean.totalPage }"></c:set>
		</c:if>	
		<!-- 尾溢出 -->
		<c:if test="${end > pageBean.totalPage}">
			<c:set var="begin" value="${pageBean.totalPage-3 }"></c:set>
			<c:set var="end" value="${pageBean.totalPage }"></c:set>
		</c:if>	
				
		<td colspan="7" align="center">	
			<c:if test="${pageBean.currPage != 1 }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=1">[首页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage-1 }">[上一页]</a>
			</c:if>
			&nbsp;&nbsp;
			
			<c:forEach var="i" begin="${begin }" end="${end }">
				<c:if test="${ pageBean.currPage != i }">
					<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${i}">[${ i }]</a>
				</c:if>
				<c:if test="${ pageBean.currPage == i }">
					[${ i }]
				</c:if>
			</c:forEach>
			
			&nbsp;&nbsp;
			<c:if test="${pageBean.currPage != pageBean.totalPage }">
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.currPage+1 }">[下一页]</a>
			<a href="${pageContext.request.contextPath }/ProductQueryPageServlet?currPage=${pageBean.totalPage }">[尾页]</a>
			</c:if>					
		<td>
	</tr>
</table>

</body>
</html>