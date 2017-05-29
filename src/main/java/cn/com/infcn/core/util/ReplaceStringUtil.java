package cn.com.infcn.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Field的字符创帮助类
 * 
 * @author NOLY DAKE
 * 
 */
public class ReplaceStringUtil {
  
  private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
  private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
  private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
  private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符  
  private static final String regEx_special = "\\&[a-zA-Z]{1,10};";

	/**
	 * 替换高亮标签为空格
	 * @param string
	 * @return
	 */
    public static String replaceHighlighterString(String string) {
        return string.replace("<span class=\"Highlighter\">", "").replace("</span>", "");
    }
    /**
     * 截取限定长度中包含高亮标签的摘要内容
     * @param length
     * @param str
     * @return
     */
    public static String replaceSummary(int length, String str) {
        String ss = "";
        if (str != null) {
            Pattern pattern = Pattern.compile("<span class=\"Highlighter\">");
            int index1 = str.indexOf("<span class=\"Highlighter\">");
            int index2 = str.indexOf("</span>");
            // str中不存在高亮标签
            if (index1 < 1) {
                // 解析的str长度大于如120,截取str0-120，小于120,则截取0,str长度
                if (str.length() > length) {

                    ss = str.substring(0, length);
                } else {
                    ss = str.substring(0, str.length());
                }
                return ss;
            } else {
                String string = str.substring(index1, index2 + 7);
                String string1 = "";
                String string2 = "";
                String string3 = "";
                String string4 = "";
                if (index1 > length / 2) {
                    string1 = str.substring(0, index1);
                } else {
                    int nextLenght = (length / 2 - index1) + index2 + length / 2;
                    string1 = str.substring(0, index1);
                    if (str.length() - index2 > nextLenght) {
                        string2 = str.substring(index2 + 7, nextLenght);
                    } else {
                        string2 = str.substring(index2 + 7, str.length());
                    }
                    Matcher matcher = pattern.matcher(string2);
                    int index3 = string2.lastIndexOf("<s");
                    if (matcher.find() == true) {
                        string3 = string2.substring(0, index3);
                    } else {
                        if (index3 < 1) {
                            string4 = string2;
                        }
                    }
                }
                ss = string1 + string + string3 + string4;
            }
        }
        return ss;
    }
    /**
     * 截取限定长度中包含高亮标签的内容
     * @param str
     * @param length
     * @return
     */
    public static String splitHtmlString(String str, int length) {
    	
    	if(StringUtils.isBlank(str))
    		return str;

		int off = 0;

		String hightStart = "<span class=\"Highlighter\">";

		String hightEnd = "</span>";

		int count = 0;

		while (true) {
			
			if (str.indexOf(hightStart, off) != -1&&
					 str.startsWith(hightStart, off)) {
				off = str.indexOf(hightEnd, off) + hightEnd.length() ;
			} else {
				off++;
			}

			count++;

			if (count >= length || off >= str.length()) {
				break;
			}
		}

		if (off == str.length()) {
			return str.substring(0, off);
		} else {
			return str.substring(0, off) + "...";
		}
	}
    /**
     * @param htmlStr
     * @return 删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
      Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
      Matcher m_script = p_script.matcher(htmlStr);
      htmlStr = m_script.replaceAll(""); // 过滤script标签

      Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
      Matcher m_style = p_style.matcher(htmlStr);
      htmlStr = m_style.replaceAll(""); // 过滤style标签

      Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
      Matcher m_html = p_html.matcher(htmlStr);
      htmlStr = m_html.replaceAll(""); // 过滤html标签

      Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
      Matcher m_space = p_space.matcher(htmlStr);
      htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
      
      Pattern p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
      
      Matcher   m_special = p_special.matcher(htmlStr);
  
      htmlStr = m_special.replaceAll(""); // 过滤特殊标签

      
     /* htmlStr=htmlStr.replaceAll("&nbsp;", "");
      htmlStr=htmlStr.replaceAll("&emsp", "");
      htmlStr=htmlStr.replaceAll("&amp;", "");
      htmlStr=htmlStr.replaceAll("lt;br/", "");
      htmlStr=htmlStr.replaceAll("emsp;", "");
      htmlStr=htmlStr.replaceAll("gt;", "");
      htmlStr=htmlStr.replaceAll("amp;", "");
      htmlStr=htmlStr.replaceAll("&beta;", "");
      htmlStr=htmlStr.replaceAll("&ldquo;", "");*/
      return htmlStr.trim(); // 返回文本字符串
    }

}
