package itmo.dev.api_gateway.controllers;

import itmo.dev.api_gateway.dto.OwnerDto;
import itmo.dev.serviceslayer.services.interfaces.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {

        this.ownerService = ownerService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerDto ownerDto) {

        return new ResponseEntity<>(ownerService.createOwner(ownerDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OwnerDto>> getAllOwners() {

        return new ResponseEntity<>(ownerService.getAllOwners(), HttpStatus.OK);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> getOwnerById(@PathVariable Integer ownerId) {

        return ResponseEntity.ok(ownerService.getOwnerById(ownerId));
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<OwnerDto> updateOwner(@PathVariable Integer ownerId, @RequestBody OwnerDto ownerDto) {

        return ResponseEntity.ok(ownerService.updateOwner(ownerDto, ownerId));
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<String> deleteOwner(@PathVariable Integer ownerId) {

        ownerService.deleteOwner(ownerId);

        return new ResponseEntity<>("Owner deleted", HttpStatus.OK);
    }
}
