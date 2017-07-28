package com.technonet.controlers;

import com.google.common.base.Strings;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Created by kaxa on 3/8/17.
 */
@Controller
public class UserCategoryController {

    @Autowired
    private CityRepo cityRepo;

    @RequestMapping("searchapi")
    @ResponseBody
    public Page<UserCategoryJoin> searchByCategoryAndCity(@RequestParam(value = "page", required = true) int page,
                                                          @RequestParam(value = "sort", required = true, defaultValue = "1") int sort,
                                                          @RequestParam(value = "category", required = true) String cat,
                                                          @RequestParam(value = "city", required = true) String city,
                                                          @CookieValue(value = "lang", defaultValue = "1") int lang,
                                                          @RequestParam(value = "location", defaultValue = "1") int location,
                                                          @RequestParam(value = "education", defaultValue = "1") int education,
                                                          @RequestParam(value = "exp", defaultValue = "1") int exp,
                                                          @RequestParam(value = "age", defaultValue = "1") int age,
                                                          @RequestParam(value = "lower", defaultValue = "1") float lower,
                                                          @RequestParam(value = "upper", defaultValue = "1") float upper) {


        int expStart = 0;
        int expEnd = 99;

        int ageStart = 0;
        int ageEnd = 99;


        switch (exp){
            case 2:
                expStart = 0;
                expEnd = 1;
                break;
            case 3:
                expStart = 1;
                expEnd = 3;
                break;
            case 4:
                expStart = 3;
                expEnd = 5;
                break;
            case 5:
                expStart = 5;
                expEnd = 10;
                break;
            case 6:
                expStart = 10;
                expEnd = 99;
                break;
        }
        switch (age){
            case 2:
                ageStart = 18;
                ageEnd = 24;
                break;
            case 3:
                expStart = 25;
                expEnd = 34;
                break;
            case 4:
                expStart = 35;
                expEnd = 44;
                break;
            case 5:
                expStart = 45;
                expEnd = 54;
                break;
            case 6:
                expStart = 55;
                expEnd = 65;
                break;
            case 7:
                expStart = 65;
                expEnd = 99;
                break;
        }



        Variables.myThreadLocal.set(lang);
        List<String> categories = new ArrayList<>();
        List<String> cities = new ArrayList<>();
        Variables.stringsMap.forEach((integer, stringStringMap) ->
                stringStringMap.forEach((s, s2) -> {
                    if (s2.toLowerCase().contains(cat.toLowerCase())) categories.add(s);
                }));

        if (sort == 1) {
            return userCategoryJoinRepo.findByCategoryAndCityScoreSum(categories, city, upper, lower,location, ageStart,ageEnd,expStart,expEnd,education,constructPageSpecification(page, 10));
        } else {
            return userCategoryJoinRepo.findByCategoryAndCityScoreNum(categories, city, upper, lower,location, ageStart,ageEnd,expStart,expEnd,education,constructPageSpecification(page, 10));
        }

    }

    @RequestMapping("/usercategory/{id}")
    @ResponseBody
    public UserCategoryJoin getUserCategoryJoin(@CookieValue("projectSessionId") long sessionId,
                                                @PathVariable("id") long id,
                                                @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        return userCategoryJoinRepo.findOne(id);
    }


    @RequestMapping("/addcategorytouser")
    @ResponseBody
    public JsonMessage addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                         @RequestParam(value = "user", required = true, defaultValue = "0") long user,
                                         @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                         @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                         @RequestParam(value = "exp", required = true, defaultValue = "0") int exp,
                                         @RequestParam(value = "education", required = true, defaultValue = "0") int education,
                                         @RequestParam(value = "duration", required = true, defaultValue = "0") int duration,
                                         @RequestParam(value = "location", required = true, defaultValue = "0") int location) {

        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.isAdmin(session) && PermisionChecks.userManagement(session)) {
            User user1 = userRepository.findOne(user);
            Category category1 = categoryRepo.findOne(category);
            UserCategoryJoin userCategoryJoin = new UserCategoryJoin(user1, category1, price, duration, location);
            userCategoryJoin.setExp(exp);
            userCategoryJoin.setEducation(education);
            userCategoryJoinRepo.save(userCategoryJoin);
            return new JsonMessage(JsonReturnCodes.Ok);
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/requestcategory")
    @ResponseBody
    public long addCategoryToUser(@CookieValue("projectSessionId") long sessionId,
                                  @RequestParam(value = "category", required = true, defaultValue = "0") long category,
                                  @RequestParam(value = "price", required = true, defaultValue = "0") float price,
                                  @RequestParam(value = "duration", required = true, defaultValue = "0") int duration,
                                  @RequestParam(value = "exp", required = true, defaultValue = "0") int exp,
                                  @RequestParam(value = "education", required = true, defaultValue = "0") int education,
                                  @RequestParam(value = "city", required = true, defaultValue = "0") long city,
                                  @RequestParam(value = "iban", required = true, defaultValue = "0") String iban,
                                  @RequestParam(value = "location", required = true, defaultValue = "0") int location) {

        Session session = sessionRepository.findOne(sessionId);
        if (session.isIsactive()) {
            User user1 = session.getUser();
            user1.setCity(cityRepo.findOne(city));
            user1.setIban(iban);
            userRepository.save(user1);
            Category category1 = categoryRepo.findOne(category);
            UserCategoryJoin userCategoryJoin = new UserCategoryJoin(user1, category1, price, duration,location);
            userCategoryJoin.setExp(exp);
            userCategoryJoin.setEducation(education);
            userCategoryJoinRepo.save(userCategoryJoin);
            return userCategoryJoin.getId();
        } else {
            return 0;
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

    @RequestMapping("/usercategories")
    @ResponseBody
    public List<UserCategoryJoin> getUserCategoriesMy(@CookieValue("projectSessionId") long sessionId,
                                                      @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        Session session = sessionRepository.findOne(sessionId);
        User user = session.getUser();
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
        List<Category> cats = user.getUserCategoryJoins().stream().map(UserCategoryJoin::getCategory)
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

    @RequestMapping("/getcategoriesforuseradding")
    @ResponseBody
    public List<Category> getCategotiesForUserAdding(@CookieValue("projectSessionId") long sessionId,
                                                     @CookieValue(value = "lang", defaultValue = "1") int lang) {
        Variables.myThreadLocal.set(lang);
        Session session = sessionRepository.findOne(sessionId);
        List<Category> categories = categoryRepo.findByActiveAndVisible(true, true);
        User user = session.getUser();
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

    @RequestMapping("/giverating")
    @ResponseBody
    public JsonMessage giveRating(@CookieValue("projectSessionId") long sessionId,
                                  @RequestParam("id") long id, @RequestParam("score") int score,
                                  @RequestParam("comment") String comment) {

        Session session = sessionRepository.findOne(id);
        User user = session.getUser();
        UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(id);


        Rating rating = new Rating(userCategoryJoin, user, comment, score);

        try {

            ratingRepo.save(rating);

            userCategoryJoin.giveRating(score);

            userCategoryJoinRepo.save(userCategoryJoin);

            return new JsonMessage(JsonReturnCodes.Ok);

        } catch (Exception e) {
            e.printStackTrace();

            return new JsonMessage(JsonReturnCodes.ERROR);
        }
    }

    @RequestMapping("/getrating/{id}")
    @ResponseBody
    public Map<String, Float> getRating(@PathVariable("id") long id) {

        Map<String, Float> ratings = new HashMap<>();

        ratings.put("profesional", ratingRepo.getrating(id));
        ratings.put("balanced", ratingRepo.getratingBalanced(id));
        ratings.put("punctual", ratingRepo.getratingPunctual(id));
        ratings.put("resolved", ratingRepo.getratingResolved(id));


        return ratings;
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
    @Autowired
    private RatingRepo ratingRepo;
    @Autowired
    private PermissionController permissionController;
}
