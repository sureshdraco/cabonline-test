package cabonline.se.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cabonline.se.test.R;
import cabonline.se.test.adapter.TripsRecyclerViewAdapter;
import cabonline.se.test.model.Trip;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripListFragment extends Fragment {

	private RecyclerView recyclerView;
	private Realm realm;
	private TripsRecyclerViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_trips, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = view.findViewById(R.id.tripRecyclerView);
	}

	@Override
	public void onStart() {
		super.onStart();
		realm = Realm.getDefaultInstance();
		setUpRecyclerView();
	}

	@Override
	public void onStop() {
		super.onStop();
		recyclerView.setAdapter(null);
		realm.close();
	}

	private void setUpRecyclerView() {
		// find all always returns a list, so no need for null check
		adapter = new TripsRecyclerViewAdapter(realm.where(Trip.class).findAllAsync());
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
	}
}