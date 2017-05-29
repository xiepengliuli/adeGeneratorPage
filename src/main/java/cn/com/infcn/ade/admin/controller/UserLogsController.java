package cn.com.infcn.ade.admin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.infcn.ade.admin.service.AdminLogsService;
import cn.com.infcn.ade.admin.service.UserLogsService;
import cn.com.infcn.ade.core.controller.BaseController;
import cn.com.infcn.ade.core.model.EasyUIDataGrid;
import cn.com.infcn.core.pageModel.AdminLogs;
import cn.com.infcn.core.pageModel.PageHelper;
import cn.com.infcn.core.pageModel.UserLogs;

/**
 * 系统日志管理
 * 
 * @author laixq
 *
 */
@Controller
@RequestMapping("/admin/userLogs")
public class UserLogsController extends BaseController {

	@Autowired
	private UserLogsService userLogsService;

	/**
	 * 跳转到日志管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manage")
	public String manage() {
		return "/admin/logInfo/logInfoList";
	}

	/**
	 * 获取日志表格
	 * 
	 * @param logInfo
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public EasyUIDataGrid dataGrid(UserLogs logInfo, PageHelper ph, String ids, HttpSession session) {
		return userLogsService.dataGrid(logInfo, ph, ids);
	}

	/**
	 * 打印页面
	 * 
	 * @param request
	 * @param pageHelper
	 * @param logInfo
	 * @return
	 */
	@RequestMapping("/logList")
	public String logList(HttpServletRequest request, PageHelper pageHelper, UserLogs logInfo, String ids) {
		pageHelper.setRows(28);
		pageHelper.setPage(Math.max(pageHelper.getPage(), 1));
		try {

			EasyUIDataGrid eul = userLogsService.dataGrid(logInfo, pageHelper, ids);
			List<AdminLogs> list = eul.getRows();
			if (pageHelper != null) {
				request.setAttribute("pageCount", eul.getTotal());
				request.setAttribute("lList", list);
				request.setAttribute("logTotal", eul.getTotal());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/admin/logInfo/logSysList";
	}

	/**
	 * 导出Excel
	 * 
	 * @param logInfo
	 * @param request
	 * @param response
	 * @param pageHelper
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dowExcel")
	public String dowExcel(UserLogs logInfo, HttpServletRequest request, HttpServletResponse response,
			PageHelper pageHelper, String ids) throws Exception {
		// pageHelper.setRows(5);
		// pageHelper.setPage(Math.max(pageHelper.getPage(), 1));
		EasyUIDataGrid eul = userLogsService.dataGrid(logInfo, pageHelper, ids);
		List<AdminLogs> list = eul.getRows();
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null;
		int rowSize = 2;// Excel的行（从哪行开始写数据）
		int num = 1;

		HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
		style.setWrapText(true);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 3000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 7000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 7000);// 设置单元格宽度
		HSSFHeader header = sheet.getHeader();// 设置sheet的头

		header.setCenter("系统日志表");
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellStyle(style1);
		cell.setCellValue("序号");

		row.setHeight((short) 400);
		cell = row.createCell(1);
		cell.setCellStyle(style1);
		cell.setCellValue("操作人");

		cell = row.createCell(2);
		cell.setCellStyle(style1);
		cell.setCellValue("操作类型");

		cell = row.createCell(3);
		cell.setCellStyle(style1);
		cell.setCellValue("操作时间");

		cell = row.createCell(4);
		cell.setCellStyle(style1);
		cell.setCellValue("操作描述");

		cell = row.createCell(5);
		cell.setCellStyle(style1);
		cell.setCellValue("登录IP");

		for (AdminLogs ls : list) {
			row = sheet.createRow(rowSize++);

			cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(num++);

			cell = row.createCell(1);
			cell.setCellStyle(style);
			cell.setCellValue(ls.getUserId());

			cell = row.createCell(2);
			cell.setCellStyle(style);
			cell.setCellValue(ls.getLogType());

			cell = row.createCell(3);
			cell.setCellStyle(style);
			SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = sim.format(ls.getTimes());
			cell.setCellValue(time);

			cell = row.createCell(4);
			cell.setCellStyle(style);
			cell.setCellValue(ls.getLogType());

			cell = row.createCell(5);
			cell.setCellStyle(style);
			cell.setCellValue(ls.getIp());
		}
		OutputStream out = null;// 创建一个输出流对象
		try {
			out = response.getOutputStream();//
			response.setHeader("Content-disposition", "attachment; filename=" + "logInfo.xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=UTF-8");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			workbook.write(out);
			out.flush();
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	@RequestMapping("/logRecord")
	public String logRecord(String id, String tableName, HttpServletRequest request) {
		request.setAttribute("recordId", id);
		request.setAttribute("tableName", tableName);
		return "/admin/logInfo/logSysRecordList";
	}

	@RequestMapping("/dataGridRecord")
	@ResponseBody
	public EasyUIDataGrid dataGridRecord(String id, String tableName, String userName, String beginDate, String endDate,
			PageHelper ph) {
		EasyUIDataGrid list = userLogsService.getRecordId(id, tableName, userName, beginDate, endDate, ph);
		return list;
	}
}
