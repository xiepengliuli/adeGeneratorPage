<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>BUG管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/editPage')}">
  <script type="text/javascript">
      $.canEdit = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/delete')}">
  <script type="text/javascript">
      $.canDelete = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/view')}">
  <script type="text/javascript">
      $.canView = true;
    </script>
</c:if>
<script type="text/javascript">
  var dataGrid;
  $(function() {
    dataGrid = $('#dataGrid')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/bug/dataGrid',
              fit : true,
              fitColumns : true,
              border : false,
              pagination : true,
              idField : 'id',
              pageSize : 10,
              pageList : [ 10, 20, 30, 40, 50 ],
              sortName : 'createdatetime',
              sortOrder : 'desc',
              checkOnSelect : false,
              selectOnCheck : false,
              nowrap : false,
              striped : true,
              rownumbers : true,
              singleSelect : true,
              frozenColumns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                hidden : true
              }, {
                field : 'name',
                title : 'BUG名称',
                width : 80,
                sortable : true
              } ] ],
              columns : [ [
                  {
                    field : 'createdatetime',
                    title : '创建时间',
                    width : 150,
                    sortable : true
                  },
                  {
                    field : 'modifydatetime',
                    title : '最后修改时间',
                    width : 150,
                    sortable : true
                  },
                  {
                    field : 'typeId',
                    title : 'BUG类型ID',
                    width : 150,
                    hidden : true
                  },
                  {
                    field : 'typeName',
                    title : 'BUG类型名称',
                    width : 150
                  },
                  {
                    field : 'action',
                    title : '操作',
                    width : 100,
                    formatter : function(value, row, index) {
                      var str = '';
                      if ($.canEdit) {
                        str += $
                            .formatString(
                                '<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_edit.png');
                      }
                      str += '&nbsp;';
                      if ($.canDelete) {
                        str += $
                            .formatString(
                                '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
                                row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_delete.png');
                      }
                      str += '&nbsp;';
                      if ($.canView) {
                        str += $
                            .formatString(
                                '<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_link.png');
                      }
                      return str;
                    }
                  } ] ],
              toolbar : '#toolbar',
              onLoadSuccess : function() {
                $('#searchForm table').show();
                parent.$.messager.progress('close');

                $(this).datagrid('tooltip');
              },
              onRowContextMenu : function(e, rowIndex, rowData) {
                e.preventDefault();
                $(this).datagrid('unselectAll');
                $(this).datagrid('selectRow', rowIndex);
                $('#menu').menu('show', {
                  left : e.pageX,
                  top : e.pageY
                });
              }
            });
  });

  function deleteFun(id) {
    if (id == undefined) {
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    }
    parent.$.messager.confirm('询问', '您是否要删除当前BUG？', function(b) {
      if (b) {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/admin/bug/delete', {
          id : id
        }, function(result) {
          if (result.success) {
            parent.$.messager.alert('提示', result.msg, 'info');
            dataGrid.datagrid('reload');
          }
          parent.$.messager.progress('close');
        }, 'JSON');
      }
    });
  }

  function editFun(id) {
    if (id == undefined) {
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    }
    parent.$.modalDialog({
      title : '编辑BUG',
      width : 780,
      height : 500,
      href : '${pageContext.request.contextPath}/admin/bug/editPage?id=' + id,
      buttons : [ {
        text : '编辑',
        handler : function() {
          parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

  function viewFun(id) {
    if (id == undefined) {
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    }
    parent.$.modalDialog({
      title : '查看BUG',
      width : 780,
      height : 500,
      href : '${pageContext.request.contextPath}/admin/bug/view?id=' + id
    });
  }

  function addFun() {
    parent.$.modalDialog({
      title : '添加BUG',
      width : 780,
      height : 500,
      href : '${pageContext.request.contextPath}/admin/bug/addPage',
      buttons : [ {
        text : '添加',
        handler : function() {
          parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

  function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
  }
  function cleanFun() {
    $('#searchForm input').val('');
    dataGrid.datagrid('load', {});
  }
</script>
</head>
<body>
  <div id="toolbar" style="padding: 5px; height: auto">
    <div>
      <form id="searchForm" style="margin: 5px;">
        <%
            //BUG名称
        %>
        <input name="name" class="span2 easyui-textbox" data-options="width:150,prompt: 'BUG名称'" />

        <select name="typeId" class="easyui-combobox"
          data-options="width:100,height:25,editable:false,panelHeight:'auto'">
          <option value=""></option>
          <c:forEach items="${bugTypeList}" var="bugType">
            <option value="${bugType.id}">${bugType.name}</option>
          </c:forEach>
        </select>
        <%
            //创建时间
        %>
        <input class="easyui-datetimebox" name="createdatetimeStart" type="text"
          data-options="width:150,height:25,prompt: '创建时间开始',editable:false" />至<input
          class="easyui-datetimebox" name="createdatetimeEnd" type="text"
          data-options="width:150,height:25,prompt: '创建时间结束',editable:false" />
        <%
            //最后修改时间
        %>
        <input class="easyui-datetimebox" name="modifydatetimeStart" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间开始',editable:false" /> 至<input
          class="easyui-datetimebox" name="modifydatetimeEnd" type="text"
          data-options="width:150,height:25,prompt:'最后修改时间结束',editable:false" />
        <%
            //最后修改时间
        %>
        <span class="toolbar-item dialog-tool-separator"></span> <a href="javascript:void(0);"
          class="easyui-linkbutton" data-options="iconCls:'map_magnifier',plain:true"
          onclick="searchFun();">查询</a><span class="toolbar-item dialog-tool-separator"></span><a
          href="javascript:void(0);" class="easyui-linkbutton"
          data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
      </form>

      <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true,iconCls:'bug_add'">添加</a>
      </c:if>
    </div>
  </div>

  <table id="dataGrid"></table>

  <div id="menu" class="easyui-menu" style="width: 120px; display: none;">
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/addPage')}">
      <div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/delete')}">
      <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/bug/editPage')}">
      <div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
    </c:if>
  </div>
</body>
</html>