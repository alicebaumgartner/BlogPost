package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.validation.constraints.*;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * a comment under the post
 */
public class Kommentarly {
    @FormParam("kommentarUUID")
    @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
    private String kommentarUUID;

    @JsonIgnore
    private Personly person;

    @JsonIgnore
    private Postly post;

    @FormParam("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotEmpty
    private Date date;


    @FormParam("kommentar")
    @Size(min = 2, max=100)
    private String kommentar;

    public Kommentarly() {
    }

    /**
     * creates a Kommentar-object
     */

    public Kommentarly(String kommentarUUID, Personly person, Postly post, Date date, String kommentar) {
        this.kommentarUUID = kommentarUUID;
        this.person = person;
        this.post = post;
        this.date = date;
        this.kommentar = kommentar;
    }


    public void setPersonUUID(String personUUID) {
        setPerson(new Personly());
        Personly person = DataHandler.readPersonbyUUID(personUUID);
        getPerson().setPersonUUID(personUUID);
        getPerson().setPerson(person.getPersonUUID());
        setPerson(DataHandler.readPersonbyUUID(personUUID));
    }

    public void setPostUUID(String postUUID) {
        setPost(new Postly());
        Postly post = DataHandler.readPostlybyUUID(postUUID);
        getPost().setPostUUID(postUUID);
        getPost().setPost(post.getPostUUID());
    }


    /**
     * gets the personsUUID from the Person-object
     * @return person-object
     */
    public Personly getPerson() {
       return person;
    }

    /**
     * gets the personsUUID from the Person-object
     * @return value of personUUID
     */
    public String  getPersonUUID() {
        if (getPerson()== null) return null;
        return getPerson().getPersonUUID();
    }

    /**
     * sets person
     *
     * @param person the value to set
     */
    public void setPerson( Personly person) {
        this.person = person;
    }



    /**
     * gets the date
     * @return value of date
     */
    public Date getDate() {
        return date;
    }

    /**
     * sets date
     *
     * @param date the value to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * gets the kommentar
     * @return value of kommentar
     */
    public String getKommentar() {
        return kommentar;
    }

    /**
     * sets publisher
     *
     * @param kommentar the value to set
     */
    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    /**
     * gets the kommentarUUID
     * @return value of kommentarUUID
     */
    public String getKommentarUUID() {
        return kommentarUUID;
    }

    /**
     * sets kommentarUUID
     *
     * @param kommentarUUID the value to set
     */
    public void setKommentarUUID(String kommentarUUID) {
        this.kommentarUUID = kommentarUUID;
    }

    /**
     * gets the post from post-Object
     * @return value of post
     */
    public Postly getPost() {
        return post;
    }

    /**
     * sets post
     *
     * @param post the value to set
     */
    public void setPost(Postly post) {
        this.post = post;
    }
}
