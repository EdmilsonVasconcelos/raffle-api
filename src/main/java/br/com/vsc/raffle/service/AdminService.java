package br.com.vsc.raffle.service;

import br.com.vsc.raffle.exception.AdminAlreadyExistsException;
import br.com.vsc.raffle.exception.AdminDoesNotExistException;
import br.com.vsc.raffle.model.Admin;
import br.com.vsc.raffle.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AdminService {
	
	private static final String ADMIN_WITH_EMAIL_EXISTS = "Admin with email %s exists.";
	
	private static final String ADMIN_WITH_EMAIL_DOES_NOT_EXIST = "Admin with email %s does not exist.";
	
	private static final String ADMIN_WITH_ID_DOES_NOT_EXIST = "Admin with id %s does not exist.";
	
	private final AdminRepository adminRepository;

	public AdminService(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}
	
	public Admin saveAdmin(Admin admin) {
		checkAdminExists(admin.getEmail());

		admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));

		return adminRepository.save(admin);
	}
	
	public Admin changePassword(String password) {
		String userLogged = getEmailAdminLogged();
		
		Admin adminToSave = getAdminByEmail(userLogged);
		
		adminToSave.setPassword(new BCryptPasswordEncoder().encode(password));
		
		return adminRepository.save(adminToSave);
	}
	
	public void deleteAdmin(Long idAdmin) {
		checkAdminExists(idAdmin);

		adminRepository.deleteById(idAdmin);
	}

	private String getEmailAdminLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		} else {
			return principal.toString();
		}
	}
	
	private Admin getAdminByEmail(String email) {
		return adminRepository.findByEmail(email)
				.orElseThrow(() -> new AdminDoesNotExistException(String.format(ADMIN_WITH_EMAIL_DOES_NOT_EXIST, email)));
	}

	private void checkAdminExists(String email) {
		Optional<Admin> admin = adminRepository.findByEmail(email);
		
		if(admin.isPresent()) {
			throw new AdminAlreadyExistsException(String.format(ADMIN_WITH_EMAIL_EXISTS, email));
		}
	}
	
	private void checkAdminExists(Long idUser) {
		Optional<Admin> admin = adminRepository.findById(idUser);
		
		if(admin.isEmpty()) {
			throw new AdminDoesNotExistException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST, idUser));
		}
	}
}