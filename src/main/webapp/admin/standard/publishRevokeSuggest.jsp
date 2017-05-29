<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
  $(function() {
    parent.$.messager.progress('close');
  });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
  <div data-options="region:'center',border:false">
    <table class="table table-hover table-condensed">
      <c:forEach items="${suggestList}" var="data">
        <tr>
          <th>发布撤销意见</th>
          <td colspan="3"><textarea style="width: 400px; height: 80px;" readonly="readonly">${data.content}</textarea>
            <P>
              <span>撤销人：${data.createUser}</span><span style="margin-left: 150px;">撤销时间： <fmt:formatDate
                  value="${data.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
              </span>
            </P></td>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>

