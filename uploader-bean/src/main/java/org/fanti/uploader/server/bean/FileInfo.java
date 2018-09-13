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
    private int chunkNumber;

    private int totalChunks;

    private long currentChunkSize;

    private long chunkSize;

    private long totalSize;

    private String identifier;

    private String filename;

    private String relativePath;

    private String currentDir;

    @JSONField(serialize = false)
    private File file;

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
