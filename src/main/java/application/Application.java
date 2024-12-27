package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private Map<String, ArrayList<Integer>> arrays = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static Application application = new Application();

    public static void main( String[] args) throws JsonProcessingException {
        application.createArray("numbers1.json");
        application.createArray("numbers2.json");
    }

    public ArrayList<Integer> createArray(String json) throws JsonProcessingException {
        ArrayList<Integer> list = new ArrayList<>();

        try {
            // Load the JSON array from the resources folder
            InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(json);

            if (inputStream == null) {
                System.out.println("Resource not found!");
                return list;
            }

            list = objectMapper.readValue(inputStream, ArrayList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        arrays.put(String.valueOf(arrays.size() + 1), list);
        return list;
    }

}
