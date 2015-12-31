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


    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        List<FacesMessage> messages = new ArrayList<>();
        Locale loc = context.getViewRoot().getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), loc);

        validate(value, bundle, messages);
        if (messages.size() > 0) {
            throw new ValidatorException(messages);
        }
    }

    public abstract void validate(Object value, ResourceBundle bundle, List<FacesMessage> messageList);

}
