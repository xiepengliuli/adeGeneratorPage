<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
  var editor_mmStandard_add;
  var editor_tcmStandard_add;
  var editor_effectStandard_add;
  var editor_tcmDiseaseDialectical_add; //中医辨证
  var editor_typeStag_add; //分型分期
  var editor_effectTarget_add;//疗效指标
  var editor_remark_add;//备注
  var dataGrid;

  $(function() {

    dataGrid = parent.$.modalDialog.openner_dataGrid;

    window.setTimeout(function() {

      initAddPageAttachs();
      editor_remark_add = initEdit("remark");
      editor_effectStandard_add = initEdit("effectStandard");
      editor_tcmDiseaseDialectical_add = initEdit("tcmDiseaseDialectical");
      editor_tcmStandard_add = initEdit("tcmStandard");
      editor_mmStandard_add = initEdit("mmStandard");
      editor_effectTarget_add = initEdit("effectTarget");
      editor_typeStag_add = initEdit("typeStag");
      parent.$.messager.progress('close');
    }, 1);

  });

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
    if (editor_tcmStandard_add.count("text") >= 100000) {
      alert("中医诊断标准的长度不能超过100000。");
      return;
    }
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
          parent.$.modalDialog.handler.dialog('close');
          dataGrid.datagrid('reload');
        } else {
          parent.$.messager.alert('错误', result.msg, 'error');
        }
      }
    });
  }

  function initEdit(editName) {
    var edit = KindEditor.create('#addDiv_form textarea[name="' + editName + '"]', {
      width : 820,
      height : 300,
      uploadJson : '${pageContext.request.contextPath}/admin/pic/pictureUpload',
      fileManagerJson : 'file_manager'
    });
    return edit;
  }

  function initAddPageAttachs() {
    //上传多个文件，数据资源用
    //         $("#fileQueue").empty();//清空上传队列DIV
    $('#uploadify')
        .uploadify(
            {
              'fileTypeDesc' : 'pdf',
              'fileSizeLimit' : '10M',
              'fileTypeExts' : '*.pdf',
              'swf' : '${pageContext.request.contextPath}/resources/plugins/uploadify/uploadify.swf?ver='
                  + Math.random(),//上传按钮的图片，默认是这个flash文件
              'uploader' : '${pageContext.request.contextPath}/admin/standard/uploadFiles?ver='
                  + Math.random(), //上传所处理的服务器
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: auto;">
    <div id="addDiv">
      <form id="addDiv_form" method="post">
        <input type="hidden" name="tdiseaseId" value="${tdiseaseId}">
        <table class="table table-hover table-condensed">
          <tr>
            <th>序号</th>
            <td><input style="width: 350px;" name="orderNum" class="easyui-validatebox"
              data-options="required:true,validType:['length[1,50]']" /></td>
            <!-- 0中医标准,1西医标准 -->
            <th>标准类型</th>
            <td><select class="easyui-combobox" name="type" style="width: 132px;">
                <option value="0">中医标准</option>
                <option value="1">西医标准</option>
            </select></td>
          </tr>
          <tr>
            <th>中文题名</th>
            <td><input name="cnTitle" class="easyui-validatebox" data-options="required:true,validType:['length[1,500]']"
              style="width: 350px;" /></td>
            <th>英文题名</th>
            <td><input name="enTitle" class="easyui-validatebox" style="width: 350px;" data-options="validType:['length[1,500]']" /></td>
          </tr>
          <tr>
            <th>发布机构</th>
            <td><input name="publishOrg" class="easyui-validatebox"
              data-options="required:true,validType:['length[1,500]']
          " style="width: 350px;" /></td>
            <th>发布日期</th>
            <td><input name="publishDate" type="text" style="height: 24px; width: 140px;" class="easyui-numberbox" data-options=""
              style="width:400px;"></td>
          </tr>
          <tr>
            <th>来源</th>
            <td><input name="source" class="easyui-validatebox" data-options="validType:['length[0,500]']
          "
              style="width: 350px;" /></td>
            <th>全文语种</th>
            <td><select class="easyui-combobox" name="language" style="width: 132px;">
                <option value="中文">中文</option>
                <option value="英文">英文</option>
                <option value="其他语种">其他语种</option>
            </select></td>
          </tr>
          <tr>
            <th>西医病名</th>
            <td><input style="width: 350px;" name="mmDiseaseName" class="easyui-validatebox"
              data-options="validType:['length[1,500]']
          " /></td>
            <th>中医病名</th>
            <td><input style="width: 350px;" name="tcmDiseaseName" class="easyui-validatebox"
              data-options="validType:['length[1,500]']
          " /></td>
          </tr>
          <tr>
            <th>中医辨证</th>
            <td colspan="3"><textarea name="tcmDiseaseDialectical"></textarea></td>
          </tr>
          <tr>
            <th>分型分期</th>
            <td colspan="3"><textarea name="typeStag"></textarea></td>
          </tr>
          <tr>
            <th>疗效指标</th>
            <td colspan="3"><textarea name="effectTarget" style="width: auto; height: 300px;"></textarea></td>
          </tr>
          <tr>
            <th>西医诊断标准</th>
            <td colspan="3"><textarea name="mmStandard"></textarea></td>
          </tr>
          <tr>
            <th>中医诊断标准</th>
            <td colspan="3"><textarea name="tcmStandard"></textarea></td>
          </tr>
          <tr>
            <th>疗效判定标准</th>
            <td colspan="3"><textarea name="effectStandard"></textarea></td>
          </tr>
          <tr>
            <th>备注</th>
            <td colspan="3"><textarea name="remark"></textarea></td>
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
  </div>
  <div data-options="region:'south'" style="height: 40px;">
    <div style="margin-right: 20px; float: right;">
      <a onclick="addData('')" style="margin-top: 10px;" id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:''">保存</a> <a
        onclick="addData('saveAndSubmit')" style="margin-top: 10px;" id="btn" href="#" class="easyui-linkbutton" data-options="iconCls:''">保存并提交审核</a>
      <a onclick="parent.$.modalDialog.handler.dialog('close');" style="margin-top: 10px;" id="btn" href="#" class="easyui-linkbutton"
        data-options="iconCls:''">关闭</a>
    </div>
  </div>
</div>
