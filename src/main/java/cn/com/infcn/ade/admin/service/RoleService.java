package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.model.Tmodule;
import cn.com.infcn.core.model.Trole;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.pageModel.Role;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;

@Service
public class RoleService {

    @Autowired
    private BaseDaoI<Trole> roleDao;
    @Autowired
    private BaseDaoI<Tuser> userDao;
    @Autowired
    private BaseDaoI<Tmodule> resourceDao;

    public void add(Role role, User user) {
        Trole t = new Trole();
        BeanUtils.copyProperties(role, t);
        if (role.getParentRoleId() != null && !role.getParentRoleId().equalsIgnoreCase("")) {
            t.setTrole(roleDao.get(Trole.class, role.getParentRoleId()));
            t.setRoleIsBuildin(CodeConstant.ROLE_ISNOT_BUILDIN);// 内置角色标识
        }
        roleDao.save(t);
        // 刚刚添加的角色，赋予给当前的用户
        Tuser tuser = userDao.get(Tuser.class, user.getId());
        tuser.getTroles().add(t);
    }

    public Role get(String id) {
        Role r = new Role();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        Trole t = roleDao.get("select distinct t from Trole t left join fetch t.tmodules resource where t.id = :id",
                params);
        if (t != null) {
            BeanUtils.copyProperties(t, r);
            if (t.getTrole() != null) {
                r.setParentRoleId(t.getTrole().getId());
                r.setParentRoleName(t.getTrole().getRoleName());
            }
            Set<Tmodule> s = t.getTmodules();
            if (s != null && !s.isEmpty()) {
                boolean b = false;
                String ids = "";
                String names = "";
                for (Tmodule tr : s) {
                    if (b) {
                        ids += ",";
                        names += ",";
                    } else {
                        b = true;
                    }
                    ids += tr.getId();
                    names += tr.getModuleName();
                }
                r.setModuleIds(ids);
                r.setModuleNames(names);
            }
        }
        return r;
    }

    public void edit(Role role) {
        Trole t = roleDao.get(Trole.class, role.getId());
        if (t != null) {
            BeanUtils.copyProperties(role, t);
            if (role.getParentRoleId() != null && !role.getParentRoleId().equalsIgnoreCase("")) {
                t.setTrole(roleDao.get(Trole.class, role.getParentRoleId()));
            }
            if (role.getParentRoleId() != null && !role.getParentRoleId().equalsIgnoreCase("")) {// 说明前台选中了上级模块
                Trole pt = roleDao.get(Trole.class, role.getParentRoleId());
                isChildren(t, pt);// 说明要将当前模块修改到当前模块的子/孙子模块下
                t.setTrole(pt);
            } else {
                t.setTrole(null);// 前台没有选中上级模块，所以就置空
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
    private boolean isChildren(Trole t, Trole pt) {
        if (pt != null && pt.getTrole() != null) {
            if (pt.getTrole().getId().equalsIgnoreCase(t.getId())) {
                pt.setTrole(null);
                return true;
            } else {
                return isChildren(t, pt.getTrole());
            }
        }
        return false;
    }

    /**
     * 少量修改，加了个排序，内置字段置顶，费内置字段排序
     * 
     * @param user
     * @return List<Role>
     */
    public List<Role> treeGrid(User user) {
        List<Role> rl = new ArrayList<Role>();
        List<Trole> tl = null;
        Map<String, Object> params = new HashMap<String, Object>();
        if (user != null) {
            params.put("userId", user.getId());// 查自己有权限的角色
            tl = roleDao.find(
                    "select distinct t from Trole t left join fetch t.tmodules resource join fetch t.tusers user where user.id = :userId order by t.roleIsBuildin desc,t.roleSort asc",
                    params);
        } else {
            tl = roleDao.find("select distinct t from Trole t left join fetch t.tmodules resource order by t.roleSort");
        }
        if (tl != null && tl.size() > 0) {
            for (Trole t : tl) {
                Role r = new Role();
                BeanUtils.copyProperties(t, r);
                r.setIconCls("status_online");
                if (t.getTrole() != null) {
                    r.setParentRoleId(t.getTrole().getId());
                    r.setParentRoleName(t.getTrole().getRoleName());
                }
                Set<Tmodule> s = t.getTmodules();
                if (s != null && !s.isEmpty()) {
                    boolean b = false;
                    String ids = "";
                    String names = "";
                    for (Tmodule tr : s) {
                        if (b) {
                            ids += ",";
                            names += ",";
                        } else {
                            b = true;
                        }
                        ids += tr.getId();
                        names += tr.getModuleName();
                    }
                    r.setModuleIds(ids);
                    r.setModuleNames(names);
                }
                rl.add(r);
            }
        }
        return rl;
    }

    public void delete(String id) {
        Trole t = roleDao.get(Trole.class, id);
        del(t);
    }

    private void del(Trole t) {
        if (t.getTroles() != null && t.getTroles().size() > 0) {
            for (Trole r : t.getTroles()) {
                del(r);
            }
        }
        roleDao.delete(t);
    }

    public List<Tree> tree(User user) {
        List<Trole> l = null;
        List<Tree> lt = new ArrayList<Tree>();

        Map<String, Object> params = new HashMap<String, Object>();
        if (user != null) {
            params.put("userId", user.getId());// 查自己有权限的角色
            l = roleDao.find(
                    "select distinct t from Trole t join fetch t.tusers user where user.id = :userId order by t.roleSort",
                    params);
        } else {
            l = roleDao.find("from Trole t order by t.roleSort");
        }

        if (l != null && l.size() > 0) {
            for (Trole t : l) {
                Tree tree = new Tree();
                BeanUtils.copyProperties(t, tree);
                tree.setText(t.getRoleName());
                tree.setIconCls("status_online");
                if (t.getTrole() != null) {
                    tree.setPid(t.getTrole().getId());
                }
                lt.add(tree);
            }
        }
        return lt;
    }

    public List<Tree> allTree() {
        return this.tree(null);
    }

    public void grant(Role role) {
        Trole t = roleDao.get(Trole.class, role.getId());
        if (role.getModuleIds() != null && !role.getModuleIds().equalsIgnoreCase("")) {
            String ids = "";
            boolean b = false;
            for (String id : role.getModuleIds().split(",")) {
                if (b) {
                    ids += ",";
                } else {
                    b = true;
                }
                ids += "'" + id + "'";
            }
            t.setTmodules(new HashSet<Tmodule>(
                    resourceDao.find("select distinct t from Tmodule t where t.id in (" + ids + ")")));
        } else {
            t.setTmodules(null);
        }
    }
}
