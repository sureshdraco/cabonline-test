package cabonline.se.test.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.InputStream;

import cabonline.se.test.R;
import cabonline.se.test.model.Trip;
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
			sleep(4000);
		} catch (InterruptedException e) {
		}
		Realm realm = Realm.getDefaultInstance();
		try {
			InputStream is = getResources().openRawResource(R.raw.trip_list);
			realm.beginTransaction();
			realm.createAllFromJson(Trip.class, is);
			realm.commitTransaction();
		} catch (Exception e) {
			if (Constant.DEBUG) Log.d(TAG, e.toString());
		} finally {
			realm.close();
		}
	}
}