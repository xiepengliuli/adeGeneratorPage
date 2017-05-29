<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    getEditData("${id}");
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/portalUser/edit',
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
  });

  function getEditData(id) {
    $.post("${pageContext.request.contextPath}/admin/portalUser/editData", {
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
          <th>登录名</th>
          <td><input type="text" id="loginName" name="loginName"
            class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,20]']
          " /></td>
          <th>密码</th>
          <td><input type="password" id="password" name="password"
            class="easyui-validatebox  span2" data-options="validType:['length[1,20]']
          " /></td>
        </tr>
        <tr>
          <th>姓名</th>
          <td colspan="3"><input type="text" id="userName" name="userName"
            class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,20]']
          " /></td>
        </tr>
        <tr>
          <th>所属单位</th>
          <td colspan="3"><input type="text" name="unit" style="width: 404px;"
            class="easyui-validatebox  span2" data-options="validType:['length[0,100]']" /></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><input type="text" name="remark" style="height: 100px; width: 420px;"
            class="easyui-textbox  span2"
            data-options="multiline:true,validType:['length[0,1000]']
          " /></td>
        </tr>
      </table>
    </form>
  </div>
</div>