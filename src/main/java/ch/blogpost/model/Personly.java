package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * a person
 */
public class Personly {

    @JsonIgnore
    private List<Postly> postList;

    @FormParam("personUUID")
    @Pattern(regexp = "[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}")
    private String personUUID;

    @NotEmpty
    @FormParam("username")
    @Pattern(regexp = "[A-Za-z]{1}[a-zA-Z0-9!?#$&`.-_]{4,30}")
    private String username;

    @NotEmpty
    @FormParam("fullname")
    @Pattern(regexp = "[A-Za-z]+ [A-Za-z]+")
    private String fullname;


    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date entrydate;

    public Personly() {
    }


    /**
     * creates a Person-object
     */
    public Personly(List<Postly> postList, String personUUID, String username, String name, Date entrydate) {
        this.postList = postList;
        this.personUUID = personUUID;
        this.username = username;
        this.fullname = name;
        this.entrydate = entrydate;
    }

    /**
     * sets the date from the request
     *
     * @param dateFromRequest
     */
    @FormParam("entrydate")
    public void setDateFromRequest(String dateFromRequest) {
        if (dateFromRequest == null || dateFromRequest.isEmpty()) {
            return;
        }
        try {
            setEntrydate(
                    new SimpleDateFormat("dd.MM.yyyy")
                            .parse(dateFromRequest)
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPostListUUID(ArrayNode postListUUID) {
        setPostList(new ArrayList<>());
        for (JsonNode postUUIDNode : postListUUID) {
            getPostList().add(
                    DataHandler
                            .readPostbyUUID(
                                    postUUIDNode
                                            .get("postUUID")
                                            .textValue()
                            )
            );
        }
    }


    /**
     * gets the postlist from the post-object
     *
     * @return postlist
     */
    public List<Postly> getPostList() {
        return postList;
    }

    /**
     * sets post
     *
     * @param postList the value to set
     */
    public void setPostList(List<Postly> postList) {
        this.postList = postList;
    }

    /**
     * gets the personUUID
     *
     * @return value of personUUID
     */
    public String getPersonUUID() {
        return personUUID;
    }

    /**
     * sets personUUID
     *
     * @param personUUID the value to set
     */
    public void setPersonUUID(String personUUID) {
        this.personUUID = personUUID;
    }

    /**
     * gets the username
     *
     * @return value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets username
     *
     * @param username the value to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * gets the name
     *
     * @return value of name
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * sets name
     *
     * @param fullname the value to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * gets the entrydate
     *
     * @return value of entrydate
     */
    public Date getEntrydate() {
        return entrydate;
    }

    /**
     * sets entrydate
     *
     * @param entrydate the value to set
     */
    public void setEntrydate(Date entrydate) {
        this.entrydate = entrydate;
    }

    public void setPerson(String personUUID) {
    }
}
