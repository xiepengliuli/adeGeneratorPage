<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
    $('#tt').tree({
      url : '${pageContext.request.contextPath}/admin/module/getSyncTree',

      onLoadSuccess : function(node, data) {
      },
      formatter : function(node) {
        return node.text+"("+node.attributes.ResourcesTotal+")";
      }

    });
  });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
  <div data-options="region:'center',border:false" title="" style="overflow: auto;">
    <ul id="tt"></ul>
  </div>
</div>