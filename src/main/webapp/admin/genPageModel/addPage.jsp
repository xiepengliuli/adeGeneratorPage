<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/genPageModel/add',
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" method="post">
      <table class="table table-hover table-condensed">
        <tr>
          <th>pageModel名称</th>
          <td><input style="width: 200px;" type="text" name="pageModelName" class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,100]']
          " /></td>
        </tr>
        <tr>
          <th>pageModel全名称</th>
          <td><input style="width: 400px;" type="text" name="pageModelFullName" class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,200]']
          " /></td>
        </tr>
      </table>
    </form>
  </div>
</div>