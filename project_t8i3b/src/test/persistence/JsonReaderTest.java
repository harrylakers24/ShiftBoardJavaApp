package persistence;

import exceptions.WeekOutOfBoundsException;
import model.Employee;
import model.Employer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Employer employer = reader.read();
            fail("IOException expected");
        } catch (IOException | WeekOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    void testReaderNoEmployee() {
        JsonReader reader = new JsonReader("./data/testReaderNoEmployees.json");
        try {
            Employer employer = reader.read();
            assertEquals("Bob", employer.getName());
            assertEquals("12345", employer.getPassword());
            assertEquals(0, employer.getListOfEmployees().size());
        } catch (IOException | WeekOutOfBoundsException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderSomeEmployees.json");
        try {
            Employer employer = reader.read();
            assertEquals("Bob", employer.getName());
            List<Employee> employees = employer.getListOfEmployees();
            assertEquals(2, employees.size());
            checkEmployee("Harry", "123", employer, employees.get(0));
            checkEmployee("Hao", "123", employer, employees.get(1));
        } catch (IOException | WeekOutOfBoundsException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoomShiftTaken() {
        JsonReader reader = new JsonReader("./data/testReaderSomeEmployeesShiftTaken.json");
        try {
            Employer employer = reader.read();
            assertEquals("Bob", employer.getName());
            List<Employee> employees = employer.getListOfEmployees();
            assertEquals(2, employees.size());
            checkEmployee("Harry", "123", employer, employees.get(0));
            checkEmployee("Hao", "123", employer, employees.get(1));
        } catch (IOException | WeekOutOfBoundsException e) {
            fail("Couldn't read from file");
        }
    }


}
