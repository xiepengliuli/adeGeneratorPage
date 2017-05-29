<%@page import="cn.com.infcn.core.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>内容管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript">
  $(function() {
    $('#test').dialog({
      title : '添加',
      width : 600,
      height : 350,
      buttons : [ {
        text : '添加',
        handler : function() {
        }
      } ]
    });
  })
</script>
</head>
<body>
  <div class="easyui-layout" data-options="fit : true,border : false">

    <div id="test">${sgeneratorEffect}</div>
  </div>

</body>
</html>

