package application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Application {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<String, ArrayList<Integer>> arrays = new HashMap<>();
    private static Application application = new Application();

    public static void main( String[] args) throws JsonProcessingException {
        application.setup();

        ArrayList<Integer> list1 = arrays.get("1");
        ArrayList<Integer> list2 = arrays.get("2");
        Collections.sort(list1);
        Collections.sort(list2);

        System.out.println(application.calculateDifference(list1, list2));
    }

    private AtomicInteger calculateDifference(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        AtomicInteger index = new AtomicInteger(0);
        AtomicInteger overallDifference = new AtomicInteger();

        list1.stream().forEach(num -> {
            int currentIndex = index.getAndIncrement();
            int list2Value = list2.get(currentIndex);
            int difference = num - list2Value;
            if (difference < 0) {difference = difference * -1;}
            overallDifference.addAndGet(difference);
        });

        return overallDifference;
    }

    private ArrayList<Integer> createArray(String json) throws JsonProcessingException {
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

    private void setup() throws JsonProcessingException {
        application.createArray("numbers1.json");
        application.createArray("numbers2.json");
    }

}
