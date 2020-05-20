package com.vn.myhome.config;

/**
 * Created by: Neo Company.
 * Developer: HuyNQ2
 * Date: 22-April-2019
 * Time: 10:36
 * Version: 1.0
 */
public class Constants {
    public class RequestCode {
        public static final int GET_CITY = 1000;
        public static final int START_REGISTER = 1001;
        public static final int GET_ADD_ORDER = 1002;
        public static final int GET_LIST_CTV = 1003;
        public static final int GET_START_CART = 1004;
        public static final int GET_MY_HOME = 1005;
        public static final int GET_TOWER = 1006;
        public static final int GET_BOOKING = 1007;
        public static final int GET_CONTACT = 1008;
        public static final int GET_NOTIFY = 1009;
        public static final int PERMISSION_CALL_PHONE = 1010;
        public static final int START_REGISTER_ADMIN = 1010;
        public static final int GET_LIST_MYHOME = 1011;
        public static final int GET_LIST_ROUTE = 1012;
        public static final int GET_LIST_CAR = 1013;
    }

    public class UserType {
        public static final String ADMIN = "0";
        public static final String CHUNHA = "1";
        public static final String CTV = "2";
        public static final String DICHVU = "4";
        public static final String GOLD = "3";
    }
    public class EventBus {
        public static final String TAB_LICH = "TAB_LICH";
        public static final String RELOAD_NOTIFY = "RELOAD_NOTIFY";
        public static final String KEY_SEND_NOTIFY = "KEY_SEND_NOTIFY";
        public static final String KEY_UPDATE_MYHOME = "KEY_UPDATE_MYHOME";
        public static final String KEY_UPDATE_API_LIST_MYHOME = "KEY_UPDATE_API_LIST_MYHOME";
    }
    public class STATE_BOOKING_SERVICE{
        public static final String CHO_XAC_NHAN = "0";
        public static final String CHECK_IN = "1";
        public static final String CHECK_OUT = "2";
        public static final String CHECK_DANG_DON = "3";
        public static final String CHECK_DA_HOANTHANH = "4";
    }

    public static final String KEY_TOKEN = "KEY_TOKEN";
    public static final String KEY_SAVE_IS_LOGIN = "KEY_SAVE_IS_LOGIN";
    public static final String KEY_SAVE_USERNAME = "KEY_SAVE_USERNAME";
    public static final String KEY_SAVE_PASSWORD = "KEY_SAVE_PASSWORD";
    public static final String KEY_SAVE_OBJECT_LOGIN = "KEY_SAVE_OBJECT_LOGIN";
    public static final String KEY_SEND_LOGIN_TO_MAIN = "KEY_SEND_LOGIN_TO_MAIN";
    public static final String KEY_SEND_ROOM_DETAIL = "KEY_SEND_ROOM_DETAIL";
    public static final String KEY_SEND_MYHOME = "KEY_SEND_MYHOME";
    public static final String KEY_SAVE_AMENITIES = "KEY_SAVE_AMENITIES";
    public static final String KEY_SEND_ROOM_BOOKING = "KEY_SEND_ROOM_BOOKING";
    public static final String KEY_SEND_STARTDAY_BOOKING = "KEY_SEND_STARTDAY_BOOKING";
    public static final String KEY_SEND_ENDDAY_BOOKING = "KEY_SEND_ENDDAY_BOOKING";
    public static final String KEY_SEND_INFO_USERID = "KEY_SEND_INFO_USERID";
    public static final String KEY_SEND_INFO_USERID_TITLE = "KEY_SEND_INFO_USERID_TITLE";
    public static final String KEY_SEND_PRICE_THANHTOAN = "KEY_SEND_PRICE_THANHTOAN";
    public static final String KEY_SEND_CONTENT_THANHTOAN = "KEY_SEND_CONTENT_THANHTOAN";
    public static final String KEY_SEND_NOTIFYCATION = "KEY_SEND_NOTIFYCATION";
    public static final String KEY_SEND_CONTACT_DETAIL = "KEY_SEND_CONTACT_DETAIL";
    public static final String KEY_SEND_NOTIFY_DETAIL = "KEY_SEND_NOTIFY_DETAIL";
    public static final String KEY_IS_REGISTER_ADMIN = "KEY_IS_REGISTER_ADMIN";
    public static final String KEY_IS_UPDATE_MYHOME= "KEY_IS_UPDATE_MYHOME";
    public static final String KEY_SEND_ID_BOOKSERVICE_THANHTOAN = "KEY_SEND_CONTENT_THANHTOAN";
    public static final String DIEM_DEN_YEU_THICH = "ĐIỂM ĐIẾN YÊU THÍCH";
    public static final String TOP_NHA_NOI_BAT = "TOP NHÀ NỔI BẬT";
    public static final String KEY_SEND_ID_PROVINCE = "KEY_SEND_ID_PROVINCE";
    public static final String KEY_SEND_NAME_PROVINCE = "KEY_SEND_NAME_PROVINCE";
    public static final String KEY_SEND_ID_BOOKCAR = "KEY_SEND_ID_BOOKCAR";
}
