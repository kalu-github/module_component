//package com.model.photo.main;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.support.v4.app.FragmentManager;
//
//import com.model.photo.R;
//import com.quasar.hdoctor.R;
//import com.quasar.hdoctor.bean.album.AlbumFileBean;
//import com.quasar.hdoctor.bean.album.AlbumPhotoBean;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import lib.quasar.base.frame.BasePresenter;
//import lib.quasar.context.BaseConstant;
//import lib.quasar.util.LogUtil;
//
///**
// * description: 选择图片
// * created by kalu on 2018/4/18 15:53
// */
//public class AlbumPresenter implements BasePresenter {
//
//    private final LinkedHashMap<String, AlbumFileBean> mFolderList = new LinkedHashMap<>();
//
//    void initAdapter(AlbumView view, FragmentManager manager) {
//
//        if (null == view || null == manager) return;
//
//        AlbumAdapter adapter = new AlbumAdapter(manager);
//        view.setAlbumPager(adapter);
//    }
//
//    LinkedHashMap getAlbumFolderList() {
//        return mFolderList;
//    }
//
//    @SuppressLint("CheckResult")
//    void scanLocalPhoto(final Context context, final AlbumView view, final ArrayList<AlbumPhotoBean> mData) {
//
//        if (null == context || null == view) return;
//
//        Observable.create((ObservableOnSubscribe<LinkedHashMap<String, AlbumFileBean>>) e -> {
//
//            final String[] projection = {     //查询图片需要的数据列
//                    MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
//                    MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
//                    MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
//                    MediaStore.Images.Media.WIDTH,          //图片的宽度，int型  1920
//                    MediaStore.Images.Media.HEIGHT,         //图片的高度，int型  1080
//                    MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
//                    MediaStore.Images.Media.DATE_ADDED};    //图片被添加的时间，long型  1450518608
//
//            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//            LogUtil.e("scanLocalPhoto", "uri = " + uri.toString());
//
//            final Cursor cursor = context.getContentResolver().query(uri, projection, null, null, MediaStore.Images.Media.DATE_ADDED);
//            LogUtil.e("scanLocalPhoto", "query = " + cursor.getCount());
//            while (cursor.moveToNext()) {
//
//                final long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
//                final int width = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
//                final int height = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
//                final String type = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));
//
//                if ("image/jpeg".equals(type) && size > 1024 && width > 100 && height > 100) {
//
//                    // 图片路径
//                    String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
//                    if (!name.contains("http:www.") && !name.contains("https:www.") && !name.contains(BaseConstant.CACHE_COMPRESS)) {
//
//                        final File file = new File(name);
//                        if (file.isFile() && file.exists()) {
//
//                            // 初次创建, 全部文件夹
//                            if (null != mFolderList && mFolderList.size() == 0) {
//
//                                final AlbumFileBean albumFolder = new AlbumFileBean();
//                                String folder = context.getResources().getString(R.string.comm_album_all);
//                                albumFolder.setSelect(true);
//                                albumFolder.setFolderName(folder);
//                                albumFolder.setFolderImage(name);
//
//                                AlbumPhotoBean albumPhoto = new AlbumPhotoBean();
//                                albumPhoto.setPicture(name);
//                                albumPhoto.setWidth(width);
//                                albumPhoto.setHeight(height);
//                                albumPhoto.setSize(size);
//                                albumFolder.insertImageList(albumPhoto);
//
//                                mFolderList.put(folder, albumFolder);
//                                // LogUtil.e("folder", "初次创建, 全部文件夹");
//                            } else {
//
//                                // 1. 首先将图片, 插入全部文件夹
//                                String all = context.getResources().getString(R.string.comm_album_all);
//                                AlbumPhotoBean albumPhoto = new AlbumPhotoBean();
//                                albumPhoto.setPicture(name);
//                                albumPhoto.setWidth(width);
//                                albumPhoto.setHeight(height);
//                                albumPhoto.setSize(size);
//                                mFolderList.get(all).insertImageList(albumPhoto);
//                                // LogUtil.e("folder", "插入, 全部文件夹" + ", name = " + name);
//
//                                // 2. 获取图片所在的文件夹名字
//                                String folder = file.getParentFile().getName();
//                                // 文件夹存在
//                                if (mFolderList.containsKey(folder)) {
//                                    final AlbumFileBean albumFolder = mFolderList.get(folder);
//                                    albumFolder.insertImageList(albumPhoto);
//                                    //  LogUtil.e("folder", "插入, 文件夹存在" + ", name = " + name);
//                                }
//                                // 文件夹不存在
//                                else {
//                                    final AlbumFileBean albumFolder = new AlbumFileBean();
//                                    albumFolder.setFolderName(folder);
//                                    albumFolder.setFolderImage(name);
//                                    albumFolder.setSelect(false);
//                                    albumFolder.insertImageList(albumPhoto);
//                                    mFolderList.put(folder, albumFolder);
//                                    //   LogUtil.e("folder", "插入, 文件夹存在" + ", name = " + name);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            cursor.close();
//            e.onNext(mFolderList);
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(map -> {
//                            view.scannSucc(map);
//                            LogUtil.e("scanLocalPhoto", "分线程: 查找相册成功 ==> size = " + map.size());
//                        }, throwable -> {
//                            LogUtil.e("scanLocalPhoto", "分线程: 查找相册失败");
//                            LogUtil.e("scanLocalPhoto", throwable.getMessage(), throwable);
//                        },
//                        () -> {
//                            LogUtil.e("scanLocalPhoto", "分线程: 查找相册结束");
//                        },
//                        disposable -> {
//                            LogUtil.e("scanLocalPhoto", "分线程: 查找相册开始");
//                        });
//    }
//
//    @Override
//    public void recycler() {
//    }
//}
