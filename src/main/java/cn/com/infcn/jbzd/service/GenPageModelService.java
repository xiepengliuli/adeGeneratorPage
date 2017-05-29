package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TgenPageModel;
import cn.com.infcn.core.modelAdd.TgenPageModelColum;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.EasyUISelectBox;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModelAdd.GenPageModel;
import cn.com.infcn.core.pageModelAdd.GenPageModelColum;
import cn.com.infcn.core.util.ReflectUtils;
import cn.com.infcn.core.util.Tools;

/**
 * 描述: TODO
 *
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class GenPageModelService {
	@Autowired
	private BaseDaoI<TgenPageModel> genPageModelDao;
	@Autowired
	private BaseDaoI<TgenPageModelColum> genPageModelColumDao;

	/**
	 * 描述: EasyUI-DataGrid访问的方法
	 *
	 * @param genPageModel
	 * @param ph
	 * @return EasyUIDataGrid
	 */
	public EasyUIDataGrid dataGrid(GenPageModel genPageModel, PageHelper ph) {

		EasyUIDataGrid dg = new EasyUIDataGrid();
		List<GenPageModel> ul = new ArrayList<GenPageModel>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = " from TgenPageModel t where 1=1";
		List<TgenPageModel> l = genPageModelDao.find(hql + whereHql(genPageModel, params) + orderHql(ph), params,
				ph.getPage(), ph.getRows());

		if (l != null && l.size() > 0) {
			for (TgenPageModel t : l) {
				GenPageModel u = new GenPageModel();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		}
		dg.setRows(ul);
		dg.setTotal(genPageModelDao.count("select count(*) " + hql + whereHql(genPageModel, params), params));
		return dg;
	}

	/**
	 * 描述: 拼接whereHql语句
	 *
	 * @param genPageModel
	 * @param params
	 * @return String
	 */
	private String whereHql(GenPageModel genPageModel, Map<String, Object> params) {

		String hql = "";

		if (genPageModel == null) {
			return hql;
		}

		if (StringUtils.isNotBlank(genPageModel.getPageModelName())) {
			hql += " and t.pageModelName like :pageModelName";
			params.put("pageModelName", "%" + genPageModel.getPageModelName() + "%");
		}
		if (StringUtils.isNotBlank(genPageModel.getPageModelFullName())) {
			hql += " and t.pageModelFullName like :pageModelFullName";
			params.put("pageModelFullName", "%" + genPageModel.getPageModelFullName() + "%");

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
	 * @param genPageModel
	 * @throws Exception
	 *             void
	 */
	synchronized public GenPageModel add(GenPageModel genPageModel) throws Exception {
		TgenPageModel u = new TgenPageModel();
		BeanUtils.copyProperties(genPageModel, u);

		genPageModelDao.save(u);

		GenPageModel pu = new GenPageModel();
		BeanUtils.copyProperties(u, pu);

		return pu;
	}

	/**
	 * 描述: 根据id获取
	 *
	 * @param id
	 * @return PageModel
	 */
	public GenPageModel get(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<TgenPageModel> ts = genPageModelDao.find("select distinct t from TgenPageModel t  where t.id = :id ",
				params);

		if (ts != null && ts.size() > 0) {
			TgenPageModel t = ts.get(0);
			GenPageModel u = new GenPageModel();
			BeanUtils.copyProperties(t, u);
			String columHql = "from TgenPageModelColum t where t.tgenPageModel.id='" + t.getId() + "'"
					+ "order by t.orderNumber";

			List<TgenPageModelColum> tgenPageModelColums = genPageModelColumDao.find(columHql);

			if (tgenPageModelColums != null) {
				for (TgenPageModelColum tpageModelColum : tgenPageModelColums) {
					GenPageModelColum pageModelColum = new GenPageModelColum();
					BeanUtils.copyProperties(tpageModelColum, pageModelColum);
					u.getGenPageModelColums().add(pageModelColum);
				}
			}

			return u;
		}

		return null;
	}

	/**
	 * 
	 * 描述: TODO
	 *
	 * @param id
	 * @return GenPageModel
	 */
	public GenPageModel getByIdContainsGenPageModelColum(String id) {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);

		List<TgenPageModel> ts = genPageModelDao.find("select distinct t from TgenPageModel t  where t.id = :id ",
				params);

		if (ts != null && ts.size() > 0) {
			TgenPageModel tGenPageModel = ts.get(0);
			GenPageModel genPageModel = new GenPageModel();
			BeanUtils.copyProperties(tGenPageModel, genPageModel);

			String hql = "from TgenPageModelColum t where t.tgenPageModel.id =:id order by t.orderNumber";
			List<TgenPageModelColum> tGenPageModelColums = genPageModelColumDao.find(hql, params);
			if (tGenPageModelColums != null && tGenPageModelColums.size() > 0) {
				for (TgenPageModelColum tgenPageModelColum : tGenPageModelColums) {
					GenPageModelColum genPageModelColum = new GenPageModelColum();
					BeanUtils.copyProperties(tgenPageModelColum, genPageModelColum);
					genPageModel.getGenPageModelColums().add(genPageModelColum);
				}
			} else {

				try {
					
					List<String> tempPropertyNames = ReflectUtils.getModelPropertys(genPageModel.getPageModelFullName());

					for (String propertyName : tempPropertyNames) {
						GenPageModelColum genPageModelColum = new GenPageModelColum();
						genPageModelColum.setColumName(propertyName);
						genPageModel.getGenPageModelColums().add(genPageModelColum);
					}

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return genPageModel;
		}

		return null;
	}

	/**
	 * 描述: //TODO
	 *
	 * @param genPageModel
	 * @throws Exception
	 *             void
	 */
	public void edit(GenPageModel genPageModel) throws Exception {
		TgenPageModel u = genPageModelDao.get(TgenPageModel.class, genPageModel.getId());
		if (u != null) {
			BeanUtils.copyProperties(genPageModel, u);
			genPageModelDao.update(u);
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
			TgenPageModel tPageModel = genPageModelDao.get(TgenPageModel.class, id);
			if (tPageModel != null) {
				String hql = "delete from TgenPageModelColum t where t.tgenPageModel.id=:id";
				Map<String, Object> params = new HashMap<>();
				params.put("id", id);
				genPageModelColumDao.executeHql(hql, params);
				genPageModelDao.delete(tPageModel);
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

		List<GenPageModel> genPageModelList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
		pageHelper.setData(genPageModelList);
		// 获取总记录数
		hql = "select count(*) " + hql;
		int rowCount = genPageModelDao.count(hql, params).intValue();
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
	 * @return List<PageModel>
	 */
	public List<GenPageModel> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

		List<GenPageModel> genPageModelList = new ArrayList<GenPageModel>();

		List<TgenPageModel> tempList = genPageModelDao.find(hql, params, pageNum, Pagecount);

		for (TgenPageModel tgenPageModel : tempList) {
			GenPageModel genPageModel = new GenPageModel();
			BeanUtils.copyProperties(tgenPageModel, genPageModel);

			genPageModelList.add(genPageModel);
		}

		return genPageModelList;

	}

	/**
	 * 描述: TODO
	 *
	 * @param genPageModel
	 *            void
	 */
	public void savePageModelColum(GenPageModel genPageModel) {

		TgenPageModel tpageModel = genPageModelDao.get(TgenPageModel.class, genPageModel.getId());

		if (tpageModel != null) {

			genPageModelColumDao.executeHql(
					"delete from TgenPageModelColum t where t.tgenPageModel.id='" + tpageModel.getId() + "'");
			int orderNum = 1;
			for (GenPageModelColum genPageModelColum : genPageModel.getGenPageModelColums()) {
				TgenPageModelColum tgenPageModelColum = new TgenPageModelColum();
				BeanUtils.copyProperties(genPageModelColum, tgenPageModelColum);
				tgenPageModelColum.setId(Tools.getUUID());
				tgenPageModelColum.setTgenPageModel(tpageModel);
				tgenPageModelColum.setOrderNumber(orderNum++);
				genPageModelColumDao.save(tgenPageModelColum);

			}
		}

	}

	/**
	 * 描述: TODO
	 *
	 * @return List<EasyUISelectBox>
	 */
	public List<EasyUISelectBox> getSelectBox() {
		ArrayList<EasyUISelectBox> arrayList = new ArrayList<EasyUISelectBox>();
		List<TgenPageModel> findList = genPageModelDao.find("from TgenPageModel t order by t.pageModelFullName");
		for (TgenPageModel tgenPageModel : findList) {
			EasyUISelectBox easyUISelectBox = new EasyUISelectBox();
			easyUISelectBox.setId(tgenPageModel.getId());
			easyUISelectBox.setText(tgenPageModel.getPageModelFullName());
			arrayList.add(easyUISelectBox);
		}

		return arrayList;
	}
}
