package cabonline.se.test.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import cabonline.se.test.R;
import cabonline.se.test.fragment.TripDetailFragment;

public class TripDetailActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back_btn_with_title);
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		Fragment fragment = new TripDetailFragment();
		fragment.setArguments(getIntent().getExtras());
		transaction.replace(R.id.content, fragment);
		transaction.commit();
	}
}
