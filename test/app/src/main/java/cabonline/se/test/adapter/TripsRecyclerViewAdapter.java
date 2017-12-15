/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cabonline.se.test.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import cabonline.se.test.R;
import cabonline.se.test.activity.TripDetailActivity;
import cabonline.se.test.fragment.TripDetailFragment;
import cabonline.se.test.model.Trip;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;

public class TripsRecyclerViewAdapter extends RealmRecyclerViewAdapter<Trip, TripsRecyclerViewAdapter.MyViewHolder> implements Filterable {

	private final Realm realm;
	private final LottieAnimationView loaderView;

	public TripsRecyclerViewAdapter(LottieAnimationView loaderView, OrderedRealmCollection<Trip> data) {
		super(data, true);
		this.loaderView = loaderView;
		setHasStableIds(true);
		realm = Realm.getDefaultInstance();
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.trip_row, parent, false);
		handleLoaderView();
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		final Trip trip = getItem(position);
		holder.data = trip;
		holder.destination.setText(trip.getDestination());
		holder.price.setText(trip.getPrice());
		holder.time.setText(trip.getDisplayTime());
	}

	private void handleLoaderView() {
		if (loaderView.getVisibility() == View.VISIBLE) {
			loaderView.cancelAnimation();
			loaderView.setVisibility(View.GONE);
		}
	}

	@Override
	public long getItemId(int index) {
		//noinspection ConstantConditions
		return getItem(index).getId();
	}

	@Override
	public Filter getFilter() {
		return new MyNamesFilter(this);
	}

	private class MyNamesFilter
			extends Filter {
		private final TripsRecyclerViewAdapter adapter;

		private MyNamesFilter(TripsRecyclerViewAdapter adapter) {
			super();
			this.adapter = adapter;
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			return new FilterResults();
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			adapter.filterResults(constraint.toString());
		}
	}

	private void filterResults(String text) {
		text = text == null ? null : text.toLowerCase().trim();
		if (text == null || "".equals(text)) {
			updateData(realm.where(Trip.class).findAllAsync());
		} else {
			updateData(realm.where(Trip.class)
					.contains("destination", text, Case.INSENSITIVE)
					.findAllAsync());
		}
	}

	class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView destination, price, time;
		Trip data;

		MyViewHolder(View view) {
			super(view);
			destination = view.findViewById(R.id.destination);
			time = view.findViewById(R.id.tripTime);
			price = view.findViewById(R.id.price);
			view.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(view.getContext(), TripDetailActivity.class);
			intent.putExtra(TripDetailFragment.EXTRA_TRIP, data.getId());
			view.getContext().startActivity(intent);
		}
	}
}
