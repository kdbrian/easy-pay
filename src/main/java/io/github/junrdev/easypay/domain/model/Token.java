package io.github.junrdev.easypay.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "generatedtoken")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    @Id
    private String token;
    private String issuedAt;

}
