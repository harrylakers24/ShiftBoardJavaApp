package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmployer {
    private Employer manager;
    private Employee worker;
    private Employee worker2;


    @BeforeEach
    public void setUp() {
        manager = new Employer("Bob", "123");
        worker = new Employee("Harry", "123", manager);
        worker2 = new Employee("Bobby", "123", manager);

    }

    @Test
    public void testConstructor() {
        assertEquals("Bob", manager.getName());
        assertEquals("123", manager.getPassword());

        assertEquals(0, manager.getListOfEmployees().size());
        assertEquals(52, manager.getListOfWeeks().size());

    }

    @Test
    public void testGetWeekNoException() {
        try {
            Week testWeek = manager.getWeek(1);
            //expected
            assertEquals(1, testWeek.getNumOfWeek());
        } catch (Exception e) {
            fail("Not expected to throw exception");
        }
    }

    @Test
    public void testGetWeekWithException() {
        try {
            Week testWeek = manager.getWeek(53);
            fail("Expected to throw exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Week testWeek = manager.getWeek(-5);
            fail("Expected to throw exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testAddEmployee() {
        assertEquals(0, manager.getListOfEmployees().size());

        manager.addEmployee(worker);
        assertEquals(1, manager.getListOfEmployees().size());
        assertEquals("Harry", manager.getEmployee("Harry").getName());
        assertEquals("123", manager.getEmployee("Harry").getPassword());

        manager.addEmployee(worker2);
        assertEquals(2, manager.getListOfEmployees().size());
        assertEquals("Bobby", manager.getEmployee("Bobby").getName());
        assertEquals("123", manager.getEmployee("Bobby").getPassword());

    }

    @Test
    public void testRemoveEmployee() {
        assertEquals(0, manager.getListOfEmployees().size());

        manager.addEmployee(worker);
        manager.removeEmployee(worker);
        assertEquals(0, manager.getListOfEmployees().size());

        manager.addEmployee(worker);
        manager.addEmployee(worker2);
        assertEquals(2, manager.getListOfEmployees().size());
        assertEquals("Harry", manager.getEmployee("Harry").getName());
        assertEquals("123", manager.getEmployee("Bobby").getPassword());
        assertEquals("Bobby", manager.getEmployee("Bobby").getName());
        assertEquals("123", manager.getEmployee("Bobby").getPassword());

    }

    @Test
    public void testGetEmployee() {
        manager.addEmployee(worker);

        assertEquals("Harry", manager.getListOfEmployees().get(0).getName());
        assertEquals("123", manager.getListOfEmployees().get(0).getPassword());

        Employee e2 = manager.getEmployee("Bob");
        assertNull(e2);

        manager.addEmployee(worker2);
        assertEquals("Bobby", manager.getListOfEmployees().get(1).getName());
        assertEquals("123", manager.getListOfEmployees().get(1).getPassword());
    }

    @Test
    public void testCheckPassword() {
        assertTrue(manager.checkPassword("123"));
        assertFalse(manager.checkPassword("0123"));
    }

    @Test
    public void testNotifyAllEmployees() {
        assertFalse(manager.notifyAllEmployees("Hello"));

        manager.addEmployee(worker);
        manager.addEmployee(worker2);

        assertTrue(manager.notifyAllEmployees(("Hello")));

        Employee harry = manager.getEmployee("Harry");
        assertEquals("Hello", harry.getNewMessage());

        Employee bobby = manager.getEmployee("Bobby");
        assertEquals("Hello", bobby.getNewMessage());

    }



}
