package br.com.vsc.raffle.service;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ImageServiceTest {

    @Mock
    private Cloudinary cloudinaryMock;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveImage() {

    }

    @Test
    void getUrlImage() {
    }

    @Test
    void deleteImage() {
    }
}