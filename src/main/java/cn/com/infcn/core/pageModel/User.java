package cn.com.infcn.core.pageModel;

import java.util.Date;
import java.util.List;

/**
 * 用户对象
 * 
 * @author NOLY DAKE
 *
 */
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 5573976003071149937L;

	/**
	 * 验证码
	 */
	private String captcha;

	private Date createDate;
	private Date createDateEnd;
	private Date createDateStart;
	private Date updateDate;
	private Date updateDateEnd;
	private Date updateDateStart;
	private String email;

	private String id;
	private String ip;
	private String loginName;

	/**
	 * 用户可以访问的模块地址列表
	 */
	private List<String> moduleList;
	private String nameLetter;
	private String password;
	private String mobilePhone;

	private String roleIds;
	private String roleNames;
	private String sex;
	private String telePhone;
	private String userDesc;
	private String userName;
	private String userPhoto;
	private String userState;
	
	private String canEdit;
	private String canAudit;
	private String canPublish;

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public String getCanAudit() {
		return canAudit;
	}

	public void setCanAudit(String canAudit) {
		this.canAudit = canAudit;
	}

	public String getCanPublish() {
		return canPublish;
	}

	public void setCanPublish(String canPublish) {
		this.canPublish = canPublish;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Date getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(Date createDateStart) {
		this.createDateStart = createDateStart;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDateEnd() {
		return updateDateEnd;
	}

	public void setUpdateDateEnd(Date updateDateEnd) {
		this.updateDateEnd = updateDateEnd;
	}

	public Date getUpdateDateStart() {
		return updateDateStart;
	}

	public void setUpdateDateStart(Date updateDateStart) {
		this.updateDateStart = updateDateStart;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<String> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<String> moduleList) {
		this.moduleList = moduleList;
	}

	public String getNameLetter() {
		return nameLetter;
	}

	public void setNameLetter(String nameLetter) {
		this.nameLetter = nameLetter;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getUserState() {
		return userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

}
