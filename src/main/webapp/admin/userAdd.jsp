<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/user/add',
      onSubmit : function() {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        var isValid = $(this).form('validate');
        if (!isValid) {
          parent.$.messager.progress('close');
        }
        return isValid;
      },
      success : function(result) {
        parent.$.messager.progress('close');
        result = $.parseJSON(result);
        if (result.success) {
          parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
          parent.$.modalDialog.handler.dialog('close');
          parent.$.messager.show({
            title : '成功',
            msg : '用户添加成功！'
          });
        } else {
          parent.$.messager.alert('错误', result.msg, 'error');
        }
      }
    });
  });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form" method="post">
      <input name="id" type="hidden" value="${user.id}" />
      <table class="table table-hover table-condensed">
        <tr>
          <th>登录名称</th>
          <td><input name="loginName" type="text" placeholder="请输入登录名称"
            class="easyui-validatebox span2" data-options="required:true" value="" /></td>
          <th>密码</th>
          <td><input name="password" type="password" placeholder="请输入密码"
            class="easyui-validatebox span2" data-options="required:true" /></td>
        </tr>
        <tr>
          <th>用户名称</th>
          <td><input name="userName" type="text" placeholder="请输入用户名称"
            class="easyui-validatebox span2" /></td>
          <th>性别</th>
          <td><input name="sex" type="radio" value="男" checked="checked"/>男<input name="sex" type="radio"
            value="女" />女</td>
        </tr>
        <tr>
          <th>手机</th>
          <td><input name="mobilePhone" type="text" placeholder="请输入手机号"
            class="easyui-validatebox span2" data-options="validType:'isMobilePhone'" /></td>
          <th>座机</th>
          <td><input name="telePhone" type="text" placeholder="请输入座机电话"
            class="easyui-validatebox span2" data-options="validType:'isTelePhone'" /></td>
        </tr>
        <tr>
          <th>邮箱</th>
          <td><input name="email" type="text" placeholder="请输入邮箱"
            class="easyui-validatebox span2" data-options="validType:'email'"/></td>
          <th>用户IP</th>
          <td><input name="ip" type="text" placeholder="请输入用户IP"
            class="easyui-validatebox span2"  data-options="validType:'isIP'"/></td>
        </tr>
        <tr>
          <th>用户状态</th>
          <td><input name="userState" type="radio" value="1" checked="checked" />正常<input name="userState"
            type="radio" value="9" />冻结</td>
          <th></th>
          <td></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><textarea name="userDesc" style="width: 330px;"></textarea></td>
        </tr>
      </table>
    </form>
  </div>
</div>