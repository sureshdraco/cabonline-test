import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cabonline.se.test.R;
import cabonline.se.test.activity.MainActivity;
import util.NavigationUtil;
import util.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TripsListFragmentTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
	private RecyclerViewMatcher tripRecycleView;

	@Before
	public void setup() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.trips));
		tripRecycleView = withRecyclerView(R.id.tripRecyclerView);
		try {
			Thread.sleep(3500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void verifyFirstItem() {
		onView(tripRecycleView.atPosition(0)).check(matches(hasDescendant(withText("stockholm"))));
	}

	@Test
	public void verifySecondItem() {
		onView(tripRecycleView.atPosition(1)).check(matches(hasDescendant(withText("Sweden"))));
	}

	@Test
	public void verifyLastItem() {
		onView(withId(R.id.tripRecyclerView)).perform(actionOnItemAtPosition(9, scrollTo()));
		onView(tripRecycleView.atPosition(9)).check(matches(hasDescendant(withText("Blekinge"))));
	}

	public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
		return new RecyclerViewMatcher(recyclerViewId);
	}
}