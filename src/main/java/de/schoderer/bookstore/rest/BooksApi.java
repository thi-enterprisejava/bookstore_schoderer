package de.schoderer.bookstore.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by michael on 22.10.15.
 */
@Path("books")
public class BooksApi {
    @GET
    @Produces("text/plain")
    public static String greeting(){
        return "Hello to the Rest-API";
    }
}
