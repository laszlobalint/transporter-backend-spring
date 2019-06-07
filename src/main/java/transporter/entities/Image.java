package transporter.entities;

import org.springframework.web.multipart.MultipartFile;

public class Image {

    private String name;

    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "\nImage: " +
                "\nName= - " + name + '\'' +
                "\nMultipart file - " + file;
    }
}
