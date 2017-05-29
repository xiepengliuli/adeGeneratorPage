package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.ade.core.model.EasyUIComboTree;
import cn.com.infcn.core.model.Tdictionary;
import cn.com.infcn.core.model.TdictionaryType;
import cn.com.infcn.core.pageModel.Dictionary;
import cn.com.infcn.core.pageModel.DictionaryType;
import cn.com.infcn.core.util.StringUtil;

/**
 * 
 * @author songgz
 * 
 */
@Service
public class DictionaryService {

	private static final Logger LOG = Logger.getLogger(DictionaryService.class);

	@Autowired
	private BaseDaoI<TdictionaryType> dictionaryTypeDao;
	@Autowired
	private BaseDaoI<Tdictionary> dictionaryDao;

	/**
	 * 获取字典项列表
	 * 
	 * @param dictionaryType
	 * @return
	 */
	public List<DictionaryType> dictionaryTypesDataGrid(DictionaryType dictionaryType) {

		String hql = " from TdictionaryType t order by t.zdTypeSort asc ";

		List<TdictionaryType> list = dictionaryTypeDao.find(hql);
		List<DictionaryType> dictionaryTypeList = new ArrayList<DictionaryType>();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TdictionaryType dt = list.get(i);
				DictionaryType dtBean = new DictionaryType();
				dtBean.setId(dt.getId());
				dtBean.setZdCode(dt.getZdTypeCode());
				dtBean.setZdDesc(dt.getZdTypeDesc());
				dtBean.setZdName(dt.getZdTypeName());
				dtBean.setZdSort(dt.getZdTypeSort());
				dtBean.setZdUse(dt.getZdTypeUse());

				Map<String, Object> params1 = new HashMap<String, Object>();
				String hql1 = " from Tdictionary t where t.tdictionaryType.id = :tdictionaryType ";
				hql1 += " order by t.zdSort asc ";
				params1.put("tdictionaryType", dt.getId());
				List<Tdictionary> list1 = dictionaryDao.find(hql1, params1);
				dtBean.setChildren(getDictionaryTypeChildren(list1));
				// dtBean.setChildren(getDictionaryTypeChildren(dt.getTdictionaries()));
				dictionaryTypeList.add(dtBean);
			}
			return dictionaryTypeList;
		}
		return null;
	}

	private List<Dictionary> getDictionaryTypeChildren(List<Tdictionary> set) {
		List<Dictionary> dList = new ArrayList<Dictionary>();
		Dictionary d = null;
		Iterator<Tdictionary> it = set.iterator();
		while (it.hasNext()) {
			Tdictionary t = it.next();
			d = new Dictionary();
			d.setId(t.getId());
			d.setZdCode(t.getZdCode());
			d.setZdName(t.getZdName());
			d.setZdSort(t.getZdSort());
			d.setZdDesc(t.getZdDesc());
			d.setZdUse(t.getZdUse());
			d.setParentId(t.getTdictionaryType().getId());
			d.setParentName(t.getTdictionaryType().getZdTypeName());
			dList.add(d);
		}

		return dList;
	}

	/**
	 * 保存字典项信息
	 * 
	 * @param dictionary
	 * @return
	 */

	public Dictionary saveDictionary(Dictionary dictionary) {

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

		Tdictionary t = new Tdictionary();
		BeanUtils.copyProperties(dictionary, t);
		t.setId(StringUtil.generateUUID());
		TdictionaryType tdictionaryType = new TdictionaryType();
		tdictionaryType.setId(dictionary.getParentId());
		t.setTdictionaryType(tdictionaryType);
		dictionaryDao.save(t);
		BeanUtils.copyProperties(t, dictionary);
		return dictionary;
	}

	/**
	 * 保存字典类型信息
	 * 
	 * @param dictionaryType
	 * @return
	 */

	public DictionaryType saveDictionaryType(DictionaryType dictionaryType) {

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

		TdictionaryType t = new TdictionaryType();
		t.setId(StringUtil.generateUUID());
		t.setZdTypeCode(dictionaryType.getZdCode());
		t.setZdTypeName(dictionaryType.getZdName());
		t.setZdTypeDesc(dictionaryType.getZdDesc());
		t.setZdTypeSort(dictionaryType.getZdSort());
		t.setZdTypeUse(dictionaryType.getZdUse());

		dictionaryTypeDao.save(t);

		dictionaryType.setId(t.getId());

		return dictionaryType;
	}

	/**
	 * 编辑字典项信息
	 * 
	 * @param dictionary
	 */

	public void editDictionary(Dictionary dictionary) {

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

		Tdictionary t = dictionaryDao.get(Tdictionary.class, dictionary.getId());
		t.setZdName(dictionary.getZdName());
		t.setZdCode(dictionary.getZdCode());
		t.setZdDesc(dictionary.getZdDesc());
		t.setZdSort(dictionary.getZdSort());
		TdictionaryType tdictionaryType = new TdictionaryType();
		tdictionaryType.setId(dictionary.getParentId());
		t.setTdictionaryType(tdictionaryType);
		t.setZdUse(dictionary.getZdUse());
		dictionaryDao.update(t);
	}

	/**
	 * 编辑字典分类信息
	 * 
	 * @param dictionaryType
	 */

	public void editDictionaryType(DictionaryType dictionaryType) {

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

		TdictionaryType t = dictionaryTypeDao.get(TdictionaryType.class, dictionaryType.getId());
		t.setZdTypeName(dictionaryType.getZdName());
		t.setZdTypeCode(dictionaryType.getZdCode());
		t.setZdTypeDesc(dictionaryType.getZdDesc());
		t.setZdTypeSort(dictionaryType.getZdSort());
		t.setZdTypeUse(dictionaryType.getZdUse());
		dictionaryTypeDao.update(t);
	}

	/**
	 * 获取文件分类 树列表
	 * 
	 * @param docClassify
	 * @return
	 */

	public List<EasyUIComboTree> dictionarysComboTree() {

		List<EasyUIComboTree> dictionaryList = new ArrayList<EasyUIComboTree>();

		String sql = " from TdictionaryType t order by t.zdTypeSort asc ";

		List<TdictionaryType> list = dictionaryTypeDao.find(sql);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TdictionaryType tdictionaryType = list.get(i);

				EasyUIComboTree tree = new EasyUIComboTree();
				tree.setId(tdictionaryType.getId());
				tree.setText(tdictionaryType.getZdTypeName());
				tree.setType(tdictionaryType.getZdTypeCode());

				dictionaryList.add(tree);
			}
			return dictionaryList;
		}
		return null;
	}

	/**
	 * 验证字典项是否重复
	 * 
	 * @param zdName
	 * @param zdCode
	 * @param parentId
	 * @return
	 */

	public List<Dictionary> verifyDictionary(String id, String zdName, String zdCode, String parentId) {

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

		List<Dictionary> dictionaryList = new ArrayList<Dictionary>();
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = " from Tdictionary t where t.zdName = :zdName and t.zdCode = :zdCode and t.tdictionaryType = :tdictionaryType ";
		if (id != null && !id.trim().equals("")) {
			hql += " and id <> :id ";
			params.put("id", id);
		}
		params.put("zdName", zdName);
		params.put("zdCode", zdCode);

		TdictionaryType tdictionaryType = new TdictionaryType();
		tdictionaryType.setId(parentId);
		params.put("tdictionaryType", tdictionaryType);

		List<Tdictionary> list = dictionaryDao.find(hql, params);

		if (list == null) {
			return null;
		}
		for (Tdictionary t : list) {
			Dictionary d = new Dictionary();
			BeanUtils.copyProperties(t, d);
			dictionaryList.add(d);
		}
		return dictionaryList;
	}

	/**
	 * 验证字典类型是否重复
	 * 
	 * @param id
	 * @param zdName
	 * @param zdCode
	 * @return
	 */

	public List<DictionaryType> verifyDictionaryType(String id, String zdName, String zdCode) {

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

		List<DictionaryType> dictionaryTypeList = new ArrayList<DictionaryType>();
		Map<String, Object> params = new HashMap<String, Object>();

		String hql = " from TdictionaryType t where t.zdTypeCode = :zdTypeCode and t.zdTypeName = :zdTypeName ";
		if (id != null && !id.trim().equals("")) {
			hql += " and id <> :id ";
			params.put("id", id);
		}
		params.put("zdTypeName", zdName);
		params.put("zdTypeCode", zdCode);

		List<TdictionaryType> list = dictionaryTypeDao.find(hql.toString(), params);

		if (list == null) {
			return null;
		}
		for (TdictionaryType t : list) {
			DictionaryType d = new DictionaryType();
			d.setId(t.getId());
			d.setZdCode(t.getZdTypeCode());
			d.setZdDesc(t.getZdTypeDesc());
			d.setZdName(t.getZdTypeName());
			d.setZdUse(t.getZdTypeUse());
			dictionaryTypeList.add(d);
		}
		return dictionaryTypeList;
	}

	/**
	 * 判断字典分类是否有子分类
	 * 
	 * @param id
	 * @return
	 */

	public boolean getDictionaryTypeIsChildren(String id) {

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

		TdictionaryType tdictionaryType = dictionaryTypeDao.get(TdictionaryType.class, id);

		Set<Tdictionary> list = tdictionaryType.getTdictionaries();

		if (list.size() > 0) {
			return true;
		}

		return false;
	}

	/**
	 * 删除字典项
	 * 
	 * @param id
	 * @return
	 */

	public String deleteDictionary(String id) {

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

		Tdictionary t = dictionaryDao.get(Tdictionary.class, id);
		String name = t.getZdName();
		dictionaryDao.delete(t);
		return name;
	}

	/**
	 * 删除字典类型
	 * 
	 * @param id
	 * @return
	 */

	public String deleteDictionaryType(String id) {

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

		// 删除文件分类明细
		TdictionaryType t = dictionaryTypeDao.get(TdictionaryType.class, id);
		String name = t.getZdTypeName();
		dictionaryTypeDao.delete(t);
		return name;
	}

	/**
	 * 根据字典类型编码获取子分类
	 * 
	 * @param zdTypeCode
	 * @return
	 */

	public List<Dictionary> getDictionaryByType(String zdTypeCode) {

		List<Dictionary> list = new ArrayList<>();
		String hql = "from Tdictionary t where t.tdictionaryType.zdTypeCode = :zdTypeCode order by t.zdSort";
		Map<String, Object> params = new HashMap<>();
		params.put("zdTypeCode", zdTypeCode);
		List<Tdictionary> findList = dictionaryDao.find(hql, params);
		for (Tdictionary tdictionary : findList) {
			Dictionary dictionary = new Dictionary();
			BeanUtils.copyProperties(tdictionary, dictionary);
			list.add(dictionary);
		}
		// String hql = "from TdictionaryType t where t.zdTypeCode =
		// :zdTypeCode";
		// Map<String, Object> params = new HashMap<String, Object>();
		// params.put("zdTypeCode", zdTypeCode);
		// TdictionaryType tdictionaryType = dictionaryTypeDao.get(hql, params);
		// if (tdictionaryType != null) {
		// Set<Tdictionary> tdictionaryList =
		// tdictionaryType.getTdictionaries();
		// if (tdictionaryList != null && tdictionaryList.size() > 0) {
		// Iterator<Tdictionary> it = tdictionaryList.iterator();
		// Tdictionary tdictionary = null;
		// Dictionary dictionary = null;
		// while (it.hasNext()) {
		// tdictionary = it.next();
		// dictionary = new Dictionary();
		// BeanUtils.copyProperties(tdictionary, dictionary);
		// dictionary.setParentId(tdictionaryType.getId());
		// dictionary.setParentCode(tdictionaryType.getZdTypeCode());
		// dictionary.setParentName(tdictionaryType.getZdTypeName());
		// list.add(dictionary);
		// }
		// }
		// }
		return list;
	}
}
