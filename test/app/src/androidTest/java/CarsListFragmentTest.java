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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CarsListFragmentTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);
	private RecyclerViewMatcher carsRecyclerView;

	@Before
	public void setup() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.cars));
		carsRecyclerView = withRecyclerView(R.id.carRecyclerView);
	}

	@Test
	public void verifyFirstItem() {
		onView(carsRecyclerView.atPosition(0)).check(matches(hasDescendant(withText("renault"))));
	}

	@Test
	public void verifySecondItem() {
		onView(carsRecyclerView.atPosition(1)).check(matches(hasDescendant(withText("mazda"))));
	}

	@Test
	public void verifyLastItem() {
		onView(carsRecyclerView.atPosition(2)).check(matches(hasDescendant(withText("audi"))));
	}

	public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
		return new RecyclerViewMatcher(recyclerViewId);
	}
}