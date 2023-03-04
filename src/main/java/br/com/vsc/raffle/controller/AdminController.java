package br.com.vsc.raffle.controller;

import br.com.vsc.raffle.dto.admin.request.AdminDTO;
import br.com.vsc.raffle.dto.admin.request.ChangePasswordRequestDTO;
import br.com.vsc.raffle.dto.admin.response.AdminSavedDTO;
import br.com.vsc.raffle.model.Admin;
import br.com.vsc.raffle.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RequestMapping("/v1/admin")
@RestController
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping
	@Cacheable(value = "listAdmins")
	public ResponseEntity<List<AdminSavedDTO>> getAllAdmins() {
		List<Admin> admins = adminService.getAllAdmins();
		return ResponseEntity.ok(AdminSavedDTO.toListAdminSavedDTO(admins));
	}

	@PostMapping
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> saveAdmin(@Valid @RequestBody AdminDTO request) {
		Admin admin = adminService.saveAdmin(Admin.toDomain(request));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(admin.getId())
				.toUri();

		return ResponseEntity.created(uri).body(AdminSavedDTO.toAdminSavedDTO(admin));
	}

	@PutMapping(value = "/change-password")
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		Admin admin = adminService.changePassword(request.getPassword());

		return ResponseEntity.ok(AdminSavedDTO.toAdminSavedDTO(admin));
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "listAdmins", allEntries = true)
	public ResponseEntity<AdminSavedDTO> deleteAdmin(@PathVariable Long id) {
		adminService.deleteAdmin(id);
		return ResponseEntity.noContent().build();
	} 
}
