package cn.com.infcn.core.modelAdd;
// Generated 2016-5-26 8:46:24 by Hibernate Tools 4.0.0.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TgenPageModelColum generated by hbm2java
 */
@Entity
@Table(name = "tgen_page_model_colum")
public class TgenPageModelColum implements java.io.Serializable {

	private String id;
	private TgenPageModel tgenPageModel;
	private String columName;
	private String columRemark;
	private Integer orderNumber;

	public TgenPageModelColum() {
	}

	public TgenPageModelColum(String id, TgenPageModel tgenPageModel) {
		this.id = id;
		this.tgenPageModel = tgenPageModel;
	}

	public TgenPageModelColum(String id, TgenPageModel tgenPageModel, String columName, String columRemark,
			Integer orderNumber) {
		this.id = id;
		this.tgenPageModel = tgenPageModel;
		this.columName = columName;
		this.columRemark = columRemark;
		this.orderNumber = orderNumber;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pageModelId", nullable = false)
	public TgenPageModel getTgenPageModel() {
		return this.tgenPageModel;
	}

	public void setTgenPageModel(TgenPageModel tgenPageModel) {
		this.tgenPageModel = tgenPageModel;
	}

	@Column(name = "columName")
	public String getColumName() {
		return this.columName;
	}

	public void setColumName(String columName) {
		this.columName = columName;
	}

	@Column(name = "columRemark")
	public String getColumRemark() {
		return this.columRemark;
	}

	public void setColumRemark(String columRemark) {
		this.columRemark = columRemark;
	}

	@Column(name = "order_number")
	public Integer getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

}
