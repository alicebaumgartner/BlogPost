package ch.blogpost.service;


import ch.blogpost.data.DataHandler;


import ch.blogpost.model.Kommentarly;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
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
            List<Kommentarly> kommentarList = DataHandler.readallKommentar();
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
        public Response readComment(
                @QueryParam("uuid") String kommentarUUID
        ) {
            int httpStatus = 200;
            Kommentarly kommentar = DataHandler.readKommentarbyUUID(kommentarUUID);
            if (kommentar == null) {
                httpStatus = 410;
            }
            return Response
                    .status(httpStatus)
                    .entity(kommentar)
                    .build();
        }


        //TODO add as param postUUID
    /**
     * inserts a new comment
     * @param personUUID the uuid of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertComment(
            @Valid @BeanParam Kommentarly kommentar,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID" ) String personUUID

    ) {

        kommentar.setPersonUUID(personUUID);

        DataHandler.insertKommentar(kommentar);
        return Response
                .status(200)
                .entity("")
                .build();
    }


    /**
     * updates a new comment
     * @param personUUID the uuid of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateComment(
            @Valid @BeanParam Kommentarly comment,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {
        int httpStatus = 200;
        Kommentarly oldcomment = DataHandler.readKommentarbyUUID(comment.getKommentarUUID());
        if (oldcomment != null) {
            oldcomment.setKommentar(comment.getKommentar());
            oldcomment.setPerson(comment.getPerson());
            oldcomment.setPersonUUID(personUUID);
            oldcomment.setPostUUID(comment.getPost().getPostUUID());
            oldcomment.setDate(oldcomment.getDate());
            oldcomment.setPost(oldcomment.getPost());

            DataHandler.updateKommentar();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a comment identified by its uuid
     * @param kommentarUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String kommentarUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteKommentar(kommentarUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}


