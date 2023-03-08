package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.model.Image;
import br.com.vsc.raffle.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imagemService;

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("images") List<MultipartFile> images) {
        try {
            for (MultipartFile image : images) {
                imagemService.saveImage(image);
            }
            return ResponseEntity.ok("Imagens salvas com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao salvar imagens: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<Image> imageOpt = imagemService.getImg(id);
        if (imageOpt.isPresent()) {
            Image imagem = imageOpt.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem.getData(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}





