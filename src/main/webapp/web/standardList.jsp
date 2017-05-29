<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>疾病诊断与疗效评价标准平台</title>

<!-- 引入总引用文件 -->
<%@ include file="/WEB-INF/inc/taglib.jsp"%>

<script type="text/javascript" charset="utf-8">
  $(function() {
    $("#checkcount").click(function() {

      if ($("#content :checked").size() == 2) {

        var ids = "";
        $("#content :checked").each(function() {
          ids = ids + $(this).val() + ",";
        });
        ids = ids.substr(0, ids.length - 1);
        window.location.href = "${pageContext.request.contextPath}/web/standardCompare?ids=" + ids;
      } else {
        alert("请选择两个进行比较");
      }

    });

    initData();
  });

  var more_cn_count = 10;

  function initData() {

    if ($("#bzxz").children().size() - 1 > more_cn_count) {

      $("#bzxz").children().hide();
      $("#bzxz").children().slice(0, more_cn_count).show();
      $("#bzxz #gd").show();

    } else {
      $("#bzxz #gd").hide();
    }

    if ($("#bzxy").children().size() - 1 > more_cn_count) {
      $("#bzxy").children().hide();
      $("#bzxy").children().slice(0, more_cn_count).show();
      $("#bzxy #xygd").show();
    } else {
      $("#bzxy #xygd").hide();
    }

  }

  function more(s_id, obj1) {
    var s_text = $(obj1).text();
    var s_more_index = s_text.indexOf("更多");
    if (s_more_index >= 0) {
      $(obj1).empty();
      $(obj1).append("<<收起");
      $("#" + s_id).children().show();
    } else {
      $(obj1).empty();
      $(obj1).append("更多>>");
      $("#" + s_id).children().slice(0, $("#" + s_id).children().size() - 1).hide();
      $("#" + s_id).children().slice(0, more_cn_count).show();
    }
  }

  function findbyexact() {
    $("#searchForm").submit();
  }

  function showThisByTitle(title, type) {
    $('div[name="allcnTitle_' + type + '"]').css("display", "none");
    var ss = 'div[value="search_' + type + '_' + title + '"]';

    $(ss).css("display", "block");
  }

  function showThisAllTitle(type) {
    $('div[name="allcnTitle_' + type + '"]').css("display", "block");
  }
  function exportWord(id) {
    window.location.href = "${pageContext.request.contextPath}/web/importWord?id=" + id;
  }
</script>

</head>

<body>
  <!-- 引入头部文件 -->
  <%@ include file="/WEB-INF/inc/banner.jsp"%>

  <!--subnav-->
  <div id="container" class="container">
    <div id="subnav" class="subnav">
      <a href="${ctx}/web/homePage">首页>></a> <label title="${diseaseData.name}"><string:string
          length="30" value="${diseaseData.name} " /></label>
    </div>
  </div>
  <!--content-->
  <div id="container" class="container">
    <div id="content" class="content">
      <div id="search_adv" class="search_adv">
        <div id="title" class="title">
          <dl>
            <dt>
              <p title="${diseaseData.name}">
                <string:string length="30" value="${diseaseData.name} " />
              </p>
            </dt>
            <dd>
              中医病名：<label title="${diseaseData.tcmDiseaseName}"><string:string length="30"
                  value="${diseaseData.tcmDiseaseName} " /></label>
            </dd>
            <dd>
              西医病名：<label title="${diseaseData.mmDiseaseName}"><string:string length="30"
                  value="${diseaseData.mmDiseaseName} " /></label>
            </dd>
            <dd>
              英文病名：<label title="${diseaseData.enName}"><string:string length="30"
                  value="${diseaseData.enName} " /></label>
            </dd>
          </dl>
        </div>

        <div id="tab_btn" class="tab_btn">
          <c:if test="${isNotTest eq 'isNotTest' }">
            <img src="../resources/img/tab_btn1.jpg" onclick="exportWord('${diseaseData.id}');" />
          </c:if>
          <img src="../resources/img/tab_btn2.jpg" id="checkcount" />
        </div>
      </div>
      <!--标题-->
      <div id="zybz" class="zybz">
        <p onclick="showThisAllTitle('zhongyi')">中医标准</p>
        <ul>
          <c:forEach items="${cnStandardgroup}" var="data">
            <li><a href="#" onclick="showThisByTitle('${data}','zhongyi')">${data}</a></li>
          </c:forEach>
        </ul>
      </div>
      <!--内容-->
      <div id="bzxz">
        <c:forEach items="${cnStandards}" var="data">
          <div class="bzxz" value="search_zhongyi_${data.tcmDiseaseName}" name="allcnTitle_zhongyi">
            <%--           <li id="bzxz_li" value="search_zhongyi_${data.tcmDiseaseName}" name="allcnTitle_zhongyi" --%>
            <!--             style="list-style-type: none;"> -->
            <div class="bzxz_t" style="float: left;">
              <input name="Fruit" type="checkbox" value="${data.id}" /> <a
                href="${pageContext.request.contextPath}/web/standardDetail?id=${data.id}"
                target="_blank" title="${data.cnTitle}"><span>${data.cnTitle}</span> </a>
            </div>

            <div class="bzxz_list">
              <ul>
                <li class="l1">发布机构：${data.publishOrg}</li>
                <li>标准类型：中医标准</li>
                <li>发布日期：${data.publishDate}</li>
              </ul>
            </div>
            <!--           </li> -->
          </div>
        </c:forEach>
        <div id="gd" class="gd">
          <a id="more_cn_1" href='javascript:void(0);' onclick="more('bzxz',this)">更多>></a>
        </div>
      </div>

      <!--第二段内容-->
      <!--标题-->
      <div id="zybz" class="zybz">
        <p onclick="showThisAllTitle('xiyi')">西医标准</p>
        <ul>
          <c:forEach items="${enStandardgroup}" var="data">
            <li><a href="#" onclick="showThisByTitle('${data}','xiyi')">${data}</a></li>
          </c:forEach>
        </ul>
      </div>
      <!--内容-->
      <div id="bzxy">
        <c:forEach items="${enStandards}" var="data">
          <div class="bzxz" value="search_xiyi_${data.mmDiseaseName}" name="allcnTitle_xiyi">

            <%--           <li value="search_xiyi_${data.mmDiseaseName}" name="allcnTitle_xiyi" --%>
            <!--             style="list-style-type: none;"> -->
            <div class="bzxz_t" style="float: left;">
              <input name="Fruit" type="checkbox" value="${data.id}" /> <a
                href="${pageContext.request.contextPath}/web/standardDetail?id=${data.id}"
                target="_blank" title="${data.cnTitle}"><span>${data.cnTitle}</span> </a>
            </div>

            <div id="bzxz_list" class="bzxz_list">
              <ul>
                <li class="l1">发布机构：${data.publishOrg}</li>
                <li>标准类型：西医标准</li>
                <li>发布日期：${data.publishDate}</li>
              </ul>
            </div>
            <!--           </li> -->

          </div>
        </c:forEach>
        <!--the end-->
        <div id="xygd" class="gd">
          <a href='javascript:void(0);' onclick="more('bzxy',this)">更多>></a>
        </div>
      </div>
    </div>
  </div>

  <div style="clear: both"></div>
  <!-- 正文区域结束 -->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
