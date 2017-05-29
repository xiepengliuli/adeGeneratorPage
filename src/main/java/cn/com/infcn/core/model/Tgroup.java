package cn.com.infcn.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * Tgroup generated by 项目开发部
 */
@Entity
@Table(name = "ade_group")
public class Tgroup implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  //
  private String id;
  //
  private Date createDate;
  //
  private String groupDesc;
  //
  private String groupFullName;
  //
  private String groupShortName;
  //
  private String groupSort;
  //
  private Date lastModifyDate;
  //
  private Tgroup tgroup;
  //
  private Tuser tuserByCreateUser;
  //
  private Tuser tuserByLastModifyUser;
  //
  private Set<Tgroup> tgroups =new HashSet<Tgroup>(0);

  public Tgroup() {
  }

  public Tgroup(String id) {
    this.id = id;
  }

  public Tgroup(String id, Date createDate, String groupDesc, String groupFullName, String groupShortName, String groupSort, Date lastModifyDate, Tgroup tgroup, Tuser tuserByCreateUser, Tuser tuserByLastModifyUser, Set<Tgroup> tgroups) {
    super();
    this.id = id;
    this.createDate = createDate;
    this.groupDesc = groupDesc;
    this.groupFullName = groupFullName;
    this.groupShortName = groupShortName;
    this.groupSort = groupSort;
    this.lastModifyDate = lastModifyDate;
    this.tgroup = tgroup;
    this.tuserByCreateUser = tuserByCreateUser;
    this.tuserByLastModifyUser = tuserByLastModifyUser;
    this.tgroups = tgroups;
  }

  @Id
  @Column(name = "ID", unique = true, nullable = false, length = 32)
  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "CREATE_DATE", nullable = true)
  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Column(name = "GROUP_DESC", nullable = false, length = 255)
  public String getGroupDesc() {
    return this.groupDesc;
  }

  public void setGroupDesc(String groupDesc) {
    this.groupDesc = groupDesc;
  }

  @Column(name = "GROUP_FULL_NAME", nullable = false, length = 200)
  public String getGroupFullName() {
    return this.groupFullName;
  }

  public void setGroupFullName(String groupFullName) {
    this.groupFullName = groupFullName;
  }

  @Column(name = "GROUP_SHORT_NAME", nullable = false, length = 200)
  public String getGroupShortName() {
    return this.groupShortName;
  }

  public void setGroupShortName(String groupShortName) {
    this.groupShortName = groupShortName;
  }

  @Column(name = "GROUP_SORT", nullable = true, length = 255)
  public String getGroupSort() {
    return this.groupSort;
  }

  public void setGroupSort(String groupSort) {
    this.groupSort = groupSort;
  }

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "LAST_MODIFY_DATE", nullable = true)
  public Date getLastModifyDate() {
    return this.lastModifyDate;
  }

  public void setLastModifyDate(Date lastModifyDate) {
    this.lastModifyDate = lastModifyDate;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "GROUP_PID")
  public Tgroup getTgroup() {
    return this.tgroup;
  }

  public void setTgroup(Tgroup tgroup) {
    this.tgroup = tgroup;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CREATE_USER")
  public Tuser getTuserByCreateUser() {
    return this.tuserByCreateUser;
  }

  public void setTuserByCreateUser(Tuser tuserByCreateUser) {
    this.tuserByCreateUser = tuserByCreateUser;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "LAST_MODIFY_USER")
  public Tuser getTuserByLastModifyUser() {
    return this.tuserByLastModifyUser;
  }

  public void setTuserByLastModifyUser(Tuser tuserByLastModifyUser) {
    this.tuserByLastModifyUser = tuserByLastModifyUser;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tgroup")
  public  Set<Tgroup> getTgroups() {
    return this.tgroups;
  }

  public void setTgroups(Set<Tgroup> tgroups) { 
    this.tgroups = tgroups; 
  }
}