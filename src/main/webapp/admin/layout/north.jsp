<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="logo" style="width: 124px; height: 60px; margin-left: 10px;"></div>
<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="alert alert-info">
  <c:if test="${LOGIN_USER.id != null}">[<strong>${LOGIN_USER.loginName}</strong>]，欢迎你！</c:if>
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
  <a target="_blank" href="${pageContext.request.contextPath}/" class="easyui-linkbutton" data-options="plain:true,iconCls:'cog'">前台链接</a> <a
    href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> <a
    href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> <a
    href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
</div>
<div id="layout_north_pfMenu" style="width: 120px; display: none;">
  <div onclick="changeThemeFun('default');" title="default">默认皮肤（天空蓝）</div>
  <div onclick="changeThemeFun('bootstrap');" title="bootstrap">银色</div>
  <div onclick="changeThemeFun('gray');" title="gray">灰霾</div>
  <div onclick="changeThemeFun('black');" title="black">金属黑</div>
  <div onclick="changeThemeFun('metro');" title="metro">浅灰</div>
  <!-- 
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
	<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
	<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
	<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
	 -->
</div>
<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
  <div onclick="editCurrentUserPwd();">修改密码</div>
  <div class="menu-sep"></div>
  <div onclick="currentUserRole();">我的角色</div>
  <div class="menu-sep"></div>
  <div onclick="currentUserResource();">我的权限</div>
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
  <div onclick="logoutFun();">退出系统</div>
</div>