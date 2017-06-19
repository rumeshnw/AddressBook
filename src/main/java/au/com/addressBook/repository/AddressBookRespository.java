package au.com.addressBook.repository;

import au.com.addressBook.domain.AddressBook;
import org.springframework.data.repository.CrudRepository;

/**
 * Data repository to handle CRUD operations for {@link AddressBook}
 *
 * @author rnadeera
 */
public interface AddressBookRespository extends CrudRepository<AddressBook, Long> {


}
