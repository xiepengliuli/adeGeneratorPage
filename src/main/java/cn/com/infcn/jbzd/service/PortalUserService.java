package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TportalUser;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModelAdd.PortalUser;
import cn.com.infcn.core.util.MD5Util;

/**
 * 描述: TODO
 *
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class PortalUserService {
	@Autowired
	private BaseDaoI<TportalUser> portalUserDao;

	/**
	 * 描述: EasyUI-DataGrid访问的方法
	 *
	 * @param portalUser
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	public EasyUIDataGrid dataGrid(PortalUser portalUser, PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<PortalUser> ul = new ArrayList<PortalUser>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TportalUser t ";
		List<TportalUser> l = portalUserDao.find(hql + whereHql(portalUser, params) + orderHql(ph), params,
				ph.getPage(), ph.getRows());

		if (l != null && l.size() > 0) {
			for (TportalUser t : l) {
				PortalUser u = new PortalUser();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(portalUserDao.count("select count(*) " + hql + whereHql(portalUser, params), params));
		return dg;
	}

	/**
	 * 描述: 拼接whereHql语句
	 *
	 * @param portalUser
	 * @param params
	 * @return String
	 */
	private String whereHql(PortalUser portalUser, Map<String, Object> params) {
		String hql = "";
		if (portalUser == null) {
			return hql;
		}
		hql += " where 1=1 ";
		if (StringUtils.isNotBlank(portalUser.getUserName())) {
			hql += " and t.userName like :userName";
			params.put("userName", "%" + portalUser.getUserName() + "%");
		}
		if (StringUtils.isNotBlank(portalUser.getLoginName())) {
			hql += " and t.loginName like :loginName";
			params.put("loginName", "%" + portalUser.getLoginName() + "%");
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
	 * 描述: 添加
	 *
	 * @param portalUser
	 * @throws Exception
	 *             void
	 */
	synchronized public PortalUser add(PortalUser portalUser) throws Exception {
		TportalUser u = new TportalUser();
		BeanUtils.copyProperties(portalUser, u);

		Date newDate = new Date();
		u.setCreateTime(newDate);
		u.setUpdateTime(newDate);

		u.setPassword(MD5Util.md5(portalUser.getPassword()));
		u.setIsDel("0");
		portalUserDao.save(u);
		
		PortalUser pu = new PortalUser();
		BeanUtils.copyProperties(u, pu);;
		pu.setPassword(null);
		
		return pu;
	}

	/**
	 * 描述: 根据id获取
	 *
	 * @param id
	 * @return PortalUser
	 */
	public PortalUser get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<TportalUser> ts = portalUserDao.find("select distinct t from TportalUser t  where t.id = :id ", params);

		if (ts != null && ts.size() > 0) {
			TportalUser t = ts.get(0);
			PortalUser u = new PortalUser();
			BeanUtils.copyProperties(t, u);
			// TODO
			return u;
		}

		return null;
	}

	/**
	 * 描述: //TODO
	 *
	 * @param portalUser
	 * @throws Exception
	 *             void
	 */
	public void edit(PortalUser portalUser) throws Exception {
		TportalUser u = portalUserDao.get(TportalUser.class, portalUser.getId());
		if (u != null) {
			// TODO
			u.setLoginName(portalUser.getLoginName());
			u.setUserName(portalUser.getUserName());
			u.setUpdateTime(new Date());
			u.setUnit(portalUser.getUnit());
			u.setRemark(portalUser.getRemark());

			if (StringUtils.isNotEmpty(portalUser.getPassword())) {
				u.setPassword(MD5Util.md5(portalUser.getPassword()));
			}

			portalUserDao.update(u);
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
			TportalUser tPortalUser = portalUserDao.get(TportalUser.class, id);
			if (tPortalUser != null) {
				portalUserDao.delete(tPortalUser);
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

		List<PortalUser> portalUserList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
		pageHelper.setData(portalUserList);
		// 获取总记录数
		hql = "select count(*) " + hql;
		int rowCount = portalUserDao.count(hql, params).intValue();
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
	 * @return List<PortalUser>
	 */
	public List<PortalUser> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

		List<PortalUser> portalUserList = new ArrayList<PortalUser>();

		List<TportalUser> tempList = portalUserDao.find(hql, params, pageNum, Pagecount);

		for (TportalUser tportalUser : tempList) {
			PortalUser portalUser = new PortalUser();
			BeanUtils.copyProperties(tportalUser, portalUser);

			portalUserList.add(portalUser);
		}

		return portalUserList;

	}

	/**
	 * 描述:用户登陆验证
	 * 
	 * @param portalUser
	 * @return
	 */
	public PortalUser login(PortalUser portalUser) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", portalUser.getLoginName());
		params.put("password", MD5Util.md5(portalUser.getPassword()));
		String hql = "from TportalUser t where t.loginName = :loginName and t.password = :password";
		TportalUser t = portalUserDao.get(hql, params);
		if (t != null) {
			BeanUtils.copyProperties(t, portalUser);
			if (t.getUserName() != null && !t.getUserName().isEmpty()) {
				String userName = t.getUserName();
				portalUser.setUserName(userName);
			}
			return portalUser;
		}
		return null;
	}

	/**
	 * 描述:修改用户密码
	 * 
	 * @param portalUser
	 * @param newPwd
	 * @return
	 */
	public Json changePassword(String loginName, String oldPwd, String newPwd) {
		Json json = new Json();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		String hql = "from TportalUser t where t.loginName = :loginName";
		TportalUser t = portalUserDao.get(hql, params);
		if (t != null) {
			if (!MD5Util.md5(oldPwd).equals(t.getPassword())) {
				json.setSuccess(false);
				json.setMsg("原始密码输入不正确!");
				return json;
			} else {
				t.setPassword(MD5Util.md5(newPwd));
				portalUserDao.update(t);
				json.setSuccess(true);
				json.setMsg("修改密码成功!");
				return json;
			}

		}
		return null;
	}
}
