package com.technonet.controlers;

import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.CategoryRepo;
import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.UserCategoryJoinRepo;
import com.technonet.Repository.UserRepository;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by kaxa on 3/8/17.
 */
@Controller
public class UserCategoryController {
    @RequestMapping("/addcategorytouser")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "user", required = true, defaultValue = "0") long user,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category){

        Session session= sessionRepository.findOne(sessionId);
        if(PermisionChecks.isAdmin(session)&&PermisionChecks.userManagement(session)){
            User user1=userRepository.findOne(user);
            Category category1=categoryRepo.findOne(category);
            UserCategoryJoin userCategoryJoin=new UserCategoryJoin(user1,category1);
            userCategoryJoinRepo.save(userCategoryJoin);
            return new JsonMessage(JsonReturnCodes.Ok);
        }else{
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }
    @RequestMapping("/usercategories/{id}")
    @ResponseBody
    public List<UserCategoryJoin> getUserCategories(@CookieValue("projectSessionId") long sessionId,
                                                    @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId);
        User user = userRepository.findOne(id);
        return user.getUserCategoryJoins();
    }
    @RequestMapping("/usercategoriescats/{id}")
    @ResponseBody
    public List<Category> getUserCategoriesCats(@CookieValue("projectSessionId") long sessionId,
                                                    @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId);
        User user = userRepository.findOne(id);
        return user.getUserCategoryJoins().stream().map(userCategoryJoin -> userCategoryJoin.getCategory()).collect(Collectors.toList());
    }
    @RequestMapping("/getcategoriesforuseradding/{id}")
    @ResponseBody
    public List<Category> getCategotiesForUserAdding(@CookieValue("projectSessionId") long sessionId,
                                                     @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId);
        List<Category> categories=categoryRepo.findByActive(true);
        User user = userRepository.findOne(id);
        user.getUserCategoryJoins().stream().forEach(userCategoryJoin -> categories.remove(userCategoryJoin.getCategory()));
        return categories;
    }

    @RequestMapping("/getusercatdocs/{id}")
    @ResponseBody
    public List<Document> getUserCategoryDocs(@CookieValue("projectSessionId") long sessionId,
                                              @PathVariable("id") long id){
        Session session
    }



    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCategoryJoinRepo userCategoryJoinRepo;
    @Autowired
    private CategoryRepo categoryRepo;
}
