package web.enums;

public enum Amenities {
    BEACH_FRONT("Beach front"),
    SWIMMING_POOL("Swimming pool"),
    FREE_WIFI("Free WiFi"),
    KITCHEN("Kitchen"),
    AIR_CONDITIONING("Air conditioning"),
    WASHING_MACHINE("Washing Machine"),
    PETS_ALLOWED("Pets allowed"),
    HOT_TUB("Hot tub"),
    STREET_PARKING("Street parking"),
    SUITABLE_FOR_CHILDREN("Suitable for children");

    private final String text;

    Amenities(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
