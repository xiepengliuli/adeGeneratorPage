<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  var dataGrid;
  $(function() {
    parent.$.messager.progress('close');
    dataGrid = $('#dataGrid')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/disease/authorizationDatagrid?tdiseaseId=${tdiseaseId}',
              fitColumns : true,
              //               fit : true,
              border : false,
              idField : 'id',
              //           pagination : true,
              pageSize : 10,
              pageList : [ 10, 20, 30, 40, 50 ],
              //               sortName : 'createDate',
              //               sortOrder : 'asc',
              singleSelect : true,
              checkOnSelect : false,
              selectOnCheck : false,
              remoteSort : false,
              frozenColumns : [ [ {
                field : 'id',
                title : '编号',
                width : 100,
                hidden : true
              }, {
                field : 'userName',
                title : '用户姓名',
                sortable : true,
                width : 300,
              }, {
                field : 'loginName',
                title : '登录名',
                sortable : true,
                width : 200,
              } ] ],
              columns : [ [
                  {
                    field : '1',
                    title : '编辑',
                    width : 150,
                    formatter : function(value, row, index) {
                      if ("1" == row.canEdit) {
                        return '<input type="radio" name="canEdit" value="'+row.id+',1"   checked="checked">';
                      } else {
                        return '<input type="radio" name="canEdit" value="'+row.id+',1" >';
                      }
                    }
                  },
                  {
                    field : '2',
                    title : '审核',
                    width : 150,
                    formatter : function(value, row, index) {
                      if ("1" == row.canAudit) {
                        return '<input type="radio" name="canAudit"  value="'+row.id+',2"  checked="checked">';
                      } else {

                        return '<input type="radio" name="canAudit"  value="'+row.id+',2">';
                      }
                    }
                  },
                  {
                    field : '3',
                    title : '发布',
                    width : 150,
                    formatter : function(value, row, index) {
                      if ("1" == row.canPublish) {
                        return '<input type="radio" name="canPublish"  value="'+row.id+',3"  checked="checked">';
                      } else {

                        return '<input type="radio" name="canPublish"  value="'+row.id+',3">';
                      }
                    }
                  }, ] ],
              onLoadSuccess : function() {
                parent.$.messager.progress('close');
              },
            });
  });

  $('#form').form({
    url : "${pageContext.request.contextPath}/admin/disease/saveAuthorization",
    onSubmit : function(param) {
      param.tdiseaseId = '${tdiseaseId}';
    },
    success : function(data) {
      data = $.parseJSON(data);
      if (data.success) {
        alert("保存成功！");
        parent.$.modalDialog.handler.dialog('close');
        parent.reflushMenu();
      } else {
        alert(data.msg);
      }
    }
  });

  //   function searchFun() {
  //     dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
  //   }
  //   function cleanFun() {
  //     $('#searchForm').form('reset');
  //     dataGrid.datagrid('load', {});
  //   }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <!--   <form id="searchForm" style="margin: 5px;"> -->
  <%--       <input type="hidden" name="tdiseaseId" value="${tdiseaseId}" > --%>
  <!--         <input name="userNameOrloginName" type="text"> -->
  <!--     <input name="userNameOrloginName" type="text" class="span2 easyui-textbox" data-options="width:150,prompt: '用户姓名和登录名'"> -->
  <%--     <% --%>
  <!--   // //查询、清空按钮 -->
  <%--     %> --%>
  <!--     <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'map_magnifier',plain:true" -->
  <!--       onclick="searchFun();">查询</a><span class="toolbar-item dialog-tool-separator"></span><a href="javascript:void(0);" -->
  <!--       class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a> -->
  <!--   </form> -->
  <form id="form">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
      <table id="dataGrid"></table>
    </div>
  </form>
</div>