<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  var edit;
  $(function() {
    parent.$.messager.progress('close');
  });

  function setEffect() {
    $('#ff').form({
      url : "${pageContext.request.contextPath}/admin/genPage/generatorEffectToSession",
      success : function(data) {

        $("#effect").prop("src", "${pageContext.request.contextPath}/admin/genPage/generatorEffect");
//         window.open("${pageContext.request.contextPath}/admin/genPage/generatorEffect");
      },
      onSubmit : function(param) {
        var tempString = $("#effectContent").val();
        if (tempString.indexOf("<html>")) {
          tempString = tempString.substring(tempString.indexOf("<html>"));
        }
        param.effectContent = tempString;
      }
    });
    
    $("#ff").submit();
  }
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: hidden;">
    <input type="hidden" name="id">
    <table class="table table-hover table-condensed">
      <tr>
        <td></td>
        <td>
          <a id="btn" href="#" class="easyui-linkbutton" data-options="" onclick="setEffect();">预览</a> 
        </td>
      </tr>
      <tr>
        <th>页面内容</th>
        <form id="ff" method="post">
        <td><textarea id="effectContent" name="effectContent" style="height: 500px; width: 650px;">
          <c:out value="${generatorHtml}"></c:out>
       </textarea></td>
        </form>
        <th>预览效果</th>
        <td><iframe id="effect" name="effect" style="height: 500px; width: 650px;"></iframe></td>
      </tr>
    </table>
  </div>
</div>
