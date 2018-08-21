
package com.cchcz.blog.util;

import com.cchcz.blog.model.enums.QiniuUploadType;
import com.cchcz.blog.exception.BlogFileException;
import com.cchcz.blog.service.SysAttachService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 文件操作工具类
 *
 * @version 1.0
 * @date 2018/01/09 17:40
 * @since 1.0
 */
@Slf4j
public class FileUtil {
    private static final String[] PICTURE_SUFFIXS = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
    public static final String CLASSPATH = BlogUtils.getUplodFilePath();

    /**
     * 删除目录，返回删除的文件数
     *
     * @param rootPath 待删除的目录
     * @param fileNum  已删除的文件个数
     * @return 已删除的文件个数
     */
    public static int deleteFiles(String rootPath, int fileNum) {
        File file = new File(rootPath);
        if (!file.exists()) {
            return -1;
        }
        if (file.isDirectory()) {
            File[] sonFiles = file.listFiles();
            if (sonFiles != null && sonFiles.length > 0) {
                for (File sonFile : sonFiles) {
                    if (sonFile.isDirectory()) {
                        fileNum = deleteFiles(sonFile.getAbsolutePath(), fileNum);
                    } else {
                        sonFile.delete();
                        fileNum++;
                    }
                }
            }
        } else {
            file.delete();
        }
        return fileNum;
    }


    public static String getPrefix(File file) {
        return getPrefix(file.getName());
    }

    public static String getPrefix(String fileName) {
        int idx = fileName.lastIndexOf(".");
        int xie = fileName.lastIndexOf("/");
        idx = idx == -1 ? fileName.length() : idx;
        xie = xie == -1 ? 0 : xie + 1;
        return fileName.substring(xie, idx);
    }

    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    public static String getSuffix(String fileName) {
        int idx = fileName.lastIndexOf(".");
        idx = idx == -1 ? fileName.length() : idx;
        return fileName.substring(idx);
    }

    public static boolean isPicture(String suffix) {
        return !StringUtils.isEmpty(suffix) && Arrays.asList(PICTURE_SUFFIXS).contains(suffix.toLowerCase());
    }

    public static void mkdirs(String filePath) {
        File file = new File(filePath);
        mkdirs(file);
    }

    public static void mkdirs(File file) {
        if (!file.exists()) {
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.getParentFile().mkdirs();
            }
        }
    }

    public static String uploadToQiniu(MultipartFile multipartFile, QiniuUploadType uploadType, boolean canBeNull) {
        // 不可为空并且file为空，抛出异常
        if (!canBeNull && null == multipartFile) {
            throw new BlogFileException("请选择图片");
        }
        // 可为空并且file为空，忽略后边的代码，返回null
        if (canBeNull && null == multipartFile) {
            return null;
        }
        try {
            boolean isPicture = FileUtil.isPicture(FileUtil.getSuffix(multipartFile.getOriginalFilename()));
            if (isPicture) {
                String filename = multipartFile.getOriginalFilename();
//                filePath = QiniuApi.getInstance()
//                        .withFileName(file.getOriginalFilename(), uploadType)
//                        .upload(file.getBytes());
//                return UrlCodeUtil.encode(filePath);
                String fkey = BlogUtils.getFileKey(filename);
                File file = new File(CLASSPATH + fkey);
                try {
                    FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                } catch (IOException e) {
                    throw new BlogFileException("上传图片到七牛云发生异常，请检查七牛配置是否正常", e);
                }
                SysAttachService sysAttachService = SpringBeanFactory.getBean(SysAttachService.class);
                sysAttachService.save(filename, fkey, "image", 1);
                return fkey;
            } else {
                throw new BlogFileException("只支持图片");
            }
        } catch (Exception e) {
            throw new BlogFileException("上传图片到七牛云发生异常，请检查七牛配置是否正常", e);
        }
    }
}
