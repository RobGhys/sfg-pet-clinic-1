package guru.springframework.sfgpetclinic.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass // The superclass is not associated with any database table.
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //we use boxed type Long instead of long

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
