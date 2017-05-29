package cn.com.infcn.core.modelAdd;

// Generated 2016-5-6 9:08:51 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.infcn.core.model.Tuser;

/**
 * TdiseasePrivilege generated by hbm2java
 */
@Entity
@Table(name = "biz_disease_privilege")
public class TdiseasePrivilege implements java.io.Serializable {

    private TdiseasePrivilegeId id;
    private TprivilegeInfo tprivilegeInfo;
    private Tuser tuser;
    private Tdisease tdisease;

    public TdiseasePrivilege() {
    }

    public TdiseasePrivilege(TdiseasePrivilegeId id, TprivilegeInfo tprivilegeInfo, Tuser tuser, Tdisease tdisease) {
        this.id = id;
        this.tprivilegeInfo = tprivilegeInfo;
        this.tuser = tuser;
        this.tdisease = tdisease;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 32)),
            @AttributeOverride(name = "privilegeCodeId", column = @Column(name = "privilege_code_id", nullable = false, length = 32)),
            @AttributeOverride(name = "diseaseId", column = @Column(name = "disease_id", nullable = false, length = 32)) })
    public TdiseasePrivilegeId getId() {
        return this.id;
    }

    public void setId(TdiseasePrivilegeId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_code_id", nullable = false, insertable = false, updatable = false)
    public TprivilegeInfo getTprivilegeInfo() {
        return this.tprivilegeInfo;
    }

    public void setTprivilegeInfo(TprivilegeInfo tprivilegeInfo) {
        this.tprivilegeInfo = tprivilegeInfo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    public Tuser getTuser() {
        return this.tuser;
    }

    public void setTuser(Tuser tuser) {
        this.tuser = tuser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id", nullable = false, insertable = false, updatable = false)
    public Tdisease getTdisease() {
        return this.tdisease;
    }

    public void setTdisease(Tdisease tdisease) {
        this.tdisease = tdisease;
    }

}