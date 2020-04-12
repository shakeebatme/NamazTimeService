package com.hsa.labs.time.namaz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class AdjustingMethodsForHigherLatitudes {

    // Adjusting Methods for Higher Latitudes
    private boolean None; // No adjustment
    private boolean MidNight; // middle of night
    private boolean OneSeventh; // 1/7th of night
    private boolean AngleBased; // angle/60th of night

}
