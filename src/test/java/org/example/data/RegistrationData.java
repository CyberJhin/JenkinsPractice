package org.example.data;

import com.github.javafaker.Faker;

import java.io.File;
import java.time.Month;
import java.time.Year;
import java.util.*;

public  class RegistrationData {

    private static final Faker faker = new Faker();
    private static final int MINIMUM_STUDENT_AGE = 16;
    private static final String[] SUBJECT_LIST =
            {"Maths", "Accounting", "Arts", "Social Studies", "Physics", "Chemistry"};
    private static final String[] HOBBIES_LIST =
            { "Sports", "Reading", "Music" };
    private static final String[] STATES_LIST = {"NCR", "Uttar Pradesh", "Haryana", "Rajasthan"};
    private static final Map<String, String[]> STATES_TO_CITY_MAP = new HashMap<>();

    static {
        STATES_TO_CITY_MAP.put("NCR", new String[]{"Delphi", "Gurgaon", "Noida"});
        STATES_TO_CITY_MAP.put("Uttar Pradesh", new String[]{"Agra", "Lucknow", "Merrut"});
        STATES_TO_CITY_MAP.put("Haryana", new String[]{"Karnal", "Panipat"});
        STATES_TO_CITY_MAP.put("Rajasthan", new String[]{"Jaipur", "Jaiselmer"});
    }

    public static RegistrationModel generateFullRegistrationData() {
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = faker.internet().emailAddress(name + lastName);
        String gender = faker.options().option("Male", "Female", "Other");
        String phone = faker.number().digits(10);
        int yearOfBirth = faker.number().numberBetween(1900, Year.now().getValue() - MINIMUM_STUDENT_AGE);
        String monthOfBirth = faker.options().option("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        String dayOfBirth = Integer.toString(faker.number().numberBetween(1, Month.valueOf(monthOfBirth.toUpperCase()).length(true)));
        String[] subjects = getRandomSelectionFromList(SUBJECT_LIST);
        String[] hobbies = getRandomSelectionFromList(HOBBIES_LIST);
        String picture = getRandomPictureFromFolder();
        String address = faker.address().fullAddress();
        String state = getRandomState();
        String city = getRandomCity(state);

        return new RegistrationModel(name, lastName, email, gender, phone, yearOfBirth, monthOfBirth, dayOfBirth,
                subjects, hobbies, picture, address, state, city);
    }

    public static RegistrationModel generateMinimumRegistrationData() {
        String name = faker.name().firstName();
        String lastName = faker.name().lastName();
        String phone = faker.number().digits(10);
        String gender = faker.options().option("Male", "Female", "Other");
        int yearOfBirth = faker.number().numberBetween(1900, Year.now().getValue() - MINIMUM_STUDENT_AGE);
        String monthOfBirth = faker.options().option("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        String dayOfBirth = Integer.toString(faker.number().numberBetween(1, Month.valueOf(monthOfBirth.toUpperCase()).length(true)));

        return new RegistrationModel(name, lastName, "", gender, phone, yearOfBirth, monthOfBirth, dayOfBirth,
                new String[0], new String[0], "", "", "", "");
    }

    private static String getRandomPictureFromFolder() {
        File folder = new File("src/test/resources");
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null && listOfFiles.length > 0) {
            return listOfFiles[(int) (Math.random() * listOfFiles.length)].getName();
        } else {
            throw new RuntimeException("No files found in the folder.");
        }
    }

    private static String[] getRandomSelectionFromList(String[] list) {
        int numberOfElements = (int) ((Math.random() * list.length) + 1);
        List<String> studentList = new ArrayList<>();
        while (studentList.size() < numberOfElements) {
            String randomSubject = list[faker.number().numberBetween(0, list.length)];
            if (!studentList.contains(randomSubject)) {
                studentList.add(randomSubject);
            }
        }
        return studentList.toArray(new String[0]);
    }

    private static String getRandomState() {
        return STATES_LIST[faker.number().numberBetween(0, STATES_LIST.length)];
    }

    private static String getRandomCity(String state) {
        String[] cities = STATES_TO_CITY_MAP.get(state);
        if (cities != null && cities.length > 0) {
            return cities[faker.number().numberBetween(0, cities.length)];
        } else {
            throw new IllegalArgumentException("No cities found for the given state: " + state);
        }
    }
}