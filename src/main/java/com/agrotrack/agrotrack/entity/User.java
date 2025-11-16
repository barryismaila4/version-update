package com.agrotrack.agrotrack.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "le username est necessaire")
    @Size(min = 3,max=50,message = "le username doit contenir minimun 3 caractere et maximum 50")
    @Column(unique=true,nullable=false)
    private String username;
    @NotBlank(message = "email est obligatoire")
    @Email(message = "email doit etre valide avec @")
    private String email;
    @NotBlank(message = "le mot de passe doit etre unique et obligatoire")
    @Size(min = 6,message = "minimum 6 caracteres le mot de passe")
    @Column(nullable=false)
    private String password;
    @Column(nullable = false)
    private String role = "USER";
    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonIgnore
    private List<Plant> plants=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval=true)
    @JsonIgnore
    private List <Notification> notifications=new ArrayList<>();


    public User() {}
    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;

    }
    public long getId(){return id;}
    public void setId(long id) {this.id=id;}
    public String getUsername(){return username;}
    public void setUsername(String username){this.username=username;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email=email;}
    public String getPassword(){return password;}
    public void setPassword(String password){this.password=password;}
    public String getRole(){return role;}
    public void setRole(String role){this.role=role;}
    public List <Plant>getPlants(){return plants;}
    public void setPlants(List<Plant> plants){this.plants=plants;}
    public List<Notification> getNotifications(){return notifications;}

    public void setNotifications(List<Notification> notifications){this.notifications=notifications;}


}