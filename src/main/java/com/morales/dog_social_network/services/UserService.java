package com.morales.dog_social_network.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.morales.dog_social_network.models.User;
import com.morales.dog_social_network.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final String baseDir = "D:\\imagenes_profile_"; // Base directory for user folders

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        // Crear el directorio del usuario al crearlo
        String userDir = baseDir + File.separator + user.getUsername();
        File directory = new File(userDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Crear las carpetas necesarias
        }

        return userRepository.save(user);
    }

    public User updateUser(Long id, User user, MultipartFile profilePicture) throws IOException {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }

            // Crear el directorio del usuario si no existe
            String userDir = baseDir + File.separator + existingUser.getUsername();
            File directory = new File(userDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Crear las carpetas necesarias
            }

            if (profilePicture != null && !profilePicture.isEmpty()) {
                // Guardar la imagen de perfil
                String profilePicPath = userDir + File.separator + "profile.jpg";
                Path path = Paths.get(profilePicPath);
                Files.write(path, profilePicture.getBytes());

                // Establecer la ruta de la imagen de perfil en el objeto usuario
                existingUser.setProfilePicture("/profile_pictures/" + existingUser.getUsername() + "/profile.jpg");
            }

            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        // Obtener el usuario antes de eliminarlo
        User userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete != null) {
            // Eliminar la carpeta del usuario
            String userDir = baseDir + File.separator + userToDelete.getUsername();
            File directory = new File(userDir);
            if (directory.exists()) {
                deleteDirectory(directory);
            }
            userRepository.deleteById(id);
        }
    }

    private void deleteDirectory(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directory.delete();
    }
}
