package ch.blogpost.data;

import ch.blogpost.model.Kommentarly;
import ch.blogpost.model.Personly;
import ch.blogpost.model.Postly;
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

public final class DataHandler {

    private static List<Kommentarly> kommentarlyList;
    private static List<Postly> postlyList;
    private static List<Personly> personlyList;

    /**
     * private constructor defeats instantiation
     */
    public DataHandler() {

    }


    /**
     * reads all books
     * @return list of books
     */
    public static List<Personly> readAllPersons() {
        return getpersonlist();
    }

    /**
     * reads a book by its uuid
     * @param personUUID
     * @return the Book (null=not found)
     */
    public static Personly readPersonbyUUID(String personUUID) {
        Personly personly = null;
        for (Personly entry : getpersonlist()) {
            if (entry.getPersonUUID().equals(personUUID)) {
                personly = entry;
            }
        }
        return personly;
    }

    /**
     * inserts a new book into the personlist
     *
     * @param personly the person to be saved
     */
    public static void insertPerson(Personly personly) {
        getpersonlist().add(personly);
        writePersonJSON();
    }

    /**
     * updates the personlist
     */
    public static void updatePerson() {
        writePersonJSON();
    }

    /**
     * deletes a Person identified by the personUUID
     * @param personUUID  the key
     * @return  success=true/false
     */
    public static boolean deletePerson(String personUUID) {
        Personly personly = readPersonbyUUID(personUUID);
        if (personly != null) {
            getpersonlist().remove(personly);
            writePersonJSON();
            return true;
        } else {
            return false;
        }
    }



    /**
     * reads all Postly
     * @return list of Postly
     */
    public static List<Postly> readAllPostly() {
        return postlyList;
    }

    /**
     * reads a post by its uuid
     * @param postUUID
     * @return the Postly (null=not found)
     */
    public static Postly readPostlybyUUID(String postUUID) {
        Postly post = null;
        for (Postly entry :getPostlylist()) {
            if (entry.getPostUUID().equals(postUUID)) {
                post = entry;
            }
        }
        return post;
    }

    /**
     * inserts a new Postly into the postlyList
     *
     *
     *
     * @param post the Postly to be saved
     */
    public static void insertPostly(Postly post) {
        getPostlylist().add(post);
        readPostlyJSON();
    }

    /**
     * updates the postlyList
     *
     *
     */
    public static void updatePostly() {
        readPostlyJSON();
    }

    /**
     * deletes a Postly identified by the postUUID
     * @param postUUID  the key
     * @return  success=true/false
     */
    public static boolean deletePostly(String postUUID) {
        Postly post = readPostlybyUUID(postUUID);
        if (post != null) {
            getPostlylist().remove(post);
            readPostlyJSON();
            return true;
        } else {
            return false;
        }
    }

    /**
     * reads all comments
     * @return list of comments
     */
    public static List<Kommentarly> readallKommentar() {
        return getKommentarlist();
    }

    /**
     * reads a comment by its uuid
     * @param kommmentarUUID
     * @return the comment (null=not found)
     */
    public static Kommentarly readKommentarbyUUID(String kommmentarUUID) {
        Kommentarly kommentarly = null;
        for (Kommentarly entry : getKommentarlist()) {
            if (entry.getKommentarUUID().equals(kommmentarUUID)) {
                kommentarly = entry;
            }
        }
        return kommentarly;
    }

    /**
     * inserts a new comment into the commentlist
     *
     * @param kommentarly the person to be saved
     */
    public static void insertKommentar(Kommentarly kommentarly) {
        getKommentarlist().add(kommentarly);
        writeKommentarJSON();
    }

    /**
     * updates the commentlist
     */
    public static void updateKommentar() {
        writeKommentarJSON();
    }

    /**
     * deletes a Kommentar identified by the kommentarUUID
     * @param kommentarUUID  the key
     * @return  success=true/false
     */
    public static boolean deleteKommentar(String kommentarUUID) {
        Kommentarly kommentarly = readKommentarbyUUID(kommentarUUID);
        if (kommentarly != null) {
            getKommentarlist().remove(kommentarly);
            writeKommentarJSON();
            return true;
        } else {
            return false;
        }
    }


    /**
     * reads the person from the JSON-file
     */
    private static void readPersonJSON() {
        try {
            String path = Config.getProperty("personJSON");
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(path)
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Personly[] personen = objectMapper.readValue(jsonData, Personly[].class);
            for (Personly personly : personen) {
                getpersonlist().add(personly);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the bookList to the JSON-file
     */
    private static void writePersonJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer fileWriter;

        String bookPath = Config.getProperty("personJSON");
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
    private static void readKommentarJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("kommentarJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Kommentarly[] kommentare = objectMapper.readValue(jsonData, Kommentarly[].class);
            for (Kommentarly kommentarly : kommentare) {
                getKommentarlist().add(kommentarly);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the Kommentarlist to the JSON-file
     */
    private static void writeKommentarJSON() {
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

    private static void readPostlyJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(
                    Paths.get(
                            Config.getProperty("postJSON")
                    )
            );
            ObjectMapper objectMapper = new ObjectMapper();
            Postly[] posts = objectMapper.readValue(jsonData, Postly[].class);
            for (Postly post : posts) {
                getPostlylist().add(post);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * writes the Postlylist to the JSON-file
     */
    private static void writePostlyJSON() {
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

    private static List<Personly> getpersonlist() {
        if(personlyList == null) {
            setPersonList(new ArrayList<>());
            readPersonJSON();
        }
        return personlyList;
    }

    /**
     * sets personlist
     *
     * @param personlist the value to set
     */

    private static void setPersonList(List<Personly> personlist) {
        DataHandler.personlyList = personlist;
    }

    /**
     * gets postlyList
     *
     *
     *
     * @return value of postlyList
     *
     *
     */

    private static List<Postly> getPostlylist() {
        if(postlyList == null) {
            setPostlyList(new ArrayList<>());
            readPostlyJSON();
        }
        return postlyList;
    }

    /**
     * sets publisherList
     *
     * @param postlyList the value to set
     */

    private static void setPostlyList(List<Postly> postlyList) {
        DataHandler.postlyList = postlyList;
    }


    /**
     * gets kommentarlist
     *
     * @return value of kommentarlist
     */

    private static List<Kommentarly> getKommentarlist() {
        if(kommentarlyList == null) {
            setKommentarlist(new ArrayList<>());
            readKommentarJSON();
        }

        return kommentarlyList;
    }

    /**
     * sets kommentarlist
     *
     * @param kommentarlist the value to set
     */

    private static void setKommentarlist(List<Kommentarly> kommentarlist) {
        DataHandler.kommentarlyList = kommentarlist;
    }




}
