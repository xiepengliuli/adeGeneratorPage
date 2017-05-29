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
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModelAdd.GenElement;
import cn.com.infcn.core.pageModelAdd.GenPage;
import cn.com.infcn.core.pageModelAdd.GenRule;
import cn.com.infcn.core.util.Tools;
import cn.com.infcn.jbzd.service.GenPageService;

/**
 * 描述: 前台用户管理
 *
 * @author xiep
 * @date 2016年5月9日 下午3:10:00
 */
@Controller
@RequestMapping("/admin/genPage")
public class GenPageController extends BaseController {

	@Autowired
	private GenPageService genPageService;

	/**
	 * 描述: 跳转管理页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/admin/genPage/manager";

	}

	/**
	 * 描述: 获取管理数据表格
	 *
	 * @param GenPage
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(GenPage genPage, PageHelper ph, String page, String rows) {
		if (StringUtils.isBlank(page)) {
			ph.setPage(1);
		}
		if (StringUtils.isBlank(rows)) {
			ph.setRows(10);
		}
		return genPageService.dataGrid(genPage, ph);
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		return "/admin/genPage/addPage";
	}

	@RequestMapping("/editElementPage")
	public String editElementPage(HttpServletRequest request, String id) {
		request.setAttribute("id", id);
		return "/admin/genPage/editElementPage";
	}

	/**
	 * 描述: 添加
	 *
	 * @param genPage
	 * @param request
	 * @param session
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(GenPage genPage, HttpServletRequest request, HttpSession session) throws Exception {

		Json j = new Json();
		try {
			genPage.setId(Tools.getUUID());
			genPageService.add(genPage);
			j.setSuccess(true);
			j.setMsg("添加成功！");

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		} finally {

		}
		return j;
	}

	@RequestMapping("/saveElement")
	@ResponseBody
	public Json saveElement(GenPage genPage, HttpServletRequest request, HttpSession session) throws Exception {

		Json j = new Json();
		try {
			genPageService.saveElement(genPage);
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
		return "/admin/genPage/editPage";
	}

	@ResponseBody
	@RequestMapping("/editData")
	public GenPage editData(HttpServletRequest request, String id) {
		GenPage genPage = genPageService.get(id);
		return genPage;
	}

	/**
	 * 描述: 修改
	 *
	 * @param genPage
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(GenPage genPage, HttpServletRequest request) {
		Json j = new Json();
		try {
			genPageService.edit(genPage);
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
			genPageService.deleteByID(id);
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

	@RequestMapping("/getChildrens")
	@ResponseBody
	public List<Tree> getChildrens(HttpServletRequest request, String id) {

		List<Tree> childrens = genPageService.getChildrens(id);

		return childrens;
	}

	@RequestMapping("/getGenElementsById")
	@ResponseBody
	public Json getGenElementsById(String id) {

		Json json = new Json();

		List<GenElement> genElementList = genPageService.getGenElementsById(id);
		json.setObj(genElementList);
		json.setSuccess(true);

		return json;
	}

	/**
	 * 
	 * 描述: 跳转到规则编辑页面
	 *
	 * @param request
	 * @param elementId
	 * @return String
	 */
	@RequestMapping("/editRulesPage")
	public String editRulesPage(HttpServletRequest request, String elementId) {
		request.setAttribute("elementId", elementId);
		return "/admin/genPage/editRulesPage";
	}

	/**
	 * 
	 * 描述: 根据规则ID获取规则
	 *
	 * @param request
	 * @param id
	 * @return Json
	 */
//	@RequestMapping("/getRuleById")
//	@ResponseBody
//	public Json getRuleById(HttpServletRequest request, String id) {
//		Json json = new Json();
//		try {
//			GenRule genRule = genPageService.getRuleByElementId(id);
//			json.setSuccess(true);
//			json.setObj(genRule);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			json.setMsg(e.getMessage());
//		}
//		return json;
//	}
	@RequestMapping("/getRuleById")
	@ResponseBody
	public GenRule getRuleById(HttpServletRequest request, String id) {
			GenRule genRule = genPageService.getRuleByElementId(id);
		return genRule;
	}

	@RequestMapping("/getInputType")
	@ResponseBody
	public List<EasyUISelectBox> getInputType() {

		return genPageService.getInputType();
	}

	@RequestMapping("/getSelectBoxGenChecks")
	@ResponseBody
	public List<EasyUISelectBox> getSelectBoxGenChecks() {

		return genPageService.getSelectBoxGenChecks();
	}

	/**
	 * 
	 * 描述: 保存标准
	 *
	 * @param genRule
	 * @return Json
	 */
	@RequestMapping("/saveRule")
	@ResponseBody
	public Json saveRule(GenRule genRule) {
		Json json = new Json();

		try {
			genPageService.saveRule(genRule);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}

		return json;
	}
	/**
	 * 根据规则ID生成html
	 * @return
	 */
	@RequestMapping("/generatorHtmlByruleId")
	@ResponseBody
	public String generatorHtmlByruleId(String id) {
		
		String generatorHtml = genPageService.generatorHtmlByruleId(id);
		System.out.println(generatorHtml);
		return generatorHtml;
	}
	/**
	 * 根据页面Id生成html
	 * @return
	 */
	@RequestMapping("/generatorHtmlForOnePage")
	public String generatorHtmlForOnePage(String pageId,HttpServletRequest request) {
		
		String generatorHtml = genPageService.generatorHtmlForOnePage(pageId);
		
		request.setAttribute("generatorHtml", generatorHtml);
		return "/admin/genPage/generatorHtmlForOnePage";
	}
	
	/**
	 * 预览效果
	 * @param effectContent
	 * @param request
	 * @return
	 */
	@RequestMapping("/generatorEffectToSession")
	@ResponseBody
	public String generatorEffectToSession(String effectContent,HttpServletRequest request) {
		request.getSession().setAttribute("sgeneratorEffect", effectContent);
		return "OK";
	}
	@RequestMapping("/generatorEffect")
	public String generatorEffect(HttpServletRequest request) {
		String attribute = (String) request.getSession().getAttribute("sgeneratorEffect");
		request.setAttribute("sgeneratorEffect", attribute);
		return "/admin/genPage/generatorEffect";
	}
	
	
	
	

}
