# Shiftboard Application

## Harry Zhu | CPSC 210 | 2020W

The application that I am going to create for my personal project is an employee scheduling system that can
be used by any company. Employers would be able to add or remove employees, while employees have the ability of 
signing up for shifts. I was inspired to create an application like this ever since 
my first job. During my first job, I was constantly struggling to find a balance in going to work as well as focusing 
on my academic studies. My former employer did not make this easy as the only system that was implemented to sign up for 
shifts was through emails and calls. There were countless times of when I missed an email or call, which 
resulted in me not being able to sign up for shifts fast enough. Some other problems with this system I imagined
from my employer's perspective would be how difficult it was to find a last minute replacement for someone
that called in sick. They would have to call each person 1 by 1 with no guarantee of them answering. The 
application that I am creating will attempt to solve these issues and improve communication between employers
and employees. 





## User Stories
Some basic features that the application can do is:
- As an employer, I want to be able to add or remove employees to my work force (adding multiple employees to employer's 
work force)
- As an employer, I want to notify my employees of an upcoming shift or other important information
- As an employer, I want to be able to save my list of currently employed workers
- As an employer, I want to be able to load my list of currently employed workers
- As an employer, I want to be able to change an employee's password or name
- As an employee, I want to be able to take or drop a shift 
- As an employee, I want to check shift availability
- As an employee, I want to able to check recent message from my employer

## Phase 4: Task 2
The option that I chose to implement was a checked exception called WeekOutOfBoundsException. This exception effects the 
following classes: Employer, Employee, JsonReader, GUI. The exception is first thrown from the Employer class, and keeps 
on getting thrown from each class all the way up to the GUI class, where it is handled in the form of an error message 
that makes it clear the user made an error while inputting a week value. Order of exception handling 
Employer -> Employee -> JsonReader -> GUI. Because of the addition of the exception, the classes involved are all part
of a robust design.

## Phase 4: Task 3
Looking at my UML class diagram, the biggest issue that I can identify is the amount of responsibilities that the GUI
class holds. Currently, all the frames that are present in my program are all created in the single GUI class, making
it hold too much responsibility. Another class I would refactor would be to improve the 
bi-directional association between an employee and employer. Currently in my program, I only have one manager object,  
meaning there is no need for each class to call methods on the other class since for example an employee would not need 
to switch to another employer. After implementing a way for there to be multiple employers, I would design an interface 
called Worker which both the Employer and Employee class implements. There is a lot of similarity in function in both
classes, and with the addition of the Worker interface, it will make the code much easier to read.

Changes to be made:
- Set up two additional GUI related classes called EmployeeFrame and EmployerFrame, and rename the main GUI class as
MainFrame (home screen) to improve cohesion
- Everytime the frame needs to be switched following log in, a new instance of the above mentioned frame will be 
instantiated
- Add multi manager support in the application by adding methods such as changeManager in the employee class and 
add/removeEmployee in the employer class, creating a need for each class in the association to call methods on the 
other class
- Refactoring a new interface called Worker which both Employee and Employer class implements to reduce redundancy 
and make the code easier to read







