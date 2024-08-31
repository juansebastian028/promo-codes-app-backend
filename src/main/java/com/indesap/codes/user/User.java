package com.indesap.codes.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.indesap.codes.promotional_codes.PromotionalCode;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "APP_USER", uniqueConstraints = { @UniqueConstraint(columnNames = {"email"}) })
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PromotionalCode> promotionalCodes;
}
