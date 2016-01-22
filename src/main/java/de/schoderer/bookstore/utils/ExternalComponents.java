package de.schoderer.bookstore.utils;

import javax.faces.application.FacesMessage;
import java.io.Serializable;

/**
 * Created by Michael Schoderer on 22.12.15.
 */

public interface ExternalComponents extends Serializable {
    /**
     * Adds a message to the FacesContext
     *
     * @param message
     */
    FacesMessage sendFacesMessage(String message);

    /**
     * Gets the localized Translation for a given parameter from the messagebundle
     *
     * @param parameterName
     * @return localized tranzlation
     */
    String getResourceBundleStringInCurrentLocal(String parameterName);

    /**
     * Get the filename of the current JSF-Page
     *
     * @return filename
     */
    String getCurrentPage();

    /**
     * Invalidates the Current Seesion (logout)
     */
    void invalidateSession();
}
