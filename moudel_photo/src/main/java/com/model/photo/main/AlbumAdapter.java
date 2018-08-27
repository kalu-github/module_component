//package com.model.photo.main;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.model.photo.R;
//import com.quasar.hdoctor.R;
//import com.quasar.hdoctor.bean.album.AlbumFileBean;
//import com.quasar.hdoctor.module.basic_album_file.AlbumFileFragment;
//import com.quasar.hdoctor.module.basic_album_photo.AlbumPhotoFragment;
//
//import java.util.Map;
//
//import lib.quasar.context.BaseApp;
//
///**
// * description: 选择图片
// * created by kalu on 2018/4/18 15:53
// */
//public class AlbumAdapter extends FragmentPagerAdapter {
//
//    private AlbumPhotoFragment mAlbumPhotoFragment = new AlbumPhotoFragment();
//    private AlbumFileFragment mAlbumFileFragment = new AlbumFileFragment();
//
//    public AlbumAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public int getCount() {
//        return 2;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return position == 0 ? mAlbumPhotoFragment : mAlbumFileFragment;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        Context context = BaseApp.getContext();
//        return context.getResources().getString(position == 0 ? R.string.comm_album_photo : R.string.comm_album_folder);
//    }
//
//    void setPhotoList(Map<String, AlbumFileBean> map) {
//        if (null == mAlbumPhotoFragment || null == mAlbumFileFragment) return;
//        final String folder = BaseApp.getResource().getString(R.string.comm_album_all);
//        mAlbumPhotoFragment.setPhotoList(map, folder);
//        mAlbumFileFragment.setPhotoList(map);
//    }
//
//    void changeFolder(Map<String, AlbumFileBean> map, String folder) {
//        if (null == mAlbumPhotoFragment) return;
//        mAlbumPhotoFragment.setPhotoList(map, folder);
//    }
//
//    void setResult() {
//        if (null == mAlbumPhotoFragment) return;
//        mAlbumPhotoFragment.setResult();
//    }
//}