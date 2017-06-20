package au.com.addressBook.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class holds all validation related utility methods
 *
 * @author rnadeera
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
