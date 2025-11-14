package com.docpet.animalhospital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrePersist;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "jhi_user")
@DynamicInsert // Chỉ insert các cột có giá trị, sử dụng DEFAULT cho các cột null
// Tắt AuditingEntityListener cho User entity để tự quản lý auditing fields
// Vì AuditingEntityListener có thể set null khi không có user đăng nhập
@EntityListeners({}) // Empty array để tắt listener từ AbstractAuditingEntity
public class User extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$")
    @Size(min = 1, max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Column(name = "lang_key", length = 10)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate = null;

    // Override createdBy field để tự quản lý, không dùng @CreatedBy annotation
    @Column(name = "created_by", nullable = false, length = 50, updatable = false)
    private String createdBy;

    // Override createdDate field để tự quản lý
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    // Override lastModifiedBy field để tự quản lý
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    // Override lastModifiedDate field để tự quản lý
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "jhi_user_authority",
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    /**
     * @PrePersist callback để đảm bảo auditing fields luôn có giá trị
     * Chạy sau AbstractAuditingEntity.prePersist() để đảm bảo giá trị không null
     */
    @PrePersist
    private void ensureAuditingFields() {
        // Gọi super để xử lý logic cơ bản
        super.prePersist();
        
        // Debug log
        System.out.println("=== User @PrePersist called ===");
        System.out.println("createdBy after super: " + this.createdBy);
        System.out.println("login: " + this.getLogin());
        
        // Đảm bảo createdBy không null - ưu tiên giá trị đã set, nếu không thì dùng login hoặc "system"
        // Override field nên cần set lại vào field local
        if (this.createdBy == null || this.createdBy.trim().isEmpty()) {
            String login = this.getLogin();
            this.createdBy = (login != null && !login.trim().isEmpty()) ? login : "system";
            System.out.println("Set createdBy to: " + this.createdBy);
        }
        // Đảm bảo createdDate không null
        if (this.createdDate == null) {
            this.createdDate = Instant.now();
        }
        // Đảm bảo lastModifiedBy không null
        if (this.lastModifiedBy == null || this.lastModifiedBy.trim().isEmpty()) {
            this.lastModifiedBy = this.createdBy;
        }
        // Đảm bảo lastModifiedDate không null
        if (this.lastModifiedDate == null) {
            this.lastModifiedDate = Instant.now();
        }
        
        System.out.println("createdBy final: " + this.createdBy);
        System.out.println("=== User @PrePersist end ===");
    }

    // Override getter/setter để sử dụng field local thay vì từ AbstractAuditingEntity
    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    @Override
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}

