package com.bookstore.model;

import javax.persistence.*;


@Entity
@Table(name = "`groups`")
public class Group {









	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupName;



    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }
    public Group(Long id, String groupName) {
		super();
		this.id = id;
		this.groupName = groupName;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }







    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
