package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

import cn.com.infcn.core.modelAdd.TgenInput;

public class GenRule implements java.io.Serializable {

	private String id;
	private GenElement genElement;
	private Integer ruleLength;
	private String ruleIsmust;
	private GenInput genInput =new GenInput();
	private String ruleContext;
	private String ruleSource;
	private GenCheck genCheck =new GenCheck();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GenElement getGenElement() {
		return genElement;
	}

	public void setGenElement(GenElement genElement) {
		this.genElement = genElement;
	}

	public Integer getRuleLength() {
		return ruleLength;
	}

	public void setRuleLength(Integer ruleLength) {
		this.ruleLength = ruleLength;
	}

	public String getRuleIsmust() {
		return ruleIsmust;
	}

	public void setRuleIsmust(String ruleIsmust) {
		this.ruleIsmust = ruleIsmust;
	}

	public GenInput getGenInput() {
		return genInput;
	}

	public void setGenInput(GenInput genInput) {
		this.genInput = genInput;
	}

	public String getRuleContext() {
		return ruleContext;
	}

	public void setRuleContext(String ruleContext) {
		this.ruleContext = ruleContext;
	}

	public String getRuleSource() {
		return ruleSource;
	}

	public void setRuleSource(String ruleSource) {
		this.ruleSource = ruleSource;
	}

	public GenCheck getGenCheck() {
		return genCheck;
	}

	public void setGenCheck(GenCheck genCheck) {
		this.genCheck = genCheck;
	}
}
