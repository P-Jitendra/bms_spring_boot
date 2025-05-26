package com.example.bms_backend.UserService;

import com.example.bms_backend.UserControllers.CheckEmailRequest;
import com.example.bms_backend.UserControllers.CheckUserIdRequest;
import com.example.bms_backend.UserControllers.UserDTO;
import com.example.bms_backend.UserRepository.UserInfo;
import com.example.bms_backend.UserRepository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public void createNewUsers(List<UserDTO> userInfoList){
        for(UserDTO userInfo:userInfoList) {
            if (userRepository.existsById(userInfo.getUserId())) {
                throw new UserAlreadyExistsException("One of user data already exists in database.");
            }
        }
        List<UserInfo> newUserInfoList = new ArrayList<>();
        for(UserDTO userInfo:userInfoList) {
            userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
            newUserInfoList.add(new UserInfo(userInfo.getUserId(), userInfo.getName(), userInfo.getEmail(), userInfo.getPassword(), userInfo.getContactNo(), userInfo.getCategory()));
        }
        userRepository.saveAll(newUserInfoList);
    }
    @Transactional
    public void updateUserData(UserInfo userInfo){
        if(!userRepository.existsById(userInfo.getUserId())){
            throw new UserDoesNotExistException("Given UserID doesn't exist!");
        }
        else if(userRepository.existsByEmail(userInfo.getEmail())){
            throw new NewEmailIdExistsException("New email for given user already exists for different user!");
        }
        else{
            userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
            userRepository.save(userInfo);
        }
    }
    @Transactional
    public void checkIfUserExists(CheckUserIdRequest userData) {
        Optional<UserInfo> userInfoRes = userRepository.findById(userData.getUserId());
        if(userInfoRes.isEmpty()) {
            throw new UserDoesNotExistException("UserId doesn't exist!");
        }
        else{
            UserInfo userInfoData = userInfoRes.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if(!encoder.matches(userData.getPassword(), userInfoData.getPassword())) {
                throw new PasswordDoesNotMatchException("Password doesn't match!");
            }
        }
    }
    @Transactional
    public void checkIfUserExistsBasedOnEmail(CheckEmailRequest userData){
        if(!userRepository.existsByEmail(userData.getEmail())){
            throw new UserDoesNotExistException("User Email doesn't exist!");
        }
        else{
            UserInfo userInfoData = userRepository.findByEmail(userData.getEmail());
            if(!userInfoData.getPassword().matches(new BCryptPasswordEncoder().encode(userData.getPassword()))){
                throw new PasswordDoesNotMatchException("Password doesn't match!");
            }
        }
    }
    public List<UserInfo> getAllUsers(){
        return userRepository.findAll();
    }
    public List<UserInfo> getUserById(String userId){
        List<UserInfo> resList = new ArrayList<>();
        if(userRepository.existsById(userId)){
            Optional<UserInfo> userInfo = userRepository.findById(userId);
            userInfo.ifPresent(resList::add);
//            if(userInfo.isPresent()){
//                resList.add(userInfo.get());
//            }
        }
        return resList;
    }
    public List<UserInfo> getUserByEmail(String email){
        List<UserInfo> resList = new ArrayList<>();
        if(userRepository.existsByEmail(email)){
            UserInfo userInfo = userRepository.findByEmail(email);
            resList.add(userInfo);
        }
        return resList;
    }
}


class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message){
        super(message);
    }
}

class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(String message){
        super(message);
    }
}

class NewEmailIdExistsException extends RuntimeException{
    public NewEmailIdExistsException(String message){
        super(message);
    }
}

class PasswordDoesNotMatchException extends RuntimeException{
    public PasswordDoesNotMatchException(String message){
        super(message);
    }
}