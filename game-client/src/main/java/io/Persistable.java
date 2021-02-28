package io;

public interface Persistable {

    String DELIMETER = ":";
    String SECTION_DELIMETER = System.lineSeparator() + "###" + System.lineSeparator();
    String LIST_DELIMETER = ", ";
    String COLUMN_DELIMETER = System.lineSeparator();

    String serialize();
    void applySerializedData(String serializedData);
}
