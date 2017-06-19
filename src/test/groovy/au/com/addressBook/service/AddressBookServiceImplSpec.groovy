package au.com.addressBook.service

import au.com.addressBook.domain.AddressBook
import au.com.addressBook.repository.AddressBookRespository
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author rnadeera
 */
class AddressBookServiceImplSpec extends Specification {

    AddressBookService addressBookService

    def setup(){
        addressBookService = new AddressBookServiceImpl()
    }

    def cleanup(){

    }

    @Unroll
    def "test createAddressBook, should throw an exception when address book name is #addressBookName"(){
        given:
        addressBookService.addressBookRespository = Mock(AddressBookRespository){
            1 * save(_) >> { AddressBook addressBook ->
                if(addressBook.name == null || addressBook.name == ""){
                    throw new IllegalArgumentException("Address Book name is required")
                }
            }
        }

        when:
        addressBookService.createAddressBook(null, null)

        then:
        def e       = thrown(IllegalArgumentException)
        e.message   == "Address Book name is required"

        where:
        _ | addressBookName
        _ | null
        _ | ""
    }

    def "test createAddressBook, should return newly created address book when required parameters and provided"(){
        given:
        String addressBookName  = "Managers"
        String description      = "Contacts of all managers"

        addressBookService.addressBookRespository = Mock(AddressBookRespository){
            1 * save(_) >> { AddressBook addressBook -> addressBook }
        }

        when:
        AddressBook addressBook = addressBookService.createAddressBook(addressBookName, description)

        then:
        addressBook.name        == addressBookName
        addressBook.description == description
    }

    def "test getAddressBook, should throw an exception when AddressBook is not exists for the given ID"(){
        given:
        addressBookService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> null
        }

        when:
        addressBookService.getAddressBook(12)

        then:
        def e       = thrown(Exception)
        e.message   == "Address Book not exists"
    }

    def "test getAddressBook, should return AddressBook instance when AddressBook is exists for the given ID"(){
        given:
        AddressBook addressBook = new AddressBook("Development", "Contacts of Dev Team")

        and:
        addressBookService.addressBookRespository = Mock(AddressBookRespository){
            1 * findOne(_ as Long) >> addressBook
        }

        when:
        AddressBook createdAddressBook = addressBookService.getAddressBook(12)

        then:
        createdAddressBook.name         == addressBook.name
        createdAddressBook.description  == addressBook.description
    }

    @Unroll
    def "test getAllAddressBooks, should return AddressBook list with #size"(){
        given:
        addressBookService.addressBookRespository = Mock(AddressBookRespository){
            1 * findAll() >> addressBkList
        }

        when:
        List<AddressBook> addressBooks = addressBookService.getAllAddressBooks()

        then:
        addressBooks.size() == size

        where:
        size    | addressBkList
        0       | []
        2       | [new AddressBook("IT", null), new AddressBook("HR", null)]
    }
}
