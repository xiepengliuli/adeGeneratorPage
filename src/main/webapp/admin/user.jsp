<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/editPage')}">
  <script type="text/javascript">
      $.canEdit = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/delete')}">
  <script type="text/javascript">
      $.canDelete = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/grantPage')}">
  <script type="text/javascript">
      $.canGrant = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/editPwdPage')}">
  <script type="text/javascript">
      $.canEditPwd = true;
    </script>
</c:if>
<script type="text/javascript">
  var dataGrid;
  $(function() {
    dataGrid = $('#dataGrid')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/user/dataGrid',
              fit : true,
              rownumbers : true,
              fitColumns : true,
              border : false,
              striped : true,
              pagination : true,
              idField : 'id',
              pageSize : 10,
              pageList : [ 10, 20, 30, 40, 50 ],
              sortName : 'loginName',
              sortOrder : 'asc',
              checkOnSelect : false,
              selectOnCheck : false,
              nowrap : true,
              toolbar : '#toolbar',
              frozenColumns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                checkbox : true
              }, {
                field : 'loginName',
                title : '登录名称',
                width : 80,
                sortable : true
              } ] ],
              columns : [ [
                  {
                    field : 'userName',
                    title : '用户名',
                    width : 60
                  },
                  {
                    field : 'sex',
                    title : '性别',
                    width : 30
                  },
                  {
                    field : 'password',
                    title : '密码',
                    width : 60,
                    formatter : function(value, row, index) {
                      return '******';
                    }
                  },
                  {
                    field : 'createDate',
                    title : '创建时间',
                    width : 150,
                    sortable : true,
                    formatter : function(value, row, index) {
                      if (value) {
                        return new Date(value).format();
                      }
                    }
                  },
                  {
                    field : 'updateDate',
                    title : '修改时间',
                    width : 150,
                    sortable : true,
                    formatter : function(value, row, index) {
                      if (value) {
                        return new Date(value).format();
                      }
                    }
                  },
                  {
                    field : 'roleIds',
                    title : '所属角色ID',
                    width : 150,
                    hidden : true
                  },
                  {
                    field : 'roleNames',
                    title : '所属角色名称',
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
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/pencil.png');
                      }
                      str += '&nbsp;';
                      if ($.canGrant) {
                        str += $
                            .formatString(
                                '<img onclick="grantFun(\'{0}\');" src="{1}" title="授权"/>', row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/key.png');
                      }
                      str += '&nbsp;';
                      if ($.canDelete) {
                        str += $
                            .formatString(
                                '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
                                row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');
                      }
                      str += '&nbsp;';
                      if ($.canEditPwd) {
                        str += $
                            .formatString(
                                '<img onclick="editPwdFun(\'{0}\');" src="{1}" title="修改密码"/>',
                                row.id,
                                '${pageContext.request.contextPath}/resources/css/images/extjs_icons/lock/lock_edit.png');
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
                $(this).datagrid('unselectAll').datagrid('uncheckAll');
                $(this).datagrid('selectRow', rowIndex);
                $('#menu').menu('show', {
                  left : e.pageX,
                  top : e.pageY
                });
              }
            });
  });

  function editPwdFun(id) {
    dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    parent.$.modalDialog({
      title : '编辑用户密码',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/user/editPwdPage?id=' + id,
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

  function deleteFun(id) {
    if (id == undefined) {//点击右键菜单才会触发这个
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    } else {//点击操作里面的删除图标会触发这个
      dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
      if (b) {
        var currentUserId = '${LOGIN_USER.id}';/*当前登录用户的ID*/
        if (currentUserId != id) {
          parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
          });
          $.post('${pageContext.request.contextPath}/admin/user/delete', {
            id : id
          }, function(result) {
            if (result.success) {
              parent.$.messager.alert('提示', result.msg, 'info');
              dataGrid.datagrid('reload');
            }
            parent.$.messager.progress('close');
          }, 'JSON');
        } else {
          parent.$.messager.show({
            title : '提示',
            msg : '不可以删除自己！'
          });
        }
      }
    });
  }

  function batchDeleteFun() {
    var rows = dataGrid.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      parent.$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
        if (r) {
          parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
          });
          var currentUserId = '${LOGIN_USER.id}';/*当前登录用户的ID*/
          var flag = false;
          for (var i = 0; i < rows.length; i++) {
            if (currentUserId != rows[i].id) {
              ids.push(rows[i].id);
            } else {
              flag = true;
            }
          }
          $.getJSON('${pageContext.request.contextPath}/admin/user/batchDelete', {
            ids : ids.join(',')
          }, function(result) {
            if (result.success) {
              dataGrid.datagrid('load');
              dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
            }
            if (flag) {
              parent.$.messager.show({
                title : '提示',
                msg : '不可以删除自己！'
              });
            } else {
              parent.$.messager.alert('提示', result.msg, 'info');
            }
            parent.$.messager.progress('close');
          });
        }
      });
    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要删除的记录！'
      });
    }
  }

  function editFun(id) {
    if (id == undefined) {
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    } else {
      dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
      title : '编辑用户',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/user/editPage?id=' + id,
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

  function addFun() {
    parent.$.modalDialog({
      title : '添加用户',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/user/addPage',
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

  function batchGrantFun() {
    var rows = dataGrid.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].id);
      }
      parent.$.modalDialog({
        title : '用户授权',
        width : 500,
        height : 300,
        href : '${pageContext.request.contextPath}/admin/user/grantPage?ids=' + ids.join(','),
        buttons : [ {
          text : '授权',
          handler : function() {
            parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
            var f = parent.$.modalDialog.handler.find('#form');
            f.submit();
          }
        } ]
      });
    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要授权的记录！'
      });
    }
  }

  function grantFun(id) {
    dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    parent.$.modalDialog({
      title : '用户授权',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/user/grantPage?ids=' + id,
      buttons : [ {
        text : '授权',
        handler : function() {
          parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
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
    $('#searchForm').form('reset');
    dataGrid.datagrid('load', {});
  }
</script>
</head>
<body>

  <div id="toolbar" style="padding: 5px; height: auto">
    <div>
      <form id="searchForm" style="margin: 5px;">
        <%
            //登录名
        %>
        <input name="loginName" class="span2 easyui-textbox" data-options="width:150,prompt: '登录名'" />
        <%
            //创建时间
        %>
        <input class="easyui-datetimebox" name="createDateStart" type="text"
          data-options="width:150,height:25,prompt: '创建时间开始',editable:false" />至<input
          class="easyui-datetimebox" name="createDateEnd" type="text"
          data-options="width:150,height:25,prompt: '创建时间结束',editable:false" />
        <%
            //最后修改时间
        %>
        <input class="easyui-datetimebox" name="updateDateStart" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间开始',editable:false" /> 至<input
          class="easyui-datetimebox" name="updateDateEnd" type="text"
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


      <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true,iconCls:'pencil_add'">添加</a>
        <span class="toolbar-item dialog-tool-separator"></span>
      </c:if>
      <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/grantPage')}">
        <a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true,iconCls:'tux'">批量授权</a>
      </c:if>
      <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/batchDelete')}">
        <a onclick="batchDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true,iconCls:'delete'">批量删除</a>
      </c:if>
    </div>
  </div>

  <table id="dataGrid"></table>

  <div id="menu" class="easyui-menu" style="width: 120px; display: none;">
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/addPage')}">
      <div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/delete')}">
      <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/user/editPage')}">
      <div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
    </c:if>
  </div>
</body>
</html>