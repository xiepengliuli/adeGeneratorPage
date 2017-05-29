package cn.com.infcn.jbzd.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.infcn.ade.core.dao.BaseDaoI;
import cn.com.infcn.core.modelAdd.TattachStandard;
import cn.com.infcn.core.pageModelAdd.AttachStandard;

/**
 * 描述: 标准的附件管理
 * 
 * @author xiep
 * @date 2016年4月18日 下午5:00:05
 */
@Service
public class AttachStandardService {
  @Autowired
  private BaseDaoI<TattachStandard> attachStandardDao;

  /**
   * 描述: 根据附件的id删除附加
   * 
   * @param id
   *          void
   */
  public void deleteAttachById(String id) {
    TattachStandard tattachStandard = getAttachById(id);
    if (tattachStandard != null) {
      attachStandardDao.delete(tattachStandard);
    }
    // ?????????
  }

  /**
   * 
   * 描述: 根据id查询Tmodel
   * 
   * @param id
   * @return TattachStandard
   */
  private TattachStandard getAttachById(String id) {
    return attachStandardDao.get(TattachStandard.class, id);

  }

  public AttachStandard get(String id) {
    TattachStandard attach = getAttachById(id);
    AttachStandard attachStandard = new AttachStandard();
    if (attach != null) {
      BeanUtils.copyProperties(attach, attachStandard);
    }
    return attachStandard;
  }

}
