package cn.com.infcn.ade.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.model.Tbugtype;

@Service
public class BugTypeService {

    @Autowired
    private BaseDaoI<Tbugtype> bugType;

    @Cacheable(value = "bugTypeServiceCache", key = "'bugTypeList'")
    public List<Tbugtype> getBugTypeList() {
        return bugType.find("from Tbugtype t");
    }
}
