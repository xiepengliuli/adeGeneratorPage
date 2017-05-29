package cn.com.infcn.core.pageModel;

import java.util.List;

public class DictionaryType implements java.io.Serializable {

    private static final long serialVersionUID = 1787149007512702094L;

    private String id;
    private String zdCode;
    private String zdName;
    private String zdDesc;
    private String zdUse;
    private Integer zdSort;
    private String parentId;
    private List<Dictionary> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZdCode() {
        return zdCode;
    }

    public void setZdCode(String zdCode) {
        this.zdCode = zdCode;
    }

    public String getZdName() {
        return zdName;
    }

    public void setZdName(String zdName) {
        this.zdName = zdName;
    }

    public String getZdDesc() {
        return zdDesc;
    }

    public void setZdDesc(String zdDesc) {
        this.zdDesc = zdDesc;
    }

    public String getZdUse() {
        return zdUse;
    }

    public void setZdUse(String zdUse) {
        this.zdUse = zdUse;
    }

    public Integer getZdSort() {
        return zdSort;
    }

    public void setZdSort(Integer zdSort) {
        this.zdSort = zdSort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Dictionary> getChildren() {
        return children;
    }

    public void setChildren(List<Dictionary> children) {
        this.children = children;
    }

}
