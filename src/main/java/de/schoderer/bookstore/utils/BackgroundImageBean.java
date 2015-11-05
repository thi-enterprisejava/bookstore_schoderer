package de.schoderer.bookstore.utils;

import javax.ejb.Singleton;
import javax.inject.Named;
import java.util.Random;

/**
 * Created by schod on 23.10.2015.
 */
@Named
@Singleton
public class BackgroundImageBean {
    private static final String BACKGROUND_IMAGE_TEMPLATE = "background-image: url(img/book_%NUMBER%.jpg) no-repeat center center fixed;";
    private static final Random random = new Random();


    public String getRandomImage() {
        return BACKGROUND_IMAGE_TEMPLATE.replace("%NUMBER%", String.valueOf(random.nextInt(4)));
    }

}
