package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.admin.request.AdminDTO;
import br.com.vsc.raffle.dto.admin.request.ChangePasswordRequestDTO;
import br.com.vsc.raffle.dto.admin.response.AdminSavedDTO;
import br.com.vsc.raffle.model.Admin;
import br.com.vsc.raffle.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/v1/admin")
@RestController
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping
	public ResponseEntity<List<AdminSavedDTO>> getAllAdmins() {
		List<Admin> admins = adminService.getAllAdmins();
		return ResponseEntity.ok(AdminSavedDTO.toListAdminSavedDTO(admins));
	}

	@PostMapping
	public ResponseEntity<AdminSavedDTO> saveAdmin(@Valid @RequestBody AdminDTO request) {
		Admin admin = adminService.saveAdmin(Admin.toDomain(request));
		return new ResponseEntity<>(AdminSavedDTO.toAdminSavedDTO(admin), CREATED);
	}

	@PutMapping(value = "/change-password")
	public ResponseEntity<AdminSavedDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		Admin admin = adminService.changePassword(request.getPassword());

		return ResponseEntity.ok(AdminSavedDTO.toAdminSavedDTO(admin));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AdminSavedDTO> deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	} 
}
