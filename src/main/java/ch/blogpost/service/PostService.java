package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Postly;

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
 * services for reading, adding, changing and deleting posts
 */
@Path("postly")
public class PostService {
    /**
     * reads a list of all posts
     * @return  post as JSON
     */
    @RolesAllowed({"admin", "user"})
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
    @RolesAllowed({"admin", "user"})
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPost(
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPost(
            @Valid @BeanParam Postly post,

            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID,
            
            @FormParam("text") String text,
            @Pattern(regexp = "[^0-9]*")
            @FormParam("readingtime") Integer readingtime,
            @Pattern(regexp = "^[0-9]{2}.[0-9]{2}.[0-9]{4}")
            @FormParam("postdate") String postdate
            
    ) throws ParseException {

       post.setAutorUUID(personUUID);
       if(!text.isEmpty()){
           post.setText(text);
       }
        if(readingtime != null){
            post.setReadingtime(readingtime);
        }
        if(!postdate.isEmpty()){
            Date date=new SimpleDateFormat("dd.MM.yyyy").parse(postdate);
            post.setPostdate(date);
        }


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
    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePost(
            @Valid @BeanParam Postly postly,
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("postUUID") String postUUID,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID,
            @Size(min=1, max = 300)
            @FormParam("text") String text,
            @Pattern(regexp = "[^0-9]*")
            @FormParam("readingtime") Integer readingtime,
            @Pattern(regexp = "^[0-9]{2}.[0-9]{2}.[0-9]{4}")
            @FormParam("postdate") String postdate
    ) throws ParseException {
        int httpStatus = 200;
        Postly oldpost = DataHandler.readPostlybyUUID(postly.getPostUUID());
        if (oldpost != null) {
            if(!personUUID.isEmpty()){
                oldpost.setAutorUUID(personUUID);
            }else{
                oldpost.setAuthor(postly.getAuthor());
                oldpost.setAutorUUID(postly.getAuthor().getPersonUUID());
            }
            if(readingtime != null){
                oldpost.setReadingtime(postly.getReadingtime());
            }else{
                oldpost.setReadingtime(readingtime);
            }
            if(!text.isEmpty()){

            }else {
                oldpost.setText(postly.getText());

            }
            if(!postdate.isEmpty()){
                Date date=new SimpleDateFormat("dd.MM.yyyy").parse(postdate);

                oldpost.setPostdate(date);
            }else {
                oldpost.setPostdate(postly.getPostdate());

            }


            oldpost.setComments(postly.getComments());



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
    @RolesAllowed({"admin"})
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
