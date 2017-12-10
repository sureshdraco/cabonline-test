package cabonline.se.test;

import android.app.Application;
import android.util.Log;

import java.io.InputStream;

import cabonline.se.test.model.Trip;
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
		Realm.deleteRealm(realmConfig);
		loadJson();
	}

	private void loadJson() {
		Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
			@Override
			public void execute(Realm realm) {
				try {
					InputStream is = MyApplication.this.getResources().openRawResource(R.raw.trip_list);
					realm.createAllFromJson(Trip.class, is);
				} catch (Exception e) {
					Log.e(TAG, e.toString());
				}
			}
		});
	}
} 