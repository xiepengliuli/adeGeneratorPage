package cn.com.infcn.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtils {

	/**
	 * 描述: 根据类的全名获取属性
	 *
	 * @param pageModelFullName
	 * @return List<String>
	 * @throws ClassNotFoundException
	 */
	public static  List<String> getModelPropertys(String pageModelFullName) throws ClassNotFoundException {

		ArrayList<String> arrayList = new ArrayList<String>();
		Class<?> tempClass = Class.forName(pageModelFullName);
		Field[] declaredFields = tempClass.getDeclaredFields();
		for (Field field : declaredFields) {
			arrayList.add(field.getName());
		}
		return arrayList;
	}

}
