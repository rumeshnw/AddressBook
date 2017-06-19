package au.com.addressBook.domain;

import au.com.addressBook.enums.ContactType;
import au.com.addressBook.util.Validator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author rnadeera
 */
@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Contact first name is required")
    private String firstName;

    @Column(nullable = false)
    @NotNull(message = "Contact last name is required")
    private String lastName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "addressBook_id", nullable = false)
    private AddressBook addressBook;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "contact")
    private Set<ContactEntry> contactEntries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String toString() {
        return "Contact ::: " +
                " firstName= " + firstName + "\n" +
                " lastName= " + lastName + "\n" +
                " addressBook=" + addressBook.getName() + "\n" +
                " contactEntries=" + contactEntries;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 31)
                .append(firstName)
                .append(lastName)
                .append(contactEntries)
                .toHashCode();
    }

    public static class ContactBuilder {
        private String firstName;
        private String lastName;
        private AddressBook addressBook;
        private Set<ContactEntry> contactEntries;

        public ContactBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public ContactBuilder setAddressBook(AddressBook addressBook) {
            this.addressBook = addressBook;
            return this;
        }

        public ContactBuilder setContactEntries(Map<ContactType, String> contactEntries) {
            if(contactEntries != null) {
                this.contactEntries = contactEntries.entrySet().stream()
                        .map(entry -> new ContactEntry(entry.getKey(), entry.getValue()))
                        .collect(Collectors.toSet());
            }
            return this;
        }

        public Contact build(){
            Contact contact = new Contact();
            contact.firstName       = this.firstName;
            contact.lastName        = this.lastName;
            contact.contactEntries  = this.contactEntries;
            contact.addressBook     = this.addressBook;
            return contact;
        }
    }
}
