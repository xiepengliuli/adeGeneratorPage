package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.com.infcn.core.modelAdd.TgenRule;

public class GenCheck implements java.io.Serializable {

	private String id;
	private String name;
	private Integer orderNumer;
	private String checkContext;
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
	public Integer getOrderNumer() {
		return orderNumer;
	}
	public void setOrderNumer(Integer orderNumer) {
		this.orderNumer = orderNumer;
	}
	public String getCheckContext() {
		return checkContext;
	}
	public void setCheckContext(String checkContext) {
		this.checkContext = checkContext;
	}
	public List<GenRule> getGenRules() {
		return genRules;
	}
	public void setGenRules(List<GenRule> genRules) {
		this.genRules = genRules;
	}


}
