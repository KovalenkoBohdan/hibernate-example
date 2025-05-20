import config.ConnectDB;
import entity.Homework;
import entity.Student;
import repository.impl.StudentsRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {
    static StudentsRepository repository = new StudentsRepository(ConnectDB.conect());

    public static void main(String[] args) {

        Student student = new Student("Bohdan", "Kovalenko", "bohdan@gmail.com");
        Homework homework = new Homework();
        homework.setDescription("Hibernate");
        homework.setDeadline(LocalDate.of(2025, Month.MAY, 25));
        homework.setMark(5);
        student.addHomework(homework);
        viewSave(student);
        viewUpdate(student,1l,"bohdan45@gmail.com");
        viewAllStudent();
        viewFindById(1L);
        viewAllStudent();
        viewFindByEmail("bohdan45@gmail.com");
        viewDelete(1L);


    }

    private static void viewSave(Student student) {
        repository.save(student);
        System.out.println("Student save");
    }

    private static void viewFindById(Long id) {
        Student student = repository.findById(id);
        System.out.println(student);
    }

    private static void viewUpdate(Student student, Long id, String correctEmail) {
        student.setId(id);
        student.setEmail(correctEmail);
        Student updateStudent = repository.update(student);
        System.out.println(updateStudent);
    }

    private static void viewDelete(Long id) {
        if (repository.deleteById(id)) {
            System.out.println("Student delete");
        } else System.out.println("Student no delete");
    }

    private static void viewFindByEmail(String email) {
        Student byEmailStudent = repository.findByEmail(email);
        if (byEmailStudent == null) {
            System.out.println("Student with " + email + " exist");
        } else System.out.println(byEmailStudent);
    }
    private static void viewAllStudent(){
        List<Student> allStudents = repository.findAll();
        for (Student student: allStudents)
            System.out.println(student);
    }
}
