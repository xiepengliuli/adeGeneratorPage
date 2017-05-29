package cn.com.infcn.core.pageModelAdd;
// Generated 2016-5-25 16:13:02 by Hibernate Tools 4.0.0.Final

public class GenPageModelColum implements java.io.Serializable {

	private String id;
	private GenPageModel genPageModel;
	private String columName;
	private String columRemark;
	private Integer orderNumber;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GenPageModel getGenPageModel() {
		return genPageModel;
	}
	public void setGenPageModel(GenPageModel genPageModel) {
		this.genPageModel = genPageModel;
	}
	public String getColumName() {
		return columName;
	}
	public void setColumName(String columName) {
		this.columName = columName;
	}
	public String getColumRemark() {
		return columRemark;
	}
	public void setColumRemark(String columRemark) {
		this.columRemark = columRemark;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}


}
