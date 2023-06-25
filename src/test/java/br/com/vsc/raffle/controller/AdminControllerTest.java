package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.admin.request.AdminDTO;
import br.com.vsc.raffle.dto.admin.request.ChangePasswordRequestDTO;
import br.com.vsc.raffle.dto.admin.response.AdminSavedDTO;
import br.com.vsc.raffle.model.Admin;
import br.com.vsc.raffle.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    public static final String ADMIN_1 = "Admin 1";

    public static final String ADMIN_2 = "Admin 2";

    public static final String ADMIN_1_EMAIL_COM = "admin1@email.com";

    public static final String ADMIN_2_EMAIL_COM = "admin2@email.com";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private AdminController adminController;

    @Mock
    private AdminService adminService;

    @Test
    void getAllAdmins_ShouldReturnListOfAdmins() {
        Admin admin1 = Admin.builder().id(1L).name(ADMIN_1).email(ADMIN_1_EMAIL_COM).build();
        Admin admin2 = Admin.builder().id(1L).name(ADMIN_2).email(ADMIN_2_EMAIL_COM).build();

        when(adminService.getAllAdmins()).thenReturn(List.of(admin1, admin2));

        ResponseEntity<List<AdminSavedDTO>> response = adminController.getAllAdmins();

        assertNotNull(response);
        assertEquals(2, response.getBody().size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ADMIN_1, response.getBody().get(0).getName());
        assertEquals(ADMIN_1_EMAIL_COM, response.getBody().get(0).getEmail());
        assertEquals(ADMIN_2, response.getBody().get(1).getName());
        assertEquals(ADMIN_2_EMAIL_COM, response.getBody().get(1).getEmail());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void saveAdmin_ShouldReturnCreatedAdmin() {
        Admin admin = Admin.builder().id(1L).name(ADMIN_1).email(ADMIN_1_EMAIL_COM).build();

        when(adminService.saveAdmin(any())).thenReturn(admin);

        ResponseEntity<AdminSavedDTO> response = adminController.saveAdmin(new AdminDTO("1", ADMIN_1, "123"));

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ADMIN_1, response.getBody().getName());
        assertEquals(ADMIN_1_EMAIL_COM, response.getBody().getEmail());
    }

    @Test
    void changePassword_ShouldReturnUpdatedAdmin() {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO();
        request.setPassword("newPassword");

        Admin admin = Admin.builder().id(1L).name(ADMIN_1).email(ADMIN_1_EMAIL_COM).build();

        when(adminService.changePassword(request.getPassword())).thenReturn(admin);

        ResponseEntity<AdminSavedDTO> response = adminController.changePassword(request);

        assertEquals(ResponseEntity.ok(AdminSavedDTO.toAdminSavedDTO(admin)), response);
    }

    @Test
    void deleteAdmin_ShouldReturnNoContent() {
        Long adminId = 1L;

        ResponseEntity<AdminSavedDTO> response = adminController.deleteAdmin(adminId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(adminService, times(1)).deleteAdmin(adminId);
    }
}