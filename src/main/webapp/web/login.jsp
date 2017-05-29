<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<%@ include file="/WEB-INF/inc/taglib.jsp"%>
<style type="text/css">
.zcUl li p, .dld p {
	position: absolute;
	right: -180px;
	top: 0;
	color: #f00;
	width: 170px;
	padding-left: 10px;
	line-height: 38px;
}
#showMessage{
    width:180px;
    height:100px;
    border:1px solid pink;
}
#showMessage div{
    width:120px;
    margin: 10px auto;
}
</style>
<script type="text/javascript">
  String.prototype.trim = function() {
    return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  }

  $(function() {

    $("#testlogin").click(function() {
      $("#login_name").val("test");
      $("#pwd").val("test");

      //通过检测后发生Ajax请求
      $.ajax({
        url : "${pageContext.request.contextPath}/web/login",
        type : "post",
        data : $("#form_login").serialize(),
        dataType : "json",
        success : function(result) {
	          if (!result.success) {
	        	  $("#login_name").val(loginName);
	        	  $("#pwd_span").html(result.msg);
	          }else if(result.success){
		          window.location.href = "${pageContext.request.contextPath}/web/homePage";
		          $("#login_name").val("");
	          }
	          $("#pwd").val("");
        },
        error : function() {
          alert("登录异常");
        }
      });
    });
 
    $("#login").click(function() {
      var loginName = $("#login_name").val().trim();
      var password = $("#pwd").val().trim();
      
      if (loginName == "") {
        $("#name_span").html("登录名为空");
        return;//用这种写法比用开关方便
      } else {
        $("#name_span").html("");
      }
      if (password == "") {
        $("#pwd_span").html("密码为空");
        return;
      }  else {
        $("#pwd_span").html("");
      }
      //通过检测后发生Ajax请求
      $.ajax({
        url : "${pageContext.request.contextPath}/web/login",
        type : "post",
        data : $("#form_login").serialize(),
        dataType : "json",
        success : function(result) {
	          if (!result.success) {
	        	  $("#login_name").val(loginName);
	        	  $("#pwd_span").html(result.msg);
	          }else if(result.success){
		          window.location.href = "${pageContext.request.contextPath}/web/homePage";
		          $("#login_name").val("");
	          }
	          $("#pwd").val("");
        },
        error : function() {
          alert("登录异常");
        }
      });
    });
    //密码按钮回车，触发登录
    $("#pwd").keydown(function(event) {
      if (event.keyCode == 13) {
        $("#login").click();
      }
    });
    //点击忘记密码,弹出提示
    $("#forget").click(function(){
    	$("#showMessage").css("display","block");
    });
    //点击确定,关闭提示
    $("#confirm").click(function(){
    	$("#showMessage").css("display","none");
    });
  });
</script>

  <!--忘记密码-->
    <script type="text/javascript">
    var isshow=0;//0小窗口没有显示，1小窗口已显
    function creatediv()
    {     
      var msgw,msgh,bordercolor;
      msgw=268;//提示窗口的宽度
      msgh=183;//提示窗口的高度
      var sWidth,sHeight;
      if( top.location == self.location )
        doc = document;
      var docElement = doc.documentElement;
      sWidth=docElement.clientWidth;
      sHeight = docElement.clientHeight;
      if( docElement.scrollHeight > sHeight )
        sHeight = docElement.scrollHeight;
      var bgObj=document.createElement("div");
      bgObj.setAttribute('id','bgDiv');
      bgObj.style.position="absolute";
      bgObj.style.top="0";
      bgObj.style.left="0";
      bgObj.style.background="#777";
      bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
      bgObj.style.opacity="0.6";
      bgObj.style.width=sWidth + "px";
      bgObj.style.height=sHeight + "px";
      bgObj.style.zIndex = "10000";
      document.body.appendChild(bgObj);
        
      var msgObj=document.createElement("div");
      msgObj.setAttribute("id","msgDiv");
      msgObj.setAttribute("align","center");
      msgObj.style.position = "absolute";
        msgObj.style.left = "50%";
        msgObj.style.background="#fff";
        msgObj.style.marginLeft = "-200px" ;
        msgObj.style.top = (document.documentElement.clientHeight/2+document.documentElement.scrollTop-252)+"px";
        msgObj.style.width = msgw + "px";
        msgObj.style.height =msgh + "px";
        msgObj.style.zIndex = "10001";
        msgObj.innerHTML = "<div id='' class='mod_password_xg'>"
                   + "<div id='mod_password_xg_header' class='mod_password_xg_header'>"
                   + "<p>忘记密码</p>"
                   + "<a href='#' onclick='delWinD();return false;'><img src='${pageContext.request.contextPath}/resources/img/cz.jpg'/></a></div>"
                   + "<div id='password_text_xg' class='password_text_xg'><ul>"
                   + "<li>请联系管理员</li>"
                   + "<li class='l1'>电话:13911000000</li>"
                   + "<li class='l1'>邮箱:324324234@qq.com</li></ul>"             
                 + "</div></div>";
//        msgObj.innerHTML = "这是弹出的小窗口！<br /><a href=\"javascript:void(0);\" onclick='delWinD();return false;'>点我关闭窗口</a>";
        document.body.appendChild(msgObj); 
    }
    function delWinD()
    {
       document.getElementById("bgDiv").style.display="none";
       document.getElementById("msgDiv").style.display="none";
       isshow=0;
    }
    function show()
    {   
        isshow=1;
        if(!document.getElementById("msgDiv"))//小窗口不存在
            creatediv();
        else
        {
            document.getElementById("bgDiv").style.display="";
            document.getElementById("msgDiv").style.display="";
            document.getElementById("msgDiv").style.top=(document.documentElement.clientHeight/2+document.documentElement.scrollTop-252)+"px";
        }  
    }   
    

    </script>
    
</head>
<body>
 	<div id="logindiv" class="logindiv" >  
		<div id="login_mi" class="login_mi">
			<div id="login_mi_left" class="login_mi_left">
		
			</div>
			<div id="login_mi_mi" class="login_mi_mi">
               <form id="form_login" action="" method="post">
               
    			<p>病证诊断与疗效评价标准平台</p>
            
               <div id="" class="login_dlk_no">
                <ul>
                  <li>用户名：</li>
                  <li>密&nbsp;&nbsp;&nbsp;码：</li>
                </ul>
              </div>
              <div id="" class="login_dlk">
                <input type="text" name="loginName" id="login_name"    onfocus="this.value=''" />
                <input type="password" name="password" id="pwd"  onfocus="this.value=''" />
              </div>
              <div id="" class="login_dlk_ll">
                <ul>
                  <li><span id="name_span"></span></li>
                  <li><span id="pwd_span"></span></li>
                </ul>
              </div>
            
            
    				<div id="login_dlk_nu" class="login_dlk_nu">
    					<ul>
    						<li><a href="javascript:void(0);" onclick="show();return false;">忘记密码</a></li>
    					</ul>
        				<div id="" class="login_dlk_nuh">
                             <input type="button" id="login" class="lan_button_login" value="登 录" />
        				</div>
                
                      <div id="" class="login_dlk_nuh">
                          <input type="button" class="lan_button_login_sy" id="testlogin" value="试用窗口" />
                      </div>    
            
    				</div>
            

              </form>
			</div>
		</div>
	</div>
</body>
</html>
