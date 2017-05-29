package cn.com.infcn.core.model;

// Generated 2016-5-10 13:04:58 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TadminLogs generated by hbm2java
 */
@Entity
@Table(name = "ade_admin_logs")
public class TadminLogs implements java.io.Serializable {

    private String id;
    private String content;
    private String ieType;
    private String ip;
    private String logType;
    private String moduleName;
    private Date operateTime;
    private String userId;

    public TadminLogs() {
    }

    public TadminLogs(String id, String content, String ieType, String ip, String logType, Date operateTime,
            String userId) {
        this.id = id;
        this.content = content;
        this.ieType = ieType;
        this.ip = ip;
        this.logType = logType;
        this.operateTime = operateTime;
        this.userId = userId;
    }

    public TadminLogs(String id, String content, String ieType, String ip, String logType, String moduleName,
            Date operateTime, String userId) {
        this.id = id;
        this.content = content;
        this.ieType = ieType;
        this.ip = ip;
        this.logType = logType;
        this.moduleName = moduleName;
        this.operateTime = operateTime;
        this.userId = userId;
    }

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "CONTENT", nullable = false, length = 100)
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "IE_TYPE", nullable = false, length = 36)
    public String getIeType() {
        return this.ieType;
    }

    public void setIeType(String ieType) {
        this.ieType = ieType;
    }

    @Column(name = "IP", nullable = false, length = 36)
    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "LOG_TYPE", nullable = false, length = 60)
    public String getLogType() {
        return this.logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Column(name = "MODULE_NAME", length = 60)
    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATE_TIME", nullable = false, length = 19)
    public Date getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    @Column(name = "USER_ID", nullable = false, length = 100)
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
