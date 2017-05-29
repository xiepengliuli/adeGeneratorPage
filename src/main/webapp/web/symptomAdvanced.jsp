<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>证候高级检索</title>
	
	<!-- 引入总引用文件 -->
    <%@ include file="/WEB-INF/inc/taglib.jsp"%>
    <script type="text/javascript">
        var total = 3;
        $(function(){
        	//增加检索条件
        	$("#add").click(function(){
        	  alert(total);
        		if(total <= 5){
	        		var submit = $("#submit");
        			if(total ==5){        			 
        				$("#remove").remove();
        				var input = "<input id='remove' type='button' value='-'/>";
        				$("#add").css("visibility", "hidden");
        				$("#add").after($(input));
        			}
	        		var div = "<div>";
	        		    div += "<select id='logic"+total+"' name='logic'>";
	        		    div += "<option value='1' selected='selected'>与</option>";
	        		    div += "<option value='2'>或</option>";
	        		    div += "<option value='3'>非</option>";
	        		    div += "</select> ";
		        		total++;
	        		    div += "<select id='fields"+total+"' name='fields'>";
	        		    div += "<option value='1'>证候名称</option>";
	        		    div += "<option value='2'>疾病名称</option>";
	        		    div += "<option value='3'>症状名称</option>";
	        		    div += "<option value='4'>文献题名</option>";
	        		    div += "<option value='5'>期刊名称</option>";
	                    div += "<option value='6'>年份</opton>"
	                    div += "</select> ";
	                    div += "<input type='text' name='keyword'/> ";
	                    div += "<select id='range"+total+"' name='range'>";
	                    div += "<option value='1' selected='selected'>模糊</option>";
	                    div += "<option value='2'>精确</option>";
	                    div += "</select>";
	                    div += "</div>";
	                    submit.before($(div));
        		}
        	});
        	//减少检索条件
        	$("#search").on("click", "#remove", function(){
        		total--;
        		if(total >= 3){
        			if(total <= 5){
        				$("#add").css("visibility", "visible");
        			}
        			if(total == 3){
        				$("#remove").css("visibility", "hidden");
        			}
        			$("#search div:eq("+total+")").remove();
        		}
        	});
        });
    </script>
</head>
<body>
    <!--当前位置 -->
            <div class="dqwz_bg">
                <div class="wid1000 dqwz">
                      <a href="${ctx}/front.jsp">首页</a> <span>&gt;</span> <a
                href="${pageContext.request.contextPath}/web/symptomAdvanced">高级检索</a>
                </div>
            </div>
    <!-- 当前位置结束 -->
        
    <!-- 正文区域 -->
    <div class="wid1000"> 
        <p>中医常见证候病症分析报告</p>
        <div id="search">
            <form id="searchForm" method="post" action="${pageContext.request.contextPath}/web/symptomAdvanced/">
	            <!-- 第一行条件开始 -->
	            <div>
	                <input id="add" type="button" value="+"/>&nbsp;
	                <select id="fields1" name="fields">
		                <option value="1" ${fields1=="1"?'selected':''}>证候名称</option>
		                <option value="2" ${fields1=="2"?'selected':''}>疾病名称</option>
		                <option value="3" ${fields1=="3"?'selected':''}>症状名称</option>
		                <option value="4" ${fields1=="4"?'selected':''}>文献题名</option>
		                <option value="5" ${fields1=="5"?'selected':''}>期刊名称</option>
		                <option value="6" ${fields1=="6"?'selected':''}>年份</opton>
	               </select>
	               <input type="text" name="keyword" value="${keyword1}"/>
	               <select id="range1" name="range">
	                    <option value="1" checked ${range=="1"?'checked':''}>模糊</option>
	                    <option value="2" ${range=="2"?'checked':''}>精确</option>
	               </select>
	            </div>
	            <!-- 第一行条件结束 -->
	            <!-- 第二行条件开始 -->
	            <div>
	                <select id="logic1" name="logic">
	                    <option value="1" ${logic1=="1"?'selected':''}>与</option>
	                    <option value="2" ${logic1=="2"?'selected':''}>或</option>
	                    <option value="3" ${logic1=="3"?'selected':''}>非</option>
	                </select>
	                <select id="fields2" name="fields">
	                    <option value="1" ${fields=="1"?'selected':''}>证候名称</option>
	                    <option value="2" ${fields=="2"?'selected':''}>疾病名称</option>
	                    <option value="3" ${fields=="3"?'selected':''}>症状名称</option>
	                    <option value="4" ${fields=="4"?'selected':''}>文献题名</option>
	                    <option value="5" ${fields=="5"?'selected':''}>期刊名称</option>
	                    <option value="6" ${fields=="6"?'selected':''}>年份</opton>
	               </select>
	               <input type="text" name="keyword" value="${keyword2}"/>
	               <select id="range2" name="range">
	                    <option value="1" checked ${range=="1"?'checked':''}>模糊</option>
	                    <option value="2" ${range2==""?'checked':''}>精确</option>
	               </select>
	            </div>
	            <!-- 第二行条件结束 -->
	            <!-- 第三行条件开始 -->
	            <div>
	                <select id="logic2" name="logic">
	                    <option value="1" ${logic2=="1"?'selected':''}>与</option>
	                    <option value="2" ${logic2=="2"?'selected':''}>或</option>
	                    <option value="3" ${logic2=="3"?'selected':''}>非</option>
	                </select>
	                <select id="fields3" name="fields">
	                    <option value="1" ${fields=="1"?'selected':''}>证候名称</option>
	                    <option value="2" ${fields=="2"?'selected':''}>疾病名称</option>
	                    <option value="3" ${fields=="3"?'selected':''}>症状名称</option>
	                    <option value="4" ${fields=="4"?'selected':''}>文献题名</option>
	                    <option value="5" ${fields=="5"?'selected':''}>期刊名称</option>
	                    <option value="6" ${fields=="6"?'selected':''}>年份</opton>
	               </select>
	               <input type="text" name="keyword" value="${keyword3}"/>
	               <select id="range3" name="range">
	                    <option value="1" checked ${range=="1"?'checked':''}>模糊</option>
	                    <option value="2" ${range=="2"?'checked':''}>精确</option>
	               </select>
	            </div>
	            <!-- 第三行条件结束 -->
	            
	            <!-- 检索按钮 -->
	            <input type="submit" value="检索" id="submit"/>
            </form>
        </div>
        <!-- 数据显示区开始 -->
         <c:forEach items="${pageHelper.data}" var="data">
                  <div>
                      <span>${data.id }</span><span>证候名称:${data.name }</span>&emsp;&emsp;<span>疾病名称:${data.diseaseName }</span><br>
                      <span>症状名称:${fn:replace(data.symptomName, '$', ',')}</span><br>
                      <span>文献题名:${data.literatureName }</span>&emsp;&emsp;<span>期刊名称:${data.journalName }</span>&emsp;&emsp;<span>年期页:${data.yearJournalPage }</span>
                  </div>
                  <hr>
         </c:forEach>
         <!-- 数据显示区结束 -->
         <!-- 分页开始 -->
         <div class="feny">
           <tools:page url="${pageContext.request.contextPath}/web/symptomAdvanced" 
              pageHelper="${pageHelper}" />
         </div>
         <!-- 分页结束 -->
    </div>  
</body>
</html>