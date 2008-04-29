package fr.cs.orekit.time;

import junit.framework.*;

public class ChunkedTimeTest
extends TestCase {

    public ChunkedTimeTest(String name) {
        super(name);
    }

    public void testOutOfRange() {
        checkConstructorCompletion(-1, 10, 10, false);
        checkConstructorCompletion(24, 10, 10, false);
        checkConstructorCompletion(10, -1, 10, false);
        checkConstructorCompletion(10, 60, 10, false);
        checkConstructorCompletion(10, 10, -1, false);
        checkConstructorCompletion(10, 10, 60, false);
        checkConstructorCompletion(-1.0, false);
        checkConstructorCompletion(86401.0, false);
    }

    public void testInRange() {
        checkConstructorCompletion(10, 10, 10, true);
        checkConstructorCompletion(0.0, true);
        checkConstructorCompletion(43200.0, true);
        checkConstructorCompletion(86399.999, true);
    }

    public void testValues() {
        assertEquals(    0.0, new ChunkedTime( 0, 0, 0).getSecondsInDay(), 1.0e-10);
        assertEquals(21600.0, new ChunkedTime( 6, 0, 0).getSecondsInDay(), 1.0e-10);
        assertEquals(43200.0, new ChunkedTime(12, 0, 0).getSecondsInDay(), 1.0e-10);
        assertEquals(64800.0, new ChunkedTime(18, 0, 0).getSecondsInDay(), 1.0e-10);
        assertEquals(86399.9, new ChunkedTime(23, 59, 59.9).getSecondsInDay(), 1.0e-10);
    }

    public void testString() {
        assertEquals("00:00:00.000", new ChunkedTime(0).toString());
        assertEquals("06:00:00.000", new ChunkedTime(21600).toString());
        assertEquals("12:00:00.000", new ChunkedTime(43200).toString());
        assertEquals("18:00:00.000", new ChunkedTime(64800).toString());
        assertEquals("23:59:59.900", new ChunkedTime(86399.9).toString());
    }

    public void testComparisons() {
        ChunkedTime[] times = {
                 new ChunkedTime( 0,  0,  0.0),
                 new ChunkedTime( 0,  0,  1.0e-15),
                 new ChunkedTime( 0, 12,  3.0),
                 new ChunkedTime(15,  9,  3.0),
                 new ChunkedTime(23, 59, 59.0),
                 new ChunkedTime(23, 59, 60.0 - 1.0e-12)
        };
        for (int i = 0; i < times.length; ++i) {
            for (int j = 0; j < times.length; ++j) {
                if (times[i].compareTo(times[j]) < 0) {
                    assertTrue(times[j].compareTo(times[i]) > 0);
                    assertFalse(times[i].equals(times[j]));
                    assertFalse(times[j].equals(times[i]));
                    assertTrue(times[i].hashCode() != times[j].hashCode());
                    assertTrue(i < j);
                } else if (times[i].compareTo(times[j]) > 0) {
                    assertTrue(times[j].compareTo(times[i]) < 0);
                    assertFalse(times[i].equals(times[j]));
                    assertFalse(times[j].equals(times[i]));
                    assertTrue(times[i].hashCode() != times[j].hashCode());
                    assertTrue(i > j);
                } else {
                    assertTrue(times[j].compareTo(times[i]) == 0);
                    assertTrue(times[i].equals(times[j]));
                    assertTrue(times[j].equals(times[i]));
                    assertTrue(times[i].hashCode() == times[j].hashCode());
                    assertTrue(i == j);
                }
            }
        }
        assertFalse(times[0].equals(this));
    }

    private void checkConstructorCompletion(int hour, int minute, double second,
                                            boolean expectedCompletion) {
        try {
            ChunkedTime time = new ChunkedTime(hour, minute, second);
            assertEquals(hour,   time.getHour());
            assertEquals(minute, time.getMinute());
            assertEquals(second, time.getSecond(), 1.0e-10);
            assertTrue(expectedCompletion);
        } catch (IllegalArgumentException iae) {
            assertTrue(! expectedCompletion);
        } catch (Exception e) {
            fail("wrong exception caught");
        }
    }

    private void checkConstructorCompletion(double seconds,
                                            boolean expectedCompletion) {
        try {
            ChunkedTime time = new ChunkedTime(seconds);
            assertEquals(seconds, time.getSecondsInDay(), 1.0e-10);
            assertTrue(expectedCompletion);
        } catch (IllegalArgumentException iae) {
            assertTrue(! expectedCompletion);
        } catch (Exception e) {
            fail("wrong exception caught");
        }
    }

    public static Test suite() {
        return new TestSuite(ChunkedTimeTest.class);
    }

}
