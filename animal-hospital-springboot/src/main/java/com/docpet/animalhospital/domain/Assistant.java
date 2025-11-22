package com.docpet.animalhospital.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assistant", uniqueConstraints = {
    @UniqueConstraint(columnNames = "user_id")
})
public class Assistant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return this.id;
    }

    public Assistant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assistant user(User user) {
        this.setUser(user);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assistant)) {
            return false;
        }
        return getId() != null && getId().equals(((Assistant) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Assistant{" +
            "id=" + getId() +
            "}";
    }
}

