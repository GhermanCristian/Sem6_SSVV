package TestStudent;

import domain.Student;
import org.junit.jupiter.api.*;
import repository.StudentXMLRepo;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddStudent {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/studentiTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("""
                    <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                    <inbox>

                    </inbox>""");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        this.studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    public void TestAddStudent_ValidStudent_StudentAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void TestAddStudent_NegativeStudentGroup_ThrowsValidationException() {
        Student newStudent = new Student("1111", "a b", -5, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_StudentGroupTooLarge_ThrowsValidationException() {
        Student newStudent = new Student("1111", "a b", 1000, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_EmptyStudentName_ThrowsValidationException() {
        Student newStudent = new Student("1111", "", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_NullStudentName_ThrowsValidationException() {
        Student newStudent = new Student("1111", null, 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_StudentNameContainsIllegalSymbols_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa11 bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_StudentNameIsJustOneWord_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_EmptyStudentID_ThrowsValidationException() {
        Student newStudent = new Student("", "aa bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_NullStudentID_ThrowsValidationException() {
        Student newStudent = new Student(null, "aa bb", 999, "aa@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_EmptyStudentEmail_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, "");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_NullStudentEmail_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, null);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_WrongStudentEmailLocalPart_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, "@yahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_WrongStudentEmailDomain_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, "aa@yahoo.con");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_DigitInStudentEmailDomain_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, "aa@yahoo1.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }

    @Test
    void TestAddStudent_NoAtSymbolInStudentEmail_ThrowsValidationException() {
        Student newStudent = new Student("1111", "aa bb", 999, "aayahoo.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent));
    }
}
