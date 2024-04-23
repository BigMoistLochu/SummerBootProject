package org.example.tomekcat.annotations;


import java.util.ArrayList;
import java.util.List;

public class Person {


    private String name;
    private String surname;

    private final List<Person> personList = new ArrayList<>();

    public Person(){}

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public List<Person> getPersonList() {
        return personList;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
