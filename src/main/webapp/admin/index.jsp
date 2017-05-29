<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>INFCN ADE</title>
<jsp:include page="/WEB-INF/inc/systemInc.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8"
  src="${pageContext.request.contextPath}/resources/js/admin/index.js"></script>

<script type="text/javascript">
  var codeBase = "${pageContext.request.contextPath}";
<%//当前页面的用户信息%>
  var sessionInfo_userId = '${LOGIN_USER.id}';

  if (sessionInfo_userId == '') {
    window.location.href = '${pageContext.request.contextPath}/admin/login.jsp';
  }
</script>

<script type="text/javascript" charset="utf-8">
  /**
   * @requires jQuery,EasyUI,jQuery cookie plugin
   * 更换EasyUI主题的方法
   * @param themeName
   *            主题名称
   */
  function changeThemeFun(themeName) {
    if ($.cookie('easyuiThemeName')) {
      $('#layout_north_pfMenu').menu('setIcon', {
        target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
        iconCls : 'emptyIcon'
      });
    }
    $('#layout_north_pfMenu').menu('setIcon', {
      target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
      iconCls : 'tick'
    });

    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);

    var $iframe = $('iframe');
    if ($iframe.length > 0) {
      for (var i = 0; i < $iframe.length; i++) {
        var ifr = $iframe[i];
        try {
          $(ifr).contents().find('#easyuiTheme').attr('href', href);
        } catch (e) {
          try {
            ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
          } catch (e) {
          }
        }
      }
    }

    $.cookie('easyuiThemeName', themeName, {
      expires : 7
    });

  };

  function logoutFun(b) {
    $.getJSON('${pageContext.request.contextPath}/admin/user/logout', {
      t : new Date()
    }, function(result) {
      window.location.href = '${pageContext.request.contextPath}/admin/index.jsp';
    });
  }

  function editCurrentUserPwd() {
    parent.$.modalDialog({
      title : '修改密码',
      width : 300,
      height : 250,
      href : '${pageContext.request.contextPath}/admin/user/editCurrentUserPwdPage',
      buttons : [ {
        text : '修改',
        handler : function() {
          var f = parent.$.modalDialog.handler.find('#editCurrentUserPwdForm');
          f.submit();
        }
      } ]
    });
  }
  function currentUserRole() {
    parent.$.modalDialog({
      title : '我的角色',
      width : 300,
      height : 250,
      href : '${pageContext.request.contextPath}/admin/user/currentUserRolePage'
    });
  }
  function currentUserResource() {
    parent.$.modalDialog({
      title : '我可以访问的资源',
      width : 300,
      height : 250,
      href : '${pageContext.request.contextPath}/admin/user/currentUserResourcePage'
    });
  }
</script>

</head>
<body>
  <div id="index_layout">
    <div
      data-options="region:'north',href:'${pageContext.request.contextPath}/admin/layout/north.jsp'"
      style="height: 70px; overflow: hidden;"></div>
    <div data-options="region:'west',split:true,iconCls:'application_application_view_detail'"
      title="菜单导航栏" style="width: 200px; overflow-x: hidden; overflow-y: scroll;">
      <div id="adeMenuBar"></div>
    </div>
    <div data-options="region:'center'" title="欢迎使用" style="overflow: hidden;">
      <div id="index_tabs" style="overflow: hidden;">
        <div title="首页" data-options="border:false" style="overflow: hidden;">
          <iframe style="border: 0; width: 100%; height: 98%;"></iframe>
        </div>
      </div>
    </div>
    <div
      data-options="region:'south',href:'${pageContext.request.contextPath}/admin/layout/south.jsp',border:false"
      style="height: 30px; overflow: hidden;"></div>
  </div>

  <div id="index_tabsMenu" style="width: 120px; display: none;">
    <div title="refresh" data-options="iconCls:'transmit'">刷新</div>
    <div class="menu-sep"></div>
    <div title="close" data-options="iconCls:'delete'">关闭</div>
    <div title="closeOther" data-options="iconCls:'delete'">关闭其他</div>
    <div title="closeAll" data-options="iconCls:'delete'">关闭所有</div>
  </div>

</body>
</html>