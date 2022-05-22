package ch.blogpost.service;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Person;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


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
            List<Person> personList = DataHandler.getInstance().readallPersons();
            return Response
                    .status(200)
                    .entity(personList)
                    .build();
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
            Person person = DataHandler.getInstance().readPersonbyUUID(personUUID);
            if (person == null) {
                httpStatus = 410;
            }
            return Response
                    .status(httpStatus)
                    .entity(person)
                    .build();
        }
    }




