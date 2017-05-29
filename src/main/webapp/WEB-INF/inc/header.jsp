<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style type="text/css">
#Shadow {
		position: absolute;
		top: 0px;
		filter: alpha(opacity = 50);
		opacity: 0.5;
		background-color: #666666;
		left: 0px;
		width: 100%;
		height: 100%;
		display: none;
}
</style>

<script type="text/javascript" charset="utf-8">

  function SetVisible(obj, type) {
    $("#standardSearch").css("display", "none");
    $("#diseasesSearch").css("display", "none");
    $("#" + type).css("display", "block");
    $('li[name="searchType"]').removeClass("select_aa");
    $(obj).addClass("select_aa");

  }

  $(function() {
    //点击修改密码,弹出提示
    $("#change").click(function() {
      $("#errorTips").text("");//清空提示
      $("#changePasswordForm ul :input").val("");

      $("#changePassword").css("display", "block");
      $("#Shadow").css("display", "block");
    });
    //点击确定,修改密码
    $("#confirm").click(function() {

      var oldPwd = $("#old").val();
      var newPwd = $("#new").val();
      var checkPwd = $("#check").val();
      var loginName = "${sessionScope.LOGIN_USER_PORTAL.loginName}";
      if (oldPwd == "") {
        $("#errorTips").html("原密码为空!");
        return;
      }

      if (newPwd == "") {

        $("#errorTips").html("新密码为空!");
        return;
      }

      if (checkPwd == "" || checkPwd != newPwd) {
        $("#errorTips").html("两次输入的密码不一致!");
        return;
      }
      //发送请求修改密码
      $.ajax({
        url : "${pageContext.request.contextPath}/web/changePassword",
        type : "post",
        dataType : "json",
        data : {
          "loginName" : loginName,
          "oldPwd" : oldPwd,
          "newPwd" : newPwd
        },
        success : function(result) {
          if (!result.success) {
            $("#errorTips").html(result.msg);
          } else {
            alert(result.msg);
            $("#changePassword").css("display", "none");
            $("#Shadow").css("display", "none");
          }
        },
        error : function() {
          alert("系统异常!");
          $("#Shadow").css("display", "none");
        }
      });

    });
  });

  function delWinD() {
    document.getElementById("Shadow").style.display = "none";
    document.getElementById("changePassword").style.display = "none";
  }

  function logout() {
    //     ${pageContext.request.contextPath}/web/logout
    if (confirm("确定要退出系统吗？")) {
      $.getJSON("${pageContext.request.contextPath}/web/logout", function(data) {
        if (data.success) {
          window.location.href = "${pageContext.request.contextPath}/web/toLogin";
        } else {

        }
      });

    }
  }
</script>

<script type="text/javascript">
  var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
  document.write(unescape("%3Cscript src='" + gaJsHost
      + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
  var pageTracker = _gat._getTracker("UA-3448069-1");
  pageTracker._initData();
  pageTracker._trackPageview();
</script>
<!--header-->
<div id="Shadow" style="z-index: 101; display: none;"></div>
<div id="header" class="header">
  <div id="container" class="container">
    <div id="banner" class="banner">
      <div id="head_title" class="head_title">疾病诊断与疗效评价标准平台</div>
      <div id="head_nav" class="head_nav">
        <ul>
          <li class="no1">欢迎您：${sessionScope.user.loginName}!</li>
          <c:if test="${empty requestType}">
            <li><a href="javascript:void(0)" id="change">修改密码</a></li>
            <li><a href="javascript:logout();">注销</a></li>
          </c:if>
        </ul>
      </div>
    </div>
  </div>
</div>
<form id="changePasswordForm">
  <div id="changePassword" class="mod_password"
    style="display: none; z-index: 112; position: absolute; background: #ffffff; left: 35%; top: 20%;">
    <div id="mod_password_header" class="mod_password_header">
      <p>修改密码</p>
      <a href="#" onclick='delWinD();return false;'><img
        src="${pageContext.request.contextPath}/resources/img/cz.jpg" /></a>
    </div>
    
    <div id="password_text" class="password_text">
      <ul>
        <li style="float:left;display: inline;">当前密码:&nbsp;<input type="password" id="old"  /></li>
        <li style="float:left; display: inline;">新密码:&nbsp;&nbsp;&nbsp;&nbsp;<input type="password" id="new" /></li>
        <li style="float:left;display: inline;">确认密码:&nbsp;<input type="password" id="check" /></li>
        <li style="float:left;padding-left:50px;"><label id="errorTips" style="color: red;"></label></li>
      </ul>
      <input type="button" class="" name="save" value="保存" id="confirm"> <input
        type="button" class="b1" name="close" value="取消" onclick='delWinD();return false;'>

    </div>
    
  </div>
</form>

