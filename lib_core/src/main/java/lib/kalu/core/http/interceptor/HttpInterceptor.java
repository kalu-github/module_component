package lib.kalu.core.http.interceptor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lib.demo.util.LogUtil;
import lib.kalu.context.BaseConstant;
import lib.kalu.core.BuildConfig;
import lib.kalu.core.http.HttpResult;
import lib.kalu.db.ObjectBoxManager;
import lib.kalu.db.model.Http;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * description: okhttp拦截器(缓存数据统一处理)
 * created by kalu on 2017/5/15 10:34
 */
public final class HttpInterceptor implements Interceptor {

    private final String TAG = HttpInterceptor.class.getName();

    public static final String OK = "1";
    public static final String NEED_CACHE = "CACHE: 1";
    public static final String NEED_LOGIN = "LOGIN: 1";
    public static final String DOWNLOAD = "DOWNLOAD";

    private final String CACHE = "CACHE";
    private final String LOGIN = "LOGIN";
    private final String APPLICATION_JSON = "application/json";
    private final String TIMESTAMP = "timestamp";
    private final String POST = "POST";
    private final String ANDROID = "ANDROID";
    private final String USER_AGENT = "User-Agent";
    private final String CACHE_CONTROL = "Cache-Control";
    private final String NO_CACHE = "no-cache";
    private final String PLATFORM = "platform";
    private final String VERSION = "android+" + android.os.Build.VERSION.RELEASE + "+" + android.os.Build.BRAND + "+" + android.os.Build.MODEL + "+" + String.valueOf(BuildConfig.VERSION_CODE);

    /**********************************************************************************************/

    @Override
    public Response intercept(Chain chain) {

        final Request request = chain.request();
        final String requestUrl = request.url().toString();
        final Request.Builder requestBuilder = request.newBuilder();

        if (OK.equals(request.header(DOWNLOAD))) {

            LogUtil.e(TAG, "\nintercept[下载apk] ==> requestUrl = " + requestUrl);

            try {
                final Response proceed = chain.proceed(requestBuilder.build());
                return proceed.newBuilder().body(proceed.body()).build();
            } catch (Exception e) {

                final HttpResult<String> result = new HttpResult<>();
                result.setShow_et_status("0");
                result.setMessage("网络请求发生异常");
                result.setData("网络请求发生异常");

                final String json = new GsonBuilder()
                        .setLenient()// json宽松
                        .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                        .serializeNulls() //智能null
                        .setPrettyPrinting()// 调整格式 ，使对齐
                        .disableHtmlEscaping() //默认是GSON把HTML 转义的
                        //.setExclusionStrategies(excludeStrings) //自定义排除转json的字段或者类名
                        .excludeFieldsWithoutExposeAnnotation()//启用	@Expose
                        .create().toJson(result);

                return new Response.Builder()
                        .code(200)
                        .message("网络请求发生异常")
                        .protocol(Protocol.HTTP_1_1)
                        .request(request)
                        .body(ResponseBody.create(MediaType.parse(APPLICATION_JSON), json))
                        .build();
            }
        } else {

            requestBuilder.addHeader(PLATFORM, VERSION);
            requestBuilder.addHeader(USER_AGENT, ANDROID);
            requestBuilder.addHeader(CACHE_CONTROL, NO_CACHE);

            final boolean needCache = OK.equals(request.header(CACHE));
            final boolean needLogin = OK.equals(request.header(LOGIN));

            if (BuildConfig.isLog) {

                LogUtil.e(TAG, "Http request[method] ==> method = " + request.method());
                LogUtil.e(TAG, "Http request[url] ==> url = " + requestUrl);
                LogUtil.e(TAG, "Http request[config] ==> cache = " + needCache + ", login = " + needLogin);

                if (POST.equalsIgnoreCase(request.method())) {
                    final RequestBody requestBody = request.body();
                    if (requestBody instanceof FormBody) {
                        final FormBody formBody = (FormBody) requestBody;
                        for (int i = 0; i < formBody.size(); i++) {
                            LogUtil.e(TAG, "Http request[params] ==> " + formBody.name(i) + " = " + formBody.value(i));
                        }
                    }
                }
            }

            // 需要登录, 添加公共参数
            if (needLogin) {

                final String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                final HttpUrl httpUrl = request.url().newBuilder()
                        .addQueryParameter(TIMESTAMP, currentTimeMillis)
                        .build();

                if (BuildConfig.isLog) {
                    LogUtil.e(TAG, "Http request[login] ==> " + TIMESTAMP + " = " + currentTimeMillis);
                }

                requestBuilder.url(httpUrl);
            }

            try {

                final Response response = chain.proceed(requestBuilder.build());
                final ResponseBody responseBody = response.body();
                String bodyString = responseBody.string();

                if (needCache) {
                    if (response.isSuccessful()) {
                        return createSuccResponse(response, responseBody, bodyString, requestUrl);
                    } else {
                        return createFailResponse(request, response, responseBody, requestUrl);
                    }
                } else {
                    LogUtil.e(TAG, "\n====> Net[网络正常, 不要缓存], responseCode = " + response.code());

                    if (BuildConfig.isLog) {
                        LogUtil.e(TAG, "Http response[网络] ==> " + bodyString);
                    }

                    return response.newBuilder()
                            .body(ResponseBody.create(responseBody.contentType(), bodyString))
                            .build();
                }
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e);
                return createExcepiton(request, requestUrl, needCache);
            }
        }
    }

    /**********************************************************************************************/

    private final Response createExcepiton(Request request, String requestUrl, boolean needCache) {

        if (needCache) {
            // 请求失败，读缓存，并重新构建response
            final Http cache = ObjectBoxManager.getInstance().query(Http.class, requestUrl);
            if (null != cache && !TextUtils.isEmpty(cache.getJson())) {

                String json = cache.getJson();
                LogUtil.e(TAG, "\n====> Net[请求异常, 缓存存在, JSON存在]");

                if (BuildConfig.isLog) {
                    LogUtil.e(TAG, "Http response[cache] ==> " + json);
                }

                return new Response.Builder()
                        .code(200)
                        .message("网络请求发生异常")
                        .protocol(Protocol.HTTP_1_1)
                        .request(request)
                        .body(ResponseBody.create(MediaType.parse(APPLICATION_JSON), json))
                        .build();
            } else {
                LogUtil.e(TAG, "\n====> Net[请求异常, 缓存为空]");
            }
        } else {
            LogUtil.e(TAG, "\n====> Net[网络异常, 不要缓存]");
        }

        final HttpResult<String> result = new HttpResult<>();
        result.setShow_et_status("0");
        result.setMessage("网络请求发生异常");
        result.setData("网络请求发生异常");

        final String json = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调整格式 ，使对齐
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                //.setExclusionStrategies(excludeStrings) //自定义排除转json的字段或者类名
                .excludeFieldsWithoutExposeAnnotation()//启用	@Expose
                .create().toJson(result);

        if (BuildConfig.isLog) {
            LogUtil.e(TAG, "Http response[custom] ==> " + json);
        }

        return new Response.Builder()
                .code(200)
                .message("网络请求发生异常")
                .protocol(Protocol.HTTP_1_1)
                .request(request)
                .body(ResponseBody.create(MediaType.parse(APPLICATION_JSON), json))
                .build();
    }

    private final Response createSuccResponse(Response response, ResponseBody responseBody, String bodyString, String requestUrl) {

        final HttpResult result = new Gson().fromJson(bodyString, HttpResult.class);
        final Object datas = result.getData();

        // 请求成功，data不为null，写缓存
        if (null != datas || !TextUtils.isEmpty(datas.toString())) {

            // 1. 读取最近保存的缓存
            final Http cache = ObjectBoxManager.getInstance().query(Http.class, requestUrl);
            if (null != cache) {
                if (null != cache && !cache.getJson().equals(bodyString)) {
                    LogUtil.e(TAG, "\n====> Net[网络正常, DATA存在,  缓存存在, JSON更新]");
                    cache.setJson(bodyString);
                    cache.setUrl(requestUrl);
                    ObjectBoxManager.getInstance().insert(Http.class, cache);
                } else {
                    LogUtil.e(TAG, "\n====> Net[网络正常, DATA存在,  缓存存在, JSON不变]");
                }
            } else {
                LogUtil.e(TAG, "\n====> Net[网络正常, DATA存在, 缓存为空, JSON保存]");
                final Http model = new Http();
                model.setUrl(requestUrl);
                model.setJson(bodyString);
                ObjectBoxManager.getInstance().insert(Http.class, model);
            }

            if (BuildConfig.isLog) {
                LogUtil.e(TAG, "Http response[web] ==> " + bodyString);
            }
        }
        // 请求成功，data为null，读缓存，并重新构建response
        else {

            // 请求成功，data为null，读缓存，并重新构建response
            final Http cache = ObjectBoxManager.getInstance().query(Http.class, requestUrl);
            if (null != cache && !TextUtils.isEmpty(cache.getJson())) {

                LogUtil.e(TAG, "\n====> Net[网络正常, DATA为空, 缓存存在, 构建response]");
                bodyString = cache.getJson();

                if (BaseConstant.IS_DEBUG) {
                    LogUtil.e(TAG, "Http response[cache] ==> " + bodyString);
                }
            } else {
                LogUtil.e(TAG, "\n====> Net[网络正常, DATA为空, 缓存为空]");
            }
        }

        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), bodyString))
                .build();
    }

    private final Response createFailResponse(Request request, Response response, ResponseBody responseBody, String requestUrl) {

        final Http cache = ObjectBoxManager.getInstance().query(Http.class, requestUrl);
        if (null != cache && !TextUtils.isEmpty(cache.getJson())) {

            LogUtil.e(TAG, "\n====> Net[请求失败, 缓存存在, 重构缓存返回值]");
            final String json = cache.getJson();

            if (BaseConstant.IS_DEBUG) {
                LogUtil.e(TAG, "Http response[cache] ==> " + json);
            }

            return response.newBuilder().
                    code(200)
                    .protocol(Protocol.HTTP_1_1)
                    .request(request)
                    .body(ResponseBody.create(responseBody.contentType(), json))
                    .build();
        } else {
            LogUtil.e(TAG, "\n====> Net[请求失败, DATA为空, 缓存为空]");

            final HttpResult<String> result = new HttpResult<>();
            result.setShow_et_status("0");
            result.setMessage("网络请求发生错误");
            result.setData("网络请求发生错误");

            final String json = new GsonBuilder()
                    .setLenient()// json宽松
                    .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                    .serializeNulls() //智能null
                    .setPrettyPrinting()// 调整格式 ，使对齐
                    .disableHtmlEscaping() //默认是GSON把HTML 转义的
                    //.setExclusionStrategies(excludeStrings) //自定义排除转json的字段或者类名
                    .excludeFieldsWithoutExposeAnnotation()//启用	@Expose
                    .create().toJson(result);

            if (BuildConfig.isLog) {
                LogUtil.e(TAG, "Http response[custom] ==> " + json);
            }

            return new Response.Builder()
                    .code(200)
                    .message("网络请求发生异常")
                    .protocol(Protocol.HTTP_1_1)
                    .request(request)
                    .body(ResponseBody.create(MediaType.parse(APPLICATION_JSON), json))
                    .build();
        }
    }
}
