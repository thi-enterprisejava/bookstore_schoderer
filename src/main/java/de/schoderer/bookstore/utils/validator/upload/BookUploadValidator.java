package de.schoderer.bookstore.utils.validator.upload;

import de.schoderer.bookstore.utils.validator.BaseValidator;

import javax.faces.application.FacesMessage;
import javax.faces.validator.FacesValidator;
import javax.servlet.http.Part;
import java.util.List;

/**
 * Created by michael on 31.12.2015.
 */
@FacesValidator("bookUploadValidator")
public class BookUploadValidator extends BaseValidator {
    @Override
    public void validate(Object value, List<FacesMessage> messageList) {
        Part book = (Part) value;
        //if no image is present, skip the other validations
        if (book == null) {
            messageList.add(new FacesMessage(getBundle().getString("error.noBook")));
            return;
        }
        //TODO check if additional validators are needed.. maybe size also a basic class for image and book would be better
    }
}
