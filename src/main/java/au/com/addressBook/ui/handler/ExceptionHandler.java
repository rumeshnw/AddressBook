package au.com.addressBook.ui.handler;

/**
 * Implement this to handle Exceptions.
 *
 * @author rnadeera
 */

public interface ExceptionHandler {

    /**
     * Translates an Exception to a User readable message.
     *
     * @param e {@link Exception}
     * @return {@link String} a User readable message
     * */
    String translate(Exception e);
}
