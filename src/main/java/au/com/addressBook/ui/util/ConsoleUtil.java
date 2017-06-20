package au.com.addressBook.ui.util;

import java.util.Collection;
import java.util.Scanner;

/**
 * Class holds all utility methods related to Console
 *
 * @author rnadeera
 */
public class ConsoleUtil {

    public static Integer getValidOptionForCollection(Scanner scanner, Collection<? extends Object> collection, String invalidMessage){
        boolean invalidInput = true;
        int option;
        do{
            option = Integer.parseInt(scanner.nextLine());

            if(option > collection.size()){
                System.out.println(invalidMessage);
            } else {
                invalidInput = false;
            }
        } while (invalidInput);

        return option;
    }
}
