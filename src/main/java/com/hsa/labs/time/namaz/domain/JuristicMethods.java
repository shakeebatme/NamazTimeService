package com.hsa.labs.time.namaz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JuristicMethods {

    private boolean shafii; // Shafii (standard)
    private boolean hanafi; // Hanafi
}
