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

import io.realm.Realm;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmLog.class, RealmCore.class})
public class ExampleRealmTest {
	// Robolectric, Using Power Mock https://github.com/robolectric/robolectric/wiki/Using-PowerMock
	
	@Rule
	public PowerMockRule rule = new PowerMockRule();
	Realm mockRealm;
	
	@Before
	public void setup() {
		mockStatic(RealmLog.class);
		mockStatic(Realm.class);
		mockStatic(RealmCore.class);
		
		Realm mockRealm = PowerMockito.mock(Realm.class);
		
		when(Realm.getDefaultInstance()).thenReturn(mockRealm);
		
		this.mockRealm = mockRealm;
	}
	
	@Test
	public void shouldBeAbleToGetDefaultInstance() {
		assertThat(Realm.getDefaultInstance(), is(mockRealm));
	}
}