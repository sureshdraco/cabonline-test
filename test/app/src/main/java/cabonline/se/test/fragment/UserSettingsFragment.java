package cabonline.se.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cabonline.se.test.R;
import cabonline.se.test.model.User;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingsFragment extends Fragment {
	
	
	private static final String TAG = UserSettingsFragment.class.getSimpleName();
	private Realm realm;
	private TextView name, phone, email;
	private User user;
	
	public UserSettingsFragment() {
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_user_profile, container, false);
	}
	
	private final RealmChangeListener<Realm> realmListener = new RealmChangeListener<Realm>() {
		
		@Override
		public void onChange(Realm realm) {
			Log.d(TAG, "realm change came");
			setupData();
		}
	};
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
	}
	
	private void initView(View view) {
		name = view.findViewById(R.id.name);
		phone = view.findViewById(R.id.phone);
		email = view.findViewById(R.id.email);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		realm = Realm.getDefaultInstance();
		user = realm.where(User.class).findFirstAsync();
		realm.addChangeListener(realmListener);
		setupData();
	}
	
	private void setupData() {
		if (user != null && user.isValid()) {
			name.setText(user.getName());
			phone.setText(user.getPhone());
			email.setText(user.getEmail());
		} else {
			name.setText("");
			phone.setText("");
			email.setText("");
		}
		
	}
	
	@Override
	public void onStop() {
		super.onStop();
		realm.removeChangeListener(realmListener);
		realm.close();
	}
}
