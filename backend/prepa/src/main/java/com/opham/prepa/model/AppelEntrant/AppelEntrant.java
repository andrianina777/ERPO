package com.opham.prepa.model.AppelEntrant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppelEntrant implements Serializable {

    private Long seq;
    private Timestamp calldate;
    private String src;
    private String numero;
    private String numero_indicatif;
    private String client;
    private String dcontext;
    private String dst;
    private String dstchannel;
    private String poste_interne;
    private int duration;
    private int billsec;
    private String disposition;
    private String uniqueid;
    private int etat;
    private String statut;
    private Long depuis;
    private Timestamp daterappel_1;
    private Timestamp daterappel_2;
  //  private Timestamp datecre;

}
