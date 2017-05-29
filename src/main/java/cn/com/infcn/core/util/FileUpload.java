package cn.com.infcn.core.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.UploadFileModel;

/**
 * @ClassName: FileUpload
 * @Description: 文件上传帮助类(springMVC )
 * 
 * @date 2016年3月23日 上午10:30:17
 */
public class FileUpload {

    public static List<UploadFileModel> upload(MultipartHttpServletRequest request) {

        List<UploadFileModel> uploadList = new ArrayList<UploadFileModel>();

        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String tempFileName_form = (String) fileNames.next();
            MultipartFile file = request.getFile(tempFileName_form);
            if (!file.isEmpty()) {
                String imagePath = ResourceUtil.getImagePath();
                String path = request.getRealPath(imagePath); // 获取本地存储路径
                String fileName_old = file.getOriginalFilename();
                String fileType = fileName_old.substring(fileName_old.lastIndexOf("."));
                String tempFileName_new = new Date().getTime() + fileType;
                File file2 = new File(path, tempFileName_new); // 新建一个文件
                try {
                    CommonsMultipartFile CommonsMultipartFile = (CommonsMultipartFile) file;
                    CommonsMultipartFile.getFileItem().write(file2); // 将上传的文件写入新建的文件中
                    uploadList.add(new UploadFileModel(fileName_old, imagePath + File.separator + tempFileName_new,
                            tempFileName_form));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return uploadList;
    }

    /**
     * 上传图片（给视频绑定图片用的）
     * 
     * @param request
     *            请求
     * @param accessableImageType
     *            要过滤的文件类型
     * @return 0成功
     */
    public static String[] uploadImage(HttpServletRequest request, String accessableImageType, String paramName) {

        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件：
        MultipartFile file = multipartRequest.getFile(paramName);
        // 获得文件名：
        String filename = file.getOriginalFilename();

        if (StringUtils.isEmpty(filename)) {
            return new String[] { CodeConstant.AJAX_RESULT_FAIL, "0" /* "对不起，找不到您上传的文件，请确认您选择了文件。" */ };
        }

        // 文件的后缀名
        String suffix = FileUtil.getSuffixUnContainPoint(filename);

        // 允许上传的图片类型
        if (!accessableImageType.contains(suffix.toLowerCase())) {
            return new String[] { CodeConstant.AJAX_RESULT_FAIL, "1"
                    /*
                     * "对不起，不支持您选择的文件类型，目前支持的文件类型为[" +
                     * accessableImageType.toUpperCase() + "]"
                     */ };
        }

        try {

            InputStream input = file.getInputStream();

            String path = request.getSession().getServletContext().getRealPath("/") + "images/video";
            File savePath = new File(path);

            if (!savePath.exists()) { // 文件夹
                savePath.mkdir();
            }

            String newFileName = System.currentTimeMillis() + "." + suffix;

            File newFile = new File(savePath, newFileName);

            FileUtils.copyInputStreamToFile(input, newFile);

            return new String[] { CodeConstant.AJAX_RESULT_SUCCESS, "images/video" + newFileName };

        } catch (IOException e) {

            return new String[] { CodeConstant.AJAX_RESULT_FAIL, "服务器出现错误，如果再次出现该错误，请联系系统管理员！" };
        }
    }

    /**
     * KindEdit上传
     * 
     * @param uploadFile
     * @param request
     * @return
     */
    public static Map<String, Object> uploadPicture(MultipartFile uploadFile, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 生成一个新的文件名
            // 取原始文件名
            String oldName = uploadFile.getOriginalFilename();
            // 生成新文件名
            String newName = Tools.getUUID();
            newName = newName + oldName.substring(oldName.lastIndexOf("."));
            Calendar ca = Calendar.getInstance();
            ca.setTime(new Date());

            // 图片上传(/yyyy/MM/dd)
            String imagePath = "/" + ca.get(Calendar.YEAR) + "/" + (ca.get(Calendar.MONTH)+1) + "/"
                    + ca.get(Calendar.DAY_OF_MONTH);
            // ResourceUtil.getImagePath()：读取资源文件的image目录
            imagePath = ResourceUtil.getImagePath() + imagePath;

            InputStream input = uploadFile.getInputStream();

            String path = request.getSession().getServletContext().getRealPath("/") + imagePath;

            File savePath = new File(path);
            if (!savePath.exists()) { // 文件夹
                savePath.mkdir();
            }

            File file = new File(savePath, newName);

            FileUtils.copyInputStreamToFile(input, file);

            resultMap.put("error", 0);
            resultMap.put("url", request.getContextPath() + "/" + imagePath + "/" + newName);
            return resultMap;

        } catch (Exception e) {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传发生异常");
            return resultMap;
        }
    }

    /**
     * 
     * 描述: 上传返回UploadFileModel,例如uploadfy调用此方法
     *
     * @param request
     * @return List<UploadFileModel>
     */
    public static UploadFileModel uploadFile(MultipartHttpServletRequest request) {

        UploadFileModel uploadList = new UploadFileModel();

        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            String tempFileName_form = (String) fileNames.next();
            MultipartFile file = request.getFile(tempFileName_form);
            if (!file.isEmpty()) {
                // 处理路径
                Calendar ca = Calendar.getInstance();
                ca.setTime(new Date());
                String imagePath = ResourceUtil.getImagePath() + "/standard/" + ca.get(Calendar.YEAR) + "/"
                        + (ca.get(Calendar.MONTH)+1) + "/" + ca.get(Calendar.DAY_OF_MONTH);
                ;
                String path = request.getRealPath(imagePath); // 获取本地存储路径
                File savePath = new File(path);
                if (!savePath.exists()) { // 文件夹
                    savePath.mkdir();
                }

                String fileName_old = file.getOriginalFilename();
                String fileType = fileName_old.substring(fileName_old.lastIndexOf("."));
                String tempFileName_new = new Date().getTime() + fileType;
                File file2 = new File(path, tempFileName_new); // 新建一个文件
                try {
                    FileUtils.copyInputStreamToFile(file.getInputStream(), file2);
                    uploadList = new UploadFileModel(fileName_old, imagePath + "/" + tempFileName_new,
                            tempFileName_form, file.getSize());
                    return uploadList;
                } catch (Exception e) {
                    e.printStackTrace();
                    return uploadList;
                }
            }
        }
        return uploadList;
    }
}
