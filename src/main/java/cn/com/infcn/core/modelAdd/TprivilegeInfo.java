package cn.com.infcn.core.modelAdd;

// Generated 2016-5-6 9:08:51 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TprivilegeInfo generated by hbm2java
 */
@Entity
@Table(name = "biz_privilege_info")
public class TprivilegeInfo implements java.io.Serializable {

    private String id;
    private String code;
    private String name;
    private Set<TdiseasePrivilege> tdiseasePrivileges = new HashSet<TdiseasePrivilege>(0);

    public TprivilegeInfo() {
    }

    public TprivilegeInfo(String id) {
        this.id = id;
    }

    public TprivilegeInfo(String id, String code, String name, Set<TdiseasePrivilege> tdiseasePrivileges) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.tdiseasePrivileges = tdiseasePrivileges;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 32)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "code", length = 50)
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "name", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tprivilegeInfo")
    public Set<TdiseasePrivilege> getTdiseasePrivileges() {
        return this.tdiseasePrivileges;
    }

    public void setTdiseasePrivileges(Set<TdiseasePrivilege> tdiseasePrivileges) {
        this.tdiseasePrivileges = tdiseasePrivileges;
    }

}