package iprwcAPI.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
public class DBFile {
    @Id
    private String fileName;

    private String fileType;

    @Lob
    @Type(type= "org.hibernate.type.ImageType")
    private byte[] data;

    public DBFile() {}

    public DBFile(String fileName, byte[] data, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}