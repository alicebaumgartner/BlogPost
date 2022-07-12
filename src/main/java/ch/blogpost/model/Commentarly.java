package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import javax.ws.rs.FormParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class Commentarly {
    @FormParam("commentUUID")
    @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}")
    private String commentUUID;

    @JsonIgnore
    private Personly person;

    @JsonIgnore
    private Postly post;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;

    @NotEmpty
    @FormParam("comment")
    @Size(min = 1, max=100)
    private String comment;

    public Commentarly() {
    }

    /**
     * creates a Kommentar-object
     */

    public Commentarly(String commentUUID, Personly person, Postly post, Date date, String comment) {
        this.commentUUID = commentUUID;
        this.person = person;
        this.post = post;
        this.date = date;
        this.comment = comment;
    }

    /**
     * sets the date from the request
     *
     * @param dateFromRequest
     */
    @FormParam("date")
    public void setDateFromRequest(String dateFromRequest) {
        if (dateFromRequest == null || dateFromRequest.isEmpty()) {
            return;
        }
        try {
            setDate(
                    new SimpleDateFormat("dd.MM.yyyy")
                            .parse(dateFromRequest)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @FormParam("personUUID")
    public void setPersonUUID(String personUUID) {
        setPerson(new Personly());
        Personly person = DataHandler.readPersonbyUUID(personUUID);
        getPerson().setPersonUUID(personUUID);
        getPerson().setPerson(person.getPersonUUID());
        setPerson(DataHandler.readPersonbyUUID(personUUID));
    }

    @FormParam("postUUID")
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
     * gets the comment
     * @return value of comment
     */
    public String getKommentar() {
        return comment;
    }

    /**
     * sets publisher
     *
     * @param comment the value to set
     */
    public void setKommentar(String comment) {
        this.comment = comment;
    }

    /**
     * gets the commentUUID
     * @return value of commentUUID
     */
    public String getKommentarUUID() {
        return commentUUID;
    }

    /**
     * sets commentUUID
     *
     * @param commentUUID the value to set
     */
    public void setKommentarUUID(String commentUUID) {
        this.commentUUID = commentUUID;
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
