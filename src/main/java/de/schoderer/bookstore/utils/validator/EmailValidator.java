package de.schoderer.bookstore.utils.validator;

import de.schoderer.bookstore.services.UserService;
import de.schoderer.bookstore.utils.ExternalComponents;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Michael Schoderer on 15.12.2015.
 */
//http://stackoverflow.com/questions/7572335/how-to-inject-in-facesvalidator-with-ejb-persistencecontext-inject-autow
@ManagedBean
@RequestScoped
public class EmailValidator extends BaseValidator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern EMAIL_COMPILED_PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Inject
    protected ExternalComponents utils;
    @Inject
    protected UserService service;


    @Override
    public void validation(Object value, List<FacesMessage> messageList) {
        if (value == null || "".equals(value)) {
            FacesMessage e = new FacesMessage(utils.getResourceBundleStringInCurrentLocal("error.emptyEmail"));
            e.setSeverity(FacesMessage.SEVERITY_ERROR);
            messageList.add(e);
        } else if (!EMAIL_COMPILED_PATTERN.matcher((String) value).matches()) {
            FacesMessage e = new FacesMessage(utils.getResourceBundleStringInCurrentLocal("error.notValidEmail"));
            e.setSeverity(FacesMessage.SEVERITY_ERROR);
            messageList.add(e);
        } else if (service.checkIfEmailIsAlreadyRegistered(String.valueOf(value))) {
            FacesMessage e = new FacesMessage(utils.getResourceBundleStringInCurrentLocal("error.emailAlreadyRegistered"));
            e.setSeverity(FacesMessage.SEVERITY_ERROR);
            messageList.add(e);
        }
    }
}
