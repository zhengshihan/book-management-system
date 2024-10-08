package com.bookstore.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    public User(Long id, String username, String password, Group group) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.group = group;
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

   
    
    @ManyToMany(fetch = FetchType.EAGER,
    	      cascade = {
    	          CascadeType.PERSIST,
    	          CascadeType.MERGE
    	      },
    	      mappedBy = "users")
    	  @JsonIgnore
    	  private Set<Role> roles = new HashSet<>();
    
    
    
    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User() {
    }

    public User(String username, String password, Group group) {
        this.username = username;
        setPassword(password); // Encrypt password
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        // Encrypt password using BCryptPasswordEncoder
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", group=" + group +
                '}';
    }
}
