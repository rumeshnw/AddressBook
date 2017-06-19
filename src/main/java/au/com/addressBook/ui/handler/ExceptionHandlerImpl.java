package au.com.addressBook.ui.handler;


import org.apache.log4j.Logger;
import org.springframework.dao.InvalidDataAccessApiUsageException;

/**
 * @author rnadeera
 * @see ExceptionHandler
 *
 */
public class ExceptionHandlerImpl implements ExceptionHandler {

    final static Logger logger = Logger.getLogger(ExceptionHandlerImpl.class);

    @Override
    public String translate(Exception e) {
        String message;
        if (e instanceof IllegalArgumentException) {
            message = e.getMessage();
        } else if(e instanceof InvalidDataAccessApiUsageException){
            message = e.getCause().getMessage();
        } else {
            logger.error("Something went wrong!", e);
            message = "Something went wrong.  Please contact Administrator.";
        }
        return message;
    }
}
