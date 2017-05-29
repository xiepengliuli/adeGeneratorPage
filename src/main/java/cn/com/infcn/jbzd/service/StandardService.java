package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.modelAdd.TattachStandard;
import cn.com.infcn.core.modelAdd.Tdisease;
import cn.com.infcn.core.modelAdd.Tstandard;
import cn.com.infcn.core.modelAdd.Tsuggest;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.UploadFileModel;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.AttachStandard;
import cn.com.infcn.core.pageModelAdd.DiseasePrivilege;
import cn.com.infcn.core.pageModelAdd.Standard;
import cn.com.infcn.core.pageModelAdd.Suggest;
import cn.com.infcn.core.util.Tools;
import cn.com.infcn.core.util.UserUtil;

/**
 * 描述: TODO
 * 
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class StandardService {
	@Autowired
	private BaseDaoI<Tstandard> standardDao;
	@Autowired
	private BaseDaoI<TattachStandard> attachStandardDao;
	@Autowired
	private BaseDaoI<Tsuggest> suggestDao;
	@Autowired
	private BaseDaoI<Tuser> userDao;

	@Autowired
	private DiseaseService diseaseService;

	/**
	 * 描述: EasyUI-DataGrid访问的方法
	 * 
	 * @param standard
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	public EasyUIDataGrid dataGrid(Standard standard, PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<Standard> ul = new ArrayList<Standard>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tstandard t ";

		List<Tstandard> l = standardDao.find(hql + whereHql(standard, params) + orderHql(ph), params, ph.getPage(),
				ph.getRows());

		if (l != null && l.size() > 0) {

			Tuser user = null;
			if (StringUtils.isNotBlank(standard.getUserId())) {

				user = userDao.get(Tuser.class, standard.getUserId());

			}
			for (Tstandard t : l) {
				Standard u = new Standard();
				BeanUtils.copyProperties(t, u);
				if (user != null) {

					setStandardPrivilege(u, user, standard.getTdiseaseId());

				}
				ul.add(u);
			}
		}

		dg.setRows(ul);
		dg.setTotal(standardDao.count("select count(*) " + hql + whereHql(standard, params), params));

		return dg;
	}

	/**
	 * 描述: 拼接whereHql语句
	 * 
	 * @param standard
	 * @param params
	 * @return String
	 */
	private String whereHql(Standard standard, Map<String, Object> params) {
		String hql = "";
		hql += " where 1=1 ";
		// 疾病ID
		if (StringUtils.isNotBlank(standard.getTdiseaseId())) {
			hql += " and  t.tdisease.id=:diseaseId";
			params.put("diseaseId", standard.getTdiseaseId());
		}
		// 中医病名
		if (StringUtils.isNotBlank(standard.getCnTitle())) {
			hql += " and  t.cnTitle like :cnTitle";
			params.put("cnTitle", "%" + standard.getCnTitle() + "%");
		}
		// 发布机构
		if (StringUtils.isNotBlank(standard.getPublishOrg())) {
			hql += " and  t.publishOrg like :publishOrg";
			params.put("publishOrg", "%" + standard.getPublishOrg() + "%");
		}
		// 编辑人
		if (StringUtils.isNotBlank(standard.getUpdateUser())) {
			hql += " and  t.updateUser like :updateUser";
			params.put("updateUser", "%" + standard.getUpdateUser() + "%");
		}
		// 最后修改时间
		if (standard.getUpdateTimeStart() != null) {
			hql += " and t.updateTime >= :updateTimeStart";
			params.put("updateTimeStart", standard.getUpdateTimeStart());
		}
		if (standard.getUpdateTimeEnd() != null) {
			hql += " and t.updateTime <= :updateTimeEnd";
			params.put("updateTimeEnd", standard.getUpdateTimeEnd());
		}

		// 修改时间段
		if (standard.getPublishDateStart() != null) {
			hql += " and t.publishDate >= :publishDateStart";
			params.put("publishDateStart", standard.getPublishDateStart());
		}
		if (standard.getPublishDateEnd() != null) {
			hql += " and t.publishDate <= :publishDateEnd";
			params.put("publishDateEnd", standard.getPublishDateEnd());
		}

		// 是否删除
		if (StringUtils.isNotBlank(standard.getIsDel())) {
			hql += " and t.isDel =:isDel";
			params.put("isDel", standard.getIsDel());
		}
		// 标准类型
		if (StringUtils.isNotBlank(standard.getType())) {
			hql += " and t.type =:type";
			params.put("type", standard.getType());
		}
		// //状态
		if (StringUtils.isNotBlank(standard.getStatus())) {
			hql += " and t.status =:status";
			params.put("status", standard.getStatus());
		}
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
	 * 
	 * 描述: 添加
	 * 
	 * @param standard
	 * @param saveAndSubmit
	 *            不为空表示保存并提交审核
	 * @throws Exception
	 *             void
	 */
	public void add(Standard standard, String saveAndSubmit) throws Exception {

		Tstandard u = new Tstandard();

		BeanUtils.copyProperties(standard, u);
		Date newDate = new Date();
		u.setCreateTime(newDate);
		u.setUpdateTime(newDate);
		u.setId(Tools.getUUID());
		u.setIsDel(CodeConstant.STANDARD_ISDEL_NORMAL);
		if (StringUtils.isNotBlank(saveAndSubmit)) {
			u.setStatus(CodeConstant.STANDARD_STUTUS_AUDIT_WAIT);
		} else {
			u.setStatus(CodeConstant.STANDARD_STUTUS_NEW);
		}

		Tdisease tdisease = new Tdisease();
		tdisease.setId(standard.getTdiseaseId());
		u.setTdisease(tdisease);

		for (UploadFileModel uploadFileModel : standard.getAttachs()) {
			TattachStandard tattachStandard = new TattachStandard();
			tattachStandard.setTstandard(u);
			tattachStandard.setName(uploadFileModel.getFileName_old());
			tattachStandard.setOrderNum(1);// ?????????
			tattachStandard.setSize(Long.toString(uploadFileModel.getSize()));
			tattachStandard.setUrl(uploadFileModel.getFilePath());

			tattachStandard.setId(Tools.getUUID());
			tattachStandard.setCreateTime(new Date());
			tattachStandard.setCreateUser(standard.getCreateUser());
			attachStandardDao.save(tattachStandard);
		}

		standardDao.save(u);

		BeanUtils.copyProperties(u, standard);
	}

	/**
	 * 描述: 根据id获取
	 * 
	 * @param id
	 * @return Standard
	 */
	public Standard get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<Tstandard> ts = standardDao.find("select distinct t from Tstandard t  where t.id = :id ", params);

		Standard u = new Standard();
		if (ts != null && ts.size() > 0) {
			Tstandard t = ts.get(0);
			BeanUtils.copyProperties(t, u);
			Set<TattachStandard> tattachStandards = t.getTattachStandards();
			if (tattachStandards != null) {
				for (TattachStandard tattachStandard : tattachStandards) {
					AttachStandard attachStandard = new AttachStandard();
					BeanUtils.copyProperties(tattachStandard, attachStandard);
					u.getAttachsPage().add(attachStandard);
				}
			}
		}

		return u;
	}

	/**
	 * 描述: 根据id获取
	 * 
	 * @param tdiseaseId
	 * @return Standard
	 */
	public Standard getObjContailsPrivilege(String tstandardId, String userId) {

		Map<String, Object> params = new HashMap<String, Object>();
		Standard u = new Standard();

		params.put("tstandardId", tstandardId);
		List<Tstandard> ts = standardDao.find("select distinct t from Tstandard t  where t.id = :tstandardId ", params);
		if (ts != null && ts.size() > 0) {

			Tstandard t = ts.get(0);

			BeanUtils.copyProperties(t, u);

			// 处理附件
			Set<TattachStandard> tattachStandards = t.getTattachStandards();
			if (tattachStandards != null) {
				for (TattachStandard tattachStandard : tattachStandards) {
					AttachStandard attachStandard = new AttachStandard();
					BeanUtils.copyProperties(tattachStandard, attachStandard);
					u.getAttachsPage().add(attachStandard);
				}
			}
			// 处理用户的权限
			Tuser user = userDao.get(Tuser.class, userId);
			if (user != null) {
				setStandardPrivilege(u, user, t.getTdisease().getId());
			}

		}

		return u;
	}

	/**
	 * 
	 * 描述: 根据用户设置标准的权限
	 * 
	 * @param standard
	 *            标准pageModel
	 * @param user
	 *            暂时用了用户登录名
	 * @param tdiseaseId
	 *            用户疾病ID void
	 */
	private void setStandardPrivilege(Standard standard, Tuser user, String tdiseaseId) {

		if (StringUtils.isNotBlank(user.getId())) {

			DiseasePrivilege diseasePrivilege = diseaseService.getPrivilegeByUserIdAndTdiseaseId(tdiseaseId,
					user.getId());
			// 超级管理员
			if (user.getId().equals("0")) {
				diseasePrivilege.setCanEdit(true);
				diseasePrivilege.setCanAudit(true);
				diseasePrivilege.setCanPublish(true);
				standard.setCanDelete(true);
				standard.setCanEdit(true);
			}

			standard.setCanPreview(true);

			if (diseasePrivilege.isCanEdit()) {
				switch (standard.getStatus()) {
				case "1":// 新建
					standard.setCanEdit(true);
					standard.setCanDelete(true);
					standard.setCanAuditSubmit(true);
					break;
				case "4":// 审核未通过
					standard.setCanEdit(true);
					standard.setCanAuditSubmit(true);
					break;
				default:
					break;
				}

			}
			if (diseasePrivilege.isCanAudit()) {
				switch (standard.getStatus()) {
				case "1":// 新建
					if (user.getLoginName().equals(standard.getCreateUser()) && !diseasePrivilege.isCanEdit()) {// 如果创建者是自己并没有编辑权限
						standard.setCanEdit(true);
						standard.setCanDelete(true);
						standard.setCanAuditSubmit(true);
					}
					break;
				case "2": // 待审核
					standard.setCanEdit(true);
					standard.setCanAudit(true);
					break;
				case "6": // 发布未通过
					standard.setCanEdit(true);
					standard.setCanAudit(true);
					break;
				default:
					break;
				}
			}
			if (diseasePrivilege.isCanPublish()) {

				switch (standard.getStatus()) {
				case "3":// 审核通过
					standard.setCanEdit(true);
					standard.setCanPublish(true);
					break;
				case "5":// 发布通过
					standard.setCanRevokePublish(true);
					break;
				case "7":// 发布撤销
					standard.setCanEdit(true);
					standard.setCanPublish(true);
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * 描述: 编辑
	 * 
	 * @param standard
	 * @throws Exception
	 *             void
	 */
	public void edit(Standard standard) throws Exception {
		Tstandard u = standardDao.get(Tstandard.class, standard.getId());

		String[] ignoreProperties = new String[] { "createTime", "createUser", "status", "isDel" };
		BeanUtils.copyProperties(standard, u, ignoreProperties);

		if (standard.getAttachs().size() > 0) {
			for (UploadFileModel uploadFileModel : standard.getAttachs()) {
				TattachStandard tattachStandard = new TattachStandard();
				tattachStandard.setTstandard(u);
				tattachStandard.setName(uploadFileModel.getFileName_old());
				tattachStandard.setOrderNum(1);// ?????????
				tattachStandard.setSize(Long.toString(uploadFileModel.getSize()));
				tattachStandard.setUrl(uploadFileModel.getFilePath());

				tattachStandard.setId(Tools.getUUID());
				tattachStandard.setCreateTime(new Date());
				tattachStandard.setCreateUser(standard.getCreateUser());
				attachStandardDao.save(tattachStandard);
			}
		}

		u.setUpdateTime(new Date());

		standardDao.update(u);
	}

	/**
	 * 描述: 根据id删除
	 * 
	 * @param id
	 * @param userId
	 *            根据用户判断权限 void
	 */
	public void deleteById(String id, String userId) {
		if (StringUtils.isNotBlank(id)) {
			Standard standard = getObjContailsPrivilege(id, userId);
			if (standard != null && standard.isCanDelete()) {
				Tstandard tstandard = standardDao.get(Tstandard.class, id);
				Map<String, Object> params = new HashMap<>();
				params.put("id", id);
				attachStandardDao.executeHql("delete from TattachStandard t where t.tstandard.id=:id", params);
				attachStandardDao.executeHql("delete from Tsuggest t where t.tstandard.id=:id", params);
				standardDao.delete(tstandard);
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

		List<Standard> standardList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
		pageHelper.setData(standardList);
		// 获取总记录数
		hql = "select count(*) " + hql;
		int rowCount = standardDao.count(hql, params).intValue();
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
	 * @return List<Standard>
	 */
	public List<Standard> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

		List<Standard> standardList = new ArrayList<Standard>();

		List<Tstandard> tempList = standardDao.find(hql, params, pageNum, Pagecount);

		for (Tstandard tstandard : tempList) {
			Standard standard = new Standard();
			BeanUtils.copyProperties(tstandard, standard);

			standardList.add(standard);
		}

		return standardList;

	}

	/**
	 * 根据ID标准信息
	 */
	public Standard getByStandardId(String id) {
		Tstandard tstandard = standardDao.get(Tstandard.class, id);
		Standard standard = new Standard();
		BeanUtils.copyProperties(tstandard, standard);
		return standard;
	}

	/**
	 * 获取标准检索
	 */
	public List<Standard> standardSearch(String feilds, String isLike, String keyWord) {

		List<Standard> StandardList = getStandardDataBySimpleQ(feilds, isLike, keyWord);

		return StandardList;
	}

	/**
	 * 描述: 根据简单检索获取分组数据
	 * 
	 * @param isLike
	 * @param keyWord
	 * @return List<Standard>
	 */
	public List<Standard> getStandardDataBySimpleQ(String feilds, String isLike, String keyWord) {

		List<Standard> standardList = new ArrayList<>();

		String hql = "";
		Map<String, Object> params = new HashMap<>();
		List<Tstandard> tstandardList = new ArrayList<>();
		hql = "from Tstandard t where";
		if (isLike.equals(CodeConstant.QUERY_TYPE_LIKE)) {

			if (CodeConstant.QUERY_cnTitle.equals(feilds)) {// 中文题名
				hql += " t.cnTitle like :keyWord ";

			} else if (CodeConstant.QUERY_publishOrg.equals(feilds)) {// 发布机构
				hql += " t.publishOrg like :keyWord ";
			} else if (CodeConstant.QUERY_mmDiseaseName.equals(feilds)) {// 西医病名
				hql += " t.mmDiseaseName like :keyWord ";
			} else if (CodeConstant.QUERY_tcmDiseaseName.equals(feilds)) {// 中医病名
				hql += " t.tcmDiseaseName like :keyWord ";
			}
			hql += " and t.status ='" + CodeConstant.STANDARD_STUTUS_PUBLISH_PASSED + "'";
			hql += " and t.isDel ='" + CodeConstant.STANDARD_ISDEL_NORMAL + "'";
			params.put("keyWord", "%" + keyWord + "%");
			tstandardList = standardDao.find(hql, params);
		} else {
			if (CodeConstant.QUERY_cnTitle.equals(feilds)) {// 中文题名
				hql += " t.cnTitle = :keyWord ";

			} else if (CodeConstant.QUERY_publishOrg.equals(feilds)) {// 发布机构
				hql += " t.publishOrg = :keyWord ";
			} else if (CodeConstant.QUERY_mmDiseaseName.equals(feilds)) {// 西医病名
				hql += " t.mmDiseaseName = :keyWord ";
			} else if (CodeConstant.QUERY_tcmDiseaseName.equals(feilds)) {// 中医病名
				hql += " t.tcmDiseaseName = :keyWord ";
			}

			hql += " and t.status ='" + CodeConstant.STANDARD_STUTUS_PUBLISH_PASSED + "'";
			hql += " and t.isDel ='" + CodeConstant.STANDARD_ISDEL_NORMAL + "'";

			params.put("keyWord", keyWord);
			tstandardList = standardDao.find(hql, params);
		}

		if (tstandardList != null) {
			for (Tstandard tstandard : tstandardList) {
				Standard standard = new Standard();
				BeanUtils.copyProperties(tstandard, standard);
				standardList.add(standard);
			}
		}
		return standardList;
	}

	/**
	 * 高级查询
	 * 
	 * @param logic
	 *            罗辑值
	 * @param fields
	 *            字段值
	 * @param keywords
	 *            关键字
	 * @param endtime
	 * @param beginTime
	 * @param range
	 *            :模糊，精确：1
	 * @return
	 */
	public List<Standard> advancedQuery(String[] fields, String[] keywords, String[] ranges, String beginTime,
			String endtime) {

		List<Standard> standardList = new ArrayList<Standard>();
		if (fields != null && fields.length > 0) {
			// 获取前台的参数据据
			StringBuilder hql = new StringBuilder("from Tstandard t where 1=1");
			Map<String, Object> params = new HashMap<>();
			String keyword = "";
			String field = "";
			String range = "";
			for (int i = 0; i < keywords.length; i++) {
				keyword = keywords[i];
				if (StringUtils.isNotEmpty(keyword)) {
					// 罗辑数组比其它数据组少一个值
					field = fields[i];
					range = ranges[i];

					if (field.equals("4")) {// 处理中西标准
						
						range = "0";// 精确查询
						
						boolean isContains=false;//是否包含中西医
						
						if (keywords[i].contains("中")) {
							keyword = CodeConstant.STANDARD_TYPE_TCM;
							isContains=true;
						}
						if (keywords[i].contains("西")) {
							keyword = CodeConstant.STANDARD_TYPE_MM;
						}else{
							isContains=false;
						}
						if (isContains) {
							continue;
						}
					}
					
	         hql.append(" and ");

					// 模糊
					if ("1".equals(range)) {
						hql.append("t." + getFieldByValue(field) + " like :keyWord" + i);
						params.put("keyWord" + i, "%" + keyword + "%");
					} else {// 精确
						hql.append("t." + getFieldByValue(field) + " = :keyWord" + i);
						params.put("keyWord" + i, keyword);
					}

				}

			}

			hql.append(" and t.status ='" + CodeConstant.STANDARD_STUTUS_PUBLISH_PASSED + "'");
			hql.append(" and t.isDel ='" + CodeConstant.STANDARD_ISDEL_NORMAL + "'");

			if (beginTime.length() == 4 && endtime.length() == 4) {
				hql.append(" and ");
				hql.append(" publishDate between " + beginTime + " and " + endtime);
			}

			List<Tstandard> tstandards = standardDao.find(hql.toString(), params);
			for (Tstandard tstandard : tstandards) {
				Standard standard = new Standard();
				BeanUtils.copyProperties(tstandard, standard);
				standardList.add(standard);
			}
		}

		return standardList;
	}

	/**
	 * 
	 * @param str
	 *            ：传入的字符串
	 * @return 返回后的字符串
	 */
	private String getFieldByValue(String str) {

		String field = "";
		switch (str) {
		case "1": // 病 种名称
			field = "tdisease.name";
			break;
		case "2": // 中文题名
			field = "cnTitle";
			break;
		case "3": // 英文题名
			field = "enTitle";
			break;
		case "4": // 标准类型
			field = "type";
			break;
		case "5": // 发布机构
			field = "publishOrg";
			break;
		case "6": // 来源
			field = "source";
			break;
		case "7": // 全文语种
			field = "language";
			break;
		case "8": // 西医病名
			field = "mmDiseaseName";
			break;
		case "9": // 分型分期
			field = "typeStag";
			break;
		case "10": // 西医诊断标准
			field = "mmStandard";
			break;
		case "11": // 中医病名
			field = "tcmStandard";
			break;
		case "12": // 中医辨证
			field = "tcmDiseaseDialectical";
			break;
		case "13": // 中医诊断标准
			field = "tcmStandard";
			break;
		case "14": // 疗效指标
			field = "effectTarget";
			break;
		case "15": // 疗效判定指标
			field = "effectStandard";
			break;
		case "16": // 备注
			field = "remark";
			break;
		}
		return field;

	}

	/**
	 * 描述: 批量删除
	 * 
	 * @param ids
	 * @param userId
	 *            void
	 */
	public void deleteBatch(String ids, String userId) {
		if (StringUtils.isNotBlank(ids)) {
			String[] deleteIds = ids.split(",");
			for (String deleteId : deleteIds) {
				deleteById(deleteId, userId);
			}

		}
	}

	/**
	 * 描述: 根据id把数据放到回收站
	 * 
	 * @param id
	 * @param userId
	 *            void
	 */
	public void deleteByIdToRecycleBin(String id, String userId) {
		if (StringUtils.isNotBlank(id)) {

			Standard standard = getObjContailsPrivilege(id, userId);

			if (standard != null && standard.isCanDelete()) {
				Tstandard tstandard = standardDao.get(Tstandard.class, id);
				if (tstandard != null) {
					tstandard.setIsDel(CodeConstant.STANDARD_ISDEL_RECYCLEBIN);
					standardDao.update(tstandard);
				}
			}

		}
	}

	/**
	 * 描述: 根据id数组把数据放到回收站
	 * 
	 * @param ids
	 * @param userId
	 *            void
	 */
	public void deleteBatchByIdsToRecycleBin(String ids, String userId) {
		if (StringUtils.isNotBlank(ids)) {
			String[] deleteIds = ids.split(",");
			for (String deleteId : deleteIds) {
				deleteByIdToRecycleBin(deleteId, userId);
			}
		}
	}

	/**
	 * 描述: 还原
	 * 
	 * @param id
	 *            void
	 */
	public void restore(String id) {
		if (StringUtils.isNotBlank(id)) {
			Tstandard tstandard = standardDao.get(Tstandard.class, id);
			if (tstandard != null) {
				tstandard.setIsDel(CodeConstant.STANDARD_ISDEL_NORMAL);
				standardDao.update(tstandard);
			}
		}
	}

	/**
	 * 描述: 批量还原
	 * 
	 * @param ids
	 *            void
	 */
	public void batchRestore(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] deleteIds = ids.split(",");
			for (String deleteId : deleteIds) {
				Tstandard tstandard = standardDao.get(Tstandard.class, deleteId);
				if (tstandard != null) {
					tstandard.setIsDel(CodeConstant.STANDARD_ISDEL_NORMAL);
					standardDao.update(tstandard);
				}
			}
		}
	}

	/**
	 * 描述: 审核
	 * 
	 * @param suggest
	 * @param userId
	 *            void
	 */
	public void saveAudit(Suggest suggest, String userId) {

		Tstandard tstandard = standardDao.get(Tstandard.class, suggest.getTstandardId());

		if (tstandard != null) {

			Standard standardContailsPrivilege = getObjContailsPrivilege(tstandard.getId(), userId);

			if (!standardContailsPrivilege.isCanAudit()) {
				return;
			}

			if (StringUtils.isNotBlank(suggest.getIsPass()) && suggest.getIsPass().equals("1")) {
				tstandard.setStatus(CodeConstant.STANDARD_STUTUS_AUDIT_PASSED);
			} else {
				tstandard.setStatus(CodeConstant.STANDARD_STUTUS_AUDIT_NOTPASSED);
			}

			Tsuggest tsuggest = new Tsuggest();
			BeanUtils.copyProperties(suggest, tsuggest);
			tsuggest.setId(Tools.getUUID());
			tsuggest.setCreateTime(new Date());
			tsuggest.setTstandard(tstandard);

			suggestDao.save(tsuggest);
			standardDao.update(tstandard);
		}
	}

	/**
	 * 描述: 批量审核
	 * 
	 * @param suggest
	 * @param ids
	 *            void
	 * @param userId
	 */
	public void saveBatchAudit(Suggest suggest, String ids, String userId) {

		if (StringUtils.isNotBlank(ids)) {
			String[] standardIds = ids.split(",");
			for (String standardId : standardIds) {
				suggest.setTstandardId(standardId);

				saveAudit(suggest, userId);
			}
		}
	}

	/**
	 * 
	 * 描述: 发布
	 * 
	 * @param suggest
	 *            接收意见
	 * @param userId
	 *            用户ID void
	 */
	public void savePublish(Suggest suggest, String userId) {

		Tstandard tstandard = standardDao.get(Tstandard.class, suggest.getTstandardId());

		Standard standardContainsPrivilege = getObjContailsPrivilege(tstandard.getId(), userId);
		if (tstandard != null && standardContainsPrivilege != null) {
			if (!standardContainsPrivilege.isCanPublish()) { // 如果发布权限则退出
				return;
			}

			if (StringUtils.isNotBlank(suggest.getIsPass()) && suggest.getIsPass().equals("1")) {
				tstandard.setStatus(CodeConstant.STANDARD_STUTUS_PUBLISH_PASSED);
			} else {
				tstandard.setStatus(CodeConstant.STANDARD_STUTUS_PUBLISH_NOTPASSED);
			}

			Tsuggest tsuggest = new Tsuggest();
			BeanUtils.copyProperties(suggest, tsuggest);
			tsuggest.setId(Tools.getUUID());
			tsuggest.setCreateTime(new Date());
			tsuggest.setTstandard(tstandard);

			suggestDao.save(tsuggest);
			standardDao.update(tstandard);
		}
	}

	/**
	 * 描述: 批量发布
	 * 
	 * @param suggest
	 * @param ids
	 *            void
	 * @param userId
	 */
	public void saveBatchPublish(Suggest suggest, String ids, String userId) {

		if (StringUtils.isNotBlank(ids)) {
			String[] standardIds = ids.split(",");
			for (String standardId : standardIds) {
				suggest.setTstandardId(standardId);
				savePublish(suggest, userId);
			}
		}
	}

	/**
	 * 描述: 撤销发布
	 * 
	 * @param id
	 *            void
	 */
	public void saveRevokePublish(Suggest suggest) {

		Tstandard tstandard = standardDao.get(Tstandard.class, suggest.getTstandardId());

		if (tstandard != null) {
			tstandard.setStatus(CodeConstant.STANDARD_STUTUS_PUBLISH_CANCEL);

			Tsuggest tsuggest = new Tsuggest();
			BeanUtils.copyProperties(suggest, tsuggest);
			tsuggest.setId(Tools.getUUID());
			tsuggest.setCreateTime(new Date());
			tsuggest.setTstandard(tstandard);

			suggestDao.save(tsuggest);

			standardDao.update(tstandard);
		}

	}

	/**
	 * 描述: 提交审核
	 * 
	 * @param suggest
	 *            void
	 */
	public void submitAudit(Suggest suggest) {
		Tstandard tstandard = standardDao.get(Tstandard.class, suggest.getTstandardId());

		if (tstandard != null) {
			tstandard.setStatus(CodeConstant.STANDARD_STUTUS_AUDIT_WAIT);

			Tsuggest tsuggest = new Tsuggest();
			BeanUtils.copyProperties(suggest, tsuggest);
			tsuggest.setId(Tools.getUUID());
			tsuggest.setCreateTime(new Date());
			tsuggest.setTstandard(tstandard);

			suggestDao.save(tsuggest);

			standardDao.update(tstandard);
		}
	}

	/**
	 * 
	 * 描述: 根据疾病ID类型获取意见
	 * 
	 * @param tdiseaseId
	 * @param suggestType
	 * @return List<Suggest>
	 */
	public List<Suggest> getSuggestByTdiseaseIdAndType(String tdiseaseId, String suggestType) {
		return getSuggestByTdiseaseIdAndTypeAndIsPass(tdiseaseId, suggestType, null);
	}

	/**
	 * 描述: 根据类型和是否通过获取意见
	 * 
	 * @param tdiseaseId
	 * @param suggestTypeAudit
	 * @param isPass
	 * @return List<Suggest>
	 */
	public List<Suggest> getSuggestByTdiseaseIdAndTypeAndIsPass(String tdiseaseId, String suggestType, String isPass) {

		List<Suggest> suggestList = new ArrayList<Suggest>();
		String hql = "";
		Map<String, Object> params = new HashMap<>();

		if (StringUtils.isNotBlank(isPass)) {
			hql = "from Tsuggest t where t.tstandard.id=:tdiseaseId and t.type=:suggestType and t.isPass=:isPass order by t.createTime desc";
			params.put("isPass", isPass);
		} else {
			hql = "from Tsuggest t where t.tstandard.id=:tdiseaseId and t.type=:suggestType order by t.createTime desc";
		}

		params.put("tdiseaseId", tdiseaseId);
		params.put("suggestType", suggestType);

		List<Tsuggest> tsuggestList = suggestDao.find(hql, params);

		if (tsuggestList != null) {
			for (Tsuggest tsuggest : tsuggestList) {
				Suggest suggest = new Suggest();
				BeanUtils.copyProperties(tsuggest, suggest);
				suggestList.add(suggest);
			}
		}

		return suggestList;
	}

}
