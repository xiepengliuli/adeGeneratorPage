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
 * TuserLogs generated by hbm2java
 */
@Entity
@Table(name = "ade_user_logs")
public class TuserLogs implements java.io.Serializable {

	private String id;
	private String content;
	private String ieType;
	private String ip;
	private String logType;
	private Date operateTime;
	private String userId;
	private String value1;
	private String value2;

	public TuserLogs() {
	}

	public TuserLogs(String id, String content, String ieType, String ip, String logType, Date operateTime) {
		this.id = id;
		this.content = content;
		this.ieType = ieType;
		this.ip = ip;
		this.logType = logType;
		this.operateTime = operateTime;
	}

	public TuserLogs(String id, String content, String ieType, String ip, String logType, Date operateTime,
			String userId, String value1, String value2) {
		this.id = id;
		this.content = content;
		this.ieType = ieType;
		this.ip = ip;
		this.logType = logType;
		this.operateTime = operateTime;
		this.userId = userId;
		this.value1 = value1;
		this.value2 = value2;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CONTENT", nullable = false)
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OPERATE_TIME", nullable = false, length = 19)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "USER_ID", length = 100)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "VALUE1", length = 100)
	public String getValue1() {
		return this.value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	@Column(name = "VALUE2", length = 100)
	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

}