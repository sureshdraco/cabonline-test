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
import cabonline.se.test.adapter.CarsRecyclerViewAdapter;
import cabonline.se.test.model.Car;
import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment {

	private RecyclerView recyclerView;
	private Realm realm;
	private CarsRecyclerViewAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_cars, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		recyclerView = view.findViewById(R.id.carRecyclerView);
		realm = Realm.getDefaultInstance();
		setUpRecyclerView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		recyclerView.setAdapter(null);
		realm.close();
	}

	private void setUpRecyclerView() {
		// find all always returns a list, so no need for null check
		adapter = new CarsRecyclerViewAdapter(realm.where(Car.class).findAllAsync());
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
	}
}