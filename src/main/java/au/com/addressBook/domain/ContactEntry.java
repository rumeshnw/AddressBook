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
    private Long contactEntryId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "contact type is required")
    private ContactType contactType;

    @Column(nullable = false)
    @NotNull(message = "Contact number is required")
    private String contactEntryValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contactId", nullable = false)
    private Contact contact;

    public ContactEntry() {
    }

    public ContactEntry(ContactType contactType, String contactEntryValue) {
        this.contactType = contactType;
        this.contactEntryValue = contactEntryValue;
    }

    public Long getContactEntryId() {
        return contactEntryId;
    }

    public void setContactEntryId(Long contactEntryId) {
        this.contactEntryId = contactEntryId;
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
}
