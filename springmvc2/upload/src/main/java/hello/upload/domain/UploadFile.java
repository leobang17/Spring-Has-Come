package hello.upload.domain;

import lombok.Data;

@Data
public class UploadFile {

    // server에 저장된 파일 명은 따로 관리 (중복 x)
    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
