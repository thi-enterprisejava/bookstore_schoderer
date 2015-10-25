package de.schoderer.bookstore.rest;

import de.schoderer.bookstore.db.BookPersistence;
import de.schoderer.bookstore.domain.Book;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by michael on 22.10.15.
 */
@Path("books")
public class BooksRestApi {
    @Inject
    private BookPersistence persistence;


    @GET
    @Produces("application/json")
    public List<Book> getAllBooks(){
        return persistence.fetchAllBooks();
    }

    @GET
    @Produces("application/json")
    @Path("tag/{tag}")
    public List<Book> getBooksByTag(@PathParam("tag") String tag){
        return persistence.fetchAllBooksByTag(tag);
    }
    @GET
    @Produces("application/json")
    @Path("title/{title}")
    public List<Book> getBooksByTitle(@PathParam("title") String title){
        return persistence.fetchAllBooksByTitle(title);
    }

    @POST
    @Consumes("application/json")
    public Response updateBook(Book book){
        persistence.updateBook(book);
        return Response.accepted().build();
    }

    @PUT
    @Consumes("application/json")
    public Response saveBook(Book book){
        persistence.saveBook(book);
        return Response.accepted().build();
    }
    @DELETE
    @Consumes("application/json")
    public Response deleteBook(Book book){
        persistence.removeBook(book);
        return Response.accepted().build();
    }

}
