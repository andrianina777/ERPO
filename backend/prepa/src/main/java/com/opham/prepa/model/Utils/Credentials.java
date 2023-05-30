package com.opham.prepa.model.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials implements Serializable {
    private String username;
    private String password;

//    public void setPassword(String password) {
//        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//    public boolean isPasswordValid(String password) {
//        return BCrypt.checkpw(password, this.password);
//    }
}