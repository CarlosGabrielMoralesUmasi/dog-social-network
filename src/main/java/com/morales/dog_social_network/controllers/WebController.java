package com.morales.dog_social_network.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.morales.dog_social_network.models.User;
import com.morales.dog_social_network.services.UserService;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Usuario no existe");
            return "login";
        }
        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login";
        }
    
        return "redirect:/posts?username=" + username;
    }
    

    @GetMapping("/profile")
    public String showProfile(Model model, @RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String handleProfileUpdate(@RequestParam("id") Long id,
                                      @RequestParam("username") String username,
                                      @RequestParam("email") String email,
                                      @RequestParam("fullName") String fullName,
                                      @RequestParam("bio") String bio,
                                      @RequestParam("gender") String gender,
                                      @RequestParam("location") String location,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam("profilePicture") MultipartFile profilePicture,
                                      Model model) throws IOException {
        User user = userService.getUserById(id);
        if (user == null) {
            model.addAttribute("error", "Usuario no encontrado");
            return "profile";
        }

        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setBio(bio);
        user.setGender(gender);
        user.setLocation(location);

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);  // Actualiza la contraseña solo si se proporciona una nueva
        }

        userService.updateUser(id, user, profilePicture);

        model.addAttribute("user", user);
        return "redirect:/profileView?username=" + username;  // Redirige a la vista con los datos del usuario
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 Model model) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setLocation("Perú"); // Asignar ubicación por defecto

        userService.createUser(newUser);

        return "redirect:/login";
    }

    @GetMapping("/profileView")
    public String showProfileView(Model model, @RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "profileView";
    }
}
