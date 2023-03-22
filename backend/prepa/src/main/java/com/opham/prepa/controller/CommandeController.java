package com.opham.prepa.controller;

import com.opham.prepa.model.Commande;
import com.opham.prepa.model.DetailPrep;
import com.opham.prepa.model.InfoCommande;
import com.opham.prepa.model.LigneCommande;
import com.opham.prepa.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE})

@RequestMapping("/api")
public class CommandeController  {
@Autowired
CommandeRepository commandeRepository;

    @GetMapping("/prep")
    public ResponseEntity<List<Commande>> findByDate(@RequestParam(required = false,defaultValue="") String groupe,@RequestParam(required = false,defaultValue="") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv ) {
        try {
            List<Commande> cmd = commandeRepository.findByDate(groupe,dateliv);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/lignecmd")
    public ResponseEntity<List<LigneCommande>> findLigneCmdebyCode(@RequestParam(required = true) String code ) {
        try {
            List<LigneCommande> cmd = commandeRepository.findLigneCmdebyCode(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDetailPrepa")
    public ResponseEntity<List<DetailPrep>> listDetailPrepa(@RequestParam(required = true) String code ) {
        try {
            List<DetailPrep> cmd = commandeRepository.listDetailBP(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/infoCmd")
    public ResponseEntity<InfoCommande> getInfoCommande(@RequestParam(required = true) String code ) {
        try {
            InfoCommande cmd = commandeRepository.plusInfoCmd(code);

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);

            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
