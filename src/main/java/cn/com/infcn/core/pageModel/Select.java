/**
 * 
 */
package cn.com.infcn.core.pageModel;

/**
 * 描述: TODO
 *
 * @author xiep
 * @date 2016年5月10日 下午2:40:20
 */
public class Select {
	private String id;
	private String text;

	public Select(String id, String text) {
		super();
		this.id = id;
		this.text = text;
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

}
