package au.com.addressBook.service;

import au.com.addressBook.domain.AddressBook;
import au.com.addressBook.repository.AddressBookRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link ContactService}
 *
 * @author rnadeera
 */
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookRespository addressBookRespository;

    @Override
    public AddressBook createAddressBook(String name, String description) {
        return addressBookRespository.save(new AddressBook(name, description));
    }

    @Override
    public AddressBook getAddressBook(Long addressBookId) {

        AddressBook addressBook = addressBookRespository.findOne(addressBookId);
        Assert.notNull(addressBook, "Address Book not exists");

        return addressBook;
    }

    @Override
    public List<AddressBook> getAllAddressBooks() {

        List<AddressBook> allAddressBooks = new ArrayList<>();
        addressBookRespository.findAll().forEach(allAddressBooks::add);

        return allAddressBooks;
    }
}
