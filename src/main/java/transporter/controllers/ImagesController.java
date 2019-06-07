package transporter.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import transporter.entities.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImagesController {

    private Map<String, Resource> images = new HashMap<>();

    @RequestMapping(value = "/images/{name:.+}", method = RequestMethod.GET)
    @ResponseBody
    public Resource getImage(@PathVariable("name") String name) {
        return images.get(name);
    }

    @RequestMapping(value = "/upload-image", method = RequestMethod.GET)
    public Model getUploadImageForm(Model model) {
        model.addAttribute("imageForm", new Image());
        return model;
    }

    @RequestMapping(value = "/upload-image", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute Image uploadImageForm) {
        try {
            images.put(uploadImageForm.getName(), new ByteArrayResource(uploadImageForm.getFile().getBytes()));
        } catch (IOException e) {
            throw new IllegalStateException("Cannot upload image file!", e);
        }
        return null;
    }
}
