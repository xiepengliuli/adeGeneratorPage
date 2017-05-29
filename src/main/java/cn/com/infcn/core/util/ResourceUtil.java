package cn.com.infcn.core.util;

import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;


/**
 * 项目参数工具类
 * 
 * @author adminstrator
 * 
 */
public class ResourceUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getUkeySessionInfoName() {
		return bundle.getString("sessioUkeyInfoName");
	}

	/**
	 * 获取配置文件对应的键值
	 * 
	 * @return
	 */
	public static final String getMessageByConfig(String param) {
		return bundle.getString(param);
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getImagePath(String str) {
        return getMessageByConfig(str);
    }
	
	public static String getImagePath() {
        return getImagePath("imagePath");
    }

	/**
	 * 获得上传表单域的名称
	 * 
	 * @return
	 */
	public static final String getUploadFieldName() {
		return bundle.getString("uploadFieldName");
	}

	/**
	 * 获得上传文件的最大大小限制
	 * 
	 * @return
	 */
	public static final long getUploadFileMaxSize() {
		return Long.valueOf(bundle.getString("uploadFileMaxSize"));
	}

	/**
	 * 获得允许上传文件的扩展名
	 * 
	 * @return
	 */
	public static final String getUploadFileExts() {
		return bundle.getString("uploadFileExts");
	}

	/**
	 * 获得上传文件要放到那个目录
	 * 
	 * @return
	 */
	public static final String getUploadDirectory() {
		return bundle.getString("uploadDirectory");
	}

	/**
	 * 转换字符串编码,默认UTF-8
	 * 
	 * @param str
	 * @return
	 */
	public static String getCharSetEncodeStr(String str) {
		return getCharSetEncodeStr(str, "UTF-8");
	}

	/**
	 * 转换字符串编码
	 * 
	 * @param str
	 * @param code
	 * @return
	 */
	public static String getCharSetEncodeStr(String str, String code) {
		try {
			return new String(str.getBytes("ISO-8859-1"), code);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 获得配置文件信息根据key值
	 * 
	 * @param key
	 * @return
	 */
	public static final String getConfigValueByKey(String key) {
		return bundle.getString(key);
	}

}
