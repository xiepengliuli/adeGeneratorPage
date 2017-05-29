package cn.com.infcn.core.pageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI tree模型
 * 
 * @author infcn
 * 
 */
public class Tree implements java.io.Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6414102995156691261L;

    /**
     * 树的唯一标识
     */
    private String id;

    /**
     * 树的标题
     */
    private String text;

    /**
     * 是否打开
     */
    private String state = "open";// open,closed

    /**
     * 如果为checkbox的树，这个值有意义
     */
    private boolean checked = false;

    /**
     * 连接串
     */
    private Object attributes;

    /**
     * 是否存在孙子节点
     */
    private boolean hasGrandson;

    /**
     * 子节点
     */
    private List<Tree> children=new ArrayList<Tree>();

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 父节点ID
     */
    private String pid;
    
    
    private Integer ResourcesCur =0;
    private Integer ResourcesChildren=0;
    private Integer ResourcesTotal=0;

    public Integer getResourcesCur() {
		return ResourcesCur;
	}

	public void setResourcesCur(Integer resourcesCur) {
		ResourcesCur = resourcesCur;
	}

	public Integer getResourcesChildren() {
		return ResourcesChildren;
	}

	public void setResourcesChildren(Integer resourcesChildren) {
		ResourcesChildren = resourcesChildren;
	}


	public Integer getResourcesTotal() {
		return ResourcesTotal;
	}

	public void setResourcesTotal(Integer resourcesTotal) {
		ResourcesTotal = resourcesTotal;
	}

	public Object getAttributes() {
        return attributes;
    }

    public List<Tree> getChildren() {
        return children;
    }

    public boolean getHasGrandson() {
        return hasGrandson;
    }

    public String getIconCls() {
        return iconCls;
    }

    public String getId() {
        return id;
    }

    public String getPid() {
        return pid;
    }

    public String getState() {
        return state;
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setChildren(List<Tree> children) {
        this.children = children;
    }

    public void setHasGrandson(boolean hasGrandson) {
        this.hasGrandson = hasGrandson;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setText(String text) {
        this.text = text;
    }

}
