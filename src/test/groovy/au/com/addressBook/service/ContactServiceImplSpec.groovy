package au.com.addressBook.service

import au.com.addressBook.domain.AddressBook
import au.com.addressBook.domain.Contact
import au.com.addressBook.dto.ContactDTO
import au.com.addressBook.enums.ContactType
import au.com.addressBook.repository.AddressBookRespository
import au.com.addressBook.repository.ContactRepository
import spock.lang.Specification

/**
 * @author rnadeera
 */
class ContactServiceImplSpec extends Specification {

    ContactService contactService

    def setup(){
        contactService = new ContactServiceImpl()
    }

    def cleanup(){

    }

    def "test addNewContact, should throw an exception when ContactDTO is null"(){
        when:
        contactService.addNewContact(null)

        then:
        def e       = thrown(Exception)
        e.message   == "Contact information is required"
    }

    def "test addNewContact, should throw an exception when AddressBook is not exists for the given Address Book ID"(){
        given:
        contactService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> null
        }

        and:
        ContactDTO contactDTO = new ContactDTO.ContactDTOBuilder().setAddressBookId(1).setContactFirstName("John").setContactLastName("Doe")
                                            .setContactEntries([(ContactType.MOBILE): "0424788766", (ContactType.WORK): "1300123243"]).build()

        when:
        contactService.addNewContact(contactDTO)

        then:
        def e     = thrown(Exception)
        e.message == "Address book not exists"
    }

    def "test addNewContact, should throw an exception when Contact domain validation failed"(){
        given:
        AddressBook addressBook = new AddressBook("Development", "Contacts of Dev Team")

        and:
        contactService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> addressBook
        }

        and:
        contactService.contactRepository = Mock(ContactRepository){
            1 * save(_) >> {Contact contact -> throw new IllegalArgumentException("invalid argument exists")}
        }

        and:
        ContactDTO contactDTO = new ContactDTO.ContactDTOBuilder().setAddressBookId(1).setContactFirstName("John").setContactLastName("Doe")
                                            .setContactEntries([(ContactType.MOBILE): "0424788766", (ContactType.WORK): "1300123243"]).build()

        when:
        contactService.addNewContact(contactDTO)

        then:
        def e       = thrown(Exception)
        e.message   == "invalid argument exists"
    }

    def "test addNewContact, should return newly created Contact when ContactDTO instance has valid parameters"(){
        given:
        AddressBook addressBook = new AddressBook("Development", "Contacts of Dev Team")

        and:
        contactService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> addressBook
        }

        and:
        contactService.contactRepository = Mock(ContactRepository){
            1 * save(_) >> {Contact contact -> contact}
        }

        and:
        ContactDTO contactDTO = new ContactDTO.ContactDTOBuilder().setAddressBookId(1).setContactFirstName("John").setContactLastName("Doe")
                                        .setContactEntries([(ContactType.MOBILE): "0424788766", (ContactType.WORK): "1300123243"]).build()

        when:
        Contact contact = contactService.addNewContact(contactDTO)

        then:
        contact.firstName == contactDTO.contactFirstName
        contact.lastName  == contactDTO.contactLastName

        contact.contactEntries.size() == contactDTO.contactEntries.size()
    }

    def "test removeContact, should throw an exception when Contact not exists for the given ID"(){
        given:
        contactService.contactRepository = Mock(ContactRepository){
            1 * exists(_ as Long) >> false

            0 * delete(_ as Long) >> {return}
        }

        when:
        contactService.removeContact(11)

        then:
        def e       = thrown(Exception)
        e.message   == "Contact not exists"
    }

    def "test removeContact, should delete Contact for the given contact ID"(){
        given:
        contactService.contactRepository = Mock(ContactRepository){
            1 * exists(_ as Long) >> true

            1 * delete(_ as Long) >> {return}
        }

        when:
        contactService.removeContact(11)

        then:
        notThrown(Exception)
    }

    def "test getAllContacts, should throw an exception when AddressBook is not exists for the given Address Book ID"(){
        given:
        contactService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> null
        }

        when:
        contactService.getAllContacts(1)

        then:
        def e       = thrown(Exception)
        e.message   == "Address Book not exists"
    }

    def "test getAllContacts, should return Contacts belong to given Address Book ID"(){
        given:
        contactService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> new AddressBook("Dev", "Contacts of Dev Team")
        }

        contactService.contactRepository = Mock(ContactRepository){
            1 * getAllByAddressBook(_ as AddressBook) >> { AddressBook addressBook -> [new Contact(firstName: "John", lastName: "Doe"), new Contact(firstName: "Jane", lastName: "Doe")] }
        }

        when:
        Set<Contact> contacts = contactService.getAllContacts(1)

        then:
        contacts.size() == 2
    }

    def "test getAllContacts, should return unique set of contacts across all address books"(){
        given:
        contactService.contactRepository = Mock(ContactRepository){
            1 * findAll() >> [new Contact(firstName: "John", lastName: "Doe"), new Contact(firstName: "Jane", lastName: "Doe")]
        }

        when:
        Set<Contact> contacts = contactService.getAllContacts()

        then:
        contacts.size() == 2
    }

}
