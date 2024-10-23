package com.opham.prepa.controller;

import com.opham.prepa.Utils.FileParser;
import com.opham.prepa.model.Utils.*;
import com.opham.prepa.repository.Utils.UtilsRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/emetteur_capa")
    public ResponseEntity<List<String>> listEmetteur( ) {
        try {
            List<String> cmd = utilsRepository.findallemetteur();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/user_capa")
    public ResponseEntity<List<String>> listUser( ) {
        try {
            List<String> cmd = utilsRepository.findalluser();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //code de select
    @GetMapping("/listenumerationcapa")
    public ResponseEntity<List<Proc>> listEnum() {
        try {
            List<Proc> procList = utilsRepository.listEnum();

            if (procList == null || procList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(procList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("General Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/dpt_capa")
    public ResponseEntity<List<String>> listDpt( ) {
        try {
            List<String> cmd = utilsRepository.findalldpt();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/dpt_capa2")
    public ResponseEntity<List<String>> listDpt2( ) {
        try {
            List<String> cmd = utilsRepository.findalldpt2();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //code de select

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

    @GetMapping("/listAlle")
    public ResponseEntity<List<Alle>> listDepot(@RequestParam(required = true) String depot,@RequestParam(required = false) String critere) {
        try {
            List<Alle> cmd = utilsRepository.listAlle(depot,critere);
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

    @GetMapping("/listArticle")
    public ResponseEntity<List<Article>> listArticle(@RequestParam(required = false) String critere) {
        try {
            List<Article> cmd = utilsRepository.listArticle(critere);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listLabo")
    public ResponseEntity<List<Labo>> listLabo(@RequestParam(required = false) String critere) {
        try {
            List<Labo> cmd = utilsRepository.listLabo(critere);
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/listFamille")
    public ResponseEntity<List<Famille>> listFamille() {
        try {
            List<Famille> cmd = utilsRepository.listFamille();
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/local-ip")
    public String getLocalIPAddress(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        return ipAddress;
    }

    @GetMapping("/listClient")
    public ResponseEntity<List<Client>> listClient() {
        try {
            List<Client> cmd = utilsRepository.listClient();
            if (cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //code de cause racine :
    @GetMapping("/causeRac")
    public ResponseEntity<List<CauseRacine>> listCauseRac(@RequestParam(required = true) String code_causerac) {
        try {
            List<CauseRacine> causeRacList = utilsRepository.findcauseraccode(code_causerac);

            if (causeRacList == null || causeRacList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(causeRacList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertcauserac")
    public void insertFNCPS_CAUSE(
            @RequestParam(value = "causeRacCode", required = true) String causeRacCode,
            @RequestParam(value = "causeRacDescription", required = true) String causeRacDescription,
            @RequestParam(value = "causeRacDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date causeRacDate,
            @RequestParam(value = "causeRacValid", required = false, defaultValue = "0") int causeRacValid,
            @RequestParam(value = "causeRacProcImput", required = false) String causeRacProcImput) {

        utilsRepository.insertFNCPS_CAUSE(causeRacCode, causeRacDescription, causeRacDate, causeRacValid ,causeRacProcImput);
    }

    //code de mis a jour :
    @PutMapping("/updatecuserac")
    public void updateFCAUSE_RAC(@RequestParam(value = "causeRacSeq", required = true) int causeRacSeq,
                                 @RequestParam(value = "causeRacCode", required = false) String causeRacCode,
                                 @RequestParam(value = "causeRacDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date causeRacDate,
                                 @RequestParam(value = "causeRacDescription", required = false) String causeRacDescription,
                                 @RequestParam(value = "causeRacProcImput", required = false) String causeRacProcImput,
                                 @RequestParam(value = "causeRacValide", required = false) int causeRacValide) {
        utilsRepository.updateFCAUSE_RAC(causeRacSeq,causeRacCode,causeRacDate,causeRacDescription,causeRacProcImput,causeRacValide);
    }

    @DeleteMapping("/deletecauserac")
    public void deleteFCAUSE_RAC(
            @RequestParam(value = "causeracseq", required = true) int causeracseq) {

        utilsRepository.deleteFCAUSE_RAC(causeracseq);
    }

    //fin code de cause racine


    @PostMapping("/insertuser")
    public void insertFNCPS_USER(
            @RequestParam(value = "ncpsUserUserCode", required = true) String ncpsUserUserCode,
            @RequestParam(value = "ncpsUserNcpsCode", required = false) String ncpsUserNcpsCode) {

        utilsRepository.insertFNCPS_USER(ncpsUserUserCode, ncpsUserNcpsCode);
    }

    @GetMapping("/listuserconsult")
    public ResponseEntity<List<Userinsert>> listUserinsert(@RequestParam(required = true) String ncpsUserNcpsCode) {
        try {
            List<Userinsert> userList = utilsRepository.listUserinsert(ncpsUserNcpsCode);

            if (userList == null || userList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(userList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("General Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/processus")
    public ResponseEntity<List<Proc>> listProc(@RequestParam(required = false) String enumType) {
        try {
            if (enumType == null || enumType.isEmpty()) {
                enumType = "Processus";
            }
            List<Proc> procList = utilsRepository.listProc(enumType);

            if (procList == null || procList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(procList, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("General Error: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/actiontype")
    public ResponseEntity<List<String>> listActionType( ) {
        try {
            List<String> cmd = utilsRepository.findallactiontype();

            if (cmd==null||cmd.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cmd, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insertaction")
    public void insertAction(
            @RequestParam(value = "actionNcpscode", required = true) String actionNcpscode,
            @RequestParam(value = "actionValide", required = false, defaultValue = "0") int actionValide,
            @RequestParam(value = "actionMiseappli", required = false, defaultValue = "0") int actionMiseappli,
            @RequestParam(value = "actionDescription", required = true) String actionDescription,
            @RequestParam(value = "actionCausesseq", required = true) int actionCausesseq,
            @RequestParam(value = "actionType", required = true) int actionType) {

        utilsRepository.insertAction(actionNcpscode, actionValide, actionMiseappli, actionDescription,actionCausesseq, actionType);
    }


}
