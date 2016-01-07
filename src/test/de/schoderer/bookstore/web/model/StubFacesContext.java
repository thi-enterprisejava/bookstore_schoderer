package de.schoderer.bookstore.web.model;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import java.util.Iterator;

/**
 * Created by Michael Schoderer on 07.01.2016.
 */
public class StubFacesContext extends FacesContext {

    private UIViewRoot viewRoot;

    @Override
    public Application getApplication() {
        return null;
    }

    @Override
    public Iterator<String> getClientIdsWithMessages() {
        return null;
    }

    @Override
    public ExternalContext getExternalContext() {
        return null;
    }

    @Override
    public FacesMessage.Severity getMaximumSeverity() {
        return null;
    }

    @Override
    public Iterator<FacesMessage> getMessages() {
        return null;
    }

    @Override
    public Iterator<FacesMessage> getMessages(String clientId) {
        return null;
    }

    @Override
    public RenderKit getRenderKit() {
        return null;
    }

    @Override
    public boolean getRenderResponse() {
        return false;
    }

    @Override
    public boolean getResponseComplete() {
        return false;
    }

    @Override
    public ResponseStream getResponseStream() {
        return null;
    }

    @Override
    public void setResponseStream(ResponseStream responseStream) {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }

    @Override
    public ResponseWriter getResponseWriter() {
        return null;
    }

    @Override
    public void setResponseWriter(ResponseWriter responseWriter) {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }

    @Override
    public UIViewRoot getViewRoot() {

        return viewRoot;
    }

    @Override
    public void setViewRoot(UIViewRoot root) {
        this.viewRoot = root;
    }

    @Override
    public void addMessage(String clientId, FacesMessage message) {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }

    @Override
    public void release() {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }

    @Override
    public void renderResponse() {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }

    @Override
    public void responseComplete() {
        throw new UnsupportedOperationException("UnimplementedOperation");
    }
}
