package cn.com.infcn.ade.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.BugService;
import cn.com.infcn.ade.admin.service.BugTypeService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.pageModel.Bug;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.util.StringUtil;


/**
 * BUG管理控制器
 * 
 * @author 孙宇
 * 
 */
@Controller
@RequestMapping("/admin/bug")
public class BugController extends BaseController {

	@Autowired
	private BugService bugService;

	@Autowired
	private BugTypeService bugTypeService;

	/**
	 * 跳转到BUG管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		request.setAttribute("bugTypeList", bugTypeService.getBugTypeList());
		return "/admin/bug";
	}

	/**
	 * 获取BUG数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(Bug bug, PageHelper ph) {
		return bugService.dataGrid(bug, ph);
	}

	/**
	 * 跳转到添加BUG页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		Bug b = new Bug();
		b.setId(StringUtil.generateUUID());
		request.setAttribute("bug", b);
		request.setAttribute("bugTypeList", bugTypeService.getBugTypeList());
		return "/admin/bugAdd";
	}

	/**
	 * 添加BUG
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public JsonResult add(Bug bug) {
		JsonResult j = new JsonResult();
		try {
			bugService.add(bug);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 跳转到BUG查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, String id) {
		Bug b = bugService.get(id);
		request.setAttribute("bug", b);
		return "/admin/bugView";
	}

	/**
	 * 跳转到BUG修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		Bug b = bugService.get(id);
		request.setAttribute("bug", b);
		request.setAttribute("bugTypeList", bugTypeService.getBugTypeList());
		return "/admin/bugEdit";
	}

	/**
	 * 修改BUG
	 * 
	 * @param bug
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public JsonResult edit(Bug bug) {
		JsonResult j = new JsonResult();
		try {
			bugService.edit(bug);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
		} catch (Exception e) {
			// e.printStackTrace();
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除BUG
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(String id) {
		JsonResult j = new JsonResult();
		bugService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
