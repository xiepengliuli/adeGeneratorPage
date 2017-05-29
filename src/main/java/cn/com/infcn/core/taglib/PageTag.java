package cn.com.infcn.core.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import cn.com.infcn.core.pageModel.PageHelper;

public class PageTag extends TagSupport {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2450648770592274870L;

    /**
     * 输入项目:要访问的地址<br>
     * 必须输入项目
     */
    private String url = null;

    /**
     * 输入项目:要访问的地址中是否包含参数<br>
     * 非必须输入项目<br>
     * 如果不输入这个项目，应用会判断url中是否包含'?'来判断是否包含参数
     */
    private String urlHasParams = null;

    /**
     * 输入项目:分页信息<br>
     * 必须输入项目
     */
    private PageHelper pageHelper = null;

    public PageHelper getPageHelper() {
        return pageHelper;
    }

    public void setPageHelper(PageHelper pageHelper) {
        this.pageHelper = pageHelper;
    }

    /**
     * 输入项目:当前页码前显示页码个数<br>
     * 非必须输入项目<br>
     * 默认值为3
     */
    private int beforeSize = 3;

    /**
     * 输入项目:当前页码后显示页码个数<br>
     * 非必须输入项目<br>
     * 默认值为4
     */
    private int afterSize = 4;

    /**
     * URL中是否包含参数<br>
     * 非输入项目，设置为成员变量，避免重复输入
     */
    private boolean hasUrlContaintsParams = false;

    /**
     * 结束页数<br>
     * 注意：页码是从1开始的。
     * 
     * @param page
     *            当前的页码。
     * @param maxPage
     *            最大页数
     * @return 结束页数
     */
    private int caculateEndPage(int page, int maxPage) {

        if (page >= maxPage) {
            return page;
        }

        int tmp = 0;
        for (int i = 0; i < afterSize; i++) {
            tmp = page + i + 1;
            if (tmp == maxPage) {
                break;
            }
        }
        return tmp;
    }

    /**
     * 开始页数<br>
     * 注意：页码是从1开始的。
     * 
     * @param page
     *            当前的页码。
     * @return 开始页数
     */
    private int caculateStartPage(int page) {

        if (page != 1) {

            for (int i = 0; i < beforeSize; i++) {
                page--;
                if (page == 1) {
                    break;
                }
            }
        }
        return page;
    }

    @Override
    public int doEndTag() throws JspException {

        // -------------------------------------------------------初始化开始
        if (StringUtils.isEmpty(urlHasParams)) {
            hasUrlContaintsParams = (url.indexOf('?') != -1);
        } else {
            hasUrlContaintsParams = Boolean.parseBoolean(urlHasParams);
        }

        // 总条数
        int totalInt = pageHelper.getTotal();
        // 每页数据量
        int pageSizeInt = pageHelper.getRows();
        // 页码
        int pageNoInt = pageHelper.getPage();
        // 总页数
        int totalPageInt = pageSizeInt == 0 ? 0 : (totalInt + pageSizeInt - 1) / pageSizeInt;

        int beforeInt = caculateStartPage(pageNoInt);
        int afterInt = caculateEndPage(pageNoInt, totalPageInt);

        // -------------------------------------------------------初始化结束

        StringBuffer sb = new StringBuffer();

        // 页码信息
        sb.append("共&nbsp;");
        sb.append(totalInt);
        sb.append("条&nbsp;");
        sb.append(totalPageInt);
        sb.append("页");

        // 首页
        sb.append(mkLink("首页", 1));
        // 上一页
        if (pageNoInt != 1) {
            sb.append(mkLink("&lt;", pageNoInt - 1));
        }
        // 页码
        for (int i = beforeInt; i <= afterInt; i++) {
            sb.append(mkLink(String.valueOf(i), i, i != pageNoInt));
        }

        // 下一页
        if (pageNoInt < totalPageInt) {
            sb.append(mkLink("&gt;", pageNoInt + 1));
        }

        // 尾页
        sb.append(mkLink("尾页", totalPageInt));


        JspWriter writer = null;

        try {
            writer = pageContext.getOut();
            writer.write(sb.toString());
            writer.flush();
        } catch (IOException e) {
            throw new JspException(e);
        }

        return TagSupport.EVAL_PAGE;
    }

    public int getAfterSize() {
        return afterSize;
    }

    public int getBeforeSize() {
        return beforeSize;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlHasParams() {
        return urlHasParams;
    }

    private String mkLink(String text, int pageNo, boolean mkLink) {

        StringBuffer sb = new StringBuffer();

        if (mkLink) {
            sb.append("&nbsp;<a href=\"");
            sb.append(url);
            // 准备分页信息
            if (hasUrlContaintsParams) {
                sb.append("&page=" + pageNo);
            } else {
                sb.append("?page=" + pageNo);
            }
            sb.append("\">");
            sb.append(text);
            sb.append("</a>");
        } else {

            sb.append("&nbsp;<a href=\"#\" class=\"fenyOn\">");
            sb.append(text);
            sb.append("</a>");
        }

        return sb.toString();
    }

    private String mkLink(String text, int pageNo) {
        return mkLink(text, pageNo, true);
    }

    public void setAfterSize(int afterSize) {
        this.afterSize = afterSize;
    }

    public void setBeforeSize(int beforeSize) {
        this.beforeSize = beforeSize;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlHasParams(String urlHasParams) {
        this.urlHasParams = urlHasParams;
    }
}
