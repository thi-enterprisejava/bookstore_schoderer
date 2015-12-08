package de.schoderer.bookstore.utils.interceptor;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by michael on 29.11.15.
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeLogging {
}
