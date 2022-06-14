package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @NotEmpty
    private Date beitritt;

    public Personly() {
    }


    /**
     * creates a Person-object
     */
    public Personly(List<Postly> postList, String personUUID, String username, String name, Date beitritt) {
        this.postList = postList;
        this.personUUID = personUUID;
        this.username = username;
        this.name = name;
        this.beitritt = beitritt;
    }

/*
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
*/
    public void setPostListUUID(ArrayNode postListUUID){
        setPostList(new ArrayList<>());
        for (JsonNode postUUIDNode : postListUUID){
            getPostList().add(DataHandler.readPostlybyUUID(postUUIDNode.get("postUUID").textValue()));
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
    public String getName() {
        return name;
    }

    /**
     * sets name
     *
     * @param name the value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the beitritt
     * @return value of beitritt
     */
    public Date getBeitritt() {
        return beitritt;
    }
    /**
     * sets beitritt
     *
     * @param beitritt the value to set
     */
    public void setBeitritt(Date beitritt) {
        this.beitritt = beitritt;
    }

    public void setPerson(String personUUID) {
    }
}
