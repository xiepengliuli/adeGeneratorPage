package cn.com.infcn.core.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.infcn.core.util.FileUtil;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtil {

	/**
	 * 日志对象
	 */
	private static final Logger LOG = Logger.getLogger(WordUtil.class);

	/**
	 * @Desc：生成word文件
	 * @param newsList
	 *            word中需要展示的动态数据，用map集合来保存
	 * @param templateName
	 *            word模板名称，例如：test.ftl
	 * @param filePath
	 *            文件生成的目标路径，例如：D:/wordFile/
	 * @param fileName
	 *            生成的文件名称，例如：test.doc
	 */
	@SuppressWarnings("unchecked")
	public static String createWord(Map<String, Object> newsList,
			String templateName, String filePath, String fileName) {
		String path="";
		try {
			// 创建配置实例
			Configuration configuration = new Configuration();
			// 设置编码
			configuration.setDefaultEncoding("UTF-8");

			File file = new File(filePath);

			TemplateLoader templateLoader = new FileTemplateLoader(
					file.getParentFile());

			// ftl模板文件统一放至 com.lun.template 包下面
			configuration.setTemplateLoader(templateLoader);

			// 获取模板
			Template template = configuration.getTemplate(file.getName());

			// 输出文件
			File outFile = new File(FileUtil.getWebAppBasePath()
					+ File.separator + "generateReport" + File.separator
					+ fileName);

			// 如果输出目标文件夹不存在，则创建
			if (!outFile.getParentFile().exists()) {
				outFile.getParentFile().mkdirs();
			}

			// 将模板和数据模型合并生成文件
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(outFile), "UTF-8"));

			// 生成文件
			template.process(newsList, out);

			// 关闭流
			out.flush();
			out.close();
			path = outFile.getPath();
		} catch (Exception e) {
			path = "";
			e.printStackTrace();
		}
		return path;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean createWordTemplete(Map<String, Object> newsList,
			String filePath,Writer out) {
		boolean flag = false;
		try {
			// 创建配置实例
			Configuration configuration = new Configuration();
			// 设置编码
			configuration.setDefaultEncoding("UTF-8");
			// 输出文件
			File file = new File(filePath);

			TemplateLoader templateLoader = new FileTemplateLoader(
					file.getParentFile());

			// ftl模板文件统一放至 com.lun.template 包下面
			configuration.setTemplateLoader(templateLoader);

			// 获取模板
			Template template = configuration.getTemplate(file.getName());


			// 生成文件
			template.process(newsList, out);

			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} 
		return flag;
	}
}