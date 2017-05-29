package cn.com.infcn.jbzd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.Tsymptom;
import cn.com.infcn.core.pageModel.PageHelper;

/**
 * @author chenf
 * @date 2016年4月20日 下午5:00:05
 */
@Service
public class SymptomService {
    @Autowired
    private BaseDaoI<Tsymptom> symptomDao;
    
    
    /**
     * 根据关键字查询证候信息
     * @return PageHelper
     */
    public PageHelper findByKeyword(PageHelper pageHelper, String feilds, String keyword, String range){
        Map<String, Object> params = new HashMap<String, Object>();
        if (pageHelper.getPage()<=0) {
            pageHelper.setPage(1);
            
        }
        if (pageHelper.getRows() <= 0) {
            pageHelper.setRows(5);
        }
        String hql = "from Tsymptom ";
        //添加精确查询条件
        if("0".equals(range)){
            if("1".equals(feilds)){
                hql += "where name =:name";
                params.put("name", keyword);
            }else if("2".equals(feilds)){
                hql += "where disease_name =:disease_name";
                params.put("disease_name", keyword);
            }else if("3".equals(feilds)){
                hql += "where symptom_name =:symptom_name";
                params.put("symptom_name", keyword);
            }else if("4".equals(feilds)){
                hql += "where literature_name =:literature_name";
                params.put("literature_name", keyword);
            }else if("5".equals(feilds)){
                hql += "where journal_name =:journal_name";
                params.put("journal_name", keyword);
            }else if("6".equals(feilds)){
                hql += "where year_journal_page =:year_journal_page";
                params.put("year_journal_page", keyword);
            }
            
        }else if("1".equals(range)){//添加模糊查询条件
            if("1".equals(feilds)){
                hql += "where name like :name";
                params.put("name", "%" + keyword + "%");
            }else if("2".equals(feilds)){
                hql += "where disease_name like :disease_name";
                params.put("disease_name", "%" + keyword + "%");
            }else if("3".equals(feilds)){
                hql += "where symptom_name like :symptom_name";
                keyword = keyword.replace(",", "$");
                params.put("symptom_name", "%" + keyword + "%");
            }else if("4".equals(feilds)){
                hql += "where literature_name like :literature_name";
                params.put("literature_name", "%" + keyword + "%");
            }else if("5".equals(feilds)){
                hql += "where journal_name like :journal_name";
                params.put("journal_name", "%" + keyword + "%");
            }else if("6".equals(feilds)){
                hql += "where year_journal_page like :year_journal_page";
                params.put("year_journal_page", "%" + keyword + "%");
            }
        }
        hql += " order by abs(id)";
        PageHelper ph = find(hql, params, pageHelper);
        return ph;
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
        List<Tsymptom> symptomList = find(hql, params, pageHelper.getPage(), pageHelper.getRows());
        pageHelper.setData(symptomList);
        // 获取总记录数
        hql = "select count(*) " + hql;
        int rowCount = symptomDao.count(hql, params).intValue();
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
    public List<Tsymptom> find(String hql, Map<String, Object> params, int pageNum, int Pagecount) {

        List<Tsymptom> symptomList = new ArrayList<Tsymptom>();

        List<Tsymptom> tempList = symptomDao.find(hql, params, pageNum, Pagecount);

        for (Tsymptom Tsymptom : tempList) {
            Tsymptom tsymptom = new Tsymptom();
            BeanUtils.copyProperties(Tsymptom, tsymptom);

            symptomList.add(tsymptom);
        }

        return symptomList;

    }
    
     
}
