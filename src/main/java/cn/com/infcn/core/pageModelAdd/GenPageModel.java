package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import java.util.ArrayList;
import java.util.List;

public class GenPageModel implements java.io.Serializable {

	private String id;
	private String pageModelName;
	private String pageModelFullName;
	private List<GenPage> genPages = new ArrayList<GenPage>();
	private List<GenPageModelColum> genPageModelColums = new ArrayList<GenPageModelColum>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPageModelName() {
		return pageModelName;
	}
	public void setPageModelName(String pageModelName) {
		this.pageModelName = pageModelName;
	}
	public String getPageModelFullName() {
		return pageModelFullName;
	}
	public void setPageModelFullName(String pageModelFullName) {
		this.pageModelFullName = pageModelFullName;
	}
	public List<GenPage> getGenPages() {
		return genPages;
	}
	public void setGenPages(List<GenPage> genPages) {
		this.genPages = genPages;
	}
	public List<GenPageModelColum> getGenPageModelColums() {
		return genPageModelColums;
	}
	public void setGenPageModelColums(List<GenPageModelColum> genPageModelColums) {
		this.genPageModelColums = genPageModelColums;
	}

}
