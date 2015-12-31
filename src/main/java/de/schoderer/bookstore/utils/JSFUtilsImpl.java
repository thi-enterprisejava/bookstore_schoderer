package de.schoderer.bookstore.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Created by michael on 22.12.15.
 */
public class JSFUtilsImpl implements JSFUtils {


    @Override
    public void sendMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(message));
    }


    public void renderResponse() {
        FacesContext.getCurrentInstance().renderResponse();
    }
}
