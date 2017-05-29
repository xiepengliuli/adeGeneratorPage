package cn.com.infcn.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

public class FileUtil {

    /** 日志对象 */
    private static final Logger LOG = Logger.getLogger(FileUtil.class);

    /**
     * 缓存的webapp的目录
     */
    private static String cachedWebAppBasePath = null;

    /**
     * 传入一个文件名，得到这个文件名称的后缀 包含 .
     * 
     * @param fileName
     *            - 文件名
     * @return
     */
    public static String getSuffixContainPoint(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (index != -1) {
            String suffix = fileName.substring(index);// 后缀
            return suffix;
        } else {
            return null;
        }
    }

    /**
     * 传入一个文件名，得到这个文件名称的后缀 不包含 .
     * 
     * @param fileName
     *            - 文件名
     * @return
     */
    public static String getSuffixUnContainPoint(String fileName) {

        int index = fileName.lastIndexOf(".");

        if (index != -1) {
            String suffix = fileName.substring(index + 1);// 后缀

            return suffix.toLowerCase();
        } else {

            return null;
        }
    }

    public static String getFileSize(File fileName) {

        String size;
        BigDecimal bd = new BigDecimal(fileName.length());
        BigDecimal kbd = bd.divide(new BigDecimal(1024), 0, BigDecimal.ROUND_UP);

        if (kbd.compareTo(new BigDecimal(1024)) > 0) {
            BigDecimal mbd = kbd.divide(new BigDecimal(1024), 2, BigDecimal.ROUND_UP);
            size = mbd.toString() + "M";
        } else {
            size = kbd.toString() + "K";
        }

        return size;
    }

    /**
     * 获得项目的路径
     * 
     * @return 项目的路径
     */
    public static String getWebAppBasePath() {

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

        if (cachedWebAppBasePath != null) {
            return cachedWebAppBasePath;
        }

        Class<FileUtil> cls = FileUtil.class;

        ClassLoader loader = cls.getClassLoader();
        // 获得类的全名，包括包名
        String clsName = cls.getName() + ".class";
        // 获得传入参数所在的包
        Package pack = cls.getPackage();
        String path = "";
        // 如果不是匿名包，将包名转化为路径
        if (pack != null) {
            String packName = pack.getName();
            // 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
            if (!packName.startsWith("cn.com.infcn")) {
                throw new java.lang.IllegalArgumentException("类型不支持");
            }
            // 在类的名称中，去掉包名的部分，获得类的文件名
            clsName = clsName.substring(packName.length() + 1);
            // 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
            if (packName.indexOf(".") < 0)
                path = packName + "/";
            else {// 否则按照包名的组成部分，将包名转换为路径
                int start = 0, end = 0;
                end = packName.indexOf(".");
                while (end != -1) {
                    path = path + packName.substring(start, end) + "/";
                    start = end + 1;
                    end = packName.indexOf(".", start);
                }
                path = path + packName.substring(start) + "/";
            }
        }

        // 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
        java.net.URL url = loader.getResource(path + clsName);
        // 从URL对象中获取路径信息

        String realPath = url.getPath();
        // 去掉路径信息中的协议名"file:"
        int pos = realPath.indexOf("file:");
        if (pos > -1)
            realPath = realPath.substring(pos + 5);

        // 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
        pos = realPath.indexOf(path + clsName);
        realPath = realPath.substring(0, pos - 1);

        // 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
        boolean isRar = realPath.endsWith("!");
        if (isRar) {
            realPath = realPath.substring(0, realPath.lastIndexOf("/"));
        }

        // 结果字符串可能因平台默认编码不同而不同。因此，改用 decode(String,String) 方法指定编码。
        try {
            realPath = java.net.URLDecoder.decode(realPath, "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        File file = new File(realPath);

        cachedWebAppBasePath = file.getParentFile().getParentFile().getAbsolutePath();

        return cachedWebAppBasePath;
    }

    public static Set<String> readSystemDics(Set<String> set, InputStream is) {

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

        InputStream in = null;
        Reader reader = null;
        BufferedReader br = null;

        try {
            in = is;
            reader = new InputStreamReader(in, "utf8");
            br = new BufferedReader(reader);
            String tempString = null;

            while ((tempString = br.readLine()) != null) {
                set.add(tempString);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                br.close();
                reader.close();
                in.close();
            } catch (IOException e1) {
                LOG.error(e1.getMessage());
            }
        }
        return set;
    }

    /**
     * 计算一个分割的目录
     * 
     * @param forder
     * @return
     */
    public static File caculateDivisionForder(File forder) {

        // 防止反编译用
        try {
            if (654789 == new Random().nextInt()) {
                throw new Exception("try again 654789 == new Random().nextInt()");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try { // 防止反编译用
                if (654789 == new Random().nextInt()) {
                    throw new Exception("try again 654789 == new Random().nextInt()");
                }
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }

        Calendar calendar = Calendar.getInstance();

        File year = new File(forder, String.valueOf(calendar.get(Calendar.YEAR)));

        File month = new File(year, String.valueOf(calendar.get(Calendar.MONTH) + 1));

        File day = new File(month, String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));

        File hour = new File(day, String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));

        File minute = new File(hour, String.valueOf(calendar.get(Calendar.MINUTE)));

        return minute;
    }
}
