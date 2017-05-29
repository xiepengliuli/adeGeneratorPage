<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>日志管理</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript">
  // 构造表单
  var dataGrid;
  $(function() {
    dataGrid = $('#dataGrid').datagrid({
      url : '${pageContext.request.contextPath}/admin/userLogs/dataGrid',
      fit : true,
      fitColumns : true,
      border : false,
      pagination : true,
      idField : 'id',
      pageSize : 10,
      pageList : [ 10, 20, 30, 40, 50 ],
      sortName : 'operateTime',
      sortOrder : 'desc',
      checkOnSelect : false,
      selectOnCheck : false,
      nowrap : false,
      frozenColumns : [ [ {
        field : 'id',
        title : '编号',
        width : 150,
        checkbox : true
      }, {
        field : 'userId',
        title : '操作人',
        width : 80,
        sortable : true
      } ] ],
      columns : [ [ {
        field : 'logType',
        title : '操作类型',
        width : 80,
        sortable : true
      }, {
        field : 'operateTime',
        title : '操作时间',
        width : 100,
        sortable : true,
        formatter : function(value, row, index) {
          var unixTimestamp = new Date(value);
          return unixTimestamp.toLocaleString();
        }
      }, {
        field : 'content',
        title : '操作描述',
        width : 200,
        sortable : true
      }, {
        field : 'ip',
        title : '登陆IP',
        width : 100,
        sortable : true
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
      },
      onLoadError : function() {
        $.messager.confirm('提示', '您未登录或者长时间没有操作，请重新登录！', function(result) {
          if (result) {
            parent.location.href = '${pageContext.request.contextPath}/adminlogin.jsp';
          }
        });
      }
    });
  });

  // 过滤条件
  function searchFun() {
    dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
  }

  // 清空条件
  function cleanFun() {
    $('#searchForm input').val('');
    dataGrid.datagrid('load', {});
  }

  //导出系统日志
  function downloadFun() {
    window.location = "${pageContext.request.contextPath}/admin/userLogs/dowExcel";
  }
</script>
</head>
<body>
  <div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false"
      style="height: 60px; overflow: hidden;">
      <form id="searchForm">
        <table class="table table-hover table-condensed" style="display: none; width: 100%;">
          <tr>
            <th style="width: 60px;">操作人:</th>
            <td style="width: 130px;"><input name="userId" placeholder="可以模糊查询操作人"
              class="span2" /></td>
            <th style="width: 60px;">操作类型:</th>
            <td style="width: 130px;"><input name="logType" placeholder="可以模糊查询操作类型"
              class="span2" /></td>
            <td><a href="javascript:void(0);" class="easyui-linkbutton"
              data-options="iconCls:'map_magnifier',plain:true" onclick="searchFun();">查询</a><a
              href="javascript:void(0);" class="easyui-linkbutton"
              data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空</a> <a
              onclick="downloadFun();" href="javascript:void(0);" class="easyui-linkbutton"
              data-options="plain:true,iconCls:'page_white_excel'">导出Excel</a> <a target="_blank"
              href="${pageContext.request.contextPath}/admin/userLogs/logList"
              class="easyui-linkbutton" data-options="plain:true,iconCls:'printer'">打印</a></td>
          </tr>
        </table>
      </form>
    </div>
    <div data-options="region:'center',border:false">
      <table id="dataGrid"></table>
    </div>
  </div>
</body>
</html>