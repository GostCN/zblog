
package com.cchcz.blog.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
@Data
@EqualsAndHashCode
public class ImageFileInfo {
    public long size;
    public String type;
    public int width;
    public int height;
    public String filename;

    public ImageFileInfo() {
    }

    public ImageFileInfo(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public ImageFileInfo withSize(long size) {
        this.size = size;
        return this;
    }

    public ImageFileInfo withType(String type) {
        this.type = type;
        return this;
    }

    public ImageFileInfo withWidth(int width) {
        this.width = width;
        return this;
    }

    public ImageFileInfo withHeight(int height) {
        this.height = height;
        return this;
    }

    public ImageFileInfo withFilename(String filename) {
        this.filename = filename;
        return this;
    }
}
