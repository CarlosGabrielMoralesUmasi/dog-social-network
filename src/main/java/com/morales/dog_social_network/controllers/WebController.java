package com.morales.dog_social_network.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.morales.dog_social_network.models.User;
import com.morales.dog_social_network.services.UserService;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "login"; // Retorna la vista 'login.html'
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              Model model) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            model.addAttribute("error", "Usuario no existe");
            return "login"; // Retorna a la vista login.html con el mensaje de error
        }
        // Aquí deberías comparar la contraseña ingresada con la almacenada en la base de datos
        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "login";
        }
        // Si las credenciales son correctas, redirige al perfil del usuario
        model.addAttribute("user", user);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Lógica para obtener el usuario actual y agregarlo al modelo si es necesario
        return "profile"; // Retorna la vista 'profile.html'
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User()); // Asegúrate de que existe un objeto 'user' en el modelo
        return "register"; // Retorna la vista 'register.html'
    }

    @PostMapping("/register")
    public String handleRegister(@RequestParam("username") String username,
                                 @RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 Model model) {
        // Verifica si el usuario ya existe
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "El nombre de usuario ya existe");
            return "register"; // Retorna la vista 'register.html' con el mensaje de error
        }

        // Si el usuario no existe, crea uno nuevo
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password); // Asegúrate de encriptar la contraseña antes de guardarla

        userService.createUser(newUser);

        return "redirect:/login"; // Redirige al login después de crear el usuario
    }
}
