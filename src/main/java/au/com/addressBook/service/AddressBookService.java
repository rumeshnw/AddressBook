package au.com.addressBook.service;

import au.com.addressBook.domain.AddressBook;

import java.util.List;

/**
 * Interface for all AddressBook related services
 *
 * @author rnadeera
 */
public interface AddressBookService {

    /**
     * Add new {@link AddressBook} to the system
     *
     * @param name
     * @param description
     * @return
     */
    AddressBook createAddressBook(String name, String description);

    /**
     * Get {@link AddressBook} by ID
     *
     * @param addressBookId key of {@link AddressBook}
     * @return {@link AddressBook} instance
     */
    AddressBook getAddressBook(Long addressBookId);

    /**
     * Get All {@link AddressBook} objects in the system
     *
     * @return List of {@link AddressBook} objects
     */
    List<AddressBook> getAllAddressBooks();
}
