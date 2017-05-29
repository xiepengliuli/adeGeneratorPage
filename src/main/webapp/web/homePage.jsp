<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <!--subnav-->
  <div id="container" class="container">
    <div id="subnav" class="subnav">首页>></div>
  </div>
  <!--content-->
  <div id="container" class="container">
    <div id="content" class="content">
      <div id="sy" class="sy">
        <ul>
          <li class="text">索引</li>
          <c:forEach items="${diseaseMaps}" var="data">
            <li><a href="javascript:void(0)"
              onclick="document.getElementById('showThis_${data.key}').scrollIntoView();"
              title="${data.key}">${data.key} </a></li>
          </c:forEach>

        </ul>
      </div>

      <c:forEach items="${diseaseMaps}" var="data">
        <div id="search_sel" class="search_sel">
          <h1 id="showThis_${data.key}" title="${data.key}">${data.key}</h1>
          <ul>
            <c:forEach items="${data.value}" var="Disease" varStatus="var">

              <c:if test="${Disease.name.length()>0}">
                <li><a
                  href="${pageContext.request.contextPath}/web/standardList?id=${Disease.id}"
                  target="_blank" title="${Disease.name}"> <string:string length="8"
                      value="${Disease.name} " />
                </a></li>
              </c:if>
            </c:forEach>
          </ul>
        </div>
      </c:forEach>
    </div>
  </div>
  <!-- 正文区域结束 -->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
