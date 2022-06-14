package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Personly;


import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;


/**
 * services for reading, adding, changing and deleting persons
 */
@Path("person")
public class PersonService {
  /**
         * reads a list of all comments
         * @return  person as JSON
         */
        @GET
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)
        public Response listpersons() {
            List<Personly> personList = DataHandler.readAllPersons();
            Response build = Response
                    .status(200)
                    .entity(personList)
                    .build();
            return build;
        }

        /**
         * reads a Person identified by the uuid
         * @param personUUID
         * @return person
         */
        @GET
        @Path("read")
        @Produces(MediaType.APPLICATION_JSON)
        public Response readPerson(
                @QueryParam("uuid") String personUUID
        ) {
            int httpStatus = 200;
            Personly person = DataHandler.readPersonbyUUID(personUUID);
            if (person == null) {
                httpStatus = 410;
            }
            return Response
                    .status(httpStatus)
                    .entity(person)
                    .build();
        }





    /**
     * inserts a new Person
     * @param username the name of the person
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPerson(
            @NotEmpty
            @Pattern(regexp = "^[A-Za-z]{1}[a-zA-Z0-9!?#$&`.-_]{4,30}")
            @FormParam("username") String username
    ) {
        Personly person = new Personly();
        person.setPersonUUID(UUID.randomUUID().toString());
        person.setUsername(username);

        DataHandler.insertPerson(person);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a person
     * @param personUUID the key
     * @param username the username of the person
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePerson(
            @FormParam("uuid") String personUUID,
            @FormParam("username") String username
    ) {
        int httpStatus = 200;
        Personly person = DataHandler.readPersonbyUUID(personUUID);
        if (person != null) {
            person.setUsername(username);

            DataHandler.updatePerson();
        } else {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }

    /**
     * deletes a Person identified by its uuid
     * @param personUUID  the key
     * @return  Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePerson(
            @QueryParam("uuid") String personUUID
    ) {
        int httpStatus = 200;
        if (!DataHandler.deletePerson(personUUID)) {
            httpStatus = 410;
        }
        return Response
                .status(httpStatus)
                .entity("")
                .build();
    }
}

