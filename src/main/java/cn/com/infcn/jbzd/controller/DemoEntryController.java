package cn.com.infcn.jbzd.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.JsonResult;
import cn.com.infcn.core.pageModel.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModelAdd.DemoEntry;
import cn.com.infcn.jbzd.service.DemoEntryService;

/**
 * 
 * 描述: DemoController
 *
 * @author xiep
 * @date 2017年6月9日 上午10:09:48
 */
@Controller
@RequestMapping("/admin/demoEntry")
public class DemoEntryController extends BaseController {

    @Autowired
    private DemoEntryService demoEntryService;

    /**
     * 描述: 获取管理数据表格
     *
     * @param DemoEntry
     * @param ph
     * @return EasyUIDataGrid
     */
    @RequestMapping("/dataGrid")
    @ResponseBody
    public EasyUIDataGrid dataGrid(DemoEntry demoEntry, PageHelper ph, Integer page, Integer rows,
            HttpServletRequest request) {
        if (page == null) {
            ph.setPage(1);
        }
        if (rows == null) {
            ph.setRows(10);
        }
        return demoEntryService.dataGrid(demoEntry, ph);
    }

    /**
     * 描述: 添加
     *
     * @param demoEntry
     * @param request
     * @param session
     * @return
     * @throws Exception
     *             Json
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonResult add(DemoEntry demoEntry, HttpServletRequest request, HttpSession session) throws Exception {

        JsonResult jsonResult = new JsonResult();
        try {
            demoEntryService.add(demoEntry);
            jsonResult.setSuccess(true);
            jsonResult.setMsg("添加成功！");

        } catch (Exception e) {
            jsonResult.setMsg(e.getMessage());
        } finally {

        }
        return jsonResult;
    }

    /**
     * 
     * 描述: 根据Id获取
     *
     * @param request
     * @param id
     * @return DemoEntry
     */
    @ResponseBody
    @RequestMapping("/getById")
    public JsonResult getById(HttpServletRequest request, String id) {
        JsonResult jsonResult = new JsonResult();
        DemoEntry demoEntry;
        try {
            demoEntry = demoEntryService.get(id);
            jsonResult.setObj(demoEntry);
            jsonResult.setSuccess(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            jsonResult.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 描述: 修改
     *
     * @param demoEntry
     * @param request
     * @return Json
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonResult edit(DemoEntry demoEntry, HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        try {
            demoEntryService.edit(demoEntry);
            jsonResult.setSuccess(true);
            jsonResult.setMsg("保存成功！");


        } catch (Exception e) {
            jsonResult.setMsg(e.getMessage());
        }
        return jsonResult;
    }

    /**
     * 描述: 删除
     *
     * @param id
     * @param session
     * @param request
     * @return
     * @throws Exception
     *             Json
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(String id, HttpSession session, HttpServletRequest request) throws Exception {

        JsonResult j = new JsonResult();
        try {
            demoEntryService.deleteByID(id);
            j.setMsg("删除成功！");
            j.setSuccess(true);
        } catch (Exception e) {
            j.setMsg("删除失败！");
            j.setSuccess(true);
        }
        return j;
    }

    /**
     * 描述: 批量删除
     *
     * @param ids
     *            ('0','1','2')
     * @param session
     * @param request
     * @return
     * @throws Exception
     *             Json
     */
    @RequestMapping("/batchDelete")
    @ResponseBody
    public JsonResult batchDelete(String ids, HttpSession session, HttpServletRequest request) throws Exception {
        JsonResult j = new JsonResult();
        if (ids != null && ids.length() > 0) {
            for (String id : ids.split(",")) {
                if (id != null) {
                    this.delete(id, session, request);
                }
            }
        }
        j.setMsg("批量删除成功！");
        j.setSuccess(true);
        return j;
    }

}
