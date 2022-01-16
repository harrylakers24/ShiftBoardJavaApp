package model;

import exceptions.WeekOutOfBoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestEmployee {
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
        assertEquals("Harry", worker.getName());
        assertEquals("123", worker.getPassword());
        assertEquals(manager, worker.getEmployer());
        assertEquals(1, worker.getMessages().size());

    }


    @Test
    public void testGetNewMessage() {
        assertEquals("No messages yet", worker.getNewMessage());

        manager.addEmployee(worker);
        manager.addEmployee(worker2);
        manager.notifyAllEmployees("Hi");
        assertEquals("Hi", worker.getNewMessage());
        assertEquals("Hi", worker2.getNewMessage());

    }

    @Test
    public void testAddMessage() {
        assertEquals(1, worker.getMessages().size());

        worker.addMessage("hi");
        assertEquals(2, worker.getMessages().size());
        assertEquals("hi", worker.getMessages().get(1));
    }

    @Test
    public void testTakeShift() throws WeekOutOfBoundsException {
        assertTrue(worker.signUpShift(1, "Monday", "M"));
        assertFalse(worker2.signUpShift(1, "Monday", "M"));
        assertTrue(worker2.signUpShift(1, "Monday", "A"));
        worker.dropShift(1, "Monday", "M");
        assertTrue(worker2.signUpShift(1, "Monday", "M"));

        assertTrue(worker.signUpShift(1, "Tuesday", "A"));
        assertFalse(worker2.signUpShift(1, "Tuesday", "A"));
        assertTrue(worker2.signUpShift(1, "Tuesday", "N"));
        worker.dropShift(1, "Tuesday", "A");
        assertTrue(worker2.signUpShift(1, "Tuesday", "A"));

        assertTrue(worker.signUpShift(1, "Wednesday", "N"));
        assertFalse(worker2.signUpShift(1, "Wednesday", "N"));
        assertTrue(worker2.signUpShift(1, "Wednesday", "M"));
        worker.dropShift(1, "Wednesday", "N");
        assertTrue(worker2.signUpShift(1, "Wednesday", "N"));

        assertFalse(worker.signUpShift(1, "Thursday", "S"));
    }

    @Test
    public void testDropShift() throws WeekOutOfBoundsException {
        assertFalse(worker.dropShift(1, "Monday", "M"));
        worker.signUpShift(1, "Monday", "M");
        assertTrue(worker.dropShift(1, "Monday", "M"));
        worker.signUpShift(1, "Monday", "M");
        assertFalse(worker2.dropShift(1, "Monday", "M"));

        assertFalse(worker.dropShift(1, "Tuesday", "A"));
        worker.signUpShift(1, "Tuesday", "A");
        assertTrue(worker.dropShift(1, "Tuesday", "A"));
        worker.signUpShift(1, "Tuesday", "A");
        assertFalse(worker2.dropShift(1, "Tuesday", "A"));

        assertFalse(worker.dropShift(1, "Wednesday", "N"));
        worker.signUpShift(1, "Wednesday", "N");
        assertTrue(worker.dropShift(1, "Wednesday", "N"));
        worker.signUpShift(1, "Wednesday", "N");
        assertFalse(worker2.dropShift(1, "Wednesday", "N"));

        assertFalse(worker.dropShift(1, "Thursday", "D"));
    }

    @Test
    public void testToString() {
        assertEquals("Name:" + worker.getName() + " Password:" + worker.getPassword(), worker.toString());
    }

    @Test
    public void testSetPassword() {
        worker.setPassword("156");
        assertEquals("156", worker.getPassword());
    }

    @Test
    public void testSetName() {
        worker.setName("Hao Han");
        assertEquals("Hao Han", worker.getName());
    }
}