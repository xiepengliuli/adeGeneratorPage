package cn.com.infcn.core.pageModelAdd;

// Generated 2016-4-22 13:21:47 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class AttachStandard implements java.io.Serializable {

    private String id;
    private String tstandardId;
    private String name;
    private String url;
    private String size;
    private Integer orderNum;
    private Date createTime;
    private String createUser;
    private String isCanDelete;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getIsCanDelete() {
        return isCanDelete;
    }

    public void setIsCanDelete(String isCanDelete) {
        this.isCanDelete = isCanDelete;
    }

}
