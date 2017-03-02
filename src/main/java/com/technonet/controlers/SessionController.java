package com.technonet.controlers;



import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.UserRepository;
import com.technonet.model.Session;
import com.technonet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by KGelashvili on 10/26/2015.
 */
@Controller
public class SessionController {
    @RequestMapping("/loginapi")
    public String login(String username, String password,HttpServletResponse response){
        Session session;
        User user;
        List<User> users=userDao.findByUsernameAndPassword(username,password);

        if(users.size()==0){
            return "redirect:/";
        }else{
            user=users.get(0);
            session=new Session(new Date(),user);
            session=sessionDao.save(session);
            response.addCookie(new Cookie("projectSessionId", session.getId()+""));
            response.addCookie(new Cookie("userId", session.getUser().getId()+""));
            return "redirect:/admin";
        }
    }
    @RequestMapping("/logout")
    @ResponseBody
    public Session logout(@CookieValue("projectSessionId") String sessionId){
        Session session=sessionDao.findOne(Long.parseLong(sessionId));
        session.setIsactive(false);
        sessionDao.save(session);
        return session;
    }
    @RequestMapping("/getsessionstatus")
    @ResponseBody
    public Session sessionStatus(@CookieValue("projectSessionId") String sessionId){
        Session session=sessionDao.findOne(Long.parseLong(sessionId));
        return session;
    }
    @Autowired
    private UserRepository userDao;
    @Autowired
    private SessionRepository sessionDao;
}
