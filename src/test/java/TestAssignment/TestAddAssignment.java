package TestAssignment;

import domain.Tema;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaXMLRepo;
import service.Service;
import validation.TemaValidator;
import validation.ValidationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestAddAssignment {
    private TemaXMLRepo temaXMLRepo;
    private TemaValidator temaValidator;
    private Service service;

    @BeforeAll
    static void createXML() {
        File xml = new File("fisiere/assignmentTest.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"+
                    "<inbox>"+

                    "</inbox>");
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        this.temaXMLRepo = new TemaXMLRepo("fisiere/assignmentTest.xml");
        this.temaValidator = new TemaValidator();
        this.service = new Service(null, null,this.temaXMLRepo, this.temaValidator, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/assignmentTest.xml").delete();
    }

    @Test
    void TestAddAssignment_ValidAssignment_AssignmentAddedCorrectly() {
        Tema newTema = new Tema("1", "a", 1, 1);
        this.service.addTema(newTema);
        assertEquals(this.service.getAllTeme().iterator().next(), newTema);
    }

    @Test
    void TestAddAssignment_EmptyID_ThrowsValidationException() {
        Tema newTema = new Tema("", "a", 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_NullID_ThrowsValidationException() {
        Tema newTema = new Tema(null, "a", 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_EmptyDescription_ThrowsValidationException() {
        Tema newTema = new Tema("1", "", 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_NullDescription_ThrowsValidationException() {
        Tema newTema = new Tema("1", null, 0, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_DeadlineTooLarge_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 15, 1);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_ReceivedTooSmall_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 0, 0);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }

    @Test
    void TestAddAssignment_ReceivedTooLarge_ThrowsValidationException() {
        Tema newTema = new Tema("1", "a", 0, 15);
        assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
    }
}
