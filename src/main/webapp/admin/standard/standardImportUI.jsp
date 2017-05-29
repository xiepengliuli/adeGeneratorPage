<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    alert("aaaaa");
    parent.$.messager.progress('close');
  });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" action="${pageContext.request.contextPath}/userController/upload" method="post"
      enctype="multipart/form-data">
      <input name="userExcel" type="file" />
    </form>
  </div>
</div>