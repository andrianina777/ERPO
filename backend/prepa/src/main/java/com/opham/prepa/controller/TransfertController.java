package com.opham.prepa.controller;

import com.opham.prepa.repository.Transfert.TransfertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api")
public class TransfertController {
    @Autowired
    TransfertRepository transfertRepository;

    @GetMapping("/rotation")
    public ResponseEntity<List<List<Object>>> listRotation(@RequestParam(required = true)  String depot,@RequestParam(required = false)  String alle) {
        try {
            List<List<Object>> cmd = transfertRepository.listRotation(depot,alle);

            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
