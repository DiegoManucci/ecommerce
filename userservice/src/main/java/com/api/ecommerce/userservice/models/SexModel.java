package com.api.ecommerce.userservice.models;

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

    @Column(name = "description", nullable = false, length = 9)
    private String description;

    public SexModel() {

    }
    public SexModel(String description) {
        this.description = description;
    }

    //region Getters/Setters

    public UUID getSexId() {
        return sexId;
    }
    public void setSexId(UUID sexId) {
        this.sexId = sexId;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    //endregion

}
