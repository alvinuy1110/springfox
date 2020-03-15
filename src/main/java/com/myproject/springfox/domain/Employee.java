package com.myproject.springfox.domain;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.myproject.springfox.jackson.CustomDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    private int id;

    private String firstName;
    private String lastName;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startDate;

    private Location location;

}
