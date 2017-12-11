/*
 * Copyright 2015 Realm Inc.
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

package cabonline.se.test;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import cabonline.se.test.activity.MainActivity;
import cabonline.se.test.model.Trip;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.internal.RealmCore;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmConfiguration.class, RealmQuery.class, RealmResults.class, RealmCore.class, RealmLog.class})
public class ExampleActivityTest {
	// Robolectric, Using Power Mock https://github.com/robolectric/robolectric/wiki/Using-PowerMock
	
	@Rule
	public PowerMockRule rule = new PowerMockRule();
	
	private Realm mockRealm;
	private RealmResults<Trip> people;
	
	@Before
	public void setup() throws Exception {
		
		// Setup Realm to be mocked. The order of these matters
		mockStatic(RealmCore.class);
		mockStatic(RealmLog.class);
		mockStatic(Realm.class);
		mockStatic(RealmConfiguration.class);
		Realm.init(RuntimeEnvironment.application);
		
		// Create the mock
		final Realm mockRealm = mock(Realm.class);
		final RealmConfiguration mockRealmConfig = mock(RealmConfiguration.class);
		
		// TODO: Better solution would be just mock the RealmConfiguration.Builder class. But it seems there is some
		// problems for powermock to mock it (static inner class). We just mock the RealmCore.loadLibrary(Context) which
		// will be called by RealmConfiguration.Builder's constructor.
		doNothing().when(RealmCore.class);
		RealmCore.loadLibrary(any(Context.class));
		
		
		// TODO: Mock the RealmConfiguration's constructor. If the RealmConfiguration.Builder.build can be mocked, this
		// is not necessary anymore.
		whenNew(RealmConfiguration.class).withAnyArguments().thenReturn(mockRealmConfig);
		
		// Anytime getInstance is called with any configuration, then return the mockRealm
		when(Realm.getDefaultInstance()).thenReturn(mockRealm);
		
		// Anytime we ask Realm to create a Person, return a new instance.
		when(mockRealm.createObject(Trip.class)).thenReturn(new Trip());
		
	}
	
	
	@Test
	public void shouldBeAbleToAccessActivityAndVerifyRealmInteractions() {
		doCallRealMethod().when(mockRealm).executeTransaction(Mockito.any(Realm.Transaction.class));
		
		// Create activity
		MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().visible().get();
		
		assertThat(activity.getTitle().toString(), is("Unit Test Example"));
		
		// Verify that two Realm.getInstance() calls took place.
		verifyStatic(times(2));
		Realm.getDefaultInstance();
		;
	}
	
	
	@SuppressWarnings("unchecked")
	private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
		return mock(RealmQuery.class);
	}
	
	@SuppressWarnings("unchecked")
	private <T extends RealmObject> RealmResults<T> mockRealmResults() {
		return mock(RealmResults.class);
	}
}