package com.technonet.controlers;

import com.technonet.Repository.SessionRepository;
import com.technonet.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

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
    public String admin(Model model, @CookieValue(value = "projectSessionId",defaultValue = "0") long sessionId){
        return "/index";
    }
    @Autowired
    private SessionRepository sessionRepository;
}
