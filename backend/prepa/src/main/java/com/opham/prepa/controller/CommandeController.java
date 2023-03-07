package com.opham.prepa.controller;

import com.opham.prepa.model.Commande;
import com.opham.prepa.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//
@CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST})
@RestController
@RequestMapping("/api")
public class CommandeController  {
@Autowired
CommandeRepository commandeRepository;

    @GetMapping("/prep")
    public ResponseEntity<List<Commande>> findByDate(@RequestParam(required = false,defaultValue="DET") String groupe,@RequestParam(required = false,defaultValue="") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateliv ) {
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
//    @GetMapping("/prep")
//    public ResponseEntity<List<Commande>> findByAll() {
//        try {
//            List<Commande> cmd = commandeRepository.findAll();
//
//            if (cmd.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(cmd, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
