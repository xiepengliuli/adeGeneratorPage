/**
 * @author 谢鹏
 * 
 * 扩展字典的选择功能
 * 
 * 使用方法：$.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
$.initDictionary= function(dictionaryTypeId,dictionaryTypeValue,dictionaryValueId,dictionaryValueValue){
 
  var  s_dictionaryTypeId=$("#"+dictionaryTypeId);
  var  s_dictionaryValueId=$("#"+dictionaryValueId);
  s_dictionaryValueId.combobox({
     valueField : 'id',
     textField : 'zdName',
     required : true
   });

  s_dictionaryTypeId.combobox(
       {
         url : "dictionary/dictionaryComboTree",
         valueField : 'id',
         textField : 'text',
         required : true,
         onLoadSuccess : function() {
           s_dictionaryTypeId.combobox("select", dictionaryTypeValue);
         },
         onSelect : function(rec) {
           var url = "dictionary/getDictionaryByType?zdTypeCode=" + rec.type
               + "&t=" + new Date();
           s_dictionaryValueId.combobox("reload", url);
           if (rec.id == dictionaryTypeValue) {
             s_dictionaryValueId.combobox("select", dictionaryValueValue);
           } else {
             s_dictionaryValueId.combobox("setValue", "");
           }
         }
       });
 }

