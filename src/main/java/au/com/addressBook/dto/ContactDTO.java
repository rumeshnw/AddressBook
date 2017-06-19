package au.com.addressBook.dto;

import au.com.addressBook.enums.ContactType;

import java.util.HashMap;
import java.util.Map;

/**
 * Data transfer object for {@link au.com.addressBook.domain.Contact} manipulation
 *
 * @author rnadeera
 */
public class ContactDTO {

    private Long addressBookId;

    private String contactFirstName;

    private String contactLastName;

    private Map<ContactType, String> contactEntries = new HashMap<>();

    private ContactDTO(){

    }

    public static ContactDTO buildContact(Long addressBookId, String firstName, String lastName, Map<ContactType, String> contactEntries){
        ContactDTO contactDTO       = new ContactDTO();
        contactDTO.addressBookId    = addressBookId;
        contactDTO.contactFirstName = firstName;
        contactDTO.contactLastName  = lastName;
        contactDTO.contactEntries   = contactEntries;
        return contactDTO;
    }

    public Long getAddressBookId() {
        return addressBookId;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public Map<ContactType, String> getContactEntries() {
        return contactEntries;
    }
}
