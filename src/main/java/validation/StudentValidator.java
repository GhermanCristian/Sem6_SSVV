package validation;

import domain.Student;
import java.util.regex.Pattern;

public class StudentValidator implements Validator<Student> {

    /**
     * Valideaza un student
     * @param entity - studentul pe care il valideaza
     * @throws ValidationException - daca studentul nu e valid
     */
    @Override
    public void validate(Student entity) throws ValidationException {
        if(entity.getID() == null){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getID().equals("")){
            throw new ValidationException("Id incorect!");
        }
        if(entity.getNume() == null){
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getNume().equals("")){
            throw new ValidationException("Nume incorect!");
        }
        if(!Pattern.compile("[a-zA-Z]+ [a-zA-Z]+").matcher(entity.getNume()).matches()) {
            throw new ValidationException("Nume incorect!");
        }
        if(entity.getGrupa() < 0 || entity.getGrupa() > 999) {
            throw new ValidationException("Grupa incorecta!");
        }
        if(entity.getEmail() == null){
            throw new ValidationException("Email incorect!");
        }
        if(entity.getEmail().equals("")){
            throw new ValidationException("Email incorect!");
        }
        if(!Pattern.compile("[a-zA-Z0-9+_.-]+@[a-zA-Z]+.com").matcher(entity.getEmail()).matches()) {
            throw new ValidationException("Email incorect!");
        }
    }
}
