package model.kalu.x5;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;
import com.tencent.smtt.sdk.TbsReaderView;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;

import java.io.File;
import java.text.DecimalFormat;

import static model.kalu.x5.X5OfficeActivity.PATH;

@Router(path = PATH)
public final class X5OfficeActivity extends FragmentActivity implements ReaderCallback {

    public static final String PATH = "/module_x5_office/office";
    public static final String URL = "mFileUrl";
    public static final String NAME = "fileName";

    private TbsReaderView mTbsReaderView;
    private TextView tv_download;
    private RelativeLayout rl_tbsView;    //rl_tbsView为装载TbsReaderView的视图
    private ProgressBar progressBar_download;
    private DownloadManager mDownloadManager;
    private long mRequestId;
    private DownloadObserver mDownloadObserver;

    @Extra
    public String mFileUrl = "";
    @Extra
    public String fileName;//文件url 由文件url截取的文件名 上个页面传过来用于显示的文件名

    public String mFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterManager.getInstance().inject(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_module_x5_office);
        findViewById();

        if (!BuildConfig.isModule) {
            mFileUrl = "http://cdn.mozilla.net/pdfjs/tracemonkey.pdf";
            fileName = "TBS测试.pdf";
        }

        setTitle(fileName);

        mTbsReaderView = new TbsReaderView(this, this);
        rl_tbsView.addView(mTbsReaderView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        if ((mFileUrl == null) || (mFileUrl.length() <= 0)) {
            Toast.makeText(X5OfficeActivity.this, "获取文件url出错了",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mFileName = parseName(mFileUrl);
        if (isLocalExist()) {
            tv_download.setText("打开文件");
            tv_download.setVisibility(View.GONE);
            displayFile();
        } else {
            if (!mFileUrl.contains("http")) {
                new AlertDialog.Builder(X5OfficeActivity.this)
                        .setTitle("温馨提示:")
                        .setMessage("文件的url地址不合法哟，无法进行下载")
                        .setCancelable(false)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                return;
                            }
                        }).create().show();
            }
            startDownload();
        }
    }

    /**
     * 将url进行encode，解决部分手机无法下载含有中文url的文件的问题（如OPPO R9）
     *
     * @param url
     * @return
     * @author xch
     */
    private String toUtf8String(String url) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = String.valueOf(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    private void findViewById() {
        tv_download = (TextView) findViewById(R.id.tv_download);
        progressBar_download = (ProgressBar) findViewById(R.id.progressBar_download);
        rl_tbsView = (RelativeLayout) findViewById(R.id.rl_tbsView);
    }

    /**
     * 跳转页面
     *
     * @param context
     * @param fileUrl  文件url
     * @param fileName 文件名
     */
//    public static void actionStart(Context context, String fileUrl, String fileName) {
//        Intent intent = new Intent(context, X5OfficeActivity.class);
//        intent.putExtra("fileUrl", fileUrl);
//        intent.putExtra("fileName", fileName);
//        context.startActivity(intent);
//    }

    /**
     * 加载显示文件内容
     */
    private void displayFile() {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", getLocalFile().getPath());
        bundle.putString("tempPath", Environment.getExternalStorageDirectory()
                .getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(mFileName), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {

            File file = new File(getLocalFile().getPath());
            if (file.exists()) {
                Intent openintent = new Intent();
                openintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String type = getMIMEType(file);
                // 设置intent的data和Type属性。
                openintent.setDataAndType(/* uri */Uri.fromFile(file), type);
                // 跳转
                startActivity(openintent);
                finish();
            }
        }
    }

    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"}};

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 利用文件url转换出文件名
     *
     * @param url
     * @return
     */
    private String parseName(String url) {
        String fileName = null;
        try {
            fileName = url.substring(url.lastIndexOf("/") + 1);
        } finally {
            if (TextUtils.isEmpty(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }
        }
        return fileName;
    }

    private boolean isLocalExist() {
        return getLocalFile().exists();
    }

    private File getLocalFile() {
        return new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                mFileName);
    }

    /**
     * 下载文件
     */
    @SuppressLint("NewApi")
    private void startDownload() {
        mDownloadObserver = new DownloadObserver(new Handler());
        getContentResolver().registerContentObserver(
                Uri.parse("content://downloads/my_downloads"), true,
                mDownloadObserver);

        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //将含有中文的url进行encode
        String fileUrl = toUtf8String(mFileUrl);
        try {

            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(fileUrl));
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, mFileName);
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            mRequestId = mDownloadManager.enqueue(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query()
                .setFilterById(mRequestId);
        Cursor cursor = null;
        try {
            cursor = mDownloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                // 已经下载的字节数
                long currentBytes = cursor
                        .getLong(cursor
                                .getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                // 总需下载的字节数
                long totalBytes = cursor
                        .getLong(cursor
                                .getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                // 状态所在的列索引
                int status = cursor.getInt(cursor
                        .getColumnIndex(DownloadManager.COLUMN_STATUS));
                tv_download.setText("下载中...(" + formatKMGByBytes(currentBytes)
                        + "/" + formatKMGByBytes(totalBytes) + ")");
                // 将当前下载的字节数转化为进度位置
                int progress = (int) ((currentBytes * 1.0) / totalBytes * 100);
                progressBar_download.setProgress(progress);

                Log.i("downloadUpdate: ", currentBytes + " " + totalBytes + " "
                        + status + " " + progress);
                if (DownloadManager.STATUS_SUCCESSFUL == status
                        && tv_download.getVisibility() == View.VISIBLE) {
                    tv_download.setVisibility(View.GONE);
                    tv_download.performClick();
                    if (isLocalExist()) {
                        tv_download.setVisibility(View.GONE);
                        displayFile();
                    }
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTbsReaderView.onStop();
        if (mDownloadObserver != null) {
            getContentResolver().unregisterContentObserver(mDownloadObserver);
        }
    }

    @SuppressLint("Override")
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class DownloadObserver extends ContentObserver {

        private DownloadObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            queryDownloadStatus();
        }
    }

    /**
     * 将字节数转换为KB、MB、GB
     *
     * @param size 字节大小
     * @return
     */
    private String formatKMGByBytes(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.00");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
}
