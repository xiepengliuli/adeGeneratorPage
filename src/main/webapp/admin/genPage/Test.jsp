<%@page import="cn.com.infcn.core.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<!-- http://localhost:8080/admin/genPage/Test.jsp -->
<!DOCTYPE html>
<script type="text/javascript">
  window.setTimeout(function(){
    var fields = $("select, :radio").serializeArray();
    ade.jsonToString(fields);
    console.log(ade.jsonToString(fields));
    jQuery.each( fields, function(i, field){
      $("#results").append(field.value + " ");
    });
  }, 2000);

</script>
<head>
</head>
<body>
<p id="results"><b>Results:</b> </p>
<form>
  <select name="single">
    <option>Single</option>
    <option>Single2</option>
  </select>
  <select name="multiple" multiple="multiple">
    <option selected="selected">Multiple</option>
    <option>Multiple2</option>
    <option selected="selected">Multiple3</option>
  </select><br/>
  <input type="checkbox" name="check" value="check1"/> check1
  <input type="checkbox" name="check" value="check2" checked="checked"/> check2
  <input type="radio" name="radio" value="radio1" checked="checked"/> radio1
  <input type="radio" name="radio" value="radio2"/> radio2
</form>
</body>