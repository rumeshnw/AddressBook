package au.com.addressBook.domain

import spock.lang.Specification

/**
 * @author rnadeera
 */
class AddressBookSpec extends Specification {

    def "test addToContacts, should add given Contact instance into contacts in AddressBook"(){
        given:
        AddressBook addressBook = new AddressBook()
        Contact contact         = new Contact()

        expect:
        !addressBook.contacts

        when:
        addressBook.addToContacts(contact)

        then:
        addressBook.contacts.size() == 1
    }
}
