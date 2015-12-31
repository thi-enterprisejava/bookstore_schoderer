package de.schoderer.bookstore.utils.validator;

import de.schoderer.bookstore.utils.JSFUtils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by michael on 15.12.2015.
 */
@FacesValidator("pictureUploadValidator")
public class PictureUploadValidator extends BaseValidator {
    private static final int MAX_PICTURE_SIZE = 6 * 1024 * 1024;


    @Override
    public void validate(Object value, ResourceBundle bundle, List<FacesMessage> messageList) {
        Part image = (Part) value;
        if (image.getSize() > MAX_PICTURE_SIZE) {
            messageList.add(new FacesMessage(bundle.getString("error.imageToBig")));
        }
        if (!image.getContentType().startsWith("image/")) {
            messageList.add(new FacesMessage(bundle.getString("error.fileIsNotAnImage")));
        }
    }
}
