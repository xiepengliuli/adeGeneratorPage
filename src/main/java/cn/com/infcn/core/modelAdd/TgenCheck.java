package cn.com.infcn.core.modelAdd;
// Generated 2016-5-26 8:46:24 by Hibernate Tools 4.0.0.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TgenCheck generated by hbm2java
 */
@Entity
@Table(name = "tgen_check")
public class TgenCheck implements java.io.Serializable {

	private String id;
	private String name;
	private Integer orderNumer;
	private String checkContext;
	private Set<TgenRule> tgenRules = new HashSet<TgenRule>(0);

	public TgenCheck() {
	}

	public TgenCheck(String id) {
		this.id = id;
	}

	public TgenCheck(String id, String name, Integer orderNumer, String checkContext, Set<TgenRule> tgenRules) {
		this.id = id;
		this.name = name;
		this.orderNumer = orderNumer;
		this.checkContext = checkContext;
		this.tgenRules = tgenRules;
	}

	@Id

	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "order_numer")
	public Integer getOrderNumer() {
		return this.orderNumer;
	}

	public void setOrderNumer(Integer orderNumer) {
		this.orderNumer = orderNumer;
	}

	@Column(name = "check_context")
	public String getCheckContext() {
		return this.checkContext;
	}

	public void setCheckContext(String checkContext) {
		this.checkContext = checkContext;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tgenCheck")
	public Set<TgenRule> getTgenRules() {
		return this.tgenRules;
	}

	public void setTgenRules(Set<TgenRule> tgenRules) {
		this.tgenRules = tgenRules;
	}

}
