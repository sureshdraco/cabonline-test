package cabonline.se.test.model;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Parent extends RealmObject {
	@SuppressWarnings("unused")
	private RealmList<Trip> tripList;

	public RealmList<Trip> getTripList() {
		return tripList;
	}
}