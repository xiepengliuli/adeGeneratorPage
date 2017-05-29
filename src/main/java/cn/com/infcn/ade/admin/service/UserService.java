package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.model.Tmodule;
import cn.com.infcn.core.model.Trole;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.ChineseCharToEn;
import cn.com.infcn.core.util.MD5Util;
import cn.com.infcn.core.util.StringUtil;

@Service
public class UserService {

	@Autowired
	private BaseDaoI<Tuser> userDao;

	@Autowired
	private BaseDaoI<Trole> roleDao;

	@Autowired
	private BaseDaoI<Tmodule> moduleDao;

	public User login(User user) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", user.getLoginName());
		params.put("password", MD5Util.md5(user.getPassword()));
		Tuser t = userDao.get("from Tuser t where t.loginName = :loginName and t.password = :password", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			if (t.getTroles() != null && !t.getTroles().isEmpty()) {
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				for (Trole role : t.getTroles()) {
					if (b) {
						roleIds += ",";
						roleNames += ",";
					} else {
						b = true;
					}
					roleIds += role.getId();
					roleNames += role.getRoleName();
				}
				user.setRoleIds(roleIds);
				user.setRoleNames(roleNames);
			}
			return user;
		}
		return null;
	}

	public User loginByLoginName(String loginName) {
		User user = new User();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		Tuser t = userDao.get("from Tuser t where t.loginName = :loginName", params);
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			if (t.getTroles() != null && !t.getTroles().isEmpty()) {
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				for (Trole role : t.getTroles()) {
					if (b) {
						roleIds += ",";
						roleNames += ",";
					} else {
						b = true;
					}
					roleIds += role.getId();
					roleNames += role.getRoleName();
				}
				user.setRoleIds(roleIds);
				user.setRoleNames(roleNames);
			}
			return user;
		}
		return null;
	}

	synchronized public void reg(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", user.getLoginName());
		if (userDao.count("select count(*) from Tuser t where t.loginName = :loginName", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setId(StringUtil.generateUUID());
			u.setLoginName(user.getLoginName());
			u.setPassword(MD5Util.md5(user.getPassword()));
			u.setUserState(CodeConstant.USER_STATE_NORMAL);
			u.setNameLetter(ChineseCharToEn.getFristLetter(u.getUserName()));
			u.setCreateDate(new Date());

			userDao.save(u);
		}
	}

	public EasyUIDataGrid dataGrid(User user, PageHelper ph) {
		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<User> ul = new ArrayList<User>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from Tuser t ";
		List<Tuser> l = userDao.find(hql + whereHql(user, params) + orderHql(ph), params, ph.getPage(), ph.getRows());
		if (l != null && l.size() > 0) {
			for (Tuser t : l) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					String roleIds = "";
					String roleNames = "";
					boolean b = false;
					for (Trole tr : roles) {
						if (b) {
							roleIds += ",";
							roleNames += ",";
						} else {
							b = true;
						}
						roleIds += tr.getId();
						roleNames += tr.getRoleName();
					}
					u.setRoleIds(roleIds);
					u.setRoleNames(roleNames);
				}
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(userDao.count("select count(*) " + hql + whereHql(user, params), params));
		return dg;
	}

	private String whereHql(User user, Map<String, Object> params) {
		String hql = "";
		if (user != null) {
			hql += " where 1=1 ";
			if (user.getLoginName() != null) {
				hql += " and t.loginName like :loginName";
				params.put("loginName", "%%" + user.getLoginName() + "%%");
			}
			if (user.getUserName() != null) {
				hql += " and t.userName like :userName";
				params.put("userName", "%%" + user.getUserName() + "%%");
			}
			if (user.getCreateDateStart() != null) {
				hql += " and t.createDate >= :createDateStart";
				params.put("createDateStart", user.getCreateDateStart());
			}
			if (user.getCreateDateEnd() != null) {
				hql += " and t.createDate <= :createDateEnd";
				params.put("createDateEnd", user.getCreateDateEnd());
			}
			if (user.getUpdateDateStart() != null) {
				hql += " and t.updateDate >= :updateDateStart";
				params.put("updateDateStart", user.getUpdateDateStart());
			}
			if (user.getUpdateDateEnd() != null) {
				hql += " and t.updateDate <= :updateDateEnd";
				params.put("updateDateEnd", user.getUpdateDateEnd());
			}
		}
		return hql;
	}

	private String orderHql(PageHelper ph) {
		String orderString = "";
		if (ph.getSort() != null && ph.getOrder() != null) {
			orderString = " order by t." + ph.getSort() + " " + ph.getOrder();
		}
		return orderString;
	}

	synchronized public void add(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", user.getLoginName());
		if (userDao.count("select count(*) from Tuser t where t.loginName = :loginName", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = new Tuser();
			BeanUtils.copyProperties(user, u);
			u.setId(StringUtil.generateUUID());
			u.setCreateDate(new Date());
			u.setPassword(MD5Util.md5(user.getPassword()));
			u.setUserState(CodeConstant.USER_STATE_NORMAL);
			u.setNameLetter(ChineseCharToEn.getFristLetter(u.getUserName()));
			userDao.save(u);
		}
	}

	public User get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tuser t = userDao.get("select distinct t from Tuser t left join fetch t.troles role where t.id = :id", params);
		User u = new User();
		BeanUtils.copyProperties(t, u);
		if (t.getTroles() != null && !t.getTroles().isEmpty()) {
			String roleIds = "";
			String roleNames = "";
			boolean b = false;
			for (Trole role : t.getTroles()) {
				if (b) {
					roleIds += ",";
					roleNames += ",";
				} else {
					b = true;
				}
				roleIds += role.getId();
				roleNames += role.getRoleName();
			}
			u.setRoleIds(roleIds);
			u.setRoleNames(roleNames);
		}
		return u;
	}

	synchronized public void edit(User user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", user.getId());
		params.put("loginName", user.getLoginName());
		if (userDao.count("select count(*) from Tuser t where t.loginName = :loginName and t.id != :id", params) > 0) {
			throw new Exception("登录名已存在！");
		} else {
			Tuser u = userDao.get(Tuser.class, user.getId());
			BeanUtils.copyProperties(user, u, new String[] { "password", "createDate" });
			u.setNameLetter(ChineseCharToEn.getFristLetter(u.getUserName()));
			u.setUpdateDate(new Date());
			userDao.save(u);
		}
	}

	public User delete(String id) {
		Tuser Tuser = userDao.get(Tuser.class, id);
		userDao.delete(Tuser);
		User user = new User();
		BeanUtils.copyProperties(Tuser, user);
		return user;
	}

	public String grant(String ids, User user) {
		String userNames = null;
		if (ids != null && ids.length() > 0) {
			List<Trole> roles = new ArrayList<Trole>();
			if (user.getRoleIds() != null) {
				for (String roleId : user.getRoleIds().split(",")) {
					roles.add(roleDao.get(Trole.class, roleId));
				}
			}
			for (String id : ids.split(",")) {
				if (id != null && !id.equalsIgnoreCase("")) {
					Tuser t = userDao.get(Tuser.class, id);
					t.setTroles(new HashSet<Trole>(roles));
					userNames += t.getUserName() + ",";
				}
			}
			return userNames.substring(0, userNames.length() - 1);
		}
		return null;
	}

	public List<String> moduleList(User user) {
		List<String> moduleList = new ArrayList<String>();
		if (user.getLoginName().equals(CodeConstant.SUPER_USER_LOGIN_NAME)) {
			List<Tmodule> list = moduleDao.find("from Tmodule");
			if (list != null && list.size() > 0) {
				for (Tmodule Tmodule : list) {
					moduleList.add(Tmodule.getModuleUrl());
				}
			}
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", user.getId());
			Tuser t = userDao.get(
					"from Tuser t join fetch t.troles role join fetch role.tmodules module where t.id = :id", params);
			if (t != null) {
				Set<Trole> roles = t.getTroles();
				if (roles != null && !roles.isEmpty()) {
					for (Trole role : roles) {
						Set<Tmodule> resources = role.getTmodules();
						if (resources != null && !resources.isEmpty()) {
							for (Tmodule resource : resources) {
								if (resource != null && resource.getModuleUrl() != null) {
									moduleList.add(resource.getModuleUrl());
								}
							}
						}
					}
				}
			}
		}
		return moduleList;
	}

	public void editPwd(User user) {
		if (user != null && user.getPassword() != null && !user.getPassword().trim().equalsIgnoreCase("")) {
			Tuser u = userDao.get(Tuser.class, user.getId());
			u.setPassword(MD5Util.md5(user.getPassword()));
		}
	}

	public boolean editCurrentUserPwd(String userId, String oldPwd, String pwd) {
		Tuser u = userDao.get(Tuser.class, userId);
		if (u.getPassword().equalsIgnoreCase(MD5Util.md5(oldPwd))) {// 说明原密码输入正确
			u.setPassword(MD5Util.md5(pwd));
			return true;
		}
		return false;
	}

	public List<Long> userCreateDatetimeChart() {
		List<Long> l = new ArrayList<Long>();
		int k = 0;
		for (int i = 0; i < 12; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("s", k);
			params.put("e", k + 2);
			k = k + 2;
			l.add(userDao.count("select count(*) from Tuser t where HOUR(t.createDate)>=:s and HOUR(t.createDate)<:e",
					params));
		}
		return l;
	}

	/**
	 * 根据用户登录名获取用户信息
	 * 
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		Tuser t = userDao.get("from Tuser t where t.loginName = :loginName ", params);
		User user = new User();
		if (t != null) {
			BeanUtils.copyProperties(t, user);
			if (t.getTroles() != null && !t.getTroles().isEmpty()) {
				String roleIds = "";
				String roleNames = "";
				boolean b = false;
				for (Trole role : t.getTroles()) {
					if (b) {
						roleIds += ",";
						roleNames += ",";
					} else {
						b = true;
					}
					roleIds += role.getId();
					roleNames += role.getRoleName();
				}
				user.setRoleIds(roleIds);
				user.setRoleNames(roleNames);
			}
			return user;
		}
		return null;
	}
}
