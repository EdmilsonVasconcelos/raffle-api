package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.AdminAlreadyExistsException;
import br.com.vsc.raffle.exception.AdminDoesNotExistException;
import br.com.vsc.raffle.model.Admin;
import br.com.vsc.raffle.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAdmins_shouldReturnListOfAdmins() {
        List<Admin> admins = new ArrayList<>();
        admins.add(new Admin());
        admins.add(new Admin());

        when(adminRepository.findAll()).thenReturn(admins);

        List<Admin> result = adminService.getAllAdmins();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAllAdmins_shouldReturnAnEmptyList() {
        when(adminRepository.findAll()).thenReturn(List.of());

        List<Admin> result = adminService.getAllAdmins();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void saveAdmin_shouldSave() {
        Admin admin = Admin.builder().email("admin@example.com").password("password").build();

        when(adminRepository.findByEmail("admin@example.com")).thenReturn(Optional.empty());
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin savedAdmin = adminService.saveAdmin(admin);

        assertNotNull(savedAdmin);
        assertEquals("admin@example.com", savedAdmin.getEmail());
        assertTrue(new BCryptPasswordEncoder().matches("password", savedAdmin.getPassword()));

        verify(adminRepository, times(1)).findByEmail("admin@example.com");
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void saveAdmin_shouldThrowsException() {
        Admin admin = Admin.builder().email("admin@example.com").password("password").build();

        when(adminRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(admin));

        assertThrows(AdminAlreadyExistsException.class, () -> adminService.saveAdmin(admin));
        verify(adminRepository, times(1)).findByEmail("admin@example.com");
        verify(adminRepository, never()).save(any(Admin.class));
    }

    @Test
    void changePassword_shouldChange() {
        String newPassword = "newPassword";
        String userLogged = "admin@example.com";

        Admin admin = new Admin();
        admin.setEmail(userLogged);
        admin.setPassword("oldPassword");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userLogged, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(adminRepository.findByEmail(userLogged)).thenReturn(Optional.of(admin));
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminService.changePassword(newPassword);

        assertTrue(new BCryptPasswordEncoder().matches(newPassword, result.getPassword()));
        verify(adminRepository, times(1)).findByEmail(userLogged);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void deleteAdmin_shouldDelete() {
        Long adminId = 1L;
        Admin admin = Admin.builder().id(adminId).build();

        when(adminRepository.findById(adminId)).thenReturn(Optional.of(admin));

        adminService.deleteAdmin(adminId);

        verify(adminRepository, times(1)).findById(adminId);
        verify(adminRepository, times(1)).deleteById(adminId);
    }

    @Test
    void deleteAdmin_shouldThrowsException() {
        Long adminId = 1L;

        when(adminRepository.findById(adminId)).thenReturn(Optional.empty());

        assertThrows(AdminDoesNotExistException.class, () -> adminService.deleteAdmin(adminId));
        verify(adminRepository, times(1)).findById(adminId);
        verify(adminRepository, never()).deleteById(adminId);
    }
}