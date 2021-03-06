package cabonline.se.test.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import cabonline.se.test.R;
import cabonline.se.test.model.User;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;

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

	private final RealmChangeListener<User> realmListener = user -> {
		Log.d(TAG, "user change came");
		setupData();
	};

	private final RealmChangeListener<Realm> listener = new RealmChangeListener<Realm>() {
		@Override
		public void onChange(Realm realm) {
			Log.d(TAG, "realm change came");
			RealmObject.removeAllChangeListeners(user);
			queryUser();
		}
	};

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initView(view);
		setupView(view);
	}

	private void setupView(View view) {
		view.findViewById(R.id.emailRow).setOnClickListener(view1 -> {
			final EditText input = new EditText(getContext());
			input.setText(user != null && RealmObject.isValid(user) ? user.getEmail() : "");
			showAlertDialog("Email", input, () -> {
				String email = input.getText().toString();
				realm.beginTransaction();
				user.setEmail(email);
				realm.commitTransaction();
			});
		});

		view.findViewById(R.id.nameRow).setOnClickListener(view12 -> {
			final EditText input = new EditText(getContext());
			input.setText(user != null && RealmObject.isValid(user) ? user.getName() : "");
			showAlertDialog("Name", input, () -> {
				String name = input.getText().toString();
				realm.beginTransaction();
				user.setName(name);
				realm.commitTransaction();
			});
		});
		view.findViewById(R.id.phoneRow).setOnClickListener(view13 -> {
			final EditText input = new EditText(getContext());
			input.setText(user != null && RealmObject.isValid(user) ? user.getPhone() : "");
			showAlertDialog("Phone", input, () -> {
				String phone = input.getText().toString();
				realm.beginTransaction();
				user.setPhone(phone);
				realm.commitTransaction();
			});
		});
	}

	private void showAlertDialog(String title, EditText editText, final Runnable onUpdate) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
		alertDialog.setTitle(title);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		editText.setLayoutParams(lp);
		alertDialog.setView(editText);

		alertDialog.setPositiveButton("Update",
				(dialog, which) -> onUpdate.run());

		alertDialog.setNegativeButton("Cancel",
				(dialog, which) -> dialog.cancel());

		alertDialog.show();
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
		realm.addChangeListener(listener);
		queryUser();
	}

	private void queryUser() {
		user = realm.where(User.class).findFirstAsync();
		RealmObject.addChangeListener(user, realmListener);
	}

	private void setupData() {
		Log.d(TAG, "setup data");
		if (user != null && RealmObject.isValid(user)) {
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
		RealmObject.removeChangeListener(user, realmListener);
		realm.removeChangeListener(listener);
		realm.close();
	}
}
