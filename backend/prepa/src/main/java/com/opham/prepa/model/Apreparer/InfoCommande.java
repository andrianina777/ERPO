package com.opham.prepa.model.Apreparer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoCommande implements Serializable {

    private String code_cc;
    private String nom_client;

    private String adresse;

    private String axe;

    private Timestamp date_liv;

    private String coms_client;

    private String coms_mag;

    private String contact;


}
