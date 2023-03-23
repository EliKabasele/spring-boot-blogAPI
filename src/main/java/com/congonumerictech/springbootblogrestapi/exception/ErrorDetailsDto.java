package com.congonumerictech.springbootblogrestapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorDetailsDto {
    private Date date;
    private String message;
    private String description;
}
