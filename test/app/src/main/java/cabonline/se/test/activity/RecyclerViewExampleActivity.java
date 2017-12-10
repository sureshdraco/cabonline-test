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
package cabonline.se.test.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cabonline.se.test.R;
import cabonline.se.test.adapter.TripsRecyclerViewAdapter;
import cabonline.se.test.model.Parent;
import io.realm.Realm;

public class RecyclerViewExampleActivity extends AppCompatActivity {

	private Realm realm;
	private RecyclerView recyclerView;
	private TripsRecyclerViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recyclerview);
		realm = Realm.getDefaultInstance();
		recyclerView = findViewById(R.id.recycler_view);
		setUpRecyclerView();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		recyclerView.setAdapter(null);
		realm.close();
	}

	private void setUpRecyclerView() {
		adapter = new TripsRecyclerViewAdapter(realm.where(Parent.class).findFirst().getTripList());
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(adapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
	}

}
