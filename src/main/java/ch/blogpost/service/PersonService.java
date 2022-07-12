package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Personly;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
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
        @RolesAllowed({"admin", "user"})
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
        @RolesAllowed({"admin", "user"})
        @GET
        @Path("read")
        @Produces(MediaType.APPLICATION_JSON)
        public Response readPerson(
                @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
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
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response insertPerson(
            @Valid @BeanParam Personly personly
    ) {

        personly.setPersonUUID(UUID.randomUUID().toString());
        DataHandler.insertPerson(personly);
        return Response
                .status(200)
                .entity("")
                .build();
    }

    /**
     * updates a person
     * @param personUUID the key
     * @return Response
     */
    @RolesAllowed({"admin", "user"})
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePerson(
            @Valid @BeanParam Personly personly,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("personUUID") String personUUID

    ) throws ParseException {
        int httpStatus = 200;
        Personly altPerson = DataHandler.readPersonbyUUID(personUUID);
        if (altPerson != null) {
            altPerson.setUsername(personly.getUsername());
            altPerson.setFullname(personly.getFullname());
            altPerson.setEntrydate(personly.getEntrydate());

            try {
                DataHandler.updatePerson();
            }catch (Exception e){
                e.printStackTrace();
            }
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
    @RolesAllowed({"admin"})
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePerson(
            @NotEmpty
            @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}")
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

