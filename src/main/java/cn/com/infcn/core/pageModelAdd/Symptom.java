package cn.com.infcn.core.pageModelAdd;

public class Symptom implements java.io.Serializable {

    private String id;
    private String name;
    private String diseaseName;
    private String symptomName;
    private String literatureName;
    private String journalName;
    private String yearJournalPage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getSymptomName() {
        return symptomName;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public String getLiteratureName() {
        return literatureName;
    }

    public void setLiteratureName(String literatureName) {
        this.literatureName = literatureName;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getYearJournalPage() {
        return yearJournalPage;
    }

    public void setYearJournalPage(String yearJournalPage) {
        this.yearJournalPage = yearJournalPage;
    }
}
