package org.serverless.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 7031811524953396495L;
    private String email;
    private String passwd;

    public User() {
    }

    public User(String email, String uname, String passwd) {
        this.email = email;
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
