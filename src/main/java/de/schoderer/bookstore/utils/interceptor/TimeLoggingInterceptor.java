package de.schoderer.bookstore.utils.interceptor;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by michael on 29.11.15.
 */


@TimeLogging
@Interceptor
public class TimeLoggingInterceptor {
    private static final Logger LOG = Logger.getLogger(TimeLoggingInterceptor.class);

    @AroundInvoke
    public Object profile(InvocationContext ic) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return ic.proceed();
        } finally {
            long end = System.currentTimeMillis();
           LOG.info(String.format("Called method %s of class %s took:\t %s ms", ic.getMethod().getName(), ic.getMethod().getDeclaringClass().getCanonicalName(), end - start));
        }
    }
}
