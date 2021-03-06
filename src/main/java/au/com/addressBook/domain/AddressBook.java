package au.com.addressBook.domain;

import au.com.addressBook.util.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rnadeera
 */
@Entity
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Address book name is required")
    private String name;

    @Column()
    private String description;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "addressBook", cascade = CascadeType.REMOVE)
    Set<Contact> contacts;

    public AddressBook() {
    }

    public AddressBook(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    @PrePersist
    public void validate(){
        Validator.validate(this);
    }

    public void addToContacts(Contact contact){
        if(contacts == null){
            contacts = new HashSet();
        }
        contact.setAddressBook(this);
        contacts.add(contact);
    }

    @Override
    public String toString() {
        return "AddressBook{" + "id=" + id + ", name='" + name + "', description='" +
                description + "'}";
    }
}
