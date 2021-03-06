package net.codejava.CodeJavaApp.Business;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;


import net.codejava.CodeJavaApp.user.*;

@Entity
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    // @Column(nullable = false, unique = true, length = 200)
    @NotNull(message = "Enter business name ")
    private String businessName;

    @NotNull
    private String category;

    @NotNull
    private Character outdoorIndoor;

    @NotNull
    private Long capacity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // @OneToMany(mappedBy = "business",cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnore
    // private List<Employee> employees;


    public Business(String businessName, String category, Character outdoorIndoor, Long capacity) {
        this.businessName = businessName;
        this.category = category;
        this.outdoorIndoor = outdoorIndoor;
        this.capacity = capacity;
    }



    public Business(Long businessId, String businessName, String category, Character outdoorIndoor, Long capacity,
            User user) {
        this.businessId = businessId;
        this.businessName = businessName;
        this.category = category;
        this.outdoorIndoor = outdoorIndoor;
        this.capacity = capacity;
        this.user = user;
    }



    public Business() {
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // public String getLocation() {
    //     return location;
    // }

    // public void setLocation(String location) {
    //     this.location = location;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public Long getMobileNum() {
    //     return mobileNum;
    // }

    // public void setMobileNum(Long mobileNum) {
    //     this.mobileNum = mobileNum;
    // }

    public Character getOutdoorIndoor() {
        return outdoorIndoor;
    }

    public void setOutdoorIndoor(Character outdoorIndoor) {
        this.outdoorIndoor = outdoorIndoor;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "Business [businessId=" + businessId + ", businessName=" + businessName + ", capacity=" + capacity
                + ", category=" + category + ", outdoorIndoor=" + outdoorIndoor + ", user=" + user + "]";
    }


}
