package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class GenPage implements java.io.Serializable {

	private String id;
	private GenPageModel genPageModel;
	private GenPage genPage;
	private String pageButtonTypeid;
	private Integer pageHeight;
	private String pageLocation;
	private Integer pageOrderNumber;
	private String pageTitle;
	private String pageUrl;
	private Integer pageWidth;
	private List<GenElement> genElements = new ArrayList<GenElement>();
	private List<GenPage> genPages = new ArrayList<GenPage>();
	private List<GenButton> genButtons = new ArrayList<GenButton>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GenPageModel getGenPageModel() {
		return genPageModel;
	}
	public void setGenPageModel(GenPageModel genPageModel) {
		this.genPageModel = genPageModel;
	}
	public GenPage getGenPage() {
		return genPage;
	}
	public void setGenPage(GenPage genPage) {
		this.genPage = genPage;
	}
	public String getPageButtonTypeid() {
		return pageButtonTypeid;
	}
	public void setPageButtonTypeid(String pageButtonTypeid) {
		this.pageButtonTypeid = pageButtonTypeid;
	}
	public Integer getPageHeight() {
		return pageHeight;
	}
	public void setPageHeight(Integer pageHeight) {
		this.pageHeight = pageHeight;
	}
	public String getPageLocation() {
		return pageLocation;
	}
	public void setPageLocation(String pageLocation) {
		this.pageLocation = pageLocation;
	}
	public Integer getPageOrderNumber() {
		return pageOrderNumber;
	}
	public void setPageOrderNumber(Integer pageOrderNumber) {
		this.pageOrderNumber = pageOrderNumber;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public Integer getPageWidth() {
		return pageWidth;
	}
	public void setPageWidth(Integer pageWidth) {
		this.pageWidth = pageWidth;
	}
	public List<GenElement> getGenElements() {
		return genElements;
	}
	public void setGenElements(List<GenElement> genElements) {
		this.genElements = genElements;
	}
	public List<GenPage> getGenPages() {
		return genPages;
	}
	public void setGenPages(List<GenPage> genPages) {
		this.genPages = genPages;
	}
	public List<GenButton> getGenButtons() {
		return genButtons;
	}
	public void setGenButtons(List<GenButton> genButtons) {
		this.genButtons = genButtons;
	}
}
