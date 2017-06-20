package au.com.addressBook.domain

import spock.lang.Specification

/**
 * Created by rnadeera on 20/6/17.
 */
class ContactSpec extends Specification {

    def "test addToContactEntries, should add given ContactEntry instance into contactEntries of Contact"(){
        given:
        Contact contact             = new Contact()
        ContactEntry contactEntry   = new ContactEntry()

        expect:
        !contact.contactEntries

        when:
        contact.addToContactEntries(contactEntry)

        then:
        contact.contactEntries.size() == 1
    }
}
