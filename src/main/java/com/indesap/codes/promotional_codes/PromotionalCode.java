package com.indesap.codes.promotional_codes;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.indesap.codes.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PROMOTIONAL_CODE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionalCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Integer discount;
    private String descText;
    private Integer counter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
