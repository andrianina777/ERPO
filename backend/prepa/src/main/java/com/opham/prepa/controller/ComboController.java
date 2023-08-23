package com.opham.prepa.controller;

import com.opham.prepa.Utils.FileParser;
import com.opham.prepa.model.Apreparer.DetailPrep;
import com.opham.prepa.model.Apreparer.InfoCommande;
import com.opham.prepa.model.Apreparer.LigneCommande;
import com.opham.prepa.model.Utils.*;
import com.opham.prepa.repository.Utils.ComboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000","http://192.168.130.69:8081","http://192.168.201.13:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
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

    @PostMapping("/lock")
    public ResponseEntity<Lock> getLock(@RequestParam(required = true) String xTable,@RequestParam(required = true) String xCode,@RequestParam(required = true) int  isClose ) {
        Lock lock = comboRepository.getLock(xTable,xCode,isClose);
        if (lock !=null) {
            return new ResponseEntity<>(lock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/droit")
    public ResponseEntity<Droit> getDroid(@RequestParam(required = true) String xUser,@RequestParam(required = true) String access ) {
        try {
            Droit cmd = comboRepository.getDoit(xUser,access);

            if (cmd!=null) {
                return new ResponseEntity<>(cmd, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDroit")
    public ResponseEntity<List<Droit>> listDoit(@RequestParam(required = true) String xUser) {
        try {
            List<Droit> cmd = comboRepository.listDoit(xUser);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDepot")
    public ResponseEntity<List<Depot>> listDepot(@RequestParam(required = true) String critaire) {
        try {
            List<Depot> cmd = comboRepository.listDepot(critaire);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/listServer")
    public ResponseEntity<List<String>> listNames() {
        try {
 /*           InputStream inputStream = getClass().getClassLoader().getResourceAsStream("../file/interfaces");

            if (inputStream == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }*/

            FileParser fileParser = new FileParser();

            List<String> names = fileParser.extractNames("interfaces");

            if (names.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(names, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
