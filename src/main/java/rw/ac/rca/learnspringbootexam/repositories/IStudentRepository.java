package rw.ac.rca.learnspringbootexam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.learnspringbootexam.models.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, Long> {

}
