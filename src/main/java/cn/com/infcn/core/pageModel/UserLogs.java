package cn.com.infcn.core.pageModel;

import java.util.Date;

public class UserLogs implements java.io.Serializable {

	private static final long serialVersionUID = -6840491920778833641L;

	private String content;
	private String id;
	private String ieType;
	private String ip;
	private String logType;
	private Date operateTime;
	private String userId;
	private String value1;
	private String value2;

	private Date timeStart;
	private Date timeEnd;
	private String userName;

	public String getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public String getIeType() {
		return ieType;
	}

	public String getIp() {
		return ip;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public String getUserId() {
		return userId;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIeType(String ieType) {
		this.ieType = ieType;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public Date getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
