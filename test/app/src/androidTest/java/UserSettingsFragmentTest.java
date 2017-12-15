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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class UserSettingsFragmentTest {

	@Rule
	public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

	@Before
	public void setup() {
		NavigationUtil.clickOnNavigationMenu();
		onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.profile));
	}

	@Test
	public void nameIsSetRight() {
		onView(withId(R.id.name)).check(matches(withText("John Watson")));
	}

	@Test
	public void phoneNumberIsSetRight() {
		onView(withId(R.id.phone)).check(matches(withText("0324242242")));
	}

	@Test
	public void emailIsSetRight() {
		onView(withId(R.id.email)).check(matches(withText("john@gmail.com")));
	}
} 