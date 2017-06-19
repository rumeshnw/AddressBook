package au.com.addressBook;

import au.com.addressBook.config.AppConfig;
import au.com.addressBook.ui.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bootstrap class to start the application
 *
 * @author rnadeera
 */
public class Application{

    public static void main(String[] args){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        Console console = ctx.getBean(Console.class);
        console.startConsole();
    }
}
