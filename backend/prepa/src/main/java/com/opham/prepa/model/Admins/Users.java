package com.opham.prepa.model.Admins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users implements Serializable {
    private String  name;
    private String fullname;
}
