package com.opham.prepa.controller;

import com.opham.prepa.model.Apreparer.Commande;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.repository.Apreparer.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

//
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
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

    @PatchMapping("/updateInfoCmd")
    public ResponseEntity<InfoCommande> updateInfoCmd(@RequestBody InfoCommande inf ) {
        int updatedRows = commandeRepository.updateInfoCmd(inf);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/genererBP")
    public ResponseEntity<List<List<Object>>> genererBP(@RequestParam(required = true) String code ) {
        try {
            List<List<Object>>  cmd = commandeRepository.genererBP(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 /*   @GetMapping("/preparerCmd")
    public ResponseEntity<List<List<Object>>> preparerCmd(@RequestParam(required = true) String code ) {
        try {
            List<List<Object>>  cmd = commandeRepository.preparerCmd(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}
