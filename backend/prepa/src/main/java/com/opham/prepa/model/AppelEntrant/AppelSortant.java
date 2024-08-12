package com.opham.prepa.model.AppelEntrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppelSortant implements Serializable {

    private Long seq;
    private Timestamp calldate_sortant;
    private String src_sortant;
    private String numero_sortant;
    private String numero_indicatif_sortant;
    private String client_sortant;
    private String dcontext_sortant;
    private String dst_sortant;
    private String dstchannel_sortant;
    private String poste_interne_sortant;
    private int duration_sortant;
    private int billsec_sortant;
    private String disposition_sortant;
    private String uniqueid_sortant;
    private int etat_sortant;
    //private String statut;
    //private Long depuis;

}
