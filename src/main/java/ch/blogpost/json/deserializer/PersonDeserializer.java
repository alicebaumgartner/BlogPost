package ch.blogpost.json.deserializer;

import ch.blogpost.data.DataHandler;
import ch.blogpost.model.Person;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;

public class PersonDeserializer extends JsonDeserializer<Person> {
    @Override
    public Person deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        TextNode node = jsonParser.getCodec().readTree(jsonParser);
        return DataHandler.getInstance().readPersonbyUUID(node.asText());
    }
}
