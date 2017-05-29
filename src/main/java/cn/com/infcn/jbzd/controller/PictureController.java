/**
 * 
 */
package cn.com.infcn.jbzd.controller;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.infcn.core.util.FileUpload;

/**
 * 描述: KindEdit的图片上传处理类
 *
 * @author xiep
 * @date 2016年4月25日 上午9:12:31
 */
@Controller
@RequestMapping("/admin/pic")
public class PictureController {
    @RequestMapping("/pictureUpload")
    @ResponseBody
    public Map<String, Object> pictureUpload(MultipartHttpServletRequest request) {
        
        Iterator<String> fileNames = request.getFileNames();
        
        MultipartFile uploadFile = null;
        
        if (fileNames.hasNext()) {
            uploadFile = request.getFile(fileNames.next());
        }

        Map<String, Object> result = FileUpload.uploadPicture(uploadFile, request);
        System.out.println(result);
        return result;
    }
}
