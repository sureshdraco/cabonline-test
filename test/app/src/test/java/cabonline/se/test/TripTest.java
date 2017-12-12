package cabonline.se.test;

import org.junit.Test;

import java.util.Date;

import cabonline.se.test.model.Trip;

import static org.junit.Assert.assertEquals;

public class TripTest {
	
	@Test
	public void testEmptyTripDisplayTime() {
		Trip trip = new Trip();
		assertEquals("", trip.getDisplayTime());
	}
	
	@Test
	public void testTripDisplayTime() {
		Date date = new Date();
		Trip trip = new Trip(1, "", "", "", date);
		assertEquals(date.toString(), trip.getDisplayTime());
	}
}