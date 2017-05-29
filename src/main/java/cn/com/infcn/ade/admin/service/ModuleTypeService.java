package cn.com.infcn.ade.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.model.TmoduleType;
import cn.com.infcn.core.pageModel.ModuleType;

@Service
public class ModuleTypeService {

    @Autowired
    private BaseDaoI<TmoduleType> moduleType;

    @Cacheable(value = "resourceTypeServiceCache", key = "'resourceTypeList'")
    public List<ModuleType> getModuleTypeList() {
        List<TmoduleType> l = moduleType.find("from TmoduleType t");
        List<ModuleType> rl = new ArrayList<ModuleType>();
        if (l != null && l.size() > 0) {
            for (TmoduleType t : l) {
                ModuleType rt = new ModuleType();
                BeanUtils.copyProperties(t, rt);
                rl.add(rt);
            }
        }
        return rl;
    }

}
