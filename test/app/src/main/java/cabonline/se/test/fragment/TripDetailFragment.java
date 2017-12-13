package cabonline.se.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cabonline.se.test.R;
import cabonline.se.test.model.Trip;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripDetailFragment extends Fragment implements SearchView.OnQueryTextListener {


	private static final String TAG = TripDetailFragment.class.getSimpleName();
	public static final String EXTRA_TRIP = "EXTRA_TRIP";
	private Trip trip;
	private TextView destination, price, time, card, km;
	private Realm realm;
	private int tripId;

	public TripDetailFragment() {
		// Required empty public constructor
	}

	private final RealmChangeListener<Trip> tripChangeListener = trip -> {
		Log.d(TAG, "user change came");
		setupData();
	};

	private final RealmChangeListener<Realm> realmChangeListener = new RealmChangeListener<Realm>() {
		@Override
		public void onChange(Realm realm) {
			Log.d(TAG, "realm change came");
			RealmObject.removeAllChangeListeners(trip);
			queryUser();
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_trip_detail, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (getArguments() == null) {
			return;
		}
		initView(view);
		tripId = getArguments().getInt(EXTRA_TRIP, -1);
	}


	private void initView(View view) {
		getActivity().findViewById(R.id.backBtn).setOnClickListener(view1 -> getActivity().finish());
		destination = view.findViewById(R.id.destination);
		price = view.findViewById(R.id.price);
		card = view.findViewById(R.id.cardType);
		time = view.findViewById(R.id.tripTime);
		km = view.findViewById(R.id.km);
		TextView toolbarTitle = getActivity().findViewById(R.id.title);
		toolbarTitle.setText("Trip");
	}

	@Override
	public void onStart() {
		super.onStart();
		realm = Realm.getDefaultInstance();
		realm.addChangeListener(realmChangeListener);
		queryUser();
	}

	private void queryUser() {
		trip = realm.where(Trip.class).equalTo("id", tripId).findFirstAsync();
		RealmObject.addChangeListener(trip, tripChangeListener);
	}

	private void setupData() {
		Log.d(TAG, "setup data");
		if (trip != null && RealmObject.isValid(trip)) {
			destination.setText(trip.getDestination());
			price.setText(trip.getDestination());
			card.setText(trip.getPaymentType());
			time.setText(trip.getDisplayTime());
			km.setText(String.valueOf(trip.getKm()));
		} else {
			destination.setText("");
			price.setText("");
			card.setText("");
			time.setText("");
			km.setText("");
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		RealmObject.removeChangeListener(trip, tripChangeListener);
		realm.removeChangeListener(realmChangeListener);
		realm.close();
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}
}
