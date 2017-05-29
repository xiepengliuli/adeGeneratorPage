<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    getEditData("${id}");
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/genPage/edit',
      success : function(result) {
        result = $.parseJSON(result);
        if (result.success) {
          parent.$.modalDialog.openner_treeGrid.datagrid('reload'); //之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
          parent.$.modalDialog.handler.dialog('close');
        } else {
          alert(result.msg);
        }
      }
    });

    $.initDictionary("dictionaryType","42CDBD7A7149474B8C9FEAC38EAC760D","dictionaryValue","061BDFE247DF44E18526271F32EDCF33");
  
  });

  function getEditData(id) {
    $.post("${pageContext.request.contextPath}/admin/genPage/editData", {
      id : id
    }, function(data) {
      $("#form").form("load", data);
    }, "json");
  }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" method="post">
      <input type="hidden" name="id">
      <table class="table table-hover table-condensed">
        <tr>
          <th>页面标题</th>
          <td><input style="width: 300px;" type="text" name="pageTitle" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面宽</th>
          <td><input style="width: 300px;" type="text" name="pageWidth" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面高</th>
          <td><input style="width: 300px;" type="text" name="pageHeight" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面访问路径</th>
          <td><input style="width: 300px;" type="text" name="pageUrl" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面排序号</th>
          <td><input style="width: 300px;" type="text" name="pageOrderNumber" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面按钮</th>
          <td><input style="width: 300px;" type="text" name="pageButtonTypeid" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面类型(字典)</th>
          <td><input style="width: 100px;" type="text" id="dictionaryType" name="dictionaryType" class=" span2" />&nbsp;&nbsp;列表页面类型<input
            style="width: 100px;" type="text" id="dictionaryValue" name="dictionaryValue" class=" span2"
          /></td>
        </tr>
      </table>
    </form>
  </div>
</div>