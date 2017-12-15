import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cabonline.se.test.R;
import cabonline.se.test.activity.MainActivity;
import util.NavigationUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TripDetailFragmentTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

	@Before
	public void setup() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.trips));
		onView(withId(R.id.tripRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
	}

	@Test
	public void testTripDetailIsOpened() {
		onView(withId(R.id.title)).check(matches(withText("Trip")));
	}

	@Test
	public void verifyDetailIsDisplayed() {
		onView(withId(R.id.destination)).check(matches(withText("stockholm")));
	}
}