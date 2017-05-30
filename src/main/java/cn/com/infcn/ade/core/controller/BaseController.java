package cn.com.infcn.ade.core.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.infcn.core.util.StringEscapeEditor;

/**
 * 基础控制器
 * 
 * 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @author infcn
 * 
 */
@Controller
@RequestMapping("/baseController")
public class BaseController {

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {

        /**
         * 自动转换日期类型的字段格式
         */
        try {
			binder.registerCustomEditor(Date.class,
			        new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

        /**
         * 防止XSS攻击
         */
//        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }
}
