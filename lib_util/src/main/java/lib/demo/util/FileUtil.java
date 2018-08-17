package lib.demo.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

/**
 * description: 文件与流处理工具类
 * created by kalu on 2016/11/19 11:58
 */
public final class FileUtil {

    private static String TAG = FileUtil.class.getSimpleName();

    /**
     * 安装APK
     */
    public static void installApk(Context context, File file) {

        try {
            Uri uri = Uri.fromFile(file);
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(install);
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    /**
     * 获取文件夹对象
     */
    public static File getSaveFolder(String folderName) {
        File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
        file.mkdirs();
        return file;
    }

    /**
     * 获取SDcard根目录
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 输入流转byte[]
     */
    public static byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        }
        byte[] in2b = null;
        BufferedInputStream in = new BufferedInputStream(inStream);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int rc;
        try {
            while ((rc = in.read()) != -1) {
                swapStream.write(rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(inStream, in, swapStream);
        }
        return in2b;
    }

    /**
     * 读取Assets文本文件 - 转换为String
     */
    public static String readAssets(Context context, String fileName) {
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                sb.append(mLine).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return "";
    }

    /**
     * 关闭流
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     */
    public static void copyFile(String oldPath, String newPath) {

        try {
            int byteread;
            int bytesum = 0;
            File oldfile = new File(oldPath);

            File newfile = new File(newPath.substring(0, newPath.lastIndexOf(File.separator)));
            if (!newfile.exists()) {
                newfile.mkdirs();
                LogUtil.e("ttt", "数据库不存在");
            } else {
                if (newfile.length() >= oldfile.length()) {
                    LogUtil.e("ttt", "数据库已存在");
                } else {
                    newfile.delete();
                    LogUtil.e("ttt", "数据库已损坏, 删除");
                }
            }

            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    public static void copyAssets(Context context, String oldPath, String newPath) {
        LogUtil.e("ttt", "copyAssets ==>  oldPath = " + oldPath + ", newPath = " + newPath);

        InputStream is = null;
        FileOutputStream fos = null;

        try {
            String fileNames[] = context.getAssets().list(oldPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(newPath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyAssets(context,oldPath + "/" + fileName, newPath + "/" + fileName);
                }
            } else {// 如果是文件

                is = context.getAssets().open(oldPath);
                File file = new File(newPath);

                if (!file.exists()) {
                    LogUtil.e("ttt", "数据库不存在");
                    fos = new FileOutputStream(new File(newPath));
                    byte[] buffer = new byte[1024];
                    int byteCount;
                    while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                        // buffer字节
                        fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                    }
                    fos.flush();// 刷新缓冲区
                    LogUtil.e("ttt", "数据库不存在, 复制成功");
                } else {
                    LogUtil.e("ttt", "数据库已存在");
                }
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        } finally {

            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static long getFileSize(File file) {
        long size = 0;
        FileChannel fc = null;
        try {
            if (file.exists() && file.isFile()) {
                FileInputStream fis = new FileInputStream(file);
                fc = fis.getChannel();
                size = fc.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }
}