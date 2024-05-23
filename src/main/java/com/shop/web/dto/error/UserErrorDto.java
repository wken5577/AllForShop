package com.shop.web.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class UserErrorDto {

    private Map<String, String> errorField;

    public UserErrorDto(List<FieldError> fieldErrors) {
        errorField = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errorField.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
    }

    public UserErrorDto (String field, String msg){
        errorField = new HashMap<>();
        errorField.put(field,msg);
    }

}
