package de.schoderer.bookstore.utils;

/**
 * Created by michael on 22.12.15.
 */

public interface ExternalComponents {
    /**
     * Adds a message to the FacesContext
     *
     * @param message
     */

    void sendMessage(String message);

    /**
     * Gets the localized Translation for a given parameter from the messagebundle
     *
     * @param parameterName
     * @return localized tranzlation
     */
    String getResourceBundleStringInCurrentLocal(String parameterName);

    String getCurrentPage();
}