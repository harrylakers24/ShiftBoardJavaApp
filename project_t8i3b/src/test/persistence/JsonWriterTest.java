package persistence;

import exceptions.WeekOutOfBoundsException;
import model.Employee;
import model.Employer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Employer employer = new Employer("Bob", "12345");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Employer employer = new Employer("Bob", "12345");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(employer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            employer = reader.read();
            assertEquals("Bob", employer.getName());
            assertEquals("12345", employer.getPassword());
            assertEquals(0, employer.getListOfEmployees().size());
        } catch (IOException | WeekOutOfBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Employer employer = new Employer("Bob", "12345");
            employer.addEmployee(new Employee("Harry", "123", employer));
            employer.addEmployee(new Employee("Hao", "123", employer));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(employer);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            employer = reader.read();
            assertEquals("Bob", employer.getName());
            List<Employee> employees = employer.getListOfEmployees();
            assertEquals(2, employees.size());
            checkEmployee("Harry", "123", employer, employees.get(0));
            checkEmployee("Hao", "123", employer, employees.get(1));

        } catch (IOException | WeekOutOfBoundsException e) {
            fail("Exception should not have been thrown");
        }
    }
}
