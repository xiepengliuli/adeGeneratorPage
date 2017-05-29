package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TgenButton;
import cn.com.infcn.core.modelAdd.TgenCheck;
import cn.com.infcn.core.modelAdd.TgenElement;
import cn.com.infcn.core.modelAdd.TgenInput;
import cn.com.infcn.core.modelAdd.TgenPage;
import cn.com.infcn.core.modelAdd.TgenPageModel;
import cn.com.infcn.core.modelAdd.TgenPageModelColum;
import cn.com.infcn.core.modelAdd.TgenRule;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.EasyUISelectBox;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModelAdd.GenElement;
import cn.com.infcn.core.pageModelAdd.GenPage;
import cn.com.infcn.core.pageModelAdd.GenPageModel;
import cn.com.infcn.core.pageModelAdd.GenPageModelColum;
import cn.com.infcn.core.pageModelAdd.GenRule;
import cn.com.infcn.core.util.StringUtil;
import cn.com.infcn.core.util.Tools;

/**
 * 描述: TODO
 *
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class GenPageService {
	@Autowired
	private BaseDaoI<TgenPage> genPageDao;
	@Autowired
	private BaseDaoI<TgenElement> genElementDao;
	@Autowired
	private BaseDaoI<TgenPageModelColum> genPageModelColumDao;
	@Autowired
	private BaseDaoI<TgenInput> genInputDao;
	@Autowired
	private BaseDaoI<TgenRule> genRuleDao;
	@Autowired
	private BaseDaoI<TgenCheck> genCheckDao;
	@Autowired
	private BaseDaoI<TgenButton> genButtonDao;
	@Autowired
	private GenPageModelService genPageModelService;

	/**
	 * 描述: EasyUI-DataGrid访问的方法
	 *
	 * @param genPage
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	public EasyUIDataGrid dataGrid(GenPage genPage, PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<GenPage> ul = new ArrayList<GenPage>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TgenPage t ";
		List<TgenPage> l = genPageDao.find(hql + whereHql(genPage, params) + orderHql(ph), params, ph.getPage(),
				ph.getRows());

		if (l != null && l.size() > 0) {
			for (TgenPage t : l) {
				GenPage u = new GenPage();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(genPageDao.count("select count(*) " + hql + whereHql(genPage, params), params));
		return dg;
	}

	/**
	 * 描述: 拼接whereHql语句
	 *
	 * @param genPage
	 * @param params
	 * @return String
	 */
	private String whereHql(GenPage genPage, Map<String, Object> params) {
		String hql = "";
		if (genPage == null) {
			return hql;
		}
		hql += " where 1=1 ";
		if (StringUtils.isNotBlank(genPage.getPageTitle())) {
			hql += " and t.pageTitle like :pageTitle";
			params.put("pageTitle", "%" + genPage.getPageTitle() + "%");
		}
		if (StringUtils.isNotBlank(genPage.getPageUrl())) {
			hql += " and t.pageUrl like :pageUrl";
			params.put("pageUrl", "%" + genPage.getPageUrl() + "%");
		}
		hql += " and t.tgenPage.id is Null ";
		return hql;
	}

	/**
	 * 描述: 拼接orderHql语句
	 *
	 * @param ph
	 * @return String
	 */
	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by  t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	/**
	 * 描述: 添加
	 *
	 * @param genPage
	 * @throws Exception
	 *             void
	 */
	synchronized public GenPage add(GenPage genPage) throws Exception {
		TgenPage u = new TgenPage();
		BeanUtils.copyProperties(genPage, u);

		// 处理pageModel
		if (genPage.getGenPageModel() != null && StringUtils.isNotBlank(genPage.getGenPageModel().getId())) {

			GenPageModel genPageModelFrom = genPageModelService.get(genPage.getGenPageModel().getId());

			TgenPageModel tgenPageModel = new TgenPageModel();
			tgenPageModel.setId(genPageModelFrom.getId());
			u.setTgenPageModel(tgenPageModel);

			// 处理子页面(以后可以扩展成下拉框)
			List<String> pageTitles = new ArrayList<>();
			pageTitles.add("添加");
			pageTitles.add("修改");
			pageTitles.add("详情");
			List<String> pageUrls = new ArrayList<>();
			String pageModelFullName = genPageModelFrom.getPageModelFullName();
			pageModelFullName = pageModelFullName.substring(pageModelFullName.lastIndexOf(".") + 1,
					pageModelFullName.length());
			pageModelFullName = pageModelFullName.substring(0, 1).toLowerCase() + pageModelFullName.substring(1);
			pageUrls.add("/admin/" + pageModelFullName + "/addPage");
			pageUrls.add("/admin/" + pageModelFullName + "/editPage");
			pageUrls.add("/admin/" + pageModelFullName + "/detailPage");

			for (int i = 0; i < 3; i++) {
				TgenPage tgenPage = new TgenPage();
				BeanUtils.copyProperties(u, tgenPage);
				tgenPage.setId(Tools.getUUID());
				tgenPage.setPageTitle(pageTitles.get(i));
				tgenPage.setPageUrl(pageUrls.get(i));
				tgenPage.setPageOrderNumber(i + 1);
				tgenPage.setTgenPage(u);
				tgenPage.setTgenPageModel(tgenPageModel);

				genPageDao.save(tgenPage);
				// 处理子页面对应的elements
				for (int j = 0; j < genPageModelFrom.getGenPageModelColums().size(); j++) {
					GenPageModelColum genElement = genPageModelFrom.getGenPageModelColums().get(j);
					TgenElement tgenElement = new TgenElement();
					BeanUtils.copyProperties(genElement, tgenElement);

					tgenElement.setElementName(genElement.getColumName());
					tgenElement.setElementNameRemark(genElement.getColumRemark());
					tgenElement.setId(Tools.getUUID());
					tgenElement.setElementOrderNumber(j + 1);
					tgenElement.setTgenPage(tgenPage);
					// tgenElement.setTdictionary(tdictionary);

					genElementDao.save(tgenElement);

					GenRule genRule = new GenRule();
					GenElement genElement2 = new GenElement();
					genElement2.setId(tgenElement.getId());
					genRule.setGenElement(genElement2);
					saveRule(genRule);

				}

			}

		}
		genPageDao.save(u);
		GenPage pu = new GenPage();
		BeanUtils.copyProperties(u, pu);

		return pu;
	}

	/**
	 * 描述: 根据id获取
	 *
	 * @param id
	 * @return GenPage
	 */
	public GenPage get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<TgenPage> ts = genPageDao.find("select distinct t from TgenPage t  where t.id = :id ", params);

		if (ts != null && ts.size() > 0) {
			TgenPage t = ts.get(0);
			GenPage u = new GenPage();
			BeanUtils.copyProperties(t, u);
			List<TgenElement> tempList = genElementDao
					.find("from TgenElement t where t.tgenPage.id='" + id + "' order by t.elementOrderNumber");
			if (tempList != null && tempList.size() > 0) {
				for (TgenElement tgenElement : tempList) {
					GenElement genElement = new GenElement();
					BeanUtils.copyProperties(tgenElement, genElement);
					u.getGenElements().add(genElement);
				}
			}
			return u;
		}

		return null;
	}

	/**
	 * 描述: //TODO
	 *
	 * @param genPage
	 * @throws Exception
	 *             void
	 */
	public void edit(GenPage genPage) throws Exception {
		TgenPage u = genPageDao.get(TgenPage.class, genPage.getId());
		if (u != null) {
			BeanUtils.copyProperties(genPage, u);
			genPageDao.update(u);
		}

	}

	/**
	 * 描述: 根据id删除
	 *
	 * @param id
	 *            void
	 */
	public void deleteByID(String id) {
		if (StringUtils.isNotBlank(id)) {
			TgenPage tGenPage = genPageDao.get(TgenPage.class, id);
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);

			if (tGenPage != null) {

				Set<TgenElement> tgenElements = tGenPage.getTgenElements();
				for (TgenElement tgenElement : tgenElements) {
					genElementDao.delete(tgenElement);
				}

				Set<TgenPage> tgenPages = tGenPage.getTgenPages();
				for (TgenPage tgenPage2 : tgenPages) {

					for (TgenElement tgenElement : tgenPage2.getTgenElements()) {
						TgenRule tgenRule = tgenElement.getTgenRule();
						genRuleDao.delete(tgenRule);
						genElementDao.delete(tgenElement);
					}

					for (TgenPage tgenPage3 : tgenPages) {
						Set<TgenButton> tgenButtons = tgenPage3.getTgenButtons();
						for (TgenButton tgenButton : tgenButtons) {
							genButtonDao.delete(tgenButton);
						}
					}
					genPageDao.delete(tgenPage2);
				}

				genPageDao.delete(tGenPage);
			}
		}
	}

	/**
	 * 描述: 分页查询
	 *
	 * @param hql
	 * @param params
	 * @param pageHelper
	 * @return PageHelper
	 */
	public PageHelper find(String hql, Map<String, Object> params, PageHelper pageHelper) {

		List<GenPage> genPageList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
		pageHelper.setData(genPageList);
		// 获取总记录数
		hql = "select count(*) " + hql;
		int rowCount = genPageDao.count(hql, params).intValue();
		pageHelper.setTotal(rowCount);
		return pageHelper;
	}

	/**
	 * 描述: 分页方法
	 *
	 * @param hql
	 * @param params
	 * @param pageNum
	 * @param Pagecount
	 * @return List<GenPage>
	 */
	public List<GenPage> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

		List<GenPage> genPageList = new ArrayList<GenPage>();

		List<TgenPage> tempList = genPageDao.find(hql, params, pageNum, Pagecount);

		for (TgenPage tgenPage : tempList) {
			GenPage genPage = new GenPage();
			BeanUtils.copyProperties(tgenPage, genPage);

			genPageList.add(genPage);
		}

		return genPageList;

	}

	public void saveElement(GenPage genPage) {

		Map<String, Object> params = new HashMap<>();
		params.put("id", genPage.getId());
		TgenPage tgenPage = genPageDao.get("from TgenPage t where t.id=:id", params);
		if (tgenPage == null) {
			return;
		} else {
			tgenPage.setPageTitle(genPage.getPageTitle());
			tgenPage.setPageHeight(genPage.getPageHeight());
			tgenPage.setPageWidth(genPage.getPageWidth());
			tgenPage.setPageUrl(genPage.getPageUrl());
			genPageDao.update(tgenPage);
		}

		List<GenElement> genElements = genPage.getGenElements();
		if (genElements != null) {
			for (int i = 0; i < genElements.size(); i++) {
				GenElement genElement = genElements.get(i);
				TgenElement tgenElement = new TgenElement();
				String isVisible = genElement.getIsVisible();
				// 处理是否显示下拉框(传过来的是一个逗号分隔的字符串)
				if (StringUtils.isNotBlank(isVisible) && isVisible.contains(",")) {
					String substring = isVisible.substring(isVisible.lastIndexOf(",") + 1);
					genElement.setIsVisible(substring);
				}

				BeanUtils.copyProperties(genElement, tgenElement);

				tgenElement.setElementOrderNumber(i + 1);
				tgenElement.setTgenPage(tgenPage);

				genElementDao.saveOrUpdate(tgenElement);
			}
		}
	}

	/**
	 * 描述: TODO
	 *
	 * @param id
	 * @return List<Tree>
	 */
	public List<Tree> getChildrens(String id) {

		List<Tree> listTree = new ArrayList<Tree>();

		String hql = "from TgenPage t where t.tgenPage.id=:id order by t.pageOrderNumber";

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);

		List<TgenPage> findList = genPageDao.find(hql, params);

		for (TgenPage tgenPage : findList) {
			Tree tree = new Tree();
			tree.setId(tgenPage.getId());
			tree.setText(tgenPage.getPageTitle());
			listTree.add(tree);
		}
		return listTree;
	}

	public List<GenElement> getGenElementsById(String id) {

		List<GenElement> genElementList = new ArrayList<GenElement>();

		String hql = "from TgenElement t where t.tgenPage.id=:id order by t.elementOrderNumber";

		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		List<TgenElement> findList = genElementDao.find(hql, params);
		if (findList != null && findList.size() > 0) {
			for (TgenElement tgenElement : findList) {
				GenElement genElement = new GenElement();
				BeanUtils.copyProperties(tgenElement, genElement);

				GenPage genPage = new GenPage();
				TgenPage tgenPage = tgenElement.getTgenPage();
				BeanUtils.copyProperties(tgenPage, genPage);
				genElement.setGenPage(genPage);
				genElementList.add(genElement);
			}
		}
		// else {
		//
		// hql = "select t from TgenPageModelColum t inner join fetch
		// t.tgenPageModel as a inner join fetch a.tgenPages b where b.id=:id
		// order by t.orderNumber";
		//
		// List<TgenPageModelColum> tgenPageModelColumList =
		// genPageModelColumDao.find(hql, params);
		// for (TgenPageModelColum tgenPageModelColum : tgenPageModelColumList)
		// {
		// GenElement genElement = new GenElement();
		// genElement.setElementName(tgenPageModelColum.getColumName());
		// genElement.setElementNameRemark(tgenPageModelColum.getColumRemark());
		// genElement.setElementOrderNumber(tgenPageModelColum.getOrderNumber());
		// GenPage genPage = new GenPage();
		// TgenPage tgenPage =
		// tgenPageModelColum.getTgenPageModel().getTgenPages().iterator().next();
		// BeanUtils.copyProperties(tgenPage, genPage);
		// genElement.setGenPage(genPage);
		// genElementList.add(genElement);
		// }
		//
		// }

		return genElementList;
	}

	public List<EasyUISelectBox> getInputType() {
		ArrayList<EasyUISelectBox> easyUISelectBoxs = new ArrayList<EasyUISelectBox>();

		List<TgenInput> tgenInputs = genInputDao.find("from TgenInput t order by t.orderNum");
		for (TgenInput tgenInput : tgenInputs) {
			EasyUISelectBox easyUISelectBox = new EasyUISelectBox();
			easyUISelectBox.setId(tgenInput.getId());
			easyUISelectBox.setText(tgenInput.getName());
			easyUISelectBoxs.add(easyUISelectBox);
		}

		return easyUISelectBoxs;
	}

	public List<EasyUISelectBox> getSelectBoxGenChecks() {

		List<EasyUISelectBox> selectBoxGenChecks = new ArrayList<EasyUISelectBox>();

		String hql = "from TgenCheck t order by t.orderNumer";
		Map<String, Object> params = new HashMap<>();

		List<TgenCheck> tgenCheckList = genCheckDao.find(hql, params);

		for (TgenCheck tgenCheck : tgenCheckList) {
			EasyUISelectBox easyUISelectBox = new EasyUISelectBox();
			easyUISelectBox.setId(tgenCheck.getId());
			easyUISelectBox.setText(tgenCheck.getName());
			selectBoxGenChecks.add(easyUISelectBox);
		}

		return selectBoxGenChecks;
	}

	/**
	 * 
	 * 描述: 保存规则
	 *
	 * @param genRule
	 *            void
	 */
	public void saveRule(GenRule genRule) {

		String elementId = genRule.getGenElement() == null ? null : genRule.getGenElement().getId();
		String inputId = genRule.getGenInput() == null ? null : genRule.getGenInput().getId();
		String checkId = genRule.getGenCheck() == null ? null : genRule.getGenCheck().getId();

		TgenRule tgenRule = new TgenRule();

		BeanUtils.copyProperties(genRule, tgenRule);

		// 处理默认值
		if (StringUtils.isBlank(tgenRule.getRuleIsmust())) {
			tgenRule.setRuleIsmust("0");
		}
		if (tgenRule.getRuleLength() == null) {
			tgenRule.setRuleLength(100);
			;
		}
		if (StringUtils.isBlank(inputId)) {
			inputId = "1";
		}
		if (StringUtils.isBlank(checkId)) {
			checkId = "1";
		}

		// 处理与元素的关系
		tgenRule.setTgenElement(new TgenElement(elementId));
		// 如果数据中不存在则新建
		if (StringUtils.isBlank(genRule.getId())) {
			tgenRule.setId(StringUtil.generateUUID());
			genRuleDao.save(tgenRule);
		}

		// 处理与控件类型关系
		tgenRule.setTgenInput(new TgenInput(inputId));
		// 处理与校验类型的关系
		tgenRule.setTgenCheck(new TgenCheck(checkId));

		genRuleDao.saveOrUpdate(tgenRule);

	}

	/**
	 * 根据规则id获取html
	 * 
	 * @param id
	 * @return
	 */
	public String generatorHtmlByruleId(String id) {
		TgenRule tgenRule = genRuleDao.get(TgenRule.class, id);
		StringBuffer bf = new StringBuffer();

		switch (tgenRule.getTgenInput().getId()) {
		case "1":// 普通输入框
			generatorText(tgenRule, bf);
			break;
		case "2":// 下拉框
			generatorCombobox(tgenRule, bf);
			break;
		case "3":// 多行文本
			generatortextarea(tgenRule, bf);
			break;
		case "4":// 大文本(KindEdit)
			generatorText(tgenRule, bf);
			break;
		case "5":// 上传组件(uploadify)
			generatorText(tgenRule, bf);
			break;
		case "6":// 下拉树
			generatorText(tgenRule, bf);
			break;

		default:
			break;
		}
		return bf.toString();
	}

	// 多行文本
	private void generatortextarea(TgenRule tgenRule, StringBuffer bf) {

		String defaultWidth = "200px";
		String defaultHeight = "200px";
		String defaultlength = "100";

		String id = tgenRule.getTgenElement().getElementName() + "Id";
		String name = tgenRule.getTgenElement().getElementName();
		String height = defaultHeight;// xxxxxxxxxxxx
		String width = tgenRule.getTgenElement().getElementWidth() == null ? defaultWidth
				: tgenRule.getTgenElement().getElementWidth().toString() + "px";
		String multiline = "true";
		String length = tgenRule.getRuleLength() == null ? defaultlength : tgenRule.getRuleLength().toString();

		String inputStart = "<input type=\"text\" id=\"" + id + "\" name=\"" + name + "\" class=\"easyui-textbox\"\n"
				+ "            style=\"height: " + height + "; width: " + width + ";\" \n";
		bf.append(inputStart);

		String data_options_start = "            data-options=\"";
		bf.append(data_options_start);

		// 处理必填项
		if ("1".equals(tgenRule.getRuleIsmust())) {// 必填
			bf.append("required:true,");
		} else {// 必填非必填
			bf.append("required:false,");
		}
		// 是否多行
		bf.append("multiline:" + multiline + ",");

		String validType_start = " validType:[";
		bf.append(validType_start);

		// 处理校验类型
		bf.append("'length[0," + length + "]',");

		String validType_end = "]";
		bf.append(validType_end);
		String data_options_end = "\"";
		bf.append(data_options_end);
		bf.append("/>");

		generatorThTd(tgenRule, bf);
	}

	// 生成combobox
	private void generatorCombobox(TgenRule tgenRule, StringBuffer bf) {

		String id = tgenRule.getTgenElement().getElementName() + "Id";
		String name = tgenRule.getTgenElement().getElementName();
		String value = "";

		String valueField = "id";
		String textField = "text";
		String url = tgenRule.getRuleSource();

		String inputStart = "<input id=\"" + id + "\" name=\"" + name + "\" value=\"" + value + "\" /> \n";
		bf.append(inputStart);

		String javaScript_start = "<script type=\"text/javascript\">\n" + "    $('#" + id + "').combobox({    \n"
				+ "        url:'" + url + "',    \n" + "        valueField:'" + valueField + "',    \n"
				+ "        textField:'" + textField + "',   \n";

		bf.append(javaScript_start);
		// 处理必填项
		if ("1".equals(tgenRule.getRuleIsmust())) {// 必填
			bf.append("        required:true, \n ");
		} else {// 必填非必填
			bf.append("        required:false, \n");
		}

		String javaScript_end = "  });  \n" + "</script>";
		;
		bf.append(javaScript_end);

		generatorThTd(tgenRule, bf);
	}

	private void generatorText(TgenRule tgenRule, StringBuffer bf) {
		String inputStart = "<input id=\"" + tgenRule.getTgenElement().getElementName() + "Id\" name=\""
				+ tgenRule.getTgenElement().getElementName() + "\" class=\"easyui-validatebox\" ";
		bf.append(inputStart);
		String data_options_start = "data-options=\"";
		bf.append(data_options_start);

		// 处理必填项
		if ("1".equals(tgenRule.getRuleIsmust())) {// 必填
			bf.append("required:true,");
		} else {// 必填非必填
			bf.append("required:false,");
		}

		String validType_start = " validType:[";
		bf.append(validType_start);

		// 处理校验类型
		String checkContext = tgenRule.getTgenCheck().getCheckContext();
		bf.append("'").append(checkContext).append("'");

		if (StringUtils.isNotBlank(tgenRule.getRuleLength().toString())) {
			bf.append(",'length[0," + tgenRule.getRuleLength().toString() + "]'");
		} else {
			bf.append(",'length[0,100]'");
		}

		String validType_end = "]";
		bf.append(validType_end);
		String data_options_end = "\"";
		bf.append(data_options_end);
		bf.append("/>");

		generatorThTd(tgenRule, bf);
	}

	/**
	 * 生成Th,Td
	 * 
	 * @param tgenRule
	 * @param bf
	 */
	private void generatorThTd(TgenRule tgenRule, StringBuffer bf) {
		String td_start = "<td>\n";
		String td_end = "\n</td>\n";
		bf.insert(0, td_start);
		bf.append(td_end);

		String th_content = tgenRule.getTgenElement().getElementNameRemark();
		String th_start = "<th>";
		String th_end = "</th>\n";

		bf.insert(0, th_start + th_content + th_end);
	}

	/**
	 * 
	 * 描述: 根据元素的ID获取规则
	 *
	 * @param elementId
	 * @return GenRule
	 */
	public GenRule getRuleByElementId(String elementId) {
		GenRule genRule = new GenRule();
		TgenRule tgenRule = genRuleDao.get(TgenRule.class, elementId);
		if (tgenRule != null) {
			BeanUtils.copyProperties(tgenRule, genRule);
			if (tgenRule.getTgenCheck() != null) {
				genRule.getGenCheck().setId(tgenRule.getTgenCheck().getId());
			}
			if (tgenRule.getTgenInput() != null) {
				genRule.getGenInput().setId(tgenRule.getTgenInput().getId());
			}
		}

		return genRule;
	}

	/**
	 * 根据页面Id生成html
	 * 
	 * @param pageId
	 *            页面ID
	 * @return
	 */
	public String generatorHtmlForOnePage(String pageId) {

		StringBuffer sb = new StringBuffer();
		sb.append("");
		TgenPage tgenPage = genPageDao.get(TgenPage.class, pageId);

		if (tgenPage != null) {

			String tr_start = "<tr>\n";
			String tr_end = "</tr>\n";

			StringBuffer sb2 = new StringBuffer();

			List<TgenElement> tempList = new ArrayList<>(tgenPage.getTgenElements());
			Collections.sort(tempList, new Comparator<TgenElement>() {
				@Override
				public int compare(TgenElement o1, TgenElement o2) {
					return o1.getElementOrderNumber() - o2.getElementOrderNumber();
				}
			});

			for (int i = 0; i < tempList.size() + 2; i = i + 2) {

				TgenElement tgenElement1 = null;
				TgenElement tgenElement2 = null;
				String tgenElementHtml1 = "";
				String tgenElementHtml2 = "";
				try {
					tgenElement1 = tempList.get(i);
					tgenElementHtml1 = generatorHtmlByruleId(tgenElement1.getTgenRule().getId());
				} catch (Exception e1) {
					continue;
				}
				try {
					tgenElement2 = tempList.get(i + 1);
					tgenElementHtml2 = generatorHtmlByruleId(tgenElement2.getTgenRule().getId());
				} catch (Exception e) {
				}

				boolean oneIsOneRow = false;

				if (tgenElement1 != null) {
					if (!"1".equals(tgenElement1.getIsGenerator())) {// 如果不生成
						i = i - 1;// 第二条数据下次循环重新分析
						continue;
					}
					if ("3".equals(tgenElement1.getTgenRule().getTgenInput().getId())
							|| "4".equals(tgenElement1.getTgenRule().getTgenInput().getId())
							|| "5".equals(tgenElement1.getTgenRule().getTgenInput().getId())) {
						sb2.append(tgenElementHtml1);
						sb2.insert(0, tr_start);
						sb2.append(tr_end);
						sb.append(sb2.toString());
						sb2 = new StringBuffer();
						oneIsOneRow = true;
						// i = i - 1;// 第二条数据下次循环重新分析
						// continue;
					} else {
						sb2.append(tgenElementHtml1);
					}
				}

				if (tgenElement2 != null) {

					if (!"1".equals(tgenElement2.getIsGenerator())) {// 如果不生成
						if (tgenElement1 != null) {
							sb2.insert(0, tr_start);
							sb2.append(tr_end);
							sb.append(sb2.toString());
							sb2 = new StringBuffer();
						}
						continue;
					}
					if ("3".equals(tgenElement2.getTgenRule().getTgenInput().getId())
							|| "4".equals(tgenElement2.getTgenRule().getTgenInput().getId())
							|| "5".equals(tgenElement2.getTgenRule().getTgenInput().getId())) {
						// 给第一条添加tr
						if (!oneIsOneRow) {
							sb2.insert(0, tr_start);
							sb2.append(tr_end);
							sb.append(sb2.toString());
							sb2 = new StringBuffer();
						}
					}
					sb2.append(tgenElementHtml2);
					sb2.insert(0, tr_start);
					sb2.append(tr_end);
					sb.append(sb2.toString());
					sb2 = new StringBuffer();
				} else {
					if (tgenElement1 != null) {
						sb2.insert(0, tr_start);
						sb2.append(tr_end);
						sb.append(sb2.toString());
						sb2 = new StringBuffer();
					}
				}

			}

		}

		// String divTable_start = " <div class=\"easyui-layout\"
		// data-options=\"fit : true,border : false\">\n"
		// + " <div data-options=\"region:'center',border:false\">\n"
		// + " <table class=\"table table-hover table-condensed\">\n";
		//
		// String divTable_end = " </table>\n" + " </div>\n" + " </div>";
		// sb.insert(0, divTable_start);
		// sb.append(divTable_end);

		// addHtmlHeadBody(sb);

		sb.insert(0, "<table class=\"table table-hover table-condensed\">\n");
		sb.append("</table>\n");
		return sb.toString();
	}

	/**
	 * 添加html的head和body
	 * 
	 * @param sb
	 */
	private void addHtmlHeadBody(StringBuffer sb) {

		String start = "<%@ page import=\"cn.com.infcn.core.util.StringUtil\"%>\n"
				+ "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>\n"
				+ "<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>\n"
				+ "<%@ taglib prefix=\"fn\" uri=\"http://java.sun.com/jsp/jstl/functions\"%>\n" + "<!DOCTYPE html>\n"
				+ "<html>\n" + "<head>\n" + "<title>内容管理</title>\n"
				+ "<jsp:include page=\"/WEB-INF/inc/systemInc.jsp\"></jsp:include>\n"
				+ "<script type=\"text/javascript\">\n" + "  $(function() {\n" + "    initData();\n"
				+ "    initEasyUI();\n" + "  });\n" + "  function initData() {\n" + "  }\n"
				+ "  function initEasyUI() {\n" + "  }\n" + "</script>\n" + "</head>\n" + "<body>\n";

		String end = "</body>\n" + "</html>";

		sb.insert(0, start);
		sb.append(end);

	}

}
