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
import sun.jvm.hotspot.debugger.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kaxa on 3/8/17.
 */
@Controller
public class UserCategoryController {

    @RequestMapping("search/{cat}/{page}")
    @ResponseBody
    public Page<UserCategoryJoin> search(@CookieValue(value = "projectSessionId",defaultValue = "0") long sessionId,
                                         @RequestParam(value = "page", required = true, defaultValue = "0") int page,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long cat){

    }

    @RequestMapping("/addcategorytouser")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "user", required = true, defaultValue = "0") long user,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                         @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                         @RequestParam(value = "duration", required = true, defaultValue = "0") int duration){

        Session session= sessionRepository.findOne(sessionId).get();
        if(PermisionChecks.isAdmin(session)&&PermisionChecks.userManagement(session)){
            User user1=userRepository.findOne(user).get();
            Category category1=categoryRepo.findOne(category).get();
            UserCategoryJoin userCategoryJoin=new UserCategoryJoin(user1,category1,price, duration);
            userCategoryJoinRepo.save(userCategoryJoin);
            return new JsonMessage(JsonReturnCodes.Ok);
        }else{
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }
    @RequestMapping("/requestcategory")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                         @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                         @RequestParam(value = "duration", required = true, defaultValue = "0") int duration){

        Session session= sessionRepository.findOne(sessionId).get();
        if(session.isIsactive()){
            User user1=session.getUser();
            Category category1=categoryRepo.findOne(category).get();
            UserCategoryJoin userCategoryJoin=new UserCategoryJoin(user1,category1,price, duration);
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
        Session session = sessionRepository.findOne(sessionId).get();
        User user = userRepository.findOne(id).get();
        return user.getUserCategoryJoins();
    }
    @RequestMapping("/usercategoriescats/{id}")
    @ResponseBody
    public List<Category> getUserCategoriesCats(@CookieValue("projectSessionId") long sessionId,
                                                    @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId).get();
        User user = userRepository.findOne(id).get();
        return user.getUserCategoryJoins().stream().map(userCategoryJoin -> userCategoryJoin.getCategory()).collect(Collectors.toList());
    }
    @RequestMapping("/getcategoriesforuseradding/{id}")
    @ResponseBody
    public List<Category> getCategotiesForUserAdding(@CookieValue("projectSessionId") long sessionId,
                                                     @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId).get();
        List<Category> categories=categoryRepo.findByActiveAndVisible(true,true);
        User user = userRepository.findOne(id).get();
        user.getUserCategoryJoins().stream().forEach(userCategoryJoin -> categories.remove(userCategoryJoin.getCategory()));
        return categories;
    }

    @RequestMapping("/getusercatdocs/{id}")
    @ResponseBody
    public List<Document> getUserCategoryDocs(@CookieValue("projectSessionId") long sessionId,
                                              @PathVariable("id") long id){
        Session session = sessionRepository.findOne(sessionId).get();
        if(PermisionChecks.isAdmin(session)){
            return userCategoryJoinRepo.findOne(id).get().getDocuments();
        }else{
            return null;
        }
    }
    @RequestMapping("/usercategoryconfirm/{id}/{action}")
    @ResponseBody
    public JsonMessage userCategoryJoinConfirm(@CookieValue("projectSessionId") long sessionId,
                                               @PathVariable("id") long id,@PathVariable("action") int action){
        Session session=sessionRepository.findOne(sessionId).get();
        if(PermisionChecks.categoryConfirmation(session)){

            UserCategoryJoin join = userCategoryJoinRepo.findOne(id).get();
            if(action==1){
                join.confirm();
            }
            if(action==2){
                join.decline();
            }
            userCategoryJoinRepo.save(join);
            return new JsonMessage(JsonReturnCodes.Ok);
        }else{
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
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
