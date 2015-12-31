package de.schoderer.bookstore.utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * Created by michael on 22.12.15.
 */

public interface JSFUtils {
    /**
     * Adds a message to the FacesContext
     * @param message
     */

    void sendMessage(String message);

    /**
     * Renders the response after the current phase
     */
    void renderResponse();

    /**
     * Gets the localized Translation for a given parameter from the messagebundle
     * @param parameterName
     * @return localized tranzlation
     */
    String getResourceBundleStringInCurrentLocal(String parameterName);
}
