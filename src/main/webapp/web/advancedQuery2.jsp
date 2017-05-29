<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>高级检索</title>

<!-- 引入总引用文件 -->
<%@ include file="/WEB-INF/inc/taglib.jsp"%>

<script type="text/javascript" charset="utf-8">
  var more_cn_count = 10;
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

  function initData() {
    if ($("#bzxz").children().size() - 1 > more_cn_count) {

      $("#bzxz").children().hide();
      $("#bzxz").children().slice(0, more_cn_count).show();
      $("#bzxz #gd").show();

    } else {
      $("#bzxz #gd").hide();
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

  function exportWord(id) {
    window.location.href = "${pageContext.request.contextPath}/web/importWord?id=" + id;
  }

  function findbykeyword() {

    if (!checkRate("beginTime", "发布时间")) {
      return false;
    }
    if (!checkRate("endtime", "结束时间")) {
      return false;
    }

    $("#searchForm").submit();

  }

  function checkRate(eId, eTipName) {

    var re = /^[1-9]+[0-9]*]*$/; //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/      

    if ($("#" + eId).val()) {

      if (!re.test($("#" + eId).val())) {
        alert(eTipName + "只能为数字");
        $("#" + eId).val("");
        return false;
      }
    }
    return true;
  }
</script>

<script type="text/javascript">
  var total;

  $(function() {
    total = $("div .ssk_1").length;
    var tempDiv = $("#ssk_1_base #ssk_1").clone(true);
    $("#ssk_1_nianfen").before(tempDiv);

  });

  function addDiv(temp) {

    var addObj = $("#ssk_1_base #ssk_1").clone();

    var tempCount = $("#search div").size() - 1;
    if (tempCount < 5) {
      $(temp).parentsUntil("#search").last().after(addObj);
    }

    var curIndex = $("#search div").index(addObj);

    if (curIndex == 1) {
      addObj.find("select[name='fields']").prop("value", "2");

    }
  }

  function delDiv(temp) {

    var tempCount = $("#search div").size() - 1;

    var curIndex = $("#search div").index($(temp).parentsUntil("#search").last());

    if (tempCount > 1) {
      $(temp).parentsUntil("#search").last().remove();
    }
  }
</script>


</head>
<body>
  <!-- 引入头部文件 -->
  <%@ include file="/WEB-INF/inc/header.jsp"%>
  <!--当前位置 -->

  <div id="container" class="container">
    <div id="subnav" class="subnav">
      <a href="${ctx}/web/homePage" style="color: #636363;">首页>></a>高级检索
    </div>
  </div>
  <!-- 当前位置结束 -->

  <!-- 正文区域 -->

  <!--高级搜索-->
  <div id="container" class="container">
    <!--搜索第一行-->
    <div id="ssk_1_base" style="display: none;">
      <div id="ssk_1" class="ssk_1">
        <ul>
          <li class="l1"><img onclick="addDiv(this);" id="add" src="${pageContext.request.contextPath}/resources/img/jh.jpg" /> <img
            onclick="delDiv(this);" id='remove' src='${pageContext.request.contextPath}/resources/img/jianh.jpg' /></li>
          <li class="l2"><select name="fields" class='select'>
              <option value="1">病种名称</option>
              <option value="2">中文题名</option>
              <option value="3">英文题名</option>
              <option value="4">标准类型</option>
              <option value="5">发布机构</option>
              <option value="6">来源</option>
              <option value="7">全文语种</option>
              <option value="8">西医病名</option>
              <option value="9">分型分期</option>
              <option value="10">西医诊断标准</option>
              <option value="11">中医病名</option>
              <option value="12">中医辨证</option>
              <option value="13">中医诊断标准</option>
              <option value="14">疗效指标</option>
              <option value="15">疗效判定指标</option>
              <option value="16">备注</option>
          </select></li>
          <li class="l3"><input type="text" name="keywords" /></li>
          <li class="l4 "><select id="range1" name="ranges" class="sl2">
              <option value="1" selected>模糊</option>
              <option value="2">精确</option>
          </select></li>
        </ul>
      </div>
    </div>
    <form id="searchForm" method="post" action="${pageContext.request.contextPath}/web/advancedQuery">
      <div id="search" class="gjss">
        <c:if test="${!empty  fieldsArray}">
          <c:forEach items="${fieldsArray}" begin="0" varStatus="varStatus">
            <div id="ssk_1" class="ssk_1">
              <ul>
                <li class="l1">
                  <p>并且</p>
                </li>

                <li class="l2"><select id="fields2" name="fields" class="select">
                    <option value="1" <c:if test="${fieldsArray[varStatus.index] eq '1'}">selected</c:if>>病种名称</option>
                    <option value="2" <c:if test="${fieldsArray[varStatus.index] eq '2'}">selected</c:if>>中文题名</option>
                    <option value="3" <c:if test="${fieldsArray[varStatus.index] eq '3'}">selected</c:if>>英文题名</option>
                    <option value="4" <c:if test="${fieldsArray[varStatus.index] eq '4'}">selected</c:if>>标准类型</option>
                    <option value="5" <c:if test="${fieldsArray[varStatus.index] eq '5'}">selected</c:if>>发布机构</option>
                    <option value="6" <c:if test="${fieldsArray[varStatus.index] eq '6'}">selected</c:if>>来源</option>
                    <option value="7" <c:if test="${fieldsArray[varStatus.index] eq '7'}">selected</c:if>>全文语种</option>
                    <option value="8" <c:if test="${fieldsArray[varStatus.index] eq '8'}">selected</c:if>>西医病名</option>
                    <option value="9" <c:if test="${fieldsArray[varStatus.index] eq '9'}">selected</c:if>>分型分期</option>
                    <option value="10" <c:if test="${fieldsArray[varStatus.index] eq '10'}">selected</c:if>>西医诊断标准</option>
                    <option value="11" <c:if test="${fieldsArray[varStatus.index] eq '11'}">selected</c:if>>中医病名</option>
                    <option value="12" <c:if test="${fieldsArray[varStatus.index] eq '12'}">selected</c:if>>中医辨证</option>
                    <option value="13" <c:if test="${fieldsArray[varStatus.index] eq '13'}">selected</c:if>>中医诊断标准</option>
                    <option value="14" <c:if test="${fieldsArray[varStatus.index] eq '14'}">selected</c:if>>疗效指标</option>
                    <option value="15" <c:if test="${fieldsArray[varStatus.index] eq '15'}">selected</c:if>>疗效判定指标</option>
                    <option value="16" <c:if test="${fieldsArray[varStatus.index] eq '16'}">selected</c:if>>备注</option>
                </select></li>
                <li class="l3"><input type="text" name="keywords" value="${keywordsArray[varStatus.index]}" /></li>
                <li class="l4 "><select id="range2" name="ranges" class="sl2">
                    <option value="1" <c:if test="${rangesArray[varStatus.index] eq '1'}">selected</c:if>>模糊</option>
                    <option value="2" <c:if test="${rangesArray[varStatus.index] eq '2'}">selected</c:if>>精确</option>
                </select></li>
              </ul>
            </div>

          </c:forEach>
        </c:if>
        <!--年份选择-->
        <div id="ssk_1_nianfen" class="ssk_1_nianfen">
          <ul>
            <li>发布时间</li>
            <li><input type="text" name="beginTime" id="beginTime" value="${beginTime}" /></li>
            <li><p>到</p></li>
            <li><input type="text" name="endtime" id="endtime" value="${endtime}" /></li>
            <li><input type="submit" class="lan_button" name="close" value="检索" onclick="findbykeyword(); return false;"></li>
          </ul>

        </div>
        <!--年份选择-->
      </div>
    </form>
  </div>
  <!--subnav-->
  <div id="container" class="container">
    <div id="subnav" class="subnav"></div>
  </div>

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

      <div id="bzxz" class="bzxz">
        <c:forEach items="${standardList}" var="data">
          <li style="list-style-type: none;">
            <div id="bzxz_t" class="bzxz_t">
              <input name="Fruit" type="checkbox" value="${data.id}" /> <a
                href="${pageContext.request.contextPath}/web/standardDetail?id=${data.id}" target="_blank" title="${data.cnTitle}"><span>${data.cnTitle}</span>
              </a>
            </div>

            <div id="bzxz_list" class="bzxz_list">
              <ul>
                <li class="l1">发布机构：${data.publishOrg}</li>
                <li>标准类型： <c:if test="${data.type=='0'}">中医标准</c:if> <c:if test="${data.type=='1'}">西医标准</c:if>
                </li>
                <li>发布日期：${data.publishDate}</li>
              </ul>

            </div>
          </li>
        </c:forEach>
        <!--更多-->
        <div id="gd" class="gd_yc">
          <a href='javascript:void(0)' onclick="more('bzxz',this);">更多>></a>
        </div>

      </div>
    </div>

    <!--the end-->

  </div>
  <!--the end-->

  <%@ include file="/WEB-INF/inc/footer.jsp"%>
</body>
</html>