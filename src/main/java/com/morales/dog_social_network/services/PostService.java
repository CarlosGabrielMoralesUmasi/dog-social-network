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

import com.morales.dog_social_network.models.Post;
import com.morales.dog_social_network.models.User;
import com.morales.dog_social_network.repositories.PostRepository;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public List<Post> getPostsByUsername(String username) {
        return postRepository.findByUser_Username(username);
    }

    public Post createPost(String username, MultipartFile imageFile, String description) throws IOException {
        User user = userService.getUserByUsername(username);
        if (user != null && imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(user, imageFile);

            Post post = new Post();
            post.setUser(user);
            post.setImageUrl(imagePath);
            post.setDescription(description);

            return postRepository.save(post);
        }
        return null;
    }

    private String saveImage(User user, MultipartFile imageFile) throws IOException {
        String userDir = "D:/imagenes_profile_/" + user.getUsername() + "/posts";
        File directory = new File(userDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String imagePath = userDir + "/" + imageFile.getOriginalFilename();
        Path path = Paths.get(imagePath);
        Files.write(path, imageFile.getBytes());

        return "/imagenes_profile_/" + user.getUsername() + "/posts/" + imageFile.getOriginalFilename();
    }
}
