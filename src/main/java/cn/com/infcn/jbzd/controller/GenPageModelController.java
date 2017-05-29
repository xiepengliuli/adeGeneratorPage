package cn.com.infcn.jbzd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.EasyUISelectBox;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModelAdd.GenPageModel;
import cn.com.infcn.core.pageModelAdd.GenPageModelColum;
import cn.com.infcn.core.util.Tools;
import cn.com.infcn.jbzd.service.GenPageModelService;

/**
 * 描述: 前台用户管理
 *
 * @author xiep
 * @date 2016年5月9日 下午3:10:00
 */
@Controller
@RequestMapping("/admin/genPageModel")
public class GenPageModelController extends BaseController {

	@Autowired
	private GenPageModelService genPageModelService;

	/**
	 * 描述: 跳转管理页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/admin/genPageModel/manager";

	}

	/**
	 * 描述: 获取管理数据表格
	 *
	 * @param GenPageModel
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(GenPageModel genPageModel, PageHelper ph, String page, String rows) {
		if (StringUtils.isBlank(page)) {
			ph.setPage(1);
		}
		if (StringUtils.isBlank(rows)) {
			ph.setRows(10);
		}
		return genPageModelService.dataGrid(genPageModel, ph);
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/admin/genPageModel/addPage";
	}

	/**
	 * 
	 * 描述: 跳转属性维护页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/editColumPage")
	public String editColumPage(HttpServletRequest request, String id) {
		request.setAttribute("id", id);
		return "/admin/genPageModel/editColumPage";
	}

	/**
	 * 
	 * 描述: 根据genPageModelId获取对应的列
	 *
	 * @param request
	 * @param genPageModel
	 * @return Json
	 */
	@RequestMapping("/getColumsByPageModelId")
	@ResponseBody
	public Json getColumsByPageModelId(HttpServletRequest request, GenPageModel genPageModel) {

		Json json = new Json();
		try {
			GenPageModel tempPageModel = genPageModelService.getByIdContainsGenPageModelColum(genPageModel.getId());
			json.setObj(tempPageModel);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 
	 * 描述: 保存genPageModel的colum属性
	 *
	 * @param request
	 * @param genPageModel
	 * @return Json
	 */
	@RequestMapping("/savePageModelColum")
	@ResponseBody
	public Json savePageModelColum(HttpServletRequest request, GenPageModel genPageModel) {

		Json json = new Json();
		try {
			genPageModelService.savePageModelColum(genPageModel);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 描述: 添加
	 *
	 * @param genPageModel
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(GenPageModel genPageModel, HttpServletRequest request, HttpSession session) throws Exception {

		Json j = new Json();
		try {
			for (GenPageModelColum iterable_element : genPageModel.getGenPageModelColums()) {
				System.out.println(iterable_element.getColumName());
			}
			genPageModel.setId(Tools.getUUID());
			genPageModelService.add(genPageModel);
			j.setSuccess(true);
			j.setMsg("添加成功！");
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		} finally {

		}
		return j;
	}

	/**
	 * 描述: 跳转到发布管理修改页面
	 *
	 * @param request
	 * @param id
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		request.setAttribute("id", id);
		return "/admin/genPageModel/editPage";
	}

	@ResponseBody
	@RequestMapping("/editData")
	public GenPageModel editData(HttpServletRequest request, String id) {
		GenPageModel genPageModel = genPageModelService.get(id);
		return genPageModel;
	}

	/**
	 * 描述: 修改
	 *
	 * @param genPageModel
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(GenPageModel genPageModel, HttpServletRequest request) {
		Json j = new Json();
		try {
			genPageModelService.edit(genPageModel);
			j.setSuccess(true);
			j.setMsg("保存成功！");

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 描述: 删除
	 *
	 * @param id
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpSession session, HttpServletRequest request) throws Exception {

		Json j = new Json();
		try {
			genPageModelService.deleteByID(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("删除失败！");
			j.setSuccess(true);
		}
		return j;
	}

	/**
	 * 描述: 批量删除
	 *
	 * @param ids
	 *            ('0','1','2')
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/batchDelete")
	@ResponseBody
	public Json batchDelete(String ids, HttpSession session, HttpServletRequest request) throws Exception {
		Json j = new Json();
		if (ids != null && ids.length() > 0) {
			for (String id : ids.split(",")) {
				if (id != null) {
					this.delete(id, session, request);
				}
			}
		}
		j.setMsg("批量删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/getSelectBox")
	@ResponseBody
	public List<EasyUISelectBox> getSelectBox(HttpSession session, HttpServletRequest request) throws Exception {

		List<EasyUISelectBox> arrayList = genPageModelService.getSelectBox();

		return arrayList;
	}

}
