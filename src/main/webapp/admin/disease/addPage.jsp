<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/disease/add',
      onSubmit : function() {

        var isValid = $(this).form('validate');

        if (!isValid) {
          return false;
        }

        var isExit = false;
        $.ajax({
          url : "${pageContext.request.contextPath}/admin/disease/isExitPropertyValue",
          async : false,
          dataType : "json",
          data : {
            "property" : "name",
            "value" : $("#name").val()
          },
          cache : false,
          success : function(data) {
            if (data.success) {
              alert("命名病名已存在");
              $("input[name='name']").focus();
              isExit = true;
            }
          }
        });
        if (isExit == true) {
          return false;
        }

      },
      success : function(result) {
        result = $.parseJSON(result);
        if (result.success) {
          parent.$.modalDialog.openner_treeGrid.datagrid('reload'); //之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
          parent.$.modalDialog.handler.dialog('close');
          reflushMenu();
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
          <th>首字符</th>
          <!--           <td><input id="firstLetter" name="firstLetter" class="easyui-validatebox  span2" -->
          <!--             data-options="required:true,validType:['fixedLength[1]'] -->
          <!--           " /></td> -->
          <td><select class="easyui-combobox" name="firstLetter" style="width: 130px;"
            data-options="required:true,editable:false,valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/admin/disease/getFirstCharList'">
          </select></td>
          <th>疾病代码</th>
          <td><input name="code" class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,50]']
          " /></td>
        </tr>
        <tr>
          <th>病名</th>
          <td><input id="name" name="name" class="easyui-validatebox  span2"
            data-options="required:true,validType:['length[1,200]']
          " /></td>
          <th>中医病名</th>
          <td><input name="tcmDiseaseName" class="easyui-validatebox  span2"
            data-options="validType:['length[0,500]']
          " /></td>
        </tr>
        <tr>
          <th>西医病名</th>
          <td><input name="mmDiseaseName" class="easyui-validatebox  span2"
            data-options="validType:['length[0,1500]']
          " /></td>
          <th>英文名称</th>
          <td><input name="enName" class="easyui-validatebox  span2"
            data-options="validType:['length[0,1500]']
          " /></td>
        </tr>
        <tr>
          <th>排序号</th>
          <td><input name="orderNum" class="easyui-numberspinner" style="width: 120px;"
            required="required" data-options="editable:false" value="${orderNum}"></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><textarea style="width: 400px; height: 80px;" name="remark"
              class="easyui-validatebox" data-options="validType:['length[0,10000]']" /></textarea></td>

        </tr>
      </table>
    </form>
  </div>
</div>