package cn.com.infcn.ade.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.RoleService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.pageModel.Role;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.StringUtil;
import cn.com.infcn.core.util.UserUtil;

/**
 * 角色控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/admin/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 跳转到角色管理页面
     * 
     * @return
     */
    @RequestMapping("/manager")
    public String manager() {
        return "/admin/role";
    }

    /**
     * 跳转到角色添加页面
     * 
     * @return
     */
    @RequestMapping("/addPage")
    public String addPage(HttpServletRequest request) {
        Role r = new Role();
        r.setId(StringUtil.generateUUID());
        request.setAttribute("role", r);
        return "/admin/roleAdd";
    }

    /**
     * 添加角色
     * 
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(Role role, HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        JsonResult j = new JsonResult();
        roleService.add(role, user);
        j.setSuccess(true);
        j.setMsg("添加成功！");
        return j;
    }

    /**
     * 跳转到角色修改页面
     * 
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, String id) {
        Role r = roleService.get(id);
        request.setAttribute("role", r);
        return "/admin/roleEdit";
    }

    /**
     * 修改角色
     * 
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult edit(Role role) {
        JsonResult j = new JsonResult();
        roleService.edit(role);
        j.setSuccess(true);
        j.setMsg("编辑成功！");
        return j;
    }

    /**
     * 获得角色列表
     * 
     * @return
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<Role> treeGrid(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        return roleService.treeGrid(user);
    }

    /**
     * 角色树(只能看到自己拥有的角色)
     * 
     * @return
     */
    @RequestMapping("/tree")
    @ResponseBody
    public List<Tree> tree(HttpServletRequest request) {
        User user = UserUtil.getLoginUser(request);
        return roleService.tree(user);
    }

    /**
     * 角色树
     * 
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree() {
        return roleService.allTree();
    }

    /**
     * 删除角色
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(String id) {
        JsonResult j = new JsonResult();
        roleService.delete(id);
        j.setMsg("删除成功！");
        j.setSuccess(true);
        return j;
    }

    /**
     * 跳转到角色授权页面
     * 
     * @return
     */
    @RequestMapping("/grantPage")
    public String grantPage(HttpServletRequest request, String id) {
        Role r = roleService.get(id);
        request.setAttribute("role", r);
        return "/admin/roleGrant";
    }

    /**
     * 授权
     * 
     * @param role
     * @return
     */
    @RequestMapping("/grant")
    @ResponseBody
    public JsonResult grant(Role role) {
        JsonResult j = new JsonResult();
        roleService.grant(role);
        j.setMsg("授权成功！");
        j.setSuccess(true);
        return j;
    }
}
