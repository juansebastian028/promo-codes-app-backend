package com.indesap.codes.promotional_codes;

import com.indesap.codes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface PromotionalCodeRepository extends JpaRepository<PromotionalCode, Long> {
    Optional<PromotionalCode> findPromotionalCodeByCode(String code);
}