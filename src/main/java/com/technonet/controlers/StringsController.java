package com.technonet.controlers;

import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.SysStringRepo;
import com.technonet.Repository.SysStringTranslationRepo;
import com.technonet.model.Session;
import com.technonet.model.SysString;
import com.technonet.staticData.PermisionChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by vakhtanggelashvili on 3/26/17.
 */
@Controller
public class StringsController {
    @RequestMapping("createstring")
    @ResponseBody
    public boolean createString(@CookieValue("projectSessionId") long sessionId,
                                @RequestParam(value = "name", required = true, defaultValue = "0") String name,
                                @RequestParam(value = "value", required = true, defaultValue = "0") String value){
        Session session = sessionRepository.findOne(sessionId).get();
        if(PermisionChecks.isAdmin(session)){
            if(sysStringRepo.findByNameAndActive(name,true).size()>0){
                return false;
            }


            sysStringRepo.save(new SysString(value,name));
            return true;
        }else{
            return false;
        }


    }
    @RequestMapping("strings/{page}")
    @ResponseBody
    public Page<SysString> getStrings(@CookieValue("projectSessionId") long sessionId, @PathVariable("page") int page){
        return sysStringRepo.findByActiveOrderByNameAsc(true,constructPageSpecification(page));
    }

    @RequestMapping("strings")
    @ResponseBody
    public Map<String,String> getStringsMap(@CookieValue(value = "lang",defaultValue = "0") int lang){
        return sysStringRepo.findByActive(true).stream().collect(Collectors.toMap(SysString::getName,SysString::getValue));
    }


    private Pageable constructPageSpecification(int pageIndex) {
        return new PageRequest(pageIndex, 100);
    }


    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SysStringRepo sysStringRepo;
    @Autowired
    private SysStringTranslationRepo sysStringTranslationRepo;
}
