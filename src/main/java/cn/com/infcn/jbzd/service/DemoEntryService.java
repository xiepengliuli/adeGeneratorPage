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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TdemoEntry;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.Json;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModelAdd.DemoEntry;
import cn.com.infcn.core.util.MD5Util;
import cn.com.infcn.core.util.StringUtil;

/**
 * 描述: TODO
 *
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class DemoEntryService {
    @Autowired
    private BaseDaoI<TdemoEntry> demoEntryDao;

    /**
     * 描述: EasyUI-DataGrid访问的方法
     *
     * @param demoEntry
     * @param ph
     * @return EasyUIDataGrid
     */
    public EasyUIDataGrid dataGrid(DemoEntry demoEntry, PageHelper ph) {
        EasyUIDataGrid dg = new EasyUIDataGrid();
        List<DemoEntry> ul = new ArrayList<DemoEntry>();
        Map<String, Object> params = new HashMap<String, Object>();
        String hql = " from TdemoEntry t ";
        List<TdemoEntry> l = demoEntryDao.find(hql + whereHql(demoEntry, params) + orderHql(ph), params, ph.getPage(),
                ph.getRows());

        if (l != null && l.size() > 0) {
            for (TdemoEntry t : l) {
                DemoEntry u = new DemoEntry();
                BeanUtils.copyProperties(t, u);
                ul.add(u);
            }
        }
        dg.setRows(ul);
        dg.setTotal(demoEntryDao.count("select count(*) " + hql + whereHql(demoEntry, params), params));
        return dg;
    }

    /**
     * 描述: 拼接whereHql语句
     *
     * @param demoEntry
     * @param params
     * @return String
     */
    private String whereHql(DemoEntry demoEntry, Map<String, Object> params) {
        String hql = "";
        if (demoEntry == null) {
            return hql;
        }
        hql += " where 1=1 ";
         if (StringUtils.isNotBlank(demoEntry.getName())) {
            hql += " and t.name like :name";
            params.put("name", "%" + demoEntry.getName() + "%");
         }
        // if (StringUtils.isNotBlank(demoEntry.getLoginName())) {
        // hql += " and t.loginName like :loginName";
        // params.put("loginName", "%" + demoEntry.getLoginName() + "%");
        // }

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
     * @param demoEntry
     * @throws Exception
     *             void
     */
    public DemoEntry add(DemoEntry demoEntry) throws Exception {
        TdemoEntry u = new TdemoEntry();
        BeanUtils.copyProperties(demoEntry, u);
        u.setId(StringUtil.generateUUID());

        demoEntryDao.save(u);

        DemoEntry pu = new DemoEntry();
        BeanUtils.copyProperties(u, pu);

        return pu;
    }

    /**
     * 描述: 根据id获取
     *
     * @param id
     * @return DemoEntry
     */
    public DemoEntry get(String id) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);

        List<TdemoEntry> ts = demoEntryDao.find("select distinct t from TdemoEntry t  where t.id = :id ", params);

        if (ts != null && ts.size() > 0) {
            TdemoEntry t = ts.get(0);
            DemoEntry u = new DemoEntry();
            BeanUtils.copyProperties(t, u);
            return u;
        }

        return null;
    }

    /**
     * 描述: //TODO
     *
     * @param demoEntry
     * @throws Exception
     *             void
     */
    public void edit(DemoEntry demoEntry) throws Exception {
        TdemoEntry u = demoEntryDao.get(TdemoEntry.class, demoEntry.getId());
        if (u != null) {
            u.setAttachments(demoEntry.getAttachments());
            u.setTags(demoEntry.getTags());
            u.setName(demoEntry.getName());
            demoEntryDao.update(u);
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
            TdemoEntry tDemoEntry = demoEntryDao.get(TdemoEntry.class, id);
            if (tDemoEntry != null) {
                demoEntryDao.delete(tDemoEntry);
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

        List<DemoEntry> demoEntryList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
        pageHelper.setData(demoEntryList);
        // 获取总记录数
        hql = "select count(*) " + hql;
        int rowCount = demoEntryDao.count(hql, params).intValue();
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
     * @return List<DemoEntry>
     */
    public List<DemoEntry> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

        List<DemoEntry> demoEntryList = new ArrayList<DemoEntry>();

        List<TdemoEntry> tempList = demoEntryDao.find(hql, params, pageNum, Pagecount);

        for (TdemoEntry tdemoEntry : tempList) {
            DemoEntry demoEntry = new DemoEntry();
            BeanUtils.copyProperties(tdemoEntry, demoEntry);

            demoEntryList.add(demoEntry);
        }

        return demoEntryList;

    }
}
