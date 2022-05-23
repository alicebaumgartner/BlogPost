package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * a comment under the post
 */
public class Kommentar {
    private String kommentarUUID;

    @JsonIgnore
    private Person person;

    @JsonIgnore
    private Post post;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date date;
    private String kommentar;

    public Kommentar() {
    }

    /**
     * creates a Kommentar-object
     */

    public Kommentar(String kommentarUUID, Person person, Post post, Date date, String kommentar) {
        this.kommentarUUID = kommentarUUID;
        this.person = person;
        this.post = post;
        this.date = date;
        this.kommentar = kommentar;
    }


    public void setPersonUUID(String personUUID) {
        setPerson(DataHandler.getInstance().readPersonbyUUID(personUUID));
    }

    public void setPostUUID(String postUUID) {
        setPost(DataHandler.getInstance().readPostbyUUID(postUUID));
    }


    /**
     * gets the personsUUID from the Person-object
     * @return value of personUUID
     */
    public ch.blogpost.model.Person getPerson() {
        return person;
    }

    /**
     * sets person
     *
     * @param person the value to set
     */
    public void setPerson(ch.blogpost.model.Person person) {
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
    public Post getPost() {
        return post;
    }

    /**
     * sets post
     *
     * @param post the value to set
     */
    public void setPost(Post post) {
        this.post = post;
    }
}
