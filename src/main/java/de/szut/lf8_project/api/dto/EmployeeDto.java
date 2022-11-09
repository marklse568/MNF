package de.szut.lf8_project.api.dto;

import lombok.Data;

import java.util.Arrays;

@Data
public class EmployeeDto {
    private int id;
    private String lastName;
    private String firstName;
    private String street;
    private String postcode;
    private String city;
    private String phone;
    private String[] skillSet;

    @Override
    public String toString() {
        return "\nEmployee\n" +
                "id: " + id +
                "\nlastName: " + lastName +
                "\nfirstName: " + firstName +
                "\nstreet: " + street +
                "\npostcode: " + postcode +
                "\ncity: " + city +
                "\nphone: " + phone +
                "\nskillSet: " + Arrays.toString(skillSet);
    }
}
