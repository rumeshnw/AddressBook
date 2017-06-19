package au.com.addressBook.dto;

import au.com.addressBook.enums.ContactType;
import org.springframework.util.Assert;

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

    public void validate(){
        Assert.notNull(addressBookId, "Address Book is required");
        Assert.notNull(contactFirstName, "Contact first name is required");
        Assert.notNull(contactLastName, "Contact last name is required");
        Assert.notEmpty(contactEntries, "At least one contact is required");
    }

    public static class ContactDTOBuilder {
        private Long addressBookId;

        private String contactFirstName;

        private String contactLastName;

        private Map<ContactType, String> contactEntries = new HashMap<>();

        public ContactDTOBuilder setAddressBookId(Long addressBookId) {
            this.addressBookId = addressBookId;
            return this;
        }

        public ContactDTOBuilder setContactFirstName(String contactFirstName) {
            this.contactFirstName = contactFirstName;
            return this;
        }

        public ContactDTOBuilder setContactLastName(String contactLastName) {
            this.contactLastName = contactLastName;
            return this;
        }

        public ContactDTOBuilder setContactEntries(Map<ContactType, String> contactEntries) {
            this.contactEntries = contactEntries;
            return this;
        }

        public ContactDTO build(){
            ContactDTO contactDTO = new ContactDTO();
            contactDTO.contactFirstName = this.contactFirstName;
            contactDTO.contactLastName  = this.contactLastName;
            contactDTO.addressBookId    = this.addressBookId;
            contactDTO.contactEntries   = this.contactEntries;
            return contactDTO;
        }
    }
}
