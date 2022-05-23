package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * a post from a person
 */
public class Post {
    private String postUUID;

    @JsonIgnore
    private Person autor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private Date datum;

    private String text;
    private int lesezeit;

    @JsonIgnore
    private List<Kommentar> kommentare;

    public Post() {
    }

    public Post(String postUUID, Person autor, Date datum, String text, int lesezeit, List<Kommentar> kommentare) {
        this.postUUID = postUUID;
        this.autor = autor;
        this.datum = datum;
        this.text = text;
        this.lesezeit = lesezeit;
        this.kommentare = kommentare;
    }

    /**
     * creates a Post-object
     */


    public void setAutorUUID(String autorUUID) {
        setAutor(DataHandler.getInstance().readPersonbyUUID(autorUUID));
    }

    public void setKommentareUUID(ArrayNode kommentareUUID){
        setKommentare(new ArrayList<>());
        for (JsonNode kommentarUUIDNode : kommentareUUID) {
            getKommentare().add(
                    DataHandler
                            .getInstance()
                            .readKommentarbyUUID(
                                    kommentarUUIDNode
                                            .get("kommentarUUID")
                                            .textValue()
                            )
            );
        }
    }


    /**
     * gets the postUUID
     * @return postUUID
     */
    public String getPostUUID() {
        return postUUID;
    }

    /**
     * sets postUUID
     *
     * @param postUUID the value to set
     */
    public void setPostUUID(String postUUID) {
        this.postUUID = postUUID;
    }

    /**
     * gets the autor from the person-object
     * @return autor
     */
    public Person getAutor() {
        return autor;
    }

    /**
     * sets autor
     *
     * @param autor the value to set
     */
    public void setAutor(Person autor) {
        this.autor = autor;
    }

    /**
     * gets the datum
     * @return datum
     */
    public Date getDatum() {
        return datum;
    }

    /**
     * sets datum
     *
     * @param datum the value to set
     */
    public void setDatum(Date datum) {
        this.datum = datum;
    }

    /**
     * gets the text
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * sets text
     *
     * @param text the value to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * gets the lesezeit
     * @return lesezeit
     */
    public int getLesezeit() {
        return lesezeit;
    }

    /**
     * sets lesezeit
     *
     * @param lesezeit the value to set
     */
    public void setLesezeit(int lesezeit) {
        this.lesezeit = lesezeit;
    }

    /**
     * gets the kommentar from the kommentar-object
     * @return kommentarlist
     */
    public List<Kommentar> getKommentare() {
        return kommentare;
    }

    /**
     * sets kommentarlist
     *
     * @param kommentare the value to set
     */
    public void setKommentare(List<Kommentar> kommentare) {
        this.kommentare = kommentare;
    }
}
