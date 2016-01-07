package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.db.interfaces.UserPersistence;
import de.schoderer.bookstore.utils.ExternalComponents;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by michael on 05.01.2016.
 */

@Category(BeanTest.class)
public class UserRegistrationBeanTest {
    private static UserRegistrationBean bean;

    @Before
    public void setUp(){
        bean = new UserRegistrationBean();
        bean.setExternalComponents(Mockito.mock(ExternalComponents.class));
        bean.setPersistence(Mockito.mock(UserPersistence.class));
        bean.setSwitcherBean(Mockito.mock(PageSwitcherBean.class));
    }

    @Test
    public void ifSha265Base64EncodingIsWorking() throws NoSuchAlgorithmException {
        Assert.assertEquals("Uy6qvZV0iA2/drm4zACDLCCm7BE9aCKZVQ16bg80XiU=", bean.hash256("Test"));
        Assert.assertEquals("/Rm/EPxSHpDVDTo9+LdFtMhkHzEJb9rTPDwkpzLakkY=", bean.hash256("BookBeanTest"));
        Assert.assertEquals(null, bean.hash256(null));
        Assert.assertEquals(null, bean.hash256(""));
    }

    @Test
    public void ifValidationFailedWhenEmailAlreadyRegisitered(){
        String email = "test@test.de";
        bean.setEmail(email);
        when(bean.getPersistence().checkIfEmailIsAlreadyRegistered(email)).thenReturn(true);

        Assert.assertFalse("Validation should return false for email: "+email, bean.checkIfEmailAlreadyInDatabase());
        verify(bean.getExternalComponents(), times(1)).sendMessage(anyString());
    }
    @Test
    public void ifValidationFailedWhenEmailIsNull(){
        bean.setEmail(null);

        Assert.assertFalse("Validation should return false for email is null ", bean.checkIfEmailAlreadyInDatabase());
        verify(bean.getExternalComponents(), times(1)).sendMessage(anyString());
    }
    @Test
    public void ifCurrentPageIsReturnedIfInputIsInvalid() throws NoSuchAlgorithmException {
        bean.setEmail(null);
        String currentPage = "index.html";
        when(bean.getExternalComponents().getCurrentPage()).thenReturn(currentPage);

        String actual = bean.doRegisterUser();

        Assert.assertEquals("CurrentPage should have been returned", currentPage,actual);
    }
    @Test
    public void ifUserIsSavedAndRoleIsAdded() throws NoSuchAlgorithmException{
        String email = "test@test.de";
        String password = "TopSecret";
        bean.setEmail(email);
        bean.setPassword(password);

       bean.doRegisterUser();

        verify(bean.getPersistence(), times(1)).saveUser(bean.getNewUser());
        Assert.assertThat(email, is(bean.getNewUser().getEmail()));
    }

}
