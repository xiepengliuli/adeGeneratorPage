<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>疾病诊断与疗效评价标准平台</title>

<!-- 引入总引用文件 -->
<%@ include file="/WEB-INF/inc/taglib.jsp"%>

</head>

<body>
	<!-- 引入头部文件 -->
		<%@ include file="/WEB-INF/inc/banner.jsp"%>
	<!-- 当前位置 -->
		<div id="container" class="container">
			<div id="subnav" class="subnav">
			<a href="${ctx}/web/homePage" style="color: #636363; " >首页>></a>病种检索
			</div>
		</div>
	<!-- 当前位置结束 -->

	<!-- 病种正文区域 -->
      <!--content-->
    <div id="container" class="container">
      <div id="content" class="content">       

        <div id="search_sel" class="search_sel">
        
          <ul>
                 <c:forEach items="${diseaseList}" var="Disease">
            <li>
            	<a 
					href="${pageContext.request.contextPath}/web/standardList?id=${Disease.id}" target="_blank" title="${Disease.name}"
					 ><string:string length="8"
                    value="${Disease.name} " />
				</a>
            </li>
                        </c:forEach>    
          </ul>

        </div>
    
      </div>
    </div>

	<!-- 正文区域结束 -->

		<%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
