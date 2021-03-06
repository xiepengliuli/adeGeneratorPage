package cn.com.infcn.core.modelAdd;

// Generated 2016-5-6 9:08:51 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tdisease generated by hbm2java
 */
@Entity
@Table(name = "biz_disease")
public class Tdisease implements java.io.Serializable {

    private String id;
    private String firstLetter;
    private String code;
    private String name;
    private String tcmDiseaseName;
    private String mmDiseaseName;
    private String enName;
    private String remark;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private Integer orderNum;
    private Set<TdiseasePrivilege> tdiseasePrivileges = new HashSet<TdiseasePrivilege>(0);
    private Set<Tstandard> tstandards = new HashSet<Tstandard>(0);

    public Tdisease() {
    }

    public Tdisease(String id) {
        this.id = id;
    }

    public Tdisease(String id, String firstLetter, String code, String name, String tcmDiseaseName,
            String mmDiseaseName, String enName, String remark, String createUser, Date createTime, String updateUser,
            Date updateTime, Integer orderNum, Set<TdiseasePrivilege> tdiseasePrivileges, Set<Tstandard> tstandards) {
        this.id = id;
        this.firstLetter = firstLetter;
        this.code = code;
        this.name = name;
        this.tcmDiseaseName = tcmDiseaseName;
        this.mmDiseaseName = mmDiseaseName;
        this.enName = enName;
        this.remark = remark;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.orderNum = orderNum;
        this.tdiseasePrivileges = tdiseasePrivileges;
        this.tstandards = tstandards;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "first_letter", length = 2)
    public String getFirstLetter() {
        return this.firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Column(name = "code", length = 50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 200)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "tcm_disease_name", length = 500)
    public String getTcmDiseaseName() {
        return this.tcmDiseaseName;
    }

    public void setTcmDiseaseName(String tcmDiseaseName) {
        this.tcmDiseaseName = tcmDiseaseName;
    }

    @Column(name = "mm_disease_name", length = 1500)
    public String getMmDiseaseName() {
        return this.mmDiseaseName;
    }

    public void setMmDiseaseName(String mmDiseaseName) {
        this.mmDiseaseName = mmDiseaseName;
    }

    @Column(name = "en_name", length = 1500)
    public String getEnName() {
        return this.enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "remark", length = 65535)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "create_user", length = 32)
    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time", length = 19)
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "update_user", length = 32)
    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time", length = 19)
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "order_num")
    public Integer getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tdisease")
    public Set<TdiseasePrivilege> getTdiseasePrivileges() {
        return this.tdiseasePrivileges;
    }

    public void setTdiseasePrivileges(Set<TdiseasePrivilege> tdiseasePrivileges) {
        this.tdiseasePrivileges = tdiseasePrivileges;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tdisease")
    public Set<Tstandard> getTstandards() {
        return this.tstandards;
    }

    public void setTstandards(Set<Tstandard> tstandards) {
        this.tstandards = tstandards;
    }

}
