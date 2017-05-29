<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 自定义js -->
<script type="text/javascript">

var recordId='<%=request.getAttribute("recordId")%>';
var tableName='<%=request.getAttribute("tableName")%>';
  var dataGrid;

  $(function() {
    dataGrid = $('#dataGrid').datagrid({
      url : '${pageContext.request.contextPath}/logInfoSysController/dataGridRecord',
      fit : true,
      fitColumns : true,
      border : false,
      queryParams:{"id":recordId,"tableName":tableName},
      pagination : true,
      idField : 'id',
      pageSize : 10,
      pageList : [ 10, 20, 30, 40, 50 ],
      sortName : 'createDate',
      sortOrder : 'DESC',
      checkOnSelect : false,
      selectOnCheck : false,
      nowrap : false,
      frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'logUser',
				title : '操作人',
				width : 80,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'logOperationType',
				title : '操作类型',
				width : 80,
				sortable : true
			}, {
				field : 'logTime',
				title : '操作时间',
				width : 100,
				sortable : true,
				formatter : function(value, row, index) {
					var unixTimestamp = new Date(value);
					return unixTimestamp.toLocaleString();
				}
			} ] ],
      toolbar : '#toolbar',
      onLoadSuccess : function() {
        $('#form table').show();
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
			onLoadError : function()
			{
		        $.messager.confirm('提示', '您未登录或者长时间没有操作，请重新登录！', function(result){
		                if (result){
		                  parent.location.href='${pageContext.request.contextPath}/adminlogin.jsp';
		                }
		            });
			}
    });
    parent.$.messager.progress('close');
    $('#form').form({
      url : '${pageContext.request.contextPath}/weaStaticSubjectDetailController/add',
      onSubmit : function() {
        parent.$.messager.progress({
          title : '提示',
          text : '数据处理中，请稍后....'
        });
        var isValid = $(this).form('validate');
        if (!isValid) {
          parent.$.messager.progress('close');
        }
        return isValid;
      },
      success : function(result) {
        parent.$.messager.progress('close');
        alert(result + "::");
        result = $.parseJSON(result);
        alert(result);
        if (result.success) {
          alert(1);
          parent.$.messager.alert('成功', result.msg, 'info', function() {
            parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
            parent.$.modalDialog.handler.dialog('close');
          });
        } else {
          parent.$.messager.alert('错误', result.msg, 'error');
        }
      }
    });
  });

  function searchFun() {
    //     alert(1);
    dataGrid.datagrid('load', $.serializeObject($('#form')));
  }
  
	// 清空条件
	function cleanFun() {
	  	var id=$("#recordId").val();
		$('#form input').val('');
		$("#recordId").val(id);
		dataGrid.datagrid('load', $.serializeObject($('#form')));
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north'" style="height: 40px;">
				<form id="form">
						<input name="id" id="recordId" type="hidden" class="span2" value="${recordId}" readonly="readonly">
						<table>
								<tr>
										<th style="width: 50px;">操作人</th>
										<td style="width: 200px;"><input name="userName" id="userName" placeholder="可模糊查询操作人" class="span2"
												style="height: 20px;" /></td>
												 <th style="width: 60px;">操作时间</th>
            <td align="center" style="width: 30px;">从</td>
            <td style="width: 130px;">
              <input id="beginDate" name="beginDate" class="easyui-datebox" data-options="width:180,height:29,editable:false"  editable="false" ></input>
            </td>
            <td align="center" style="width: 30px;">到 </td>
            <td style="width: 150px;">
                <input id="endDate" name="endDate" validType="checkDateTo['#beginDate']" class="easyui-datebox" data-options="width:180,height:29,editable:false"  editable="false" ></input>
            </td>
										<td><a href="javascript:void(0);" class="easyui-linkbutton"
												data-options="iconCls:'map_magnifier',plain:true" onclick="searchFun();">查询</a><a
												href="javascript:void(0);" class="easyui-linkbutton"
												data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">重置</a></td>
								</tr>
						</table>
				</form>
		</div>
		<div data-options="region:'center',border:false">
				<table id="dataGrid"></table>
		</div>

</div>