package cn.com.infcn.core.util;

import java.util.regex.Pattern;

public class WipeHtmlStyleUtil {
    /**
     * 去除html标签
     */
    public static String wipeStyle(String value) {
        
        if(value == null){
            return null;
        }

        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(value);
            value = m_script.replaceAll(""); // 过滤script标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(value);
            value = m_style.replaceAll(""); // 过滤style标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(value);
            value = m_html.replaceAll(""); // 过滤html标签
            /* 空格 —— */
            // p_html = Pattern.compile("\\ ", Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(value);
            value = value.replaceAll(" ", " ");
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
