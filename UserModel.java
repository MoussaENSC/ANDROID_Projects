package com.example.basicapp;

public class UserModel {

    private int id;
    private String name;
    private int age;
    private boolean male;
    private boolean female;
    private float weight;
    private float height;


    //Constructors
    public UserModel(int id, String name, int age, boolean male, boolean female, float weight, float height) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.male = male;
        this.female = female;
        this.weight = weight;
        this.height = height;
    }
    public UserModel() {
    }


    //toString
    @Override
    public String toString() {
        String gender = "";
        if(isMale()){
            gender = "Male";
        }
        else if(isFemale()){
            gender = "Female";
        }
        String s = name + ", " + age + " years old, " + gender + "\n" + weight + "kg, " + height + "cm";
        return s;
    }


    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
