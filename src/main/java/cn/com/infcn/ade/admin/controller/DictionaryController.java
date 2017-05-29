package cn.com.infcn.ade.admin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.DictionaryService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.EasyUIComboTree;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.pageModel.Dictionary;
import cn.com.infcn.core.pageModel.DictionaryType;


/**
 * 字典信息管理
 * 
 * @author jijs
 * 
 */
@Controller
@RequestMapping("/admin/dictionary")
public class DictionaryController extends BaseController {

  private static final Logger LOG = Logger.getLogger(DictionaryController.class);

  @Autowired
  private DictionaryService dictionaryService = null;


  /**
   * 跳转到字典页
   * 
   * @param request
   * @return String
   */
  @RequestMapping("/manager")
  public String dictionary(HttpServletRequest request) {
    return "/admin/dictionary";
  }

  /**
   * 获取字典信息list
   * 
   * @param DictionaryType
   *          dictionaryType 字典类型
   * @param request
   * @return List<DictionaryType>
   */
  @ResponseBody
  @RequestMapping("/dictionaryDataGrid")
  public List<DictionaryType> dictionaryDataGrid(DictionaryType dictionaryType, HttpServletRequest request) {

    return dictionaryService.dictionaryTypesDataGrid(dictionaryType);
  }

  /**
   * 添加字典信息
   * 
   * @param Dictionary
   *          dictionary 字典信息
   * @param request
   * @return JsonResult
   */
  @ResponseBody
  @RequestMapping("/addDictionary")
  public JsonResult addDictionary(Dictionary dictionary, HttpServletRequest request) {

    JsonResult r = new JsonResult();
    try {

      if (dictionary.getParentId() == null || dictionary.getParentId().equals("")) {
        // 如果是父类字典项，添加到字典类型里
        DictionaryType dictionaryType = new DictionaryType();
        dictionaryType.setZdName(dictionary.getZdName());
        dictionaryType.setZdCode(dictionary.getZdCode());
        dictionaryType.setZdSort(dictionary.getZdSort());
        dictionaryType.setZdUse(dictionary.getZdUse());
        dictionaryType.setZdDesc(dictionary.getZdDesc());
        dictionaryService.saveDictionaryType(dictionaryType);
      } else {
        dictionaryService.saveDictionary(dictionary);
      }
      r.setMsg("添加成功。");
      r.setSuccess(true);
      r.setObj(dictionary);

    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      r.setMsg("添加失败" + e.toString());
    }

    return r;
  }

  /**
   * 编辑字典信息
   * 
   * @param Dictionary
   *          dictionary 字典信息
   * @param request
   * @return JsonResult
   */
  @ResponseBody
  @RequestMapping("/editDictionary")
  public JsonResult editDictionary(Dictionary dictionary, HttpServletRequest request) {

    JsonResult r = new JsonResult();
    try {
      if (dictionary.getParentId() == null || dictionary.getParentId().equals("")) {

        DictionaryType dictionaryType = new DictionaryType();
        dictionaryType.setId(dictionary.getId());
        dictionaryType.setZdName(dictionary.getZdName());
        dictionaryType.setZdCode(dictionary.getZdCode());
        dictionaryType.setZdDesc(dictionary.getZdDesc());
        dictionaryType.setZdUse(dictionary.getZdUse());
        dictionaryType.setZdSort(dictionary.getZdSort());
        dictionaryService.editDictionaryType(dictionaryType);
      } else {
        dictionaryService.editDictionary(dictionary);
      }
      r.setMsg("修改成功。");
      r.setSuccess(true);
     
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      r.setMsg("操作失败。");
    }
    return r;
  }

  /**
   * 获取字典列表
   * 
   * @return List<EasyUIComboTree>
   */
  @ResponseBody
  @RequestMapping("/dictionaryComboTree")
  public List<EasyUIComboTree> dictionaryComboTree() {

    return dictionaryService.dictionarysComboTree();
  }

  /**
   * 验证字典项是否有子分类
   * 
   * @param Dictionary
   *          dictionary 字典对象
   * @param HttpServletRequest
   *          request 请求
   * @return JsonResult
   */
  @ResponseBody
  @RequestMapping("/verifyDictionaryChildren")
  public JsonResult verifyDictionaryChildren(Dictionary dictionary, HttpServletRequest request) {

    JsonResult r = new JsonResult();
    r.setSuccess(true);

    boolean flag = dictionaryService.getDictionaryTypeIsChildren(dictionary.getId());
    if (flag) {
      r.setSuccess(false);
      r.setMsg("该字典项下还有子分类，不能进行删除操作。");
    }
    return r;
  }

  /**
   * 字典项名是否重复
   * 
   * @param Dictionary
   *          dictionary 字典对象
   * @param HttpServletRequest
   *          request 请求
   * @return JsonResult
   */
  @ResponseBody
  @RequestMapping("/verifyDictionary")
  public JsonResult verifyDictionary(Dictionary dictionary, HttpServletRequest request) {

    JsonResult r = new JsonResult();
    r.setSuccess(true);
    String parentId = request.getParameter("parentId");

    if (parentId == null || parentId.equals("")) {
      List<DictionaryType> list = new ArrayList<DictionaryType>();
      list = dictionaryService.verifyDictionaryType(dictionary.getId(), dictionary.getZdName(), dictionary.getZdCode());
      if (list != null && list.size() > 0) {
        r.setSuccess(false);
        r.setMsg("字典分类名重复。");
      }
    } else {

      List<Dictionary> list = new ArrayList<Dictionary>();
      list = dictionaryService.verifyDictionary(dictionary.getId(), dictionary.getZdName(), dictionary.getZdCode(),
          parentId);
      if (list != null && list.size() > 0) {
        r.setSuccess(false);
        r.setMsg("字典项名重复。");
      }
    }
    return r;
  }

  /**
   * 删除字典信息
   * 
   * @param Dictionary
   *          dictionary 字典对象
   * @param HttpServletRequest
   *          request 请求
   * @return JsonResult
   */
  @RequestMapping("/deleteDictionary")
  public @ResponseBody JsonResult deleteDictionary(Dictionary dictionary, HttpServletRequest request) {

    JsonResult r = new JsonResult();
    String name = "";
    try {
      if (dictionary.getParentId() == null || dictionary.getParentId().equals("")) {
        name = dictionaryService.deleteDictionaryType(dictionary.getId());
      } else {
        name = dictionaryService.deleteDictionary(dictionary.getId());
      }
      r.setMsg("[" + name + "] 删除成功。");
      r.setSuccess(true);
     
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      r.setMsg("删除失败：" + e.toString());
    }
    return r;
  }

  /**
   * 根据字典类型获取字典信息
   */
  @RequestMapping("/getDictionaryByType")
  public @ResponseBody List<Dictionary> getDictionaryByType(String zdTypeCode, HttpServletRequest request) {

    List<Dictionary> list = dictionaryService.getDictionaryByType(zdTypeCode);
    return list;
  }

}
