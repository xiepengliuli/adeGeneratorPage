package cn.com.infcn.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * 文件下载帮助类
 * 
 * @author hxl
 * 
 */
public class FileDownloadUtil {

  /**
   * 文件下载
   * 
   * @param request
   * @param response
   * @param downloadRealPath
   * @param sourceFileName
   * @return
   * @throws Exception
   */
  public static String download(HttpServletRequest request, HttpServletResponse response, String downloadRealPath,
      String sourceFileName) throws Exception {
    String path = request.getRealPath(downloadRealPath);
    downloadRealPath = path;
    File file = new File(downloadRealPath);
    if (!file.exists()) {
      return "/error/404";
    }
    String fileName = "";
    if (StringUtils.isNotBlank(sourceFileName)) {
      fileName = sourceFileName;
    } else {
      fileName = downloadRealPath.substring(downloadRealPath.lastIndexOf("\\") + 1);
    }
    try {
      response.setHeader("content-disposition", "attachment;filename=" +new String(fileName.getBytes("gbk"),"iso-8859-1"));
      // 读取要下载的文件,保存到文件输入流
      FileInputStream in = new FileInputStream(file);
      // 创建输出流
      OutputStream out = response.getOutputStream();
      // 创建缓冲区
      byte[] buffer = new byte[1024];
      int len = 0;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
      // 关闭流
      in.close();
      out.close();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
