package com.ptit.bb.timgiasu.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.location.Location;

import java.util.List;

public class AppUtils {

    public static String[] citiesVN() {
        return new String[]{"An Giang",
                "Bà Rịa - Vũng Tàu",
                "Bắc Giang",
                "Bắc Kạn",
                "Bạc Liêu",
                "Bắc Ninh",
                "Bến Tre",
                "Bình Định",
                "Bình Dương",
                "Bình Phước",
                "Bình Thuận",
                "Cà Mau",
                "Cao Bằng",
                "Đắk Lắk",
                "Đắk Nông",
                "Điện Biên",
                "Đồng Nai",
                "Đồng Tháp",
                "Gia Lai",
                "Hà Giang",
                "Hà Nam",
                "Hà Tĩnh",
                "Hải Dương",
                "Hậu Giang",
                "Hòa Bình",
                "Hưng Yên",
                "Khánh Hòa",
                "Kiên Giang",
                "Kon Tum",
                "Lai Châu",
                "Lâm Đồng",
                "Lạng Sơn",
                "Lào Cai",
                "Long An",
                "Nam Định",
                "Nghệ An",
                "Ninh Bình",
                "Ninh Thuận",
                "Phú Thọ",
                "Quảng Bình",
                "Quảng Nam",
                "Quảng Ngãi",
                "Quảng Ninh",
                "Quảng Trị",
                "Sóc Trăng",
                "Sơn La",
                "Tây Ninh",
                "Thái Bình",
                "Thái Nguyên",
                "Thanh Hóa",
                "Thừa Thiên Huế",
                "Tiền Giang",
                "Trà Vinh",
                "Tuyên Quang",
                "Vĩnh Long",
                "Vĩnh Phúc",
                "Yên Bái",
                "Phú Yên",
                "Cần Thơ",
                "Đà Nẵng",
                "Hải Phòng",
                "Hà Nội",
                "TP HCM"};
    }

    public static String[] classes() {
        return new String[]{"Mầm non", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "Đại học"};
    }

    public static String[] subjects() {
        return new String[]{"Toán", "Lý", "Hóa", "Sinh", "Văn", "T.Anh", "T.Pháp", "T.Nhật", "T.Trung", "T.Hàn", "T.Đức", "Lập trình", "Mỹ thuật", "Tin học văn phòng"};
    }

    public static final String DANG_TUYEN = "Đăng tuyển";

    public static boolean isForeGround(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static double distance(double lat1, double lon1, double lat2,
                                  double lon2) {

        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        double distance = loc1.distanceTo(loc2) / 1000;
        return (double) Math.round(distance * 10) / 10;
    }
}
