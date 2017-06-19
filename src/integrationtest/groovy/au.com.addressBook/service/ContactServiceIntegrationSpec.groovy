package au.com.addressBook.service

import au.com.addressBook.BaseIntegrationSpec
import au.com.addressBook.domain.AddressBook
import au.com.addressBook.domain.Contact
import au.com.addressBook.domain.ContactEntry
import au.com.addressBook.dto.ContactDTO
import au.com.addressBook.enums.ContactType
import au.com.addressBook.repository.AddressBookRespository
import au.com.addressBook.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

/**
 * @author rnadeera
 */
@Transactional
@Rollback
class ContactServiceIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    ContactService contactService

    @Autowired
    ContactRepository contactRepository

    @Autowired
    AddressBookRespository addressBookRespository

    def "test addNewContact, should return persisted Contact instance"(){
        given:
        AddressBook addressBook = addressBookRespository.save(new AddressBook(name: "DevOps", description: "All DevOps contacts"))
        ContactDTO contactDTO   = ContactDTO.buildContact(addressBook.addressBookId, "John", "Doe", [(ContactType.MOBILE): "0457798446"])

        when:
        Contact contact = contactService.addNewContact(contactDTO)

        then:
        contact.addressBook.addressBookId   == addressBook.addressBookId
        contact.firstName                   == contactDTO.contactFirstName
        contact.lastName                    == contactDTO.contactLastName
        contact.contactEntries.size()       == contactDTO.contactEntries.size()
    }

    def "test removeContact, should remove contact of given contact ID"(){
        given:
        def addressBooks = initAddressBooks()
        Contact contact  = addressBooks[0].contacts.iterator().next()

        expect:
        contactRepository.findOne(contact.contactId)

        when:
        contactService.removeContact(contact.contactId)

        then:
        !contactRepository.findOne(contact.contactId)
    }

    def "test getAllContacts, should return all contacts belong to given AddressBook ID"(){
        given:
        def addressBooks = initAddressBooks()

        when:
        Set<Contact> contacts = contactService.getAllContacts(addressBooks[1].addressBookId)

        then:
        contacts.size() == 3
    }

    def "test getAllContacts, should return unique set of contacts across all address books"(){
        given:
        initAddressBooks()

        expect:
        addressBookRespository.findAll().size() == 2

        when:
        Set<Contact> contacts = contactService.getAllContacts()

        then:
        contacts.size() == 4

        contacts.find { it.firstName == "John" && it.lastName == "Doe" && it.contactEntries.size() == 2}
        contacts.find { it.firstName == "Jane" && it.lastName == "Doe" && it.contactEntries.size() == 1 && it.contactEntries.iterator().next().contactType == ContactType.WORK}
        contacts.find { it.firstName == "Jane" && it.lastName == "Doe" && it.contactEntries.size() == 1 && it.contactEntries.iterator().next().contactType == ContactType.MOBILE}
        contacts.find { it.firstName == "Mike" && it.lastName == "Doe" && it.contactEntries.size() == 2}
    }

    private def initAddressBooks(){

        //Create Manager Address Book
        Contact johnDoeMgr = new Contact(firstName: "John", lastName: "Doe")
        johnDoeMgr.addToContactEntries(new ContactEntry(contactType: ContactType.MOBILE, contactEntryValue: "0424111222"))
        johnDoeMgr.addToContactEntries(new ContactEntry(contactType: ContactType.WORK, contactEntryValue: "1300112233"))

        Contact janeDoeMgr = new Contact(firstName: "Jane", lastName: "Doe")
        janeDoeMgr.addToContactEntries(new ContactEntry(contactType: ContactType.WORK, contactEntryValue: "1300443322"))

        AddressBook mgrAddressBook = new AddressBook(name: "Managers", description: "All Manager contacts")
        mgrAddressBook.addToContacts(janeDoeMgr)
        mgrAddressBook.addToContacts(johnDoeMgr)


        //Create Developer Address Book
        Contact johnDoeDev = new Contact(firstName: "John", lastName: "Doe")
        johnDoeDev.addToContactEntries(new ContactEntry(contactType: ContactType.MOBILE, contactEntryValue: "0424111222"))
        johnDoeDev.addToContactEntries(new ContactEntry(contactType: ContactType.WORK, contactEntryValue: "1300112233"))


        Contact janeDoeDev = new Contact(firstName: "Jane", lastName: "Doe")
        janeDoeDev.addToContactEntries(new ContactEntry(contactType: ContactType.MOBILE, contactEntryValue: "0424443322"))

        Contact mikeDev    = new Contact(firstName: "Mike", lastName: "Doe")
        mikeDev.addToContactEntries(new ContactEntry(contactType: ContactType.MOBILE, contactEntryValue: "0424911119"))
        mikeDev.addToContactEntries(new ContactEntry(contactType: ContactType.WORK, contactEntryValue: "1300345233"))

        AddressBook devAddressBook = new AddressBook(name: "Developer", description: "All developer contacts")
        devAddressBook.addToContacts(johnDoeDev)
        devAddressBook.addToContacts(janeDoeDev)
        devAddressBook.addToContacts(mikeDev)

        //Persist Address Books & Contacts
        def addressBooks = addressBookRespository.save([mgrAddressBook, devAddressBook])
        contactRepository.save([johnDoeMgr, janeDoeMgr, johnDoeDev, janeDoeDev, mikeDev])

        addressBooks
    }

}
