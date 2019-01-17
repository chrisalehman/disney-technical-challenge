package com.disney.studios.petapp.domain.internal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
public class Client extends Auditable {

    public Client(String clientName, String sharedSecret) {
        super();
        this.clientName = clientName;
        this.sharedSecret = sharedSecret;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 128)
    private String clientName;

    @NotBlank
    @Size(max = 128)
    private String sharedSecret;
}