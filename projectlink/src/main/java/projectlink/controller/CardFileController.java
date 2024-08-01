package projectlink.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectlink.models.CardFile;
import projectlink.services.CardFileService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cardfiles")
@Tag(name = "CardFileController", description = "CardFileController")
public class CardFileController {

    @Autowired
    private CardFileService cardFileService;

    @PostMapping("/upload/{cardId}")
    public ResponseEntity<List<Long>> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("cardId") String cardId) {
        List<Long> fileIds = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                Long fileId = cardFileService.storeFile(file, cardId);
                fileIds.add(fileId);
            }
            return ResponseEntity.status(HttpStatus.OK).body(fileIds);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId) {
        CardFile cardFile = cardFileService.getFile(fileId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cardFile.getFileName() + "\"")
                .body(cardFile.getData());
    }

    @GetMapping("/card/{cardId}/files")
    public ResponseEntity<List<CardFile>> getFilesByCardId(@PathVariable String cardId) {
        try {
            List<CardFile> cardFiles = cardFileService.getFilesByCardId(cardId);
            return ResponseEntity.status(HttpStatus.OK).body(cardFiles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {
        try {
            cardFileService.deleteFile(fileId);
            return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File deletion failed");
        }
    }
}