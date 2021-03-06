package flipage.cpu.com.cpuflipage.utils;

import android.content.Context;
import android.content.SharedPreferences;

import flipage.cpu.com.cpuflipage.data.Department;
import flipage.cpu.com.cpuflipage.data.User;

/**
 * Created by paolo on 5/1/18.
 */

public class FlipagePrefrences {

    private static String PREF = "flipage.cpu.com.cpuflipage.PREFERENCE";
    private static String IS_LOGGED_IN = "flipage.cpu.com.cpuflipage.IS_LOGGED_IN";
    private static String USERNAME = "flipage.cpu.com.cpuflipage.USERNAME";
    private static String EMAIL = "flipage.cpu.com.cpuflipage.EMAIL";
    private static String ID_NUMBER = "flipage.cpu.com.cpuflipage.ID_NUMBER";
    private static String IMAGE = "flipage.cpu.com.cpuflipage.IMAGE";
    private static String IS_ADMIN = "flipage.cpu.com.cpuflipage.IS_ADMIN";
    private static String DEPARTMENT = "flipage.cpu.com.cpuflipage.DEPARTMENT";
    private static String ID = "flipage.cpu.com.cpuflipage.ID";
    private static String IS_GUEST =  "flipage.cpu.com.cpuflipage.GUEST";


    public synchronized static SharedPreferences getPrefs() {
        return FlipageApp.getContext().getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public static boolean getIsGuest() {
        return getPrefs().getBoolean(IS_GUEST, false);
    }

    public static boolean getIsLoggedIn() {
        return getPrefs().getBoolean(IS_LOGGED_IN, false);
    }

    public static boolean getIsAdmin() {
        return getPrefs().getBoolean(IS_ADMIN, false);
    }

    public static String getUSERNAME() {
        return getPrefs().getString(USERNAME, "");
    }

    public static String getEMAIL() {
        return getPrefs().getString(EMAIL, "");
    }

    public static String getIdNumber() {
        return getPrefs().getString(ID_NUMBER, "");
    }

    public static String getIMAGE() {
        return getPrefs().getString(IMAGE, "");
    }

    public static String getDepartment() {
        return getPrefs().getString(DEPARTMENT, "");
    }

    public static long getId() {
        return getPrefs().getLong(ID, 0);
    }

    public static void setIsGuest(boolean isGuest) {
        getPrefs().edit().putBoolean(IS_GUEST, isGuest).commit();
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        getPrefs().edit().putBoolean(IS_LOGGED_IN, isLoggedIn).commit();
    }

    public static void setIsAdmin(boolean isAdmin) {
        getPrefs().edit().putBoolean(IS_ADMIN, isAdmin).commit();
    }

    public static void setUSERNAME(String userName) {
        getPrefs().edit().putString(USERNAME, userName).commit();
    }

    public static void setEMAIL(String email) {
        getPrefs().edit().putString(EMAIL, email).commit();
    }

    public static void setIdNumber(String idNumber) {
        getPrefs().edit().putString(ID_NUMBER, idNumber).commit();
    }

    public static void setId(long id) {
        getPrefs().edit().putLong(ID, id).commit();
    }

    public static void setIMAGE(String image) {
        getPrefs().edit().putString(IMAGE, image).commit();
    }

    public static void setDepartment(String department) {
        getPrefs().edit().putString(DEPARTMENT, department).commit();
    }

    public static User getUser() {
        User user = new User();
        user.setIdNumber(getIdNumber());
        user.setUsername(getUSERNAME());
        user.setImage(getIMAGE());
        user.setAdmin(getIsAdmin());
        user.setEmail(getEMAIL());
        Department department = new Department();
        department.setName(getDepartment());
        user.setDepartment(department);
        user.setId(getId());
        return user;
    }

    public static void setUser(User user, boolean loggedIn) {
        setId(user.getId());
        setIdNumber(user.getIdNumber());
        setUSERNAME(user.getUsername());
        setIMAGE(user.getImage());
        setEMAIL(user.getEmail());
        setIsGuest(loggedIn);
        if(user.getDepartment() != null) {
            setDepartment(user.getDepartment().getName());
        }
        setIsAdmin(user.isAdmin());
        setEMAIL(user.getEmail());
        setIsLoggedIn(loggedIn);
    }
}
