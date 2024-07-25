package com.opham.prepa.controller;

import com.opham.prepa.model.AppelEntrant.AppelEntrant;
import com.opham.prepa.repository.AppelEntrant.AppelEntrantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
