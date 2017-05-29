package cn.com.infcn.core.pageModelAdd;

// Generated 2016-4-22 13:21:47 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class Suggest implements java.io.Serializable {

	private String id;
	private String tstandardId;
	private String type;
	private String content;
	private String createUser;
	private Date createTime;

	private String isPass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTstandardId() {
		return tstandardId;
	}

	public void setTstandardId(String tstandardId) {
		this.tstandardId = tstandardId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 是否通过 0：未通过,1：通过
	 */
	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}
}
