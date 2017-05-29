package cn.com.infcn.core.pageModel;

/**
 * 
 * @ClassName: UploadFileModel
 * @Description: 封装文件上传的返回值
 * 
 * @date 2016年3月23日 上午10:07:40
 */
public class UploadFileModel {

    /**
     * 文件原始名称
     */
    private String fileName_old;
    /**
     * 文件相对路径，包括新的文件名
     */
    private String filePath;
    /**
     * form表单中的 名称
     */
    private String fileFormName;

    private long size;

    public UploadFileModel() {
    }

    public UploadFileModel(String fileName_old, String filePath, String fileFormName) {
        this.fileName_old = fileName_old;
        this.filePath = filePath;
        this.fileFormName = fileFormName;
    }

    public UploadFileModel(String fileName_old, String filePath, String fileFormName, long size) {
        super();
        this.fileName_old = fileName_old;
        this.filePath = filePath;
        this.fileFormName = fileFormName;
        this.size = size;
    }

    public String getFileName_old() {
        return fileName_old;
    }

    public void setFileName_old(String fileName_old) {
        this.fileName_old = fileName_old;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileFormName() {
        return fileFormName;
    }

    public void setFileFormName(String fileFormName) {
        this.fileFormName = fileFormName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "UploadFileModel [fileName_old=" + fileName_old + ", filePath=" + filePath + ", fileFormName="
                + fileFormName + ", size=" + size + "]";
    }


}
