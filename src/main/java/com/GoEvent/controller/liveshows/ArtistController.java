package com.GoEvent.controller.liveshows;

import com.GoEvent.model.liveshows.Artist;
import com.GoEvent.service.liveshows.ArtistServiceImpl;
import com.GoEvent.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(value = "/artists")
public class ArtistController {


    @Autowired
    private ArtistServiceImpl artistService;

    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("artists", artistService.listAllArtist());
        return "liveshows/artists/artists-list";
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("artists", artistService.listAllArtist());
        model.addAttribute("parseUtil", new ParseUtil());
        return "liveshows/artists/artists-user-list";
    }

    @RequestMapping("/new")
    public String newProduct(Model model) {
        model.addAttribute("artist", new Artist());
        return "liveshows/artists/artist-form";
    }

    @RequestMapping("/{id}")
    public String showProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getArtistById(id));
        model.addAttribute("parseUtil", new ParseUtil());
        return "liveshows/artists/artist-details";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("artist", artistService.getArtistById(id));
        return "liveshows/artists/artist-form";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveArtist(Artist artist, Model model, @RequestParam MultipartFile fileUpload) throws IOException, InterruptedException {

        if (artist.getName().isEmpty() || artist.getDescription().isEmpty() || fileUpload.isEmpty()
                || artist.getReview().isEmpty() || artist.getVideo().isEmpty()) {
            model.addAttribute("error", "One of the field is empty.");
            return "liveshows/artists/artist-form";
        }

        if (fileUpload.getContentType().equals("image/png") || fileUpload.getContentType().equals("image/jpg")
                || fileUpload.getContentType().equals("image/jpeg") || fileUpload.getContentType().equals("image/gif")) {
            artist.setImage(fileUpload.getBytes());
        }

        if (!artistService.saveArtist(artist)) {
            model.addAttribute("error", "Artist name already exists.");
            return "liveshows/artists/artist-form";
        }

        return "redirect:/artists/" + artist.getId();
    }


    @RequestMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id, Model model) {
        if (!artistService.deleteArtist(id))
            model.addAttribute("error", "The artist's live show was invited by users");
        model.addAttribute("artists", artistService.listAllArtist());
        return "liveshows/artists/artists-list";

    }


}
