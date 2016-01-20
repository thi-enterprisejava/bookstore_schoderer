package de.schoderer.bookstore.utils.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by michael on 31.12.2015.
 */
public abstract class BaseValidator implements Validator, Serializable {
    private ResourceBundle bundle;
    private UIComponent component;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<FacesMessage> messages = new ArrayList<>();
        if (bundle == null) {
            Locale loc = context.getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), loc);
        }
        this.component = component;
        validation(value, messages);
        if (!messages.isEmpty()) {
            throw new ValidatorException(messages);
        }
    }

    public abstract void validation(Object value, List<FacesMessage> messageList);

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public UIComponent getComponent() {
        return component;
    }
}
