package pl.example.spring.punkty;

import io.vavr.collection.List;
import org.springframework.stereotype.Service;
import pl.example.spring.punkty.db.StudentRepository;
import pl.example.spring.punkty.db.StudentRow;

import java.util.function.Function;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) { this.repository = repository; }

    List<Student> getStudents(){
        return List.ofAll(this.repository.findAll()).map(getStudentRowStudentFunction());
    }

    Student addStudent(final NewStudent newStudent) {
        StudentRow created = this.repository.save(new StudentRow( newStudent.name, newStudent.number, newStudent.grupa));
        return getStudentRowStudentFunction().apply(created);
    }

    private Function<StudentRow, Student> getStudentRowStudentFunction() { return dbObj-> new Student( dbObj.getId(), dbObj.getName(), dbObj.getNumber(), dbObj.getGrupa()); }
}
