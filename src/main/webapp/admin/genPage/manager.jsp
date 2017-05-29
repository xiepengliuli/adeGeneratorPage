<%@page import="cn.com.infcn.core.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>内容管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript">
  var dataGrid;
  $(function() {
    parent.$.messager.progress('close');
    dataGrid = $('#dataGrid').datagrid(
        {
          url : '${pageContext.request.contextPath}/admin/genPage/dataGrid',
          fit : true,
          fitColumns : true,
          border : false,
          idField : 'id',
          pagination : true,
          pageSize : 10,
          pageList : [ 10, 20, 30, 40, 50 ],
          checkOnSelect : false,
          selectOnCheck : false,
          nowrap : false,
          frozenColumns : [ [ {
            field : 'id',
            title : '编号',
            width : 150,
            hidden : true
          } ] ],
          columns : [ [
              {
                field : 'pageTitle',
                title : '页面标题',
                width : 200,
                height : 93
              },
              {
                field : 'pageUrl',
                title : '页面访问路径',
                width : 200,
                height : 93
              },

              {
                field : 'action',
                title : '操作',
                width : 100,

                formatter : function(value, row, index) {

                  var str = '';

                  str += "&nbsp;&nbsp;";
                  str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id,
                      '${pageContext.request.contextPath}/resources/css/images/extjs_icons/pencil.png');

                  str += "&nbsp;&nbsp;";
                  str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id,
                      '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');
                  str += "&nbsp;&nbsp;";
                  str += $.formatString('<img onclick="editElementFun(\'{0}\');" src="{1}" title="页面元素"/>', row.id,
                      '${pageContext.request.contextPath}/resources/css/images/extjs_icons/book_edit.png');
                  return str;
                }
              } ] ],
          toolbar : '#toolbar',
          onLoadSuccess : function() {

            $('#searchForm table').show();
            parent.$.messager.progress('close');
          }
        });
  });

  function deleteFun(id) {
    if (id == undefined) {
      var rows = dataGrid.datagrid('getSelections');
      id = rows[0].id;
    } else {
      dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.messager.confirm('询问', '您是否要删除当前选中的项目？', function(b) {
      if (b) {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/admin/genPage/delete', {
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
    if (id) {
      parent.$.modalDialog({
        title : '编辑',
        width : 600,
        height : 350,
        href : '${pageContext.request.contextPath}/admin/genPage/editPage?id=' + id,
        buttons : [ {
          text : '编辑',
          handler : function() {
            parent.$.modalDialog.openner_treeGrid = dataGrid; //因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
            var f = parent.$.modalDialog.handler.find('#form');
            f.submit();
          }
        } ]
      });
    }
  }

  function addFun() {

    parent.$.modalDialog({
      title : '添加',
      width : 600,
      height : 350,
      href : '${pageContext.request.contextPath}/admin/genPage/addPage',
      buttons : [ {
        text : '添加',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = dataGrid; //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }
  function editElementFun(id) {

    parent.$.modalDialog({
      title : '页面元素',
      width : 1300,
      height : 650,
      href : '${pageContext.request.contextPath}/admin/genPage/editElementPage?id=' + id,
      buttons : [{
        text : '生成html',
        handler : function() {
          parent.$.modalDialog.handler.find('#div_hide #generatorHtmlForOnePage').click();
        }
      },  {
        text : '复制到',
        handler : function() {
          parent.$.modalDialog.handler.find('#div_hide #copy_to').click();
        }
      }, {
        text : '保存',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = dataGrid; //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
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
  <div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 80px; overflow: hidden;">
      <form id="searchForm" style="margin: 5px;">
        <input name="pageTitle" class="span2 easyui-textbox" data-options="width:150,prompt: '页面标题'" /> <input name="pageUrl"
          class="span2 easyui-textbox" data-options="width:150,prompt: '页面访问路径'"
        /> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'map_magnifier',plain:true" onclick="searchFun();">查询</a><span
          class="toolbar-item dialog-tool-separator"
        ></span><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
      </form>

    </div>
    <div data-options="region:'center',border:false">
      <table id="dataGrid"></table>
    </div>
  </div>
  <div id="toolbar" style="display: none;">
    <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:' pencil_add'">添加</a>
  </div>

  <div id="menu" class="easyui-menu" style="width: 120px; display: none;">

    <div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
    <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
  </div>
</body>
</html>