package com.indesap.codes.promotional_codes;

import com.indesap.codes.config.ApiError;
import com.indesap.codes.config.PromotionalCodeNotFoundException;
import com.indesap.codes.user.User;
import com.indesap.codes.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/promo-codes")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class PromotionalCodeController {
    private final PromotionalCodeService promotionalCodeService;
    private final UserRepository userRepository;
    private final PromotionalCodeRepository promotionalCodeRepository;

    @PostMapping(value="redeem")
    public ResponseEntity<?> redeemPromotionalCode(@RequestParam String email, @RequestParam String code) {
        try {
            User finalUser = getOrCreateUser(email);
            PromotionalCode promotionalCode = getPromotionalCode(code);
            return handleCodeRedemption(finalUser, promotionalCode);
        } catch (PromotionalCodeNotFoundException e) {
            ApiError error = new ApiError("Código promocional no encontrado", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    private User getOrCreateUser(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseGet(() -> userRepository.save(User.builder().email(email).build()));
    }

    private PromotionalCode getPromotionalCode(String code) throws PromotionalCodeNotFoundException {
        return promotionalCodeRepository.findPromotionalCodeByCode(code)
                .orElseThrow(() -> new PromotionalCodeNotFoundException("Código promocional no encontrado"));
    }

    private ResponseEntity<?> handleCodeRedemption(User user, PromotionalCode code) {
        if (code.getUser() != null) {
            if (!code.getUser().getEmail().equals(user.getEmail())) {
                ApiError error = new ApiError("El código promocional ya ha sido canjeado por otro usuario", HttpStatus.FORBIDDEN.value());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            } else {
                code.setCounter(code.getCounter() + 1);
                promotionalCodeRepository.save(code);
                return ResponseEntity.ok(code);
            }
        }
        code.setUser(user);
        code.setCounter(0);
        promotionalCodeRepository.save(code);
        return ResponseEntity.ok(code);
    }

    @PostMapping(value = "register")
    public ResponseEntity<Object> registerPromotionalCode(@RequestBody PromotionalCode promotionalCode) throws IllegalAccessException {
        return this.promotionalCodeService.newPromotionalCode(promotionalCode);
    }
}

