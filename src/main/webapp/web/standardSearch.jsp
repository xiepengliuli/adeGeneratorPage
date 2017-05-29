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

  })

  var more_cn_count = 10;

  function initData() {
    if ($("#bzxz").children().size() - 1 > more_cn_count) {

      $("#bzxz").children().hide();
      $("#bzxz").children().slice(0, more_cn_count).show();
      $("#bzxz #gd").show();

    } else {
      $("#bzxz #gd").hide();
    }
  }

  function exportWord(id) {
    window.location.href = "${pageContext.request.contextPath}/web/importWord?id=" + id;
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
</script>

</head>

<body>
  <!-- 引入头部文件 -->
  <%@ include file="/WEB-INF/inc/banner.jsp"%>

  <!--subnav-->
  <div id="container" class="container">
    <div id="subnav" class="subnav">
      <a href="${ctx}/web/homePage" style="color: #636363;">首页>></a>标准检索
    </div>
  </div>

  <!--content-->
  <div id="container" class="container">
    <div id="content" class="content">
      <div id="search_adv" class="search_adv">
        <div id="title" class="title">
          <dl>
            <dt>&nbsp;</dt>
          </dl>
        </div>

        <div id="tab_btn" class="tab_btn">
          <img src="../resources/img/tab_btn2.jpg" id="checkcount" />
        </div>
      </div>
      <!--标题-->

      <!--内容-->
      <div id="bzxz">
        <c:forEach items="${standardList}" var="data">
          <div  class="bzxz">

            <div id="bzxz_t" class="bzxz_t">
              <input name="Fruit" type="checkbox" value="${data.id}" /> <a
                href="${pageContext.request.contextPath}/web/standardDetail?id=${data.id}"
                target="_blank" title="${data.cnTitle}"><span>${data.cnTitle}</span> </a>
            </div>

            <div id="bzxz_list" class="bzxz_list">
              <ul>
                <li class="l1">发布机构：${data.publishOrg}</li>
                <li>标准类型： <c:if test="${data.type=='0'}">中医标准</c:if> <c:if
                    test="${data.type=='1'}">西医标准</c:if>
                </li>
                <li>发布日期：${data.publishDate}</li>
              </ul>
            </div>

          </div>
        </c:forEach>

        <div id="gd" class="bzxz">
          <div class="gd">
            <a href='#' onclick="more('bzxz',this)">更多>></a>
          </div>
        </div>
      </div>

    </div>

    <!--the end-->
  </div>
  </div>



  <!-- 正文区域结束 -->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>
