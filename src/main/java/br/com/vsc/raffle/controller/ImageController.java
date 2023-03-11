package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<String> saveImage(@RequestParam MultipartFile image) throws IOException {
        String urlImage = imageService.saveImage(image);
        return ResponseEntity.ok(urlImage);
    }

    @GetMapping()
    public ResponseEntity<String> showImage(@PathVariable("id") String id) {
        return ResponseEntity.ok(imageService.getUrlImage(id));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteImage(@PathVariable("id") String id) throws IOException {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}





