package cabonline.se.test;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import cabonline.se.test.model.Trip;
import io.realm.Realm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class})
public class RealmTest {
	// Robolectric, Using Power Mock https://github.com/robolectric/robolectric/wiki/Using-PowerMock
	
	@Rule
	public PowerMockRule rule = new PowerMockRule();
	Realm mockRealm;
	
	@Before
	public void setup() {
		mockStatic(Realm.class);
		Realm mockRealm = PowerMockito.mock(Realm.class);
		when(Realm.getDefaultInstance()).thenReturn(mockRealm);
		this.mockRealm = mockRealm;
	}
	
	@Test
	public void shouldBeAbleToGetDefaultInstance() {
		assertThat(Realm.getDefaultInstance(), is(mockRealm));
	}
	
	@Test
	public void shouldBeAbleToCreateARealmObject() {
		Trip trip = new Trip();
		when(mockRealm.createObject(Trip.class)).thenReturn(trip);
		Trip output = mockRealm.createObject(Trip.class);
		assertThat(output, is(trip));
	}
}