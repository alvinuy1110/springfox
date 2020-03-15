package com.myproject.springfox.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@ApiModel(value = "create student", description = "desc")
public class CreateStudentRequest {

    @ApiModelProperty(value = "first name", example = "John")
    @NotEmpty
    private String firstName;
    @NotEmpty

    @Size(max = 100)
    private String lastName;
}
