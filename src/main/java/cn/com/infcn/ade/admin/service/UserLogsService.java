package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.model.TuserLogs;
import cn.com.infcn.core.pageModel.AdminLogs;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.UserLogs;
import cn.com.infcn.core.util.DateUtil;
import cn.com.infcn.core.util.StringUtil;

/**
 * 
 * @author songgz
 * 
 */
@Service
public class UserLogsService {

	private static final Logger LOG = Logger.getLogger(UserLogsService.class);

	@Autowired
	private BaseDaoI<TuserLogs> userLogsDao;
	@Autowired
	private BaseDaoI<Tuser> userDao;

	public EasyUIDataGrid dataGrid(UserLogs logInfoSys, PageHelper ph, String ids) {

		EasyUIDataGrid dg = new EasyUIDataGrid();

		List<AdminLogs> ul = new ArrayList<AdminLogs>();

		Map<String, Object> params = new HashMap<String, Object>();

		String hql = " from TuserLogs t";

		if (ids != null && !"".equals(ids)) {
			String whereIn = " ";
			for (String tempId : ids.split(",")) {
				tempId = StringUtil.trim(tempId);
				whereIn += ("'" + tempId + "'" + ", ");
			}
			whereIn = whereIn.substring(0, whereIn.lastIndexOf(",")) + " ";
			hql += " and t.id in (" + whereIn + ") ";
		}

		List<TuserLogs> l = userLogsDao.find(hql + whereHql(logInfoSys, params) + orderHql(ph), params, ph.getPage(),
				ph.getRows());
		if (l != null && l.size() > 0) {
			for (TuserLogs t : l) {
				AdminLogs d = new AdminLogs();
				BeanUtils.copyProperties(t, d);
				ul.add(d);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userLogsDao.count("select count(*) " + hql + whereHql(logInfoSys, params), params));
		return dg;
	}

	public EasyUIDataGrid getUserLogs(UserLogs userLogs, PageHelper ph) {

		// 防止反编译用
		try {
			if (654789 == new Random().nextInt()) {
				throw new Exception("try again 654789 == new Random().nextInt()");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try { // 防止反编译用
				if (654789 == new Random().nextInt()) {
					throw new Exception("try again 654789 == new Random().nextInt()");
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<UserLogs> userLogsList = new ArrayList<UserLogs>();
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" from TuserLogs t ");
		hql.append(whereHql(userLogs, params));
		hql.append(orderHql(ph));
		List<TuserLogs> list = userLogsDao.find(hql.toString(), params, ph.getPage(), ph.getRows());
		if (list != null) {
			for (TuserLogs t : list) {
				UserLogs u = new UserLogs();
				// u.setUserId(t.getTuser().getId());
				// u.setUserName(t.getTuser().getUserName());
				// u.setUserId(t.getUserId());
				u.setUserName(t.getUserId());
				// if (t.getLogType() != 0) {
				BeanUtils.copyProperties(t, u);
				// }
				userLogsList.add(u);
			}
		}
		dg.setRows(userLogsList);
		dg.setTotal(userLogsDao.count("select count(*) from TuserLogs t " + whereHql(userLogs, params), params));
		return dg;
	}

	public EasyUIDataGrid getSearchWordsStatistics(PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<AdminLogs> userLogsList = new ArrayList<AdminLogs>();
		Map<String, Object> params = new HashMap<String, Object>();

		String sql = "SELECT t.VALUE2,COUNT(t.VALUE2) AS num FROM edu_user_logs t WHERE t.LOG_TYPE = :logType GROUP BY t.VALUE2 ORDER BY num DESC";
		// params.put("logType", CodeConstant.LOG_TYPE_DOUCMENT_SEARCH);

		List<Object[]> list = userLogsDao.findBySql(sql, params, ph.getPage(), ph.getRows());

		if (list != null) {
			for (Object[] objects : list) {

				AdminLogs u = new AdminLogs();
				u.setValue2(objects[0].toString());
				u.setTimes(Integer.valueOf(objects[1].toString()));
				userLogsList.add(u);
			}
		}
		dg.setRows(userLogsList);
		dg.setTotal(userLogsDao.countBySql("select count(*) from (" + sql + ") t", params).longValue());

		return dg;
	}

	/**
	 * 拼接的where语句
	 * 
	 * @param dingml
	 * @param params
	 * @return
	 */
	private String whereHql(UserLogs userLogs, Map<String, Object> params) {

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

	public void add(UserLogs logInfoSys, HttpServletRequest request) throws Exception {
		TuserLogs t = new TuserLogs();
		// 获得当前IP地址
		BeanUtils.copyProperties(logInfoSys, t);
		if (logInfoSys.getId() == null) {
			t.setId(StringUtil.generateUUID());
			t.setOperateTime(new Date());

			userLogsDao.save(t);
		}
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

	/**
	 * 删除历史记录
	 * 
	 * @param id
	 */

	public void deleteUserLog(String id) {

		// 防止反编译用
		try {
			if (654789 == new Random().nextInt()) {
				throw new Exception("try again 654789 == new Random().nextInt()");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try { // 防止反编译用
				if (654789 == new Random().nextInt()) {
					throw new Exception("try again 654789 == new Random().nextInt()");
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		userLogsDao.delete(userLogsDao.get(TuserLogs.class, id));
	}

	public void log(UserLogs simpleUserLog, String logType, String content) {

		// 防止反编译用
		try {
			if (654789 == new Random().nextInt()) {
				throw new Exception("try again 654789 == new Random().nextInt()");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		} finally {
			try { // 防止反编译用
				if (654789 == new Random().nextInt()) {
					throw new Exception("try again 654789 == new Random().nextInt()");
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		log(simpleUserLog, logType, content, null, null);
	}

	public void log(UserLogs simpleUserLog, String logType, String content, String value1) {

		log(simpleUserLog, logType, content, value1, null);
	}

	public void log(UserLogs simpleUserLog, String logType, String content, String value1, String value2) {

		simpleUserLog.setId(StringUtil.generateUUID());
		simpleUserLog.setValue1(value1);
		simpleUserLog.setValue2(value2);
		simpleUserLog.setContent(content);
		simpleUserLog.setOperateTime(new Date());
		simpleUserLog.setLogType(logType);

		TuserLogs tuserLogs = new TuserLogs();
		BeanUtils.copyProperties(simpleUserLog, tuserLogs);

		Tuser tuser = new Tuser();
		tuser.setId(simpleUserLog.getUserId());

		// tuserLogs.setTuser(tuser);
		tuserLogs.setUserId(simpleUserLog.getUserName());

		userLogsDao.save(tuserLogs);
	}

	public List<UserLogs> getCorrelativeWords(String keyword) {

		String hql = " from TuserLogs t where t.value2 like :keyword and t.logType = :logType group by t.value2 order by t.operateTime desc ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyword", "%" + keyword + "%");
		// params.put("logType", CodeConstant.LOG_TYPE_DOUCMENT_SEARCH);

		List<TuserLogs> tlist = userLogsDao.find(hql, params, 0, 10);
		List<UserLogs> list = new ArrayList<UserLogs>();

		for (TuserLogs t : tlist) {
			UserLogs u = new UserLogs();
			// 相关检索词不包含正在检索的词
			if (!keyword.equals(t.getValue2())) {
				u.setValue2(t.getValue2());
				list.add(u);
			}
		}

		return list;

	}

	public List<String> getUserAutoWords(List<String> list, String keyword, String userId) {

		// 获得当前用户的相关检索词
		String hql = " from TuserLogs t where t.tuser.id = :userId and  t.logType = :logType and t.value2 like :keyword group by t.value2 order by t.operateTime desc ";

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		// params.put("logType", CodeConstant.LOG_TYPE_DOUCMENT_SEARCH);
		params.put("keyword", "%" + keyword + "%");

		List<TuserLogs> tlist = userLogsDao.find(hql, params, 0, 10);

		for (TuserLogs t : tlist) {
			list.add(t.getValue2());
		}

		return list;

	}

	public EasyUIDataGrid getRecordId(String id, String tableName, String userName, String beginDate, String endDate,
			PageHelper ph) {
		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<AdminLogs> list = new ArrayList<AdminLogs>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TuserLogs t where 1=1";
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

		List<TuserLogs> listt = userLogsDao.find(hql, params);
		if (listt != null && listt.size() > 0) {
			for (TuserLogs tAdmLogInfoSys : listt) {
				AdminLogs admLogInfoSys = new AdminLogs();
				BeanUtils.copyProperties(tAdmLogInfoSys, admLogInfoSys);
				list.add(admLogInfoSys);
			}
			dg.setRows(list);
			dg.setTotal(userDao.count("select count(*)" + hql, params));

		}
		return dg;
	}
}
