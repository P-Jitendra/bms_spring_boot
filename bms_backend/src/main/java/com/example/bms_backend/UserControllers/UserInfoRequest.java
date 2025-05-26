package com.example.bms_backend.UserControllers;

import com.example.bms_backend.UserRepository.UserInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class UserInfoRequest {
    @JsonProperty("user_data")
    private List<UserDTO> userInfoList;

    public List<UserDTO> getUsersList(){
        return userInfoList;
    }
    public void setUserInfoList(List<UserDTO> userInfoList){
        this.userInfoList = userInfoList;
    }
}
