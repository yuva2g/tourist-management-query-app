package com.cognizant.fse.assignment.touristmanagementqueryapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class TouristPlaceDTO implements Serializable {

    private Long id;

    @NotBlank
    private String name;
    private double tariff;

}