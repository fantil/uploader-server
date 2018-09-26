package org.fanti.uploader.server.bean;

import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.sql.Timestamp;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/26
 */

public class FileInfo {
    /**
     * 当前分片编号
     */
    private int chunkNumber;

    /**
     * 总分片数目
     */
    private int totalChunks;

    /**
     * 当前分片大小
     */
    private long currentChunkSize;

    /**
     * 单个文件分片大小
     */
    private long chunkSize;

    /**
     * 文件总大小
     */
    private long totalSize;

    /**
     * 文件标识符,目前为文件的md5值
     */
    private String identifier;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件相对路径,当上传单个文件时为"/",当上传文件夹时，为相对于文件夹的路径
     */
    private String relativePath;

    /**
     * 文件上传时，用户所处的目录
     */
    private String currentDir;

    /**
     * 文件完整的相对路径
     */
    private String fullRelativePath;

    /**
     * 文件在硬盘中实际存放的目录
     */
    private String realPath;

    /**
     * 实际文件对象
     */
    @JSONField(serialize = false)
    private File file;

    /**
     * 文件相关字段项
     */
    @JSONField(serialize = false)
    private FileItem fileItem;

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public int getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(int totalChunks) {
        this.totalChunks = totalChunks;
    }

    public long getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(long currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFullRelativePath() {
        return fullRelativePath;
    }

    public void setFullRelativePath(String fullRelativePath) {
        this.fullRelativePath = fullRelativePath;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getCurrentDir() {
        return currentDir;
    }

    public void setCurrentDir(String currentDir) {
        this.currentDir = currentDir;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileItem getFileItem() {
        return fileItem;
    }

    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }
}
