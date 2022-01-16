package persistence;

import model.Employee;
import model.Employer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkEmployee(String name, String password, Employer employer, Employee employee) {
        assertEquals(name, employee.getName());
        assertEquals(password, employee.getPassword());
        assertEquals(employer, employee.getEmployer());
    }
}
