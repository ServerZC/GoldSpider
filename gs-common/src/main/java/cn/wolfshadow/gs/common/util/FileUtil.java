package cn.wolfshadow.gs.common.util;

import java.io.File;

public class FileUtil {

    public static final String CHARACTER_SET_UTF_8 = "UTF_8";
    public static final String CHARACTER_SET_GBK = "GBK";

    /**
     * 返回文件路径
     * @param directory 目录
     * @param fileName 文件名
     * @return
     */
    public static String getFilePath(String directory, String fileName){
        return directory + (directory.endsWith("\\")||directory.endsWith("/") ? "" : "/") + fileName;
    }

    /**
     * 返回指定文件
     * @param directory
     * @param fileName
     * @return
     */
    public static File getFile(String directory, String fileName){
        return new File(getFilePath(directory,fileName));
    }
}
