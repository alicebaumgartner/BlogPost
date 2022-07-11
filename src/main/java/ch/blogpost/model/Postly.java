package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * a post from a person
 */
public class Postly {
    @FormParam("postUUID")
    @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}")
    private String postUUID;

    @JsonIgnore
    private Personly autor;

    @FormParam("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private Date datum;

    @FormParam("text")
    @NotEmpty
    @Size(min=1, max = 200)
    private String text;


    @FormParam("lesezeit")
    @Digits(integer = 300, fraction = 0)
    private int lesezeit;

    @JsonIgnore
    private List<Kommentarly> kommentare;

    public Postly() {
    }

    public Postly(String postUUID, Personly autor, Date datum, String text, int lesezeit, List<Kommentarly> kommentare) {
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
        setAutor(DataHandler.readPersonbyUUID(autorUUID));
    }



    public void setCommentUUID(ArrayNode commentUUID){
        setKommentare(new ArrayList<>());
        for (JsonNode kommentarUUIDNode : commentUUID) {
            getKommentare().add(
                    DataHandler

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
    public Personly getAutor() {
        return autor;
    }

    /**
     * sets autor
     *
     * @param autor the value to set
     */
    public void setAutor(Personly autor) {
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
    public List<Kommentarly> getKommentare() {
        return kommentare;
    }

    /**
     * sets kommentarlist
     *
     * @param kommentare the value to set
     */
    public void setKommentare(List<Kommentarly> kommentare) {
        this.kommentare = kommentare;
    }

    public void setPost(String postUUID) {
    }
}
