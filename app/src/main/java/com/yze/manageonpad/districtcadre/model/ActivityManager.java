package com.yze.manageonpad.districtcadre.model;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class ActivityManager {
    private List<Activity> activities = new ArrayList<Activity>();

    public ActivityManager() {

    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

}
