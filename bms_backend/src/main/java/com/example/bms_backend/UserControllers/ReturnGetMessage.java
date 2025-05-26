package com.example.bms_backend.UserControllers;
import com.example.bms_backend.UserRepository.UserInfo;

import java.util.List;
public class ReturnGetMessage {
    private String status;
    private List<UserInfo> data;
    public ReturnGetMessage(String status, List<UserInfo> userInfoList){
        this.status = status;
        this.data = userInfoList;
    }

    public String getStatus(){
        return status;
    }

    public List<UserInfo> getUsersList(){
        return data;
    }
}
