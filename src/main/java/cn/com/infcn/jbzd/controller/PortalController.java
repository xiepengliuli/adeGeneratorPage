package cn.com.infcn.jbzd.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.AttachStandard;
import cn.com.infcn.core.pageModelAdd.Disease;
import cn.com.infcn.core.pageModelAdd.PortalUser;
import cn.com.infcn.core.pageModelAdd.Standard;
import cn.com.infcn.core.util.FileDownloadUtil;
import cn.com.infcn.core.util.ReplaceStringUtil;
import cn.com.infcn.core.util.UserUtil;
import cn.com.infcn.core.util.WordUtil;
import cn.com.infcn.jbzd.service.AttachStandardService;
import cn.com.infcn.jbzd.service.DiseaseService;
import cn.com.infcn.jbzd.service.PortalUserService;
import cn.com.infcn.jbzd.service.StandardService;
import cn.com.infcn.jbzd.service.SymptomService;

/**
 * 
 * 描述: 前台Controller
 * 
 * @author xiep
 * @date 2016年4月20日 上午9:14:48
 */
@Controller
@RequestMapping("/web")
public class PortalController extends BaseController {
	private static final Logger LOG = Logger.getLogger(PortalController.class);
	@Autowired
	private DiseaseService diseaseService;
	@Autowired
	private SymptomService symptomService;
	@Autowired
	private StandardService standardService;
	@Autowired
	private PortalUserService portalUserService;
	@Autowired
	private AttachStandardService attachStandardService;

	/**
	 * 
	 * 描述: 根据简单检索获取分组数据
	 * 
	 * @param column
	 * @param isLike
	 * @param keyWord
	 * @return String
	 */
	@RequestMapping("/diseasesBySimpleQ")
	public String diseasesBySimpleQ(String isLike, String keyWord, HttpServletRequest request) {

		List<Disease> diseaseList = diseaseService.getDiseaseDataBySimpleQ(isLike, keyWord);

		request.setAttribute("diseaseList", diseaseList);
		request.setAttribute("isLike", isLike);
		request.setAttribute("keyWord", keyWord);

		return "/web/diseasesBySimpleQ";
	}

	@RequestMapping("/homePage")
	public String homePage(@RequestParam(defaultValue = CodeConstant.QUERY_TYPE_LIKE) String isLike,
			@RequestParam(defaultValue = "") String keyWord, HttpServletRequest request) {

		Map<String, List<Disease>> diseaseMaps = diseaseService.getDiseaseData(isLike, keyWord);

		request.setAttribute("diseaseMaps", diseaseMaps);
		request.setAttribute("isLike", isLike);
		request.setAttribute("keyWord", keyWord);

		return "/web/homePage";

	}

	/**
	 * 生成word1
	 * 
	 * @param templateName
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/importWord")
	public JsonResult importWord(String id, String templateName, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取疾病数据
		Disease diseaseData = diseaseService.getByDiseaseId(id);
		dataMap.put("diseaseData", diseaseData);

		// 获取全部标准数据
		Map<String, List<Standard>> standardList = diseaseService.getStandardsByDiseaseId(id);
		// 选择中医标准
		List<Standard> cnStandards = standardList.get("cnList");

		// 处理标准中富文本内容
		if (cnStandards != null) {
			for (Standard standard : cnStandards) {

				String mmStandard = standard.getMmStandard();
				if (StringUtils.isNotEmpty(mmStandard)) {
					mmStandard = mmStandard.replaceAll("<br/>", "<w:br/>");
					mmStandard = ReplaceStringUtil.delHTMLTag(mmStandard);
					standard.setMmStandard(mmStandard);
				}
				String tcmDiseaseDialectical = standard.getTcmDiseaseDialectical();
				if (StringUtils.isNotEmpty(tcmDiseaseDialectical)) {
					tcmDiseaseDialectical = tcmDiseaseDialectical.replaceAll("<br/>", "<w:br/>");
					tcmDiseaseDialectical = ReplaceStringUtil.delHTMLTag(tcmDiseaseDialectical);
					standard.setTcmDiseaseDialectical(tcmDiseaseDialectical);
				}
				String tcmStandard = standard.getTcmStandard();
				if (StringUtils.isNotEmpty(mmStandard)) {
					tcmStandard = tcmStandard.replaceAll("<br/>", "<w:br/>");
					tcmStandard = ReplaceStringUtil.delHTMLTag(tcmStandard);
					standard.setTcmStandard(tcmStandard);
				}

				String effectTarget = standard.getEffectTarget();
				if (StringUtils.isNotEmpty(effectTarget)) {
					effectTarget = effectTarget.replaceAll("<br/>", "<w:br/>");
					effectTarget = ReplaceStringUtil.delHTMLTag(effectTarget);
					standard.setEffectTarget(effectTarget);
				}

				String effectStandard = standard.getEffectStandard();
				if (StringUtils.isNotEmpty(effectStandard)) {
					effectStandard = effectStandard.replaceAll("<br/>", "<w:br/>");
					effectStandard = ReplaceStringUtil.delHTMLTag(effectStandard);
					standard.setEffectStandard(effectStandard);
				}

				String remark = standard.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					remark = remark.replaceAll("<br/>", "<w:br/>");
					remark = ReplaceStringUtil.delHTMLTag(remark);
					standard.setMmStandard(remark);
				}

			}
		}

		// 设置中医标准
		dataMap.put("cnStandards", cnStandards);

		// 选择西医标准
		List<Standard> enStandards = standardList.get("enList");
		// 处理标准中富文本内容
		if (enStandards != null) {
			for (Standard standard : enStandards) {

				String mmStandard = standard.getMmStandard();
				if (StringUtils.isNotEmpty(mmStandard)) {
					mmStandard = mmStandard.replaceAll("<br/>", "<w:br/>");
					mmStandard = ReplaceStringUtil.delHTMLTag(mmStandard);
					standard.setMmStandard(mmStandard);
				}
				String tcmDiseaseDialectical = standard.getTcmDiseaseDialectical();
				if (StringUtils.isNotEmpty(tcmDiseaseDialectical)) {
					tcmDiseaseDialectical = tcmDiseaseDialectical.replaceAll("<br/>", "<w:br/>");
					tcmDiseaseDialectical = ReplaceStringUtil.delHTMLTag(tcmDiseaseDialectical);
					standard.setTcmDiseaseDialectical(tcmDiseaseDialectical);
				}
				String tcmStandard = standard.getTcmStandard();
				if (StringUtils.isNotEmpty(tcmStandard)) {
					tcmStandard = tcmStandard.replaceAll("<br/>", "<w:br/>");
					tcmStandard = ReplaceStringUtil.delHTMLTag(tcmStandard);
					standard.setTcmStandard(tcmStandard);
				}

				String effectTarget = standard.getEffectTarget();
				if (StringUtils.isNotEmpty(effectTarget)) {
					effectTarget = effectTarget.replaceAll("<br/>", "<w:br/>");
					effectTarget = ReplaceStringUtil.delHTMLTag(effectTarget);
					standard.setEffectTarget(effectTarget);
				}

				String effectStandard = standard.getEffectStandard();
				if (StringUtils.isNotEmpty(effectStandard)) {
					effectStandard = effectStandard.replaceAll("<br/>", "<w:br/>");
					effectStandard = ReplaceStringUtil.delHTMLTag(effectStandard);
					standard.setEffectStandard(effectStandard);
				}
				String typeStag = standard.getTypeStag();
				if (StringUtils.isNotEmpty(typeStag)) {
					typeStag = typeStag.replaceAll("<br/>", "<w:br/>");
					typeStag = ReplaceStringUtil.delHTMLTag(typeStag);
					standard.setTypeStag(typeStag);
				}

				String remark = standard.getRemark();
				if (StringUtils.isNotEmpty(remark)) {
					remark = remark.replaceAll("<br/>", "<w:br/>");
					remark = ReplaceStringUtil.delHTMLTag(remark);
					standard.setRemark(remark);
				}
			}
		}

		// 设置西医标准

		dataMap.put("enStandards", enStandards);

		dataMap.put("StandardsCount", cnStandards.size() + enStandards.size());
		// 中医标准个数
		dataMap.put("cnStandardsCount", cnStandards.size());
		// 西医标准个数
		dataMap.put("enStandardsCount", enStandards.size());

		// 选择中文语言标准
		List<Standard> cnLangueList = standardList.get("cnLangueList");
		// 选择西文语言标准
		List<Standard> enLangueList = standardList.get("enLangueList");
		// 中文标准个数
		dataMap.put("cnLangueListCount", cnLangueList.size());
		// 西文标准个数
		dataMap.put("enLangueListCount", enLangueList.size());

		// 找出所有包括中医病名集合
		List<String> tcmDiseaseNameList = new ArrayList<String>();
		if (cnStandards != null) {
			for (Standard standard : cnStandards) {
				boolean isEixct = false;
				if (StringUtils.isNotBlank(standard.getTcmDiseaseName())) {
					if (tcmDiseaseNameList.size() > 0) {
						for (String s : tcmDiseaseNameList) {
							if (s.equals(standard.getTcmDiseaseName().trim() + "、")) {
								isEixct = true;
								break;
							}
						}
					}
					if (!isEixct)
						tcmDiseaseNameList.add(standard.getTcmDiseaseName().trim() + "、");
				}
			}
		}
		// 去除最后一项的符号
		if (tcmDiseaseNameList.size() > 0) {
			String lastitem = (String) tcmDiseaseNameList.get(tcmDiseaseNameList.size() - 1);
			lastitem = lastitem.substring(0, lastitem.length() - 1);
			tcmDiseaseNameList.remove(tcmDiseaseNameList.size() - 1);
			tcmDiseaseNameList.add(lastitem);
		}
		dataMap.put("tcmDiseaseNameList", tcmDiseaseNameList);

		// 找出所有包括西医病名集合
		List<String> entcmDiseaseNameList = new ArrayList<String>();
		if (enStandards != null) {
			for (Standard standard : enStandards) {

				if (StringUtils.isNotBlank(standard.getMmDiseaseName())) {
					boolean isEixct = false;

					if (entcmDiseaseNameList.size() > 0) {
						for (String s : entcmDiseaseNameList) {
							if (s.equals(standard.getMmDiseaseName().trim() + "、")) {
								isEixct = true;
								break;
							}
						}
					}
					if (!isEixct)
						entcmDiseaseNameList.add(standard.getMmDiseaseName() + "、");

				}
			}

		}
		// 去除最后一项的符号
		if (entcmDiseaseNameList.size() > 0) {
			String enlastitem = (String) entcmDiseaseNameList.get(entcmDiseaseNameList.size() - 1);
			enlastitem = enlastitem.substring(0, enlastitem.length() - 1);
			entcmDiseaseNameList.remove(entcmDiseaseNameList.size() - 1);
			entcmDiseaseNameList.add(enlastitem);
		}
		dataMap.put("entcmDiseaseNameList", entcmDiseaseNameList);

		// 文件名称，唯一字符串
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
		StringBuffer sb = new StringBuffer();
		sb.append(sdf1.format(new Date()));
		// 文件唯一名称
		String fileOnlyName = diseaseData.getName() + sb + ".doc";
		// 文件路径
		templateName = "templete.ftl";
		String filePath = request.getSession().getServletContext().getRealPath("templete") + File.separator
				+ templateName;
				// System.out.println(filePath);

		// ** 生成word */
		String downloadParh = WordUtil.createWord(dataMap, filePath, filePath, fileOnlyName);
		File downFile = new File(downloadParh);

		OutputStream os = null;
		try {
			os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-Disposition",
					"attachment; filename=" + new String(fileOnlyName.getBytes("gbk"), "ISO8859-1"));
			response.setContentType("application/octet-stream; charset=utf-8");
			os.write(FileUtils.readFileToByteArray(downFile));

		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {

			try {
				os.close();
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 返回到standardList页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/standardList")
	public String standardList(String id, HttpServletRequest request) {

		PortalUser user = (PortalUser) request.getSession().getAttribute("user");
		String isNotTest = "isNotTest";
		if (user != null) {
			String loginName = user.getLoginName();
			if ("test".equals(loginName)) {
				isNotTest = "isTest";
			}
		}
		request.setAttribute("isNotTest", isNotTest);
		// 获取病种数据
		Disease diseaseData = diseaseService.getByDiseaseId(id);
		request.setAttribute("diseaseData", diseaseData);

		// 获取标准数据
		Map<String, List<Standard>> standardList = diseaseService.getStandardsByDiseaseId(id);

		List<Standard> cnStandards = standardList.get("cnList");
		List<Standard> enStandards = standardList.get("enList");
		// 设置中医标准，西医标准
		request.setAttribute("cnStandards", cnStandards);
		request.setAttribute("enStandards", enStandards);

		Set<String> cnStandardgroup = new HashSet<>();
		Set<String> enStandardgroup = new HashSet<>();

		// 找出所有包括中医标准的病种名称
		for (Standard standard : cnStandards) {
			cnStandardgroup.add(standard.getTcmDiseaseName());
		}
		// 找出所有包括西医标准的病种名称
		for (Standard standard : enStandards) {
			enStandardgroup.add(standard.getMmDiseaseName());
		}

		// 设置病种名称
		request.setAttribute("cnStandardgroup", cnStandardgroup);
		request.setAttribute("enStandardgroup", enStandardgroup);

		return "/web/standardList";

	}

	/**
	 * 返回到standardDetail页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/standardDetail")
	public String standardDetail(String id, HttpServletRequest request) {

		// 获取病种数据
		Standard standardData = standardService.get(id);
		request.setAttribute("standardData", standardData);
		return "/web/standardDetail";

	}

	/**
	 * 返回到standardCompare页
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/standardCompare")
	public String standardCompare(String ids, HttpServletRequest request) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");

			Standard standardData = standardService.get(idsArray[0]);
			Standard standardData2 = standardService.get(idsArray[1]);

			request.setAttribute("standardData", standardData);
			request.setAttribute("standardData2", standardData2);
		}
		return "/web/standardCompare";

	}

	/**
	 * 证候首页
	 * 
	 * @param request
	 * @param ph
	 * @return
	 */
	@RequestMapping("/symptomIndex")
	public String symptomIndex(HttpServletRequest request, PageHelper ph) {
		String fidlds = request.getParameter("fields");// 查询的字段名称
		String keyword = request.getParameter("keyword");// 检索关键字
		String range = request.getParameter("range");// 精确/模糊
		PageHelper pageHelper = symptomService.findByKeyword(ph, fidlds, keyword, range);
		request.setAttribute("pageHelper", pageHelper);
		request.setAttribute("fields", fidlds);
		request.setAttribute("keyword", keyword);
		request.setAttribute("range", range);
		return "/web/symptomIndex";
	}

	// TODO 暂时不做高级检索功能,该方法目前可以获取前台传过来的条件参数
	/**
	 * 证候高级检索页面
	 * 
	 * @param request
	 * @param ph
	 * @return
	 */
	@RequestMapping("/symptomAdvanced")
	public String symptomAdvanced(HttpServletRequest request, PageHelper ph) {
		String[] logic = request.getParameterValues("logic");
		String[] fields = request.getParameterValues("fields");
		String[] keyword = request.getParameterValues("keyword");
		String[] range = request.getParameterValues("range");
		return "/web/symptomAdvanced";
	}

	/**
	 * PDF文件下载
	 * 
	 * @param request
	 * @param response
	 * @param downloadRealPath
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public String download(HttpServletRequest request, HttpServletResponse response, String id) throws Exception {

		AttachStandard attachStandard = attachStandardService.get(id);
		return FileDownloadUtil.download(request, response, attachStandard.getUrl(), attachStandard.getName());

	}

	/**
	 * 返回到diseasesTypeSearch页 病 种检索
	 * 
	 * @param id
	 * @param range
	 *            :模糊，精确：1
	 * @return
	 */
	@RequestMapping("/diseasesTypeSearch")
	public String diseasesTypeSearch(@RequestParam(defaultValue = CodeConstant.QUERY_TYPE_LIKE) String range,
			@RequestParam(defaultValue = "") String keyWord, HttpServletRequest request) {

		// 获取前台的参数据据
		keyWord = request.getParameter("keyword");// 检索关键字
		range = request.getParameter("range");// 精确/模糊
		String feilds = request.getParameter("diseasesSearch");// 查询的字段名称

		// 准备数据
		List<Disease> diseaseList = diseaseService.diseasesTypeSearch(range, keyWord);

		request.setAttribute("diseaseList", diseaseList);
		request.setAttribute("range", range);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("feilds", feilds);
		request.setAttribute("diseaseSelected", true);
		// 返回到diseasesTypeSearch页面
		return "/web/diseasesTypeSearch";
	}

	/**
	 * 返回到standardSearch页 标准检索
	 * 
	 * @param id
	 * @param range
	 *            :模糊，精确：1
	 * @return
	 */
	@RequestMapping("/standardSearch")
	public String standardSearch(@RequestParam(defaultValue = CodeConstant.QUERY_TYPE_LIKE) String range,
			@RequestParam(defaultValue = "") String keyWord, HttpServletRequest request) {

		// 获取前台的参数据据
		String feilds = request.getParameter("standardSearch");// 查询的字段名称
		keyWord = request.getParameter("keyword");// 检索关键字
		range = request.getParameter("range");// 精确/模糊

		// 准备数据
		List<Standard> standardList = standardService.standardSearch(feilds, range, keyWord);
		request.setAttribute("standardList", standardList);
		request.setAttribute("range", range);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("feilds", feilds);
		request.setAttribute("diseaseSelected", false);
		// 返回到diseasesTypeSearch页面
		return "/web/standardSearch";

	}

	/*
	 * 返回到advancedQuery页 标准检索
	 * 
	 * @param logic 罗辑值
	 * 
	 * @param fields 字段值
	 * 
	 * @param keywords 关键字
	 * 
	 * @param range :模糊，精确：1
	 * 
	 * @return
	 */
	@RequestMapping("/advancedQuery")
	public String advancedQuery(String[] fields, String[] keywords, String[] ranges, String beginTime, String endtime,
			HttpServletRequest request) {

		// 准备数据
		List<Standard> standardList = standardService.advancedQuery(fields, keywords, ranges, beginTime, endtime);
		request.setAttribute("standardList", standardList);

		request.setAttribute("fieldsArray", fields);
		request.setAttribute("keywordsArray", keywords);
		request.setAttribute("rangesArray", ranges);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endtime", endtime);

		// 返回到diseasesTypeSearch页面
		return "/web/advancedQuery";

	}

	/**
	 * 打开登陆界面
	 * 
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest request) {
		PortalUser loginUserPortal = UserUtil.getLoginUserPortal(request);
		if (loginUserPortal != null) {
			return "redirect:/web/homePage";
		}
		return "/web/login";
	}

	/**
	 * 用户登陆验证
	 * 
	 * @param portalUser
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Json login(PortalUser portalUser, HttpServletRequest request) {
		Json json = new Json();
		PortalUser user = portalUserService.login(portalUser);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			UserUtil.setLoginUserPortal(request, user);
			json.setSuccess(true);
			json.setMsg("登陆成功!");
			json.setObj(user);
		} else {
			json.setMsg("用户名或密码错误!");
		}
		return json;
	}

	/**
	 * 退出登录
	 * 
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logout")
	public JsonResult logout(HttpSession session) {
		JsonResult j = new JsonResult();
		if (session != null) {
			session.invalidate();
		}
		j.setSuccess(true);
		j.setMsg("注销成功！");
		return j;
	}

	/**
	 * 用户修改密码
	 * 
	 * @param portalUser
	 * @param request
	 * @return
	 */
	@RequestMapping("/changePassword")
	@ResponseBody
	public Json changePassword(String loginName, String oldPwd, String newPwd, HttpServletRequest request) {
		Json json = portalUserService.changePassword(loginName, oldPwd, newPwd);
		return json;
	}
}
