package de.schoderer.bookstore.utils.validator.upload;

import de.schoderer.bookstore.utils.validator.BaseValidator;

import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.servlet.http.Part;
import java.util.List;

/**
 * Created by michael on 15.12.2015.
 */
@FacesValidator("pictureUploadValidator")
public class PictureUploadValidator extends BaseValidator {
    private static final int MAX_PICTURE_SIZE = 6 * 1024 * 1024;


    @Override
    public void validate(Object value, List<FacesMessage> messageList) {
        Part image = (Part) value;
        //if no image is present, skip the other validations
        if (image == null) {
            messageList.add(new FacesMessage(getBundle().getString("error.noImage")));
            return;
        }
        if (image.getSize() > MAX_PICTURE_SIZE) {
            messageList.add(new FacesMessage(getBundle().getString("error.imageToBig")));
        }
        if (!image.getContentType().startsWith("image/")) {
            messageList.add(new FacesMessage(getBundle().getString("error.fileIsNotAnImage")));
        }
    }
}
