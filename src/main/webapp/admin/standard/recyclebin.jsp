<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  var dataGrid111;
  $(function() {
    parent.$.messager.progress('close');
    dataGridRecyclebin = $('#dataGridRecyclebin')
        .datagrid(
            {
              url : '${pageContext.request.contextPath}/admin/standard/dataGrid?tdiseaseId=${tdiseaseId}&isDel=1',
              fit : true,
              fitColumns : true,
              border : false,
              idField : 'id',
              rownumbers : true,
              striped : true,
              pagination : true,
              pageSize : 10,
              pageList : [ 10, 20, 30, 40, 50 ],
              sortName : 'createTime',
              sortOrder : 'asc',
              checkOnSelect : false,
              selectOnCheck : false,
              nowrap : false,
              toolbar : '#tools',

              frozenColumns : [ [ {
                field : 'id',
                title : '编号',
                width : 150,
                checkbox : true,
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
                    field : 'publishOrg',
                    title : '发布机构',
                    sortable : true,
                    width : 200,
                    height : 93
                  },
                  {
                    field : 'type',
                    title : '标准类型',
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
                    field : 'action',
                    title : '操作',
                    width : 100,

                    formatter : function(value, row, index) {

                      var str = '';

                      str += "&nbsp;&nbsp;";
                      str += $
                          .formatString(
                              '<img onclick="restoreFun(\'{0}\');" src="{1}" title="还原"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/arrow_turn_left.png');

                      str += "&nbsp;&nbsp;";
                      str += $
                          .formatString(
                              '<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id,
                              '${pageContext.request.contextPath}/resources/css/images/extjs_icons/cancel.png');
                      return str;
                    }
                  } ] ],
              onLoadSuccess : function() {
                parent.$.messager.progress('close');
              }
            });
  });

  function deleteFun(id) {
    if (id == undefined) {
      var rows = dataGridRecyclebin.datagrid('getSelections');
      id = rows[0].id;
    } else {
      dataGridRecyclebin.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.messager.confirm('询问', '您是否要删除当前选中的项目？', function(b) {
      if (b) {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/admin/standard/delete', {
          id : id
        }, function(result) {
          if (result.success) {
            dataGridRecyclebin.datagrid('reload');
            dataGrid.datagrid('reload');
          }
          parent.$.messager.progress('close');
        }, 'JSON');
      }
    });
  }

  //批量删除
  function batchDeleteFun() {
    var rows = dataGridRecyclebin.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      parent.$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
        if (r) {
          for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
          }
        }
        $.getJSON('${pageContext.request.contextPath}/admin/standard/batchDelete', {
          ids : ids.join(',')
        }, function(result) {
          if (result.success) {
            dataGridRecyclebin.datagrid('load');
            dataGrid.datagrid('load');
            dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
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
  function restoreFun(id) {
    if (id == undefined) {
      var rows = dataGridRecyclebin.datagrid('getSelections');
      id = rows[0].id;
    } else {
      dataGridRecyclebin.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.messager.confirm('询问', '您是否要还原当前选中的项目？', function(b) {
      if (b) {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        $.post('${pageContext.request.contextPath}/admin/standard/restore', {
          id : id
        }, function(result) {
          if (result.success) {
            dataGridRecyclebin.datagrid('reload');
            dataGrid.datagrid('reload');
          }
          parent.$.messager.progress('close');
        }, 'JSON');
      }
    });
  }

  //批量还原
  function batchRestoreFun() {
    var rows = dataGridRecyclebin.datagrid('getChecked');
    var ids = [];
    if (rows.length > 0) {
      parent.$.messager.confirm('确认', '您是否要还原当前选中的项目？', function(r) {
        if (r) {
          for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
          }
        }
        $.getJSON('${pageContext.request.contextPath}/admin/standard/batchRestore', {
          ids : ids.join(',')
        }, function(result) {
          if (result.success) {
            dataGridRecyclebin.datagrid('load');
            dataGrid.datagrid('load');
            dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
          }
        });
      });
    } else {
      parent.$.messager.show({
        title : '提示',
        msg : '请勾选要还原的记录！'
      });
    }
  }
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <div data-options="region:'center',border:false">
    <table id="dataGridRecyclebin"></table>
  </div>
  <div id="tools" style="height: auto">
    <a href="javascript:void(0)" class="easyui-linkbutton"
      data-options="iconCls:'arrow_arrow_turn_left',plain:true" onclick="batchRestoreFun()">批量还原</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
      data-options="iconCls:'cancel',plain:true" onclick="batchDeleteFun()">批量删除</a>
  </div>

</div>

