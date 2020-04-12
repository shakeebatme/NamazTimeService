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
public class TimeFormatter {

    // Time Formats
    private boolean time24; // 24-hour format
    private boolean Time12; // 12-hour format
    private boolean Time12NS; // 12-hour format with no suffix
    private boolean Floating; // floating point number
}
