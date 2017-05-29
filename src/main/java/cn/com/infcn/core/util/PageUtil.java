package cn.com.infcn.core.util;

public class PageUtil {

    /**
     * 结束页数
     * 
     * @param page
     * @param maxPage
     * @return
     */
    public static int caculateEndPage(int page, int maxPage) {

        if (page >= maxPage) {
            return page;
        }

        int afterSize = 3;

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
     * 计算开始页数
     * 
     * @param page
     * @return
     */
    public static int caculateStartPage(int page) {

        if (page == 1) {
            return page;
        }

        int beforeSize = 2;

        for (int i = 0; i < beforeSize; i++) {

            page--;

            if (page == 1) {
                break;
            }
        }

        return page;
    }

    /**
     * 计算总页数
     * 
     * @param totalNo
     * @param onePageNo
     * @return
     */
    public static int caculatePageCount(Integer totalNo, Integer onePageNo) {

        return totalNo % onePageNo > 0 ? totalNo / onePageNo + 1 : totalNo / onePageNo;
    }
}
