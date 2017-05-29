package cn.com.infcn.core.util;

import org.codehaus.jackson.map.ObjectMapper;

public final class JacksonUtil {
	private static ObjectMapper mapper;   
	static{
		mapper = new ObjectMapper(); 
	}
	
	public static String toJSONString(Object obj){
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			return "";
		}
	}
	
}
