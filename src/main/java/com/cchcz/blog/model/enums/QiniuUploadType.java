
package com.cchcz.blog.model.enums;

import com.cchcz.blog.constant.FileConstant;

/**
* @author cchcz

 * @date 2018/4/16 16:26
 * @since 1.0
 */
public enum QiniuUploadType {
    QRCODE("blog/qrcode/", FileConstant.DEFAULT_IMG_WIDTH, FileConstant.DEFAULT_IMG_HEIGHT, FileConstant.DEFAULT_IMG_SIZE),
    SIMPLE("blog/article/", FileConstant.DEFAULT_IMG_WIDTH, FileConstant.DEFAULT_IMG_HEIGHT, FileConstant.DEFAULT_IMG_SIZE),
    COVER_IMAGE("blog/cover/", FileConstant.DEFAULT_IMG_WIDTH, FileConstant.DEFAULT_IMG_HEIGHT, FileConstant.DEFAULT_IMG_SIZE);

    private String path;
    /**
     * 上传图片的宽度
     */
    private int[] width;
    /**
     * 上传图片的高度
     */
    private int[] height;
    /**
     * 上传图片的大小
     */
    private int[] size;

    QiniuUploadType(String path, int[] width, int[] height, int[] size) {
        this.path = path;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public int[] getWidth() {
        return width;
    }

    public int[] getHeight() {
        return height;
    }

    public int[] getSize() {
        return size;
    }
}
