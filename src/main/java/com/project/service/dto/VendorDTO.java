package com.project.service.dto;


import com.project.config.Constants;
import com.project.domain.Authority;

import com.project.domain.User;
import com.project.domain.Vendor;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;


public class VendorDTO {

    private Long vendorId;

    private User userId;


    @Size(max = 50)
    private String fullName;

    @Size(max = 50)
    private String address;

    @Size(max = 50)
    private String website;

    @Size(max = 50)
    private String contactPerson;

    @Size(max = 50)
    private String zipCode;
    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;






    public VendorDTO() {
        // Empty constructor needed for Jackson.
    }


    public VendorDTO(Vendor vendor) {
        this.userId = vendor.getUser();
        this.vendorId=vendor.getId();
        this.address=vendor.getAddress();
        this.contactPerson=vendor.getContactPerson();
        this.website=vendor.getWebsite();
        this.zipCode=vendor.getZipCode();
        this.login = vendor.getUser().getLogin();
        this.fullName = vendor.getFullName();
        this.email = vendor.getEmail();
        this.activated = vendor.getUser().getActivated();
        this.imageUrl = vendor.getUser().getImageUrl();
        this.langKey = vendor.getUser().getLangKey();
        this.createdBy = vendor.getUser().getCreatedBy();
        this.createdDate = vendor.getUser().getCreatedDate();
        this.lastModifiedBy = vendor.getUser().getLastModifiedBy();
        this.lastModifiedDate = vendor.getUser().getLastModifiedDate();
        this.authorities = vendor.getUser().getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    @Override
    public String toString() {
        return "VendorDTO{" +
            "vendorId=" + vendorId +
            ", userId=" + userId +
            ", fullName='" + fullName + '\'' +
            ", address='" + address + '\'' +
            ", website='" + website + '\'' +
            ", contactPerson='" + contactPerson + '\'' +
            ", zipCode='" + zipCode + '\'' +
            ", login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            '}';
    }
}
