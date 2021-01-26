package com.medhub;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GlobalVariables {


    private static GlobalVariables instance = new GlobalVariables();

    // Getter-Setters
    public static GlobalVariables getInstance() {
        return instance;
    }

    public static void setInstance(GlobalVariables instance) {
        GlobalVariables.instance = instance;
    }

    private String notification_index;

    private ArrayList<String> other;

    private String user_id;
    private String other_user_id;


    public GlobalVariables() {

    }


    public String getValue() {
        return notification_index;
    }


    public void setValue(String notification_index) {
        this.notification_index = notification_index;
    }

    public void setOtherValue(ArrayList<String> notification_index) {
        this.other = notification_index;
    }

    public void setUserId(String user_id)
    {
        this.user_id = user_id;
    }
    public void setOtherUserId(String other_user_id)
    {
        this.other_user_id = other_user_id;
    }
    public String getUserId()
    {
        return user_id;
    }
    public String getOther_user_id()
    {
        return other_user_id;
    }

    public ArrayList<String> getOtherValue() {
        return other;
    }

}
