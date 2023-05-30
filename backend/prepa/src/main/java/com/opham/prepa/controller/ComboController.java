package com.opham.prepa.controller;

import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Utils.Alerte;
import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;
import com.opham.prepa.model.Utils.Etape;
import com.opham.prepa.repository.Utils.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequestMapping("/api")
public class ComboController {

    @Autowired
    ComboRepository comboRepository;

    @GetMapping("/axe")
    public ResponseEntity<List<String>> listAxe( ) {
        try {
            List<String> cmd = comboRepository.findAllAxe();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/enumeration")
    public ResponseEntity<List<String>> listEnumeration( @RequestParam(required = true) String code) {
        try {
            List<String> cmd = comboRepository.findParCode(code);

            if (cmd.isEmpty()|| cmd==null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/timing")
    public ResponseEntity<Alerte> getAlerte(@RequestParam(required = true) String code ) {
        try {
            Alerte cmd = comboRepository.getAlerte(code);

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/etape")
    public ResponseEntity<Etape> getEtape( ) {
        try {
            Etape cmd = comboRepository.getEtape();

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
