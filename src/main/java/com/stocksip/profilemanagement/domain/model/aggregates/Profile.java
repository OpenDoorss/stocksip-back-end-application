package com.stocksip.profilemanagement.domain.model.aggregates;

import com.stocksip.profilemanagement.domain.model.valueobjects.*;
import com.stocksip.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;

@Entity
public class Profile extends AuditableAbstractAggregateRoot {

    @Embedded
    private UserName name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "email", column = @Column(name = "email"))})
    private UserEmail email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number"))})
    private UserPhoneNumber phoneNumber;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "business_address", column = @Column(name = "address"))})
    private BusinessAddress businessAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "business_name", column = @Column(name = "business_name"))})
    private BusinessName businessName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "role", column = @Column(name = "role"))})
    private UserRole role;

    public Profile(String UserName, String UserEmail, String UserPhoneNumber, String BusinessAddress, String BusinessName, String UserRole) {
        this.name = new UserName(UserName);
        this.email = new UserEmail(UserEmail);
        this.phoneNumber = new UserPhoneNumber(UserPhoneNumber);
        this.businessAddress = new BusinessAddress(BusinessAddress);
        this.businessName = new BusinessName(BusinessName);
        this.role = new UserRole(UserRole);
    }

    public Profile() {}


}