package cn.com.infcn.core.pageModel;

import java.util.List;

/**
 * session信息模型
 * 
 * @author infcn
 * 
 */
public class SessionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 7666450198509328432L;
    private String id;// 用户ID
    private String loginName;// 用户登录名
    private String ip;// 用户IP
    private String userName; // 用户名
    private String expertIds;   //专家ID，多个逗号分割
    private String expertNames; //专家名称，多个逗号分割


    private List<String> moduleList;// 用户可以访问的模块地址列表
    
    //系统名称，用于区分前后台和子系统登陆
    private String systemName;
    

    public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public List<String> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<String> moduleList) {
        this.moduleList = moduleList;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getExpertIds() {
        return expertIds;
    }

    public void setExpertIds(String expertIds) {
        this.expertIds = expertIds;
    }

    public String getExpertNames() {
        return expertNames;
    }

    public void setExpertNames(String expertNames) {
        this.expertNames = expertNames;
    }

    @Override
    public String toString() {
        return this.loginName;
    }
    
    

}
