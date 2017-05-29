//根据特定长度获取特定长度加3个点
function getStringContainDot(str, showLen) {
  var strLen = 0;
  var tempValue = "";
  for (var i = 0; i < str.length; i++) {
    if (strLen < showLen) {
      tempValue = tempValue + str.substr(i, 1);
    } else {
      tempValue = tempValue + "...";
      break;
    }
    if (str.charCodeAt(i) > 255) {
      strLen += 2;
    } else {
      strLen++;
    }
  }
  return tempValue;
}
