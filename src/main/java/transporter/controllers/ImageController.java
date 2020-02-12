package transporter.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/images")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class ImageController {

    private Map<String, Resource> images = new HashMap<>();

    @GetMapping("/{name:.+}")
    public Resource getImage(@PathVariable("name") String name) {
        return images.get(name);
    }

    @GetMapping("/upload-image")
    public Model getUploadImageForm(Model model) {
        model.addAttribute("imageForm", new Image());
        return model;
    }

    @PostMapping("/upload-image")
    public String uploadImage(@ModelAttribute Image uploadImageForm) {
        try {
            images.put(uploadImageForm.getName(), new ByteArrayResource(uploadImageForm.getFile().getBytes()));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot upload image file!", e);
        }
        return null;
    }
}
