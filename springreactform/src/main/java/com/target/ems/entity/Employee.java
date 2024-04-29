package com.target.ems.entity;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;


//@NoArgsConstructor
@Entity
@Table(name= "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="email_id", nullable = false, unique = true)
    private String email;

    public Employee(){}

    @Autowired
    public Employee(Long id, String firstName, String lastName, String email)
    {
        this.id= id;
        this.email=email;
        this.firstName=firstName;
        this.lastName= lastName;

    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = this.lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
