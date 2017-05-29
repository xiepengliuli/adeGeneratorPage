<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>证候首页</title>
	<!-- 引入总引用文件 -->
    <%@ include file="/WEB-INF/inc/taglib.jsp"%>

<script type="text/javascript" charset="utf-8">
	function findbykeyword() {
	    $("#searchForm").submit();
	}
</script>
</head>
<body>

         
    
        <!--当前位置 -->
	        <div class="dqwz_bg">
	            <div class="wid1000 dqwz">
	                  <a href="${ctx}/front.jsp">首页</a> <span>&gt;</span> <a
	            href="${pageContext.request.contextPath}/web/symptomIndex">证候</a>
	            </div>
	        </div>
        <!-- 当前位置结束 -->
   
        <!-- 正文区域 -->
		  <div class="wid1000">
		     <p>中医常见证候病症分析报告</p>
		     <div id="showName">
		       <ul>
		           <li><a>脾肾阳虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		           <li><a>气滞血瘀证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		           <li><a>气阴两虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		           <li><a>气血两虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		           <li><a>肝郁气滞证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		           <li><a>肝胆湿热证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
	               <li><a>肝肾阴虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
	               <li><a>心脾两虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
	               <li><a>阴虚阳亢证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
	               <li><a>阴阳两虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
	               <li><a>肝郁脾虚证</a><a href="${pageContext.request.contextPath}/web/download?pdf_path=D:\apache-tomcat-7.0.55\webapps\download\jQuery_EasyUI中文.pdf">PDF</a></li>
		       </ul>
		     </div>
		     
		     <div id="search">
		          <form id="searchForm" method="post" action="${pageContext.request.contextPath}/web/symptomIndex/">
		               <select id="fields" name="fields">
		                   <option value="1" ${fields=="1"?'selected':''}>证候名称</option>
		                   <option value="2" ${fields=="2"?'selected':''}>疾病名称</option>
		                   <option value="3" ${fields=="3"?'selected':''}>症状名称</option>
		                   <option value="4" ${fields=="4"?'selected':''}>文献题名</option>
		                   <option value="5" ${fields=="5"?'selected':''}>期刊名称</option>
		                   <option value="6" ${fields=="6"?'selected':''}>年期页</opton>
		               </select>
		               <input type="text" id="keyword" placeholder="输入检索词" name="keyword" value="${keyword}"/>
		               <input id="jq" type="radio" value="0" name="range" ${range=="0"?'checked':''}><label for="jq">精确</label></input>
		               <input id="mh" type="radio" value="1" name="range" checked ${range=="1"?'checked':''}><label for="mh">模糊</label></input>
		               <input type="button" class="" onclick="findbykeyword();" value="检索"/>
		               <a href="${pageContext.request.contextPath}/web/symptomAdvanced">高级检索</a>
		          </form> 
		     </div>
		     <div id="show">
			     <c:forEach items="${pageHelper.data}" var="data">
			          <div>
			              <span>${data.id }</span><span>证候名称:${data.name }</span>&emsp;&emsp;<span>疾病名称:${data.diseaseName }</span><br>
			              <span>症状名称:${fn:replace(data.symptomName, '$', ',')}</span><br>
			              <span>文献题名:${data.literatureName }</span>&emsp;&emsp;<span>期刊名称:${data.journalName }</span>&emsp;&emsp;<span>年期页:${data.yearJournalPage }</span>
			          </div>
			          <hr>
			     </c:forEach>
		     </div>
		     <div class="feny">
		       <tools:page url="${pageContext.request.contextPath}/web/symptomIndex?keyword=${keyword}&fields=${fields}&range=${range}" 
		          pageHelper="${pageHelper}" />
	         </div>
		  </div>
      <!-- 正文区域结束 -->
</body>
</html>