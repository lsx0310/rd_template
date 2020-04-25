package com.rendu.utils;

import cn.hutool.core.util.IdUtil;
import com.rendu.common.exception.FileException;
import com.rendu.common.response.ResultCode;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName FileUtil
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/15 10:36
 * @Version v1.0
 **/
public class FileUtil extends cn.hutool.core.io.FileUtil {
    
    /**
     * 定义GB的计算常量
     */
    
    public static final int GB = 1024 * 1024 * 1024;

    /**
     * 定义MB的计算常量
     */
    private static final int MB = 1024 * 1024;
    /**
     * 定义KB的计算常量
     */
    private static final int KB = 1024;
    
    /**
     * 格式化小数
     */
    private static final DecimalFormat DF = new DecimalFormat("0.00");
    
    private static final String fireWareSuffix = "din";
    
    
    
    
    /**
     * MultipartFile转File
     */
    
    public static File toFile(MultipartFile multipartFile){
        //获取文件名
        String fileName = checkBrowser(multipartFile.getOriginalFilename());
        //获取文件后缀
        String prefix = "." + getExtensionName(fileName);
        File file = null;
        try {
            file = File.createTempFile(IdUtil.simpleUUID(),prefix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    
    
    /**
     * 检查浏览器类型，获取正确的文件名
     */
    
    public static String checkBrowser(String fileName){
    
        //由于浏览器的不同对getOriginalFilename处理不同，Chrome 会获取到该文件的直接文件名称，IE/Edge会获取到文件上传时完整路径/文件名
        //所以对fileName做一个判断
        //check for unix-style path
        int unixSep = fileName.lastIndexOf("/");
        //check for windows-style path
        int winSep = fileName.lastIndexOf("\\");
        int pos = winSep > unixSep ? winSep : unixSep;
        if (pos != -1){
            fileName = fileName.substring(pos+1);
        }
        return fileName;
    }
    
    /**
     * 获取文件扩展名，不带 .
     */
    public static String getExtensionName(String fileName){
        fileName = checkBrowser(fileName);
        int len = fileName.length();
        if ((fileName != null) && len > 0){
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && (dot < len -1)){
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }
    
    
    /**
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNotEx(String fileName){
        fileName = checkBrowser(fileName);
        int len = fileName.length();
        if ((fileName != null) && len > 0){
            int dot = fileName.lastIndexOf(".");
            if (dot > -1 && (dot < len - 1)){
                return fileName.substring(0,dot);
            }
        }
        return fileName;
    }
    
    
    public static String getFileType(String type) {
        String documents = "txt doc pdf ppt pps xlsx xls docx";
        String music = "mp3 wav wma mpa ram ra aac aif m4a";
        String video = "avi mpg mpe mpeg asf wmv mov qt rm mp4 flv m4v webm ogv ogg";
        String image = "bmp dib pcp dif wmf gif jpg tif eps psd cdr iff tga pcd mpt png jpeg";
        String firmware = "bin";
        if(image.contains(type)){
            return "图片";
        } else if(documents.contains(type)){
            return "文档";
        } else if(music.contains(type)){
            return "音乐";
        } else if(video.contains(type)){
            return "视频";
        } else if(firmware.contains(type)){
            return "固件";
        }else {
            return "其他";
        }
    }
    
    /*
     * 判断上传文件是否为固件
     */
    
    public static boolean isFireWare(String type){
        return fireWareSuffix.equals(type);
    }
    
    /* 如果上传的文件为固件，那么解析出 固件对应的设备类型 及 版本号
    * */
    
    public static String[] getFireWareTypeAndVersion(String fileName){
        String filename = getFileNameNotEx(fileName);
        
        String[] split = filename.split("_");

        return split;
    }
    
    public static void checkSize(long maxSize, long size){
        if (size > (maxSize * MB)){
            throw new FileException(ResultCode.FILE_MAX_SIZE_OVERFLOW);
        }
    }
    
    
    /**
     * 将文件名解析成文件的上传路径
     */
    
    public static File upload(MultipartFile file,String filePath,Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");

        String suffix = getExtensionName(file.getOriginalFilename());
        String nowStr = format.format(date);
        
        String fileName = nowStr + "." + suffix;
        String path = filePath + fileName;
        //getCanonicalFile 可正确解析各种路径
        try {
            File dest = new File(path).getCanonicalFile();
            //检查是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            
            //文件写入
            file.transferTo(dest);
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    /**
     * 文件大小转换
     */
    public static String getSize(long size){
        String resultSize;
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = DF.format(size / (float) GB) + "GB   ";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = DF.format(size / (float) MB) + "MB   ";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = DF.format(size / (float) KB) + "KB   ";
        } else {
            resultSize = size + "B   ";
        }
        return resultSize;
    }
    
    /**
     * 下载文件
     * @param request /
     * @param response /
     * @param path /
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String path, boolean deleteOnExit) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        File file = new File(path);
        try {
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    if (deleteOnExit) {
                        file.deleteOnExit();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
