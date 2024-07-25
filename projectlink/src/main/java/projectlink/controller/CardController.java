package projectlink.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectlink.controller.helpers.NewCard;
import projectlink.controller.helpers.URIFactory;
import projectlink.models.AppUser;
import projectlink.models.Board;
import projectlink.models.Card;
import projectlink.services.CardService;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/lists")
@Tag(name = "CommentController", description = "CommentController")
public class CardController {

    private final CardService cardService;
//    private final AppUserService appUserService;

    @Autowired
//    public CardController(CardService cardService, AppUserService appUserService) {
    public CardController(CardService cardService) {
        this.cardService = cardService;
//        this.appUserService = appUserService;
    }

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping(path = "/cards/{cardId}")
    public Card getSingleCard(@PathVariable String cardId) throws Exception {
        return cardService.getSingleCard(cardId);
    }

    @GetMapping(path = "/{listId}/cards")
    public List<Card> getListCards(@PathVariable String listId) {
        return cardService.getListCards(listId);
    }

    @PostMapping(path = "/{listId}/cards")
    public ResponseEntity<Board> createNewCard(
            Principal principal,
            @PathVariable String listId,
            @RequestBody NewCard newCard
    ) {
        URI uri = URIFactory.create();
//        AppUser appUser = appUserService.getUserByUsername(principal.getName());
//        Board board = cardService.createNewCard(listId, newCard.title, appUser);
//        return ResponseEntity.created(uri).body(board);
        return null;
    }

    @PatchMapping(path = "/cards/{cardId}")
    public Board updateCard(@PathVariable String cardId, @RequestBody Map<String, Object> body) {
        return cardService.updateCard(cardId, body);
    }

    @DeleteMapping("/cards/{cardId}")
    public Board deleteCard(Principal principal, @PathVariable String cardId) {
        return cardService.deleteCard(principal.getName(), cardId);
    }
}