package cn.com.infcn.ade.core.model;

import java.util.List;

public class EasyUIComboTree {

    private String id;
    private String text;
    private String state;
    private String iconCls;
    private List<EasyUIComboTree> children;

    private String type;
    
    
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public List<EasyUIComboTree> getChildren() {
        return children;
    }

    public void setChildren(List<EasyUIComboTree> children) {
        this.children = children;
    }

}
