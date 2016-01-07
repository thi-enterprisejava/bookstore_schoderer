package de.schoderer.bookstore.testUtils.validator;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by Michael Schoderer on 07.01.2016.
 */
public class StubRessourceBundle extends ResourceBundle {
    @Override
    protected Object handleGetObject(String key) {
        return "";
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
