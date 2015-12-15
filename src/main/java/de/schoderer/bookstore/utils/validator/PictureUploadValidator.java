package de.schoderer.bookstore.utils.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 15.12.2015.
 */
@FacesValidator("pictureUploadValidator")
public class PictureUploadValidator implements Validator {
    private static int MAX_PICTURE_SIZE= 6* 1024 * 1024;
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        if(null == o){
            return;
        }
        List<FacesMessage> messages = new ArrayList<>();
        Part image = (Part) o;
        if(image.getSize() > MAX_PICTURE_SIZE){
            messages.add(new FacesMessage("Image: File is to big"));
        }
        if(!image.getContentType().startsWith("image/")){
            messages.add(new FacesMessage("Image: File is not an image"));
        }
        if(messages.size()>0){
            throw new ValidatorException(messages);
        }
    }
}
