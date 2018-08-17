package permission.kalu.core.support;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import permission.kalu.core.platform.PlatformManager;
import permission.kalu.core.wrapper.WrapperImp;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * description:权限检测
 * <p>
 * Android 7.0 - 24 - N
 * Android 6.0 - 23 - M
 * Android 5.1 - 22 - LOLLIPOP_MR1
 * Android 5.0 - 21 - LOLLIPOP
 * <p>
 * created by kalu on 2018/5/12 13:54
 */
public final class SupportCheck {

    private static final String TAG = "PlatformSupport";
    private static final String TAG_NUMBER = "1";
    private static boolean granted = false;

    public final static boolean isUnderBuild6_0(Context context) {
        return context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M;
    }

    public final static boolean isUnderAndroid6_0() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    public final static boolean isUnderBuild4_3(Context context) {
        return context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public final static boolean isUnderAndroid4_3() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    public final static String getSystemVersion() {
        return "系统版本：" + Build.VERSION.SDK_INT + "(" + Build.VERSION.RELEASE + ")";
    }

    public final static String getBuildVersion(Context context) {
        return "编译版本：" + context.getApplicationInfo().targetSdkVersion;
    }

    final static boolean isDenied(WrapperImp wrapper, String permissionName) {

        if (null == wrapper) return true;

        final Activity activity = wrapper.getActivity();
        if (null == activity) return true;

        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permissionName);
    }

    public static boolean isGranted(Activity activity, String permission) {
        try {
            switch (permission) {
                case Manifest.permission.READ_CONTACTS:
                    return checkReadContacts(activity);
                case Manifest.permission.WRITE_CONTACTS:
                    return checkWriteContacts(activity);
                case Manifest.permission.GET_ACCOUNTS:
                    return true;
                case Manifest.permission.READ_CALL_LOG:
                    return checkReadCallLog(activity);
                case Manifest.permission.READ_PHONE_STATE:
                    return checkReadPhoneState(activity);
                case Manifest.permission.CALL_PHONE:
                    return true;
                case Manifest.permission.WRITE_CALL_LOG:
                    return checkWriteCallLog(activity);
                case Manifest.permission.USE_SIP:
                    return true;
                case Manifest.permission.PROCESS_OUTGOING_CALLS:
                    return true;
                case Manifest.permission.ADD_VOICEMAIL:
                    return true;
                case Manifest.permission.READ_CALENDAR:
                    return checkReadCalendar(activity);
                case Manifest.permission.WRITE_CALENDAR:
                    return true;
                case Manifest.permission.BODY_SENSORS:
                    return checkBodySensors(activity);
                case Manifest.permission.CAMERA:
                    return true;
                case Manifest.permission.ACCESS_COARSE_LOCATION:
                case Manifest.permission.ACCESS_FINE_LOCATION:
                    return checkLocation(activity);
                case Manifest.permission.READ_EXTERNAL_STORAGE:
                    return checkReadStorage(activity);
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    return checkWriteStorage(activity);
                case Manifest.permission.RECORD_AUDIO:
                    return checkRecordAudio(activity);
                case Manifest.permission.READ_SMS:
                    return checkReadSms(activity);
                case Manifest.permission.SEND_SMS:
                case Manifest.permission.RECEIVE_WAP_PUSH:
                case Manifest.permission.RECEIVE_MMS:
                case Manifest.permission.RECEIVE_SMS:
                    return true;
                default:
                    return true;
            }
        } catch (Exception e) {
            Log.e("", "获取权限异常 ==>" + e.getMessage(), e);
            return false;
        }
    }

    private static boolean checkRecordAudio(Activity activity) throws Exception {
        SuppportAudioRecord recordManager = new SuppportAudioRecord();

        recordManager.startRecord(activity.getExternalFilesDir(Environment.DIRECTORY_RINGTONES) + "/" +
                TAG + ".3gp");
        recordManager.stopRecord();

        return recordManager.getSuccess();
    }

    private static boolean checkReadCalendar(Activity activity) {
        Cursor cursor = activity.getContentResolver().query(Uri.parse("content://com" +
                ".android.calendar/calendars"), null, null, null, null);
        if (cursor != null) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkWriteCallLog(Activity activity) {
        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues content = new ContentValues();
        content.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
        content.put(CallLog.Calls.NUMBER, TAG_NUMBER);
        content.put(CallLog.Calls.DATE, 20140808);
        content.put(CallLog.Calls.NEW, "0");
        contentResolver.insert(Uri.parse("content://call_log/calls"), content);

        contentResolver.delete(Uri.parse("content://call_log/calls"), "number = ?", new
                String[]{TAG_NUMBER});

        return true;
    }

    private static boolean checkReadSms(Activity activity) {
        Cursor cursor = activity.getContentResolver().query(Uri.parse("content://sms/"), null, null, null, null);
        if (null == cursor) return false;

        if (PlatformManager.isForceManufacturer()) {
            if (isNumberIndexInfoIsNull(cursor, cursor.getColumnIndex(Telephony.Sms.DATE))) {
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    private static boolean checkWriteStorage(Activity activity) {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath(), TAG);
        if (!file.exists()) {
            boolean newFile;
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return newFile;
        } else {
            return file.delete();
        }
    }

    private static boolean checkReadStorage(Activity activity) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).getPath());
        File[] files = file.listFiles();
        return files != null;
    }

    @SuppressLint("MissingPermission")
    private static boolean checkLocation(Activity activity) {
        granted = false;
        final LocationManager locationManager = (LocationManager) activity.getSystemService(Context
                .LOCATION_SERVICE);
        List<String> list = locationManager.getProviders(true);

        if (list.contains(LocationManager.GPS_PROVIDER)) {
            return true;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            return true;
        } else {
            if (!locationManager.isProviderEnabled("gps")) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, new
                        LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                locationManager.removeUpdates(this);
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {
                                locationManager.removeUpdates(this);
                                granted = true;
                            }

                            @Override
                            public void onProviderEnabled(String provider) {
                                locationManager.removeUpdates(this);
                            }

                            @Override
                            public void onProviderDisabled(String provider) {
                                locationManager.removeUpdates(this);
                            }
                        });
            }
            return granted;
        }
    }

    private static boolean checkBodySensors(Activity activity) {
        SensorManager sensorManager = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor((Sensor.TYPE_ACCELEROMETER));
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.unregisterListener(listener, sensor);

        return true;
    }

    @SuppressLint({"HardwareIds", "MissingPermission"})
    private static boolean checkReadPhoneState(Activity activity) {
        TelephonyManager service = (TelephonyManager) activity.getSystemService
                (TELEPHONY_SERVICE);
        if (PlatformManager.isMEIZU()) {
            return !TextUtils.isEmpty(service.getSubscriberId());
        } else if (PlatformManager.isXIAOMI() || PlatformManager.isOPPO()) {
            return !TextUtils.isEmpty(service.getDeviceId());
        } else {
            return !TextUtils.isEmpty(service.getDeviceId()) || !TextUtils.isEmpty(service
                    .getSubscriberId());
        }
    }

    private static boolean checkReadCallLog(Activity activity) {
        Cursor cursor = activity.getContentResolver().query(Uri.parse
                        ("content://call_log/calls"), null, null,
                null, null);
        if (cursor != null) {
            if (PlatformManager.isForceManufacturer()) {
                if (isNumberIndexInfoIsNull(cursor, cursor.getColumnIndex(CallLog.Calls.NUMBER))) {
                    cursor.close();
                    return false;
                }
            }
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkWriteContacts(Activity activity) {
        if (checkReadContacts(activity)) {
            // write some info
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = activity.getContentResolver();
            Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts
                    .CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);
            values.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds
                    .StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, TAG);
            values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, TAG_NUMBER);
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, values);

            // delete info
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = activity.getContentResolver();
            Cursor cursor = resolver.query(uri, new String[]{ContactsContract.Contacts.Data._ID},
                    "display_name=?", new String[]{TAG}, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);
                    resolver.delete(uri, "display_name=?", new String[]{TAG});
                    uri = Uri.parse("content://com.android.contacts/data");
                    resolver.delete(uri, "raw_contact_id=?", new String[]{id + ""});
                }
                cursor.close();
            }
            return true;
        } else {
            return false;
        }
    }

    private static boolean checkReadContacts(Activity activity) {

        final ContentResolver resolver = activity.getContentResolver();
        if (null == resolver) return false;

        final Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (null == cursor) return false;

        if (PlatformManager.isForceManufacturer()) {
            if (isNumberIndexInfoIsNull(cursor, cursor.getColumnIndex(ContactsContract.CommonDataKinds
                    .Phone.NUMBER))) {
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    private static boolean isNumberIndexInfoIsNull(Cursor cursor, int numberIndex) {
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                return TextUtils.isEmpty(cursor.getString(numberIndex));
            }
            return false;
        } else {
            return true;
        }
    }
}
