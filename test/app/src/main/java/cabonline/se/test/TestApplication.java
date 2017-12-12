package cabonline.se.test;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import java.io.InputStream;

import cabonline.se.test.model.Trip;
import cabonline.se.test.util.Constant;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class TestApplication extends Application {
	
	private static final String TAG = TestApplication.class.getSimpleName();
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
}