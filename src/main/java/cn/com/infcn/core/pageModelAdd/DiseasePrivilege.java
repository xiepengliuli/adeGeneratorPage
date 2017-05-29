package cn.com.infcn.core.pageModelAdd;

public class DiseasePrivilege implements java.io.Serializable {
	private boolean canEdit;
	private boolean canAudit;
	private boolean canPublish;

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
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

}
