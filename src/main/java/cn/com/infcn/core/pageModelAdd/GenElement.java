package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class GenElement implements java.io.Serializable {

	private String id;
	private GenInput genInput;
	private GenPage genPage;
	private String elementId;
	private String elementName;
	private String elementNameRemark;
	private Integer elementOrderNumber;
	private Integer elementSize;
	private Integer elementWidth;
	private String elementRules;
	private GenRule genRule;
	
	private String isVisible;
	
	private String isGenerator;
	


	public String getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public String getIsGenerator() {
		return isGenerator;
	}

	public void setIsGenerator(String isGenerator) {
		this.isGenerator = isGenerator;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GenInput getGenInput() {
		return genInput;
	}
	public void setGenInput(GenInput genInput) {
		this.genInput = genInput;
	}
	public GenPage getGenPage() {
		return genPage;
	}
	public void setGenPage(GenPage genPage) {
		this.genPage = genPage;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getElementNameRemark() {
		return elementNameRemark;
	}
	public void setElementNameRemark(String elementNameRemark) {
		this.elementNameRemark = elementNameRemark;
	}
	public Integer getElementOrderNumber() {
		return elementOrderNumber;
	}
	public void setElementOrderNumber(Integer elementOrderNumber) {
		this.elementOrderNumber = elementOrderNumber;
	}
	public Integer getElementSize() {
		return elementSize;
	}
	public void setElementSize(Integer elementSize) {
		this.elementSize = elementSize;
	}
	public Integer getElementWidth() {
		return elementWidth;
	}
	public void setElementWidth(Integer elementWidth) {
		this.elementWidth = elementWidth;
	}
	public String getElementRules() {
		return elementRules;
	}
	public void setElementRules(String elementRules) {
		this.elementRules = elementRules;
	}
	public GenRule getGenRule() {
		return genRule;
	}
	public void setGenRule(GenRule genRule) {
		this.genRule = genRule;
	}
}
