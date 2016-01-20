package de.schoderer.bookstore.web.model;

import de.schoderer.bookstore.services.UserService;
import de.schoderer.bookstore.utils.ExternalComponents;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by michael on 05.01.2016.
 */

@Category(BeanTest.class)
public class UserRegistrationBeanTest {
    private static UserRegistrationBean bean;

    @Before
    public void setUp() {
        bean = new UserRegistrationBean();
        bean.setExternalComponents(Mockito.mock(ExternalComponents.class));
        bean.setService(Mockito.mock(UserService.class));
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
    public void ifUserIsSavedAndRoleIsAdded() throws NoSuchAlgorithmException {
        String email = "test@test.de";
        String password = "TopSecret";
        bean.setEmail(email);
        bean.setPassword(password);

        bean.doRegisterUser();

        verify(bean.getService(), times(1)).saveUser(bean.getNewUser());
        Assert.assertThat(email, is(bean.getNewUser().getEmail()));
    }

    @Test
    public void ifLogoutWorks() {

        String logut = bean.logout();

        verify(bean.getExternalComponents(), times(1)).invalidateSession();
    }

}
