<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>系统日志列表</title>
<link href="../style/portal/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">


	//打印
	function print_onclick(){
		document.getElementById("tjxx").style.display="none";		
		window.print();
		document.getElementById("tjxx").style.display="";
	}
</script>

</head>
<body>

  <div class="cont_wrap cf">
    <div id="tjxx" class="tjxx">
      <a href="javascript:void(0);" onclick="return print_onclick();"
        class="tjBtn">打印</a> 
    </div>
    <div class="right" id="searchDiv">
    <c:if test="${not empty lList}">
       <table  cellspacing="0" cellpadding="0" class="right_table">
		<thead>
        <tr>
          <th width="120px;">编号</th>
          <th width="120px;">操作人</th>
          <th width="200px;">操作类型</th>
          <th width="255px;">操作时间</th>
          <th width="255px;">操作描述</th>
          <th width="255px;">登录IP</th>
        </tr></thead>
        <c:forEach items="${lList}" varStatus="status" var="log">
          <c:choose>
            <c:when test="${status.index % 2 == 0}">
              <tr class="colf2fff1">
            </c:when>
            <c:otherwise>
              <tr>
            </c:otherwise>
          </c:choose>
          <td>${status.index+1}</td>
          <td>${log.logUser}</td>
          <td>${log.logOperationType}</td>
          <td><fmt:formatDate value="${log.logTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td>${log.logMessage}</td>
          <td>${log.logIp}</td>
          </tr>
        </c:forEach>
      </table>

      <!-- 分页 -->
    </c:if>
    </div>
  </div>
</body>
</html>
