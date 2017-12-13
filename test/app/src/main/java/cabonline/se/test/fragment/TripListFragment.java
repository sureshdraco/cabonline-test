package cabonline.se.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cabonline.se.test.R;
import cabonline.se.test.adapter.TripsRecyclerViewAdapter;
import cabonline.se.test.model.Trip;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripListFragment extends Fragment implements SearchView.OnQueryTextListener {

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
		realm = Realm.getDefaultInstance();
		setUpRecyclerView();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);
		final MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(this);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
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

	@Override
	public boolean onQueryTextSubmit(String query) {
		adapter.getFilter().filter(query);
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}
}