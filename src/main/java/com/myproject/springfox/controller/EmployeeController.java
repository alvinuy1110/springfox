package com.myproject.springfox.controller;

import com.myproject.springfox.domain.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/simple")
// To customize the name and description
//@Api(tags="Customer Controller", description = "some desc")
public class EmployeeController {

    @ApiOperation(value = "Find employee by id",  notes = "this is the description")
    @RequestMapping(value = "/employees/{id}", method = RequestMethod.GET)
    public ResponseEntity<Employee> get(@PathVariable(name="id") int id) throws Exception {

        Employee employee = createEmployee(id);
        return ResponseEntity.ok().body(employee);
    }

    @ApiOperation(value = "Find employee",  notes = "this is the description")
    @RequestMapping(value = "/employees/param", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeByParam(
            @RequestHeader("User-Agent") String userAgent,
            @RequestParam(name = "param1", required = true, defaultValue = "abc") String param1
    ,@RequestParam(name = "param2", required = false) String param2
    ) throws Exception {

        Employee employee = createEmployee(1);
        return ResponseEntity.ok().body(employee);
    }

    private Employee createEmployee(int id) throws ParseException {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("John");
        employee.setLastName("Doe");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        employee.setStartDate(formatter.parse("01-05-1998"));

        Location location = new Location();
        location.setRoom("room1");
        location.setFloor("flr1");
        employee.setLocation(location);
        return employee;
    }
}
