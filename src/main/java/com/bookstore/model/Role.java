package com.bookstore.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String roleName;
	
	
	
	
	 @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
		  @JoinTable(name = "role_users",
		        joinColumns = { @JoinColumn(name = "role_id") },
		        inverseJoinColumns = { @JoinColumn(name = "user_id") })
		  private Set<User> users = new HashSet<>();
	
	
	public Role() {
		
	}
	public Role(Long id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public void addUser(User user) {
	    this.users.add(user);
	    user.getRoles().add(this);
	  }
	  
	  public void removeUser(long userId) {
	    User user = this.users.stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
	    if (user != null) {
	      this.users.remove(user);
	      user.getRoles().remove(this);
	    }
	  }
}
