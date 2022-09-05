package com.api.ecommerce.userservice.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESS")
public class AddressModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private UUID addressId;

    @ManyToOne
    @JoinColumn(name = "user_id",
                referencedColumnName = "user_id")
    private UserModel userModel;


    @Column(name = "postcode", nullable = false, length = 9)
    private String postcode;

    @Column(name = "public_place", nullable = false, length = 255)
    private String publicPlace;

    @Column(name = "number", nullable = false, precision = 4)
    private Integer number;

    @Column(name = "complement", length = 255)
    private String complement;

    @Column(name = "district", nullable = false, length = 255)
    private String district;

    @Column(name = "city", nullable = false, length = 255)
    private String city;

    //ADD TABLE FOR UF
    @Column(name = "federative_unit", nullable = false, length = 2)
    private String federativeUnit;

    public AddressModel() {

    }

    public AddressModel(UUID addressId, UserModel userModel, String postcode, String publicPlace, Integer number, String complement, String district, String city, String federativeUnit) {
        this.addressId = addressId;
        this.userModel = userModel;
        this.postcode = postcode;
        this.publicPlace = publicPlace;
        this.number = number;
        this.complement = complement;
        this.district = district;
        this.city = city;
        this.federativeUnit = federativeUnit;
    }

    //region Getters/Setters

    public UUID getAddressId() {
        return addressId;
    }
    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public UserModel getUserModel() {
        return userModel;
    }
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPublicPlace() {
        return publicPlace;
    }
    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }
    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDistrict() {
        return district;
    }
    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getFederativeUnit() {
        return federativeUnit;
    }
    public void setFederativeUnit(String federativeUnit) {
        this.federativeUnit = federativeUnit;
    }

    //endregion
}
