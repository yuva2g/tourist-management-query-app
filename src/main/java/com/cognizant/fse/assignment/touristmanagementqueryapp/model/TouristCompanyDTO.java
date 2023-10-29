package com.cognizant.fse.assignment.touristmanagementqueryapp.model;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Getter
public class TouristCompanyDTO implements Serializable {

    private Long id;

    @NotBlank
    private String branchName;

    @NotBlank
    private String website;

    @NotBlank
    private String contact;

    @NotBlank
    @Email
    private String email;

    @NotEmpty
    private List<TouristPlaceDTO> touristPlaces;
}