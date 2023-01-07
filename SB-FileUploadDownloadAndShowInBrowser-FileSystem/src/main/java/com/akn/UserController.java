package com.akn;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public String users(Model model) {

		List<User> listAllUser = userRepository.findAll();

		model.addAttribute("listAllUser", listAllUser);

		return "user";

	}

	@GetMapping("/create/user")
	public String createUser(Model model) {
		model.addAttribute("user", new User());
		return "create_user";
	}

	@PostMapping("/save/user")
	public String saveUser(@ModelAttribute User user, @RequestParam("image") MultipartFile multipartFile)
			throws IOException {

		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			String uploadDir = "user-photo";

			String finaleImageName = FileUtils.saveFile(uploadDir, fileName, multipartFile);

			user.setPhoto(finaleImageName);

			userRepository.save(user);
		} else {
			userRepository.save(user);
		}

		return "redirect:/create/user";
	}

	@GetMapping("/view/user/{userId}")
	public String viewUser(@PathVariable Integer userId, Model model) {

		User user = userRepository.findById(userId).get();
		model.addAttribute("user", user);

		return "view_user";

	}

}
