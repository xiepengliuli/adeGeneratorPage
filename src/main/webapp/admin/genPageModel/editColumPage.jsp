<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');

    $.getJSON("${pageContext.request.contextPath}/admin/genPageModel/getColumsByPageModelId", {
      id : '${id}'
    }, function(data) {
      if (data.success) {
        $.each(data.obj.genPageModelColums, function(i) {
          addRowFrom(this.columName, this.columRemark);
        });
        if (data.obj.genPageModelColums.length <= 0) {
          var template = $("#template tr").clone();
          $("#form tbody").append(template);
          changeNum();
        }

      } else {
        alert(data.msg);
      }
    });

    $('#form').form({
      url : '${pageContext.request.contextPath}/admin/genPageModel/savePageModelColum',
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
  });

  function addRowFrom(columName, columRemark) {
    var template = $("#template tr").clone();
    template.find("input").eq(0).val(columName);
    template.find("input").eq(1).val(columRemark);
    $("#form tbody").append(template);
    changeNum();
  }

  function addRow(temp) {
    var template = $(temp).parents("tr").clone();
    template.find(":input").val("");
    $(temp).parents("tr").after(template);
    changeNum();
    changeBackGroud(template);
  }

  function delRow(temp) {
    changeBackGroud($(temp).parents("tr").prev());
    $(temp).parents("tr").remove();
    changeNum();
  }

  function moveUp(temp) {
    $(temp).parents("tr").prev().before($(temp).parents("tr"));
    changeNum();
    changeBackGroud($(temp).parents("tr"));
  }
  function moveDown(temp) {
    $(temp).parents("tr").next().after($(temp).parents("tr"));
    changeNum();
    changeBackGroud($(temp).parents("tr"));
  }

  function changeBackGroud(temp) {
    temp.siblings().css({
      background : "white"
    });
    temp.css({
      background : "#E6E6E6"
    });
  }

  function changeNum() {
    $("#form tbody tr").each(function(i) {
      $(this).find("th").each(function(k, kthis) {
        if (k == 0) {
          $(kthis).html("属性名" + (i + 1));
          $(kthis).next().find("input").prop("name", "genPageModelColums[" + i + "].columName");
        } else if (k == 1) {
          $(kthis).html("属性注释" + (i + 1));
          $(kthis).next().find("input").prop("name", "genPageModelColums[" + i + "].columRemark");
        }
      });
    });
  }
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
  <div data-options="region:'center',border:false" title="" style="overflow: auto;">
    <form id="form" method="post">
      <input type="hidden" name="id" value="${id}">
      <table class="table table-hover table-condensed">
        <thead>
        </thead>
        <tbody>
        </tbody>
      </table>
    </form>
  </div>
  <div id="template" style="display: none;">
    <table>
      <tr id="test">
<!--         <td>&nbsp;&nbsp;<img onclick="addRow(this);" alt="添加" -->
<%--           src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/add.png"> --%>
<!--           &nbsp;&nbsp;<img onclick="delRow(this);" alt="删除" -->
<%--           src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/cross.png">&nbsp;&nbsp; --%>
<!--         </td> -->
        <th>属性名</th>
        <td><input style="width: 200px;" type="text" name="pageModelName" class="  span2" /></td>
        <th>属性注释</th>
        <td><input style="width: 200px;" type="text" name="pageModelName" class=" span2" /></td>
        <td>&nbsp;&nbsp;<img onclick="moveUp(this);" alt="上移"
          src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/arrow_up.png">
          &nbsp;&nbsp;<img onclick="moveDown(this);" alt="下移"
          src="${pageContext.request.contextPath}/resources/css/images/extjs_icons/arrow/arrow_down.png">
          &nbsp;&nbsp;
        </td>
      </tr>
    </table>
  </div>
</div>