package au.com.addressBook.enums;

/**
 * @author rnadeera
 */
public enum ContactType {
    MOBILE("Mobile"), WORK("Work"), HOME("Home");

    String name;

    ContactType(String name){
        this.name = name;
    }
}
