package com.zhangrenjie.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {


    private static final int  BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP 方法1
     * @param compressFilePath 压缩文件夹路径
     * @param excludeFolderName  是否保留原来的目录结构,true:保留目录结构;
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception 压缩失败会抛出运行时异常
     */
    public boolean toZip(String compressFilePath, String excludeFolderName)
            throws Exception {
        OutputStream out = new FileOutputStream(new File(compressFilePath + ".zip"));
        long start = System.currentTimeMillis();
        /*
        此类实现了一个输出流过滤器，用于以ZIP文件格式写入文件。 包括对压缩和未压缩条目的支持。
        https://nowjava.com/docs/java-api-11/java.base/java/util/zip/ZipOutputStream.html
         */
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(compressFilePath);
            compress(sourceFile,zos,sourceFile.getName(), excludeFolderName);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos        zip输出流
     * @param name       压缩后的名称
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception 一场
     */
    private void compress(File sourceFile, ZipOutputStream zos, String name, String excludeFolderName) throws Exception{
        byte[] buf = new byte[BUFFER_SIZE];
        // 如果是文件进行压缩
        if(sourceFile.isFile()){
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            /*
            ZipEntry 类用于表示 ZIP 文件条目
            https://blog.csdn.net/weixin_45109925/article/details/96443201
             */
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            /*
            对文件数据以字节的形式进行读取操作如读取图片视频等
            https://blog.csdn.net/ai_bao_zi/article/details/81097898
             */
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1){
                /*
                将一个字节数组写入当前ZIP条目数据
                https://nowjava.com/docs/java-api-11/java.base/java/util/zip/ZipOutputStream.html
                 */
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            // 如果是文件夹，获取文件列表
            File[] listFiles = sourceFile.listFiles();
            // 如果是空文件夹
            if(listFiles == null || listFiles.length == 0){
                // 空文件夹的处理
                /*
                putNextEntry 开始编写新的ZIP文件条目并将流定位到条目数据的开头。
                 */
                zos.putNextEntry(new ZipEntry(name + "/"));
                // 没有文件，不需要文件的copy
                zos.closeEntry();
            }else {
                // 如果不是空文件夹，便利每个元素
                for (File file : listFiles) {
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    if (!file.getName().equals(excludeFolderName)){
                        compress(file, zos, name + "/" + file.getName(), excludeFolderName);
                    }
                }
            }
        }
    }

    /**
     * 解压文件到指定目录
     * @param decompressionFilePath 压缩文件的名字（含路径 如D:/ZipTest.zip）
     */
    public boolean decompress(String decompressionFilePath){

        File file = new File(decompressionFilePath);
        String targetPath = file.getParentFile().getPath();

        ZipInputStream zipInputStream ;
        try {
            ZipFile zipFile = new ZipFile(file);
            zipInputStream= new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while(zipEntry != null){
//              if (zipEntry.isDirectory()){  //这样有可能是\结尾 导致空文件夹判断不了
                if (zipEntry.getName().endsWith("/") || zipEntry.getName().endsWith("\\")){
                    File dir = new File(targetPath +
                            zipEntry.getName().substring(0,zipEntry.getName().length()-1));//空文件的结尾带/
                    if (!dir.exists()){
                        dir.mkdirs();
                    }
                }else {
                    decompressFile(targetPath,zipEntry,zipFile);
                }
                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 解压单个文件（非文件夹）
     * targetPath 需要解压地址
     * zipFilePath 压缩文件的名字（含路径 如D:/ZipTest.zip）
     */
    private void decompressFile(String targetPath,ZipEntry zipEntry,ZipFile zipFile)throws Exception{
        File tmp = new File(targetPath+zipEntry.getName());
        if (!tmp.exists()){  //先判断父路径是否存在
            tmp.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(tmp);  //输出流生成新文件
            InputStream inputStream = zipFile.getInputStream(zipEntry); //输入流读取Zip文件

            byte bytes[]=new byte[1024];
            int n=0;
            while((n=inputStream.read(bytes))!= -1){
                String str = new String(bytes,0,n);
                outputStream.write(n);
            }
            outputStream.close();
            inputStream.close();
        }
    }
}
