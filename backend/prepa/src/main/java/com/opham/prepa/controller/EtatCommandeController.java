package com.opham.prepa.controller;

import com.opham.prepa.model.EtatCommande.EtatCommande;
import com.opham.prepa.model.Utils.Alle;
import com.opham.prepa.repository.Etatcommande.EtatcommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")

public class EtatCommandeController {
    @Autowired
    EtatcommandeRepository etatcommandeRepository;

    @GetMapping("/etatcommande")
    public ResponseEntity<List<EtatCommande>> onEtatcommande(@RequestParam(required = false) String CodeCommande, @RequestParam(required = false) String CodeClient, @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateDeb, @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateFin , @RequestParam(required = false) String Axe) {
        try {
            List<EtatCommande> cmd = etatcommandeRepository.findEtatcommande(CodeCommande, CodeClient, DateDeb, DateFin , Axe);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
