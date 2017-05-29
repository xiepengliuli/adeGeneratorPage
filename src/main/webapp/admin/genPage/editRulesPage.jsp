<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  var dataObject;
  $(function() {
    parent.$.messager.progress('close');
    fillFormData();
  });

  function fillFormData() {
    $.getJSON("${pageContext.request.contextPath}/admin/genPage/getRuleById", {
      id : '${elementId}'
    }, function(data) {
      dataObject = data;
      $("#hidden_form").form("load", data);
      //       $("#hidden_form :input[name='genInput.id']").val(data.genInput.id);//设置的话会影响select事件（改变页面效果）
      $("#hidden_form :input[name='genCheck.id']").val(data.genCheck.id);
      initFormTable();
      initEasyUI();
    });
  };

  function initFormTable() {

    var tempTable = $("#div_table table").clone();
    tempTable.find(".t_ruleSource").remove();
    $("#form_editRulesPage").append(tempTable);

  }

  function initEasyUI() {
    //控件类型
    $("#form_editRulesPage input[name='genInput.id']").combobox({
      url : '${pageContext.request.contextPath}/admin/genPage/getInputType',
      valueField : 'id',
      textField : 'text',
      editable : false,
      onLoadSuccess : function() {
        $(this).combobox('select', dataObject.genInput.id);
      },
      onSelect : function(record) {
        var div_dataSource = $("#div_table .t_ruleSource").clone(true);
        var tr_inputType = $(this).parents("tr");
        if (record.id == 2 || record.id == 6) {//下拉框或下拉树
          if (tr_inputType.children().size() > 2) {
//             tr_inputType.children().slice(2).remove();
            tr_inputType.parent().find("tr:not(.d_select)").hide();
          } else {
            tr_inputType.append(div_dataSource);
            tr_inputType.parent().find("tr:not(.d_select)").hide();
          }
        } else if (record.id == 3) {//如果是多行文本
          tr_inputType.children().slice(2).remove();
          tr_inputType.parent().find("tr:not(.d_textarea)").hide();
          tr_inputType.parent().find(".d_textarea").show();
        } else if (record.id == 4) {//如果是大文本(kindEdit)
          tr_inputType.children().slice(2).remove();
          tr_inputType.children().slice(2).remove();
          tr_inputType.parent().find("tr:not(.d_kindEdit)").hide();
          tr_inputType.parent().find(".d_kindEdit").show();
        } else if (record.id == 5) {//如果是上传组件(uploadify)？？？？？？？？？？？
          tr_inputType.children().slice(2).remove();
          tr_inputType.parent().find("tr:not(.d_select)").show();
        }  else {//不是下拉框或下拉树
          tr_inputType.children().slice(2).remove();
          tr_inputType.parent().find("tr:not(.d_select)").show();
        }
      }
    });
    //校验类型
    $("#form_editRulesPage input[name='genCheck.id']").combobox({
      url : '${pageContext.request.contextPath}/admin/genPage/getSelectBoxGenChecks',
      valueField : 'id',
      textField : 'text',
      editable : false
    });

    $("#form_editRulesPage input[name='ruleIsmust']").combobox({
      valueField : 'label',
      textField : 'value',
      editable : false,
      data : [ {
        label : '0',
        value : '否'
      }, {
        label : '1',
        value : '是'
      } ]
    });

  }

  function save() {
    $('#form_editRulesPage').form('submit', {
      url : '${pageContext.request.contextPath}/admin/genPage/saveRule',
      onSubmit : function() {
        return $(this).form("validate");
      },
      success : function(result) {
        result = $.parseJSON(result);
        if (result.success) {
          div_rulesEdit_dialog.dialog("close");
        } else {
          alert(result.msg);
        }
      }
    });
  }

  function genHtml() {
    $('#form_editRulesPage').form('submit', {
      url : '${pageContext.request.contextPath}/admin/genPage/generatorHtmlByruleId',
      onSubmit : function() {
        return $(this).form("validate");
      },
      success : function(result) {
        alert(result);
      }
    });
  }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <form id="form_editRulesPage" method="post">
      <input type="hidden" name="genElement.id" value="${elementId}">
    </form>
    <a id="save" onclick="save();" style="display: none;">保存</a> <a id="genHtml" onclick="genHtml();" style="display: none;"></a>
  </div>
  <div id="div_table" style="display: none;">
    <form id="hidden_form">
      <table class="table table-hover table-condensed">
        <input name="id" type="hidden">
        <tr class="t_genElementGenInputId d_select d_textarea d_kindEdit">
          <th>控件类型</th>
          <td><input style="width: 200px;" type="text" name="genInput.id" class=" span2" /></td>
          <th class="t_ruleSource">数据来源</th>
          <td class="t_ruleSource"><input style="width: 400px;" type="text" name="ruleSource" class="easyui-validatebox span2"
            data-options="required:true"
          /></td>
        </tr>
        <tr class="t_ruleIsmust d_select d_textarea d_kindEdit">
          <th>是否必填</th>
          <td><input name="ruleIsmust" value="aa" style="width: 200px;"></td>

        </tr>
        <tr class="t_ruleLength d_textarea d_kindEdit">
          <th>最大长度限制</th>
          <td><input style="width: 200px;" type="text" name="ruleLength" class=" span2" /></td>
        </tr>
        <tr class="t_jylxgenChecksId">
          <th>校验类型</th>
          <td><input style="width: 200px;" type="text" name="genCheck.id" class=" span2" /></td>
        </tr>
      </table>
    </form>

  </div>
</div>