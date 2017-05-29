package cn.com.infcn.jbzd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.core.pageModelAdd.TestTree;
import cn.com.infcn.jbzd.service.TestTreeService;

/**
 * 模块控制器
 * 
 * @author infcn
 * 
 */
@Controller
@RequestMapping("/admin/testTree")
public class TestTreeController extends BaseController {

    @Autowired
    private TestTreeService moduleTestService;


    /**
     * 跳转到模块管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "/admin/testTree/tree";
    }
    
    @RequestMapping("/tree")
    @ResponseBody
    public List<TestTree> tree(HttpServletRequest request,String id) {
    	List<TestTree> allTrees = moduleTestService.tree(id);
        return allTrees;
    }

}
