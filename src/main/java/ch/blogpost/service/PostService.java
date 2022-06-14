package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Personly;
import ch.blogpost.model.Postly;
import ch.blogpost.model.Postly;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * services for reading, adding, changing and deleting posts
 */
@Path("postly")
public class PostService {
    /**
     * reads a list of all posts
     * @return  post as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listposts() {
        List<Postly> postList = DataHandler.readAllPostly();
        return Response
                .status(200)
                .entity(postList)
                .build();
    }

    /**
     * reads a Post identified by the uuid
     * @param postUUID
     * @return post
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPost(
            @QueryParam("uuid") String postUUID
    ) {
        int httpStatus = 200;
        Postly post =  DataHandler.readPostlybyUUID(postUUID);
        if (post == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(post)
                .build();
    }


    /**
     * inserts a new post
     * @param personUUID the uuid of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPost(
            @Valid @BeanParam Postly post,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID
    ) {

       post.setAutorUUID(personUUID);

        DataHandler.insertPostly(post);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a new post
     * @param postUUID the uuid of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePerson(
            @Valid @BeanParam Postly postly,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("postUUID") String postUUID
    ) {
        int httpStatus = 200;
        Postly oldpost = DataHandler.readPostlybyUUID(postly.getPostUUID());
        if (oldpost != null) {
            oldpost.setAutor(postly.getAutor());
            oldpost.setAutorUUID(postly.getAutor().getPersonUUID());
            oldpost.setText(postly.getText());
            oldpost.setLesezeit(postly.getLesezeit());
            oldpost.setKommentare(postly.getKommentare());
            oldpost.setDatum(postly.getDatum());



            DataHandler.updatePostly();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a post identified by its uuid
     * @param postUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePost(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String postUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deletePostly(postUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}
