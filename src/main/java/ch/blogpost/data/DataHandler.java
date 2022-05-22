package ch.blogpost.data;

import ch.blogpost.model.Kommentar;
import ch.blogpost.model.Person;
import ch.blogpost.model.Post;
import ch.blogpost.service.Config;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * reads and writes the data in the JSON-files
 */

public class DataHandler {
    private static DataHandler instance = null;
    private List<Kommentar> kommentarList;
    private List<Post> postList;
    private List<Person> personList;

    /**
     * private constructor defeats instantiation
     */
    private DataHandler() {
        setPersonList(new ArrayList<>());
        readallPersons();
        setPostList(new ArrayList<>());
        readAllPost();
        setKommentarlist(new ArrayList<>());
        readallKommentar();
    }

    /**
     * gets the only instance of this class
     * @return
     */
    public static DataHandler getInstance() {
        if (instance == null)
            instance = new DataHandler();
        return instance;
    }


    /**
     * reads all books
     * @return list of books
     */
    public List<Person> readallPersons() {
        return getpersonlist();
    }

    /**
     * reads a book by its uuid
     * @param personUUID
     * @return the Book (null=not found)
     */
    public Person readPersonbyUUID(String personUUID) {
        Person person = null;
        for (Person entry : getpersonlist()) {
            if (entry.getPersonUUID().equals(personUUID)) {
                person = entry;
            }
        }
        return person;
    }

    /**
     * inserts a new book into the personlist
     *
     * @param person the person to be saved
     */
    public void insertPerson(Person person) {
        getpersonlist().add(person);
        writePersonJSON();
    }

    /**
     * updates the personlist
     */
    public void updatePerson() {
        writePersonJSON();
    }

    /**
     * deletes a Person identified by the personUUID
     * @param personUUID  the key
     * @return  success=true/false
     */
    public boolean deltePerson(String personUUID) {
        Person person = readPersonbyUUID(personUUID);
        if (person != null) {
            getpersonlist().remove(person);
            writePersonJSON();
            return true;
        } else {
            return false;
        }
    }



    /**
     * reads all Post
     * @return list of Post
     */
    public List<Post> readAllPost() {
        return postList;
    }

    /**
     * reads a post by its uuid
     * @param postUUID
     * @return the Post (null=not found)
     */
    public Post readPostbyUUID(String postUUID) {
        Post post = null;
        for (Post entry : getPostlist()) {
            if (entry.getPostUUID().equals(postUUID)) {
                post = entry;
            }
        }
        return post;
    }

    /**
     * inserts a new Post into the postlist
     *
     * @param post the Post to be saved
     */
    public void insertPost(Post post) {
        getPostlist().add(post);
        writePostJSON();
    }

    /**
     * updates the postlist
     */
    public void updatePost() {
        writePostJSON();
    }

    /**
     * deletes a Post identified by the postUUID
     * @param postUUID  the key
     * @return  success=true/false
     */
    public boolean deletePost(String postUUID) {
        Post post = readPostbyUUID(postUUID);
        if (post != null) {
            getPostlist().remove(post);
            writePostJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all comments
     * @return list of comments
     */
    public List<Kommentar> readallKommentar() {
        return getKommentarlist();
    }

    /**
     * reads a comment by its uuid
     * @param kommmentarUUID
     * @return the comment (null=not found)
     */
    public Kommentar readKommentarbyUUID(String kommmentarUUID) {
        Kommentar kommentar = null;
        for (Kommentar entry : getKommentarlist()) {
            if (entry.getKommentarUUID().equals(kommmentarUUID)) {
                kommentar = entry;
            }
        }
        return kommentar;
    }

    /**
     * inserts a new comment into the commentlist
     *
     * @param kommentar the person to be saved
     */
    public void insertKommentar(Kommentar kommentar) {
        getKommentarlist().add(kommentar);
        writeKommentarJSON();
    }

    /**
     * updates the commentlist
     */
    public void updateKommentar() {
        writeKommentarJSON();
    }

    /**
     * deletes a Kommentar identified by the kommentarUUID
     * @param kommentarUUID  the key
     * @return  success=true/false
     */
    public boolean deleteKommentar(String kommentarUUID) {
        Kommentar kommentar = readKommentarbyUUID(kommentarUUID);
        if (kommentar != null) {
            getKommentarlist().remove(kommentar);
            writeKommentarJSON();
            return true;
        } else {
            return false;
        }
    }


    /**
     * reads the person from the JSON-file
     */
    private void readPersonJSON() {
        try {
            String path = Config.getProperty("bookJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Person[] personen = objectMapper.readValue(jsonData, Person[].class);
            for (Person person : personen) {
                getpersonlist().add(person);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the bookList to the JSON-file
     */
    private void writePersonJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("bookJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getpersonlist());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * reads the publishers from the JSON-file
     */
    private void readKommentarJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("kommentarJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Kommentar[] kommentare = objectMapper.readValue(jsonData, Kommentar[].class);
            for (Kommentar kommentar : kommentare) {
                getKommentarlist().add(kommentar);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the Kommentarlist to the JSON-file
     */
    private void writeKommentarJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("kommentarJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getpersonlist());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void readPostJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("postJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Post[] posts = objectMapper.readValue(jsonData, Post[].class);
            for (Post post : posts) {
                getPostlist().add(post);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the Postlist to the JSON-file
     */
    private void writePostJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("postJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            fileWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectWriter.writeValue(fileWriter, getpersonlist());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * gets personlist
     *
     * @return value of personlist
     */

    private List<Person> getpersonlist() {
        return personList;
    }

    /**
     * sets personlist
     *
     * @param personlist the value to set
     */

    private void setPersonList(List<Person> personlist) {
        this.personList = personlist;
    }

    /**
     * gets postlist
     *
     * @return value of postlist
     */

    private List<Post> getPostlist() {
        return postList;
    }

    /**
     * sets publisherList
     *
     * @param postList the value to set
     */

    private void setPostList(List<Post> postList) {
        this.postList = postList;
    }


    /**
     * gets kommentarlist
     *
     * @return value of kommentarlist
     */

    private List<Kommentar> getKommentarlist() {
        return kommentarList;
    }

    /**
     * sets kommentarlist
     *
     * @param kommentarlist the value to set
     */

    private void setKommentarlist(List<Kommentar> kommentarlist) {
        this.kommentarList = kommentarlist;
    }




}
