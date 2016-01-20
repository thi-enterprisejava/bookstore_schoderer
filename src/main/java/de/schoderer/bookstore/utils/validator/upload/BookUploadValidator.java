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

    /**
     * Checks if the uploaded book is not empty, else produces an error message.
     * No check for given format here, because basically a book can be a txt, pdf, mobi, jpeg...
     *
     * @param value
     * @param messageList
     */
    @Override
    public void validation(Object value, List<FacesMessage> messageList) {
        Part book = (Part) value;
        //if no image is present, skip the other validations
        if (book == null) {
            messageList.add(new FacesMessage(getBundle().getString("error.noBook")));
            return;
        }
    }
}
