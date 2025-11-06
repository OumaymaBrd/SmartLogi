package org.example.smartspring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisCreationResponseDTO {

    private String message;
    private String numeroSuivi;
}
