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
    private Personly author;

    @FormParam("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private Date postdate;

    @FormParam("text")
    @NotEmpty
    @Size(min=1, max = 200)
    private String text;


    @FormParam("readingtime")
    @Digits(integer = 300, fraction = 0)
    private int readingtime;

    @JsonIgnore
    private List<Commentarly> comments;

    public Postly() {
    }

    public Postly(String postUUID, Personly author, Date postdate, String text, int readingtime, List<Commentarly> comments) {
        this.postUUID = postUUID;
        this.author = author;
        this.postdate = postdate;
        this.text = text;
        this.readingtime = readingtime;
        this.comments = comments;
    }

    /**
     * creates a Post-object
     */


    public void setAutorUUID(String autorUUID) {
        setAuthor(DataHandler.readPersonbyUUID(autorUUID));
    }



    public void setCommentUUID(ArrayNode commentUUID){
        setComments(new ArrayList<>());
        for (JsonNode kommentarUUIDNode : commentUUID) {
            getComments().add(
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
    public Personly getAuthor() {
        return author;
    }

    /**
     * sets autor
     *
     * @param author the value to set
     */
    public void setAuthor(Personly author) {
        this.author = author;
    }

    /**
     * gets the datum
     * @return datum
     */
    public Date getPostdate() {
        return postdate;
    }

    /**
     * sets datum
     *
     * @param postdate the value to set
     */
    public void setPostdate(Date postdate) {
        this.postdate = postdate;
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
    public int getReadingtime() {
        return readingtime;
    }

    /**
     * sets lesezeit
     *
     * @param readingtime the value to set
     */
    public void setReadingtime(int readingtime) {
        this.readingtime = readingtime;
    }

    /**
     * gets the kommentar from the kommentar-object
     * @return kommentarlist
     */
    public List<Commentarly> getComments() {
        return comments;
    }

    /**
     * sets kommentarlist
     *
     * @param comments the value to set
     */
    public void setComments(List<Commentarly> comments) {
        this.comments = comments;
    }

    public void setPost(String postUUID) {
    }
}
