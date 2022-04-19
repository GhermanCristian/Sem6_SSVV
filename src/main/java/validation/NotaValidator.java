package validation;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.*;
import static curent.Curent.calculeazaSPredare;

public class NotaValidator implements Validator<Nota> {
    private final StudentXMLRepo studentFileRepository;
    private final TemaXMLRepo temaFileRepository;

    /**
     * Class constructor
     *
     * @param studentFileRepository - repository student
     * @param temaFileRepository    - repository tema
     */
    public NotaValidator(StudentXMLRepo studentFileRepository, TemaXMLRepo temaFileRepository) {
        this.studentFileRepository = studentFileRepository;
        this.temaFileRepository = temaFileRepository;
    }

    /**
     * Valideaza o nota
     *
     * @param nota - nota pe care o valideaza
     * @throws ValidationException daca nota nu e valida
     */
    @Override
    public void validate(Nota nota) throws ValidationException {
        Student student = studentFileRepository.findOne(nota.getIdStudent());
        if (student == null) {
            throw new ValidationException("Studentul nu exista!");
        }
        Tema tema = temaFileRepository.findOne(nota.getIdTema());
        if (tema == null) {
            throw new ValidationException("Tema nu exista!");
        }
        double notaC = nota.getNota();
        if (notaC > 10.00 || notaC < 0.00) {
            throw new ValidationException("Valoarea notei nu este corecta!");
        }

        int predare = calculeazaSPredare(nota.getData());
        if (predare < 1)
            throw new ValidationException("Saptamana de predare nu poate fi inainte de saptamana de inceput!");
        if (predare > 14)
            throw new ValidationException("Studentul nu poate preda tema dupa finalul semestrului!");
        if (predare > tema.getDeadline()) {
            if (predare - tema.getDeadline() == 1) {
                nota.setNota(nota.getNota() - 2.5);
            }
            else {
                throw new ValidationException("Studentul nu mai poate preda aceasta tema!");
            }
        }
    }
}
