package cabonline.se.test.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	public static final String PREF_STORAGE_LOCATION = "cabonline";
	private static final String FIRST_TIME_LAUNCH = "FIRST_TIME_LAUNCH";

	public static SharedPreferences getPref(Context context) {
		return context.getSharedPreferences(PREF_STORAGE_LOCATION, 0);
	}

	private static SharedPreferences.Editor getPrefEditor(Context context) {
		return getPref(context).edit();
	}

	public static boolean isFirstTimeLaunch(Context context) {
		return getPref(context).getBoolean(FIRST_TIME_LAUNCH, true);
	}

	public static void setFirstTimeLaunch(Context context, boolean firstTime) {
		getPrefEditor(context).putBoolean(FIRST_TIME_LAUNCH, firstTime).apply();
	}
}
