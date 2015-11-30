package de.schoderer.bookstore.utils.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * Created by michael on 29.11.15.
 */


@TimeLogging
@Interceptor
public class TimeLoggingInterceptor {
    @AroundInvoke
    public Object profile(InvocationContext ic) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return ic.proceed();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(String.format("Called method %s of class %s took:\t ms", ic.getMethod().getName(), ic.getMethod().getDeclaringClass().getCanonicalName(), end - start));
        }
    }
}
