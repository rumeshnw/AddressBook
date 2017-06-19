package au.com.addressBook.service;

import au.com.addressBook.domain.Contact;
import au.com.addressBook.dto.ContactDTO;

import java.util.List;
import java.util.Set;

/**
 * Interface for all Contact related service
 *
 * @author rnadeera
 */
public interface ContactService {

    /**
     * Add new {@link Contact} to the system
     *
     * @param contactDTO {@link ContactDTO}
     * @return newly created {@link Contact}
     */
    Contact addNewContact(ContactDTO contactDTO);

    /**
     * Delete {@link Contact} from an {@link au.com.addressBook.domain.AddressBook}
     *
     * @param contactId key of {@link Contact}
     */
    void removeContact(Long contactId);

    /**
     * Get all {@link Contact} list belong to an {@link au.com.addressBook.domain.AddressBook}
     *
     * @param addressBookId key of {@link au.com.addressBook.domain.AddressBook}
     * @return Set of {@link Contact} objects
     */
    List<Contact> getAllContacts(Long addressBookId);

    /**
     * Get unique set of all {@link Contact} objects across all {@link au.com.addressBook.domain.AddressBook} instances
     *
     * @return unique set of {@link Contact} objects
     */
    Set<Contact> getAllContacts();
}
