package ch.blogpost.service;

import ch.blogpost.data.DataHandler;

import ch.blogpost.model.Kommentar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * services for reading, adding, changing and deleting comments
 */
@Path("comment")
public class KommentarService {


        /**
         * reads a list of all comments
         * @return  comment as JSON
         */
        @GET
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)
        public Response listcomments() {
            List<Kommentar> kommentarList = DataHandler.getInstance().readallKommentar();
            return Response
                    .status(200)
                    .entity(kommentarList)
                    .build();
        }

        /**
         * reads a Comment identified by the uuid
         * @param kommentarUUID
         * @return Kommentar
         */
        @GET
        @Path("read")
        @Produces(MediaType.APPLICATION_JSON)
        public Response readKommentar(
                @QueryParam("uuid") String kommentarUUID
        ) {
            int httpStatus = 200;
            Kommentar kommentar = DataHandler.getInstance().readKommentarbyUUID(kommentarUUID);
            if (kommentar == null) {
                httpStatus = 410;
            }
            return Response
                    .status(httpStatus)
                    .entity(kommentar)
                    .build();
        }
    }


