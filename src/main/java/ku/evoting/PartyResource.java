package ku.evoting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/party"})
public class PartyResource {

    @Autowired
    private PartyRepository repository;

    @GetMapping
    public List<Party> findAll() {
        return repository.findAll(); //about JSON format

    }

    @GetMapping("/{id}")
    public ResponseEntity<Party> findById(@PathVariable int id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Party create(@RequestBody Party party){
        return repository.save(party);
    }


    @PutMapping(value="/{id}")
    public ResponseEntity<Party> update(@PathVariable("id") int id,
                                        @RequestBody Party party){
        return repository.findById(id)
                .map(record -> {
                    record.setName(party.getName());
                    record.setVoteCount(party.getVoteCount());
                    record.setElectedMemberCount(party.getElectedMemberCount());
                    record.setPartylistCount(party.getPartylistCount());
                    Party updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
