package cn.com.infcn.core.pageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyUI DataGrid模型
 * 
 * @author infcn
 * 
 */
public class EasyUIDataGrid implements java.io.Serializable {

	private static final long serialVersionUID = -5853568065264527789L;
	private Long total = 0L;
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
