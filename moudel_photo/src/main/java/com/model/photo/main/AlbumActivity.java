//package com.model.photo.main;
//
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//
//import com.model.photo.R;
//
//import lib.kalu.core.frame.BaseActivity;
//import router.kalu.annotation.Router;
//
//import static com.model.photo.main.MainPhotoActivity.PATH;
//
//@Router(path = PATH)
//public class AlbumActivity extends BaseActivity<AlbumPresenter> implements AlbumView {
//
//    public static final int RESULT_CODE = 1002;
//    public static final int RESULT_CODE_OPEN_CAMERA = 1003;
//
//    public static final String PATH = "/module_photo/photo";
//
//    public final static String LIST = "LIST";
//    public final static String CAMERA = "CAMERA";
//
//    @Override
//    public int initView() {
//        return R.layout.moudel_layout_photo;
//    }
//
//    @Override
//    public void initDataLocal() {
//
//        findViewById(R.id.activity_comm_album_back).setOnClickListener(view -> onBackPressed());
//
//        findViewById(R.id.activity_comm_album_ok).setOnClickListener(view -> {
//            setResult();
//            onBackPressed();
//        });
//
//        AlbumPresenter presenter = getPresenter();
//        if (null == presenter) return;
//        presenter.initAdapter(this, getSupportFragmentManager());
//    }
//
//    @Override
//    public void initDataNet() {
//    }
//
//    @Override
//    public void setResult() {
//
//        ViewPager pager = findViewById(R.id.activity_comm_album_pager);
//        if (null == pager) return;
//
//        PagerAdapter pagerAdapter = pager.getAdapter();
//        if (null == pagerAdapter || !(pagerAdapter instanceof AlbumAdapter)) return;
//
//        AlbumAdapter adapter = (AlbumAdapter) pagerAdapter;
//        adapter.setResult();
//    }
//
//    @Override
//    public void scannSucc(Map<String, AlbumFileBean> map) {
//
//        ViewPager pager = findViewById(R.id.activity_comm_album_pager);
//        if (null == pager) return;
//
//        PagerAdapter pagerAdapter = pager.getAdapter();
//        if (null == pagerAdapter || !(pagerAdapter instanceof AlbumAdapter)) return;
//
//        AlbumAdapter adapter = (AlbumAdapter) pagerAdapter;
//        adapter.setPhotoList(map);
//    }
//
//    @Override
//    public void setAlbumPager(final AlbumAdapter adapter) {
//
//        ViewPager pager = findViewById(R.id.activity_comm_album_pager);
//        if (null != pager) {
//            pager.setOffscreenPageLimit(2);
//            pager.setAdapter(adapter);
//
//            AlbumPresenter presenter = getPresenter();
//            if (null == presenter) return;
//
//            ArrayList<AlbumPhotoBean> mData = (ArrayList<AlbumPhotoBean>) getIntent().getSerializableExtra(LIST);
//            presenter.scanLocalPhoto(getApplicationContext(), this, mData);
//        }
//
//        TextTabLayout menu = findViewById(R.id.activity_comm_album_menu);
//        if (null != menu) {
//            menu.setViewPager(pager);
//        }
//    }
//
//    @Override
//    public void changeFolder(String folder) {
//
//        ViewPager pager = findViewById(R.id.activity_comm_album_pager);
//        if (null == pager) return;
//
//        PagerAdapter pagerAdapter = pager.getAdapter();
//        if (null == pagerAdapter || !(pagerAdapter instanceof AlbumAdapter)) return;
//
//        AlbumAdapter adapter = (AlbumAdapter) pagerAdapter;
//        pager.setCurrentItem(0);
//        adapter.changeFolder(getPresenter().getAlbumFolderList(), folder);
//    }
//}