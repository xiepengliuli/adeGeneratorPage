<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>资源管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript">
  var treeGrid;
  $(function() {
    treeGrid = $('#tree').tree({
      
//       data:
      url : '${pageContext.request.contextPath}/admin/testTree/tree',

      onContextMenu : function(e, node) {
        e.preventDefault();
        // 查找节点
        $('#tree').tree('select', node.target);
        // 显示快捷菜单
        $('#menu').menu('show', {
          left : e.pageX,
          top : e.pageY
        });
      },

      onLoadSuccess : function() {
        parent.$.messager.progress('close');
      }
    });
  });
</script>
</head>
<body>

  <div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',title:'North Title',split:true" style="height: 100px;">
      <c:if test="${fn:contains(LOGIN_USER.moduleList, '/admin/module/addPage')}">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
      </c:if>
      <a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'resultset_next'">展开</a> <a
        onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'resultset_previous'"
      >折叠</a> <a onclick="treeGrid.treegrid('reload');" href="javascript:void(0);" class="easyui-linkbutton"
        data-options="plain:true,iconCls:'transmit'"
      >刷新</a> <a onclick="treeFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'transmit'">异步树</a>
    </div>

    <div data-options="region:'center',border:false" title="" style="overflow: auto;">
      <ul id="tree"></ul>
    </div>
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
<script type="text/javascript">
  function deleteFun(id) {
    if (id != undefined) {
      treeGrid.treegrid('select', id);
    }
    var node = treeGrid.treegrid('getSelected');
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
              treeGrid.treegrid('reload');
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
      treeGrid.treegrid('select', id);
    }
    var node = treeGrid.treegrid('getSelected');
    if (node) {
      parent.$.modalDialog({
        title : '编辑资源',
        width : 500,
        height : 300,
        href : '${pageContext.request.contextPath}/admin/module/editPage?id=' + node.id,
        buttons : [ {
          text : '编辑',
          handler : function() {
            parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
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
      href : '${pageContext.request.contextPath}/admin/module/addPage',
      buttons : [ {
        text : '添加',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

  function treeFun() {
    parent.$.modalDialog({
      title : '显示资源',
      width : 500,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/module/treePage',
      buttons : [ {
        text : '添加',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

  function redo() {
    var node = treeGrid.tree('getSelected');
    if (node) {
      treeGrid.tree('expandAll', node.target);
    } else {
      treeGrid.tree('expandAll');
    }
  }


  function undo() {
    var node = treeGrid.tree('getSelected');
    if (node) {
      treeGrid.tree('collapseAll', node.target);
    } else {
      treeGrid.tree('collapseAll');
    }
  }
</script>
</html>