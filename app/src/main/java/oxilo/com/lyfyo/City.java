package oxilo.com.lyfyo;

/**
 * Created by nikk on 29/6/17.
 */

public enum City {
    MUMBAI("Mumbai", 0),
    PUNE("Pune", 1),
    BHUNESWAR("Bhuneswar", 2),
    THANE("Thane", 3),
    NAVIMUMBAI("Navimumbai", 4),
    CHENNAI("Chennai", 5);
    private String stringValue;
    private int intValue;
    private City(String toString, int value) {
        stringValue = toString;
        intValue = value;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
