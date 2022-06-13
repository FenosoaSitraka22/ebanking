package org.sid.ebanking2.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking2.dtos.CustumerDTO;
import org.sid.ebanking2.exceptions.CustumerNotFoundException;
import org.sid.ebanking2.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustumerRestController {
    private BankAccountService bankAccountService;
    @RequestMapping("/custumers")
    public List<CustumerDTO> custumers(){
        return bankAccountService.listCustumers();
    }
    @GetMapping("/custumers/{idCustumer}")
    public CustumerDTO getCustumerDTO(@PathVariable(name="idCustumer")Long idCustumer) throws CustumerNotFoundException {
        return bankAccountService.getCustumerDTO(idCustumer);
    }
    @PutMapping("/custumers/{idCustumer}")
    public CustumerDTO updateCustumer(@PathVariable(name = "idCustumer") Long idCustumer, @RequestBody CustumerDTO custumerDTO){
        //@RequestBody les données concernant le custumerDTO vient du corrp du requette en format JSON
        custumerDTO.setId(idCustumer);
        return bankAccountService.saveCustumer(custumerDTO);
    }
    @DeleteMapping("/custumers/{idCustumer}")
    public void deleteCustumer(@PathVariable(name = "idCustumer") Long idCustumer){
        bankAccountService.deleteCustumer(idCustumer);
    }
    @PostMapping("/custumers") ///mbl ts mety temps restant 1:40:00
    public CustumerDTO saveCustumer(@RequestBody CustumerDTO custumerDTO){
        return  bankAccountService.saveCustumer(custumerDTO);
    }
    @GetMapping("/custumers/search{keyWord}")
    public List<CustumerDTO> findCustumersByName(@PathVariable(name = "keyWord") String keyWord){
       return  bankAccountService.fyndCustumer(keyWord);
    }
}
