package rw.ac.rca.learnspringbootexam.endpoints;

import jaxb.classes.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import rw.ac.rca.learnspringbootexam.models.Student;
import rw.ac.rca.learnspringbootexam.repositories.IStudentRepository;

@Endpoint
public class StudentsEndPoint {
    private final IStudentRepository studentRepository;

    @Autowired
    public StudentsEndPoint(IStudentRepository repository) {
        this.studentRepository = repository;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "NewStudentDTORequest")
    @ResponsePayload
    public NewStudentDTOResponse create(@RequestPayload NewStudentDTORequest dto) {
        var __student = dto.getStudent();

        var _student = new Student(__student.getFirstName(), __student.getFirstName(), __student.getGender(), __student.getDateOfBirth(), __student.getResident(), __student.getParentsPhoneNumber());

        var student = studentRepository.save(_student);

        var studentDTO = new NewStudentDTOResponse();

        __student.setId(student.getId());

        studentDTO.setStudent(__student);

        return studentDTO;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "GetAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse findAll(@RequestPayload GetAllStudentsRequest request){

        var students = studentRepository.findAll();

        var response = new GetAllStudentsResponse();

        for (Student student: students){
            jaxb.classes.Student _student = new jaxb.classes.Student();
            _student.setId(student.getId());
            _student.setFirstName(student.getFirstName());
            _student.setLastName(student.getLastName());
            _student.setGender(student.getGender());
            _student.setResident(student.getResident());
            _student.setDateOfBirth(student.getDateOfBirth());
            _student.setParentsPhoneNumber(student.getParentsPhoneNumber());

            response.getStudent().add(_student);
        }

        return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "GetStudentDetailsRequest")
    @ResponsePayload
    public GetStudentDetailsResponse findById(@RequestPayload GetStudentDetailsRequest request){
        var _student = studentRepository.findById(request.getId());

        if(_student.isEmpty())
            return new GetStudentDetailsResponse();

        Student student = _student.get();

        GetStudentDetailsResponse response = new GetStudentDetailsResponse();

        var __student = new jaxb.classes.Student();
        __student.setId(student.getId());
        __student.setFirstName(student.getFirstName());
        __student.setLastName(student.getLastName());
        __student.setGender(student.getGender());
        __student.setResident(student.getResident());
        __student.setDateOfBirth(student.getDateOfBirth());
        __student.setParentsPhoneNumber(student.getParentsPhoneNumber());

       response.setStudent(__student);

       return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "DeleteStudentRequest")
    @ResponsePayload
    public DeleteStudentResponse delete(@RequestPayload DeleteStudentRequest request){
        studentRepository.deleteById(request.getId());
        var response = new DeleteStudentResponse();
        response.setMessage("Successfully deleted a message");
        return response;
    }

    @PayloadRoot(namespace = "https://rca.ac.rw/anselme/soap-app", localPart = "UpdateStudentRequest")
    @ResponsePayload
    public UpdateStudentResponse update(@RequestPayload UpdateStudentRequest request){
        var __student = request.getStudent();

        var _student = new Student(__student.getFirstName(), __student.getFirstName(), __student.getGender(), __student.getDateOfBirth(), __student.getResident(), __student.getParentsPhoneNumber());
        _student.setId(__student.getId());

        var student = studentRepository.save(_student);

        var studentDTO = new UpdateStudentResponse();

        __student.setId(student.getId());

        studentDTO.setStudent(__student);

        return studentDTO;
    }


}
