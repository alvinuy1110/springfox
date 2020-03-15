package com.myproject.springfox.controller;

import com.myproject.springfox.domain.Student;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/api/simple")
// To customize the name and description
//@Api(tags="Customer Controller", description = "some desc")
public class StudentController {

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable(name="id") int id) {

        Student student = createStudent(id);
        return ResponseEntity.ok().body(student);
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity create() {

        return ResponseEntity.status(201).build();
    }

    @RequestMapping(value = "/student", method = RequestMethod.PUT)
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
