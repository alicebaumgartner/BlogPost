package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
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
    @Pattern(regexp = "(?=[0-9]{13}|[- 0-9]{17})97[89](-[0-9]{1,5}){3}-[0-9]")
    private String personUUID;

    @FormParam("username")
    @Pattern(regexp = "^[A-Za-z]{1}[a-zA-Z0-9!?#$&`.-_]{4,30}")
    private String username;

    @FormParam("name")
    @Pattern(regexp = "/^[A-Za-z]+ [A-Za-z]+$/")
    private String personname;

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
        this.personname = name;
        this.entrydate = entrydate;
    }


    public void setPostListUUID(ArrayNode postListUUID){
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
     * @return value of name
     */
    public String getPersonname() {
        return personname;
    }

    /**
     * sets name
     *
     * @param personname the value to set
     */
    public void setPersonname(String personname) {
        this.personname = personname;
    }

    /**
     * gets the entrydate
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
