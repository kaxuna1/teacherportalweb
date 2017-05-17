package com.technonet.controlers;

import com.technonet.Enums.InfoRecordTypes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
    public String admin(Model model,
                        @CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                        @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Session sessiona;
        Variables.myThreadLocal.set(lang);
        Map<String, String> stringMap = Variables.stringsMap.get(lang);
        model.addAttribute("strings", stringMap);
        if (sessionId != 0) {
            sessiona = sessionRepository.findOne(sessionId);
            if (sessiona.isIsactive()) {
                model.addAttribute("sessionobj", sessiona);
                model.addAttribute("userNameSurname", sessiona.getUser().getNameSurname());
                model.addAttribute("userId", sessiona.getUser().getId());
                String profilePicUrl = "/profilePic/" + sessiona.getUser().getId() + "?" + Math.random();

                if (!sessiona.getUser().getFacebookId().isEmpty()) {
                    profilePicUrl = "http://graph.facebook.com/" + sessiona.getUser().getFacebookId() + "/picture?type=large";
                }
                model.addAttribute("profilePicUrl", profilePicUrl);

                model.addAttribute("loggedIn", true);


            } else {
                model.addAttribute("loggedIn", false);
            }
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "main/index";
    }

    @GetMapping(value = "/search", produces = "text/html")
    public String search(Model model,
                         @CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                         @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Session sessiona;
        Variables.myThreadLocal.set(lang);
        Map<String, String> stringMap = Variables.stringsMap.get(lang);
        model.addAttribute("strings", stringMap);
        if (sessionId != 0) {
            sessiona = sessionRepository.findOne(sessionId);
            if (sessiona.isIsactive()) {
                model.addAttribute("sessionobj", sessiona);
                model.addAttribute("userNameSurname", sessiona.getUser().getNameSurname());
                model.addAttribute("userId", sessiona.getUser().getId());
                String profilePicUrl = "/profilePic/" + sessiona.getUser().getId() + "?" + Math.random();

                if (!sessiona.getUser().getFacebookId().isEmpty()) {
                    profilePicUrl = "http://graph.facebook.com/" + sessiona.getUser().getFacebookId() + "/picture?type=large";
                }
                model.addAttribute("profilePicUrl", profilePicUrl);

                model.addAttribute("loggedIn", true);


            } else {
                model.addAttribute("loggedIn", false);
            }
        } else {
            model.addAttribute("loggedIn", false);
        }

        return "main/search";
    }

    @GetMapping(value = "/class", produces = "text/html")
    public String classPage(Model model,
                            @RequestParam("id") long id,
                            @CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                            @CookieValue(value = "lang", defaultValue = "1") int lang) {

        Variables.myThreadLocal.set(lang);

        UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(id);
        String teacherName = userCategoryJoin.getUser().getName();
        String cityCountry = userCategoryJoin.getUser().getCity().getName() + ", "
                + userCategoryJoin.getUser().getCity().getCountry().getName();
        String className = userCategoryJoin.getCategory().getName();
        String classAbout = userCategoryJoin.getAbout();
        String teacherPic = "/profilePic/" + userCategoryJoin.getUser().getId();


        model.addAttribute("teacherName", teacherName);
        model.addAttribute("city", cityCountry);
        model.addAttribute("className", className);
        model.addAttribute("classAbout", classAbout);
        model.addAttribute("teacherPic", teacherPic);


        final boolean[] academic = {false};
        final boolean[] employment = {false};
        final boolean[] succeed = {false};
        final boolean[] skills = {false};
        final boolean[] attachments = {false};

        final String[] academicString = {""};
        final String[] employmentString = {""};
        final String[] succeedString = {""};
        final String[] skillsString = {""};
        String attachmentsString = "";



        int scoreCount = ratingRepo.getratingCount(id);


        model.addAttribute("scoreCount",scoreCount+" Reviews");



        Float proF=ratingRepo.getrating(id);
        Float punctF = ratingRepo.getratingPunctual(id);
        Float balF =ratingRepo.getratingBalanced(id);
        Float resolF = ratingRepo.getratingResolved(id);

        int pro=(int)Math.ceil(proF==null?5:proF);
        int punct =  (int)Math.ceil((punctF==null?5:punctF));
        int bal = (int)Math.ceil(balF==null?5:balF);
        int resol = (int)Math.ceil(resolF==null?5:resolF);
        int score=(int)Math.ceil((pro+punct+bal+resol)/4);


        List<Rating> reviews = ratingRepo.findByJoin(id,constructPageSpecification(0,5));


        model.addAttribute("reviews",reviews);

        model.addAttribute("scoreProfessional", pro);
        model.addAttribute("scorePunctual",punct);
        model.addAttribute("scoreBalanced", bal);
        model.addAttribute("scoreResolved",resol );
        model.addAttribute("scoreMain", score);




        List<InfoRecord> infoRecordList = userCategoryJoin.getUser().getInfoRecords();

        infoRecordList.forEach(infoRecord -> {
            if(infoRecord.getType()==InfoRecordTypes.academic.getCODE()){
                academic[0] = true;
                academicString[0] +="<li>"+infoRecord.getValue()+"</li>";
            }
            if(infoRecord.getType()==InfoRecordTypes.employment.getCODE()){
                employment[0] = true;
                employmentString[0] +="<li>"+infoRecord.getValue()+"</li>";
            }
            if(infoRecord.getType()==InfoRecordTypes.succeed.getCODE()){
                succeed[0] = true;
                succeedString[0] +="<li>"+infoRecord.getValue()+"</li>";
            }
            if(infoRecord.getType()==InfoRecordTypes.skills.getCODE()){
                skills[0] = true;
                skillsString[0] +="<li>"+infoRecord.getValue()+"</li>";
            }
            if(infoRecord.getType()==InfoRecordTypes.attachment.getCODE()){
                attachments[0] = true;
            }
        });



        model.addAttribute("academic", academic[0]);
        model.addAttribute("employment", employment[0]);
        model.addAttribute("succeed", succeed[0]);
        model.addAttribute("skills", skills[0]);
        model.addAttribute("attachments", attachments[0]);


        model.addAttribute("academicString", academicString[0]);
        model.addAttribute("employmentString", employmentString[0]);
        model.addAttribute("succeedString", succeedString[0]);
        model.addAttribute("skillsString", skillsString[0]);
        model.addAttribute("attachmentsString",attachmentsString);

        model.addAttribute("duration",userCategoryJoin.getDuration());

        Session sessiona;
        Variables.myThreadLocal.set(lang);
        Map<String, String> stringMap = Variables.stringsMap.get(lang);
        model.addAttribute("strings", stringMap);
        if (sessionId != 0) {
            sessiona = sessionRepository.findOne(sessionId);
            if (sessiona.isIsactive()) {
                model.addAttribute("sessionobj", sessiona);
                model.addAttribute("userNameSurname", sessiona.getUser().getNameSurname());
                model.addAttribute("userId", sessiona.getUser().getId());
                String profilePicUrl = "/profilePic/" + sessiona.getUser().getId() + "?" + Math.random();

                if (!sessiona.getUser().getFacebookId().isEmpty()) {
                    profilePicUrl = "http://graph.facebook.com/" + sessiona.getUser().getFacebookId() + "/picture?type=large";
                }
                model.addAttribute("profilePicUrl", profilePicUrl);

                model.addAttribute("loggedIn", true);


            } else {
                model.addAttribute("loggedIn", false);
            }
        } else {
            model.addAttribute("loggedIn", false);
        }


        return "main/class";
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

    private Pageable constructPageSpecification(int pageIndex, int size) {
        return new PageRequest(pageIndex, size);
    }

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SysStringRepo sysStringRepo;
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;
    @Autowired
    private UserCategoryJoinRepo userCategoryJoinRepo;
    @Autowired
    private RatingRepo ratingRepo;
}
