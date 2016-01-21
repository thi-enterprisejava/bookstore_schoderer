package de.schoderer.bookstore.testUtils;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import java.util.concurrent.Callable;

/**
 * Created by Michael Schoderer on 20.01.2016.
 */
@Stateless
@RunAs("user")
@PermitAll
public class AuthenticatedUser {

    public void execute(Callable statement) throws Exception {
        statement.call();
    }

    public void run(Runnable runnable) throws Exception {
        runnable.run();
    }

}
