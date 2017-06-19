package au.com.addressBook.service

import au.com.addressBook.BaseIntegrationSpec
import au.com.addressBook.domain.AddressBook
import au.com.addressBook.repository.AddressBookRespository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional

/**
 * @author randeera
 */
@Transactional
@Rollback
class AddressBookServiceIntegrationSpec extends BaseIntegrationSpec {

    @Autowired
    AddressBookService addressBookService

    @Autowired
    AddressBookRespository addressBookRespository


    def "test createAddressBook, should persist new AddressBook when required parameters are provided"(){
        when:
        AddressBook addressBook = addressBookService.createAddressBook("Finance", "All finance contacts")

        then:
        addressBookRespository.findOne(addressBook.id)
    }

    def "test getAddressBook, should return persisted AddressBook instance when AddressBook available for given ID"(){
        given:
        AddressBook addressBook = addressBookRespository.save(new AddressBook("IT", "My IT Guys"))

        when:
        AddressBook response = addressBookService.getAddressBook(addressBook.id)

        then:
        response.id  == addressBook.id
        response.name           == addressBook.name
    }

    def "test getAddressBook, should return all persisted AddressBook instances"(){
        given:
        3.times {
            addressBookRespository.save(new AddressBook("Dept $it", "Desc Dept $it"))
        }

        when:
        List<AddressBook> response = addressBookService.getAllAddressBooks()

        then:
        response.size() == 3
    }
}
