package com.mariano.araullobooks;

public class Globals {
    private static Globals instance;
    private Integer userId;
    private boolean isLoggedIn;
    private String firstName;
    private String lastName;


    private Globals() {
        Integer userId;
        String firstName = "";
        String lastName = "";
        Boolean isLoggedIn = false;
    }

    public static synchronized Globals getInstance() {
        if (instance == null) {
            instance = new Globals();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }
}
