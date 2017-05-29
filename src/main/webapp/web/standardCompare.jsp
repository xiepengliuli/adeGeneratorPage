<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>疾病诊断与疗效评价标准平台</title>

<!-- 引入总引用文件 -->
<%@ include file="/WEB-INF/inc/taglib.jsp"%>


<script type="text/javascript" charset="utf-8">
//   $(function() {
//     //将对比不同的数据设置为红色显示
//     var trs = $(".u1,.u2");//取得所有需要比较的行
//     for (var i = 0; i < trs.length; i++) {
//       var tds = $(trs[i]).children();//取得每行的所有列
//       //判断第二列和第三列的值是否相同
//       if ($(tds[1]).html() != $(tds[2]).html()) {
//         $(tds[2]).css('color', 'red');//为不同的列设置红色样式
//       }
//     }
//   });
</script>
</head>

<body>
  <!-- 引入头部文件 -->
  <%@ include file="/WEB-INF/inc/header.jsp"%>

  <!-- 当前位置 -->
  <div id="container" class="container">
    <div id="subnav" class="subnav"><a href="${ctx}/web/homePage" style="color: #636363; " >首页>></a></div>
  </div>
  <!-- 当前位置结束 -->


  <!-- 正文区域 -->
  <div id="container" class="container">
    <div id="content" class="content">
      <div id="db_table" class="db_table">
        <ul>
          <li class="l1 l1w" >【字段名称】:</li>
          <li class="l2">数据1</li>
          <li class="l3">数据2</li>
        </ul>

        <ul class="u1">
          <li class="l1">【中文题名】:</li>
          <li class="l2">&nbsp;${standardData.cnTitle}</li>
          <li class="l3">&nbsp;${standardData2.cnTitle}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【英文题名】:</li>
          <li class="l2">&nbsp;${standardData.enTitle}</li>
          <li class="l3">&nbsp;${standardData2.enTitle}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【序号】:</li>
          <li class="l2">&nbsp;${standardData.orderNum}</li>
          <li class="l3">&nbsp;${standardData2.orderNum}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【标准类型】:</li>
          <li class="l2"><c:if test="${standardData.type=='0'}">&nbsp;中医标准</c:if> <c:if
              test="${standardData.type=='1'}">&nbsp;西医标准</c:if></li>
          <li class="l3"><c:if test="${standardData2.type=='0'}">&nbsp;中医标准</c:if> <c:if
              test="${standardData2.type=='1'}">&nbsp;西医标准</c:if></li>
        </ul>

        <ul class="u1">
          <li class="l1">【发布机构 】:</li>
          <li class="l2">&nbsp;${standardData.publishOrg}</li>
          <li class="l3">&nbsp;${standardData2.publishOrg}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【发布日期】:</li>
          <li class="l2">&nbsp;${standardData.publishDate}</li>
          <li class="l3">&nbsp;${standardData2.publishDate}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【 来源】:</li>
          <li class="l2">&nbsp;${standardData.source}</li>
          <li class="l3">&nbsp;${standardData2.source}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【全文语种】:</li>
          <li class="l2">&nbsp;${standardData.language}</li>
          <li class="l3">&nbsp;${standardData2.language}</li>
        </ul>


        <ul class="u1">
          <li class="l1">【西医病名】:</li>
          <li class="l2">&nbsp;${standardData.mmDiseaseName}</li>
          <li class="l3">&nbsp;${standardData2.mmDiseaseName}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【分型分期】:</li>
          <li class="l2">&nbsp;${standardData.typeStag}</li>
          <li class="l3">&nbsp;${standardData2.typeStag}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【西医诊断标准】:</li>
          <li class="l2">&nbsp;${standardData.mmStandard}</li>
          <li class="l3">&nbsp;${standardData2.mmStandard}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【中医病名】:</li>
          <li class="l2">&nbsp;${standardData.tcmDiseaseName}</li>
          <li class="l3">&nbsp;${standardData2.tcmDiseaseName}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【中医辨证】:</li>
          <li class="l2">&nbsp;${standardData.tcmDiseaseDialectical}</li>
          <li class="l3">&nbsp;${standardData2.tcmDiseaseDialectical}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【中医诊断标准】:</li>
          <li class="l2">&nbsp;${standardData.tcmStandard}</li>
          <li class="l3">&nbsp;${standardData2.tcmStandard}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【疗效指标】:</li>
          <li class="l2">&nbsp;${standardData.effectTarget}</li>
          <li class="l3">&nbsp;${standardData2.effectTarget}</li>
        </ul>

        <ul class="u2">
          <li class="l1">【疗效判定标准】:</li>
          <li class="l2">&nbsp;${standardData.effectStandard}</li>
          <li class="l3">&nbsp;${standardData2.effectStandard}</li>
        </ul>

        <ul class="u1">
          <li class="l1">【备注】:</li>
          <li class="l2">&nbsp;${standardData.remark}</li>
          <li class="l3">&nbsp;${standardData2.remark}</li>
        </ul>
        
        <ul class="u2">
          <li class="l1">【原文】:</li>
          <li class="l2">&nbsp;					
                     <c:forEach items="${standardData.attachsPage}" var="data1" varStatus="varStatus">
                     <a href="${pageContext.request.contextPath}/web/download?id=${data1.id}">${data1.name}&nbsp;&nbsp;</a>
					</c:forEach></li>
          <li class="l3">&nbsp;					
                      <c:forEach items="${standardData2.attachsPage}" var="data2" varStatus="varStatus">
                     <a href="${pageContext.request.contextPath}/web/download?id=${data2.id}">${data2.name}&nbsp;&nbsp;</a>
					</c:forEach></li>
        </ul>
      </div>
    </div>
  </div>

  <!-- 正文区域结束 -->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
