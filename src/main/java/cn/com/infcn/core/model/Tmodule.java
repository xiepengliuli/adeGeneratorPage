package cn.com.infcn.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

/**
 * 
 * Tmodule generated by 项目开发部
 */
@Entity
@Table(name = "ade_module")
public class Tmodule implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  //
  private String id;
  //
  private String moduleDesc;
  //
  private String moduleIcon;
  //
  private String moduleName;
  //
  private Integer moduleSort;
  //
  private String moduleUrl;
  //
  private Tmodule tmodule;
  //
  private TmoduleType tmoduleType;
  //
  private Set<Tmodule> tmodules =new HashSet<Tmodule>(0);
  
  private Set<Trole> troles =new HashSet<Trole>(0);

  public Tmodule() {
  }

  public Tmodule(String id) {
    this.id = id;
  }

  public Tmodule(String id, String moduleDesc, String moduleIcon, String moduleName, Integer moduleSort, String moduleUrl, Tmodule tmodule, TmoduleType tmoduleType, Set<Tmodule> tmodules, Set<Trole> troles) {
    super();
    this.id = id;
    this.moduleDesc = moduleDesc;
    this.moduleIcon = moduleIcon;
    this.moduleName = moduleName;
    this.moduleSort = moduleSort;
    this.moduleUrl = moduleUrl;
    this.tmodule = tmodule;
    this.tmoduleType = tmoduleType;
    this.tmodules = tmodules;
    this.troles = troles;
  }

  @Id
  @Column(name = "ID", unique = true, nullable = false, length = 32)
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Column(name = "MODULE_DESC", nullable = true, length = 6000)
  public String getModuleDesc() {
    return this.moduleDesc;
  }

  public void setModuleDesc(String moduleDesc) {
    this.moduleDesc = moduleDesc;
  }

  @Column(name = "MODULE_ICON", nullable = true, length = 100)
  public String getModuleIcon() {
    return this.moduleIcon;
  }

  public void setModuleIcon(String moduleIcon) {
    this.moduleIcon = moduleIcon;
  }

  @Column(name = "MODULE_NAME", nullable = false, length = 100)
  public String getModuleName() {
    return this.moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  @Column(name = "MODULE_SORT", nullable = true, length = 11)
  public Integer getModuleSort() {
    return this.moduleSort;
  }

  public void setModuleSort(Integer moduleSort) {
    this.moduleSort = moduleSort;
  }

  @Column(name = "MODULE_URL", nullable = true, length = 200)
  public String getModuleUrl() {
    return this.moduleUrl;
  }

  public void setModuleUrl(String moduleUrl) {
    this.moduleUrl = moduleUrl;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MODULE_PID")
  public Tmodule getTmodule() {
    return this.tmodule;
  }

  public void setTmodule(Tmodule tmodule) {
    this.tmodule = tmodule;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MODULE_TYPE_ID")
  public TmoduleType getTmoduleType() {
    return this.tmoduleType;
  }

  public void setTmoduleType(TmoduleType tmoduleType) {
    this.tmoduleType = tmoduleType;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tmodule")
  public  Set<Tmodule> getTmodules() {
    return this.tmodules;
  }

  public void setTmodules(Set<Tmodule> tmodules) { 
    this.tmodules = tmodules; 
  }

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "ade_role_module", joinColumns = { @JoinColumn(name = "MODULE_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
  public Set<Trole> getTroles() {
    return this.troles;
  }

  public void setTroles(Set<Trole> troles) {
    this.troles = troles;
  }
}
