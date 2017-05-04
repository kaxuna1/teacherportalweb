package com.technonet.controlers;

import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.*;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import com.technonet.staticData.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kaxa on 3/8/17.
 */
@Controller
public class UserCategoryController {

    @Autowired
    private CityRepo cityRepo;

    @RequestMapping("search/{cat}/{city}/{page}")
    @ResponseBody
    public Page<UserCategoryJoin> search(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                         @PathVariable(value = "page", required = true) int page,
                                         @PathVariable(value = "category", required = true) long cat,
                                         @PathVariable(value = "city", required = true) long city) {



        return userCategoryJoinRepo.findByCategoryAndCity(categoryRepo.getOne(cat),
                cityRepo.getOne(city),constructPageSpecification(page,20));
    }

    @RequestMapping("/addcategorytouser")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "user", required = true, defaultValue = "0") long user,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                         @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                         @RequestParam(value = "duration", required = true, defaultValue = "0") int duration) {

        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.isAdmin(session) && PermisionChecks.userManagement(session)) {
            User user1 = userRepository.findOne(user);
            Category category1 = categoryRepo.findOne(category);
            UserCategoryJoin userCategoryJoin = new UserCategoryJoin(user1, category1, price, duration);
            userCategoryJoinRepo.save(userCategoryJoin);
            return new JsonMessage(JsonReturnCodes.Ok);
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/requestcategory")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                         @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                         @RequestParam(value = "duration", required = true, defaultValue = "0") int duration) {

        Session session = sessionRepository.findOne(sessionId);
        if (session.isIsactive()) {
            User user1 = session.getUser();
            Category category1 = categoryRepo.findOne(category);
            UserCategoryJoin userCategoryJoin = new UserCategoryJoin(user1, category1, price, duration);
            userCategoryJoinRepo.save(userCategoryJoin);
            return new JsonMessage(JsonReturnCodes.Ok);
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/usercategories/{id}")
    @ResponseBody
    public List<UserCategoryJoin> getUserCategories(@CookieValue("projectSessionId") long sessionId,
                                                    @PathVariable("id") long id,
                                                    @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        Session session = sessionRepository.findOne(sessionId);
        User user = userRepository.findOne(id);
        return user.getUserCategoryJoins();
    }

    @RequestMapping("/usercategoriescats/{id}")
    @ResponseBody
    public List<Category> getUserCategoriesCats(@CookieValue("projectSessionId") long sessionId,
                                                @PathVariable("id") long id,
                                                @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        Session session = sessionRepository.findOne(sessionId);
        User user = userRepository.findOne(id);
        List<Category> cats= user.getUserCategoryJoins().stream().map(UserCategoryJoin::getCategory)
                .collect(Collectors.toList());
        cats.forEach(category -> category.setLang(lang));

        return cats;
    }

    @RequestMapping("/getcategoriesforuseradding/{id}")
    @ResponseBody
    public List<Category> getCategotiesForUserAdding(@CookieValue("projectSessionId") long sessionId,
                                                     @PathVariable("id") long id,
                                                     @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        Session session = sessionRepository.findOne(sessionId);
        List<Category> categories = categoryRepo.findByActiveAndVisible(true, true);
        User user = userRepository.findOne(id);
        user.getUserCategoryJoins().stream().forEach(userCategoryJoin -> categories.remove(userCategoryJoin.getCategory()));
        categories.forEach(category -> category.setLang(lang));
        return categories;
    }

    @RequestMapping("/getusercatdocs/{id}")
    @ResponseBody
    public List<Document> getUserCategoryDocs(@CookieValue("projectSessionId") long sessionId,
                                              @PathVariable("id") long id) {
        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.isAdmin(session)) {
            return userCategoryJoinRepo.findOne(id).getDocuments();
        } else {
            return null;
        }
    }

    @RequestMapping("/usercategoryconfirm/{id}/{action}")
    @ResponseBody
    public JsonMessage userCategoryJoinConfirm(@CookieValue("projectSessionId") long sessionId,
                                               @PathVariable("id") long id, @PathVariable("action") int action) {
        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.categoryConfirmation(session)) {

            UserCategoryJoin join = userCategoryJoinRepo.findOne(id);
            if (action == 1) {
                join.confirm();
            }
            if (action == 2) {
                join.decline();
            }
            userCategoryJoinRepo.save(join);
            return new JsonMessage(JsonReturnCodes.Ok);
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    private Pageable constructPageSpecification(int pageIndex, int size) {
        return new PageRequest(pageIndex, size);
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
