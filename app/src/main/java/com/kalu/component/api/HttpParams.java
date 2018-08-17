package com.kalu.component.api;

import com.google.gson.Gson;

import java.util.HashMap;

import lib.demo.util.LogUtil;
import lib.kalu.context.BaseConstant;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public final class HttpParams {

    public static final String HttpCached = "xCached: 1"; // 需要缓存
    public static final String HttpLogin = "xLogin: 1"; // 需要登录公共参数
    public static final String HttpDownload = "xDownload: 1"; // 下载文件

    public static String DATA_TYPE_1_1 = "00010001";
    public static String DATA_TYPE_1_2 = "00010002"; // 医生信息
    public static String DATA_TYPE_1_7 = "00010007";
    public static String DATA_TYPE_1_4 = "00010004";
    public static String DATA_TYPE_13 = "00010003";
    public static String DATA_TYPE_1_5 = "00010005";
    public static String DATA_TYPE_16 = "00010006";
    public static String DATA_TYPE_1_11 = "00010011"; //修改密码
    public static String DATA_TYPE_1_12 = "00010012"; //重置密码
    public static String DATA_TYPE_1_10 = "00090001"; //握手
    public static String DATA_TYPE_1_13 = "00010013";
    public static String DATA_TYPE_1_17 = "00010017";
    public static String DATA_TYPE_1_18 = "00010018";

    public static String DATA_TYPE_9 = "00090002";//发消息
    public static String DATA_TYPE_9_5 = "00090005";//聊天历史记录


    public static String DATA_TYPE_47 = "00040007";
    public static String DATA_TYPE_4_8 = "00040008";
    public static String DATA_TYPE_4_2 = "00040002";
    public static String DATA_TYPE_4_3 = "00040003";
    public static String DATA_TYPE_412 = "00040012";
    public static String DATA_TYPE_414 = "00040014";
    public static String DATA_TYPE_4_10 = "00040010";

    public static String DATA_TYPE_3_1 = "00030001";
    public static String DATA_TYPE_3_2 = "00030002";
    public static String DATA_TYPE_3_3 = "00030003";
    public static String DATA_TYPE_3_4 = "00030004";
    public static String DATA_TYPE_3_5 = "00030005";
    public static String DATA_TYPE_3_6 = "00030006";
    public static String DATA_TYPE_3_7 = "00030007";
    public static String DATA_TYPE_3_8 = "00030008";
    public static String DATA_TYPE_3_9 = "00030009";

    public static String DATA_TYPE_5_1 = "00050001";
    public static String DATA_TYPE_5_2 = "00050002";
    public static String DATA_TYPE_5_3 = "00050003";
    public static String DATA_TYPE_5_4 = "00050004";
    public static String DATA_TYPE_5_5 = "00050005";
    public static String DATA_TYPE_5_6 = "00050006";

    public static String T_WARN_SIGN = "t_warn_sign";
    public static String HT_WARN_SIGN = "ht_warn_sign";
    public static String BS_WARN_SIGN = "bs_warn_sign";
    public static String BP_WARN_SIGN = "bp_warn_sign";
    public static String WARN_SIGN = "warn_sign";


    public static String INSPECTIONDATE = "inspectiondate";
    public static String INSPECTION_DATE = "inspection_date";
    public static String PICTURE_LIST = "picturelist";
    public static String JSON_INSPECTION_PICTURE = "json_inspection_picture";
    public static String JSON_INSPECTION_DATA = "json_inspection_data";
    public static String JSON_INSPECTION_DOCTORS = "json_inspection_doctors";
    public static String DELPICTURE = "delpicture";
    public static String NULL_STR = "";
    public static String ANDROID = "1";
    public static String PASSWORD = "passwd";
    public static String OLD_PASSWORD = "oldpasswd";
    public static String PHONE = "phone";
    public static String USER_CODE = "userCode";
    public static String USER_ID = "userid";
    public static String USER_TYPE = "user_type";
    public static String USER_PATIENT = "2";
    public static String VERSION = "version";
    public static String PLATFORM = "platform";
    public static String DATATYPE = "dataType";
    public static String CHECK_CODE = "checkCode";
    public static String MSG_DATAS = "msgDatas";
    public static String KEY_NAME = "keyName";
    public static String PATIENT_ID = "patientid";
    public static String PATIENT_NAME = "patient_name";
    public static String DOCTOR_ID = "doctorid";
    public static String RELATION_TYPE = "relationtype";
    public static String DATAS = "datas";
    public static String COMMA = ",";
    public static String MEDIA_TYPE = "application/json; charset=utf-8";

    public static String JSON_EVENT = "json_event";
    public static String JSON_HEARTRATE = "json_heartrate";
    public static String JSON_URINEVOLUME = "json_urinevolume";
    public static String JSON_BLOODSUGAR = "json_bloodsugar";
    public static String JSON_BLOODPRESSURE = "json_bloodpressure";
    public static String JSON_TEMPERATURE = "json_temperature";
    public static String JSON_OTHER = "json_other";

    public static String NUM = "num";
    public static String DAILY_ITEMNAME = "daily_itemname";
    public static String EVENT_NAME = "event_name";
    public static String DURATION_LENGTH = "duration_Length";
    public static String EDITOR_TYPE = "editor_type";
    public static String EVENT_TYPE = "event_type";
    public static String RECORD_DATE = "record_date";
    public static String EVENT_DATETIME = "event_datetime";
    public static String DESCRIPTION = "description";
    public static String BEGIN_DATETIME = "begin_datetime";
    public static String DATE = "date";
    public static String PRESCRIPTION_DATE = "prescription_date";
    public static String DOSE_DATE = "dose_date";
    public static String JSON_CONTENT = "json_content";

    public static String ICON = "icon";
    public static String REAL_NAME = "real_name";
    public static String SEX = "sex";
    public static String BIRTHDAY = "birthday";
    public static String BLOOD_TYPE = "blood_type";
    public static String SUPERVISOR_DOCTOR = "supervisor_doctor";
    public static String DONOR_RECEPTOR_TYPE = "donor_receptor_type";
    public static String TRANSPLANT_TYPE = "transplant_type";
    public static String SOURCE_TYPE = "source_type";
    public static String OPERATION_DATE = "operation_date";
    public static String HOSPITAL_NAME = "hospital_name";
    public static String ADDRESS = "address";
    public static String ICARD = "icard";

    public static String BEGIN_DATE = "begin_date";
    public static String END_DATE = "end_date";

    public static String ID = "id";
    public static String MEDICAL_HISTORY_DATE = "medical_history_date";
    public static String MEDICAL_TITLE = "medical_title";
    public static String JSON_PIC = "json_pic";
    public static String STATUS = "status";
}
