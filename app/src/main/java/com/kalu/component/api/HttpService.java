package com.kalu.component.api;

import io.reactivex.Observable;
import lib.kalu.core.http.HttpResult;
import lib.kalu.core.http.interceptor.HttpInterceptor;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * description 网络接口
 * created by kalu on 2016/11/12 11:56
 */
public interface HttpService {

    @Headers({HttpInterceptor.NEED_CACHE, HttpInterceptor.NEED_LOGIN})
    @GET("api/news/feed/v51/?concern_id=12345678228934679042&refer=1&count=20&min_behot_time=1492080290&last_refresh_sub_entrance_interval=1492080292&loc_mode=6&loc_time=1492079387&latitude=28.687511709859&longitude=116.02067822305&city=南昌市&tt_from=pull&lac=31176&cid=123456789&cp=5183e0f15e6a4q1&iid=0123456789&device_id=12345678952&ac=wifi&channel=oppo-cpa&aid=13&app_name=news_article&version_code=607&version_name=6.0.7&device_platform=ios&ab_version=116031%2C112577%2C101786%2C117787%2C114037%2C101533%2C118766%2C110341%2C113607%2C118273%2C114108%2C106784%2C113608%2C101558%2C105475%2C118213%2C117714%2C105610%2C118751%2C104321%2C118607%2C117725%2C112578%2C115570%2C118602%2C118850%2C115776%2C116615%2C118660%2C31650%2C118530%2C118976%2C118216%2C114338%2C118846&ab_client=a1%2Cc4%2Ce1%2Cf2%2Cg2%2Cf7&ab_group=94563%2C102749&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=XIAOMI&device_brand=Google&language=zh&os_api=23&os_version=6.0&openudid=123456789d36d6z6&manifest_version_code=607&resolution=1080*1821&dpi=440&update_version_code=6075&_rticket=123456789123")
    Observable<HttpResult<Object>> test();
}