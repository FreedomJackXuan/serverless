package org.serverless.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class EarnPojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String uuid;
    private String server;
    private float money;
    private int clickNum;

    @Override
    public String toString() {
        return "EarnPojo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", server='" + server + '\'' +
                ", money=" + money +
                ", clickNum=" + clickNum +
                '}';
    }
}
