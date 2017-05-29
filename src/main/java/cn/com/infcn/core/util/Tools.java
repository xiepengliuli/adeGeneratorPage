package cn.com.infcn.core.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Tools {

	/**
	 * 获得UUID
	 * @return String
	 * @author caiys.job
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replaceAll("-", "");
	}

	public static String timeToString(Date time, String format) {
		//"yyyy-MM-dd HH:mm:ss"
		java.text.SimpleDateFormat s = new java.text.SimpleDateFormat(format);
		return s.format(time);
	}

	public static String timeToString(Date time) {
		//"yyyy-MM-dd HH:mm:ss"
		java.text.SimpleDateFormat s = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return s.format(time);
	}

	public static String nowToString() {
		//"yyyy-MM-dd HH:mm:ss"
		java.text.SimpleDateFormat s = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return s.format(new java.util.Date());
	}
	
	public static String dayToString() {
		//"yyyy-MM-dd HH:mm:ss"
		java.text.SimpleDateFormat s = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		return s.format(new java.util.Date());
	}
	
	public static Date StringToDate(String dateStr,String formatStr){
		DateFormat dd=new SimpleDateFormat(formatStr);
		Date date=null;
		try {
			date = dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	  * 将日期格式的字符串转换为长整型
	  * 
	  * @param date
	  * @param format
	  * @return
	 * @throws ParseException 
	  */
	 public static long convert2long(String date, String format) throws ParseException {
		    SimpleDateFormat sf = new SimpleDateFormat(format);
		    return sf.parse(date).getTime();
	 }
	
	public static String long2String(long time) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return sf.format(date);
	}

	public static String convert2String(String time) {
		long ltime = Long.parseLong(time);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(ltime);
		return sf.format(date);
	}

	/**
	 * 根据文件获得字节数组
	 * @param f 文件
	 * @return  byte[] 
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}

		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);

			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			stream.close();
			out.close();

			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * MD5 加密方法
	 * @param password 需要加密字符串
	 * @return MD5值
	 */
	public static String makePassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
			byte[] bytes = password.getBytes();
			bytes = md.digest(bytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < 16; i++) {
				byteHexAppend(sb, bytes[i]);
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    public static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
  
    } 

	private static void byteHexAppend(StringBuffer sb, byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		sb.append(Digit[(ib >>> 4) & 0X0F]);
		sb.append(Digit[ib & 0X0F]);
	}
	
	/**
	 * 符号全角转半角
	 * @param src
	 * @return
	 */
	public static String SBC2DBC(String src) {
        if (src == null) {
            return src;
        }
         final char SBC_CHAR_START = 65281; // 全角！
         final char SBC_CHAR_END   = 65374; // 全角～
         final int  CONVERT_STEP   = 65248; // 全角半角转换间隔
         final char SBC_SPACE      = 12288; // 全角空格 12288
         final char DBC_SPACE      = ' ';  // 半角空格
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }
	
	// 上传文件重名命
	public static String getNewFileName() {
	    String fileName = "";
	    fileName = getUUID();
	    fileName = fileName.replaceAll("-", "");
        return fileName;
    }
	
	// 获取后缀名
	public static String getExpValue(String fileName) {
	    return fileName.substring(fileName.indexOf("."));
	}
	
	//替换字符串中的html标签
	public static String replaceHtml(String value) {
		value = value.replaceAll("<p.*?>", "");
		value = value.replaceAll("<brs*/?>", "");
		value = value.replaceAll("<.*?>", "");
		return value;
	}
	
	//金额格式化
	public static String insertComma(String s, int len) {
	    if (s == null || s.length() < 1) {
	        return "";
	    }
	    NumberFormat formater = null;
	    double num = Double.parseDouble(s);
	    if (len == 0) {
	        formater = new DecimalFormat("###,###");
	 
	    } else {
	        StringBuffer buff = new StringBuffer();
	        buff.append("###,###.");
	        for (int i = 0; i < len; i++) {
	            buff.append("#");
	        }
	        formater = new DecimalFormat(buff.toString());
	    }
	    return formater.format(num);
	}
	
	public static String next(String a){
        // String[] data = a.split("-");
		  String mark = a.split("-")[0];
		  int upData = Integer.parseInt(a.split("-")[1]);
		  upData++;
		  DecimalFormat df = new DecimalFormat("000000");
		  return mark+"-"+df.format(upData);
	}
	
	//金额去掉“,”
	public static String delComma(String s) {
	    String formatString = "";
	    if (s != null && s.length() >= 1) {
	        formatString = s.replaceAll(",", "");
	    }
	 
	    return formatString;
	}
	
	public static void main(String[] args) {
		
		System.out.println(insertComma("50000000",2));
		
	}
	
	/**
	 * 生成lenght位随机数S
	 * @param lenght
	 * @return
	 */ 
	public static String getStockCode(int lenght){
		String[] randomValues = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","U",
			"T","S","O","X","V","P","Q","R","W","Y","Z"};
		String[] numberValues = new String[]{"0","1","2","3","4","5","6","7","8","9"};
		StringBuffer str = new StringBuffer();
		for(int i = 0;i < lenght; i++){ 
			if(i == 0){
				Double number = Math.random()*(randomValues.length-1);
				str.append(randomValues[number.intValue()]);
			}else{
				Double number = Math.random()*(numberValues.length-1);
				str.append(numberValues[number.intValue()]);
			}
		}
		return str.toString();
	}
	
	/**
	 * 格式化数字（去除.00/.0）
	 * @return
	 */
	public static int formatDigital(String dig){
		
		int digi = 0;
		if(dig.indexOf(".") != -1){
			digi = Integer.parseInt(dig.replace(".00", "").replace(".0", ""));
		} else {
			digi = Integer.parseInt(dig);
		}
		
		return digi;
	}
	
}
	
