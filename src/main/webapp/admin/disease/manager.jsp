<%@page import="cn.com.infcn.core.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<%-- '${pageContext.request.contextPath}/admin/disease/authorization?tdiseaseId=' + id --%>
<%-- '${pageContext.request.contextPath}/admin/disease/authorizationDataGrid?tdiseaseId=' + id --%>
<%-- '${pageContext.request.contextPath}/admin/disease/saveAuthorization?tdiseaseId=aa&canEdit=uid11,1&canAudit=uid,2&canPublish=uid,3 --%>
<head>
<title>内容管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript">
  var dataGrid;
  function showMenu(name) {
    $.getJSON("${pageContext.request.contextPath}/admin/disease/getMenuDataByName", {
      diseaseName : name
    }, function(data) {
      if (data.success) {
        parent.clickStandardMenmu(name);
      } else {
        alert(data.msg);
      }
    });
  }
  $(function() {
    parent.$.messager.progress('close');
    dataGrid = $('#dataGrid')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/disease/dataGrid',
              fit : true,
              fitColumns : true,
              border : false,
              idField : 'id',
              pagination : true,
              pageSize : 10,
              pageList : [ 10, 20, 30, 40, 50 ],
              sortName : 'orderNum',
              sortOrder : 'asc',
              checkOnSelect : false,
              selectOnCheck : false,
              nowrap : true,
              frozenColumns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                checkbox : true
              } ] ],
              columns : [ [
                  {
                    field : 'name',
                    title : '病名',
                    sortable : true,
                    width : 200,
                    height : 93,
                    formatter : function(value, row, index) {
                      return "<a style=\"color: black;cursor:pointer;\" onclick=\"showMenu('" + value + "');\">"
                          + value + "</a>";
                    }

                  },
                  {
                    field : 'tcmDiseaseName',
                    title : '中医病名',
                    sortable : true,
                    hidden:false,
                    width : 200
                  },
                  {
                    field : 'mmDiseaseName',
                    title : '西医病名',
                    sortable : true,
                    width : 200
                  },

                  {
                    field : 'createTime',
                    title : '创建时间',
                    sortable : true,
                    width : 120,

                    formatter : function(value, row, index) {
                      if (value == null || value == "") {
                        return "";
                      }
                      var unixTimestamp = new Date(value);
                      return unixTimestamp.format();
                    }
                    

                  },
                  {
                    field : 'updateTime',
                    title : '修改时间',
                    sortable : true,
                    width : 120,

                    formatter : function(value, row, index) {
                      if (value == null || value == "") {
                        return "";
                      }
                      var unixTimestamp = new Date(value);
                      return unixTimestamp.format();
                    }
                  },
                  {
                    field : 'orderNum',
                    title : '排序号',
                    sortable : true,
                    width : 100
                  },
                  //                   {
                  //                     field : 'status',
                  //                     title : '状态',
                  //                     width : 60,

                  //                     formatter : function(value, row, index) {
                  //                       if (value == "1") {
                  //                         return "发布";
                  //                       } else {
                  //                         return "停用";
                  //                       }
                  //                     }
                  //                   },
                  {
                    field : 'action',
                    title : '操作',
                    width : 100,

                    formatter : function(value, row, index) {

                      var str = '';

                      //                       str += "&nbsp;&nbsp;";
                      //                       if (row.status == "1") {
                      //                         str += $
                      //                             .formatString(
                      //                                 '<img id ="img_publish" onclick="publish(\'{0}\',\'{2}\');" src="{1}" title="停用"/>',
                      //                                 row.id,
                      //                                 '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_delete.png',
                      //                                 row.status);

                      //                       } else {
                      //                       }

                      str += "&nbsp;&nbsp;";
                      str += $
                          .formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/pencil.png');

                      str += "&nbsp;&nbsp;";
                      str += $
                          .formatString(
                              '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');
                      str += "&nbsp;&nbsp;";
                      str += $
                          .formatString(
                              '<img onclick="authorizationFun(\'{0}\');" src="{1}" title="授权"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/key.png');
                      return str;
                    }
                  } ] ],
              toolbar : '#toolbar',
              onLoadSuccess : function() {

                $('#searchForm table').show();
                parent.$.messager.progress('close');

                $(this).treegrid('tooltip');
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

  function authorizationFun(id) {
    parent.$.modalDialog({
      title : '授权',
      width : 750,
      height : 380,
      href : '${pageContext.request.contextPath}/admin/disease/authorization?tdiseaseId=' + id,
      buttons : [ {
        text : '保存',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = dataGrid; //因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
          var f = parent.$.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }

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
        $.post('${pageContext.request.contextPath}/admin/disease/delete', {
          id : id
        }, function(result) {
          if (result.success) {
            parent.$.messager.alert('提示', result.msg, 'info');
            dataGrid.datagrid('reload');
          } else {
            parent.$.messager.alert('提示', result.msg, 'info');
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
        width : 550,
        height : 350,
        href : '${pageContext.request.contextPath}/admin/disease/editPage?id=' + id,
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
      width : 550,
      height : 350,
      href : '${pageContext.request.contextPath}/admin/disease/addPage',
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
    <div data-options="region:'north',title:'查询条件',border:false"
      style="height: 80px; overflow: hidden;">
      <form id="searchForm" style="margin: 5px;">
        <input name="name" class="span2 easyui-textbox"
          data-options="width:150,prompt: '广安门医院命名的病名'" /> <input name="tcmDiseaseName"
          class="span2 easyui-textbox" data-options="width:150,prompt: '中医病名'" /> <input
          name="mmDiseaseName" class="span2 easyui-textbox" data-options="width:150,prompt: '西医病名'" />
        <span class="toolbar-item dialog-tool-separator"></span> <a href="javascript:void(0);"
          class="easyui-linkbutton" data-options="iconCls:'map_magnifier',plain:true"
          onclick="searchFun();">查询</a><span class="toolbar-item dialog-tool-separator"></span><a
          href="javascript:void(0);" class="easyui-linkbutton"
          data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
      </form>


    </div>
    <div data-options="region:'center',border:false">
      <table id="dataGrid"></table>
    </div>
    <div id="menu" class="easyui-menu" style="width: 120px; display: none;">
      <div id="aaaa" onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
      <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
      <!--     <div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div> -->
    </div>
    <div id="toolbar">
      <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
        data-options="plain:true,iconCls:'pencil_add'">添加</a>
    </div>
  </div>
</body>
</html>