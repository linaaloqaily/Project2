package com.example.Project2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor @Data
public class Comment {

    @NotEmpty(message = "id is required")
    @Min(value = 3 ,message = "id should be 3 number")
    private String id;

    @NotEmpty(message = "userid is required")
    @Min(value = 5 ,message = "userid should be 5 number")
    private String userid;

    @NotEmpty(message = "message is required")
    @Min(value = 6 ,message = "message should be 6 character")
    private String message;

    @NotNull(message = "rate is required")
    @Size(min = 1,max = 5,message = "rate should be number between 1 - 5")
    private Integer rate;
}
