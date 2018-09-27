package com.kalu.component.api;

import java.util.concurrent.TimeUnit;

import lib.kalu.context.BaseConstant;
import lib.kalu.core.http.BaseClient;
import lib.kalu.core.http.interceptor.HttpInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * description: Http
 * created by kalu on 2017/5/15 10:34
 */
public final class HttpClient extends BaseClient {

    private volatile OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(new HttpInterceptor())
            .proxySelector(new ProxySelector() {
                @Override
                public List<Proxy> select(URI uri) {
                    return Collections.singletonList(Proxy.NO_PROXY);
                }

                @Override
                public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                }
            })
            .build();

    private volatile Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseConstant.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    private final <T> T getApiService(Class<T> apiService) {
        return retrofit.create(apiService);
    }

    public final HttpService getHttpService() {
        return getApiService(HttpService.class);
    }

    /**********************************************************************************************/

    private HttpClient() {
    }

    public final static HttpClient getInstance() {
        return SingleHolder.single;
    }

    private final static class SingleHolder {
        private final static HttpClient single = new HttpClient();
    }

    /**********************************************************************************************/
}