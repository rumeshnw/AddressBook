package au.com.addressBook.domain;

import au.com.addressBook.util.Validator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rnadeera
 */
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    @Column(nullable = false)
    @NotNull(message = "Contact first name is required")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Contact last name is required")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addressBookId", nullable = false)
    private AddressBook addressBook;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contact")
    private Set<ContactEntry> contactEntries;

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public Set<ContactEntry> getContactEntries() {
        return contactEntries;
    }

    public void setContactEntries(Set<ContactEntry> contactEntries) {
        this.contactEntries = contactEntries;
    }

    @PrePersist
    public void validate(){
        Validator.validate(this);
    }

    public void addToContactEntries(ContactEntry contactEntry){
        if(contactEntries == null){
            contactEntries = new HashSet();
        }
        contactEntry.setContact(this);
        contactEntries.add(contactEntry);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return new EqualsBuilder()
                .append(this.firstName, contact.firstName)
                .append(this.lastName, contact.lastName)
                .append(this.contactEntries, contact.contactEntries)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31)
                .append(firstName)
                .append(lastName)
                .append(contactEntries)
                .toHashCode();
    }
}
