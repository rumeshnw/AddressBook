package au.com.addressBook.service;

import au.com.addressBook.domain.AddressBook;
import au.com.addressBook.domain.Contact;
import au.com.addressBook.domain.ContactEntry;
import au.com.addressBook.dto.ContactDTO;
import au.com.addressBook.repository.AddressBookRespository;
import au.com.addressBook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ContactService}
 *
 * @author rnadeera
 */
public class ContactServiceImpl implements ContactService {

    @Autowired
    private AddressBookRespository addressBookRespository;

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact addNewContact(ContactDTO contactDTO) {

        Assert.notNull(contactDTO, "Contact information is required");
        AddressBook addressBook = addressBookRespository.findOne(contactDTO.getAddressBookId());
        Assert.notNull(addressBook, "Address book not exists");

        Contact contact = new Contact();
        contact.setAddressBook(addressBook);
        contact.setFirstName(contactDTO.getContactFirstName());
        contact.setLastName(contactDTO.getContactLastName());

        if(contactDTO.getContactEntries() != null) {
            contact.setContactEntries(contactDTO.getContactEntries().entrySet().stream()
                    .map(entry -> new ContactEntry(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toSet()));
        }

        return contactRepository.save(contact);
    }

    @Override
    public void removeContact(Long contactId) {

        Assert.isTrue(contactRepository.exists(contactId), "Contact not exists");
        contactRepository.delete(contactId);
    }

    @Override
    public Set<Contact> getAllContacts(Long addressBookId) {

        AddressBook addressBook = addressBookRespository.findOne(addressBookId);
        Assert.notNull(addressBook, "Address Book not exists");

        return contactRepository.getAllByAddressBook(addressBook);
    }

    @Override
    public Set<Contact> getAllContacts() {

        Set<Contact> allContacts = new HashSet<>();
        contactRepository.findAll().forEach(allContacts::add);

        return allContacts;
    }
}
