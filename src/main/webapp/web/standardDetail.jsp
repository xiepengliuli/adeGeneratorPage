<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>疾病诊断与疗效评价标准平台</title>
<!-- 引入总引用文件 -->
<%@ include file="/WEB-INF/inc/taglib.jsp"%>
<script type="text/javascript" charset="utf-8">
  function findbyexact() {
    $("#searchForm").submit();
  }
</script>
</head>

<body>
  <!-- 引入头部文件 -->
  <%@ include file="/WEB-INF/inc/header.jsp"%>

  <!--导航-->
  <div id="topnav" class="topnav">
    <div id="container" class="container"><a href="${ctx}/web/homePage">首页>></a>慢性肺原性心脏病诊断标准</div>
  </div>

  <!--内容-->
  <div id="container" class="container">
    <div id="content" class="content">
      <div id="content_left" class="content_left">
        <c:if test="${not empty fn:trim(standardData.cnTitle)}">
          <div id="showThis_1" class="content_left_title">
            <b>【中文题名】：</b>${standardData.cnTitle}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.enTitle)}">
          <div id="showThis_2" class="content_left_title">
            <b>【英文题名】：</b>${standardData.enTitle}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.orderNum)}">
          <div id="showThis_3" class="content_left_title">
            <b>【序号】：</b>${standardData.orderNum}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.type)}">
          <div id="showThis_4" class="content_left_title">
            <b>【标准类型】：</b>
            <c:if test="${standardData.type=='0'}">中医标准</c:if>
            <c:if test="${standardData.type=='1'}">西医标准</c:if>
          </div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.publishOrg)}">
          <div id="showThis_5" class="content_left_title">
            <b>【发布机构 】：</b>${standardData.publishOrg}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.publishDate)}">
          <div id="showThis_6" class="content_left_title">
            <b>【发布日期】：</b>${standardData.publishDate}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.source)}">
          <div id="showThis_7" class="content_left_title">
            <b>【来源】：</b>${standardData.source}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.language)}">
          <div id="showThis_8" class="content_left_title">
            <b>【全文语种】：</b>${standardData.language}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.mmDiseaseName)}">
          <div id="showThis_9" class="content_left_title">
            <b>【西医病名】：</b>${standardData.mmDiseaseName}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.typeStag)}">
          <div id="showThis_10" class="content_left_title">
            <b>【分型分期】：</b>${standardData.typeStag}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.mmStandard)}">
          <div id="showThis_11" class="content_left_title">
            <b>【西医诊断标准】：</b>${standardData.mmStandard}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.tcmDiseaseName)}">
          <div id="showThis_12" class="content_left_title">
            <b>【中医病名】：</b>${standardData.tcmDiseaseName}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.tcmDiseaseDialectical)}">
          <div id="showThis_13" class="content_left_title">
            <b>【中医辨证】：</b>${standardData.tcmDiseaseDialectical}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.tcmStandard)}">
          <div id="showThis_14" class="content_left_title">
            <b>【中医诊断标准】：</b>${standardData.tcmStandard}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.effectTarget)}">
          <div id="showThis_15" class="content_left_title">
            <b>【疗效指标】：</b>${standardData.effectTarget}</div>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.effectTarget)}">
          <div id="showThis_16" class="content_left_title">
            <b>【疗效判定标准】：</b>${standardData.effectStandard}</div>
        </c:if>
        <c:if test="${standardData.attachsPage.size()>0}">
          <div id="showThis_17" class="content_left_title">
            <b>【原文】：</b>
          </div>
          <c:forEach items="${standardData.attachsPage}" var="data1" varStatus="varStatus">
            <a href="${pageContext.request.contextPath}/web/download?id=${data1.id}">${data1.name}&nbsp;&nbsp;</a>
          </c:forEach>
        </c:if>
        <c:if test="${not empty fn:trim(standardData.remark)}">
          <div id="showThis_18" class="content_left_title">
            <b>【备注】：</b>${standardData.remark}</div>
        </c:if>

      </div>
      <!--时间轴-->
      <div id="content_tree" class="content_tree">

        <div class="times">
          <ul>
            <c:if test="${not empty fn:trim(standardData.cnTitle)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_1').scrollIntoView();">中文题名</a>
                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.enTitle)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_2').scrollIntoView();">英文题名</a>
                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.orderNum)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_3').scrollIntoView();">序号</a>
                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.type)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_4').scrollIntoView();">标准类型</a>
                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.publishOrg)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_5').scrollIntoView();">发布机构</a>
                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.publishDate)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_6').scrollIntoView();">发布日期</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.source)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_7').scrollIntoView();">来源</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.language)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_8').scrollIntoView();">全文语种</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.mmDiseaseName)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_9').scrollIntoView();">西医病名</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.typeStag)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_10').scrollIntoView();">分型分期</a>


                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.mmStandard)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_11').scrollIntoView();">西医诊断标准</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.tcmDiseaseName)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_12').scrollIntoView();">中医病名</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.tcmDiseaseDialectical)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_13').scrollIntoView();">中医辨证</a>


                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.tcmStandard)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_14').scrollIntoView();">中医诊断标准</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.effectTarget)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_15').scrollIntoView();">疗效指标</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.effectStandard)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_16').scrollIntoView();">疗效判定标准</a>

                </p></li>
            </c:if>
            <c:if test="${standardData.attachsPage.size()>0}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_17').scrollIntoView();">原文</a>

                </p></li>
            </c:if>
            <c:if test="${not empty fn:trim(standardData.remark)}">
              <li><b></b>
                <p>
                  <a href="javascript:void(0)" onclick="document.getElementById('showThis_18').scrollIntoView();">备注</a>

                </p></li>
            </c:if>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- 正文区域结束 -->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
