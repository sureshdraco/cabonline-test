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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cabonline.se.test.R;
import cabonline.se.test.model.Trip;
import io.realm.OrderedRealmCollection;

public class TripsRecyclerViewAdapter extends RealmRecyclerViewAdapter<Trip, TripsRecyclerViewAdapter.MyViewHolder> {

	public TripsRecyclerViewAdapter(OrderedRealmCollection<Trip> data) {
		super(data, true);
		// Only set this if the model class has a primary key that is also a integer or long.
		// In that case, {@code getItemId(int)} must also be overridden to return the key.
		// See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#hasStableIds()
		// See https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#getItemId(int)
		setHasStableIds(true);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.row, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		final Trip trip = getItem(position);
		holder.data = trip;
		holder.title.setText(trip.getAmount());
	}

	@Override
	public long getItemId(int index) {
		//noinspection ConstantConditions
		return getItem(index).getId();
	}

	class MyViewHolder extends RecyclerView.ViewHolder {
		TextView title;
		public Trip data;

		MyViewHolder(View view) {
			super(view);
			title = view.findViewById(R.id.textview);
		}
	}
}
