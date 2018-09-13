package org.fanti.uploader.server.db;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/9/13
 */

@Table(name = "file")
public class DBFile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String length;

    private String md5;

    private String realPath;

    private String ext;

    private Timestamp firstUploadTime;

    private int firstUploadUserId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Timestamp getFirstUploadTime() {
        return firstUploadTime;
    }

    public void setFirstUploadTime(Timestamp firstUploadTime) {
        this.firstUploadTime = firstUploadTime;
    }

    public int getFirstUploadUserId() {
        return firstUploadUserId;
    }

    public void setFirstUploadUserId(int firstUploadUserId) {
        this.firstUploadUserId = firstUploadUserId;
    }
}
