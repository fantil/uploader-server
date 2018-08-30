package org.fanti.uploader.server.dto;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/30
 */

public class FileDTO {
    private String id;

    private String name;

    private String path;

    private String type;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
