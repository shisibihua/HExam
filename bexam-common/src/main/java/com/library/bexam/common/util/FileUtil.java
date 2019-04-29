package com.library.bexam.common.util;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 处理文件上传
 * @author caoqian
 * @date 20190108
 */
public final class FileUtil {
    private static final int GB = 1073741824;
    private static final int MB = 1048576;
    private static final int KB = 1024;

    private FileUtil() {
    }

    public static String getFileSize(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "0KB";
        } else {
            long kSize = file.length();
            if (kSize / 1073741824L >= 1L) {
                return Math.round((float)kSize / 1.07374182E9F) + "GB";
            } else if (kSize / 1048576L >= 1L) {
                return Math.round((float)kSize / 1048576.0F) + "MB";
            } else {
                return kSize / 1024L >= 1L ? Math.round((float)kSize / 1024.0F) + "KB" : kSize + "Byte";
            }
        }
    }

    public static int getFileSizeKB(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return 0;
        } else {
            long kSize = file.length();
            return Math.round((float)kSize / 1024.0F);
        }
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        } else {
            int startIndex = file.getName().lastIndexOf(".");
            return startIndex == -1 ? "" : file.getName().substring(0, startIndex);
        }
    }

    public static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf(46);
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1).toLowerCase();
            }
        }

        return filename;
    }

    public static String getFileDic(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        } else {
            String fileName = file.getName();
            return filePath.replaceAll(fileName, "").trim();
        }
    }

    /**
     * 文件上传工具类服务方法
     * @param file       文件字节
     * @param filePath   生成文件路径
     * @param fileName   文件名
     * @throws Exception
     */

    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception{

        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+File.separator+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}

