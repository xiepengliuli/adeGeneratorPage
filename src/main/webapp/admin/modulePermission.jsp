<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>资源管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/editPage')}">
  <script type="text/javascript">
      $.canEdit = true;
    </script>
</c:if>
<c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/delete')}">
  <script type="text/javascript">
      $.canDelete = true;
    </script>
</c:if>
<script type="text/javascript">
  var treeGrid;
  var authGrid;
  $(function() {
    treeGrid = $('#treeGrid').treegrid({
      url : '${pageContext.request.contextPath}/admin/module/treeGrid',
      idField : 'id',
      treeField : 'moduleName',
      parentField : 'parentModuleId',
      fit : true,
      fitColumns : true,
      border : false,
      striped : true,

      onClickRow:loadAuthGrid,
      frozenColumns : [ [ {
        title : '编号',
        field : 'id',
        width : 150,
        hidden : true
      } ] ],
      columns : [ [ {
        field : 'moduleName',
        title : '资源名称',
        width : 200
      } ] ],
      onLoadSuccess : function() {
        parent.$.messager.progress('close');

        $(this).treegrid('tooltip');
      }
    });
    loadAuthGrid(null);
  });

  function loadAuthGrid(row){
    var pid="";
    if (row) {
      pid= row.id;
    }
    authGrid = $('#authGrid')
    .treegrid(
        {
          url : '${pageContext.request.contextPath}/admin/module/dataGridForMenu',
        	queryParams: {
        	  pid: pid
        	},
          fit : true,
          fitColumns : true,
          border : false,
          pagination : true,
          idField : 'id',
          pageSize : 10,
          pageList : [ 10, 20, 30, 40, 50 ],
//           sortName : 'createdatetime',
//           sortOrder : 'desc',
          checkOnSelect : false,
          selectOnCheck : false,
          nowrap : false,
          striped : true,
          rownumbers : true,
          singleSelect : true,
          frozenColumns : [ [ {
            title : '编号',
            field : 'id',
            width : 150,
            hidden : true
          } ] ],
          columns : [ [
              {
                field : 'moduleName',
                title : '资源名称',
                width : 100
              },
              {
                field : 'moduleUrl',
                title : '资源路径',
                width : 230
              },
              {
                field : 'moduleTypeId',
                title : '资源类型ID',
                width : 150,
                hidden : true
              },
              {
                field : 'moduleSort',
                title : '排序',
                width : 40
              },
              {
                field : 'parentModuleId',
                title : '上级资源ID',
                width : 150,
                hidden : true
              },
              {
                field : 'parentModuleName',
                title : '上级资源',
                width : 80
              },
              {
                field : 'action',
                title : '操作',
                width : 80,
                formatter : function(value, row, index) {
                  var str = '';
                  if ($.canEdit) {
                    str += $
                        .formatString(
                            '<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id,
                            '${pageContext.request.contextPath}/resources/css/images/extjs_icons/pencil.png');
                  }
                  str += '&nbsp;';
                  if ($.canDelete) {
                    str += $
                        .formatString(
                            '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>',
                            row.id,
                            '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');
                  }
                  return str;
                }
              }, {
                field : 'remark',
                title : '备注',
                width : 150
              } ] ],
          toolbar : '#toolbar',
          onContextMenu : function(e, row) {
            e.preventDefault();
            $(this).treegrid('unselectAll');
            $(this).treegrid('select', row.id);
            $('#menu').menu('show', {
              left : e.pageX,
              top : e.pageY
            });
          },
          onLoadSuccess : function() {
            parent.$.messager.progress('close');

            $(this).treegrid('tooltip');
          }
        });
  }
  
  
  function deleteFun(id) {
    if (id != undefined) {
      authGrid.treegrid('select', id);
    }
    var node = authGrid.treegrid('getSelected');
    if (node) {
      parent.$.messager.confirm('询问', '您是否要删除当前资源？', function(b) {
        if (b) {
          parent.$.messager.progress({
            title : '提示',
            text : '数据处理中，请稍后....'
          });
          $.post('${pageContext.request.contextPath}/admin/module/delete', {
            id : node.id
          }, function(result) {
            if (result.success) {
              parent.$.messager.alert('提示', result.msg, 'info');
              authGrid.treegrid('reload');
              parent.layout_west_tree.tree('reload');
            }
            parent.$.messager.progress('close');
          }, 'JSON');
        }
      });
    }
  }

  function editFun(id) {
    if (id != undefined) {
      authGrid.treegrid('select', id);
    }
    var node = authGrid.treegrid('getSelected');
    if (node) {
      parent.$.modalDialog({
        title : '编辑资源',
        width : 500,
        height : 300,
        href : '${pageContext.request.contextPath}/admin/module/editPagePermission?id=' + node.id,
        buttons : [ {
          text : '编辑',
          handler : function() {
            parent.$.modalDialog.openner_treeGrid = authGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
            var f = parent.$.modalDialog.handler.find('#form');
            f.submit();
          }
        } ]
      });
    }
  }

  function addFun() {
    parent.$.modalDialog({
      title : '添加资源',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/module/moduleAddPermission',
      buttons : [ {
        text : '添加',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = authGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

</script>
</head>
<body>
  <div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'west',border:false,split:true,title:'菜单列表'" title=""
      style="overflow: hidden; width: 300px;">
      <table id="treeGrid"></table>
    </div>
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
      <table id="authGrid"></table>
    </div>
  </div>
  <div id="toolbar" style="display: none;">
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/addPage')}">
      <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
        data-options="plain:true,iconCls:'pencil_add'">添加</a>
    </c:if>
    <a
      onclick="authGrid.treegrid('reload');" href="javascript:void(0);" class="easyui-linkbutton"
      data-options="plain:true,iconCls:'transmit'">刷新</a>
  </div>

  <div id="menu" class="easyui-menu" style="width: 120px; display: none;">
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/addPage')}">
      <div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/delete')}">
      <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
    </c:if>
    <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/editPage')}">
      <div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
    </c:if>
  </div>
</body>
</html>