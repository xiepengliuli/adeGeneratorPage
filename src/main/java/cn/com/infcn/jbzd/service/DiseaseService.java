package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.CodeConstant;
import cn.com.infcn.core.model.Tuser;
import cn.com.infcn.core.modelAdd.Tdisease;
import cn.com.infcn.core.modelAdd.TdiseasePrivilege;
import cn.com.infcn.core.modelAdd.TdiseasePrivilegeId;
import cn.com.infcn.core.modelAdd.Tstandard;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.Tree;
import cn.com.infcn.core.pageModel.User;
import cn.com.infcn.core.pageModelAdd.Disease;
import cn.com.infcn.core.pageModelAdd.DiseasePrivilege;
import cn.com.infcn.core.pageModelAdd.Standard;
import cn.com.infcn.core.util.Tools;

/**
 * 描述: TODO
 * 
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class DiseaseService {
  @Autowired
  private BaseDaoI<Tdisease> diseaseDao;
  @Autowired
  private BaseDaoI<Tuser> userDao;
  @Autowired
  private BaseDaoI<TdiseasePrivilege> diseasePrivilegeDao;
  @Autowired
  private BaseDaoI<Tstandard> standardDao;

  /**
   * 描述: EasyUI-DataGrid访问的方法
   * 
   * @param disease
   * @param ph
   * @return EasyUIDataGrid
   */
  public EasyUIDataGrid dataGrid(Disease disease, PageHelper ph) {
    EasyUIDataGrid dg = new EasyUIDataGrid();
    List<Disease> ul = new ArrayList<Disease>();
    Map<String, Object> params = new HashMap<String, Object>();
    String hql = " from Tdisease t ";
    List<Tdisease> l = diseaseDao.find(hql + whereHql(disease, params) + orderHql(ph), params, ph.getPage(),
        ph.getRows());

    if (l != null && l.size() > 0) {
      for (Tdisease t : l) {
        Disease u = new Disease();
        BeanUtils.copyProperties(t, u);
        ul.add(u);
      }
    }
    dg.setRows(ul);
    dg.setTotal(diseaseDao.count("select count(*) " + hql + whereHql(disease, params), params));
    return dg;
  }

  /**
   * 描述: 拼接whereHql语句
   * 
   * @param disease
   * @param params
   * @return String
   */
  private String whereHql(Disease disease, Map<String, Object> params) {
    String hql = "";
    if (disease == null) {
      return hql;
    }
    hql += " where 1=1 ";

    if (StringUtils.isNotBlank(disease.getName())) {
      hql += " and t.name like :name";
      params.put("name", "%" + disease.getName() + "%");
    }
    if (StringUtils.isNotBlank(disease.getTcmDiseaseName())) {
      hql += " and t.tcmDiseaseName like :tcmDiseaseName";
      params.put("tcmDiseaseName", "%" + disease.getTcmDiseaseName() + "%");
    }
    if (StringUtils.isNotBlank(disease.getMmDiseaseName())) {
      hql += " and t.mmDiseaseName like :mmDiseaseName";
      params.put("mmDiseaseName", "%" + disease.getMmDiseaseName() + "%");
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
      orderString += " ,t.updateTime desc";
    }
    return orderString;
  }

  /**
   * 描述: 添加
   * 
   * @param disease
   * @throws Exception
   *           void
   */
  synchronized public void add(Disease disease) throws Exception {
    Tdisease u = new Tdisease();
    BeanUtils.copyProperties(disease, u);
    Date newDate = new Date();
    u.setCreateTime(newDate);
    u.setUpdateTime(newDate);
    u.setId(Tools.getUUID());

    diseaseDao.save(u);

    BeanUtils.copyProperties(u, disease);
  }

  /**
   * 描述: 根据id获取
   * 
   * @param id
   * @return Disease
   */
  public Disease get(String id) {

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("id", id);

    List<Tdisease> ts = diseaseDao.find("select distinct t from Tdisease t  where t.id = :id ", params);

    Disease u = new Disease();
    if (ts != null && ts.size() > 0) {
      Tdisease t = ts.get(0);
      BeanUtils.copyProperties(t, u);
    }

    return u;
  }

  /**
   * 描述: //编辑
   * 
   * @param disease
   * @throws Exception
   *           void
   */
  public void edit(Disease disease) throws Exception {
    Tdisease u = diseaseDao.get(Tdisease.class, disease.getId());
    disease.setFirstLetter(disease.getFirstLetter().toUpperCase());
    String[] ignoreProperties = new String[] { "createTime", "createUser" };
    BeanUtils.copyProperties(disease, u, ignoreProperties);

    u.setUpdateTime(new Date());

    diseaseDao.update(u);
  }

  /**
   * 描述: 根据id删除
   * 
   * @param id
   *          void
   * @throws Exception
   */
  public String deleteByID(String id) {
    if (StringUtils.isNotBlank(id)) {
      Tdisease tdisease = diseaseDao.get(Tdisease.class, id);
      if (tdisease.getTstandards().size() > 0) {
        return "请先删除该疾病对应的标准,再删除疾病！";
      } else {
        if (tdisease != null) {
          Map<String, Object> params = new HashMap<>();
          params.put("id", id);

          // 清楚疾病权限表对应的数据
          diseasePrivilegeDao.executeHql("delete from TdiseasePrivilege t where t.tdisease.id=:id", params);

          diseaseDao.delete(tdisease);
        }
      }
    }
    return "";
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

    List<Disease> diseaseList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
    pageHelper.setData(diseaseList);
    // 获取总记录数
    hql = "select count(*) " + hql;
    int rowCount = diseaseDao.count(hql, params).intValue();
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
   * @return List<Disease>
   */
  public List<Disease> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

    List<Disease> diseaseList = new ArrayList<Disease>();

    List<Tdisease> tempList = diseaseDao.find(hql, params, pageNum, Pagecount);

    for (Tdisease tdisease : tempList) {
      Disease disease = new Disease();
      BeanUtils.copyProperties(tdisease, disease);

      diseaseList.add(disease);
    }

    return diseaseList;

  }

  public Tree getMenuTree(String userId) {

    Tree tree = new Tree();
    tree.setId("111111");
    tree.setChecked(false);
    tree.setHasGrandson(false);
    tree.setIconCls("building_add");
    tree.setState("open");
    tree.setText("标准管理");
    tree.setChildren(getDiseaseByUserId(userId, tree.getId()));
    return tree;
  }

  /**
   * 描述: 根据用户获取对应的疾病,并转成Tree
   * 
   * @param userId
   * @return List<Tree>
   */
  private List<Tree> getDiseaseByUserId(String userId, String pid) {

    List<Tree> tempList = new ArrayList<>();
    List<Tdisease> diseaseList = null;
    // 超级管理员
    if ("0".equals(userId)) {
      String hql = "select distinct t from Tdisease t order by t.orderNum, t.createTime";
      diseaseList = diseaseDao.find(hql);

    } else {

      String hql = "select distinct t from Tdisease as t inner join fetch t.tdiseasePrivileges as s where s.tuser.id=:userId order by t.orderNum";// 第二版要替换的hql
      Map<String, Object> params = new HashMap<>();
      params.put("userId", userId);
      diseaseList = diseaseDao.find(hql, params);
    }

    if (diseaseList != null && diseaseList.size() > 0) {
      for (Tdisease tdisease : diseaseList) {
        Tree tree = new Tree();
        tree.setId(tdisease.getId());
        tree.setChecked(false);
        tree.setHasGrandson(false);
        tree.setIconCls("building_add");
        tree.setState("open");
        tree.setText(tdisease.getName());
        tree.setChildren(null);
        tree.setPid(pid);
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("url", "/admin/standard/manager?tdiseaseId=" + tdisease.getId());
        tree.setAttributes(hashMap);
        tempList.add(tree);
      }
    }
    return tempList;
  }

  /**
   * 描述: 根据简单检索获取分组数据
   * 
   * @param isLike
   * @param keyWord
   * @return List<Disease>
   */
  public List<Disease> getDiseaseDataBySimpleQ(String isLike, String keyWord) {

    List<Disease> diseaseList = new ArrayList<>();

    String hql = "";
    Map<String, Object> params = new HashMap<>();
    List<Tdisease> tdiseaseList = new ArrayList<>();
    if (isLike.equals(CodeConstant.QUERY_TYPE_LIKE)) {
      hql = "from Tdisease t where t.tcmDiseaseName like :keyWord or t.mmDiseaseName like:keyWord or t.enName like :keyWord or t.name like :keyWord ";
      params.put("keyWord", "%" + keyWord + "%");
      tdiseaseList = diseaseDao.find(hql, params);
    } else {
      hql = "from Tdisease t where t.tcmDiseaseName =:keyWord or t.mmDiseaseName =:keyWord or t.enName =:keyWord or t.name=:keyWord ";
      params.put("keyWord", keyWord);
      tdiseaseList = diseaseDao.find(hql, params);
    }
    if (tdiseaseList != null) {
      for (Tdisease tdisease : tdiseaseList) {
        Disease disease = new Disease();
        BeanUtils.copyProperties(tdisease, disease);
        diseaseList.add(disease);
      }
    }
    return diseaseList;
  }

  /**
   * 构建map集合，key为firstChar,value为病种List集合
   */
  public Map<String, List<Disease>> getDiseaseData(String isLike, String keyWord) {

    TreeMap<String, List<Disease>> maps = new TreeMap<String, List<Disease>>();

    List<Disease> diseaseList = getDiseaseDataBySimpleQ(isLike, keyWord);

    if (diseaseList != null) {
      for (Disease disease : diseaseList) {
        String upperChar = disease.getFirstLetter().toUpperCase();
        // 不包括firstChar,则新建一个LIST
        if (!maps.containsKey(upperChar)) {
          List<Disease> tempDiseases = new ArrayList<>();
          tempDiseases.add(disease);
          maps.put(upperChar, tempDiseases);
        } else {
          List<Disease> list = maps.get(upperChar);
          list.add(disease);
        }
      }
    }
    return maps;
  }

  /**
   * 根据ID获取病种及标准
   */
  public Map<String, List<Standard>> getStandardsByDiseaseId(String id) {

    Map<String, List<Standard>> tempList = new HashMap<>();

    String hql = "from Tstandard t where t.tdisease.id =:tdiseaseId order by t.publishDate desc";
    Map<String, Object> params = new HashMap<>();

    params.put("tdiseaseId", id);

    List<Tstandard> tstandards = standardDao.find(hql, params);

    List<Tstandard> tstandards1 = new ArrayList<>();

    // 过滤标准
    for (Tstandard tstandard : tstandards) {
      if (tstandard.getStatus().equalsIgnoreCase(CodeConstant.STANDARD_STUTUS_PUBLISH_PASSED)) {
        if (tstandard.getIsDel().equalsIgnoreCase(CodeConstant.NOTDELETED)) {
          tstandards1.add(tstandard);
        }
      }
    }
    tstandards = tstandards1;

    List<Standard> cnList = new ArrayList<>();
    List<Standard> enList = new ArrayList<>();

    List<Standard> cnLangueList = new ArrayList<>();
    List<Standard> enLangueList = new ArrayList<>();

    if (tstandards != null && tstandards.size() > 0) {
      for (Tstandard tstandard : tstandards) {
        if (CodeConstant.CNTYPE.equals(tstandard.getType())) {// 0中医标准类型
          Standard standard = new Standard();
          BeanUtils.copyProperties(tstandard, standard);
          cnList.add(standard);
        } else if (CodeConstant.ENTYPE.equals(tstandard.getType())) {// 1西医标准类型
          Standard standard = new Standard();
          BeanUtils.copyProperties(tstandard, standard);
          enList.add(standard);
        }

        if ("中文".equals(tstandard.getLanguage())) {// 0中医语言
          Standard standard = new Standard();
          BeanUtils.copyProperties(tstandard, standard);
          cnLangueList.add(standard);
        } else if ("英文".equals(tstandard.getLanguage())) {// 1西医语言
          Standard standard = new Standard();
          BeanUtils.copyProperties(tstandard, standard);
          enLangueList.add(standard);
        }

      }
    }

    tempList.put("cnList", cnList);
    tempList.put("enList", enList);

    tempList.put("cnLangueList", cnLangueList);
    tempList.put("enLangueList", enLangueList);

    return tempList;
  }

  /**
   * 根据ID获取病种及标准
   */
  public Disease getByDiseaseId(String id) {
    Tdisease tdisease = diseaseDao.get(Tdisease.class, id);
    Disease disease = new Disease();
    BeanUtils.copyProperties(tdisease, disease);
    return disease;

  }

  /**
   * 获取病种检索
   */
  public List<Disease> diseasesTypeSearch(String isLike, String keyWord) {

    List<Disease> diseaseList = getDiseaseDataBySimpleQ(isLike, keyWord);

    return diseaseList;
  }

  /**
   * 
   * 描述: 属性是否存在某个值
   * 
   * @param property
   *          属性的名称
   * @param value
   *          属性的值
   * @return Json
   */
  public boolean getIsExitPropertyValue(String property, String value, String id) {

    Map<String, Object> params = new HashMap<String, Object>();
    String hql = "";

    if (StringUtils.isNotBlank(id)) {
      hql = "select count(*) from Tdisease t where t." + property + " =:value and t.id !=:id";
      params.put("id", id);
    } else {
      hql = "select count(*) from Tdisease t where t." + property + " =:value";
    }

    params.put("value", value);

    Long count = diseaseDao.count(hql, params);
    if (count > 0) {
      return true;
    }
    return false;
  }

  /**
   * 
   * 描述: 获取可以授权的用户
   * 
   * @param tdiseaseId
   * @param name
   * @param ph
   * @return EasyUIDataGrid
   */
  public EasyUIDataGrid authorizationDatagrid(String tdiseaseId, String name, PageHelper ph) {

    EasyUIDataGrid easyUIDataGrid = new EasyUIDataGrid();

    String from = "from Tuser t where t.loginName like :name or t.userName like :name";

    Map<String, Object> params = new HashMap<>();
    params.put("name", "%" + name + "%");

    List<Tuser> tuserLists = userDao.find(from, params);
    List<User> userLists = new ArrayList<>();

    Map<String, Object> paramsDiseaseId = new HashMap<>();
    paramsDiseaseId.put("tdiseaseId", tdiseaseId);

    List<TdiseasePrivilege> tdiseasePrivilegeLists = diseasePrivilegeDao.find(
        "from TdiseasePrivilege t where t.tdisease.id=:tdiseaseId", paramsDiseaseId);

    for (Tuser tuser : tuserLists) {
      User user = new User();
      BeanUtils.copyProperties(tuser, user);
      for (TdiseasePrivilege tdiseasePrivilege : tdiseasePrivilegeLists) {
        System.out.println(tdiseasePrivilege.getTprivilegeInfo().getCode());
        if (tdiseasePrivilege.getId().getPrivilegeCodeId().equals("1")
            && tdiseasePrivilege.getId().getUserId().equalsIgnoreCase(user.getId())) {
          user.setCanEdit("1");
        }
        if (tdiseasePrivilege.getId().getPrivilegeCodeId().equals("2")
            && tdiseasePrivilege.getId().getUserId().equalsIgnoreCase(user.getId())) {
          user.setCanAudit("1");
        }
        if (tdiseasePrivilege.getId().getPrivilegeCodeId().equals("3")
            && tdiseasePrivilege.getId().getUserId().equalsIgnoreCase(user.getId())) {
          user.setCanPublish("1");
        }

      }
      userLists.add(user);
    }

    easyUIDataGrid.setRows(userLists);

    easyUIDataGrid.setTotal(userDao.count("select count(*) " + from, params));

    return easyUIDataGrid;
  }

  /**
   * 
   * 描述: 保存授权
   * 
   * @param tdiseaseId
   * @param canEdit
   * @param canAudit
   * @param canPublish
   *          void
   */
  public void saveAuthorization(String tdiseaseId, String canEdit, String canAudit, String canPublish) {

    Map<String, Object> params = new HashMap<>();
    params.put("tdiseaseId", tdiseaseId);
    // 根据病的ID清空对应的权限
    diseasePrivilegeDao.executeHql("delete from TdiseasePrivilege t where t.tdisease.id=:tdiseaseId", params);

    if (StringUtils.isNotBlank(canEdit)) {
      TdiseasePrivilege tdiseasePrivilege = new TdiseasePrivilege();
      TdiseasePrivilegeId id = new TdiseasePrivilegeId();
      id.setDiseaseId(tdiseaseId);
      tdiseasePrivilege.setId(id);
      String[] tempString = canEdit.split(",");
      id.setUserId(tempString[0]);
      id.setPrivilegeCodeId(tempString[1]);
      diseasePrivilegeDao.saveOrUpdate(tdiseasePrivilege);
    }
    if (StringUtils.isNotBlank(canAudit)) {
      TdiseasePrivilege tdiseasePrivilege = new TdiseasePrivilege();
      TdiseasePrivilegeId id = new TdiseasePrivilegeId();
      id.setDiseaseId(tdiseaseId);
      tdiseasePrivilege.setId(id);
      String[] tempString = canAudit.split(",");
      id.setUserId(tempString[0]);
      id.setPrivilegeCodeId(tempString[1]);
      diseasePrivilegeDao.saveOrUpdate(tdiseasePrivilege);
    }
    if (StringUtils.isNotBlank(canPublish)) {
      TdiseasePrivilege tdiseasePrivilege = new TdiseasePrivilege();
      TdiseasePrivilegeId id = new TdiseasePrivilegeId();
      id.setDiseaseId(tdiseaseId);
      tdiseasePrivilege.setId(id);
      String[] tempString = canPublish.split(",");
      id.setUserId(tempString[0]);
      id.setPrivilegeCodeId(tempString[1]);
      diseasePrivilegeDao.saveOrUpdate(tdiseasePrivilege);
    }
  }

  /**
   * 描述: 根据用户id和疾病id获取权限
   * 
   * @param tdiseaseId
   * @param id
   * @return DiseasePrivilege
   */
  public DiseasePrivilege getPrivilegeByUserIdAndTdiseaseId(String tdiseaseId, String userId) {

    DiseasePrivilege diseasePrivilege = new DiseasePrivilege();
    // 超级管理员
    if ("0".equals(userId)) {
      diseasePrivilege.setCanEdit(true);
      diseasePrivilege.setCanAudit(true);
      diseasePrivilege.setCanPublish(true);
    }

    String hql = "from TdiseasePrivilege t where t.tuser.id=:userId and t.tdisease.id=:tdiseaseId";
    Map<String, Object> params = new HashMap<>();
    params.put("userId", userId);
    params.put("tdiseaseId", tdiseaseId);

    List<TdiseasePrivilege> findList = diseasePrivilegeDao.find(hql, params);

    if (findList != null) {

      for (TdiseasePrivilege tdiseasePrivilege : findList) {
        if (tdiseasePrivilege.getTprivilegeInfo().getId().equals(CodeConstant.DISEASE_PRIVILEGE_CANEDIT)) {
          diseasePrivilege.setCanEdit(true);
        } else if (tdiseasePrivilege.getTprivilegeInfo().getId().equals(CodeConstant.DISEASE_PRIVILEGE_CANAUDIT)) {
          diseasePrivilege.setCanAudit(true);
        } else if (tdiseasePrivilege.getTprivilegeInfo().getId().equals(CodeConstant.DISEASE_PRIVILEGE_CANPUBLISH)) {
          diseasePrivilege.setCanPublish(true);
        }
      }
    }

    return diseasePrivilege;
  }

  /**
   * 描述: 获取下一个orderNum值
   * 
   * @return int
   */
  public int getNextOrderNum() {
    int maxValue = diseaseDao.maxByHql("select max(t.orderNum) from Tdisease t ") + 1;
    return maxValue;
  }

  /**
   * 描述: 根据病名获取菜单项
   * 
   * @param diseaseName
   * @param userId
   * @return Tree
   */
  public Tree getMenuDataByName(String diseaseName, String userId) {

    List<Tree> diseaseList = getDiseaseByUserId(userId, null);

    for (Tree tree : diseaseList) {
      if (diseaseName.equals(tree.getText())) {
        return tree;
      }
    }

    return null;
  }

}
