package cabonline.se.test;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.realm.Realm;
import io.realm.RealmQuery;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmQuery.class})
@Ignore
public class UserSettingsFragmentTest {

	@Rule
	public PowerMockRule rule = new PowerMockRule();

	@Before
	public void setup() {
//		mockStatic(Realm.class);
//		Realm mockRealm = PowerMockito.mock(Realm.class);
//		User mockUser = PowerMockito.mock(User.class);
//		when(mockUser.getName()).thenReturn("suresh");
//		RealmQuery<User> realmQuery = PowerMockito.mock(RealmQuery.class);
//		when(Realm.getDefaultInstance()).thenReturn(mockRealm);
//		when(mockRealm.where(User.class)).thenReturn(realmQuery);
//		when(realmQuery.findFirstAsync()).thenReturn(mockUser);
	}

//	@Test
//	public void shouldNotBeNull() throws Exception {
//		UserSettingsFragment fragment = new UserSettingsFragment();
//		startVisibleFragment(fragment);
//		assertNotNull(fragment);
//	}
} 