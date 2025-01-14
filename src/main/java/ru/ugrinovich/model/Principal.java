package ru.ugrinovich.model;

import jakarta.persistence.*;

import java.io.Serializable;



@Entity
@Table(name = "Principal")
public class Principal implements Serializable                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @OneToOne(mappedBy = "principal", cascade = CascadeType.PERSIST)
    private School school;

    public Principal() {

    }

    public Principal(int age, String name, School school) {
        this.age = age;
        this.name = name;
        this.school = school;
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

    @Override
    public String toString() {
        return "Principal{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", school=" + school +
                '}';
    }
}
