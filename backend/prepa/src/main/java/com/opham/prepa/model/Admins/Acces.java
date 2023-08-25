package com.opham.prepa.model.Admins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acces implements Serializable {
    private String code;
    private String desc;

}
