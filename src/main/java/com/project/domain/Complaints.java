package com.project.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "complaints")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Complaints {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "details")
    private String details;


    public Complaints()
    {}

    public Complaints(User user,String details)
    {
        this.user=user;
        this.userName=user.getFirstName();
        this.details=details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName){
        this.userName=userName;

    }



    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public String toString() {
        return "Complaints{" +
            "id=" + id +
            ", user=" + user +
            ", userName='" + userName + '\'' +
            ", details='" + details + '\'' +
            '}';
    }
}
