package de.schoderer.bookstore.utils;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.ResourceBundle;

/**
 * Created by Michael Schoderer on 22.12.15.
 */
@Named
@RequestScoped
public class ExternalComponentsImpl implements ExternalComponents {

    @Override
    public FacesMessage sendFacesMessage(String message) {
        FacesMessage facesMessage = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return facesMessage;
    }

    @Override
    public String getResourceBundleStringInCurrentLocal(String parameterName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getViewRoot().getLocale()).getString(parameterName);
    }

    @Override
    public String getCurrentPage() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }

    @Override
    public void invalidateSession() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
}
