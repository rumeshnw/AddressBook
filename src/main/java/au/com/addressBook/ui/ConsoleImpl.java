package au.com.addressBook.ui;

import au.com.addressBook.domain.AddressBook;
import au.com.addressBook.domain.Contact;
import au.com.addressBook.dto.ContactDTO;
import au.com.addressBook.enums.ContactType;
import au.com.addressBook.service.AddressBookService;
import au.com.addressBook.service.ContactService;
import au.com.addressBook.ui.handler.ExceptionHandler;
import au.com.addressBook.ui.util.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.*;
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
        withScanner(scanner -> {
            String option;
            do {
                showMainMenu();
                option = scanner.nextLine();
                try {
                    switch (option){
                        case "1":
                            AddressBook addressBook = createAddressBook(scanner);
                            System.out.println("Address Book Created.... \n" + addressBook.toString() + "\n");
                            break;
                        case "2":
                            Contact contact = addNewContact(scanner);
                            if(contact != null){
                                System.out.println("New Contact Created.... \n" + contact.toString() + "\n");
                            }
                            break;
                        case "3":
                            deleteContact(scanner);
                            break;
                        case "4":
                            showAllContacts(scanner);
                            break;
                        case "5":
                            showUniqueContacts();
                            break;
                        case "99":
                            System.out.println("See you soon. Have a nice day!!!");
                            break;
                        default:
                            System.out.println("Invalid option. Please enter a valid option and try again.");
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
        System.out.println("-- 99. Exit the system");
    }

    private AddressBook createAddressBook(Scanner scanner){
        System.out.println("Please enter an Address Book name");
        String name = trimUserInput(scanner);

        System.out.println("Please enter an Address Book description (this is optional. Hit enter to skip)");
        String description = trimUserInput(scanner);

        return addressBookService.createAddressBook(name, description);
    }

    private Contact addNewContact(Scanner scanner){
        AddressBook selectedAddressBook = selectAddressBook(scanner);

        if(selectedAddressBook != null) {

            System.out.println("Enter contact first name");
            String firstName = trimUserInput(scanner);

            System.out.println("Enter contact last name");
            String lastName = trimUserInput(scanner);

            Map<ContactType, String> contactEntries = new HashMap();
            Arrays.stream(ContactType.values()).forEach(contactType -> {
                System.out.println("Enter contact " + contactType.getName());
                String contactEntry = scanner.nextLine();
                if(StringUtils.hasLength(contactEntry)){
                    contactEntries.put(contactType, contactEntry);
                }
            });

            ContactDTO contactDTO = new ContactDTO.ContactDTOBuilder().setAddressBookId(selectedAddressBook.getId())
                    .setContactFirstName(firstName).setContactLastName(lastName).setContactEntries(contactEntries).build();

            return contactService.addNewContact(contactDTO);
        }

        return null;
    }

    private void deleteContact(Scanner scanner){
        AddressBook selectedAddressBook = selectAddressBook(scanner);
        if(selectedAddressBook == null){
            return;
        }

        List<Contact> contacts = contactService.getAllContacts(selectedAddressBook.getId());

        if(isContactsExist(contacts)){
            showAllContactsOfAddressBook(selectedAddressBook, contacts);
            System.out.println("Please select a contact from above list");

            int option = ConsoleUtil.getValidOptionForCollection(scanner, contacts, "Invalid contact select. Please try again.");
            Contact contact = contacts.get(option - 1);

            contactService.removeContact(contact.getId());
            System.out.println("Contact removed from " + selectedAddressBook.getName() + " Address Book");
        }
    }

    private void showAllContacts(Scanner scanner){
        AddressBook selectedAddressBook = selectAddressBook(scanner);
        if(selectedAddressBook != null){
            showAllContactsOfAddressBook(selectedAddressBook);
        }
    }

    private void showUniqueContacts(){
        Set<Contact> uniqueContacts = contactService.getAllUniqueContacts();
        if(isContactsExist(uniqueContacts)){
            System.out.println("All Contacts in the System\n");
            uniqueContacts.stream().forEach(System.out::println);
        }
    }

    private void showAllContactsOfAddressBook(AddressBook addressBook){
        List<Contact> contacts = contactService.getAllContacts(addressBook.getId());
        if(isContactsExist(contacts)){
            showAllContactsOfAddressBook(addressBook, contacts);
        }
    }

    private void showAllContactsOfAddressBook(AddressBook addressBook, List<Contact> contacts){
        System.out.println("All Contacts of Address Book - " + addressBook.getName());
        AtomicInteger atomicInteger = new AtomicInteger();
        contacts.stream().forEach(contact -> System.out.println(atomicInteger.incrementAndGet() + ". " + contact.toString()));
    }

    private AddressBook selectAddressBook(Scanner scanner){

        List<AddressBook> addressBooks = addressBookService.getAllAddressBooks();

        if(isAddressBooksExist(addressBooks)){
            showAllAddressBooks(addressBooks);
            int option = ConsoleUtil.getValidOptionForCollection(scanner, addressBooks, "Invalid address book select. Please try again.");

            return addressBooks.get(option - 1);
        } else {
            return null;
        }
    }

    private void showAllAddressBooks(List<AddressBook> addressBooks){
        AtomicInteger atomicInteger = new AtomicInteger();
        addressBooks.stream()
                .forEach(addressBook ->
                        System.out.println(atomicInteger.incrementAndGet() + ". " + addressBook.getName()));
    }

    private boolean isAddressBooksExist(List<AddressBook> addressBooks){
        if(addressBooks != null && !addressBooks.isEmpty()) {
           return true;
        }

        System.out.println("No Address Book available. Please create an Address Book to continue");
        return false;
    }

    private boolean isContactsExist(Collection<Contact> contacts){
        if(contacts != null && !contacts.isEmpty()) {
            return true;
        }

        System.out.println("No contacts exists.");
        return false;
    }
}
