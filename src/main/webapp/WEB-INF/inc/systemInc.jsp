<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" charset="utf-8">
  var codeBase = "${pageContext.request.contextPath}";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript" src="${ctx}/resources/plugins/extBrowser.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<script src="${ctx}/resources/plugins/jquery.min.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入kindEditor插件 -->
<link rel="stylesheet" href="${ctx}/resources/plugins/kindeditor-4.1.11/themes/default/default.css">
<script type="text/javascript"
  src="${ctx}/resources/plugins/kindeditor-4.1.11/kindeditor-all-min.js" charset="utf-8"></script>


<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet"
  href="${ctx}/resources/plugins/jquery-easyui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css"
  type="text/css">

<script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/jquery.easyui.min.js"
  charset="utf-8"></script>

<!-- 引入插件 -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.accordion.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.calendar.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.combo.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.combobox.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.combogrid.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.combotree.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.datagrid.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.datalist.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.datebox.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.datetimebox.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.datetimespinner.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.dialog.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.draggable.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.droppable.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.filebox.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.form.js" --%>
<!--   charset="utf-8"></script> -->
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.layout.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.linkbutton.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.menu.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.menubutton.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.messager.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.numberbox.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.numberspinner.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.pagination.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.panel.js" --%>
<!--   charset="utf-8"></script> -->
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.parser.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.progressbar.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.propertygrid.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.resizable.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.searchbox.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.slider.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.spinner.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.splitbutton.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.switchbutton.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.tabs.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.textbox.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.timespinner.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.tooltip.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.tree.js" --%>
<!--   charset="utf-8"></script> -->
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.treegrid.js" charset="utf-8"></script> --%>
<!-- <script type="text/javascript" -->
<%--   src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.validatebox.js" charset="utf-8"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.window.js" --%>
<!--   charset="utf-8"></script> -->
<%-- <script type="text/javascript" src="${ctx}/resources/plugins/jquery-easyui/plugins/jquery.radio.js" --%>
<!--   charset="utf-8"></script> -->


<!-- 扩展EasyUI -->
<script type="text/javascript" src="${ctx}/resources/plugins/extEasyUI.js?v=201305241044"
  charset="utf-8"></script>
<!-- 扩展EasyUI Icon -->
<link rel="stylesheet" href="${ctx}/resources/css/extEasyUIIcon.css?v=201305301906" type="text/css">
<link rel="stylesheet" href="${ctx}/resources/plugins/jquery-easyui/themes/icon.css" type="text/css">
<!-- 扩展EasyUI界面 -->
<link rel="stylesheet" href="${ctx}/resources/css/extEasyUI.css?v=201305301906" type="text/css">
<!-- 扩展jQuery -->
<script type="text/javascript" src="${ctx}/resources/plugins/extJquery.js?v=201305301341"
  charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/resources/plugins/extMathed.js"
  charset="utf-8"></script>

<script type="text/javascript" charset="utf-8" src="${ctx}/resources/js/ade.js"></script>


<script type="text/javascript"
  src="${ctx}/resources/plugins/jquery-easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css" href="${ctx}/resources/plugins/uploadify/uploadify.css">
<script type="text/javascript" charset="utf-8"
  src="${ctx}/resources/plugins/uploadify/jquery.uploadify.min.js"></script>
  
  
<!--   ztree3.5 -->
<!-- <script type="text/javascript" charset="utf-8" -->
<%--   src="${ctx}/resources/plugins/zTree3.5/js/jquery.ztree.all-3.5.min.js"></script> --%>
<%--   <link rel="stylesheet" type="text/css" href="${ctx}/resources/plugins/zTree3.5/css/zTreeStyle/zTreeStyle.css"> --%>

