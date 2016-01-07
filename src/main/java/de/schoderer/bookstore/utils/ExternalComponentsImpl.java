package de.schoderer.bookstore.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private static final Logger LOG = LogManager.getLogger(ExternalComponentsImpl.class);

    @Override
    public void sendMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
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
}
