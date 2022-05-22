package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Post;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * services for reading, adding, changing and deleting posts
 */
@Path("post")
public class PostService {
    /**
     * reads a list of all posts
     * @return  post as JSON
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listposts() {
        List<Post> postList = DataHandler.getInstance().readAllPost();
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
        Post post = DataHandler.getInstance().readPostbyUUID(postUUID);
        if (post == null) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity(post)
                .build();
    }
}


