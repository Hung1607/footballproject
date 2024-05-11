package nl.fontys.individualproject.controllers;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.individualproject.domain.request.CoachTotalMatchRequest;
import nl.fontys.individualproject.domain.request.MatchRequest;
import nl.fontys.individualproject.domain.response.Match;
import nl.fontys.individualproject.domain.response.Player;
import nl.fontys.individualproject.services.impl.MatchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

    private MatchService matchService;
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping
    @RolesAllowed({"Coach"})
    public ResponseEntity<Void> createMatch(@RequestBody @Valid MatchRequest matchRequest){
        this.matchService.createMatch(matchRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @RolesAllowed({"Player"})
    public ResponseEntity<Void> addPlayer(@PathVariable ("id") int matchID,@RequestBody Player player){
        this.matchService.addPlayerList(matchID,player);

        simpMessagingTemplate.convertAndSend("/topic/user", player.getUsername());

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatch(){
        return ResponseEntity.ok(this.matchService.getAllMatch());
    }

    @GetMapping("{id}")
    public ResponseEntity<Match> getMatchByID(@PathVariable ("id") int matchID){
        Optional<Match> m = this.matchService.getMatchByID(matchID);
        if(m.isPresent()){
            return ResponseEntity.ok(m.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/coachTotalMatch")
    public ResponseEntity<List<Object[]>> getTotalMatchByCoachId(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_start,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_end){
        try {
            List<Object[]> list =  this.matchService.getTotalMatchByCoach(CoachTotalMatchRequest.builder()
                    .date_end(date_end)
                    .date_start(date_start).build());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


