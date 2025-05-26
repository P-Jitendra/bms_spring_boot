package com.example.bms_backend.UserRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @NotNull(message = "UserID is required.")
    private String userId;
    @NotNull(message = "Name is required.")
    private String name;
    @Column(unique = true)
    @NotNull(message = "Email is required.")
    private String email;
    @NotNull(message = "Password is required.")
    private String password;
    @NotNull(message = "ContactNo is required.")
    private long contactNo;
    private String category;
    public UserInfo(){}
    public UserInfo(String userId, String name, String email, String password, long contactNo, String category){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.category = category;
        this.contactNo = contactNo;
    }
    public UserInfo(String userId, String name, String email, String password, long contactNo){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.contactNo = contactNo;
        this.category = "Other";
    }
    public String getUserId(){
        return userId;
    }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getPassword(){
        return password;
    }
    public long getContactNo(){
        return contactNo;
    }
    public String getCategory(){
        return category;
    }

    public void setPassword(String newPassword){
        password = newPassword;
    }
    public void setContactNo(long newContactNo){
        contactNo = newContactNo;
    }
    public void setCategory(String newCategory){
        category = newCategory;
    }
    public void setName(String newName){
        name = newName;
    }
    public void setEmail(String newEmail) { email = newEmail; }
    public void setUserId(String newUserId){ userId = newUserId; }
}
