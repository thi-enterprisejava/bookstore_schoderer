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

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<FacesMessage> messages = new ArrayList<>();
        if (bundle == null) {
            Locale loc = context.getViewRoot().getLocale();
            bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), loc);
        }
        validation(value, messages);
        if (!messages.isEmpty()) {
            throw new ValidatorException(messages);
        }
    }

    /**
     * Simplyfied method for validation, when a message is added to the list, the main mehtode throws an Validation Error
     *
     * @param value
     * @param messageList
     */
    public abstract void validation(Object value, List<FacesMessage> messageList);

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

}
