package com.opham.prepa.controller;

import com.opham.prepa.model.Commande;
import com.opham.prepa.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

//
//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CommandeController  {


@Autowired
CommandeRepository commandeRepository;
    @GetMapping("/prep/{group}/{dateliv}")
    public ResponseEntity<List<Commande>> findByDate(@PathVariable("groupe") String groupe,@PathVariable("dateliv") Date dateliv ) {
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
    @GetMapping("/prep")
    public ResponseEntity<List<Commande>> findByAll() {
        try {
            List<Commande> cmd = commandeRepository.findAll();

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
