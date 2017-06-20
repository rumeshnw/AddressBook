package au.com.addressBook.ui;

import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Interface to handle CLI. Implement this to handle different CLI logic
 *
 * @author rnadeera
 */
public interface Console {

    void startConsole();

    default void withScanner(Consumer<Scanner> consumer){
        Scanner scanner = new Scanner(System.in);
        try {
            consumer.accept(scanner);
        } finally {
            scanner.close();
        }
    }

    default String trimUserInput(Scanner scanner){
        String strValue = StringUtils.trimWhitespace(scanner.nextLine());
        return StringUtils.isEmpty(strValue) ? null : strValue;
    }
}
