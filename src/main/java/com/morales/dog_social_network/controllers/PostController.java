package com.morales.dog_social_network.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.morales.dog_social_network.models.Post;
import com.morales.dog_social_network.services.PostService;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public String showPosts(Model model, @RequestParam("username") String username) {
        List<Post> posts = postService.getPostsByUsername(username);
        model.addAttribute("posts", posts);
        model.addAttribute("username", username);
        return "posts";
    }

    @PostMapping("/posts")
    public String handlePostUpload(@RequestParam("username") String username,
                                   @RequestParam("image") MultipartFile image,
                                   @RequestParam("description") String description,
                                   Model model) throws IOException {
        postService.createPost(username, image, description);
        return "redirect:/posts?username=" + username;
    }
}
