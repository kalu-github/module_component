package lib.kalu.context;

/**
 * description: 常量
 * created by kalu on 2018/4/16 22:10
 */
public final class BaseConstant {

    public static final String MOB_ID = "9bc4887421774c49005892af6249e362";
    public static final String MOB_NAME = "25ac3f8903110";
    public static final String MOB_PHONE = "86";

    public static final String TYPE_PATIENT = "0"; //患者
    public static final String TYPE_DOCTOR = "1";  //医生
    public static final String SIZE_BANNER = "5";  //轮播图显示数量
    public static final String NEW_LINE = "\n";
    public static final String NULL_STR = "";
    public static final String UNDERLINE = "_";
    public static final String RUNG = "-";
    public static final String WELL = "#";
    public static final String COLON = ":";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String PERCENT = "%";
    public static final String FLOAT_ONE = "1.0";
    public static final String ZERO = "0";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";
    public static final String POST = "POST";

    // cache
    public static final String CACHE_ROOT = "quasar";
    public static final String CACHE_IMAGE = CACHE_ROOT + "/image";
    public static final String CACHE_HTTP = CACHE_ROOT + "/http";
    public static final String CACHE_UPDATA = CACHE_ROOT + "/apk";
    public static final String CACHE_USER = CACHE_ROOT + "/user";
    public static final String CACHE_WEB = CACHE_ROOT + "/web";
    public static final String CACHE_CAMERA = CACHE_ROOT + "/camera/";
    public static final String CACHE_COMPRESS = CACHE_ROOT + "/compress/";
    // type
    public static final String USER_PATIENT = "1";
    // platfrom
    public static final String PLATFROM = "1";
    // version
    public static final String VERSION = "12";
    // httpcode
    public static final String HTTP_CODE_LOGIN_FAILURE = "-00010001"; // 用户已经失效
    public static final String HTTP_CODE_LOGIN_SUCCESS = "00010001"; // 用户登录成功
    public static final String HTTP_CODE_LOGIN_AUDIT = "00010000"; // 用户登录成功, 处于审核状态
    public static final String HTTP_CODE_LOGIN_NOT_TRUE = "00010002"; // 用户审核失败, 资料不真实
    public static final String HTTP_CODE_LOGIN_INCOMPLETE = "00010003"; // 用户资料不全, 需要补充
    public static final String HTTP_CODE_LOGIN_FREEZE = "00010004"; // 用户账户被冻结

    /******************************************************************************************/

    public static final int PERMISSION_SD = 1001;
    public static final int PERMISSION_CAMERA = 1002;

    /******************************************************************************************/

    public static boolean IS_DEBUG = true;
    private static final String API_DEV = "http://is.snssdk.com/";
    private static final String API_NET = "http://is.snssdk.com/";
    private static final String SOCKET_DEV = "ws://192.168.1.200:8282/";
    private static final String SOCKET_NET = "ws://192.144.147.161:8282/";
    public static final String API_URL = false ? API_DEV : API_NET;
    public static final String SOCKET_URL = false ? SOCKET_DEV : SOCKET_NET;
}