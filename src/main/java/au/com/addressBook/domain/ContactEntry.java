package au.com.addressBook.domain;

import au.com.addressBook.enums.ContactType;
import au.com.addressBook.util.Validator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author rnadeera
 */
@Entity
public class ContactEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "contact type is required")
    private ContactType contactType;

    @Column(nullable = false)
    @NotNull(message = "Contact number is required")
    private String contactEntryValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    public ContactEntry() {
    }

    public ContactEntry(ContactType contactType, String contactEntryValue) {
        this.contactType = contactType;
        this.contactEntryValue = contactEntryValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getContactEntryValue() {
        return contactEntryValue;
    }

    public void setContactEntryValue(String contactEntryValue) {
        this.contactEntryValue = contactEntryValue;
    }



    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @PrePersist
    public void validate(){
        Validator.validate(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactEntry contactEntry = (ContactEntry) o;

        return new EqualsBuilder()
                .append(this.contactType, contactEntry.contactType)
                .append(this.contactEntryValue, contactEntry.contactEntryValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(9, 31)
                .append(contactType)
                .append(contactEntryValue)
                .toHashCode();
    }

    @Override
    public String toString() {
        return contactType.getName() + " - " + contactEntryValue;
    }
}
