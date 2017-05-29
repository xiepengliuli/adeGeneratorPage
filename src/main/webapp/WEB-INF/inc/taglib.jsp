<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tools" uri="/WEB-INF/tld/ade-tools-tag.tld"%>
<%@ taglib prefix="string" uri="/WEB-INF/tld/ade-string-tags.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/plugins/jquery.min.js" ></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />


<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/script.js"></script> --%>
<!--[if lte IE 6]>
<script src="${pageContext.request.contextPath}/resources/js/pngImg.js" type="text/javascript"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('div, ul, img, li, input , a');
    </script>
<![endif]-->