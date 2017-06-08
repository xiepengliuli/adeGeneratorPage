package cn.com.infcn.ade.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.ModuleService;
import cn.com.infcn.ade.admin.service.ModuleTypeService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.pageModel.Module;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.StringUtil;
import cn.com.infcn.core.util.UserUtil;
import cn.com.infcn.jbzd.service.DiseaseService;

/**
 * 模块控制器
 * 
 * @author infcn
 * 
 */
@Controller
@RequestMapping("/admin/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleTypeService moduleTypeService;
    @Autowired
    private DiseaseService diseaseService;

    /**
     * 获取当前用户能够看到的一级菜单列表
     * 
     * @param request
     * @return 当前用户能够看到的模块列表
     */
    @RequestMapping("/topMenu")
    @ResponseBody
    public List<Tree> topMenu(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);

        List<Tree> topMenu = moduleService.topMenu(user);
        if (user != null) {
            Tree menuTree = diseaseService.getMenuTree(user.getId());
            if (menuTree != null) {
                topMenu.add(menuTree);
            }
        }
        return topMenu;
    }

    /**
     * 构造树形菜单
     * 
     * @param request
     * @return 当前用户能够看到的模块列表
     */
    @RequestMapping("/treeMenu")
    @ResponseBody
    public List<Tree> treeMenu(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        String pid = request.getParameter("pid");
        return moduleService.treeMenu(user, pid);
    }

    /**
     * 获得模块列表<br>
     * 根据此列表，可以方便的生成当前用户可以看到的模块
     * 
     * 通过用户ID判断，他能看到的模块
     * 
     * @param session
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        return moduleService.tree(user);
    }

    /**
     * 获得模块树(包括所有模块类型)
     * 
     * 通过用户ID判断，他能看到的模块
     * 
     * @param session
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        return moduleService.allTree(user);
    }

    /**
     * 跳转到模块管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "/admin/module";
    }
    /**
     * 跳转到模块管理页面
     * 
     * @return
     */
    @RequestMapping("/modulePermission")
    public String modulePermission() {
        return "/admin/modulePermission";
    }

    /**
     * 跳转到模块添加页面
     * 
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        request.setAttribute("moduleTypeList", moduleTypeService.getModuleTypeList());
        Module r = new Module();
        r.setId(StringUtil.generateUUID());
        request.setAttribute("module", r);
        return "/admin/moduleAdd";
    }
    @RequestMapping("/treePage")
    public String treePage(HttpServletRequest request) {
    	request.setAttribute("moduleTypeList", moduleTypeService.getModuleTypeList());
    	Module r = new Module();
    	r.setId(StringUtil.generateUUID());
    	request.setAttribute("module", r);
    	return "/admin/treePage";
    }
    /**
     * 跳转到模块添加页面
     * 
     * @return
     */
    @RequestMapping("/moduleAddPermission")
    public String moduleAddPermission(HttpServletRequest request) {
        request.setAttribute("moduleTypeList", moduleTypeService.getModuleTypeList());
        Module r = new Module();
        r.setId(StringUtil.generateUUID());
        request.setAttribute("module", r);
        return "/admin/moduleAddPermission";
    }

    /**
     * 添加模块
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(Module module, HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        JsonResult j = new JsonResult();
        moduleService.add(module, user);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到模块编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id) {
        request.setAttribute("moduleTypeList", moduleTypeService.getModuleTypeList());
        Module r = moduleService.get(id);
        request.setAttribute("module", r);
        return "/admin/moduleEdit";
    }
    
    /**
     * 
     * 根据ID获取对象
     *
     * @param id
     * @return JsonResult
     */
    @RequestMapping("/getById")
    @ResponseBody
    public JsonResult getById(String id) {
        
        JsonResult jsonResult = new JsonResult();
        
        Module r;
        try {
            r = moduleService.get(id);
            jsonResult.setObj(r);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            jsonResult.setMsg(e.getMessage());
            e.printStackTrace();
        }
        
        return jsonResult;
    }
    
    /**
     * 跳转到模块编辑页面
     * 
     * @return
     */
    @RequestMapping("/editPagePermission")
    public String editPagePermission(HttpServletRequest request, String id) {
        request.setAttribute("moduleTypeList", moduleTypeService.getModuleTypeList());
        Module r = moduleService.get(id);
        request.setAttribute("module", r);
        return "/admin/editPagePermission";
    }

    /**
     * 编辑模块
     * 
     * @param module
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult edit(Module module) {
        JsonResult j = new JsonResult();
        moduleService.edit(module);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 获得模块列表
     * 
     * 通过用户ID判断，他能看到的模块
     * 
     * @return
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<Module> treeGrid(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        return moduleService.treeGrid(user);
    }
    /**
     * 获得菜单的功能项
     * 
     * 
     * @return
     */
    @RequestMapping("/dataGridForMenu")
    @ResponseBody
    public List<Module> dataGridForMenu(HttpServletRequest request,String pid) {
        if (StringUtils.isBlank(pid)) {
            return new ArrayList<Module>();
        }
        User user = UserUtil.getLoginUser(request);
        return moduleService.dataGridForMenu(user,pid);
    }

    /**
     * 删除模块
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(String id) {
        JsonResult j = new JsonResult();
        moduleService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }
    
    
    @RequestMapping("/getTree")
    @ResponseBody
    public List<Tree> getTree(HttpServletRequest request,String id) {
        return moduleService.getTree(id);
    }
    
    @RequestMapping("/getSyncTree")
    @ResponseBody
    public List<Tree> getSyncTree(HttpServletRequest request,String id) {
    	return moduleService.getSyncTree(id);
    }

}
