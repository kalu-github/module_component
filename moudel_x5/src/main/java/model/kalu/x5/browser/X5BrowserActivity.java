package model.kalu.x5.browser;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import lib.kalu.core.frame.BaseActivity;
import model.kalu.x5.BuildConfig;
import model.kalu.x5.R;
import router.kalu.annotation.Extra;
import router.kalu.annotation.Router;
import router.kalu.core.RouterManager;

import static model.kalu.x5.browser.X5BrowserActivity.PATH;

@Router(path = PATH)
public final class X5BrowserActivity extends BaseActivity<X5BrowserPresenter> implements X5BrowserView {

    public static final String PATH = "/module_x5_browser/browser";
    public static final String URL = "mHtmlUrl";
    public static final String NAME = "mHtmlName";

    @Extra
    public String mHtmlUrl = "";
    @Extra
    public String mHtmlName = "";

    @Override
    public int initView() {
        RouterManager.getInstance().inject(this);
        return R.layout.layout_module_x5_browser;
    }

    @Override
    public void initDataLocal() {
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

    @Override
    public void onBackPressed() {

        final WebView mWebView = findViewById(R.id.module_x5_web);
        if (null != mWebView && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }

        super.onBackPressed();
    }
}
