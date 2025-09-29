package com.example.cardatabase4.web;

import com.example.cardatabase4.domain.Owner;
import com.example.cardatabase4.domain.OwnerRepository;
import com.example.cardatabase4.service.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService onwerService) {
        this.ownerService = onwerService;
    }
    @GetMapping("/owners/{ownerId}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable Long ownerId){
        return ownerService.getOwnerById(ownerId)
                .map(owner -> ResponseEntity.ok().body(owner))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/owners")
    public List<Owner> getOwner(){
        return ownerService.getOwners();
    }
    @PostMapping("/owners")
    public ResponseEntity<Owner> addOwner(@RequestBody Owner owner){
        Owner savedOwner = ownerService.addOwner(owner);

        return new ResponseEntity<>(savedOwner, HttpStatus.CREATED);
    }
    @DeleteMapping("/owners/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long ownerId){
        if(ownerService.deleteOwner(ownerId)){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/owners/{ownerId}")
    public ResponseEntity<Owner> updateOnwer(@PathVariable Long ownerId, @RequestBody Owner ownerDetails){
        return ownerService.updateOwner(ownerId,ownerDetails)
                .map(updateOnwer -> ResponseEntity.ok().body(updateOnwer))
                .orElse(ResponseEntity.notFound().build());
    }
}
