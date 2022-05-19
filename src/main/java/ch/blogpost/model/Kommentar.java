package ch.blogpost.model;

import java.time.LocalDate;

public class Kommentar {
    private String kommentarUUID;
    private Person person;
    private Post post;
    private LocalDate date;
    private String kommentar;

    public Kommentar( String kommentarUUID,Person person, LocalDate date, String kommentar, Post post) {
        this.kommentarUUID = kommentarUUID;
        this.post = post;
        this.person = person;
        this.date = date;
        this.kommentar = kommentar;
    }

    public ch.blogpost.model.Person getPerson() {
        return person;
    }

    public void setPerson(ch.blogpost.model.Person person) {
        this.person = person;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public String getKommentarUUID() {
        return kommentarUUID;
    }

    public void setKommentarUUID(String kommentarUUID) {
        this.kommentarUUID = kommentarUUID;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
