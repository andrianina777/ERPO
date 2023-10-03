package com.opham.prepa.model.Transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuiviTD implements Serializable {
    private String code;
    private Date date;
    private String empl_org;
    private String libelle;
    private int qte;
    private String empl_dest;
    private String commentaire;
    private String motif;
    private Timestamp date_entree;
    private String users;



}
