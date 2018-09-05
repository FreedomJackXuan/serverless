package org.serverless.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class ServicePojo implements Serializable {
    private static final long serialVersionUID = 3062566527428551280L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String uuid;
    private String servername;
    private String path;
    private String type;

    public ServicePojo() {
    }

    public ServicePojo(String uuid, String servername, String path, String type) {
        this.uuid = uuid;
        this.servername = servername;
        this.path = path;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ServicePojo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", uuid='" + uuid + '\'' +
                ", servername='" + servername + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
