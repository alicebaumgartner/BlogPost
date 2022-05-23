package ch.blogpost.model;

import ch.blogpost.data.DataHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * a person
 */
public class Person {

    @JsonIgnore
    private List<Post> postList;

    private String personUUID;
    private String username;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date beitritt;

    public Person() {
    }


    /**
     * creates a Person-object
     */
    public Person(List<Post> postList, String personUUID, String username, String name, Date beitritt) {
        this.postList = postList;
        this.personUUID = personUUID;
        this.username = username;
        this.name = name;
        this.beitritt = beitritt;
    }


    public void setPostListUUID(ArrayNode postListUUID){
        setPostList(new ArrayList<>());
        for (JsonNode postUUIDNode : postListUUID) {
            getPostList().add(
                    DataHandler
                            .getInstance()
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
    public List<Post> getPostList() {
        return postList;
    }

    /**
     * sets post
     *
     * @param postList the value to set
     */
    public void setPostList(List<Post> postList) {
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
}
