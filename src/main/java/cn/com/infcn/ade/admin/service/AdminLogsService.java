package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.core.model.TadminLogs;
import cn.com.infcn.core.pageModel.AdminLogs;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.util.DateUtil;
import cn.com.infcn.core.util.StringUtil;

/**
 * 
 * @author dingml
 * 
 */
@Service
public class AdminLogsService {

	@Autowired
	private BaseDaoI<TadminLogs> adminLogsDao;

	public EasyUIDataGrid dataGrid(AdminLogs logInfoSys, PageHelper ph, String ids) {

		EasyUIDataGrid dg = new EasyUIDataGrid();

		List<AdminLogs> ul = new ArrayList<AdminLogs>();

		Map<String, Object> params = new HashMap<String, Object>();

		String hql = " from TadminLogs t ";

		if (ids != null && !"".equals(ids)) {
			String whereIn = " ";
			for (String tempId : ids.split(",")) {
				tempId = StringUtil.trim(tempId);
				whereIn += ("'" + tempId + "'" + ", ");
			}
			whereIn = whereIn.substring(0, whereIn.lastIndexOf(",")) + " ";
			hql += " and t.id in (" + whereIn + ") ";
		}

		List<TadminLogs> l = adminLogsDao.find(hql + whereHql(logInfoSys, params) + orderHql(ph), params, ph.getPage(),
				ph.getRows());
		if (l != null && l.size() > 0) {
			for (TadminLogs t : l) {
				AdminLogs d = new AdminLogs();
				BeanUtils.copyProperties(t, d);
				ul.add(d);
			}
		}
		dg.setRows(ul);
		dg.setTotal(adminLogsDao.count("select count(*) " + hql + whereHql(logInfoSys, params), params));
		return dg;
	}

	public void add(AdminLogs logInfoSys, HttpServletRequest request) throws Exception {
		TadminLogs t = new TadminLogs();
		// 获得当前IP地址
		BeanUtils.copyProperties(logInfoSys, t);
		if (logInfoSys.getId() == null) {
			t.setId(StringUtil.generateUUID());
			t.setOperateTime(new Date());

			adminLogsDao.save(t);
		}
	}

	public EasyUIDataGrid getAdminLogs(AdminLogs adminLogs, PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<AdminLogs> userLogsList = new ArrayList<AdminLogs>();
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TadminLogs t ");
		hql.append(whereHql(adminLogs, params));
		hql.append(orderHql(ph));
		List<TadminLogs> list = adminLogsDao.find(hql.toString(), params, ph.getPage(), ph.getRows());
		if (list != null) {
			for (TadminLogs t : list) {
				AdminLogs u = new AdminLogs();
				u.setUserName(t.getUserId());
				BeanUtils.copyProperties(t, u);
				userLogsList.add(u);
			}
		}
		dg.setRows(userLogsList);
		dg.setTotal(adminLogsDao.count("select count(*) from TadminLogs t " + whereHql(adminLogs, params), params));
		return dg;
	}

	/**
	 * 拼接的where语句
	 * 
	 * @param dingml
	 * @param params
	 * @return
	 */
	private String whereHql(AdminLogs userLogs, Map<String, Object> params) {

		StringBuffer hql = new StringBuffer();
		if (userLogs != null) {
			hql.append(" where 1=1 ");
			if (StringUtils.isNotBlank(userLogs.getUserId())) {
				hql.append(" and t.userId like:userId");
				params.put("userId", "%" + userLogs.getUserId() + "%");
			}
			if (StringUtils.isNotBlank(userLogs.getLogType())) {
				hql.append(" and t.logType like :logType ");
				params.put("logType", "%%" + userLogs.getLogType() + "%%");
			}
		}
		return hql.toString();
	}

	/**
	 * 拼接的order语句
	 * 
	 * @param dingml
	 * @param params
	 * @return
	 */
	private String orderHql(PageHelper ph) {

		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	public void log(AdminLogs adminLogs) {

		if (adminLogs.getContent().length() > 33) {
			adminLogs.setContent(StringUtils.substring(adminLogs.getContent(), 0, 32));
		}

		TadminLogs t = new TadminLogs();
		BeanUtils.copyProperties(adminLogs, t);
		t.setOperateTime(new Date());
		t.setUserId(adminLogs.getContent());
		t.setId(StringUtil.generateUUID());
		adminLogsDao.save(t);
	}

	public EasyUIDataGrid getRecordId(String id, String tableName, String userName, String beginDate, String endDate,
			PageHelper ph) {
		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<AdminLogs> list = new ArrayList<AdminLogs>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TadminLogs t where 1=1";
		if (id != null && !"".equals(id)) {
			params.put("recordId", id);
			hql += " and t.recordId = :recordId";
		}
		if (tableName != null && !"".equals(tableName)) {
			params.put("tableName", tableName);
			hql += " and t.tableName = :tableName";
		}

		if (userName != null && !"".equals(userName)) {
			params.put("userName", "%%" + userName + "%%");
			hql += " and t.TAdmUser.loginName like :userName";
		}

		if (StringUtils.isNotEmpty(beginDate)) {
			params.put("beginDate", DateUtil.dateValidation(beginDate, "yyyy-MM-dd"));
			hql += " and t.logTime>=:beginDate";
		}
		if (StringUtils.isNotEmpty(endDate)) {
			params.put("endDate", DateUtil.dateValidation(endDate, "yyyy-MM-dd"));
			hql += " and t.logTime<=:endDate";
		}

		hql += " order by t.logTime desc ";

		List<TadminLogs> listt = adminLogsDao.find(hql, params);
		if (listt != null && listt.size() > 0) {
			for (TadminLogs tAdmLogInfoSys : listt) {
				AdminLogs admLogInfoSys = new AdminLogs();
				BeanUtils.copyProperties(tAdmLogInfoSys, admLogInfoSys);
				list.add(admLogInfoSys);
			}
			dg.setRows(list);
			dg.setTotal(adminLogsDao.count("select count(*)" + hql, params));

		}
		return dg;
	}
}
