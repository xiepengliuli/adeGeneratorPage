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
    //增加检索条件
    $("#add")
        .click(
            function() {
              if (total <= 5) {
                var ssk_1_nianfen = $("#ssk_1_nianfen");
                if (total == 3) {
                  $("#remove").remove();
                  var input = "<img id='remove' src='${pageContext.request.contextPath}/resources/img/jianh.jpg'/>";
                  $("#add").after($(input));
                }

                if (total == 5) {
                  $("#add").css("visibility", "hidden");
                }
                var div = "<div id='ssk_1' class='ssk_1'>";
                div += "<ul>";
                div += "<li class='l1'>";
                div += "<p>并且</p>";
                div += "</li>";
                total++;
                div += "<li class='l2'>";
                div += "<select id='fields"+total+"' name='fields' class='select'>";
                div += "<option value='1' selected='selected'>病 种名称</option>";
                div += "<option value='2'>中文题名</option>";
                if (total == 6) {
                  div += "<option value='3' selected='selected'>英文题名</option>";
                } else {
                  div += "<option value='3'>英文题名</option>";
                }
                if (total == 4) {
                  div += "<option value='4' selected='selected'>标准类型</option>";
                } else {
                  div += "<option value='4'>标准类型</option>";
                }
                div += "<option value='5'>发布机构</option>";
                div += "<option value='6'>来源</option>";
                if (total == 5) {
                  div += "<option value='7' selected='selected'>全文语种</option>";
                } else {
                  div += "<option value='7'>全文语种</option>";
                }
                div += "<option value='8'>西医病名</option>";
                div += "<option value='9'>分型分期</option>";
                div += "<option value='10'>西医诊断标准</option>";
                div += "<option value='11'>中医病名</option>";
                div += "<option value='12'>中医辨证</option>";
                div += "<option value='13'>中医诊断标准</option>";
                div += "<option value='14'>疗效指标</option>";
                div += "<option value='15'>疗效判定指标</option>";
                div += "<option value='16'>备注</option>";

                div += "</select> ";
                div += "</li>";
                div += "<li class='l3'>";
                div += "<input type='text' name='keywords'/> ";
                div += "</li>";
                div += "<li class='l4'>";
                div += "<select id='range"+total+"' name='ranges' class='sl2'>";
                div += "<option value='1' selected='selected'>模糊</option>";
                div += "<option value='2'>精确</option>";
                div += "</select>";
                div += "</li>";
                div += "</ul>";
                div += "</div>";
                ssk_1_nianfen.before($(div));
              }
            });
    //减少检索条件
    $("#search").on("click", "#remove", function() {
      total--;
      if (total >= 3) {
        if (total <= 5) {
          $("#add").css("visibility", "visible");
        }
        if (total == 3) {
          $("#remove").css("visibility", "hidden");
        }
        $("#search div:eq(" + total + ")").remove();
      }
    });

  });
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
    <form id="searchForm" method="post" action="${pageContext.request.contextPath}/web/advancedQuery">
      <div id="search" class="gjss">
        <!--搜索第一行-->
        <div id="ssk_1" class="ssk_1">
          <ul>
            <li class="l1"><img id="add" src="${pageContext.request.contextPath}/resources/img/jh.jpg" /></li>
            <li class="l2"><select name="fields" class="select">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
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
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${fieldsArray[0] eq '1'}">selected</c:if>>病种名称</option>
                    <option value="2" <c:if test="${fieldsArray[0] eq '2'}">selected</c:if>>中文题名</option>
                    <option value="3" <c:if test="${fieldsArray[0] eq '3'}">selected</c:if>>英文题名</option>
                    <option value="4" <c:if test="${fieldsArray[0] eq '4'}">selected</c:if>>标准类型</option>
                    <option value="5" <c:if test="${fieldsArray[0] eq '5'}">selected</c:if>>发布机构</option>
                    <option value="6" <c:if test="${fieldsArray[0] eq '6'}">selected</c:if>>来源</option>
                    <option value="7" <c:if test="${fieldsArray[0] eq '7'}">selected</c:if>>全文语种</option>
                    <option value="8" <c:if test="${fieldsArray[0] eq '8'}">selected</c:if>>西医病名</option>
                    <option value="9" <c:if test="${fieldsArray[0] eq '9'}">selected</c:if>>分型分期</option>
                    <option value="10" <c:if test="${fieldsArray[0] eq '10'}">selected</c:if>>西医诊断标准</option>
                    <option value="11" <c:if test="${fieldsArray[0] eq '11'}">selected</c:if>>中医病名</option>
                    <option value="12" <c:if test="${fieldsArray[0] eq '12'}">selected</c:if>>中医辨证</option>
                    <option value="13" <c:if test="${fieldsArray[0] eq '13'}">selected</c:if>>中医诊断标准</option>
                    <option value="14" <c:if test="${fieldsArray[0] eq '14'}">selected</c:if>>疗效指标</option>
                    <option value="15" <c:if test="${fieldsArray[0] eq '15'}">selected</c:if>>疗效判定指标</option>
                    <option value="16" <c:if test="${fieldsArray[0] eq '16'}">selected</c:if>>备注</option>
                  </c:otherwise>
                </c:choose>


            </select></li>
            <li class="l3"><input type="text" name="keywords" value="${keywordsArray[0]}" /></li>
            <li class="l4 "><select id="range1" name="ranges" class="sl2">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
                    <option value="1" selected>模糊</option>
                    <option value="2">精确</option>
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${rangesArray[0] eq '1'}">selected</c:if>>模糊</option>
                    <option value="2" <c:if test="${rangesArray[0] eq '2'}">selected</c:if>>精确</option>
                  </c:otherwise>
                </c:choose>
            </select></li>
          </ul>
        </div>
        <!--搜索第二行-->
        <div id="ssk_1" class="ssk_1">
          <ul>
            <li class="l1">
              <p>并且</p>
            </li>

            <li class="l2"><select id="fields2" name="fields" class="select">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
                    <option value="1">病种名称</option>
                    <option value="2" selected="selected">中文题名</option>
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
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${fieldsArray[1] eq '1'}">selected</c:if>>病种名称</option>
                    <option value="2" <c:if test="${fieldsArray[1] eq '2'}">selected</c:if>>中文题名</option>
                    <option value="3" <c:if test="${fieldsArray[1] eq '3'}">selected</c:if>>英文题名</option>
                    <option value="4" <c:if test="${fieldsArray[1] eq '4'}">selected</c:if>>标准类型</option>
                    <option value="5" <c:if test="${fieldsArray[1] eq '5'}">selected</c:if>>发布机构</option>
                    <option value="6" <c:if test="${fieldsArray[1] eq '6'}">selected</c:if>>来源</option>
                    <option value="7" <c:if test="${fieldsArray[1] eq '7'}">selected</c:if>>全文语种</option>
                    <option value="8" <c:if test="${fieldsArray[1] eq '8'}">selected</c:if>>西医病名</option>
                    <option value="9" <c:if test="${fieldsArray[1] eq '9'}">selected</c:if>>分型分期</option>
                    <option value="10" <c:if test="${fieldsArray[1] eq '10'}">selected</c:if>>西医诊断标准</option>
                    <option value="11" <c:if test="${fieldsArray[1] eq '11'}">selected</c:if>>中医病名</option>
                    <option value="12" <c:if test="${fieldsArray[1] eq '12'}">selected</c:if>>中医辨证</option>
                    <option value="13" <c:if test="${fieldsArray[1] eq '13'}">selected</c:if>>中医诊断标准</option>
                    <option value="14" <c:if test="${fieldsArray[1] eq '14'}">selected</c:if>>疗效指标</option>
                    <option value="15" <c:if test="${fieldsArray[1] eq '15'}">selected</c:if>>疗效判定指标</option>
                    <option value="16" <c:if test="${fieldsArray[1] eq '16'}">selected</c:if>>备注</option>
                  </c:otherwise>
                </c:choose>
            </select></li>
            <li class="l3"><input type="text" name="keywords" value="${keywordsArray[1]}" /></li>
            <li class="l4 "><select id="range2" name="ranges" class="sl2">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
                    <option value="1" selected>模糊</option>
                    <option value="2">精确</option>
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${rangesArray[1] eq '1'}">selected</c:if>>模糊</option>
                    <option value="2" <c:if test="${rangesArray[1] eq '2'}">selected</c:if>>精确</option>
                  </c:otherwise>
                </c:choose>
            </select></li>
          </ul>
        </div>
        <!--搜索第三行-->
        <div id="ssk_1" class="ssk_1">
          <ul>
            <li class="l1">
              <p>并且</p>
            </li>
            <li class="l2"><select id="fields3" name="fields" class="select">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
                    <option value="1">病 种名称</option>
                    <option value="2">中文题名</option>
                    <option value="3" >英文题名</option>
                    <option value="4">标准类型</option>
                    <option value="5" selected="selected">发布机构</option>
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
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${fieldsArray[2] eq '1'}">selected</c:if>>病种名称</option>
                    <option value="2" <c:if test="${fieldsArray[2] eq '2'}">selected</c:if>>中文题名</option>
                    <option value="3" <c:if test="${fieldsArray[2] eq '3'}">selected</c:if>>英文题名</option>
                    <option value="4" <c:if test="${fieldsArray[2] eq '4'}">selected</c:if>>标准类型</option>
                    <option value="5" <c:if test="${fieldsArray[2] eq '5'}">selected</c:if>>发布机构</option>
                    <option value="6" <c:if test="${fieldsArray[2] eq '6'}">selected</c:if>>来源</option>
                    <option value="7" <c:if test="${fieldsArray[2] eq '7'}">selected</c:if>>全文语种</option>
                    <option value="8" <c:if test="${fieldsArray[2] eq '8'}">selected</c:if>>西医病名</option>
                    <option value="9" <c:if test="${fieldsArray[2] eq '9'}">selected</c:if>>分型分期</option>
                    <option value="10" <c:if test="${fieldsArray[2] eq '10'}">selected</c:if>>西医诊断标准</option>
                    <option value="11" <c:if test="${fieldsArray[2] eq '11'}">selected</c:if>>中医病名</option>
                    <option value="12" <c:if test="${fieldsArray[2] eq '12'}">selected</c:if>>中医辨证</option>
                    <option value="13" <c:if test="${fieldsArray[2] eq '13'}">selected</c:if>>中医诊断标准</option>
                    <option value="14" <c:if test="${fieldsArray[2] eq '14'}">selected</c:if>>疗效指标</option>
                    <option value="15" <c:if test="${fieldsArray[2] eq '15'}">selected</c:if>>疗效判定指标</option>
                    <option value="16" <c:if test="${fieldsArray[2] eq '16'}">selected</c:if>>备注</option>
                  </c:otherwise>
                </c:choose>
            </select></li>
            <li class="l3"><input type="text" name="keywords" value="${keywordsArray[2]}" /></li>
            <li class="l4 "><select id="range2" name="ranges" class="sl2">
                <c:choose>
                  <c:when test="${empty fieldsArray}">
                    <option value="1" selected>模糊</option>
                    <option value="2">精确</option>
                  </c:when>
                  <c:otherwise>
                    <option value="1" <c:if test="${rangesArray[2] eq '1'}">selected</c:if>>模糊</option>
                    <option value="2" <c:if test="${rangesArray[2] eq '2'}">selected</c:if>>精确</option>
                  </c:otherwise>
                </c:choose>
            </select></li>
          </ul>
        </div>

        <!--搜索>3的行-->

        <c:if test="${!empty  fieldsArray}">
          <c:forEach items="${fieldsArray}" begin="3" varStatus="varStatus">
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