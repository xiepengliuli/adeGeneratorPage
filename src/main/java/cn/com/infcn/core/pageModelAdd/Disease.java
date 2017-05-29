package cn.com.infcn.core.pageModelAdd;

import java.util.Date;

public class Disease implements java.io.Serializable {

    private String id;
    private String firstLetter;
    private String code;
    private String name;
    private String tcmDiseaseName;
    private String mmDiseaseName;
    private String enName;
    private String remark;
    private Integer orderNum;
    
    private Date createTime;
    private Date updateTime;
    private String createUser;
    private String updateUser;

    private String tdiseasePrivilegeIds;
    private String tstandardIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTcmDiseaseName() {
        return tcmDiseaseName;
    }

    public void setTcmDiseaseName(String tcmDiseaseName) {
        this.tcmDiseaseName = tcmDiseaseName;
    }

    public String getMmDiseaseName() {
        return mmDiseaseName;
    }

    public void setMmDiseaseName(String mmDiseaseName) {
        this.mmDiseaseName = mmDiseaseName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTdiseasePrivilegeIds() {
        return tdiseasePrivilegeIds;
    }

    public void setTdiseasePrivilegeIds(String tdiseasePrivilegeIds) {
        this.tdiseasePrivilegeIds = tdiseasePrivilegeIds;
    }

    public String getTstandardIds() {
        return tstandardIds;
    }

    public void setTstandardIds(String tstandardIds) {
        this.tstandardIds = tstandardIds;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
