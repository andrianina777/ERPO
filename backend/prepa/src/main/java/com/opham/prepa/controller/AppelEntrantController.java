package com.opham.prepa.controller;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import com.opham.prepa.model.AppelEntrant.AppelSortant;
import com.opham.prepa.model.AppelEntrant.ActionFirstAppel;
import com.opham.prepa.repository.AppelEntrant.AppelEntrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")

public class AppelEntrantController {

    @Autowired
    AppelEntrantRepository appelentrantRepository;

    @GetMapping("/appelentrant")

    public ResponseEntity<List<AppelEntrant>> onAppelEntrant(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_deb, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date_fin) {
        try {
            List<AppelEntrant> cmd = appelentrantRepository.findAppelEntrant(date_deb ,date_fin);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    AppelEntrantRepository appelsortantrepository;

    @GetMapping("/appelsortant")

    public ResponseEntity<List<AppelSortant>> onAppelSortant(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date_sortant, @RequestParam(required = false)  String numero) {
        try {
            List<AppelSortant> cmd = appelsortantrepository.findAppelSortant(date_sortant ,numero);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    AppelEntrantRepository actionfirstappelrepository;

    @GetMapping("/listAction")

    public ResponseEntity<List<ActionFirstAppel>> onActionFirstAppel() {
        try {
            List<ActionFirstAppel> cmd = actionfirstappelrepository.findActionFirstAppel();
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PutMapping("/updateEtat")
    public ResponseEntity<String> updateFrais(@RequestParam Long AppelId) {
        appelentrantRepository.updateEtat(AppelId);
        return ResponseEntity.status(HttpStatus.OK).body("update Etat successfully");
    }

    @PutMapping("/updateEtat1")
    public ResponseEntity<String> updateEtat(@RequestParam Long AppelId) {
        appelentrantRepository.updateEtat(AppelId);
        return ResponseEntity.status(HttpStatus.OK).body("update Etat to 2 successfully");
    }
    @PostMapping("/updateEtatSortie")
    public ResponseEntity<String> updateEtatSortie(@RequestParam BigDecimal seq,@RequestParam int etat,@RequestParam BigDecimal seqSortie) {

        try {
            int rowsAffected = appelentrantRepository.updateEtatSortie(seq,etat,seqSortie);
            if (rowsAffected > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("resultat update vel avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Échec de l'update vl ");
            }
        } catch (DataAccessException e) {
            // Log the exception details

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour vl");
        }

    }





}
