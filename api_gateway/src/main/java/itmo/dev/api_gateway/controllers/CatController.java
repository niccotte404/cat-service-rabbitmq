package itmo.dev.api_gateway.controllers;

import itmo.dev.api_gateway.dto.CatDto;
import itmo.dev.serviceslayer.services.interfaces.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owner")
public class CatController {

    private CatService catService;

    @Autowired
    public CatController(CatService catService) {

        this.catService = catService;
    }

    @PostMapping("/{ownerId}/cat")
    public ResponseEntity<CatDto> createCat(@PathVariable Integer ownerId, @RequestBody CatDto catDto) {

        return ResponseEntity.ok(catService.createCat(catDto, ownerId));
    }

    @GetMapping("/cats")
    public ResponseEntity<List<CatDto>> getAllCats() {

        return ResponseEntity.ok(catService.getAllCats());
    }

    @GetMapping("/{ownerId}/cat/{catId}")
    public ResponseEntity<CatDto> getCatById(@PathVariable Integer ownerId, @PathVariable Integer catId) {

        return ResponseEntity.ok(catService.getCatById(ownerId, catId));
    }

    @GetMapping("/{ownerId}/cats")
    public ResponseEntity<List<CatDto>> getAllCatsByOwnerId(@PathVariable Integer ownerId) {

        return ResponseEntity.ok(catService.getCatsByOwnerId(ownerId));
    }

    @PutMapping("/{ownerId}/cat/{catId}/update")
    public ResponseEntity<CatDto> updateCat(@PathVariable Integer ownerId,
                                            @PathVariable Integer catId,
                                            @RequestBody CatDto catDto) {

        return ResponseEntity.ok(catService.updateCat(ownerId, catId, catDto));
    }

    @PutMapping("/cat/{catId}/friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Integer catId, @PathVariable Integer friendId) {

        catService.addCatFriend(catId, friendId);

        return ResponseEntity.ok("Cat(id = " + catId + ") and cat (id = " + friendId + ") becomes friend");
    }

    @DeleteMapping("/{ownerId}/cat/{catId}/delete")
    public ResponseEntity<String> deleteCat(@PathVariable Integer ownerId, @PathVariable Integer catId) {

        catService.deleteCat(ownerId, catId);

        return ResponseEntity.ok("Deleted Cat");
    }
}
