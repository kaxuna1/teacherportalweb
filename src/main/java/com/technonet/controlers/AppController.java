package com.technonet.controlers;

import com.technonet.Repository.ConfirmationTokenRepo;
import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.SysStringRepo;
import com.technonet.model.ConfirmationToken;
import com.technonet.model.Session;
import com.technonet.model.SysString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.stream.Collectors;

import static com.technonet.staticData.PermisionChecks.isAdmin;

/**
 * Created by kakha on 3/2/2017.
 */
@Controller
public class AppController {
    private Facebook facebook;
    private ConnectionRepository connectionRepository;

    public AppController(Facebook facebook, ConnectionRepository connectionRepository) {
        this.facebook = facebook;
        this.connectionRepository = connectionRepository;
    }

    @GetMapping(value = "/", produces = "text/html")
    public String admin(Model model, @CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId) {
        Session session;
        Map<String, String> stringMap = sysStringRepo
                .findByActive(true)
                .stream().collect(Collectors.toMap(SysString::getName, SysString::getValue));
        model.addAttribute("strings", stringMap);
        if (sessionId != 0) {
            session = sessionRepository.findOne(sessionId).get();
            if (session.isIsactive()) {
                model.addAttribute("session", session);
                model.addAttribute("userNameSurname", session.getUser().getNameSurname());
                model.addAttribute("userId", session.getUser().getId());
                String profilePicUrl = "/profilePic/" + session.getUser().getId() + "?" + Math.random();

                if (!session.getUser().getFacebookId().isEmpty()) {
                    profilePicUrl = "http://graph.facebook.com/" + session.getUser().getFacebookId() + "/picture?type=large";
                }
                model.addAttribute("profilePicUrl", profilePicUrl);

                model.addAttribute("loggedIn", true);


            } else {
                model.addAttribute("loggedIn", false);
            }
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "/index";
    }

    @GetMapping(value = "/confirmtoken", produces = "text/html")
    public String confirmToken(Model model, @RequestParam(name = "token", defaultValue = "") String token) {
        if (token.isEmpty()) {
            return "redirect:/";
        }
        ConfirmationToken confirmationToken = confirmationTokenRepo.findByToken(token);
        //TODO გაგზავნის დროის შემოწმება. არ დაადასტუროს თუ 1 საათზე ძველია.
        //TODO ახალი ტოკენის მოთხოვნის ღილაკი დაემატოს სეთინგებში.
        if (confirmationToken.isConfimed())
            return "redirect:/";
        confirmationToken.confirmToken();
        confirmationTokenRepo.save(confirmationToken);

        return "redirect:/";
    }


    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SysStringRepo sysStringRepo;
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;
}
