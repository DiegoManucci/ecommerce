package com.ecommerce.api.userservice.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_SEX")
public class SexModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sex_id")
    private UUID sexId;

    @Column(name = "description", nullable = false)
    private String description;

    public SexModel(String description) {
        this.description = description;
    }
}
