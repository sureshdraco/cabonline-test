package cabonline.se.test;

import android.app.Application;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import cabonline.se.test.model.Trip;
import cabonline.se.test.util.Constant;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

	private static final String TAG = MyApplication.class.getSimpleName();

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.init(this);
		RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
		Realm.setDefaultConfiguration(realmConfig);
	}

	private void loadJson() {
		InputStream is = getResources().openRawResource(R.raw.trip_list);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
			Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			if (Constant.DEBUG) Log.e(TAG, e.toString());
		}
		String jsonString = writer.toString();
		Realm.getDefaultInstance().createObjectFromJson(List<Trip>.class, jsonString);
	}
} 