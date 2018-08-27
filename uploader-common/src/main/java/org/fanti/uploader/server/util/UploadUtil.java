package org.fanti.uploader.server.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.fanti.uploader.server.bean.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.locks.Lock;

/**
 * Description:
 *
 * @author ftk
 * @date 2018/8/26
 */

public class UploadUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadUtil.class);

    /**
     * 加载分片信息
     * @param list 存储分片信息的FileItem列表
     * @return 分片信息
     */
    public static FileInfo initFileInfo(List<FileItem> list) {
        if (list == null) {
            return null;
        }

        FileInfo fileInfo = new FileInfo();
        FileItem fileContent = null;

        //遍历list，得到用于封装第一个上传输入项数据fileItem对象
        for (FileItem item : list) {

            if (item.isFormField()) {
                //得到的是普通输入项
                String fieldName = item.getFieldName();  //得到输入项的名称
                String value = item.getString();
//                LOGGER.info("field:{}, value:{}" , fieldName, value);
                switchFileInfoField(fileInfo, fieldName, value);
            } else {
                fileContent = item;
            }
        }

        fileInfo.setFileItem(fileContent);

//        if (fileContent != null) {
//            initFileContent(fileInfo, fileContent, uploadFolder);
//        }

//        LOGGER.info("fileInfo:{}", JSON.toJSONString(fileInfo, true));
        return fileInfo;
    }


    /**
     * 加载文件分片信息
     * @param fileInfo 文件分片信息
     * @param fieldName 当前取到的字段名称
     * @param value 当前取到的字段值
     * @return 文件分片信息
     */
    private static FileInfo switchFileInfoField(FileInfo fileInfo, String fieldName, String value) {
        if (fileInfo == null) {
            fileInfo = new FileInfo();
        }

        switch (fieldName) {
            case "chunkNumber":
                fileInfo.setChunkNumber(Integer.parseInt(value));
                break;
            case "totalChunks":
                fileInfo.setTotalChunks(Integer.parseInt(value));
                break;
            case "currentChunkSize":
                fileInfo.setCurrentChunkSize(Long.parseLong(value));
                break;
            case "chunkSize":
                fileInfo.setChunkSize(Long.parseLong(value));
                break;
            case "totalSize":
                fileInfo.setTotalSize(Long.parseLong(value));
                break;
            case "identifier":
                fileInfo.setIdentifier(value);
                break;
            case "filename":
                fileInfo.setFilename(value);
                break;
            case "relativePath":
                fileInfo.setRelativePath(value);
                break;
            default:
                LOGGER.info("field:{}, value:{}", fieldName, value);
                break;
        }

        return fileInfo;
    }

    /**
     * 文件分片数为1的使用此方法接收数据
     * @param fileInfo 文件内容
     * @param targetDir 目标文件
     */
    public static void receiveSingleFile(FileInfo fileInfo, String targetDir) {
        try {
            FileItem fileContent = fileInfo.getFileItem();
            //得到上传输入项
            String filename = fileContent.getName();  //得到上传文件名
            LOGGER.info("filename:{}", filename);
            filename = filename.substring(filename.lastIndexOf("\\") + 1 );
            InputStream in = fileContent.getInputStream();   //得到上传数据
            int len = 0;
            byte[] buffer = new byte[1024];

            File file = new File(targetDir + "\\" + filename);;
            file.deleteOnExit();

            file.createNewFile();

            FileOutputStream out = new FileOutputStream(file);  //向upload目录中写入文件
            while ((len = in.read(buffer)) > 0 ) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

            fileInfo.setFile(file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 文件分片数大于1的使用此方法接收数据
     * @param fileInfo 文件内容
     * @param tmpDir 存放临时文件的位置
     */
    public static void receiveChunkFile(FileInfo fileInfo, String tmpDir) {
        try {
            //得到上传输入项
            FileItem fileContent = fileInfo.getFileItem();

            //文件名为chunk编号
            String filename = fileInfo.getChunkNumber() + "";
            LOGGER.info("filename:{}", filename);

            InputStream in = fileContent.getInputStream();   //得到上传数据
            int len = 0;
            byte[] buffer = new byte[1024];

            //创建目标目录
            String targetDir = tmpDir + File.separator + fileInfo.getIdentifier();
            File dirFile = new File(targetDir);
            dirFile.mkdirs();

            //创建目标文件
            File file = new File(targetDir + File.separator + filename);;
            file.deleteOnExit();

            file.createNewFile();

            //接收文件数据
            FileOutputStream out = new FileOutputStream(file);
            while ((len = in.read(buffer)) > 0 ) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

            fileInfo.setFile(file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    public static void mergeChunks(String filename, String identifier, String targetDir, String tmpDir) {
        String targetFilePath = targetDir + File.separator + filename;
        String chunkPath = tmpDir + File.separator + identifier;
        File targetFile = new File(targetFilePath);
        File chunkDir = new File(chunkPath);

        try {
            if (!chunkDir.exists() || !chunkDir.isDirectory()) {
                LOGGER.error("分片目录不存在");
            }

            targetFile.deleteOnExit();
            targetFile.createNewFile();

            File[] chunks = chunkDir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isDirectory()) {
                        return false;
                    }
                    return true;
                };
            });

            Lock lock = FileLockUtil.getLock(identifier);
            lock.lock();

            List<File> files = new ArrayList<File>(Arrays.asList(chunks));


            //按照名称排序文件，这里分片都是按照数字命名的
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if(Integer.valueOf(o1.getName()) < Integer.valueOf(o2.getName())){
                        return -1;
                    }
                    return 1;
                }
            });

            FileChannel outChannel = new FileOutputStream(targetFile).getChannel();

            //合并
            FileChannel inChannel;
            for(File file : files){
                inChannel = new FileInputStream(file).getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inChannel.close();
                //删除分片
                if(!file.delete()){
                    LOGGER.error("分片[" + identifier + "=>" + file.getName() + "]删除失败");
                }
            }
            outChannel.close();

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private static FileInfo initFileContent(FileInfo fileInfo, FileItem fileContent, String uploadFolder) {
        try {
            //得到上传输入项
            String filename = fileContent.getName();  //得到上传文件名
            LOGGER.info("filename:{}", filename);
            filename = filename.substring(filename.lastIndexOf("\\") + 1 );
            InputStream in = fileContent.getInputStream();   //得到上传数据
            int len = 0;
            byte[] buffer = new byte[1024];

            File file = null;
            if (fileInfo.getTotalChunks() > 1) {
                if (fileInfo.getChunkNumber() > 0) {
                    file = new File(uploadFolder + "\\" + filename + ".part" + fileInfo.getChunkNumber());
                } else {
                    LOGGER.error("文件分片编号有误,请检查分片信息:{}", JSON.toJSONString(fileInfo));
                }
            } else {
                file = new File(uploadFolder + "\\" + filename);
            }
            if (!file.exists()) {
                file.createNewFile();
            } else {
//                file.deleteOnExit();
                LOGGER.error("当前文件夹中已存在同名文件。");
                fileInfo.setFile(file);
            }

            FileOutputStream out = new FileOutputStream(file);  //向upload目录中写入文件
            while ((len = in.read(buffer)) > 0 ) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

            fileInfo.setFile(file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }


        return fileInfo;
    }
}
