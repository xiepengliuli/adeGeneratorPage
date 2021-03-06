/**
 * 包含easyui的扩展和常用的方法
 * 
 * @author adminstrator
 * 
 * @version 20120806
 */
$(function() {
  document.body.onkeydown = function(event) {
    var eve = document.all ? window.event : event;
    if (eve.keyCode == 13) {
      return;
    }
  };
});
var ade = $.extend({}, ade);/* 定义全局对象，类似于命名空间或包的作用 */

/**
 * 
 * 取消easyui默认开启的parser
 * 
 * 在页面加载之前，先开启一个进度条
 * 
 * 然后在页面所有easyui组件渲染完毕后，关闭进度条
 * 
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.parser.auto = false;
$(function() {
  $.messager.progress({
    text : '页面加载中....',
    interval : 100
  });
  $.parser.parse(window.document);
  window.setTimeout(function() {
    $.messager.progress('close');
    if (self != parent) {
      window.setTimeout(function() {
        try {
          parent.$.messager.progress('close');
        } catch (e) {
        }
      }, 500);
    }
  }, 1);
  $.parser.auto = true;
});

/**
 * 使panel和datagrid在加载时提示
 * 
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 避免验证tip屏幕跑偏
 */
var removeEasyuiTipFunction = function() {
  window.setTimeout(function() {
    $('div.validatebox-tip').remove();
  }, 0);
};
$.fn.panel.defaults.onClose = removeEasyuiTipFunction;
$.fn.window.defaults.onClose = removeEasyuiTipFunction;
$.fn.dialog.defaults.onClose = removeEasyuiTipFunction;

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 通用错误提示
 * 
 * 用于datagrid/treegrid/tree/combogrid/combobox/form加载数据出错时的操作
 */
var easyuiErrorFunction = function(XMLHttpRequest) {
  $.messager.progress('close');
  $.messager.alert('错误', XMLHttpRequest.responseText);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.tree.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 为datagrid、treegrid增加表头菜单，用于显示或隐藏列，注意：冻结列不在此菜单中
 */
var createGridHeaderContextMenu = function(e, field) {
  e.preventDefault();
  var grid = $(this);/* grid本身 */
  var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
  if (!headerContextMenu) {
    var tmenu = $('<div style="width:100px;"></div>').appendTo('body');
    var fields = grid.datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
      var fildOption = grid.datagrid('getColumnOption', fields[i]);
      if (!fildOption.hidden) {
        $('<div iconCls="icon-ok" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(
            tmenu);
      } else {
        $('<div iconCls="icon-empty" field="' + fields[i] + '"/>').html(fildOption.title).appendTo(
            tmenu);
      }
    }
    headerContextMenu = this.headerContextMenu = tmenu.menu({
      onClick : function(item) {
        var field = $(item.target).attr('field');
        if (item.iconCls == 'icon-ok') {
          grid.datagrid('hideColumn', field);
          $(this).menu('setIcon', {
            target : item.target,
            iconCls : 'icon-empty'
          });
        } else {
          grid.datagrid('showColumn', field);
          $(this).menu('setIcon', {
            target : item.target,
            iconCls : 'icon-ok'
          });
        }
      }
    });
  }
  headerContextMenu.menu('show', {
    left : e.pageX,
    top : e.pageY
  });
};
$.fn.datagrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;
$.fn.treegrid.defaults.onHeaderContextMenu = createGridHeaderContextMenu;

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 防止panel/window/dialog组件超出浏览器边界
 * @param left
 * @param top
 */
var easyuiPanelOnMove = function(left, top) {
  var l = left;
  var t = top;
  if (l < 1) {
    l = 1;
  }
  if (t < 1) {
    t = 1;
  }
  var width = parseInt($(this).parent().css('width')) + 14;
  var height = parseInt($(this).parent().css('height')) + 14;
  var right = l + width;
  var buttom = t + height;
  var browserWidth = $(window).width();
  var browserHeight = $(window).height();
  if (right > browserWidth) {
    l = browserWidth - width;
  }
  if (buttom > browserHeight) {
    t = browserHeight - height;
  }
  $(this).parent().css({/* 修正面板位置 */
    left : l,
    top : t
  });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * panel关闭时回收内存
 */
$.fn.panel.defaults.onBeforeDestroy = function() {
  var frame = $('iframe', this);
  try {
    if (frame.length > 0) {
      for (var i = 0; i < frame.length; i++) {
        frame[i].contentWindow.document.write('');
        frame[i].contentWindow.close();
      }
      frame.remove();
      if ($.browser.msie) {
        CollectGarbage();
      }
    }
  } catch (e) {
  }
};

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证两次密码功能
 */
$.extend($.fn.validatebox.defaults.rules, {
  eqPassword : {
    validator : function(value, param) {
      return value == $(param[0]).val();
    },
    message : '两次输入密码不一致！'
  }
});

/**
 * @author dingml
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展validatebox，添加验证修改时密码不能相同
 */
$.extend($.fn.validatebox.defaults.rules, {
  uneqPassword : {
    validator : function(value, param) {
      return value != $(param[0]).val();
    },
    message : '不能与原密码相同！'
  }
});

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展datagrid，添加动态增加或删除Editor的方法
 * 
 * 例子如下，第二个参数可以是数组
 * 
 * datagrid.datagrid('removeEditor', 'cpwd');
 * 
 * datagrid.datagrid('addEditor', [ { field : 'ccreatedatetime', editor : { type :
 * 'datetimebox', options : { editable : false } } }, { field :
 * 'cmodifydatetime', editor : { type : 'datetimebox', options : { editable :
 * false } } } ]);
 * 
 */
$.extend($.fn.datagrid.methods, {
  addEditor : function(jq, param) {
    if (param instanceof Array) {
      $.each(param, function(index, item) {
        var e = $(jq).datagrid('getColumnOption', item.field);
        e.editor = item.editor;
      });
    } else {
      var e = $(jq).datagrid('getColumnOption', param.field);
      e.editor = param.editor;
    }
  },
  removeEditor : function(jq, param) {
    if (param instanceof Array) {
      $.each(param, function(index, item) {
        var e = $(jq).datagrid('getColumnOption', item);
        e.editor = {};
      });
    } else {
      var e = $(jq).datagrid('getColumnOption', param);
      e.editor = {};
    }
  }
});

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * 扩展datagrid的editor
 * 
 * 增加带复选框的下拉树
 * 
 * 增加日期时间组件editor
 * 
 * 增加多选combobox组件
 */
$.extend($.fn.datagrid.defaults.editors, {
  combocheckboxtree : {
    init : function(container, options) {
      var editor = $('<input />').appendTo(container);
      options.multiple = true;
      editor.combotree(options);
      return editor;
    },
    destroy : function(target) {
      $(target).combotree('destroy');
    },
    getValue : function(target) {
      return $(target).combotree('getValues').join(',');
    },
    setValue : function(target, value) {
      $(target).combotree('setValues', ade.getList(value));
    },
    resize : function(target, width) {
      $(target).combotree('resize', width);
    }
  },
  datetimebox : {
    init : function(container, options) {
      var editor = $('<input />').appendTo(container);
      editor.datetimebox(options);
      return editor;
    },
    destroy : function(target) {
      $(target).datetimebox('destroy');
    },
    getValue : function(target) {
      return $(target).datetimebox('getValue');
    },
    setValue : function(target, value) {
      $(target).datetimebox('setValue', value);
    },
    resize : function(target, width) {
      $(target).datetimebox('resize', width);
    }
  },
  multiplecombobox : {
    init : function(container, options) {
      var editor = $('<input />').appendTo(container);
      options.multiple = true;
      editor.combobox(options);
      return editor;
    },
    destroy : function(target) {
      $(target).combobox('destroy');
    },
    getValue : function(target) {
      return $(target).combobox('getValues').join(',');
    },
    setValue : function(target, value) {
      $(target).combobox('setValues', ade.getList(value));
    },
    resize : function(target, width) {
      $(target).combobox('resize', width);
    }
  }
});

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * @param options
 */
ade.dialog = function(options) {
  var opts = $.extend({
    modal : true,
    onClose : function() {
      $(this).dialog('destroy');
    }
  }, options);
  return $('<div/>').dialog(opts);
};

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 * 
 * @param title
 *          标题
 * 
 * @param msg
 *          提示信息
 * 
 * @param fun
 *          回调方法
 */
ade.messagerConfirm = function(title, msg, fn) {
  return $.messager.confirm(title, msg, fn);
};

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 */
ade.messagerShow = function(options) {
  return $.messager.show(options);
};

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI
 */
ade.messagerAlert = function(title, msg, icon, fn) {
  return $.messager.alert(title, msg, icon, fn);
};

/**
 * @author adminstrator
 * 
 * @requires jQuery,EasyUI,jQuery cookie plugin
 * 
 * 更换EasyUI主题的方法
 * 
 * @param themeName
 *          主题名称
 */
ade.changeTheme = function(themeName) {
  var $easyuiTheme = $('#easyuiTheme');
  var url = $easyuiTheme.attr('href');
  var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
  $easyuiTheme.attr('href', href);

  var $iframe = $('iframe');
  if ($iframe.length > 0) {
    for (var i = 0; i < $iframe.length; i++) {
      var ifr = $iframe[i];
      $(ifr).contents().find('#easyuiTheme').attr('href', href);
    }
  }

  $.cookie('easyuiThemeName', themeName, {
    expires : 7
  });
};

/**
 * @author adminstrator
 * 
 * 获得项目根路径
 * 
 * 使用方法：ade.bp();
 * 
 * @returns 项目根路径
 */
ade.bp = function() {
  var curWwwPath = window.document.location.href;
  var pathName = window.document.location.pathname;
  var pos = curWwwPath.indexOf(pathName);
  var localhostPaht = curWwwPath.substring(0, pos);
  return (localhostPaht + codeBase);
};

/**
 * @author adminstrator
 * 
 * 使用方法:ade.pn();
 * 
 * @returns 项目名称(/sshe)
 */
ade.pn = function() {
  return window.document.location.pathname.substring(0, window.document.location.pathname.indexOf(
      '\/', 1));
};

/**
 * @author adminstrator
 * 
 * 增加formatString功能
 * 
 * 使用方法：ade.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
ade.fs = function(str) {
  for (var i = 0; i < arguments.length - 1; i++) {
    str = str.replace("{" + i + "}", arguments[i + 1]);
  }
  return str;
};

/**
 * @author adminstrator
 * 
 * 增加命名空间功能
 * 
 * 使用方法：ade.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
ade.ns = function() {
  var o = {}, d;
  for (var i = 0; i < arguments.length; i++) {
    d = arguments[i].split(".");
    o = window[d[0]] = window[d[0]] || {};
    for (var k = 0; k < d.slice(1).length; k++) {
      o = o[d[k + 1]] = o[d[k + 1]] || {};
    }
  }
  return o;
};

/**
 * @author 郭华(夏悸)
 * 
 * 生成UUID
 * 
 * @returns UUID字符串
 */
ade.random4 = function() {
  return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
ade.UUID = function() {
  return (ade.random4() + ade.random4() + "-" + ade.random4() + "-" + ade.random4() + "-"
      + ade.random4() + "-" + ade.random4() + ade.random4() + ade.random4());
};

/**
 * @author adminstrator
 * 
 * 获得URL参数
 * 
 * @returns 对应名称的值
 */
ade.getUrlParam = function(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
  var r = window.location.search.substr(1).match(reg);
  if (r != null)
    return unescape(r[2]);
  return null;
};

/**
 * @author adminstrator
 * 
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 * 
 * @returns list
 */
ade.getList = function(value) {
  if (value != undefined && value != '') {
    var values = [];
    var t = value.split(',');
    for (var i = 0; i < t.length; i++) {
      values.push('' + t[i]);/* 避免他将ID当成数字 */
    }
    return values;
  } else {
    return [];
  }
};

/**
 * @author adminstrator
 * 
 * @requires jQuery
 * 
 * 判断浏览器是否是IE并且版本小于8
 * 
 * @returns true/false
 */
ade.isLessThanIe8 = function() {
  return ($.browser.msie && $.browser.version < 8);
};

/**
 * @author dingml 增加是否为空值的判断 2014/2/17
 * 
 * @requires jQuery
 * 
 * 将form表单元素的值序列化成对象
 * 
 * @returns object
 */
ade.serializeObject = function(form) {
  var o = {};
  $.each(form.serializeArray(), function(index) {
    if (this['value']) {
      if (o[this['name']]) {
        o[this['name']] = o[this['name']] + "," + this['value'];
      } else {
        o[this['name']] = this['value'];
      }
    }
  });
  return o;
};

/**
 * 
 * 将JSON对象转换成字符串
 * 
 * @param o
 * @returns string
 */
ade.jsonToString = function(o) {
  var r = [];
  if (typeof o == "string")
    return "\""
        + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(
            /(\t)/g, "\\t") + "\"";
  if (typeof o == "object") {
    if (!o.sort) {
      for ( var i in o)
        r.push(i + ":" + obj2str(o[i]));
      if (!!document.all
          && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
              .test(o.toString)) {
        r.push("toString:" + o.toString.toString());
      }
      r = "{" + r.join() + "}";
    } else {
      for (var i = 0; i < o.length; i++)
        r.push(obj2str(o[i]));
      r = "[" + r.join() + "]";
    }
    return r;
  }
  return o.toString();
};

/**
 * @author 郭华(夏悸)
 * 
 * 格式化日期时间
 * 
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
  if (isNaN(this.getMonth())) {
    return '';
  }
  if (!format) {
    format = "yyyy-MM-dd hh:mm:ss";
  }
  var o = {
    /* month */
    "M+" : this.getMonth() + 1,
    /* day */
    "d+" : this.getDate(),
    /* hour */
    "h+" : this.getHours(),
    /* minute */
    "m+" : this.getMinutes(),
    /* second */
    "s+" : this.getSeconds(),
    /* quarter */
    "q+" : Math.floor((this.getMonth() + 3) / 3),
    /* millisecond */
    "S" : this.getMilliseconds()
  };
  if (/(y+)/.test(format)) {
    format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  for ( var k in o) {
    if (new RegExp("(" + k + ")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
          .substr(("" + o[k]).length));
    }
  }
  return format;
};

/**
 * @author adminstrator
 * 
 * @requires jQuery
 * 
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
  type : 'POST',
  error : function(XMLHttpRequest, textStatus, errorThrown) {
    $.messager.progress('close');
    $.messager.alert('错误', XMLHttpRequest.responseText);
  }
});

/**
 * @author dingml
 * 
 * @requires easyui-validatebox data-options 验证项增加 maxLength[INT]
 * 
 * 最大长度限制
 */
$.extend($.fn.validatebox.defaults.rules, {
  maxLength : {
    validator : function(value, param) {
      return param[0] >= $.trim(value).length;
    },
    message : '最大可输入{0}位字符.'
  }
});

$.extend($.fn.validatebox.defaults.rules, {
  fixedLength : {
    validator : function(value, param) {
      return param[0] == $.trim(value).length;
    },
    message : '只能输入{0}位字符.'
  }
});

/**
 * @author dingml
 * 
 * @requires easyui-validatebox data-options 验证项增加 查看是否是ip
 * 
 * 正则表达式验证是否是ip
 */
$.extend($.fn.validatebox.defaults.rules, {
  isIP : {
    validator : function(value) {
      return /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value);
    },
    message : '请输入正确的IP地址！'
  }
});
/**
 * @author dingml
 * 
 * @requires easyui-validatebox data-options 验证项增加 查看是否是QQ号
 * 
 * 正则表达式验证是否是QQ号
 */
$.extend($.fn.validatebox.defaults.rules, {
  isQQ : {
    validator : function(value) {
      return /^[0-9]{5,17}$/.test(value);
    },
    message : '请输入正确的QQ号码！'
  }
});

$.extend($.fn.validatebox.defaults.rules, {
  isN : {
    validator : function(value) {
      return /^[0-9]{1,8}$/.test(value);
    },
    message : '请输入1-8位的数字！'
  }
});
/**
 * @author dingml
 * 
 * @requires easyui-validatebox data-options 验证项增加 查看是否是手机号
 * 
 * 正则表达式验证是否是手机号
 */
$.extend($.fn.validatebox.defaults.rules, {
  isMobilePhone : {
    validator : function(value) {
      return /^[0-9]{11}$/.test(value);
    },
    message : '请输入正确的手机号码！'
  }
});

/**
 * @author yangb
 * 
 * @requires easyui-validatebox data-options 验证项增加 查看是否是电话号
 * 
 * 正则表达式验证是否是电话号
 */
$.extend($.fn.validatebox.defaults.rules, {
  isTelePhone : {
    validator : function(value) {
      return /^\d{3}-\d{8}|\d{4}-\d{7}$/.test(value);
    },
    message : '请输入正确的电话号码！'
  }
});
$.extend($.fn.validatebox.defaults.rules, {
  notnull : {
    validator : function(value) {
      return $.trim(value);
    },
    message : '不能保存空字符串'
  }
});

/**
 * @author dingml
 * 
 * @requires 清除全选状态
 * 
 */
ade.loadDatagrid = function(datagridId) {
  $('#' + datagridId).datagrid('reload');
  $('#' + datagridId).datagrid('unselectAll').datagrid('clearSelections');
  $('#' + datagridId).parent().find("div .datagrid-header-check")
      .children("input[type='checkbox']").eq(0).attr("checked", false);
};
/**
 * @author dingml
 * 
 * @requires 去除空格
 * 
 */
ade.trim = function(inputId) {
  $('#' + inputId).val($.trim($('#' + inputId).val()));
};

//xp......扩展的方法
/**
 * 根据特定长度获取特定长度加3个点
 * 
 * @param str
 * @param showLen
 * @returns {String}
 */
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

/**
 * 获取字符串的字节长度
 * 
 * @param str
 * @returns {Number}
 */
function getStringLengthByByte(str) {
  var strLen = 0;
  for (var i = 0; i < str.length; i++) {
    if (str.charCodeAt(i) > 255) {
      strLen += 2;
    } else {
      strLen++;
    }
  }
  return strLen;
}
