package com.model.photo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.model.photo.R;

import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

import static com.model.photo.main.MainPhotoActivity.PATH;

@Router(path = PATH)
public final class MainPhotoActivity extends AppCompatActivity {

    public static final String PATH = "/module_photo/photo";

    @Extra
    public String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moudel_layout_photo);

        RouterManager.getInstance().inject(this);

        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        final Intent intent = new Intent();
        intent.putExtra("respond", "moudel_photo_hello");
        setResult(1111, intent);
        super.onBackPressed();
    }

    public String make(String s) {
        return "返回值 ==>" + s;
    }
}
