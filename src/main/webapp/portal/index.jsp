<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/portal/angular.min.js"></script>
</head>
<body>
<div ng-app="myApp" ng-controller="siteCtrl"> 
<ul>
  <li ng-repeat="x in names">
    {{ x.userName + ',  ' + x.loginName}}
  </li>
</ul>

</div>

<script>
var app = angular.module('myApp', []);
app.controller('siteCtrl', function($scope, $http) {
  var temp={msg:'hello word!'};
  $http.post('${pageContext.request.contextPath}/portal/angular/dataGrid?rows=5', temp).
  success(function(data) {
    $scope.names=data.rows;
  });
  
});
</script>
</body>
</html>