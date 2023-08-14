import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarProAppTest {

    private static final List<Event> events = new ArrayList<>();

    @Test
    public void testHasSchedulingConflict() {
        Event existingEvent = new Event(LocalDate.of(2023, 8, 15), "Test Event", "12:00 PM", "Location", "Notes", "Category");
        events.add(existingEvent);

        boolean conflictTest1 = CalendarProApp.hasSchedulingConflict(LocalDate.of(2023, 8, 15), "12:00 PM");
        boolean conflictTest2 = CalendarProApp.hasSchedulingConflict(LocalDate.of(2023, 8, 16), "12:00 PM");

        System.out.println("Test 1: Scheduling conflict expected (true), actual: " + conflictTest1);
        System.out.println("Test 2: Scheduling conflict not expected (false), actual: " + conflictTest2);

        assertTrue(conflictTest1);
        assertFalse(conflictTest2);

        events.clear();
    }

    @Test
    public void testNoSchedulingConflict() {
        Event existingEvent = new Event(LocalDate.of(2023, 8, 15), "Test Event", "12:00 PM", "Location", "Notes", "Category");
        events.add(existingEvent);

        boolean conflictTest1 = CalendarProApp.hasSchedulingConflict(LocalDate.of(2023, 8, 15), "10:00 AM");
        boolean conflictTest2 = CalendarProApp.hasSchedulingConflict(LocalDate.of(2023, 8, 16), "2:00 PM");

        System.out.println("Test 1: No scheduling conflict expected (false), actual: " + conflictTest1);
        System.out.println("Test 2: No scheduling conflict expected (false), actual: " + conflictTest2);

        assertFalse(conflictTest1);
        assertFalse(conflictTest2);

        events.clear();
    }
}
