package lib.kalu.core.frame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import lib.demo.util.CalendarUtil;
import lib.demo.util.LogUtil;
import lib.kalu.context.BaseApp;
import lib.kalu.context.BaseConstant;
import lib.kalu.core.R;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * description: 解除订阅回调
 * created by kalu on 17-10-16 下午5:07
 */
public interface BasePresenter {

    BaseModel mBaseModel = new BaseModel();

    String NULL_STR = "";
    String ANDROID = "1";
    String USER_PATIENT = "1";
    String USER_CODE = "userCode";
    String USER_ID = "userid";
    String USER_TYPE = "user_type";
    String VERSION = "version";
    String PLATFORM = "platform";
    String DATATYPE = "dataType";
    String CHECK_CODE = "checkCode";
    String MSG_DATAS = "msgDatas";
    String KEY_NAME = "keyName";
    String PATIENT_ID = "patientid";
    String COMMA = ",";
    String MEDIA_TYPE = "application/json; charset=utf-8";
    String RECORD_DATE = "record_date";

    String BEGIN_DATE = "begin_date";
    String END_DATE = "end_date";
    String NUMBER = "number";
    String TYPE = "type";


    String PRESCRIPTION_DATE = "prescription_date";
    String JSON_CONTENT = "json_content";
    String OLD_DATA = "old_data";
    String NEW_DATA = "new_data";
    String JSON_RECORD = "json_record";
    String NULL = "";

    String DOCTOR_ID = "doctorid";
    String DOCTOR_NAME = "doctor_name";
    String SUBMIT_DATETIME = "submit_datetime";

//    default HashMap<String, Object> createDatas(String dataType) {
//
//        final HashMap<String, Object> params1 = new HashMap<>();
//        params1.clear();
//        params1.put(VERSION, BaseConstant.VERSION);
//        params1.put(PLATFORM, BaseConstant.PLATFROM);
//        params1.put(DATATYPE, dataType);
//        params1.put(CHECK_CODE, NULL_STR);
//        return params1;
//    }
//
//    default RequestBody createParams(Map<String, Object> map, String dataType) {
//
//        final StringBuilder keyName = new StringBuilder();
//        final HashMap<String, Object> map2 = new HashMap<>();
//
//        final Long doctorId = getDoctorId();
//        if (null != doctorId && !doctorId.equals(0L)) {
//            map2.put(DOCTOR_ID, doctorId);
//            keyName.append(DOCTOR_ID);
//        }
//
//        final String doctorName = getDoctorName();
//        if (!TextUtils.isEmpty(doctorName)) {
//            if (!map2.isEmpty()) {
//                keyName.append(COMMA);
//            }
//            map2.put(DOCTOR_NAME, doctorName);
//            keyName.append(DOCTOR_NAME);
//        }
//
//        if (null != map) {
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                if (!map2.isEmpty()) {
//                    keyName.append(COMMA);
//                }
//                keyName.append(entry.getKey());
//                map2.put(entry.getKey(), entry.getValue());
//            }
//        }
//
//        map2.put(KEY_NAME, keyName.toString());
//
//        final HashMap<String, Object> map1 = createDatas(dataType);
//        map1.put(MSG_DATAS, map2);
//
//        final Gson mGson = new Gson();
//        final String params = mGson.toJson(map1);
//        LogUtil.e("BasePresenter", "createParams ==> " + params);
//
//        return RequestBody.create(MediaType.parse(MEDIA_TYPE), params);
//    }
//
//    default <T> RequestBody createParams(T model, String dataType) {
//
//        final HashMap<String, Object> map1 = createDatas(dataType);
//        map1.put(MSG_DATAS, model);
//
//        final Gson mGson = new Gson();
//        final String params = mGson.toJson(map1);
//        LogUtil.e("BasePresenter", "createParams ==> " + params);
//
//        return RequestBody.create(MediaType.parse(MEDIA_TYPE), params);
//    }

    default void request(Context context, Observable observable, OnModelChangeListener listener) {
        mBaseModel.request(context, observable, listener);
    }

    default <T> void request(Context context, Observable observable, OnModelAcceptChangeListener<T> listener) {
        mBaseModel.request(context, observable, listener);
    }

//
//    default String getDate(CharSequence yearMonthDayStr) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_date_str, yearMonthDayStr);
//    }
//
//    default String getDate(CharSequence yearStr, CharSequence monthStr, CharSequence dayStr) {
//
//        return BaseApp.getResource().getString(R.string.http_date, yearStr, monthStr, dayStr);
//    }
//
//
//    default String getDate(String str1, String str2) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_record_date_time, str1, str2);
//    }
//
//    default String getDateLittle() {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_date_little,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays());
//    }
//
//    default String getMinute() {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_dates,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays(),
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes());
//    }
//
//    default String getDailyMinute(String yearmonthday) {
//
//        return BaseApp.getContext().getResources().getString(R.string.date_daily,
//                yearmonthday,
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes());
//    }
//
//    default String getMinute3(String yearmonthday) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_date_minute_yearmonthday,
//                yearmonthday,
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes());
//    }
//
//    default String getDateTemperature(String temperature1, String temperature2) {
//
//        return BaseApp.getContext().getResources().getString(R.string.date_temperature,
//                temperature1, temperature2, CalendarUtil.getSeconds());
//    }
//
//    default String getMinute(String hourStr, String minuteStr) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_dates,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays(),
//                hourStr, minuteStr);
//    }
//
//    default String getDayMinute(String timeStr) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_datem,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays(),
//                timeStr);
//    }
//
//
//    default String getMonthLittle() {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_month,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths());
//    }
//
//    default String getSecond() {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_second,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays(),
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes(),
//                CalendarUtil.getSeconds());
//    }
//
//    default String getSecondYearMonthDay(String yearmonthday) {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_second_yearmonthday,
//                yearmonthday,
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes(),
//                CalendarUtil.getSeconds());
//    }
//
//    default String getMedicineDate(String hourMinureStr) {
//        return BaseApp.getContext().getResources().getString(R.string.http_time,
//                CalendarUtil.getYears(),
//                CalendarUtil.getMonths(),
//                CalendarUtil.getDays(),
//                hourMinureStr);
//    }
//
//    default String getDateEvent(String yearmonthday) {
//        return BaseApp.getContext().getResources().getString(R.string.date_event,
//                yearmonthday,
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes());
//    }
//
//    default String getHms(String hm) {
//        return BaseApp.getContext().getResources().getString(R.string.http_second_little,
//                hm,
//                "00");
//    }
//
//    default String getMinuteLittle() {
//
//        return BaseApp.getContext().getResources().getString(R.string.http_second_little,
//                CalendarUtil.getHours(),
//                CalendarUtil.getMinutes());
//    }
//
//    default Long getDoctorId() {
//        try {
//            final User doctorModel = DBManager.getInstance().syncGetUserModel();
//            return doctorModel.getDoctorid();
//        } catch (Exception e) {
//            Log.e("BasePresenter", "getPatientId ==> " + e.getMessage(), e);
//            return 0L;
//        }
//    }
//
//    default String getDoctorName() {
//        try {
//            final User doctorModel = DBManager.getInstance().syncGetUserModel();
//            return doctorModel.getReal_name();
//        } catch (Exception e) {
//            Log.e("BasePresenter", "getPatientId ==> " + e.getMessage(), e);
//            return "";
//        }
//    }
//
//    default String getClientId() {
//
//        try {
//            final User doctorModel = DBManager.getInstance().syncGetUserModel();
//            return doctorModel.getClientid();
//        } catch (Exception e) {
//            Log.e("BasePresenter", "getClientId ==> " + e.getMessage(), e);
//            return "";
//        }
//    }
//
//    default String getHospital() {
//
//        try {
//            final User doctorModel = DBManager.getInstance().syncGetUserModel();
//            return doctorModel.getHospital_name();
//        } catch (Exception e) {
//            Log.e("BasePresenter", "getHospitalName ==> " + e.getMessage(), e);
//            return "";
//        }
//    }
//
//    default String getCalendar() {
//        return CalendarUtil.getYears() + "-" + CalendarUtil.getMonths() + "-" + CalendarUtil.getDays();
//    }
//
//    default Boolean needGuide() {
//
//        try {
//
//            final User user = DBManager.getInstance().syncGetUserModel();
//            return user.needGuide();
//
//        } catch (Exception e) {
//            Log.e("BasePresenter", "needGuide ==> " + e.getMessage(), e);
//            return true;
//        }
//    }
//
//    default Boolean needLogin() {
//
//        try {
//
//            final User user = DBManager.getInstance().syncGetUserModel();
//            return user.needLogin();
//
//        } catch (Exception e) {
//            Log.e("BasePresenter", "needGuide ==> " + e.getMessage(), e);
//            return true;
//        }
//    }
//
//    default <T extends View> T getHead(RecyclerView recycler, int viewId) {
//
//        if (null == recycler)
//            return null;
//
//        final RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerAdapter = recycler.getAdapter();
//        if (null == recyclerAdapter || !(recyclerAdapter instanceof BaseCommonAdapter))
//            return null;
//
//        final LinearLayout head = ((BaseCommonAdapter) recyclerAdapter).getHead();
//        if (null == head || !(head instanceof LinearLayout))
//            return null;
//
//        return ((T) head.findViewById(viewId));
//    }
//
//    default <T extends View> T getFoot(RecyclerView recycler, int viewId) {
//
//        if (null == recycler)
//            return null;
//
//        final RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerAdapter = recycler.getAdapter();
//        if (null == recyclerAdapter || !(recyclerAdapter instanceof BaseCommonAdapter))
//            return null;
//
//        final LinearLayout foot = ((BaseCommonAdapter) recyclerAdapter).getFoot();
//        if (null == foot || !(foot instanceof LinearLayout))
//            return null;
//
//        return ((T) foot.findViewById(viewId));
//    }

    void recycler();
}