package au.com.addressBook.config;

import au.com.addressBook.service.AddressBookService;
import au.com.addressBook.service.AddressBookServiceImpl;
import au.com.addressBook.service.ContactService;
import au.com.addressBook.service.ContactServiceImpl;
import au.com.addressBook.ui.Console;
import au.com.addressBook.ui.ConsoleImpl;
import au.com.addressBook.ui.handler.ExceptionHandler;
import au.com.addressBook.ui.handler.ExceptionHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rnadeera
 */
@Configuration
@Import(PersistanceConfig.class)
@EnableJpaRepositories("au.com.addressBook.repository")
public class AppConfig {

    @Bean
    public Console console(){
        return new ConsoleImpl();
    }

    @Bean
    @Transactional
    public AddressBookService addressBookService() {
        return new AddressBookServiceImpl();
    }

    @Bean
    @Transactional
    public ContactService contactService(){
        return new ContactServiceImpl();
    }

    @Bean
    public ExceptionHandler exceptionHandler(){
        return new ExceptionHandlerImpl();
    }
}
