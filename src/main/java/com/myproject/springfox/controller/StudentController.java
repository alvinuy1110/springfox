package com.myproject.springfox.controller;

import com.myproject.springfox.domain.ApiError;
import com.myproject.springfox.domain.CreateStudentRequest;
import com.myproject.springfox.domain.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/simple")
// To customize the name and description
//@Api(tags="Customer Controller", description = "some desc")
public class StudentController {

    @ApiOperation(value = "Find student by id",  notes = "this is the description")
    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> get(@PathVariable(name="id") int id) {

        Student student = createStudent(id);
        return ResponseEntity.ok().body(student);
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAll() {

        Student student = createStudent(1);
        Student student2 = createStudent(2);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);
        return ResponseEntity.ok().body(students);
    }

    @ApiResponses(value = {
            @ApiResponse(code = 422, message = "processing error", response = ApiError.class)})
    @RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<Student> create(@Valid @RequestBody CreateStudentRequest createStudentRequest) {

        Student student = createStudent(1);
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        return ResponseEntity.status(201).body(student);
    }

    @ApiOperation(value = "Find student by id",  notes = "this is the description")
    @RequestMapping(value = "/students/filter/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> getWithParam(@PathVariable(name="id") int id, @RequestParam(value = "sort") String sort) {

        Student student = createStudent(id);
        return ResponseEntity.ok().body(student);
    }


    @RequestMapping(value = "/students", method = RequestMethod.PUT)
    public ResponseEntity update() {

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/student", method = RequestMethod.DELETE)
    public ResponseEntity delete() {

        return ResponseEntity.ok().build();
    }

    private Student createStudent(int id) {
        Student student = new Student();
        student.setId(id);
        student.setFirstName("John");
        student.setLastName("Doe");

        return student;
    }
}
