package br.com.vsc.raffle.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Service
public class ImageService {

    private final Cloudinary cloudinary;

    public String saveImage(MultipartFile image) throws IOException {
        byte[] bytes = image.getBytes();
        Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }
    
    public String getUrlImage(String id) {
        return cloudinary.url().generate(id);
    }

    public void deleteImage(String id) throws IOException {
        cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }
}
