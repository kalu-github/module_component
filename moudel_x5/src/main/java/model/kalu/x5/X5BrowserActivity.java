package model.kalu.x5;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

import static model.kalu.x5.X5BrowserActivity.PATH;

@Router(path = PATH)
public final class X5BrowserActivity extends FragmentActivity {

    public static final String PATH = "/module_x5_browser/browser";
    public static final String URL = "mHtmlUrl";
    public static final String NAME = "mHtmlName";

    @Extra
    public String mHtmlUrl = "";
    @Extra
    public String mHtmlName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterManager.getInstance().inject(this);
        setContentView(R.layout.layout_module_x5_browser);

        if (TextUtils.isEmpty(mHtmlUrl)) {
            if (BuildConfig.isModule) {
                Toast.makeText(getApplicationContext(), "网址不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                mHtmlUrl = "http://www.baidu.com";
            }
        }

        final WebView mWebView = findViewById(R.id.module_x5_web);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.loadUrl(mHtmlUrl);
    }
}
