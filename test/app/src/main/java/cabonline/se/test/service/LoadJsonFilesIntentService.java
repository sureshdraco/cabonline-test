package cabonline.se.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.InputStream;

import cabonline.se.test.R;
import cabonline.se.test.model.Car;
import cabonline.se.test.model.Trip;
import cabonline.se.test.model.User;
import cabonline.se.test.util.Constant;
import io.realm.Realm;

import static java.lang.Thread.sleep;

public class LoadJsonFilesIntentService extends IntentService {
	private static final String TAG = LoadJsonFilesIntentService.class.getSimpleName();

	public LoadJsonFilesIntentService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			sleep(2000);
		} catch (InterruptedException e) {
		}
		Realm realm = Realm.getDefaultInstance();
		try {
			InputStream tripIs = getResources().openRawResource(R.raw.trip_list);
			InputStream userIs = getResources().openRawResource(R.raw.user);
			InputStream carIs = getResources().openRawResource(R.raw.car_list);
			realm.beginTransaction();
			realm.createOrUpdateAllFromJson(Trip.class, tripIs);
			realm.createOrUpdateAllFromJson(Car.class, carIs);
			realm.createObjectFromJson(User.class, userIs);
			realm.commitTransaction();
		} catch (Exception e) {
			if (Constant.DEBUG) Log.e(TAG, e.toString());
		} finally {
			realm.close();
		}
	}
}