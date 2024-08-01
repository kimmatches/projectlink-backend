package projectlink.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectlink.models.Card;
import projectlink.models.CardFile;
import projectlink.repositories.CardFileRepository;
import projectlink.repositories.CardRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CardFileService {

    @Autowired
    private CardFileRepository cardFileRepository;

    @Autowired CardRepository cardRepository;

    public Long storeFile(MultipartFile file, String cardId) throws IOException {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found with id " + cardId));

        CardFile cardFile = new CardFile();
        cardFile.setFileName(file.getOriginalFilename());
        cardFile.setFileType(file.getContentType());
        cardFile.setData(file.getBytes());
        cardFile.setCard(card);

        CardFile savedFile = cardFileRepository.save(cardFile);
        return savedFile.getId();
    }

    public CardFile getFile(Long fileId) {
        return cardFileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found with id " + fileId));
    }

    public List<CardFile> getFilesByCardId(String cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found with id " + cardId));
        return cardFileRepository.findByCard(card);
    }

    public void deleteFile(Long fileId) {
        cardFileRepository.deleteById(fileId);
    }
}