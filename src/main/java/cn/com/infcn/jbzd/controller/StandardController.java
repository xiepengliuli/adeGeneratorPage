package cn.com.infcn.jbzd.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.EasyUIComboTree;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.UploadFileModel;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.DiseasePrivilege;
import cn.com.infcn.core.pageModelAdd.Standard;
import cn.com.infcn.core.pageModelAdd.Suggest;
import cn.com.infcn.core.util.FileUpload;
import cn.com.infcn.core.util.LogUtil;
import cn.com.infcn.core.util.StringEscapeEditor;
import cn.com.infcn.core.util.UserUtil;
import cn.com.infcn.jbzd.service.AttachStandardService;
import cn.com.infcn.jbzd.service.DiseaseService;
import cn.com.infcn.jbzd.service.StandardService;

/**
 * 
 * 描述: 标准controller
 *
 * @author xiep
 * @date 2016年4月25日 下午1:49:26
 */
@Controller
@RequestMapping("/admin/standard")
public class StandardController {

	@Autowired
	private StandardService standardService;
	@Autowired
	private DiseaseService diseaseService;
	@Autowired
	private AttachStandardService attachStandardService;

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {

		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	/**
	 * 描述: 跳转管理页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request, String tdiseaseId) {

		request.setAttribute("tdiseaseId", tdiseaseId);

		User loginUser = UserUtil.getLoginUser(request);
		DiseasePrivilege diseasePrivilege = diseaseService.getPrivilegeByUserIdAndTdiseaseId(tdiseaseId,
				loginUser.getId());
		request.setAttribute("diseasePrivilege", diseasePrivilege);

		return "/admin/standard/manager";

	}

	/**
	 * 描述: 获取管理数据表格
	 *
	 * @param standard
	 * @param ph
	 * @param request
	 * @return EasyUIDataGrid
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(Standard standard, PageHelper ph, HttpServletRequest request) {

		User loginUser = UserUtil.getLoginUser(request);
		standard.setUserId(loginUser.getId());

		if (ph.getRows() == 0) {
			ph.setRows(10);
		}
		if (ph.getPage() == 0) {
			ph.setPage(1);
		}
		return standardService.dataGrid(standard, ph);
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request, String tdiseaseId) {

		request.setAttribute("tdiseaseId", tdiseaseId);
		return "/admin/standard/addPage";
	}

	/**
	 * 描述: 跳转到添加页面
	 *
	 * @param request
	 * @return String
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, String id) {
		request.setAttribute("id", id);
		return "/admin/standard/editPage";
	}

	/**
	 * 
	 * 描述: 添加
	 *
	 * @param standard
	 * @param request
	 * @param saveAndSubmit
	 *            不为空表示保存并提交审核
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(Standard standard, HttpServletRequest request, String saveAndSubmit) throws Exception {

		User loginUser = UserUtil.getLoginUser(request);

		standard.setCreateUser(loginUser.getLoginName());
		standard.setUpdateUser(loginUser.getLoginName());

		Json j = new Json();
		try {
			standardService.add(standard, saveAndSubmit);
			j.setSuccess(true);
			j.setMsg("保存成功！");
			j.setObj(standard);
			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_ADD, standard.getId(), params,
					request);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 描述: 根据Id获取待编辑的数据
	 *
	 * @param request
	 * @param id
	 * @return Standard
	 */
	@RequestMapping("/editData")
	@ResponseBody
	public Standard editData(HttpServletRequest request, String id) {

		Standard standard = standardService.get(id);
		if (standard != null) {
			if (standard.getMmStandard() == null) {
				standard.setMmStandard("");
			}
			if (standard.getTcmStandard() == null) {
				standard.setTcmStandard("");
			}
			if (standard.getEffectStandard() == null) {
				standard.setEffectStandard("");
			}
		}

		return standard;
	}

	/**
	 * 描述: 修改
	 *
	 * @param standard
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(Standard standard, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			standard.setUpdateUser(loginUser.getLoginName());
			standardService.edit(standard);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(standard);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_EDIT, standard.getId(), params,
					request);

		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;

	}

	/**
	 * 描述: 删除
	 *
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(String id, HttpServletRequest request) throws Exception {

		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			standardService.deleteById(id, loginUser.getId());
			j.setMsg("删除成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_DEL_FROM_RECYCLEBIN,
					id, params, request);
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
	public Json batchDelete(String ids, HttpServletRequest request) throws Exception {
		Json j = new Json();
		User loginUser = UserUtil.getLoginUser(request);
		standardService.deleteBatch(ids, loginUser.getId());
		j.setMsg("批量删除成功！");
		j.setSuccess(true);

		Map<String, Object> params = new HashMap<>();
		LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_DEL_FROM_RECYCLEBIN, ids,
				params, request);

		return j;
	}

	/**
	 * 
	 * 处理多文件上传
	 * 
	 * @param response
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/uploadFiles")
	public UploadFileModel uploadFiles(HttpServletResponse response, HttpServletRequest request) throws IOException {

		UploadFileModel uploadFile = FileUpload.uploadFile((MultipartHttpServletRequest) request);

		return uploadFile;
	}

	/**
	 * 
	 * 描述: 根据附件ID删除附件
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 *             Json
	 */
	@RequestMapping("/deleteAttach")
	@ResponseBody
	public Json deleteAttachById(String id) throws Exception {
		Json j = new Json();
		try {
			attachStandardService.deleteAttachById(id);
			j.setMsg("删除成功！");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setMsg("删除失败！");
		}
		return j;
	}

	/**
	 * 描述: 根据id把数据放到回收站
	 *
	 * @param id
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/deleteByIdToRecycleBin")
	@ResponseBody
	public Json deleteByIdToRecycleBin(String id, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			standardService.deleteByIdToRecycleBin(id, loginUser.getId());

			j.setMsg("删除成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_DEL_TO_RECYCLEBIN, id,
					params, request);

		} catch (Exception e) {
			j.setMsg("删除失败！");
		}
		return j;
	}

	/**
	 * 描述: 根据id数组把数据放到回收站
	 *
	 * @param ids
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/deleteBatchByIdsToRecycleBin")
	@ResponseBody
	public Json deleteBatchByIdsToRecycleBin(String ids, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			standardService.deleteBatchByIdsToRecycleBin(ids, loginUser.getId());
			j.setMsg("删除成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_DEL_TO_RECYCLEBIN, ids,
					params, request);
		} catch (Exception e) {
			j.setMsg("删除失败！");
		}
		return j;
	}

	/**
	 * 描述: 跳转到回收站
	 *
	 * @param tdiseaseId
	 * @param request
	 * @return String
	 */
	@RequestMapping("/recyclebin")
	public String recyclebin(String tdiseaseId, HttpServletRequest request) {

		request.setAttribute("tdiseaseId", tdiseaseId);

		return "/admin/standard/recyclebin";
	}

	/**
	 * 描述: 还原
	 * 
	 * @param id
	 *            void
	 */
	@RequestMapping("/restore")
	@ResponseBody
	public Json restore(String id, HttpServletRequest request) {
		Json j = new Json();
		try {

			standardService.restore(id);

			j.setMsg("还原成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD,
					CodeConstant.OPER_STANDARD_RESTOREF_ROM_RECYCLEBIN, id, params, request);
		} catch (Exception e) {
			j.setMsg("还原失败！");
		}
		return j;
	}

	/**
	 * 描述: 批量还原
	 * 
	 * @param ids
	 *            void
	 */

	@RequestMapping("/batchRestore")
	@ResponseBody
	public Json batchRestore(String ids, HttpServletRequest request) {
		Json j = new Json();
		try {

			standardService.batchRestore(ids);

			j.setMsg("批量还原成功！");
			j.setSuccess(true);
			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD,
					CodeConstant.OPER_STANDARD_RESTOREF_ROM_RECYCLEBIN, ids, params, request);
		} catch (Exception e) {
			j.setMsg("批量还原失败！");
		}
		return j;
	}

	/**
	 * 描述: 审核
	 *
	 * @param suggest
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public Json audit(Suggest suggest, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			suggest.setCreateUser(loginUser.getLoginName());

			standardService.saveAudit(suggest, loginUser.getId());

			j.setMsg("审核成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_AUDIT,
					suggest.getTstandardId(), params, request);
		} catch (Exception e) {
			j.setMsg("审核失败！");
		}
		return j;
	}

	/**
	 * 描述: 批量审核
	 *
	 * @param suggest
	 * @param ids
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/batchAudit")
	@ResponseBody
	public Json batchAudit(Suggest suggest, String ids, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			suggest.setCreateUser(loginUser.getLoginName());

			standardService.saveBatchAudit(suggest, ids, loginUser.getId());

			j.setMsg("批量审核成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_AUDIT, ids, params,
					request);
		} catch (Exception e) {
			j.setMsg("批量审核失败！");
		}
		return j;
	}

	/**
	 * 
	 * 描述: 发布
	 *
	 * @param suggest
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public Json publish(Suggest suggest, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			suggest.setCreateUser(loginUser.getLoginName());

			standardService.savePublish(suggest, loginUser.getId());

			j.setMsg("发布成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_PUBLISH,
					suggest.getTstandardId(), params, request);
		} catch (Exception e) {
			j.setMsg("发布失败！");
		}
		return j;
	}

	/**
	 * 描述: 批量发布
	 *
	 * @param suggest
	 * @param ids
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/batchPublish")
	@ResponseBody
	public Json batchPublish(Suggest suggest, String ids, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);

			suggest.setCreateUser(loginUser.getLoginName());

			standardService.saveBatchPublish(suggest, ids, loginUser.getId());

			j.setMsg("批量发布成功！");
			j.setSuccess(true);
			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_PUBLISH, ids, params,
					request);

		} catch (Exception e) {
			j.setMsg("批量发布失败！");
		}
		return j;
	}

	/**
	 * 描述: 撤销发布
	 *
	 * @param suggest
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/revokePublish")
	@ResponseBody
	public Json revokePublish(Suggest suggest, HttpServletRequest request) {
		Json j = new Json();
		try {
			User loginUser = UserUtil.getLoginUser(request);
			suggest.setCreateUser(loginUser.getLoginName());
			// 撤销发布时设置默认值
			if (StringUtils.isNotBlank(suggest.getIsPass())) {
				suggest.setIsPass(CodeConstant.SUGGEST_ISPASS_FAIL);
			}

			standardService.saveRevokePublish(suggest);

			j.setMsg("撤销发布成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_REVOKE_PUBLISH,
					suggest.getTstandardId(), params, request);

		} catch (Exception e) {
			j.setMsg("撤销发布失败！");
		}
		return j;
	}

	/**
	 * 描述: 提交审核
	 *
	 * @param suggest
	 * @param request
	 * @return Json
	 */
	@RequestMapping("/submitAudit")
	@ResponseBody
	public Json submitAudit(Suggest suggest, HttpServletRequest request) {
		Json j = new Json();
		try {

			User loginUser = UserUtil.getLoginUser(request);

			if (StringUtils.isNotBlank(suggest.getIsPass())) {
				suggest.setIsPass(CodeConstant.SUGGEST_ISPASS_PASS);
			}

			suggest.setCreateUser(loginUser.getLoginName());

			standardService.submitAudit(suggest);

			j.setMsg("提交审核成功！");
			j.setSuccess(true);

			Map<String, Object> params = new HashMap<>();
			LogUtil.userLogs(CodeConstant.MODELNAME_FOR_LOG_STANDARD, CodeConstant.OPER_STANDARD_SUBMITAUDIT,
					suggest.getTstandardId(), params, request);

		} catch (Exception e) {
			j.setMsg("提交审核失败！");
		}
		return j;
	}

	/**
	 * 
	 * 描述: 获取年份
	 *
	 * @param start
	 * @param order
	 * @return List<EasyUIComboTree>
	 */
	@RequestMapping("/getYears")
	@ResponseBody
	public List<EasyUIComboTree> getYears(@RequestParam(defaultValue = "1970") int start,
			@RequestParam(defaultValue = "asc") String order) {

		ArrayList<EasyUIComboTree> returnList = new ArrayList<EasyUIComboTree>();
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		int year = ca.get(Calendar.YEAR);
		if (order.equalsIgnoreCase("asc")) {
			for (int i = start; i <= year; i++) {
				EasyUIComboTree easyUIComboTree = new EasyUIComboTree();
				easyUIComboTree.setId(Integer.toString(i));
				easyUIComboTree.setText(Integer.toString(i));
				returnList.add(easyUIComboTree);
			}
		} else {
			for (int i = year; i >= start; i--) {
				EasyUIComboTree easyUIComboTree = new EasyUIComboTree();
				easyUIComboTree.setId(Integer.toString(i));
				easyUIComboTree.setText(Integer.toString(i));
				returnList.add(easyUIComboTree);
			}
		}

		return returnList;
	}

	/**
	 * 描述: 审核未通过的意见
	 *
	 * @param tdiseaseId
	 * @param request
	 * @return String
	 */
	@RequestMapping("/showAuditSuggest")
	public String showAuditSuggest(String tdiseaseId, HttpServletRequest request) {

		List<Suggest> suggestList = standardService.getSuggestByTdiseaseIdAndTypeAndIsPass(tdiseaseId,
				CodeConstant.SUGGEST_TYPE_AUDIT, CodeConstant.SUGGEST_ISPASS_FAIL);

		request.setAttribute("suggestList", suggestList);
		return "/admin/standard/auditSuggest";
	}

	/**
	 * 描述: 发布未通过的意见
	 *
	 * @param tdiseaseId
	 * @param request
	 * @return String
	 */
	@RequestMapping("/showPublishSuggest")
	public String showPublishSuggest(String tdiseaseId, HttpServletRequest request) {

		List<Suggest> suggestList = standardService.getSuggestByTdiseaseIdAndTypeAndIsPass(tdiseaseId,
				CodeConstant.SUGGEST_TYPE_PUBLISH, CodeConstant.SUGGEST_ISPASS_FAIL);

		request.setAttribute("suggestList", suggestList);
		return "/admin/standard/publishSuggest";
	}

	@RequestMapping("/standardImportUI")
	public String standardImportUI() {
		return "/admin/standard/standardImportUI";
	}

	/**
	 * 
	 * 描述: 发布撤销
	 *
	 * @param tdiseaseId
	 * @param request
	 * @return String
	 */
	@RequestMapping("/publishRevokeSuggest")
	public String publishRevokeSuggest(String tdiseaseId, HttpServletRequest request) {

		List<Suggest> suggestList = standardService.getSuggestByTdiseaseIdAndType(tdiseaseId,
				CodeConstant.SUGGEST_TYPE_REVOKE);

		request.setAttribute("suggestList", suggestList);

		return "/admin/standard/publishRevokeSuggest";
	}

	@RequestMapping("/standardDetail")
	public String standardDetail(String id, HttpServletRequest request) {

		// 获取病种数据
		Standard standardData = standardService.get(id);
		request.setAttribute("standardData", standardData);
		request.setAttribute("requestType", "/admin/");
		return "/web/standardDetail";

	}

}
