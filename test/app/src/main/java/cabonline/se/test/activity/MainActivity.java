package cabonline.se.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import cabonline.se.test.R;
import cabonline.se.test.fragment.CarListFragment;
import cabonline.se.test.fragment.TripListFragment;
import cabonline.se.test.fragment.UserSettingsFragment;
import cabonline.se.test.model.Car;
import cabonline.se.test.model.Trip;
import cabonline.se.test.model.User;
import cabonline.se.test.service.LoadJsonFilesIntentService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	public static final int FRAGMENT_TRIPS = 0;
	public static final int FRAGMENT_SETTINGS = 1;
	public static final int FRAGMENT_CARS = 2;

	private int currentFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initRealm();
		startService(new Intent(getApplicationContext(), LoadJsonFilesIntentService.class));
		initView();
	}

	private void initRealm() {
		Realm.init(this);
		RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
		Realm.setDefaultConfiguration(realmConfiguration);
	}

	private void initView() {
		final Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.findViewById(R.id.delete).setOnClickListener(view -> {
			Realm realm = Realm.getDefaultInstance();
			realm.beginTransaction();
			switch (currentFragment) {
				case FRAGMENT_SETTINGS:
					realm.delete(User.class);
					break;
				case FRAGMENT_CARS:
					realm.delete(Car.class);
					break;
				case FRAGMENT_TRIPS:
				default:
					realm.delete(Trip.class);
					break;
			}
			realm.commitTransaction();
			realm.close();
		});
		setSupportActionBar(toolbar);
		setupNavigationDrawer(toolbar);
		showFragment(FRAGMENT_TRIPS);
	}

	private void setupNavigationDrawer(Toolbar toolbar) {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.setCheckedItem(R.id.trips);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.profile) {
			showFragment(FRAGMENT_SETTINGS);
		} else if (id == R.id.trips) {
			showFragment(FRAGMENT_TRIPS);
		} else if (id == R.id.cars) {
			showFragment(FRAGMENT_CARS);
		}
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void showFragment(int fragmentNo) {
		currentFragment = fragmentNo;
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Bundle bundle = new Bundle();
		Fragment fragment;
		switch (fragmentNo) {
			case FRAGMENT_CARS:
				fragment = new CarListFragment();
				((TextView) findViewById(R.id.title)).setText("Cars");
				break;
			case FRAGMENT_SETTINGS:
				fragment = new UserSettingsFragment();
				((TextView) findViewById(R.id.title)).setText("Profile");
				break;
			case FRAGMENT_TRIPS:
			default:
				fragment = new TripListFragment();
				((TextView) findViewById(R.id.title)).setText("Trips");
				break;
		}
		fragment.setArguments(bundle);
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}
}