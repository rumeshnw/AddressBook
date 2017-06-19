package au.com.addressBook.ui;

import au.com.addressBook.domain.AddressBook;
import au.com.addressBook.domain.Contact;
import au.com.addressBook.service.AddressBookService;
import au.com.addressBook.service.ContactService;
import au.com.addressBook.ui.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @See Console
 * @author rnadeera
 */
public class ConsoleImpl implements Console {

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Override
    public void startConsole() {
        showBanner();
        Console.withScanner(scanner -> {
            String option;
            do {
                showMainMenu();
                option = scanner.nextLine();
                try {
                    switch (option){
                        case "1":
                            AddressBook addressBook = createAddressBook(scanner);
                            System.out.println("Address Book Created.... " + addressBook.toString());
                            showMainMenu();
                            break;
                        case "2":
                            Contact contact = addNewContact(scanner);
                            System.out.println("Invalid option. Please enter a valid option and try again.");
                            break;
                        case "3":
                            System.out.println("Invalid option. Please enter a valid option and try again.");
                            break;
                        case "4":
                            System.out.println("Invalid option. Please enter a valid option and try again.");
                            break;
                        case "5":
                            System.out.println("Invalid option. Please enter a valid option and try again.");
                            break;
                        case "99":
                            System.out.println("See you soon. Have a nice day!!!");
                            break;
                        default:
                            System.out.println("Invalid option. Please enter a valid option and try again.");
                            showMainMenu();
                            break;
                    }
                } catch (Exception e){
                    System.out.println(exceptionHandler.translate(e));
                }
            } while (!"99".equals(option));
        });
    }

    private void showBanner(){
        System.out.println("##################################################################");
        System.out.println("################### Welcome to Address Book ######################");
        System.out.println("##################################################################");
    }

    private void showMainMenu(){
        System.out.println("Please select any option to proceed..");
        System.out.println("-- 1. Create new Address Book");
        System.out.println("-- 2. Add new Contact");
        System.out.println("-- 3. Delete existing Contact");
        System.out.println("-- 4. Show all Contacts of an Address Book");
        System.out.println("-- 5. Show all unique Contacts of all Address Books");
        System.out.println("-- 99. Create new Address Book");
    }

    private AddressBook createAddressBook(Scanner scanner){
//        System.out.println("Please enter an Address Book name");
//        String name = scanner.nextLine();
//        Assert.isTrue(StringUtils.hasLength(name), "Address Book name is required");
//
//        System.out.println("Please enter an Address Book description (this is optional. Hit enter to skip)");
//        String description = scanner.nextLine();

        return addressBookService.createAddressBook(null, "test");
    }

    private Contact addNewContact(Scanner scanner){
        //Show Address books
        List<AddressBook> addressBooks = addressBookService.getAllAddressBooks();
        showAllAddressBooks(addressBooks);

        AddressBook selectedAddressBook = selectAddressBook(addressBooks, scanner);

        System.out.println("Enter contact first name");
        String firstName = scanner.nextLine();
        Assert.isTrue(StringUtils.hasLength(firstName), "Contact first name is required");

        System.out.println("Enter contact last name");
        String lastName = scanner.nextLine();
        Assert.isTrue(StringUtils.hasLength(lastName), "Contact last name is required");

        return null;
    }

    private void showAllAddressBooks(List<AddressBook> addressBooks){
        if(addressBooks != null && !addressBooks.isEmpty()){
            AtomicInteger atomicInteger = new AtomicInteger();
            addressBooks.stream()
                        .forEach(addressBook ->
                                System.out.print(atomicInteger.incrementAndGet() + ". " + addressBook.getName())
                        );
        } else {
            System.out.println("No Address Book available. Please create one to continue");
        }
    }

    private AddressBook selectAddressBook(List<AddressBook> addressBooks, Scanner scanner){
        boolean invalidInput = true;
        int option;
        do{
            System.out.println("Please select an Address Book");
            option = scanner.nextInt();

            if(option > addressBooks.size()){
                System.out.println("Invalid address book select. Please try again.");
            } else {
                invalidInput = false;
            }
        } while (invalidInput);

        return addressBooks.get(option - 1);
    }
}
