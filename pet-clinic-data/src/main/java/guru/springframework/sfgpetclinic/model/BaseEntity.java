package guru.springframework.sfgpetclinic.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    private Long id; //we use boxed type Long instead of long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
