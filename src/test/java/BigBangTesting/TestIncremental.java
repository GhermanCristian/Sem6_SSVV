package BigBangTesting;

import curent.Curent;
import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIncremental {
    private Service service;
    private static final String STUDENT_REPOSITORY_PATH = "fisiere/studentiTest.xml";
    private static final String TEMA_REPOSITORY_PATH = "fisiere/assignmentTest.xml";
    private static final String NOTA_REPOSITORY_PATH = "fisiere/gradeTest.xml";

    private StudentXMLRepo studentXMLRepo;
    private TemaXMLRepo temaXMLRepo;
    private NotaXMLRepo notaXMLRepo;

    private static void createSingleXML(String filePath) {
        File xml = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + "<inbox>" + "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRepositories() {
        this.studentXMLRepo = new StudentXMLRepo(STUDENT_REPOSITORY_PATH);
        this.temaXMLRepo = new TemaXMLRepo(TEMA_REPOSITORY_PATH);
        this.notaXMLRepo = new NotaXMLRepo(NOTA_REPOSITORY_PATH);
    }

    @BeforeEach
    void setup() {
        createSingleXML(STUDENT_REPOSITORY_PATH);
        createSingleXML(TEMA_REPOSITORY_PATH);
        createSingleXML(NOTA_REPOSITORY_PATH);
        this.initRepositories();
        this.service = new Service(this.studentXMLRepo, new StudentValidator(),
                this.temaXMLRepo, new TemaValidator(),
                this.notaXMLRepo, new NotaValidator(this.studentXMLRepo, this.temaXMLRepo));
    }

    private static void deleteXML() {
        new File(STUDENT_REPOSITORY_PATH).delete();
        new File(TEMA_REPOSITORY_PATH).delete();
        new File(NOTA_REPOSITORY_PATH).delete();
    }

    @AfterEach
    void clearXMLFiles() {
        deleteXML();
    }

    @Test
    public void TestAddStudent_ValidStudent_StudentAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
    }

    @Test
    public void TestAddStudentAndAddAssignment_ValidStudentAndAssignment_BothAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        this.service.addStudent(newStudent);
        assertEquals(this.service.getAllStudenti().iterator().next(), newStudent);
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    @Test
    public void TestAddStudentAndAddAssignmentAndAddNota_ValidGradeAndStudentAndAssignment_AllAddedCorrectly() {
        Student newStudent = new Student("1111", "a b", 999, "aa@yahoo.com");
        this.service.addStudent(newStudent);
        Tema newTema = new Tema("1", "a", 14, 1);
        this.service.addTema(newTema);
        Nota nota = new Nota("1111", "1111", "1", 10, Curent.getStartDate().plusWeeks(3));
        this.service.addNota(nota, "bun");
        assertEquals(this.service.getAllNote().iterator().next(), nota);
    }
}
