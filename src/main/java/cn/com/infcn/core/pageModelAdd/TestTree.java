package cn.com.infcn.core.pageModelAdd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestTree implements java.io.Serializable {

	private String id;
	private TestTree testTree;
	private String text;
	private String name;
	private String iconCls;
	private String checked;
	private String state;
	private Set<TestTree> children = new HashSet<TestTree>(0);
	private Map<String,Object> attributes= new HashMap<>(0);
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public TestTree getTestTree() {
		return testTree;
	}
	public void setTestTree(TestTree testTree) {
		this.testTree = testTree;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<TestTree> getChildren() {
		return children;
	}
	public void setChildren(Set<TestTree> children) {
		this.children = children;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	

}
