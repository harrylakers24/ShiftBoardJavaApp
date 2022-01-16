package model;

import exceptions.WeekOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDay {
    private Employer manager;
    private Employee employee;
    private Employee employee2;
    private Day monday;
    private Day tuesday;
    private Day wednesday;


    @BeforeEach
    public void setUp() throws WeekOutOfBoundsException {
        manager = new Employer("Bob", "123");
        employee = new Employee("Harry", "123", manager);
        employee2 = new Employee("Bob", "123", manager);
        monday = manager.getWeek(1).getDay("Monday");
        tuesday = manager.getWeek(1).getDay("Tuesday");
        wednesday = manager.getWeek(1).getDay("Wednesday");

    }

    @Test
    public void testConstructor() {
        assertEquals("Monday", monday.getDayName());
        assertTrue(monday.getMorningAvailability());
        assertTrue(monday.getAfternoonAvailability());
        assertTrue(monday.getNightAvailability());


        assertEquals(3, monday.getListOfTimeAvailability().size());
        assertEquals(3, monday.getListOfEmployee().size());

        for (int i = 0; i < 3; i++) {
            assertTrue(monday.getListOfTimeAvailability().get(i));
        }

        for (int i = 0; i < 3; i++) {
            assertNull(monday.getListOfEmployee().get(i));
        }
    }

    @Test
    public void testGetWorkingEmployee() throws WeekOutOfBoundsException {
        assertNull(monday.getWorkingEmployee("M"));

        employee.signUpShift(1, "Monday", "M");
        assertEquals(employee, monday.getWorkingEmployee("M"));
        assertNull(monday.getWorkingEmployee("A"));

        assertNull(monday.getWorkingEmployee("S"));
    }

    @Test
    public void testTakeShift() {
        assertTrue(monday.getMorningAvailability());

        monday.takeShift(employee, "M");
        assertEquals(employee, monday.getListOfEmployee().get(0));
        assertFalse(monday.getMorningAvailability());

        monday.removeShift("M");
        monday.takeShift(employee2, "M");
        assertEquals(employee2, monday.getListOfEmployee().get(0));
        assertFalse(monday.getMorningAvailability());

        tuesday.takeShift(employee, "A");
        assertEquals(employee, tuesday.getListOfEmployee().get(1));
        assertFalse(tuesday.getAfternoonAvailability());

        wednesday.takeShift(employee, "N");
        assertEquals(employee, wednesday.getListOfEmployee().get(2));
        assertFalse(wednesday.getNightAvailability());
    }

    @Test
    public void testRemoveShift() {
        assertTrue(monday.getMorningAvailability());

        monday.takeShift(employee, "M");
        monday.removeShift("M");
        assertNull(monday.getListOfEmployee().get(0));
        assertTrue(monday.getMorningAvailability());

        assertTrue(tuesday.getAfternoonAvailability());

        tuesday.takeShift(employee, "A");
        tuesday.removeShift("A");
        assertNull(tuesday.getListOfEmployee().get(1));
        assertTrue(tuesday.getAfternoonAvailability());

        assertTrue(wednesday.getMorningAvailability());

        wednesday.takeShift(employee, "N");
        wednesday.removeShift("N");
        assertNull(wednesday.getListOfEmployee().get(2));
        assertTrue(wednesday.getNightAvailability());
    }

    @Test
    public void testShowAvailability() throws WeekOutOfBoundsException {

        assertEquals(3, monday.returnAvailability().size());
        assertEquals(("Morning:" + "Available"), monday.returnAvailability().get(0));
        assertEquals(("Afternoon:" + "Available"), monday.returnAvailability().get(1));
        assertEquals(("Night:" + "Available"), monday.returnAvailability().get(2));

        employee.signUpShift(1, "Monday", "A");
        assertEquals(3, monday.returnAvailability().size());
        assertEquals(("Morning:" + "Available"), monday.returnAvailability().get(0));
        assertEquals(("Afternoon:" + "Not Available," + " Harry Is working"), monday.returnAvailability().get(1));
        assertEquals(("Night:" + "Available"), monday.returnAvailability().get(2));


    }

    @Test
    public void testBooleanToString() throws WeekOutOfBoundsException {
        assertEquals("Available", monday.booleanToString(true, 0));

        employee.signUpShift(1, "Monday", "A");
        assertEquals("Not Available, " + "Harry" + " Is working", monday.booleanToString(false, 1));
    }
}
