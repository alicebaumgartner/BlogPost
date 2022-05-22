package ch.blogpost.model;

import ch.blogpost.json.deserializer.PersonDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private String postUUID;

    @JsonDeserialize(using = PersonDeserializer.class)
    private Person autor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy hh:mm:ss")
    private LocalDateTime datum;

    private String text;
    private int lesezeit;

    private List<Kommentar> kommentare;

    public Post(String postUUID,Person autor, LocalDateTime datum, String text, int lesezeit) {
        this.postUUID = postUUID;
        this.autor = autor;
        this.datum = datum;
        this.text = text;
        this.lesezeit = lesezeit;
    }

    public String getPostUUID() {
        return postUUID;
    }

    public void setPostUUID(String postUUID) {
        this.postUUID = postUUID;
    }

    public Person getAutor() {
        return autor;
    }

    public void setAutor(Person autor) {
        this.autor = autor;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLesezeit() {
        return lesezeit;
    }

    public void setLesezeit(int lesezeit) {
        this.lesezeit = lesezeit;
    }

    public List<Kommentar> getKommentare() {
        return kommentare;
    }

    public void setKommentare(List<Kommentar> kommentare) {
        this.kommentare = kommentare;
    }
}
