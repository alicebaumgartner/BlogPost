package ch.blogpost.service;


import ch.blogpost.data.DataHandler;


import ch.blogpost.model.Commentarly;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * services for reading, adding, changing and deleting comments
 */
@Path("comment")
public class CommentService {


        /**
         * reads a list of all comments
         * @return  comment as JSON
         */
        @RolesAllowed({"admin", "user"})
        @GET
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)
        public Response listcomments() {
            List<Commentarly> kommentarList = DataHandler.readallKommentar();
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
        @RolesAllowed({"admin", "user"})
        @GET
        @Path("read")
        @Produces(MediaType.APPLICATION_JSON)
        public Response readComment(
                @NotEmpty
                @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                @QueryParam("uuid") String kommentarUUID

        ) {
            int httpStatus = 200;
            Commentarly kommentar = DataHandler.readKommentarbyUUID(kommentarUUID);
            if (kommentar == null) {
                httpStatus = 410;
            }
            return Response
                    .status(httpStatus)
                    .entity(kommentar)
                    .build();
        }

    /**
     * inserts a new comment
     * @param personUUID the uuid of the person
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertComment(
            @Valid @BeanParam Commentarly kommentar,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID" ) String personUUID,
            @Size(min=1, max = 300)
            @FormParam("commentcontent" ) String commentcontent,
            @Pattern(regexp = "^[0-9]{2}.[0-9]{2}.[0-9]{4}")
            @FormParam("date" ) String date


    ) throws ParseException {

        kommentar.setPersonUUID(personUUID);
        kommentar.setKommentar(commentcontent);
        Date datefinal=new SimpleDateFormat("dd.MM.yyyy").parse(date);
        kommentar.setDate(datefinal);
        DataHandler.insertKommentar(kommentar);
        return Response
                .status(200)
                .entity("")
                .build();
    }


    /**
     * updates a new comment
     * @param postuuid the uuid of the post
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateComment(
            @Valid @BeanParam Commentarly comment,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("commentuuid") String commentUUID,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("postuuid") String postuuid,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID,
            @Size(min=1, max = 300)
            @FormParam("commentcontent" ) String commentcontent,
            @Pattern(regexp = "^[0-9]{2}.[0-9]{2}.[0-9]{4}")
            @FormParam("date" ) String date
    ) throws ParseException {
        int httpStatus = 200;
        Commentarly oldcomment = DataHandler.readKommentarbyUUID(commentUUID);
        if (oldcomment != null) {
            if(!commentcontent.isEmpty()){
                oldcomment.setKommentar(commentcontent);
            }else {
                oldcomment.setKommentar(comment.getKommentar());
            }

            oldcomment.setPerson(comment.getPerson());
            if(!personUUID.isEmpty()){
                oldcomment.setPersonUUID(personUUID);

            }else {
                oldcomment.setPersonUUID(comment.getPersonUUID());
            }
            oldcomment.setPostUUID(comment.getPost().getPostUUID());
            if(!date.isEmpty()){
                Date datefinal=new SimpleDateFormat("dd.MM.yyyy").parse(date);
                oldcomment.setDate(datefinal);
            }else{
                oldcomment.setDate(oldcomment.getDate());
            }

            oldcomment.setPost(oldcomment.getPost());

            DataHandler.updateComment();
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
     * @param commentUUID  the key
     * @return  Response
     */
    @RolesAllowed({"admin"})
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteComment(
            @NotEmpty
            @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}")
            @QueryParam("uuid") String commentUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deleteComment(commentUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

}


