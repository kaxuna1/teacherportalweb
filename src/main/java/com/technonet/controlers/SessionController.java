package com.technonet.controlers;



import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Key;
import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.UserRepository;
import com.technonet.model.JsonMessage;
import com.technonet.model.Session;
import com.technonet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by KGelashvili on 10/26/2015.
 */
@Controller
public class SessionController {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();


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
    @RequestMapping("/loginapifb/{token}")
    @ResponseBody
    public Session loginFB(@PathVariable("token")String token, HttpServletResponse response){
        Session session;
        User user;
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl url=new GenericUrl("https://graph.facebook.com/me?access_token="+token);
        try {
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse responseFB=request.execute();

            FBSession fbSession=responseFB.parseAs(FBSession.class);
            List<User> users=userDao.findByFacebookIdAndActive(fbSession.id,true);

            if(users.size()==0){
                return null;
            }else{
                user=users.get(0);
                session=new Session(new Date(),user);
                session=sessionDao.save(session);
                response.addCookie(new Cookie("userId", session.getUser().getId()+""));
                response.addCookie(new Cookie("projectSessionId", session.getId()+""));
                return session;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }








    }
    public static class FBSession {

        /** List of Google+ activities. */
        @Key("id")
        private String id;

        public String getId() {
            return id;
        }
    }
    @RequestMapping("/loginapigoogle")
    @ResponseBody
    public Session loginGoogle(String token, HttpServletResponse response){
        Session session;
        User user;
        HttpRequestFactory requestFactory =
                HTTP_TRANSPORT.createRequestFactory(request -> request.setParser(new JsonObjectParser(JSON_FACTORY)));
        GenericUrl url=new GenericUrl("https://www.googleapis.com/oauth2/v1/tokeninfo?id_token="+token);
        try {
            HttpRequest request = requestFactory.buildGetRequest(url);
            HttpResponse responseFB=request.execute();
            GoogleSession googleSession=responseFB.parseAs(GoogleSession.class);
            List<User> users=userDao.findByGoogleIdAndActive(googleSession.id,true);

            if(users.size()==0){
                return null;
            }else{
                user=users.get(0);
                session=new Session(new Date(),user);
                session=sessionDao.save(session);
                response.addCookie(new Cookie("userId", session.getUser().getId()+""));
                response.addCookie(new Cookie("projectSessionId", session.getId()+""));
                return session;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }








    }
    public static class GoogleSession {

        @Key("user_id")
        private String id;

        public String getId() {
            return id;
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
