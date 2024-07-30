package projectlink.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectlink.models.Card;
import projectlink.models.CardFile;
import projectlink.repositories.CardFileRepository;
import projectlink.repositories.CardRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class CardFileService {

    @Autowired
    private CardFileRepository cardFileRepository;

    @Autowired
    private CardRepository cardRepository;

    public CardFile storeFile(MultipartFile file, String cardId) throws IOException {
        String fileName = file.getOriginalFilename();
        CardFile cardFile = new CardFile();
        cardFile.setFileName(fileName);
        cardFile.setFileType(file.getContentType());
        cardFile.setData(file.getBytes());

        Optional<Card> cardOptional = cardRepository.findById(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            cardFile.setCard(card);
            return cardFileRepository.save(cardFile);
        } else {
            throw new RuntimeException("Card not found with id " + cardId);
        }
    }

    public CardFile getFile(Long fileId) {
        return cardFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }
}