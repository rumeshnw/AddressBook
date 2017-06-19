package au.com.addressBook.ui;

import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Interface to handle CLI. Implement this to handle different CLI logic
 *
 * @author rnadeera
 */
public interface Console {

    void startConsole();

    static void withScanner(Consumer<Scanner> consumer){
        Scanner scanner = new Scanner(System.in);
        try {
            consumer.accept(scanner);
        } finally {
            scanner.close();
        }
    }
}
