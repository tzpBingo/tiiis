package org.utils;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by Administrator on 2016/4/22.
 * 解压.gz文件
 */
public class GZip {

    private BufferedOutputStream bufferedOutputStream;
    String zipfileName = null;

    public GZip(String fileName) {
        this.zipfileName = fileName;
    }

    /**
     * FileName为需要解压的文件路径(具体到文件),destDir为解压目标路径
     */
    public static void unTargzFile(String fileName, String destDir) {
        GZip gzip = new GZip(fileName);
        String outputDirectory = destDir;
        File file = new File(outputDirectory);
        if (!file.exists()) {
            file.mkdir();
        }
        gzip.unzipOarFile(outputDirectory);
    }

    public void unzipOarFile(String outputDirectory) {
        FileInputStream fis = null;
        ArchiveInputStream in = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            fis = new FileInputStream(zipfileName);
            GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(fis));
            in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
            bufferedInputStream = new BufferedInputStream(in);
            TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                String[] names = name.split("/");
                String fileName = outputDirectory;
                for (int i = 0; i < names.length; i++) {
                    String str = names[i];
                    fileName = fileName + File.separator + str;
                }
                if (name.endsWith("/")) {
                    mkFolder(fileName);
                } else {
                    File file = mkFile(fileName);
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file, true));
                    int b;
                    while ((b = bufferedInputStream.read()) != -1) {
                        bufferedOutputStream.write(b);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                }
                entry = (TarArchiveEntry) in.getNextEntry();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ArchiveException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void mkFolder(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            f.mkdir();
        }

    }

    private File mkFile(String fileName) {
        File f = new File(fileName);
        try {
            File dir = new File(f.getParent());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}
