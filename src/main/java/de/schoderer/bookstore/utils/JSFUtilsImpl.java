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
public class JSFUtilsImpl implements JSFUtils {
    private static final Logger LOG = LogManager.getLogger(JSFUtilsImpl.class);

    @Override
    public void sendMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }


    public void renderResponse() {
        FacesContext.getCurrentInstance().renderResponse();
    }

    @Override
    public String getResourceBundleStringInCurrentLocal(String parameterName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getViewRoot().getLocale()).getString(parameterName);

    }
}
