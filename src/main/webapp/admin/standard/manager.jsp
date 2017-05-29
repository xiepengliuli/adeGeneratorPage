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
    dataGrid = $('#dataGrid')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/standard/dataGrid?tdiseaseId=${tdiseaseId}&isDel=0',
              fit : true,
              fitColumns : true,
              border : false,
              idField : 'id',
              rownumbers : true,
              striped : true,
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
                    field : 'cnTitle',
                    title : '中文题名',
                    sortable : true,
                    width : 200,
                    height : 93
                  },
                  {
                    field : 'orderNum',
                    title : '序号',
                    sortable : true,
                    width : 200,
                    height : 93
                  },
                  {
                    field : 'publishOrg',
                    title : '发布机构',
                    sortable : true,
                    width : 200,
                    height : 93
                  },
                  {
                    field : 'type',
                    title : '标准类型',
                    sortable : true,
                    width : 100,

                    formatter : function(value, row, index) {
                      if (value == "0") {
                        return "中医标准";
                      } else if (value == "1") {
                        return "西医标准";
                      } else {
                        return "未知";
                      }
                    }
                  },
                  {
                    field : 'publishDate',
                    title : '发布日期',
                    sortable : true,
                    width : 100
                  },
                  {
                    field : 'updateUser',
                    title : '编辑人',
                    width : 100
                  },
                  {
                    field : 'updateTime',
                    title : '最后修改日期',
                    sortable : true,
                    width : 100,
                    formatter : function(value, row, index) {
                      if (value == null || value == "") {
                        return "";
                      } else {
                        var valueDate = new Date(value);
                        return valueDate.format("yyyy-MM-dd");
                      }
                    }
                  },
                  {
                    field : 'status',
                    title : '状态',
                    sortable : true,
                    width : 100,

                    formatter : function(value, row, index) {
                      if (value == "1") {
                        return "新建";
                      } else if (value == "2") {
                        return "待审核";
                      } else if (value == "3") {
                        return "审核通过";
                      } else if (value == "4") {
                        return '<a style="color:red;cursor:pointer;" onclick="showAuditSuggest(\''
                            + row.id + '\');">审核未通过</a>';
                      } else if (value == "5") {
                        return "发布通过";
                      } else if (value == "6") {
                        return '<a style="color:red;cursor:pointer;" onclick="showPublishSuggest(\''
                            + row.id + '\');">发布未通过</a>';
                      } else if (value == "7") {
                        return '<a style="color:red;cursor:pointer;" onclick="showPublishRevokeSuggest(\''
                            + row.id + '\');">发布撤销</a>';
                      } else {
                        return "未知";
                      }
                    }
                  },
                  {
                    field : 'action',
                    title : '操作',
                    width : 200,

                    formatter : function(value, row, index) {
                      var tempFileSelect = "<tr><tr>\n"
                          + "  <td colspan=\"4\"><input  id=\"aaa\"  style=\"width: 500px\"\n" +
                    "> <a id=\"btn\" href=\"#\" \n"
                          + "   onclick=\"aaaa(this)\"></a></td>\n" + "</tr></tr>";

                      var canEditString = "&nbsp;&nbsp;";//编辑
                      canEditString += $
                          .formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/pencil.png');

                      var canDeleteString = "&nbsp;&nbsp;"; //删除
                      canDeleteString += $
                          .formatString(
                              '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');

                      var canAuditSubmitString = "&nbsp;&nbsp;";//提交审核
                      canAuditSubmitString += $
                          .formatString(
                              '<img onclick="submitAuditFun(\'{0}\');" src="{1}" title="提交审核"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/accept.png');

                      var canAuditString = "&nbsp;&nbsp;";//审核
                      canAuditString += $
                          .formatString('<img onclick="auditFun(\'{0}\');" src="{1}" title="审核"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_edit.png');

                      var canPublishString = "&nbsp;&nbsp;";//发布
                      canPublishString += $
                          .formatString(
                              '<img onclick="publishFun(\'{0}\');" src="{1}" title="发布"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_go.png');

                      var canRevokePublishString = "&nbsp;&nbsp;";//撤销发布
                      canRevokePublishString += $
                          .formatString(
                              '<img onclick="revokePublishFun(\'{0}\');" src="{1}" title="撤销发布"/>',
                              row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/bug/bug_delete.png');

                      var canPreviewString = "&nbsp;&nbsp;";//预览
                      canPreviewString += $
                          .formatString(
                              '<img onclick="previewFun(\'{0}\');" src="{1}" title="预览"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/world/world.png');
                      var str = '';

                      if (row.canEdit == true) {
                        str += canEditString;
                      }
                      if (row.canDelete == true) {
                        str += canDeleteString;
                      }
                      if (row.canAuditSubmit == true) {
                        str += canAuditSubmitString;
                      }
                      if (row.canAudit == true) {
                        str += canAuditString;
                      }
                      if (row.canPublish == true) {
                        str += canPublishString;
                      }
                      if (row.canRevokePublish == true) {
                        str += canRevokePublishString;
                      }
                      if (row.canPreview == true) {
                        str += canPreviewString;
                      }
                      return str;
                    }
                  } ] ],
              toolbar : '#toolbar',
              onLoadSuccess : function() {

                $('#searchForm table').show();
                parent.$.messager.progress('close');
                parent.$.modalDialog.openner_dataGrid = dataGrid;

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

  function showAuditSuggest(id) {
    parent.$.modalDialog({
      title : '审核意见',
      width : 550,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/standard/showAuditSuggest?tdiseaseId=' + id,
      buttons : [ {
        text : '关闭',
        handler : function() {
          parent.$.modalDialog.handler.dialog('close');
        }
      } ]
    });
  }
  function showPublishSuggest(id) {
    parent.$.modalDialog({
      title : '发布意见',
      width : 550,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/standard/showPublishSuggest?tdiseaseId='
          + id,
      buttons : [ {
        text : '关闭',
        handler : function() {
          parent.$.modalDialog.handler.dialog('close');
        }
      } ]
    });
  }
  function showPublishRevokeSuggest(id) {
    parent.$.modalDialog({
      title : '发布撤销意见',
      width : 550,
      height : 300,
      href : '${pageContext.request.contextPath}/admin/standard/publishRevokeSuggest?tdiseaseId='
          + id,
      buttons : [ {
        text : '关闭',
        handler : function() {
          parent.$.modalDialog.handler.dialog('close');
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
        $.post('${pageContext.request.contextPath}/admin/standard/deleteByIdToRecycleBin', {
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

      parent.$.modalDialog.openner_dataGrid = dataGrid;

      parent.$.modalDialog({
        title : '编辑',
        width : 1000,
        height : 600,
        href : '${pageContext.request.contextPath}/admin/standard/editPage?id=' + id,
        onBeforeClose : function() {
          parent.$.modalDialog.handler.find('#uploadify').uploadify("destroy");
        }
      });
    }
  }

  //打开添加窗体
  function addFun() {

    parent.$.modalDialog({
      title : '添加',
      width : 1000,
      height : 600,
      href : '${pageContext.request.contextPath}/admin/standard/addPage?tdiseaseId=${tdiseaseId}',
      onBeforeClose : function() {
        parent.$.modalDialog.handler.find('#uploadify').uploadify("destroy");
      }
    });
  }

  //搜索
  function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
  }
  //清空条件
  function cleanFun() {
    $('#searchForm').form("reset");
    dataGrid.datagrid('load', {});
  }

  //批量删除
  function batchDelete() {
    var rows = dataGrid.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      parent.$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
        if (r) {
          for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
          }
        }
        $
            .getJSON(
                '${pageContext.request.contextPath}/admin/standard/deleteBatchByIdsToRecycleBin', {
                  ids : ids.join(',')
                }, function(result) {
                  if (result.success) {
                    dataGrid.datagrid('load');
                    dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                        'clearSelections');
                  }
                });
      });
    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要删除的记录！'
      });
    }
  }

  //提交审核
  function submitAuditFun(id) {
    parent.$.messager.confirm('询问', '您是否要提交当前选中的项目？', function(b) {
      if (b) {
        $
            .getJSON(
                "${pageContext.request.contextPath}/admin/standard/submitAudit?type=0&tstandardId="
                    + id, function(data) {
                  if (data.success) {
                    dataGrid.datagrid('reload');
                    dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                        'clearSelections');
                  } else {
                    alert("提交审核失败!");
                  }
                });
      }
    });
  }
  //审核
  function auditFun(id) {
    $("#auditDiv_form").form("reset");
    $('#auditDiv').dialog(
        {
          title : '审核',
          width : 400,
          height : 250,
          closed : false,
          cache : false,
          modal : true,
          buttons : [ {
            text : '确定',
            handler : function() {
              if ($("#auditDiv_form :checked").size() <= 0) {
                alert("请选择审核结果！");
                return;
              }
              if (!$("#auditDiv_form").form("validate")) {
                return;
              }
              $.getJSON(
                  "${pageContext.request.contextPath}/admin/standard/audit?type=1&tstandardId="
                      + id, $("#auditDiv_form").serialize(), function(data) {
                    if (data.success) {
                      dataGrid.datagrid('reload');
                      dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                          'clearSelections');
                      $('#auditDiv').dialog("close");
                    } else {
                      alert("审核失败!");
                    }
                  })
            }
          } ]
        });

  }

  //批量审核
  function batchAuditFun() {

    $("#auditDiv_form").form("reset");

    var rows = dataGrid.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].id);
      }

      $('#auditDiv').dialog(
          {
            title : '批量审核',
            width : 400,
            height : 250,
            closed : false,
            cache : false,
            modal : true,
            buttons : [ {
              text : '确定',
              handler : function() {
                if ($("#auditDiv_form :checked").size() <= 0) {
                  alert("请选择审核结果！");
                  return;
                }
                if (!$("#auditDiv_form").form("validate")) {
                  return;
                }
                $.getJSON(
                    "${pageContext.request.contextPath}/admin/standard/batchAudit?type=1&ids="
                        + ids.join(','), $("#auditDiv_form").serialize(), function(data) {
                      if (data.success) {
                        dataGrid.datagrid('reload');
                        dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                            'clearSelections');
                        $('#auditDiv').dialog("close");
                      } else {
                        alert("批量审核失败!");
                      }
                    })
              }
            } ]
          });

    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要操作的记录！'
      });
    }

  }
  //发布
  function publishFun(id) {
    $("#publishDiv_form").form("reset");
    $('#publishDiv').dialog(
        {
          title : '发布',
          width : 400,
          height : 250,
          closed : false,
          cache : false,
          modal : true,
          buttons : [ {
            text : '确定',
            handler : function() {
              if ($("#publishDiv :checked").size() <= 0) {
                alert("请选择发布结果！");
                return;
              }
              if (!$("#publishDiv_form").form("validate")) {
                return;
              }
              $.getJSON(
                  "${pageContext.request.contextPath}/admin/standard/publish?type=2&tstandardId="
                      + id, $("#publishDiv_form").serialize(), function(data) {
                    if (data.success) {
                      dataGrid.datagrid('reload');
                      dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                          'clearSelections');
                      $('#publishDiv').dialog("close");
                    } else {
                      alert("发布失败!");
                    }
                  })
            }
          } ]
        });

  }
  //批量发布
  function batchPublishFun() {
    $("#publishDiv_form").form("reset");

    var rows = dataGrid.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      for (var i = 0; i < rows.length; i++) {
        ids.push(rows[i].id);
      }

      $('#publishDiv').dialog(
          {
            title : '批量发布',
            width : 400,
            height : 250,
            closed : false,
            cache : false,
            modal : true,
            buttons : [ {
              text : '确定',
              handler : function() {
                if ($("#publishDiv :checked").size() <= 0) {
                  alert("请选择发布结果！");
                  return;
                }
                if (!$("#publishDiv_form").form("validate")) {
                  return;
                }
                $.getJSON(
                    "${pageContext.request.contextPath}/admin/standard/batchPublish?type=2&ids="
                        + ids.join(','), $("#publishDiv_form").serialize(), function(data) {
                      if (data.success) {
                        dataGrid.datagrid('reload');
                        dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                            'clearSelections');
                        $('#publishDiv').dialog("close");
                      } else {
                        alert("批量发布失败!");
                      }
                    })
              }
            } ]
          });

    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要操作的记录！'
      });
    }

  }
  //撤销发布
  function revokePublishFun(id) {
    $("#revokePublishDiv_form").form("reset");
    $('#revokePublishDiv').dialog(
        {
          title : '撤销发布',
          width : 400,
          height : 200,
          closed : false,
          cache : false,
          modal : true,
          buttons : [ {
            text : '确定',
            handler : function() {
              if ($("#revokePublishDiv_form").form("validate")) {
                $.getJSON(
                    "${pageContext.request.contextPath}/admin/standard/revokePublish?type=3&tstandardId="
                        + id, $("#revokePublishDiv_form").serialize(), function(data) {
                      if (data.success) {
                        dataGrid.datagrid('reload');
                        dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid(
                            'clearSelections');
                        $('#revokePublishDiv').dialog("close");
                      } else {
                        alert("撤销发布失败!");
                      }
                    })
              }
            }
          } ]
        });
  }
  //数据回收站
  function recyclebinFun() {
    parent.$
        .modalDialog({
          title : '回收站',
          width : 800,
          height : 500,
          href : '${pageContext.request.contextPath}/admin/standard/recyclebin?tdiseaseId=${tdiseaseId}',
          onBeforeClose : function() {
            dataGrid.datagrid('load');
            dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
          }
        });
  }

  function previewFun(id) {
    window.open("${pageContext.request.contextPath}/admin/standard/standardDetail?id=" + id);
  }

  function standardExcelImport() {
    parent.$.modalDialog({
      title : '标准导入',
      width : 550,
      height : 240,
      href : '${pageContext.request.contextPath}/admin/standard/standardImportUI',
      buttons : [ {
        text : '导入',
        handler : function() {
          parent.$.modalDialog.openner_treeGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
          var f = $.modalDialog.handler.find('#form');
          f.submit();
        }
      } ]
    });
  }
</script>
</head>
<body>
  <div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false"
      style="height: 140px; overflow: hidden;">
      <form id="searchForm" style="margin: 5px;">
        <input name="cnTitle" class="span2 easyui-textbox" data-options="width:150,prompt: '中文题名'" />&nbsp;&nbsp;
        <%
        	//标准类型
        %>
        标准类型：&nbsp; <select class="easyui-combobox" name="type" style="width: 100px;">
          <option value="">全部</option>
          <option value="0">中医标准</option>
          <option value="1">西医标准</option>
        </select>
        <%
        	//发布机构
        %>
        &nbsp;&nbsp; <input name="publishOrg" class="span2 easyui-textbox"
          data-options="width:150,prompt: '发布机构'" />
        <%
        	//状态
        %>
        &nbsp;&nbsp; 状态：&nbsp; <select class="easyui-combobox" name="status" style="width: 100px;">
          <option value="">全部</option>
          <option value="1">新建</option>
          <option value="2">待审核</option>
          <option value="3">审核通过</option>
          <option value="4">审核未通过</option>
          <option value="5">发布通过</option>
          <option value="6">发布未通过</option>
          <option value="7">发布撤销</option>
        </select>
        <%
        	//编辑人
        %>
        &nbsp;&nbsp; <input name="updateUser" class="span2 easyui-textbox"
          data-options="width:150,prompt: '编辑人'" />
        <%
        	//最后修改时间
        %>
        &nbsp;&nbsp; <input class="easyui-datetimebox" name="updateTimeStart" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间开始',editable:false" /> <input
          class="easyui-datetimebox" name="updateTimeEnd" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间结束',editable:false" />
        <p />
        <div>
          &nbsp;&nbsp;发布日期：&nbsp;<input class="easyui-combobox" name="publishDateStart"
            style="width: 100px;"
            data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/admin/standard/getYears?start=1900&order=asc'" />
          至 <input class="easyui-combobox" name="publishDateEnd" style="width: 100px;"
            data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/admin/standard/getYears?start=1900&order=desc'" />
          <%
          	//查询、清空条件
          %>
          &nbsp;&nbsp; <a href="javascript:void(0);" class="easyui-linkbutton"
            data-options="iconCls:'map_magnifier',plain:true" onclick="searchFun();">查询</a><span
            class="toolbar-item dialog-tool-separator"></span><a href="javascript:void(0);"
            class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true"
            onclick="cleanFun();">清空条件</a>
        </div>
      </form>
      <c:if test="${diseasePrivilege.canEdit}">
        <%
        	//添加
        %>

        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true">添加</a>
        <span class="toolbar-item dialog-tool-separator"></span>
        <%
        	//批量删除
        %>
        <!--         <a onclick="batchDelete();" href="javascript:void(0);" class="easyui-linkbutton" -->
        <!--           data-options="plain:true">批量删除</a> -->
        <!--         <span class="toolbar-item dialog-tool-separator"></span> -->
      </c:if>

      <c:if test="${diseasePrivilege.canAudit}">
        <%
        	//批量审核
        %>
        <a onclick="batchAuditFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true">批量审核</a>
        <span class="toolbar-item dialog-tool-separator"></span>
      </c:if>
      <c:if test="${diseasePrivilege.canPublish}">
        <%
        	//批量发布
        %>
        <a onclick="batchPublishFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true">批量发布</a>
        <span class="toolbar-item dialog-tool-separator"></span>
      </c:if>

      <c:if test="${diseasePrivilege.canEdit}">
        <%
        	//数据回收站
        %>
        <a onclick="recyclebinFun();" href="javascript:void(0);" class="easyui-linkbutton"
          data-options="plain:true">数据回收站</a>
        <span class="toolbar-item dialog-tool-separator"></span>
        <!--         <a onclick="standardExcelImport();" href="javascript:void(0);" class="easyui-linkbutton" -->
        <!--           data-options="plain:true,iconCls:'pencil_add'">导入</a> -->
      </c:if>
    </div>

    <div data-options="region:'center',border:false">
      <table id="dataGrid"></table>
    </div>
  </div>
  <div id="menu" class="easyui-menu" style="width: 120px; display: none;">

    <div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
    <div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
    <!--     <div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div> -->
  </div>

  <div id="auditDiv">
    <form id="auditDiv_form" method="post">
      <table class="table table-hover table-condensed">
        <tr>
          <th>审核结果</th>
          <td><input type="radio" name="isPass" value="1">通过&nbsp;&nbsp;&nbsp;&nbsp;<input
            type="radio" name="isPass" value="2">未通过</td>
        </tr>
        <tr>
          <th>审核意见</th>
          <td><input type="text" name="content" class="easyui-textbox"
            data-options="multiline:true,validType:['length[0,500]']"
            style="height: 100px; width: 250px;" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="publishDiv">
    <form id="publishDiv_form" method="post">
      <table class="table table-hover table-condensed">
        <tr>
          <th>发布结果</th>
          <td><input type="radio" name="isPass" value="1">通过&nbsp;&nbsp;&nbsp;&nbsp;<input
            type="radio" name="isPass" value="2">未通过</td>
        </tr>
        <tr>
          <th>发布意见</th>
          <td><input type="text" name="content" class="easyui-textbox"
            data-options="multiline:true,validType:['length[0,500]']"
            style="height: 100px; width: 250px;" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="revokePublishDiv">
    <form id="revokePublishDiv_form" method="post">
      <table class="table table-hover table-condensed">
        <tr>
          <th>撤销发布意见</th>
          <td><input type="text" name="content" class="easyui-textbox"
            data-options="multiline:true,validType:['length[0,500]']"
            style="height: 100px; width: 250px;" /></td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>