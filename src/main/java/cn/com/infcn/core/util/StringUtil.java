package cn.com.infcn.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 字符串工具类
 * 
 * @author NOLY DAKE
 * 
 */
public class StringUtil {

    /**
     * 日志对象
     */
    private static final Logger LOG = Logger.getLogger(StringUtil.class);

    /**
     * 生成一个UUID，一般用处是用来标识数据库唯一主键用
     * 
     * @return UUID
     */
    public static String generateUUID() {

        // 防止反编译用
        try {
            if (654789 == new Random().nextInt()) {
                throw new Exception("try again 654789 == new Random().nextInt()");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try { // 防止反编译用
                if (654789 == new Random().nextInt()) {
                    throw new Exception("try again 654789 == new Random().nextInt()");
                }
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
    /**
     * 清除字符串两侧空格
     * 
     * @return boolean
     */
    public static String trim(String str) {
        return (str != null && str.length() != 0) ? str.trim() : "";
    }
    /**
     * 判断字符串是否大于传过来的长度，如果超过则截取
     * 
     * @param str
     * @param len
     * @return
     */
    public static String subString(String str, int len) {
        if (str == null) {
            return null;
        }
        if (str.length() > len) {
            return str.substring(0, len - 2) + "...";
        }
        return str;
    }

    /**
     * 判断字符串是否大于传过来的长度，如果超过则截取
     * 
     * @param str
     * @param len
     * @return
     */
    public static String subString2(String str, int len) {
        if (str == null) {
            return null;
        }
        if (str.length() > len) {
            return str.substring(0, len);
        }
        return str;
    }

    public static String object2String(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    public static Integer object2Integer(Object obj) {
        if (obj != null) {
            try {
                return Integer.parseInt(obj.toString());
            } catch (NumberFormatException e) {
            }
        }
        return 0;
    }

    public static String decodeUTF8(String str) {
        try {
            if (str != null) {
                return URLDecoder.decode(str, "UTF-8");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String encodeUTF8(String str) {
        try {
            if (str != null) {
                return URLEncoder.encode(str, "UTF-8");
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 从summon系统中获取的作者进行切分作者
     * 
     * @param authorStr
     * @return
     */
    public static List<String> getAuthorList(String authorStr) {
        List<String> authorlist = new ArrayList<String>();
        if (authorStr == null) {
            return authorlist;
        }
        
//        //如果包含逗号，则按照逗号进行分割
//        if(authorStr.indexOf(",")!=-1){
//            String[] authors = authorStr.split(",");
//            for (String string : authors) {
//                if (string != null) {
//                    string = string.trim();
//                }
//                authorlist.add(string);
//            }
//        }else{
            //判断是否包含英文字母，如果保护则认为是英文名称
            if(isIncludeLetter(authorStr)){
                authorlist.add(authorStr);
            }else{
                String[] authors = authorStr.split(" ");
                for (String string : authors) {
                    if (string != null) {
                        string = string.trim();
                    }
                    authorlist.add(string);
                }
            }
//        }
        return authorlist;
    }

    public static String getHighlighter(String str, String keyword) {
        if (str != null && keyword != null) {
            str = str.replace(keyword, "<b><font color=\"red\">" + keyword + "</font></b>");
        }
        System.out.println("---------------" + str);
        return str;
    }

    /**
     * 过滤引号
     * 
     * @param str
     * @return
     */
    public static String filterQuotationMark(String str) {
        if (str != null && !str.trim().equals("")) {
            return str.replace("\"", "").replace("\'", "");
        }

        return null;
    }

    public static void main(String[] args) {
    }

    public static boolean isChineseChar(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    public static boolean isIncludeLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) <= 'Z' && str.charAt(i) >= 'A') || (str.charAt(i) <= 'z' && str.charAt(i) >= 'a')) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取日程计划日期
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getPlanDate(int year, int month, int day){
        StringBuffer resultDate = new StringBuffer();
        resultDate.append(year);
        resultDate.append("-");
        if(month<10){
            resultDate.append("0");
        }
        resultDate.append(month);
        resultDate.append("-");
        if(day<10){
            resultDate.append("0");
        }
        resultDate.append(day);
        return resultDate.toString();
    }
    
    public static boolean verrifyInteger(String num){
        try {
            Integer.parseInt(num);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /*
     * 检查是否有乱码，有就转换
     */
    public static String toUTF8(String  str)  { 
       if  (str  ==  null)  return  null; 
       String  retStr  =  str; 
       byte  b[]; 
       try  { 
           b  =  str.getBytes("ISO-8859-1"); 
           for  (int  i  =  0;  i  <  b.length;  i++)  { 
                   byte  b1  =  b[i]; 
                   if  (b1  ==  63) 
                           break;    //1 
                   else  if  (b1  >  0) 
                           continue;//2 
                   else  if  (b1  <  0)  {        //不可能为0，0为字符串结束符 
                           retStr  =  new  String(b,  "UTF8"); 
                           break; 
                   } 
           } 
       }  catch  (UnsupportedEncodingException  e)  { 
       } 
       return  retStr; 
   }
    
}
