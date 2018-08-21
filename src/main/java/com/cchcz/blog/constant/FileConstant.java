
package com.cchcz.blog.constant;

/**
 * 文件常量类
 *
 * @author cchcz
 * @date 2018/4/16 16:26
 * @since 1.0
 */
public interface FileConstant {

    /**
     * 默认图片的宽度:为一数组，如果为一个元素则必须等于该值才合格，如果为两个值，则为图片宽度应符合的区间值。<strong>元素个数最多只可为两个</strong>
     */
    int[] DEFAULT_IMG_WIDTH = {0, 1366};

    /**
     * 默认图片的高度
     */
    int[] DEFAULT_IMG_HEIGHT = {0, 768};

    /**
     * 默认图片的大小：单位B, 52428800B = 51200KB = 50M
     */
    int[] DEFAULT_IMG_SIZE = {1024, 52428800};
}
