package com.example.nguyentrungkien.drumpadelectronic.DTO;

public class Mp3DTO {
    private String fileName, filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Mp3DTO() {

    }

    public Mp3DTO(String fileName, String filePath) {

        this.fileName = fileName;
        this.filePath = filePath;
    }
}
