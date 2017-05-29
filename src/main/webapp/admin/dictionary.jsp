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
  var management_dictionarys_datagrid;
  var management_dictionary_dialog;

  // 初始化datagrid
  $(function() {
    // 初始化数据
    management_dictionarys_datagrid = $('#management_dictionarys_datagrid').treegrid({
      url : '${pageContext.request.contextPath}/admin/dictionary/dictionaryDataGrid',
      idField : 'id',
      striped : true,
      fit : true,
      fitColumns : true,
      treeField : 'zdName',
      columns : [ [ {
        title : 'id',
        field : 'id',
        width : 50,
        checkbox : true
      }, {
        field : 'zdName',
        title : '名称',
        width : 200
      }, {
        field : 'zdCode',
        title : '编码',
        width : 100
      }, {
        field : 'zdSort',
        title : '排序',
        width : 100
      }, {
        field : 'zdUse',
        title : '是否使用',
        width : 100
      }, {
        field : 'zdDesc',
        title : '备注',
        width : 350
      } ] ],
      toolbar : '#toolbar',
      onLoadSuccess : function() {
        parent.$.messager.progress('close');
        $(this).datagrid('tooltip');
        management_dictionarys_datagrid.treegrid('collapseAll');

      }
    });

  });

  // 添加文件分类
  function management_dictionary_addFun() {
    management_dictionary_dialog = $('#management_dictionary_dialog').dialog({
      title : '添加文件分类',
      modal : true,
      buttons : [ {
        text : '确认',
        iconCls : 'pencil-add',
        handler : function() {
          ade.trim('management_dictionary_Form_zdName');
          ade.trim('management_dictionary_Form_zdCode');
          ade.trim('management_dictionary_Form_zdDesc');
          // 判断备注长度
          if ($('#management_dictionary_Form_zdDesc').val().length > 100) {
            $.messager.show({
              title : '提示',
              msg : '备注长度过长，请删减'
            });
            return;
          }
          // 提交前验证重复性
          $.ajax({
            type : 'POST',
            url : '${pageContext.request.contextPath}/admin/dictionary/verifyDictionary',
            data : {
              t : new Date(),
              zdName : management_dictionary_dialog.find('input[name=zdName]').val(),
              zdCode : management_dictionary_dialog.find('input[name=zdCode]').val(),
              parentId : $('#management_dictionary_Form_parentId').combobox('getValue')
            },
            dataType : 'json',
            success : function(result) {
              if (result.success) {
                // 提交表单
                $('#management_dictionary_Form').form('submit', {
                  url : '${pageContext.request.contextPath}/admin/dictionary/addDictionary',
                  success : function(r) {
                    var obj = jQuery.parseJSON(r);
                    if (obj.success) {
                      management_dictionary_dialog.dialog('close');
                      $('#management_dictionarys_input').val('');
                      management_dictionarys_datagrid.treegrid('reload');
                    }
                    $.messager.show({
                      title : '提示',
                      msg : obj.msg
                    });
                  }
                });

              } else {
                $.messager.show({
                  title : '提示',
                  msg : result.msg
                });
              }
            },
            error : function() {
            }
          });
        }
      }, {
        text : '取消',
        iconCls : 'pencil-remove',
        handler : function() {
          management_dictionary_dialog.dialog('close');
        }
      } ],
      onBeforeOpen : function() {
        management_dictionary_dialog.find("input").val('');
        $('#management_dictionary_Form_zdDesc').val('');
        $('#management_dictionary_Form_parentId_trId').show();
        $('#management_dictionary_Form_parentId_trId_hidden').hide();
      }
    });
    // 页面加载时默认为隐藏
    $('#management_dictionary_dialog_display').show();
    management_dictionary_dialog.dialog('open');
    var date = new Date();
    var random = date.getTime();

    $('#management_dictionary_Form_parentId').combotree({
      url : "${pageContext.request.contextPath}/admin/dictionary/dictionaryComboTree?&r=" + random
    });

  };

  // 编辑文件分类
  function management_dictionary_editFUN() {

    var date = new Date();
    var random = date.getTime();
    $('#management_dictionary_Form_parentId').combotree({
      url : "${pageContext.request.contextPath}/admin/dictionary/dictionaryComboTree?&r=" + random
    });
    var rows = management_dictionarys_datagrid.datagrid('getSelections');

    if (rows.length == 1) {
      management_dictionary_dialog = $('#management_dictionary_dialog').dialog({
        title : '编辑文件分类',
        modal : true,
        buttons : [ {
          text : '确认',
          iconCls : 'pencil-add',
          handler : function() {
            ade.trim('management_dictionary_Form_zdName');
            ade.trim('management_dictionary_Form_zdCode');
            ade.trim('management_dictionary_Form_zdDesc');
            // 判断备注长度
            if ($('#management_dictionary_Form_zdDesc').val().length > 100) {
              $.messager.show({
                title : '提示',
                msg : '备注长度过长，请删减'
              });
              return;
            }
            if (management_dictionary_dialog.find("input[name=name]").val() == rows[0].name) {
              $('#management_dictionary_Form').form('submit', {
                url : '${pageContext.request.contextPath}/admin/dictionary/editDictionary',
                success : function(r) {
                  var obj = jQuery.parseJSON(r);
                  if (obj.success) {
                    management_dictionary_dialog.dialog('close');
                    management_dictionarys_datagrid.treegrid('reload');
                  }
                  $.messager.show({
                    title : '提示',
                    msg : obj.msg
                  });
                }
              });
            } else {
              $.ajax({
                type : 'POST',
                url : '${pageContext.request.contextPath}/admin/dictionary/verifydictionary',
                data : {
                  t : new Date(),
                  name : management_dictionary_dialog.find('input[name=name]').val(),
                  parentId : rows[0].parentId
                },
                dataType : 'json',
                success : function(result) {
                  if (result.success) {
                    // 提交表单
                    $('#management_dictionary_Form').form('submit', {
                      url : '${pageContext.request.contextPath}/admin/dictionary/editDictionary',
                      success : function(r) {
                        var obj = jQuery.parseJSON(r);
                        if (obj.success) {
                          management_dictionary_dialog.dialog('close');
                          management_dictionarys_datagrid.treegrid('reload');
                        }
                        $.messager.show({
                          title : '提示',
                          msg : obj.msg
                        });
                      }
                    });
                  } else {
                    $.messager.show({
                      title : '提示',
                      msg : result.msg
                    });
                  }
                },
                error : function() {
                }
              });

            }
          }
        }, {
          text : '取消',
          iconCls : 'pencil-remove',
          handler : function() {
            management_dictionary_dialog.dialog('close');
          }
        } ],
        onBeforeOpen : function() {
          management_dictionary_dialog.find("input").val('');
          management_dictionary_dialog.find("input[name=id]").val(rows[0].id);
          //alert(rows[0].zdSort);
          $("#management_dictionary_Form_zdName").val(rows[0].zdName);
          $("#management_dictionary_Form_zdCode").val(rows[0].zdCode);
          $("#management_dictionary_Form_zdSort").val(rows[0].zdSort);
          $("#management_dictionary_Form_zdUse").val(rows[0].zdUse);
          $("#management_dictionary_Form_zdDesc").val(rows[0].zdDesc);
          $('#management_dictionary_Form_parentId').combotree('setValue', rows[0].parentId);
          $('#management_dictionary_Form_parentId_trId').hide();
          $('#management_dictionary_Form_parentId_tdId_hidden').html('');
          $('#management_dictionary_Form_parentId_tdId_hidden').html(rows[0].parentName);
          $('#management_dictionary_Form_parentId_trId_hidden').show();
        }
      });
      // 页面加载时默认为隐藏
      $('#management_dictionary_dialog_display').show();
      management_dictionary_dialog.dialog('open');
    } else {
      ade.messagerAlert('提示', '请选择一条信息进行操作!', 'error');
    }
  }

  // 删除文件分类
  function management_dictionarys_deleteFUN() {
    var rows = management_dictionarys_datagrid.datagrid('getSelections');
    if (rows.length = 1) {
      ade.messagerConfirm('确认', '您是否要删除当前选中的文件分类？', function(r) {
        if (r) {
          if (rows[0].parentId == null) {
            $.ajax({
              type : 'POST',
              url : '${pageContext.request.contextPath}/admin/dictionary/verifyDictionaryChildren',
              data : {
                t : new Date(),
                id : rows[0].id
              },
              dataType : 'json',
              success : function(result) {
                if (result.success) {
                  $.getJSON('${pageContext.request.contextPath}/admin/dictionary/deleteDictionary',
                      {
                        t : new Date(),
                        id : rows[0].id,
                        parentId : rows[0].parentId
                      }, function(result) {
                        if (result.success) {
                          management_dictionarys_datagrid.treegrid('reload');
                        }
                        $.messager.progress('close');
                        $.messager.show({
                          title : '提示',
                          msg : result.msg + '删除条数' + rows.length
                        });
                      });
                } else {
                  $.messager.show({
                    title : '提示',
                    msg : result.msg
                  });
                }
              },
              error : function() {
              }
            });
          } else {
            $.getJSON('${pageContext.request.contextPath}/admin/dictionary/deleteDictionary', {
              t : new Date(),
              id : rows[0].id,
              parentId : rows[0].parentId
            }, function(result) {
              if (result.success) {
                management_dictionarys_datagrid.treegrid('reload');
              }
              $.messager.progress('close');
              $.messager.show({
                title : '提示',
                msg : result.msg + '删除条数' + rows.length
              });
            });
          }
        }
      });
    } else {
      $.messager.alert('提示', '请选择一条信息进行操作!');
    }
  };
  function redo() {
    var node = management_dictionarys_datagrid.treegrid('getSelected');
    if (node) {
      management_dictionarys_datagrid.treegrid('expandAll', node.id);
    } else {
      management_dictionarys_datagrid.treegrid('expandAll');
    }
  }

  function undo() {
    var node = management_dictionarys_datagrid.treegrid('getSelected');
    if (node) {
      management_dictionarys_datagrid.treegrid('collapseAll', node.id);
    } else {
      management_dictionarys_datagrid.treegrid('collapseAll');
    }
  }
</script>
</head>
<body>
  <div id="toolbar" style="display: none;">
    <a href="javascript:void(0);" onclick="management_dictionary_addFun();"
      class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">增加</a> <a
      href="javascript:void(0);" onclick="management_dictionary_editFUN();"
      class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil'">修改</a> <a
      href="javascript:void(0);" onclick="management_dictionarys_deleteFUN();"
      class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_delete'">删除</a> <a
      onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton"
      data-options="plain:true,iconCls:'resultset_next'">展开</a> <a onclick="undo();"
      href="javascript:void(0);" class="easyui-linkbutton"
      data-options="plain:true,iconCls:'resultset_previous'">折叠</a>
  </div>

  <div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'center',border:false">
      <table id="management_dictionarys_datagrid"></table>
    </div>
  </div>


  <div id="management_dictionary_dialog" class="easyui-dialog" data-options="closed : true"
    style="width: 350px; height: 280px;" align="center">
    <div id="management_dictionary_dialog_display" style="display: none;">
      <form id="management_dictionary_Form" method="post">
        <input name="id" type="hidden" />
        <table>
          <tr>
            <th>字典项名称</th>
            <td><input name="zdName" id="management_dictionary_Form_zdName"
              class="easyui-validatebox" data-options="validType:'maxLength[50]',required:true"
              style="width: 189px;"
              onkeypress="if(event.keyCode==13||event.which==13){return false;}" /></td>
          </tr>
          <tr>
            <th>字典项编码</th>
            <td><input name="zdCode" id="management_dictionary_Form_zdCode"
              class="easyui-validatebox" data-options="validType:'maxLength[30]',required:true"
              style="width: 189px;"
              onkeypress="if(event.keyCode==13||event.which==13){return false;}" /></td>
          </tr>
          <tr id="management_dictionary_Form_parentId_trId">
            <th>父分类</th>
            <td><select name="parentId" id="management_dictionary_Form_parentId"
              class="easyui-combotree" style="width: 195px;"></select></td>
          </tr>
          <tr id="management_dictionary_Form_parentId_trId_hidden" style="display: none;">
            <th>父分类</th>
            <td id="management_dictionary_Form_parentId_tdId_hidden" align="left"></td>
          </tr>
          <tr>
            <th>字典项排序</th>
            <td><input name="zdSort" id="management_dictionary_Form_zdSort"
              class="easyui-validatebox" data-options="validType:'maxLength[9]',required:true"
              style="width: 189px;"
              onkeypress="if(event.keyCode==13||event.which==13){return false;}" /></td>
          </tr>
          <tr>
            <th>是否在用</th>
            <td><input name="zdUse" id="management_dictionary_Form_zdUse"
              class="easyui-validatebox" data-options="validType:'maxLength[9]',required:true"
              style="width: 189px;"
              onkeypress="if(event.keyCode==13||event.which==13){return false;}" /></td>
          </tr>
          <tr>
            <th>备注</th>
            <td><textarea name="zdDesc" style="width: 190px; height: 60px;"
                id="management_dictionary_Form_zdDesc"></textarea></td>
          </tr>
        </table>
      </form>
    </div>
  </div>

</body>
</html>