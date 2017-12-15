import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cabonline.se.test.R;
import cabonline.se.test.activity.MainActivity;
import util.NavigationUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationMenuTest {

	public static final String PROFILE = "Profile";
	public static final String TRIP_LIST = "Trips";
	public static final String CAR_LIST = "Cars";
	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
			MainActivity.class);

	@Test
	public void testOpenSettings() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.profile));
		onView(withId(R.id.title)).check(matches(withText(PROFILE)));
	}

	@Test
	public void testOpenTrips() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.trips));
		onView(withId(R.id.title)).check(matches(withText(TRIP_LIST)));
	}

	@Test
	public void testOpenCars() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.cars));
		onView(withId(R.id.title)).check(matches(withText(CAR_LIST)));
	}
}
