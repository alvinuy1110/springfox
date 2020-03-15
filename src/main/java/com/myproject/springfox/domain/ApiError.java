package com.myproject.springfox.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class ApiError {
    private String description;
}
