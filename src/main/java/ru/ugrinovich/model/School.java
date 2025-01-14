package ru.ugrinovich.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;

@Entity
@Table(name = "School")
public class School{

    @Id
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "principal_id", referencedColumnName = "id")
    private Principal principal;

    @Column(name = "school_number")
    private int schoolNumber;

    public School() {
    }

    public School( int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public int getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(int schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    @Override
    public String toString() {
        return "School{" +
                ", schoolNumber='" + schoolNumber + '\'' +
                '}';
    }
}
