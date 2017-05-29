package cn.com.infcn.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import cn.com.infcn.core.util.WipeHtmlStyleUtil;

/**
 * 字符串截取标签
 */
public class StringTag extends TagSupport {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3210930182023680187L;

    /**
     * 要转换的字符串对象
     */
    private String value = null;

    /**
     * 要截取的长度
     */
    private int length = -1;

    /**
     * 字符串长度超出指定的长度后，补充的字符串，默认值"…"
     */
    private String overflowString = "…";

    /**
     * 是否去除html标签
     */
    private String wipeHtmlStyle = null;

    public String getWipeHtmlStyle() {
        return wipeHtmlStyle;
    }

    public void setWipeHtmlStyle(String wipeHtmlStyle) {
        this.wipeHtmlStyle = wipeHtmlStyle;
    }

    public String getOverflowString() {
        return overflowString;
    }

    public void setOverflowString(String overflowString) {
        this.overflowString = overflowString;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int doEndTag() throws JspException {

        if (StringUtils.isNotEmpty(value)) {

            if ("true".equalsIgnoreCase(wipeHtmlStyle)) {
                value = WipeHtmlStyleUtil.wipeStyle(value);
            }

            // 如果需要截取长度
            if (length != -1 && value.length() > length + 1) {
                value = value.substring(0, length) + overflowString;
            }

            JspWriter writer = null;

            try {
                writer = pageContext.getOut();
                writer.write(value);
                writer.flush();
            } catch (IOException e) {
                throw new JspException(e);
            }
        }

        return TagSupport.EVAL_PAGE;
    }
}
