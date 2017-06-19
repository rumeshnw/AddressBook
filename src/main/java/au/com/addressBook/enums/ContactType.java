package au.com.addressBook.enums;

/**
 * @author rnadeera
 */
public enum ContactType {
    MOBILE("Mobile"), WORK("Work"), HOME("Home");

    private String name;

    ContactType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
