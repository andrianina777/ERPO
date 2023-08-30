package com.opham.prepa.controller;

import com.opham.prepa.Utils.FileParser;
import com.opham.prepa.model.Utils.*;
import com.opham.prepa.repository.Utils.UtilsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
//@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.130.64:3000","http://192.168.130.69:8081","http://192.168.201.13:3000","http://192.168.201.212:3000"},methods = {RequestMethod.GET,RequestMethod.PUT,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PATCH})
@RequestMapping("/api")
public class UtilsController {

    @Autowired
    UtilsRepository utilsRepository;

    @GetMapping("/axe")
    public ResponseEntity<List<String>> listAxe( ) {
        try {
            List<String> cmd = utilsRepository.findAllAxe();

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
            List<String> cmd = utilsRepository.findParCode(code);

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
              Alerte cmd = utilsRepository.getAlerte(code);

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
            Etape cmd = utilsRepository.getEtape();

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
        Lock lock = utilsRepository.getLock(xTable,xCode,isClose);
        if (lock !=null) {
            return new ResponseEntity<>(lock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/droit")
    public ResponseEntity<Droit> getDroid(@RequestParam(required = true) String xUser,@RequestParam(required = true) String access ) {
        try {
            Droit cmd = utilsRepository.getDoit(xUser,access);

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
            List<Droit> cmd = utilsRepository.listDoit(xUser);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listDepot")
    public ResponseEntity<List<Depot>> listDepot(@RequestParam(required = false) String critere) {
        try {
            List<Depot> cmd = utilsRepository.listDepot(critere);
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
            FileParser fileParser = new FileParser();

            List<String> names = fileParser.extractNames("/Users/anjaniainaandrianina/Desktop/Preparation Aout2023/Backend Aout 2023/ERPO/backend/prepa/interfaces");

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
