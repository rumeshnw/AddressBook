package au.com.addressBook.repository;

import au.com.addressBook.domain.AddressBook;
import au.com.addressBook.domain.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Data repository to handle CRUD operations for {@link Contact}
 *
 * @author rnadeera
 */
public interface ContactRepository extends CrudRepository<Contact, Long> {

    List<Contact> getAllByAddressBook(AddressBook addressBook);
}
