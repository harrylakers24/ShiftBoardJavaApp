package model;

import exceptions.WeekOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestWeek {
    private Employer manager;
    private Week week;

    @BeforeEach
    public void setUp() throws WeekOutOfBoundsException {
        manager = new Employer("Bob", "123");
        week = manager.getWeek(1);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, week.getNumOfWeek());
        assertEquals(7, week.getDaysInWeek().size());

    }

    @Test
    public void testGetDay() {
        assertNull(week.getDay("Mon"));
        assertEquals("Monday", week.getDay("Monday").getDayName());
    }

}
