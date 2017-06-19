package au.com.addressBook.util;

import au.com.addressBook.domain.AddressBook;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rnadeera on 18/6/17.
 */
public class Validator {

    public static void validate(Object obj){
        Set<ConstraintViolation<Object>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(obj);
        if(violations != null && !violations.isEmpty()){
            throw new IllegalArgumentException(violations.stream()
                    .map(violation -> violation.getMessage())
                    .collect(Collectors.joining("\n")));
        }
    }
}
