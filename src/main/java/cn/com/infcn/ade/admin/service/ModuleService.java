package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.model.Tmodule;
import cn.com.infcn.core.model.TmoduleType;
import cn.com.infcn.core.model.Trole;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.pageModel.Module;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.util.MenuUtil;

@Service
public class ModuleService {

	@Autowired
	private BaseDaoI<Tmodule> moduleDao;
	@Autowired
	private BaseDaoI<TmoduleType> moduleTypeDao;
	@Autowired
	private BaseDaoI<Tuser> userDao;

	/**
	 * 获取指定节点下的树形列表
	 * 
	 * @param user
	 *            能够访问的用户
	 * @param pid
	 *            父节点ID
	 * @return 树形菜单，null的时候为找不到对象
	 */
	public List<Tree> treeMenu(User user, String pid) {

		List<Tree> topMenus = MenuUtil.list2Tree(tree(user));

		for (Tree topMenu : topMenus) {

			if (topMenu.getId().equals(pid)) {
				return topMenu.getChildren();
			}
		}

		return null;
	}

	/**
	 * 获取当前用户能够访问的一级菜单
	 * 
	 * @param user
	 *            用户
	 * @return 一级菜单
	 */
	public List<Tree> topMenu(User user) {

		List<Tree> topMenus = MenuUtil.list2Tree(tree(user));

		for (Tree topMenu : topMenus) {

			boolean hasGrandson = false;

			List<Tree> subMenus = topMenu.getChildren();
			if (subMenus != null) {
				for (Tree subMenu : subMenus) {
					// 判断孙子节点

					List<Tree> grandsonMenus = subMenu.getChildren();
					if (grandsonMenus != null && grandsonMenus.size() > 0) {
						hasGrandson = true;
						break;
					}
				}

				topMenu.setHasGrandson(hasGrandson);
				if (hasGrandson) {
					topMenu.setChildren(null);
				}
			} else {
				topMenu.setChildren(new ArrayList<Tree>());
			}

		}

		return topMenus;

	}

	public List<Tree> tree(User user) {
		List<Tmodule> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("resourceTypeId", "0");// 菜单类型的模块

		if (user != null) {
			params.put("userId", user.getId());// 自查自己有权限的模块
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type join fetch t.troles role join role.tusers user where type.id = :resourceTypeId and user.id = :userId order by t.moduleSort",
					params);
		} else {
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type where type.id = :resourceTypeId order by t.moduleSort",
					params);
		}

		if (l != null && l.size() > 0) {
			for (Tmodule r : l) {
				Tree EasyUITree = new Tree();
				BeanUtils.copyProperties(r, EasyUITree);
				if (r.getTmodule() != null) {
					EasyUITree.setPid(r.getTmodule().getId());
				}
				EasyUITree.setText(r.getModuleName());
				EasyUITree.setIconCls(r.getModuleIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getModuleUrl());
				EasyUITree.setAttributes(attr);
				lt.add(EasyUITree);
			}
		}

		return lt;
	}

	public List<Tree> allTree(User user) {
		List<Tmodule> l = null;
		List<Tree> lt = new ArrayList<Tree>();

		Map<String, Object> params = new HashMap<String, Object>();
		if (user != null && !user.getLoginName().equals(CodeConstant.SUPER_USER_LOGIN_NAME)) {
			params.put("userId", user.getId());// 查自己有权限的模块
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type join fetch t.troles role join role.tusers user where user.id = :userId order by t.moduleSort",
					params);
		} else {
			l = moduleDao.find("select distinct t from Tmodule t join fetch t.tmoduleType type order by t.moduleSort",
					params);
		}

		if (l != null && l.size() > 0) {
			for (Tmodule r : l) {
				Tree EasyUITree = new Tree();
				BeanUtils.copyProperties(r, EasyUITree);
				if (r.getTmodule() != null) {
					EasyUITree.setPid(r.getTmodule().getId());
				}
				EasyUITree.setText(r.getModuleName());
				EasyUITree.setIconCls(r.getModuleIcon());
				Map<String, Object> attr = new HashMap<String, Object>();
				attr.put("url", r.getModuleUrl());
				EasyUITree.setAttributes(attr);
				lt.add(EasyUITree);
			}
		}
		return lt;
	}

	public List<Module> treeGrid(User user) {
		List<Tmodule> l = null;
		List<Module> lr = new ArrayList<Module>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmoduleType", "0");// 0：系统菜单
		if (user != null) {
			params.put("userId", user.getId());// 自查自己有权限的模块
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type join fetch t.troles role join role.tusers user where user.id = :userId and t.tmoduleType.id=:tmoduleType order by t.moduleSort",
					params);
		} else {
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type where  and t.tmoduleType.id=:tmoduleType  order by t.moduleSort",
					params);
		}

		if (l != null && l.size() > 0) {
			for (Tmodule t : l) {
				Module r = new Module();
				BeanUtils.copyProperties(t, r);
				if (t.getTmodule() != null) {
					r.setParentModuleId(t.getTmodule().getId());
					r.setParentModuleName(t.getTmodule().getModuleName());
					// 给Module传递图标
					r.setModuleIcon(t.getModuleIcon());
				}
				r.setModuleTypeId(t.getTmoduleType().getId());
				r.setModuleTypeName(t.getTmoduleType().getName());
				if (t.getModuleIcon() != null && !t.getModuleIcon().equalsIgnoreCase("")) {
					r.setModuleIcon(t.getModuleIcon());
					r.setIconCls(t.getModuleIcon());
				}
				lr.add(r);
			}
		}

		return lr;
	}

	/**
	 * 
	 * 描述: 根据用户和菜单ID获取对应的功能
	 *
	 * @param user
	 * @param pid
	 *            菜单ID
	 * @return List<Module>
	 */
	public List<Module> dataGridForMenu(User user, String pid) {
		List<Tmodule> l = null;
		List<Module> lr = new ArrayList<Module>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tmoduleType", "1");// 1：功能菜单
		params.put("pid", pid);//
		if (user != null) {
			params.put("userId", user.getId());// 自查自己有权限的模块
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type join fetch t.troles role join role.tusers user where user.id = :userId and t.tmoduleType.id=:tmoduleType and t.tmodule.id=:pid order by t.moduleSort",
					params);
		} else {
			l = moduleDao.find(
					"select distinct t from Tmodule t join fetch t.tmoduleType type where  and t.tmoduleType.id=:tmoduleType and t.tmodule.id=:pid  order by t.moduleSort",
					params);
		}

		if (l != null && l.size() > 0) {
			for (Tmodule t : l) {
				Module r = new Module();
				BeanUtils.copyProperties(t, r);
				if (t.getTmodule() != null) {
					r.setParentModuleId(t.getTmodule().getId());
					r.setParentModuleName(t.getTmodule().getModuleName());
				}
				r.setModuleTypeId(t.getTmoduleType().getId());
				r.setModuleTypeName(t.getTmoduleType().getName());
				if (t.getModuleIcon() != null && !t.getModuleIcon().equalsIgnoreCase("")) {
					r.setModuleIcon(t.getModuleIcon());
				}
				lr.add(r);
			}
		}

		return lr;
	}

	public void add(Module module, User user) {
		Tmodule t = new Tmodule();
		BeanUtils.copyProperties(module, t);
		if (module.getParentModuleId() != null && !module.getParentModuleId().equalsIgnoreCase("")) {
			t.setTmodule(moduleDao.get(Tmodule.class, module.getParentModuleId()));
		}
		if (module.getModuleTypeId() != null && !module.getModuleTypeId().equalsIgnoreCase("")) {
			t.setTmoduleType(moduleTypeDao.get(TmoduleType.class, module.getModuleTypeId()));
		}
		if (module.getModuleIcon() != null && !module.getModuleIcon().equalsIgnoreCase("")) {
			t.setModuleIcon(module.getModuleIcon());
		}
		moduleDao.save(t);

		// 由于当前用户所属的角色，没有访问新添加的模块权限，所以在新添加模块的时候，将当前模块授权给当前用户的所有角色，以便添加模块后在模块列表中能够找到
		Tuser tuser = userDao.get(Tuser.class, user.getId());
		Set<Trole> roles = tuser.getTroles();
		for (Trole r : roles) {
			r.getTmodules().add(t);
		}
	}

	public void delete(String id) {
		Tmodule t = moduleDao.get(Tmodule.class, id);
		if (t != null) {
			del(t);
		}
	}

	private void del(Tmodule t) {
		if (t.getTmodules() != null && t.getTmodules().size() > 0) {
			for (Tmodule r : t.getTmodules()) {
				del(r);
			}
		}
		moduleDao.delete(t);
	}

	public void edit(Module module) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", module.getId());
		Tmodule t = moduleDao.get("select distinct t from Tmodule t where t.id = :id", params);
		if (t != null) {
			BeanUtils.copyProperties(module, t);
			if (module.getModuleTypeId() != null && !module.getModuleTypeId().equalsIgnoreCase("")) {
				t.setTmoduleType(moduleTypeDao.get(TmoduleType.class, module.getModuleTypeId()));// 赋值模块类型
			}
			if (module.getModuleIcon() != null && !module.getModuleIcon().equalsIgnoreCase("")) {
				t.setModuleIcon(module.getModuleIcon());
			}
			if (module.getParentModuleId() != null && !module.getParentModuleId().equalsIgnoreCase("")) {// 说明前台选中了上级模块
				Tmodule pt = moduleDao.get(Tmodule.class, module.getParentModuleId());
				isChildren(t, pt);// 说明要将当前模块修改到当前模块的子/孙子模块下
				t.setTmodule(pt);
			} else {
				t.setTmodule(null);// 前台没有选中上级模块，所以就置空
			}
		}
	}

	/**
	 * 判断是否是将当前节点修改到当前节点的子节点
	 * 
	 * @param t
	 *            当前节点
	 * @param pt
	 *            要修改到的节点
	 * @return
	 */
	private boolean isChildren(Tmodule t, Tmodule pt) {
		if (pt != null && pt.getTmodule() != null) {
			if (pt.getTmodule().getId().equalsIgnoreCase(t.getId())) {
				pt.setTmodule(null);
				return true;
			} else {
				return isChildren(t, pt.getTmodule());
			}
		}
		return false;
	}

	public Module get(String id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		Tmodule t = moduleDao.get(
				"from Tmodule t left join fetch t.tmodule resource left join fetch t.tmoduleType type where t.id = :id",
				params);
		Module r = new Module();
		BeanUtils.copyProperties(t, r);
		if (t.getTmodule() != null) {
			r.setParentModuleId(t.getTmodule().getId());
			r.setParentModuleName(t.getTmodule().getModuleName());
		}
		r.setModuleTypeId(t.getTmoduleType().getId());
		r.setModuleTypeName(t.getTmoduleType().getName());
		if (t.getModuleIcon() != null && !t.getModuleIcon().equalsIgnoreCase("")) {
			r.setModuleIcon(t.getModuleIcon());
		}
		return r;
	}

	/**
	 * 描述: 获取同步树
	 *
	 * @param id
	 * @return List<Tree>
	 */
	public List<Tree> getTree(String id) {
		String hql = "from Tmodule t where t.tmodule.id=:id";

		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(id)) {
			params.put("id", id);
		} else {
			hql = "from Tmodule t where t.tmodule.id is Null";
		}
		;
		List<Tmodule> findList = moduleDao.find(hql, params);

		List<Tree> listTree = new ArrayList<Tree>();

		for (Tmodule r : findList) {
			Tree EasyUITree = new Tree();
			BeanUtils.copyProperties(r, EasyUITree);
			EasyUITree.setId(r.getId());
			EasyUITree.setText(r.getModuleName());
			EasyUITree.setIconCls(r.getModuleIcon());
			if (r.getTmodules().size() > 0) {
				EasyUITree.setState("closed");
			}
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("url", r.getModuleUrl());
			EasyUITree.setAttributes(attr);
			listTree.add(EasyUITree);
		}

		return listTree;
	}

	/**
	 * 
	 * 描述: 获取异步树
	 *
	 * @param id
	 * @return List<Tree>
	 */
	public List<Tree> getSyncTree(String id) {
		String hql = "from Tmodule t where t.tmodule.id=:id";

		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(id)) {
			params.put("id", id);
		} else {
			hql = "from Tmodule t where t.tmodule.id is Null";
		}
		List<Tmodule> findList = moduleDao.find(hql, params);

		List<Tree> listTree = new ArrayList<Tree>();

		for (Tmodule r : findList) {
			Tree EasyUITree = new Tree();
			BeanUtils.copyProperties(r, EasyUITree);
			EasyUITree.setId(r.getId());
			EasyUITree.setText(r.getModuleName());
			EasyUITree.setIconCls(r.getModuleIcon());
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("url", r.getModuleUrl());
			EasyUITree.setAttributes(attr);
			EasyUITree.setResourcesCur(3);// ？？？？？？？？？？？
			addTree(EasyUITree,
					moduleDao.find("from Tmodule t where t.tmodule.id='" + r.getId() + "' order by t.moduleSort"));
			listTree.add(EasyUITree);
		}

		for (Tree tree : listTree) {
			resourceCount(tree);
		}

		return listTree;
	}

	/**
	 * 描述: 计算树节点下的资源数
	 *
	 * @param tree
	 *            void
	 */
	private void resourceCount(Tree tree) {

		Map<String, Integer> tempMaps = new HashMap<>();
		if (tree.getChildren().size() > 0) {
			for (Tree tempTree : tree.getChildren()) {
				resourceCount(tempTree);
				tree.setResourcesChildren(tree.getResourcesChildren() + tempTree.getResourcesTotal());
			}
			tree.setResourcesTotal(tree.getResourcesChildren() + tree.getResourcesCur());

		} else {
			tree.setResourcesTotal(tree.getResourcesCur() + tree.getResourcesChildren());
		}
		tempMaps.put("ResourcesCur", tree.getResourcesCur());
		tempMaps.put("ResourcesChildren", tree.getResourcesChildren());
		tempMaps.put("ResourcesTotal", tree.getResourcesTotal());
		tree.setAttributes(tempMaps);

	}

	/**
	 * 描述: 递归拼树节点
	 *
	 * @param easyUITree
	 * @param tmodules
	 *            void
	 */
	private void addTree(Tree easyUITree, List<Tmodule> tmodules) {
		for (Tmodule tModule : tmodules) {
			Tree tree = new Tree();
			BeanUtils.copyProperties(tModule, tree);
			tree.setId(tModule.getId());
			tree.setText(tModule.getModuleName());
			tree.setIconCls(tModule.getModuleIcon());
			Map<String, Object> attr = new HashMap<String, Object>();
			attr.put("url", tModule.getModuleUrl());
			tree.setAttributes(attr);
			tree.setResourcesCur(4);// ？？？？？？？？？？？
			if (tModule.getTmodules().size() > 0) {
				addTree(tree, moduleDao
						.find("from Tmodule t where t.tmodule.id='" + tModule.getId() + "' order by t.moduleSort"));
			}
			easyUITree.getChildren().add(tree);
		}
	}

}
