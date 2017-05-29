package cn.com.infcn.core.modelAdd;
// Generated 2016-5-26 8:46:24 by Hibernate Tools 4.0.0.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TgenPage generated by hbm2java
 */
@Entity
@Table(name = "tgen_page", catalog = "bzpt_new")
public class TgenPage implements java.io.Serializable {

	private String id;
	private TgenPageModel tgenPageModel;
	private TgenPage tgenPage;
	private String pageButtonTypeid;
	private Integer pageHeight;
	private String pageLocation;
	private Integer pageOrderNumber;
	private String pageTitle;
	private String pageUrl;
	private Integer pageWidth;
	private Set<TgenElement> tgenElements = new HashSet<TgenElement>(0);
	private Set<TgenPage> tgenPages = new HashSet<TgenPage>(0);
	private Set<TgenButton> tgenButtons = new HashSet<TgenButton>(0);

	public TgenPage() {
	}

	public TgenPage(String id) {
		this.id = id;
	}

	public TgenPage(String id, TgenPageModel tgenPageModel, TgenPage tgenPage, String pageButtonTypeid,
			Integer pageHeight, String pageLocation, Integer pageOrderNumber, String pageTitle, String pageUrl,
			Integer pageWidth, Set<TgenElement> tgenElements, Set<TgenPage> tgenPages, Set<TgenButton> tgenButtons) {
		this.id = id;
		this.tgenPageModel = tgenPageModel;
		this.tgenPage = tgenPage;
		this.pageButtonTypeid = pageButtonTypeid;
		this.pageHeight = pageHeight;
		this.pageLocation = pageLocation;
		this.pageOrderNumber = pageOrderNumber;
		this.pageTitle = pageTitle;
		this.pageUrl = pageUrl;
		this.pageWidth = pageWidth;
		this.tgenElements = tgenElements;
		this.tgenPages = tgenPages;
		this.tgenButtons = tgenButtons;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "page_model_id")
	public TgenPageModel getTgenPageModel() {
		return this.tgenPageModel;
	}

	public void setTgenPageModel(TgenPageModel tgenPageModel) {
		this.tgenPageModel = tgenPageModel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "page_id")
	public TgenPage getTgenPage() {
		return this.tgenPage;
	}

	public void setTgenPage(TgenPage tgenPage) {
		this.tgenPage = tgenPage;
	}

	@Column(name = "page_button_typeid")
	public String getPageButtonTypeid() {
		return this.pageButtonTypeid;
	}

	public void setPageButtonTypeid(String pageButtonTypeid) {
		this.pageButtonTypeid = pageButtonTypeid;
	}

	@Column(name = "page_height")
	public Integer getPageHeight() {
		return this.pageHeight;
	}

	public void setPageHeight(Integer pageHeight) {
		this.pageHeight = pageHeight;
	}

	@Column(name = "page_location", length = 1000)
	public String getPageLocation() {
		return this.pageLocation;
	}

	public void setPageLocation(String pageLocation) {
		this.pageLocation = pageLocation;
	}

	@Column(name = "page_order_number")
	public Integer getPageOrderNumber() {
		return this.pageOrderNumber;
	}

	public void setPageOrderNumber(Integer pageOrderNumber) {
		this.pageOrderNumber = pageOrderNumber;
	}

	@Column(name = "page_title")
	public String getPageTitle() {
		return this.pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	@Column(name = "page_url")
	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	@Column(name = "page_width")
	public Integer getPageWidth() {
		return this.pageWidth;
	}

	public void setPageWidth(Integer pageWidth) {
		this.pageWidth = pageWidth;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tgenPage")
	public Set<TgenElement> getTgenElements() {
		return this.tgenElements;
	}

	public void setTgenElements(Set<TgenElement> tgenElements) {
		this.tgenElements = tgenElements;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tgenPage")
	public Set<TgenPage> getTgenPages() {
		return this.tgenPages;
	}

	public void setTgenPages(Set<TgenPage> tgenPages) {
		this.tgenPages = tgenPages;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tgen_page_buttton", catalog = "bzpt_new", joinColumns = {
			@JoinColumn(name = "page_id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "button_id", nullable = false, updatable = false) })
	public Set<TgenButton> getTgenButtons() {
		return this.tgenButtons;
	}

	public void setTgenButtons(Set<TgenButton> tgenButtons) {
		this.tgenButtons = tgenButtons;
	}

}
