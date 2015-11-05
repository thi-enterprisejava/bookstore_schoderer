/**
 * Created by schod on 05.11.2015.
 */



    var pathname = window.location.pathname;
    var index  = "";
    if(pathname.indexOf("index.xhtml")!=-1){
        index = "#navIndex";
    } else if(pathname.indexOf("listResults")!=-1){
        index = "#navList";
    }

    $(index).addClass("active");

