package com.indesap.codes.promotional_codes;


import com.indesap.codes.user.User;
import com.indesap.codes.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class PromotionalCodeService {
    private final PromotionalCodeRepository promotionalCodeRepository;

    public PromotionalCodeService(PromotionalCodeRepository promotionalCodeRepository, UserRepository userRepository) {
        this.promotionalCodeRepository = promotionalCodeRepository;
    }

    public Optional<PromotionalCode> findByCode(String code) {
        return promotionalCodeRepository.findPromotionalCodeByCode(code);
    }

    public ResponseEntity<Object> newPromotionalCode(PromotionalCode promotionalCode) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("message", "Se guardó con exitó");
        promotionalCodeRepository.save(promotionalCode);
        data.put("data", promotionalCode);
        return new ResponseEntity<>(
                data,
                HttpStatus.CREATED
        );
    }
}
