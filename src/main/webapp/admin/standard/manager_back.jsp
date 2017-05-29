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
  var editor_mmStandard_add;
  var editor_tcmStandard_add;
  var editor_effectStandard_add;
  var editor_tcmDiseaseDialectical_add;//中医辨证
  var editor_typeStag_add;//分型分期
  var editor_effectTarget_add;//疗效指标
  var editor_remark_add;//备注

  var editor_mmStandard_edit;
  var editor_tcmStandard_edit;
  var editor_effectStandard_edit;
  var editor_tcmDiseaseDialectical_edit;//中医辨证
  var editor_typeStag_edit;//分型分期
  var editor_effectTarget_edit;//疗效指标
  var editor_remark_edit;//备注

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
              sortName : 'updateTime',
              sortOrder : 'desc',
              checkOnSelect : false,
              selectOnCheck : false,
              nowrap : true,
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
                        return "发布撤销";
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
  function deleteAttach(id) {
    $.post("${pageContext.request.contextPath}/admin/standard/deleteAttach", {
      'id' : id
    }, function(data) {
      if (data.success) {
        $("#edit_attach_show_clear div[id='" + id + "']").remove();
      } else {
        alert(data.msg);
      }
    }, "json");
  }
  function editFun(id) {
    if (id) {
      $("#edit_attach_show_clear").empty();//清空编辑窗体中的文件列表
      $("#edit_hidden_input_clear").empty();//清空附件上传的隐藏域
      initEditPageAttachs();//初始化组件
      $('#editDiv')
          .dialog(
              {
                title : '编辑',
                width : 1050,
                height : 600,
                closed : false,
                cache : false,
                modal : true,
                onOpen : function(event, ui) { // 打开Dialog后创建编辑器 

                  if (editor_mmStandard_edit) {
                  } else {
                    editor_mmStandard_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="mmStandard"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_tcmStandard_edit) {
                  } else {
                    editor_tcmStandard_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="tcmStandard"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_effectStandard_edit) {
                  } else {
                    editor_effectStandard_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="effectStandard"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_tcmDiseaseDialectical_edit) {//中医辨证
                  } else {
                    editor_tcmDiseaseDialectical_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="tcmDiseaseDialectical"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_typeStag_edit) {//分型分期
                  } else {
                    editor_typeStag_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="typeStag"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_effectTarget_edit) {//疗效指标
                  } else {
                    editor_effectTarget_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="effectTarget"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }
                  if (editor_remark_edit) {//备注
                  } else {
                    editor_remark_edit = KindEditor
                        .create(
                            '#editDiv_form textarea[name="remark"]',
                            {
                              uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                              fileManagerJson : 'file_manager',//文件管理
                              autoHeightMode : true,
                              allowUpload : true,
                            });
                  }

                  $
                      .post(
                          "${pageContext.request.contextPath}/admin/standard/editData?id=" + id,
                          {},
                          function(data) {

                            $("#editDiv_form").form("load", data);

                            editor_mmStandard_edit.html(data.mmStandard);
                            editor_tcmStandard_edit.html(data.tcmStandard);
                            editor_effectStandard_edit.html(data.effectStandard);
                            editor_tcmDiseaseDialectical_edit.html(data.tcmDiseaseDialectical);
                            editor_typeStag_edit.html(data.typeStag);
                            editor_effectTarget_edit.html(data.effectTarget);
                            editor_remark_edit.html(data.remark);

                            var attachsPage = data.attachsPage;
                            $
                                .each(
                                    data.attachsPage,
                                    function(i, attachData) {
                                      var tempAttachDiv = "              <div id=\""+attachData.id+"\"><div>\n"
                                          + "                <span id=\""+attachData.id+"\">"
                                          + attachData.name
                                          + "</span><a\n"
                                          + "                  style=\"margin-left: 5px; color: red;\" href=\"javascript:deleteAttach('"
                                          + attachData.id
                                          + "');\">X</a>\n"
                                          + "              </div></div>";
                                      $("#edit_attach_show_clear").append(tempAttachDiv);
                                    })

                          }, "json");
                },
                buttons : [ {
                  text : '保存',
                  handler : function() {
                    editData();
                  }
                }, {
                  text : '关闭',
                  handler : function() {
                    $('#editDiv').dialog('close');
                  }
                } ]
              });
    }
  }

  //打开添加窗体
  function addFun() {

    $("#add_hidden_input_clear").empty();//清空附件上传的隐藏域
    initAddPageAttachs();//初始化组件

    $('#addDiv').dialog(
        {
          title : '添加',
          width : 1050,
          height : 600,
          closed : false,
          cache : false,
          modal : true,
          onOpen : function(event, ui) { // 打开Dialog后创建编辑器 
            $('#addDiv_form').form("reset");

            if (editor_mmStandard_add) {
              editor_mmStandard_add.html('');
              editor_mmStandard_add.sync();
            } else {
              editor_mmStandard_add = KindEditor.create('#addDiv_form textarea[name="mmStandard"]',
                  {
                    uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                    fileManagerJson : 'file_manager',//文件管理
                    autoHeightMode : true,
                    allowUpload : true,
                  });
            }

            if (editor_tcmStandard_add) {
              editor_tcmStandard_add.html('');
              editor_tcmStandard_add.sync();
            } else {
              editor_tcmStandard_add = KindEditor.create(
                  '#addDiv_form textarea[name="tcmStandard"]', {
                    uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                    fileManagerJson : 'file_manager',//文件管理
                    autoHeightMode : true,
                    allowUpload : true,
                  });
            }

            if (editor_effectStandard_add) {
              editor_effectStandard_add.html('');
              editor_effectStandard_add.sync();
            } else {
              editor_effectStandard_add = KindEditor.create(
                  '#addDiv_form textarea[name="effectStandard"]', {
                    uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                    fileManagerJson : 'file_manager',//文件管理
                    autoHeightMode : true,
                    allowUpload : true,
                  });
            }
            if (editor_tcmDiseaseDialectical_add) {//中医辨证
              editor_tcmDiseaseDialectical_add.html('');
              editor_tcmDiseaseDialectical_add.sync();
            } else {
              editor_tcmDiseaseDialectical_add = KindEditor.create(
                  '#addDiv_form textarea[name="tcmDiseaseDialectical"]', {
                    uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                    fileManagerJson : 'file_manager',//文件管理
                    autoHeightMode : true,
                    allowUpload : true,
                  });
            }
            if (editor_typeStag_add) {//分型分期
              editor_typeStag_add.html('');
              editor_typeStag_add.sync();
            } else {
              editor_typeStag_add = KindEditor.create('#addDiv_form textarea[name="typeStag"]', {
                uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                fileManagerJson : 'file_manager',//文件管理
                autoHeightMode : true,
                allowUpload : true,
              });
            }
            if (editor_effectTarget_add) {//疗效指标
              editor_effectTarget_add.html('');
              editor_effectTarget_add.sync();
            } else {
              editor_effectTarget_add = KindEditor.create(
                  '#addDiv_form textarea[name="effectTarget"]', {
                    uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                    fileManagerJson : 'file_manager',//文件管理
                    autoHeightMode : true,
                    allowUpload : true,
                  });
            }
            if (editor_remark_add) {//分型分期
              editor_remark_add.html('');
              editor_remark_add.sync();
            } else {
              editor_remark_add = KindEditor.create('#addDiv_form textarea[name="remark"]', {
                uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',//上传
                fileManagerJson : 'file_manager',//文件管理
                autoHeightMode : true,
                allowUpload : true,
              });
            }
          },
          buttons : [ {
            text : '保存',
            handler : function() {
              addData("");
            }
          }, {
            text : '保存并提交审核',
            handler : function() {
              addData("saveAndSubmit");
            }
          },

          {
            text : '关闭',
            handler : function() {
              $('#addDiv').dialog('close');
            }
          } ]
        });

  }

  //添加提交
  function addData(saveAndSubmit) {
    //检验

    if (!$('#addDiv_form').form('validate')) {
      return;
    }

    //     if (editor_mmStandard_add.isEmpty()) {
    //       alert("西医诊断标准为空或全为空");
    //       return;
    //     }
    if (editor_mmStandard_add.count("text") >= 100000) {
      alert("西医诊断标准的长度不能超过100000。");
      return;
    }
    //     if (editor_tcmStandard_add.isEmpty()) {
    //       alert("中医诊断标准为空或全为空");
    //       return;
    //     }
    if (editor_tcmStandard_add.count("text") >= 100000) {
      alert("中医诊断标准的长度不能超过100000。");
      return;
    }
    //     if (editor_effectStandard_add.isEmpty()) {
    //       alert("疗效判定标准为空或全为空");
    //       return;
    //     }
    if (editor_effectStandard_add.count("text") >= 100000) {
      alert("疗效判定标准的长度不能超过100000。");
      return;
    }
    if (editor_tcmDiseaseDialectical_add.count("text") >= 100000) {
      alert("中医辨证的长度不能超过100000。");
      return;
    }
    if (editor_typeStag_add.count("text") >= 100000) {
      alert("分型分期的长度不能超过100000。");
      return;
    }
    if (editor_effectTarget_add.count("text") >= 100000) {
      alert("疗效指标的长度不能超过100000。");
      return;
    }
    if (editor_remark_add.count("text") >= 100000) {
      alert("备注的长度不能超过100000。");
      return;
    }

    editor_mmStandard_add.sync();
    editor_tcmStandard_add.sync();
    editor_effectStandard_add.sync();
    editor_tcmDiseaseDialectical_add.sync();
    editor_typeStag_add.sync();
    editor_effectTarget_add.sync();
    editor_remark_add.sync();

    $.ajax({
      url : '${pageContext.request.contextPath}/admin/standard/add?saveAndSubmit=' + saveAndSubmit,
      data : $('#addDiv_form').serialize(),
      type : 'POST',
      dataType : 'json',
      success : function(result) {

        if (result.success) {
          parent.$.messager.alert('正确', result.msg, 'success');
          $('#addDiv').dialog('close');
          dataGrid.datagrid('reload');
        } else {
          parent.$.messager.alert('错误', result.msg, 'error');
        }
      }
    });
  }
  function editData() {
    //检验

    if (!$('#editDiv_form').form('validate')) {
      return;
    }

    //     if (editor_mmStandard_edit.isEmpty()) {
    //       alert("西医诊断标准为空或全为空");
    //       return;
    //     }
    if (editor_mmStandard_edit.count("text") >= 100000) {
      alert("西医诊断标准的长度不能超过100000。");
      return;
    }
    //     if (editor_tcmStandard_edit.isEmpty()) {
    //       alert("中医诊断标准为空或全为空");
    //       return;
    //     }
    if (editor_tcmStandard_edit.count("text") >= 100000) {
      alert("中医诊断标准的长度不能超过100000。");
      return;
    }
    //     if (editor_effectStandard_edit.isEmpty()) {
    //       alert("疗效判定标准为空或全为空");
    //       return;
    //     }
    if (editor_effectStandard_edit.count("text") >= 100000) {
      alert("疗效判定标准的长度不能超过100000。");
      return;
    }

    editor_mmStandard_edit.sync();
    editor_tcmStandard_edit.sync();
    editor_effectStandard_edit.sync();

    $.ajax({
      url : '${pageContext.request.contextPath}/admin/standard/edit',
      data : $('#editDiv_form').serialize(),
      type : 'POST',
      dataType : 'json',
      success : function(result) {

        if (result.success) {
          parent.$.messager.alert('正确', result.msg, 'success');
          $('#editDiv').dialog('close');
          dataGrid.datagrid('reload');
        } else {
          parent.$.messager.alert('错误', result.msg, 'error');
        }
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
    $.getJSON("${pageContext.request.contextPath}/admin/standard/submitAudit?type=0&tstandardId="
        + id, function(data) {
      if (data.success) {
        dataGrid.datagrid('reload');
        dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
      } else {
        alert("提交审核失败!");
      }
    });
    //     alert("功能开发中");
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

    //     alert("功能开发中。。。");
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
    //     alert("功能开发中。。。");
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

    //     alert("功能开发中。。。");
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
    //     alert("功能开发中。。。");
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
          } ]
        });

    //     alert("功能开发中。。。");
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
          },
        });
  }

  function initAddPageAttachs() {
    //上传多个文件，数据资源用
    $("#fileQueue").empty();//清空上传队列DIV
    $('#uploadify')
        .uploadify(
            {
              'swf' : '${pageContext.request.contextPath}/resources/plugins/uploadify/uploadify.swf',//上传按钮的图片，默认是这个flash文件
              'uploader' : '${pageContext.request.contextPath}/admin/standard/uploadFiles', //上传所处理的服务器
              //       'cancelImg' : '${pageContext.request.contextPath}/resources/images/uploadify-cancel.png',//取消图片
              'method' : 'post',
              //             'folder' : '/UploadFile',//上传后，所保存文件的路径
              'queueID' : 'fileQueue',//上传显示进度条的那个div
              'buttonText' : '请选择文件',
              //             progressData : 'percentage',
              'auto' : true,
              'multi' : false,
              'onFallback' : function() {//检测FLASH失败调用  
                alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
              },
              'onDisable' : function() {
                alert('uploadify is disable');
              },
              'onError' : function(errorType, errObj) {
                alert('The error was: ' + errObj.info);
              },
              'onUploadSuccess' : function(file, data, response) {

                //                                     alert('The file ' + file.name
                //                                         + ' was successfully uploaded with a response of '
                //                                         + response + ':' + data);
                var tempJson = JSON.parse(data);
                if ($("#add_hidden_input_clear input[id='" + file.name + "']").size() > 0) {

                } else {//如果没有添加过则添加隐藏域
                  var tempi = $("#add_hidden_input_clear input").size() / 3;
                  var tempsize = '<input type="hidden" name="attachs['+tempi+'].size" id="'+file.name+'" value="'+tempJson.size+'">';
                  $("#add_hidden_input_clear").append(tempsize);
                  var tempfileName_old = '<input type="hidden" name="attachs['+tempi+'].fileName_old" id="'+file.name+'" value="'+tempJson.fileName_old+'">';
                  $("#add_hidden_input_clear").append(tempfileName_old);
                  var filePath = '<input type="hidden" name="attachs['+tempi+'].filePath" id="'+file.name+'" value="'+tempJson.filePath+'">';
                  $("#add_hidden_input_clear").append(filePath);
                  var cancel = $("#" + file.id + " .cancel a");

                  cancel.click(function() {
                    $("#add_hidden_input_clear input[id='" + file.name + "']").remove();
                  });
                }
              }
            });
  }
  function initEditPageAttachs() {
    //上传多个文件，数据资源用
    $("#fileQueueEdit").empty();//清空上传队列DIV
    $('#uploadifyEdit')
        .uploadify(
            {
              'swf' : '${pageContext.request.contextPath}/resources/plugins/uploadify/uploadify.swf',//上传按钮的图片，默认是这个flash文件
              'uploader' : '${pageContext.request.contextPath}/admin/standard/uploadFiles', //上传所处理的服务器
              //       'cancelImg' : '${pageContext.request.contextPath}/resources/images/uploadify-cancel.png',//取消图片
              'method' : 'post',
              //             'folder' : '/UploadFile',//上传后，所保存文件的路径
              'queueID' : 'fileQueueEdit',//上传显示进度条的那个div
              'buttonText' : '请选择文件',
              //             progressData : 'percentage',
              'auto' : true,
              'multi' : false,
              'onFallback' : function() {//检测FLASH失败调用  
                alert("您未安装FLASH控件，无法上传图片！请安装FLASH控件后再试。");
              },
              'onDisable' : function() {
                alert('uploadify is disable');
              },
              'onError' : function(errorType, errObj) {
                alert('The error was: ' + errObj.info);
              },
              'onUploadSuccess' : function(file, data, response) {
                //                                     alert('The file ' + file.name
                //                                         + ' was successfully uploaded with a response of '
                //                                         + response + ':' + data);
                var tempJson = JSON.parse(data);
                if ($("#add_hidden_input_clear input[id='" + file.name + "']").size() > 0) {

                } else {//如果没有添加过则添加隐藏域
                  var tempi = $("#edit_hidden_input_clear input").size() / 3;
                  var tempsize = '<input type="hidden" name="attachs['+tempi+'].size" id="'+file.name+'" value="'+tempJson.size+'">';
                  $("#edit_hidden_input_clear").append(tempsize);
                  var tempfileName_old = '<input type="hidden" name="attachs['+tempi+'].fileName_old" id="'+file.name+'" value="'+tempJson.fileName_old+'">';
                  $("#edit_hidden_input_clear").append(tempfileName_old);
                  var filePath = '<input type="hidden" name="attachs['+tempi+'].filePath" id="'+file.name+'" value="'+tempJson.filePath+'">';
                  $("#edit_hidden_input_clear").append(filePath);
                  var cancel = $("#" + file.id + " .cancel a");

                  cancel.click(function() {
                    $("#edit_hidden_input_clear input[id='" + file.name + "']").remove();
                  });
                }
              }
            });
  }
  function previewFun(id) {
    window.open("${pageContext.request.contextPath}/web/standardDetail?id=" + id);
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
        <input name="cnTitle" class="span2 easyui-textbox" data-options="width:150,prompt: '中文题名'" />
        <%
        	//标准类型
        %>
        标准类型: <select class="easyui-combobox" name="type" style="width: 100px;">
          <option value="">全部</option>
          <option value="0">中医标准</option>
          <option value="1">西医标准</option>
        </select>
        <%
        	//发布机构
        %>
        <input name="publishOrg" class="span2 easyui-textbox"
          data-options="width:150,prompt: '发布机构'" />
        <%
        	//状态
        %>
        状态: <select class="easyui-combobox" name="status" style="width: 100px;">
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
        <input name="updateUser" class="span2 easyui-textbox" data-options="width:150,prompt: '编辑人'" />
        <%
        	//最后修改时间
        %>
        <input class="easyui-datetimebox" name="updateTimeStart" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间开始',editable:false" /> <input
          class="easyui-datetimebox" name="updateTimeEnd" type="text"
          data-options="width:150,height:25,prompt: '最后修改时间开始',editable:false" />
        <p />
        <div>
          <input class="easyui-combobox" name="publishDateStart" style="width: 100px;"
            data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/admin/standard/getYears?start=1900&order=asc'" />
          至 <input class="easyui-combobox" name="publishDateEnd" style="width: 100px;"
            data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath}/admin/standard/getYears?start=1900&order=desc'" />
          <span class="toolbar-item dialog-tool-separator"></span>
          <%
          	//查询、清空条件
          %>
          <a href="javascript:void(0);" class="easyui-linkbutton"
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


  <div id="addDiv">
    <form id="addDiv_form" method="post">
      <input type="hidden" name="tdiseaseId" value="${tdiseaseId}">
      <table class="table table-hover table-condensed">
        <tr>
          <th>序号</th>
          <td><input style="width: 400px;" name="orderNum" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,50]']" /></td>
          <!-- 0中医标准,1西医标准 -->
          <th>标准类型</th>
          <td><select class="easyui-combobox" name="type" style="width: 132px;">
              <option value="0">中医标准</option>
              <option value="1">西医标准</option>
          </select></td>
        </tr>
        <tr>
          <th>中文题名</th>
          <td><input name="cnTitle" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,500]']" style="width: 400px;" /></td>
          <th>英文题名</th>
          <td><input name="enTitle" class="easyui-validatebox" style="width: 400px;" /></td>
        </tr>
        <tr>
          <th>发布机构</th>
          <td><input name="publishOrg" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,500]']
          "
            style="width: 400px;" /></td>
          <th>发布日期</th>
          <td><input name="publishDate" type="text" style="height: 24px; width: 140px;"
            class="easyui-numberbox" data-options="" style="width:400px;"></td>
        </tr>
        <tr>
          <th>来源</th>
          <td><input name="source" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " style="width: 400px;" /></td>
          <th>全文语种</th>
          <td><select class="easyui-combobox" name="language" style="width: 132px;">
              <option value="中文">中文</option>
              <option value="英文">英文</option>
              <option value="其他语种">其他语种</option>
          </select></td>
        </tr>
        <tr>
          <th>西医病名</th>
          <td><input style="width: 400px;" name="mmDiseaseName" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " /></td>
          <th>中医病名</th>
          <td><input style="width: 400px;" name="tcmDiseaseName" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " /></td>
        </tr>
        <tr>
          <th>中医辨证</th>
          <td colspan="3"><textarea name="tcmDiseaseDialectical"
              style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>分型分期</th>
          <td colspan="3"><textarea name="typeStag" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>疗效指标</th>
          <td colspan="3"><textarea name="effectTarget" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>西医诊断标准</th>
          <td colspan="3"><textarea name="mmStandard" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>中医诊断标准</th>
          <td colspan="3"><textarea name="tcmStandard" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>疗效判定标准</th>
          <td colspan="3"><textarea name="effectStandard" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><textarea name="remark" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>原文</th>
        </tr>
        <tr>
          <td colspan="4">
            <div>
              <div id="fileQueue"></div>
              <input type="file" name="uploadify" id="uploadify">
            </div>
          </td>
        </tr>
      </table>
      <div id="add_hidden_input_clear" style="display: none;"></div>
    </form>
  </div>

  <div id="editDiv">
    <form id="editDiv_form" method="post">
      <input type="hidden" name="id">
      <table class="table table-hover table-condensed">
        <tr>
          <th>序号</th>
          <td><input style="width: 400px;" name="orderNum" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,50]']" /></td>
          <!-- 0中医标准,1西医标准 -->
          <th>标准类型</th>
          <td><select class="easyui-combobox" name="type" style="width: 132px;">
              <option value="0">中医标准</option>
              <option value="1">西医标准</option>
          </select></td>
        </tr>
        <tr>
          <th>中文题名</th>
          <td><input style="width: 400px;" name="cnTitle" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,500]']" /></td>
          <th>英文题名</th>
          <td><input style="width: 400px;" name="enTitle" class="easyui-validatebox" /></td>
        </tr>
        <tr>
          <th>发布机构</th>
          <td><input style="width: 400px;" name="publishOrg" class="easyui-validatebox"
            data-options="required:true,validType:['length[0,500]']
          " /></td>
          <th>发布日期</th>
          <td><input style="width: 400px;" name="publishDate" type="text"
            style="height: 24px; width: 140px;" class="easyui-numberbox" data-options=""></td>
        </tr>
        <tr>
          <th>来源</th>
          <td><input style="width: 400px;" name="source" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " /></td>
          <th>全文语种</th>
          <td><select class="easyui-combobox" name="language" style="width: 132px;">
              <option value="中文">中文</option>
              <option value="英文">英文</option>
              <option value="其他语种">其他语种</option>
          </select></td>
        </tr>
        <tr>
          <th>西医病名</th>
          <td><input style="width: 400px;" name="mmDiseaseName" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " /></td>
          <th>中医病名</th>
          <td><input style="width: 400px;" name="tcmDiseaseName" class="easyui-validatebox"
            data-options="validType:['length[0,500]']
          " /></td>
        </tr>
        <tr>
          <th>中医辨证</th>
          <td colspan="3"><textarea name="tcmDiseaseDialectical"
              style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>分型分期</th>
          <td colspan="3"><textarea name="typeStag" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>疗效指标</th>
          <td colspan="3"><textarea name="effectTarget" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>西医诊断标准</th>
          <td colspan="3"><textarea id="mmStandard_edit" name="mmStandard"
              style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>中医诊断标准</th>
          <td colspan="3"><textarea name="tcmStandard" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>疗效判定标准</th>
          <td colspan="3"><textarea name="effectStandard" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>备注</th>
          <td colspan="3"><textarea name="remark" style="width: 820px; height: 300px;"></textarea></td>
        </tr>
        <tr>
          <th>原文</th>
          <td colspan="3">
            <div id="edit_attach_show_clear">
              <!--               <div> -->
              <!--                 <span id="1">aaaaaaaaaaaaaaaaaaaaaaaaaaaa.txt</span><a -->
              <!--                   style="margin-left: 5px; color: red;" href="javascript:deleteAttach(1);">X</a> -->
              <!--               </div> -->
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="4">
            <div>
              <div id="fileQueueEdit"></div>
              <input type="file" name="uploadifyEdit" id="uploadifyEdit">
            </div>
          </td>
        </tr>
      </table>
      <div id="edit_hidden_input_clear" style="display: none;"></div>
    </form>
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
          <td><input type="text" name="content" class="easyui-textbox  span2"
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
          <td><input type="text" name="content" class="easyui-textbox  span2"
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
          <td><input type="text" name="content" class="easyui-textbox  span2"
            data-options="multiline:true,validType:['length[0,500]']"
            style="height: 100px; width: 250px;" /></td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>