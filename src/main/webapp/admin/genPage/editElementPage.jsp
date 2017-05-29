<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
    initData();

  });

  function initData() {
    initEasyUIElement();
  }
  function initEasyUIElement() {
    $('#tt').tree({
      url : '${pageContext.request.contextPath}/admin/genPage/getChildrens?id=${id}',
      onSelect : function(node) {
        $("#form_table2 tbody").empty();
        $.getJSON("${pageContext.request.contextPath}/admin/genPage/getGenElementsById", {
          id : node.id
        }, function(data) {
          if (data.success) {
            $.each(data.obj, function(i) {
              //               alert(this.elementName);
              if (i == 0) {
                if (this.genPage) {
                  fillPageData(this);
                }
              }
              addRowFrom(this.id, this.elementName, this.elementNameRemark, this.elementSize, this.isGenerator,this.isVisible);
            });
          } else {
            alert(data.msg);
          }
        });
      },
      onLoadSuccess : function(node, data) {
        $.each(data, function(i, s_this) {
          if (i == 0) {
            var node = $('#tt').tree('find', s_this.id);
            $('#tt').tree('select', node.target);
          }
        })
      }

    });

    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/genPage/saveElement',
      success : function(result) {
        result = $.parseJSON(result);
        if (result.success) {
          parent.$.modalDialog.openner_treeGrid.datagrid('reload'); //之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为role.jsp页面预定义好了
          parent.$.modalDialog.handler.dialog('close');
        } else {
          alert(result.msg);
        }
      }
    });
  }

  function addRowFrom(id, elementName, elementNameRemark, elementSize, checked,isVisible) {

    var template = $("#test tr").clone();

    template.find("input").each(function(i) {
      if (i == 0) {
        $(this).val(elementName);
      } else if (i == 1) {
        $(this).val(elementNameRemark);
      } else if (i == 2) {
        $(this).val(elementSize);
      } else if (i == 3) {//是否生成
        if (checked == 1) {
          $(this).attr("checked","checked");
        }
      } else if (i == 4) {//是否显示
//           $(this).val(isVisible);
      }

      else if (i == 5) {
        $(this).val(id);
      }
    });
    $("#form_table2 tbody").append(template);
    flushRow();
    template.find("#isVisible").combobox({
      valueField : 'id',
      textField : 'text',
      onLoadSuccess:function(){
        $(this).combobox("select",isVisible);
      },
      data : [ {
        "id" : 1,
        "text" : "是"
      }, {
        "id" : 0,
        "text" : "否"
      } ]
    });

  }

  //填充form的第一个table,即page
  function fillPageData(pageData) {
    $("#form_table1 input[name='pageTitle']").val(pageData.genPage.pageTitle);
    $("#form_table1 input[name='pageWidth']").val(pageData.genPage.pageWidth);
    $("#form_table1 input[name='pageHeight']").val(pageData.genPage.pageHeight);
    $("#form_table1 input[name='pageUrl']").val(pageData.genPage.pageUrl);
    $("#form_table1 input[name='id']").val(pageData.genPage.id);
  }
  //function addRow(temp) {
  //var template = $("#test  tr").clone();
  //$(temp).parents("tr").after(template);
  //flushRow();
  //}
  //function delRow(temp) {
  //$(temp).parents("tr").remove();
  //flushRow();
  //}

  function moveUp(temp) {
    $(temp).parents("tr").prev().before($(temp).parents("tr"));
    flushRow();
  }

  function moveDown(temp) {
    $(temp).parents("tr").next().after($(temp).parents("tr"));
    flushRow();
  }

  var div_rulesEdit_dialog;

  function editRulesPage(temp_this) {
    var elementId = $(temp_this).siblings("#id1").val();
    //     div_rulesEdit_dialog = $('#div_rulesEdit').dialog({
    div_rulesEdit_dialog = $('<div/>').dialog({
      title : '编辑规则',
      width : 1000,
      height : 400,
      closed : false,
      cache : false,
      href : '${pageContext.request.contextPath}/admin/genPage/editRulesPage?elementId=' + elementId,
      modal : true,
      onClose : function() {
        div_rulesEdit_dialog = undefined;
        $(this).dialog('destroy');
      },
      buttons : [ {
        text : '保存',
        handler : function() {
          div_rulesEdit_dialog.find("#save").click();
        }
      }, {
        text : '生成html',
        handler : function() {
          div_rulesEdit_dialog.find("#genHtml").click();
        }
      }, {
        text : '关闭',
        handler : function() {
          div_rulesEdit_dialog.dialog("close");
        }
      } ]
    });
  }

  function copy_to() {
    div_rulesEdit_dialog = $('<div/>').dialog({
      title : '编辑规则',
      width : 1000,
      height : 400,
      closed : false,
      cache : false,
      href : '${pageContext.request.contextPath}/admin/genPage/editRulesPage?elementId=',
      modal : true,
      onClose : function() {
        div_rulesEdit_dialog = undefined;
        $(this).dialog('destroy');
      },
      buttons : [ {
        text : '保存',
        handler : function() {

        }
      }, {
        text : '关闭',
        handler : function() {
          div_rulesEdit_dialog.dialog("close");
        }
      } ]
    });
  }
  function generatorHtmlForOnePage() {

    var getSelected = $('#tt').tree("getSelected");

    div_rulesEdit_dialog = $('<div/>').dialog({
      title : '页面代码',
      width : 1500,
      height : 600,
      closed : false,
      cache : false,
      href : '${pageContext.request.contextPath}/admin/genPage/generatorHtmlForOnePage?pageId=' + getSelected.id,
      modal : true,
      onClose : function() {
        div_rulesEdit_dialog = undefined;
        $(this).dialog('destroy');
      },
      buttons : [ {
        text : '关闭',
        handler : function() {
          div_rulesEdit_dialog.dialog("close");
        }
      } ]
    });

  }

  function flushRow() {
    $("#form_table2 tr").each(function(i) {
      var curIndex = i;
      $(this).find("th").each(function(k, kthis) {
        if (k == 0) {
          $(kthis).html("名称(英)" + (++i));
          $(kthis).next().find("input").prop("name", "genElements[" + curIndex + "].elementName");
        } else if (k == 1) {
          $(kthis).html("名称(中)" + i);
          $(kthis).next().find("input").prop("name", "genElements[" + curIndex + "].elementNameRemark");
        } else if (k == 2) {
          $(kthis).html("宽度" + i);
          $(kthis).next().find("input").prop("name", "genElements[" + curIndex + "].elementSize");
        } else if (k == 3) {
          $(kthis).html("是否生成" + i);
          $(kthis).next().find("#isGenerator").prop("name", "genElements[" + curIndex + "].isGenerator");
        } else if (k == 4) {
          $(kthis).html("是否显示" + i);
          $(kthis).next().find("#isVisible").prop("name", "genElements[" + curIndex + "].isVisible");
          //设置id
          $(kthis).next().next().find("#id1").prop("name", "genElements[" + curIndex + "].id");
        }

      });
    });
  }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'west',title:'子页面',split:true" style="width: 200px;">
    <ul id="tt"></ul>
  </div>
  <div data-options="region:'center',border:false" title="" style="overflow: auto;">
    <form id="form" method="post">
      <table id="form_table1" class="table table-hover table-condensed">
        <input type="hidden" name="id">
        <tr>
          <th>页面标题</th>
          <td><input name="pageTitle" style="width: 300px;" type="text" name="pageTitle" class=" span2" /></td>
          <th>页面宽</th>
          <td><input name="pageWidth" style="width: 300px;" type="text" name="pageWidth" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面高</th>
          <td><input name="pageHeight" style="width: 300px;" type="text" name="pageHeight" class=" span2" /></td>
          <th>页面访问路径</th>
          <td><input name="pageUrl" style="width: 300px;" type="text" name="pageUrl" class=" span2" /></td>
        </tr>
        <tr>
          <th>页面按钮</th>
          <td><input style="width: 300px;" type="text" name="pageButtonTypeid" class=" span2" /></td>
        </tr>
      </table>
      <br>
      <table id="form_table2" class="table table-hover table-condensed">
        <thead></thead>
        <tbody>
        </tbody>
      </table>
    </form>
  </div>
  <div id="test" style="display: none;">
    <table class="table table-hover table-condensed">
      <tr>
        <%--         <td><img onclick="addRow(this);" src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/add.png"> --%>
        <%--           <img onclick="delRow(this);" src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/cross.png"></td> --%>
        <th>名称(英)</th>
        <td><input style="width: 150px;" type="text" name="element_enName" class=" span2" /></td>
        <th>名称(中)</th>
        <td><input style="width: 100px;" type="text" name="element_cnName" class=" span2" /></td>
        <th>宽度</th>
        <td><input style="width: 50px;" type="text" name="element_width" class=" span2" /></td>
        <th>是否生成</th>
        <td><input type="checkbox"  id="isGenerator" name="isGenerator" style="width: 20px;" value="1" class=" span2" /></td>
        <th>是否显示</th>
        <td><input id="isVisible" name="isVisible" style="width: 60px;" value="" class=" span2" /></td>
        <td><a class="easyui-linkbutton" data-options="iconCls:'bug_bug_edit'" onclick="editRulesPage(this);">配置规则</a> <input
          type="hidden" name="id" id="id1"
        ></td>
        <td><img onclick="moveUp(this);" src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/arrow_up.png">
          <img onclick="moveDown(this);" src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/arrow_down.png"></td>
      </tr>
    </table>
  </div>
  <!--   <div id="div_rulesEdit"></div> -->
  <div id="div_hide" style="display: none;">
    <a id="copy_to" onclick="copy_to();"></a> <a id="generatorHtmlForOnePage" onclick="generatorHtmlForOnePage();"></a>
  </div>
</div>