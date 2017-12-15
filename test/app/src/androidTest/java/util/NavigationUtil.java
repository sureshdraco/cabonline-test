package util;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;

/**
 * Created by suresh on 14/12/17.
 */

public class NavigationUtil {


	public static void clickOnNavigationMenu() {
		onView(withContentDescription("Open navigation drawer")).perform(click());
	}
}
