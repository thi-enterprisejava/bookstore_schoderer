package de.schoderer.bookstore.utils.validator;

import de.schoderer.bookstore.utils.JSFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by michael on 15.12.2015.
 */
@FacesValidator("emailValidator")
public class EmailValidator extends BaseValidator {
    private final static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final static Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Inject
    protected JSFUtils utils;


    @Override
    public void validate(Object value, List<FacesMessage> messageList) {
        if (value == null || "".equals(value)) {
            FacesMessage e = new FacesMessage(getBundle().getString("error.emptyEmail"));
            e.setSeverity(FacesMessage.SEVERITY_ERROR);
            messageList.add(e);
        } else if (!EMAIL_COMPILED_PATTERN.matcher((String) value).matches()) {
            FacesMessage e = new FacesMessage(getBundle().getString("error.notValidEmail"));
            e.setSeverity(FacesMessage.SEVERITY_ERROR);
            messageList.add(e);
        }
    }
}
