package com.ptit.bb.timgiasu.data.dto;

public class TutorDTO extends UserDTO {

    public TutorDTO() {

    }

    public TutorDTO(String id, String name, String email, String phoneNo, String gender, String dob, String address) {
        super(id, name, email, phoneNo, gender, dob, address);
    }
}
