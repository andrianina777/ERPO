package com.opham.prepa.model.Transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetitColis implements Serializable {
    private String article;
    private String libelle;
    private String lot;
    private Date datePer;
    private String depot_org;
    private String empl_org;
    private int colisage;
    private int palette;
    private int nbColis;
    private int perc;
    private int stock_det;
    private String depot_dest;
    private String empl_dest;
    private int qte;
    private int lot_vte;
    private Double nb_j_vte;
    private boolean etat;
}
