package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import java.util.ArrayList;
import java.util.List;

public class GenInput implements java.io.Serializable {

	private String id;
	private String name;
	private Integer orderNum;
	private List<GenElement> genElements = new ArrayList<GenElement>();
	private List<GenRule> genRules = new ArrayList<GenRule>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<GenElement> getGenElements() {
		return genElements;
	}

	public void setGenElements(List<GenElement> genElements) {
		this.genElements = genElements;
	}

	public List<GenRule> getGenRules() {
		return genRules;
	}

	public void setGenRules(List<GenRule> genRules) {
		this.genRules = genRules;
	}

}
