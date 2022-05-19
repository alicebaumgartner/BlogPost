package ch.blogpost.model;

import java.time.LocalDate;
import java.util.List;

public class Person {
    private List<Post> postList;
    private String personUUID;
    private String username;
    private String name;
    private LocalDate beitritt;

    public Person( String personUUID,String username, String name, LocalDate beitritt) {
        this.personUUID = personUUID;
        this.username = username;
        this.name = name;
        this.beitritt = beitritt;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public String getPersonUUID() {
        return personUUID;
    }

    public void setPersonUUID(String personUUID) {
        this.personUUID = personUUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBeitritt() {
        return beitritt;
    }

    public void setBeitritt(LocalDate beitritt) {
        this.beitritt = beitritt;
    }
}
