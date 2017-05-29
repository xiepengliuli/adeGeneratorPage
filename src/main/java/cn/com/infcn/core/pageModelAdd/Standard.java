package cn.com.infcn.core.pageModelAdd;

import java.util.ArrayList;

// Generated 2016-4-22 13:21:47 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.List;

import cn.com.infcn.core.pageModel.UploadFileModel;

public class Standard implements java.io.Serializable {

	private String id;
	private String orderNum;
	private String type;
	private String cnTitle;
	private String enTitle;
	private String publishOrg;
	private Integer publishDate;
	private String source;
	private String language;
	private String typeStag;
	private String mmStandard;
	private String mmDiseaseName;
	private String tcmDiseaseName;
	private String tcmDiseaseDialectical;
	private String tcmStandard;
	private String effectTarget;
	private String effectStandard;
	private String remark;
	private String status;
	private Date createTime;
	private String createUser;
	private String updateUser;
	private Date updateTime;
	private String isDel;

	private String tdiseaseId;
	private String tsuggestIds;
	private String tattachStandardIds;
	private Integer publishDateStart;
	private Integer publishDateEnd;
	private Date updateTimeStart;
	private Date updateTimeEnd;

	// 编辑时回显用的
	private List<AttachStandard> attachsPage = new ArrayList<>();

	// 保存时用的
	private List<UploadFileModel> attachs = new ArrayList<>();

	// 权限控制
	private boolean canEdit;
	private boolean canDelete;
	private boolean canAuditSubmit;
	private boolean canAudit;
	private boolean canPublish;
	private boolean canRevokePublish;
	private boolean canPreview;
	private String userId;

	public List<UploadFileModel> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<UploadFileModel> attachs) {
		this.attachs = attachs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTdiseaseId() {
		return tdiseaseId;
	}

	public void setTdiseaseId(String tdiseaseId) {
		this.tdiseaseId = tdiseaseId;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCnTitle() {
		return cnTitle;
	}

	public void setCnTitle(String cnTitle) {
		this.cnTitle = cnTitle;
	}

	public String getEnTitle() {
		return enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getPublishOrg() {
		return publishOrg;
	}

	public void setPublishOrg(String publishOrg) {
		this.publishOrg = publishOrg;
	}

	public Integer getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Integer publishDate) {
		this.publishDate = publishDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTypeStag() {
		return typeStag;
	}

	public void setTypeStag(String typeStag) {
		this.typeStag = typeStag;
	}

	public String getMmStandard() {
		return mmStandard;
	}

	public void setMmStandard(String mmStandard) {
		this.mmStandard = mmStandard;
	}

	public String getMmDiseaseName() {
		return mmDiseaseName;
	}

	public void setMmDiseaseName(String mmDiseaseName) {
		this.mmDiseaseName = mmDiseaseName;
	}

	public String getTcmDiseaseName() {
		return tcmDiseaseName;
	}

	public void setTcmDiseaseName(String tcmDiseaseName) {
		this.tcmDiseaseName = tcmDiseaseName;
	}

	public String getTcmDiseaseDialectical() {
		return tcmDiseaseDialectical;
	}

	public void setTcmDiseaseDialectical(String tcmDiseaseDialectical) {
		this.tcmDiseaseDialectical = tcmDiseaseDialectical;
	}

	public String getTcmStandard() {
		return tcmStandard;
	}

	public void setTcmStandard(String tcmStandard) {
		this.tcmStandard = tcmStandard;
	}

	public String getEffectTarget() {
		return effectTarget;
	}

	public void setEffectTarget(String effectTarget) {
		this.effectTarget = effectTarget;
	}

	public String getEffectStandard() {
		return effectStandard;
	}

	public void setEffectStandard(String effectStandard) {
		this.effectStandard = effectStandard;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getTsuggestIds() {
		return tsuggestIds;
	}

	public void setTsuggestIds(String tsuggestIds) {
		this.tsuggestIds = tsuggestIds;
	}

	public String getTattachStandardIds() {
		return tattachStandardIds;
	}

	public void setTattachStandardIds(String tattachStandardIds) {
		this.tattachStandardIds = tattachStandardIds;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getPublishDateStart() {
		return publishDateStart;
	}

	public void setPublishDateStart(Integer publishDateStart) {
		this.publishDateStart = publishDateStart;
	}

	public Integer getPublishDateEnd() {
		return publishDateEnd;
	}

	public void setPublishDateEnd(Integer publishDateEnd) {
		this.publishDateEnd = publishDateEnd;
	}

	public List<AttachStandard> getAttachsPage() {
		return attachsPage;
	}

	public void setAttachsPage(List<AttachStandard> attachsPage) {
		this.attachsPage = attachsPage;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanAudit() {
		return canAudit;
	}

	public void setCanAudit(boolean canAudit) {
		this.canAudit = canAudit;
	}

	public boolean isCanPublish() {
		return canPublish;
	}

	public void setCanPublish(boolean canPublish) {
		this.canPublish = canPublish;
	}

	public boolean isCanAuditSubmit() {
		return canAuditSubmit;
	}

	public void setCanAuditSubmit(boolean canAuditSubmit) {
		this.canAuditSubmit = canAuditSubmit;
	}

	public boolean isCanRevokePublish() {
		return canRevokePublish;
	}

	public void setCanRevokePublish(boolean canRevokePublish) {
		this.canRevokePublish = canRevokePublish;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isCanPreview() {
		return canPreview;
	}

	public void setCanPreview(boolean canPreview) {
		this.canPreview = canPreview;
	}

}
