package com.opham.prepa.controller;

import com.opham.prepa.model.Utils.Axe;
import com.opham.prepa.model.Utils.Enumeration;
import com.opham.prepa.repository.Utils.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE})
@RequestMapping("/api")
public class ComboController {
    @Autowired
    ComboRepository comboRepository;
    @GetMapping("/axe")
    public ResponseEntity<List<Axe>> listAxe( ) {
        try {
            List<Axe> cmd = comboRepository.findAllAxe();

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/enumeration")
    public ResponseEntity<List<Enumeration>> listEnumeration( @RequestParam(required = true) String code) {
        try {
            List<Enumeration> cmd = comboRepository.findParCode(code);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
