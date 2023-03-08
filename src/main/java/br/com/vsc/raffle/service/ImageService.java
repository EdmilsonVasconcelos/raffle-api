package br.com.vsc.raffle.service;

import br.com.vsc.raffle.model.Image;
import br.com.vsc.raffle.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImage(MultipartFile image) throws IOException {
        String fileName = image.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg") && !extension.equalsIgnoreCase("png")) {
            throw new IllegalArgumentException("Formato de arquivo não suportado. Por favor, envie uma imagem nos formatos JPEG, JPG ou PNG.");
        }

        Image newImg = new Image();
        newImg.setName(image.getOriginalFilename());
        newImg.setData(image.getBytes());
        imageRepository.save(newImg);
        saveImageInTheDisk(image);
    }

    public Optional<Image> getImg(Long id) {
        return imageRepository.findById(id);
    }

    private void saveImageInTheDisk(MultipartFile image) throws IOException {
        String path = new File("src/main/resources/static/images").getAbsolutePath();

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = image.getOriginalFilename();
        File file = new File(directory, fileName);
        image.transferTo(file);
    }

}
