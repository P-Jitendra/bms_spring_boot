package com.example.bms_backend.UserControllers;

import com.example.bms_backend.UserRepository.UserInfo;
import com.example.bms_backend.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/create-new-users")
    public ResponseEntity<ReturnMessage> createNewUsers(@RequestBody @Valid UserInfoRequest userInfo){
        try{
            List<UserDTO> usersList = userInfo.getUsersList();
            userService.createNewUsers(usersList);
            ReturnMessage returnObj = new ReturnMessage("success", "Created given new user data.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnObj);
        }
        catch (RuntimeException exception){
            if(exception.getMessage().equals("One of user data already exists in database.")){
                return ResponseEntity.badRequest().body(new ReturnMessage("failure", "One of user data already exists in database."));
            }
            return ResponseEntity.badRequest().body(new ReturnMessage("failure", "Transaction failure while adding new user data."));
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<ReturnMessage> updateUserData(@RequestBody @Valid UserInfo userInfo){
        try {
            userService.updateUserData(userInfo);
            ReturnMessage returnObj = new ReturnMessage("success", "Updated the user details successfully!");
            return ResponseEntity.accepted().body(returnObj);
        }
        catch (RuntimeException exception){
            if(exception.getMessage().equals("Given UserID doesn't exist!")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnMessage("failure", exception.getMessage()));
            }
            return ResponseEntity.badRequest().body(new ReturnMessage("failure", exception.getMessage()));
        }
    }

    @PostMapping("/check-user-existence/user-id")
    public ResponseEntity<ReturnMessage> findByUserId(@RequestBody @Valid CheckUserIdRequest userExistenceData){
        try{
            userService.checkIfUserExists(userExistenceData);
            ReturnMessage returnObj = new ReturnMessage("success", "Given user data exists in database.");
            return ResponseEntity.ok().body(returnObj);
        }
        catch (RuntimeException exception){
            if(exception.getMessage().equals("UserId doesn't exist!")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnMessage("failure", exception.getMessage()));
            }
            return ResponseEntity.badRequest().body(new ReturnMessage("failure", exception.getMessage()));
        }
    }

    @PostMapping("/check-user-existence/email")
    public ResponseEntity<ReturnMessage> findByEmail(@RequestBody @Valid CheckEmailRequest emailRequestData){
        try{
            userService.checkIfUserExistsBasedOnEmail(emailRequestData);
            ReturnMessage returObj = new ReturnMessage("success", "Given user data exists in database.");
            return ResponseEntity.ok().body(returObj);
        }
        catch(RuntimeException exception){
            if(exception.getMessage().equals("UserId doesn't exist!")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReturnMessage("failure", exception.getMessage()));
            }
            return ResponseEntity.badRequest().body(new ReturnMessage("failure", exception.getMessage()));
        }
    }

    @GetMapping("/all-userinfo")
    public ResponseEntity<ReturnGetMessage> getAllUsers(){
        try{
            List<UserInfo> data = userService.getAllUsers();
            ReturnGetMessage returnObj = new ReturnGetMessage("success", data);
            return ResponseEntity.ok().body(returnObj);
        }
        catch (RuntimeException exception){
            return ResponseEntity.internalServerError().body(new ReturnGetMessage("failure", new ArrayList<>()));
        }
    }

    @GetMapping("/from-id/{userId}")
    public ResponseEntity<ReturnGetMessage> getUserById(@PathVariable String userId){
        try{
            List<UserInfo> usersList = userService.getUserById(userId);
            ReturnGetMessage returnObj = new ReturnGetMessage("success", usersList);
            return ResponseEntity.ok().body(returnObj);
        }
        catch (RuntimeException exception){
            return ResponseEntity.internalServerError().body(new ReturnGetMessage("failure", new ArrayList<>()));
        }
    }

    @GetMapping("/from-email")
    public ResponseEntity<ReturnGetMessage> getUserByEmail(@RequestParam(required = true) String userEmail){
        try{
            List<UserInfo> usersList = userService.getUserByEmail(userEmail);
            ReturnGetMessage returnObj = new ReturnGetMessage("success", usersList);
            return ResponseEntity.ok().body(returnObj);
        }
        catch(RuntimeException exception){
            return ResponseEntity.internalServerError().body(new ReturnGetMessage("failure", new ArrayList<>()));
        }
    }
}
