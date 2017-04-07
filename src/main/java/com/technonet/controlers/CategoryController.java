package com.technonet.controlers;

import com.technonet.Enums.JsonReturnCodes;
import com.technonet.Repository.CategoryRepo;
import com.technonet.Repository.DocTypeRepo;
import com.technonet.Repository.SessionRepository;
import com.technonet.model.*;
import com.technonet.staticData.PermisionChecks;
import com.technonet.staticData.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static sun.plugin2.main.client.ServiceDelegate.get;

/**
 * Created by kakha on 3/7/2017.
 */
@Controller
public class CategoryController {
    @RequestMapping("/createcategory")
    @ResponseBody
    public JsonMessage createCategory(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @RequestParam(value = "name", required = true, defaultValue = "") String name) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.categoriesManagement(session)) {
            try {
                categoryRepo.save(new Category(name));
                return new JsonMessage(JsonReturnCodes.Ok);
            } catch (Exception e) {
                return new JsonMessage(JsonReturnCodes.ERROR);
            }
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/createdoctype")
    @ResponseBody
    public JsonMessage createDocType(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                     @RequestParam(value = "name", required = true, defaultValue = "") String name) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.documentTypes(session)) {
            try {
                docTypeRepo.save(new DocType(name));
                return new JsonMessage(JsonReturnCodes.Ok);
            } catch (Exception e) {
                return new JsonMessage(JsonReturnCodes.ERROR);
            }
        } else {
            return new JsonMessage(JsonReturnCodes.DONTHAVEPERMISSION);
        }
    }

    @RequestMapping("/categories/{page}")
    @ResponseBody
    public Page<Category> getCategories(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                        @PathVariable("page") int page,
                                        @CookieValue(value = "lang", defaultValue = "1")int lang) {
        Session session = sessionRepository.findOne(sessionId).get();
        if (PermisionChecks.categoriesManagement(session)) {
            Page<Category> cats = categoryRepo.findByActiveOrderByNameAsc(true, constructPageSpecification(page, 20));
            cats.forEach(c->c.setLang(lang));
            return cats;
        } else
            return null;
    }

    @RequestMapping("/categories")
    @ResponseBody
    public List<Category> getCategoriesAll( @CookieValue(value = "lang", defaultValue = "1")int lang) {
        List<Category> cats = categoryRepo.findByActiveAndVisible(true, true);
        cats.forEach(c->c.setLang(lang));
        return cats;
    }

    @RequestMapping("uploadcategorylogo/{id}")
    @ResponseBody
    public boolean uploadCategoryLogo(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @PathVariable("id") long id,
                                      @RequestParam("file") MultipartFile file) {

        Session session = sessionRepository.findOne(sessionId).get();
        if (!PermisionChecks.categoriesManagement(session))
            return false;
        if (file.isEmpty()) {
            return false;
        } else {
            try {

                Category category = categoryRepo.findOne(id).get();
                UUID uuid = UUID.randomUUID();
                Files.copy(file.getInputStream(), Paths.get(Variables.appDir +
                        "/images/categoryLogos", uuid.toString()));
                category.setLogo(uuid.toString());
                categoryRepo.save(category);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @RequestMapping("categorylogo/{id}")
    @ResponseBody
    public byte[] doc(HttpServletResponse response, @CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId, @PathVariable("id") long id) {


        Session session;
        Optional<Session> result = sessionRepository.findOne(sessionId);
        if (result.isPresent())
            session = result.get();


        Category category = categoryRepo.findOne(id).get();
        Path path;

        if (category.getLogo() == null) {
            path = Paths.get(Variables.appDir + "/images/categoryLogos/nologo.png");
        } else {
            path = Paths.get(Variables.appDir + "/images/categoryLogos/" + category.getLogo());
        }

        try {
            byte[] data = Files.readAllBytes(path);
            //response.setContentType("image/png");
            //response.setHeader("filename", (category.getName().replace(" ", "")) + ".png");
            //response.setHeader("Content-disposition", "attachment; filename=" + (category.getName().replace(" ", "")) + ".png");
            //response.getOutputStream().write(data);
            //response.flushBuffer();
            //return data;
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/givecategorydoctype")
    @ResponseBody
    public JsonMessage giveUserPermissions(long id, @RequestParam(value = "ids") ArrayList<Long> ids) {

        Category category = categoryRepo.findOne(id).get();
        for (Long id1 : ids) {
            category.getDocTypes().add(docTypeRepo.findOne(id1).get());
        }
        categoryRepo.save(category);
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "წარმატებით");

    }

    @RequestMapping("/removecategorydoctype")
    @ResponseBody
    public JsonMessage removeUserPermissions(long id, @RequestParam(value = "ids") ArrayList<Long> ids) {

        Category category = categoryRepo.findOne(id).get();
        for (Long id1 : ids) {
            category.getDocTypes().remove(docTypeRepo.findOne(id1));
        }
        categoryRepo.save(category);
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "წარმატებით");

    }

    @RequestMapping("/getcategorydocs/{id}")
    @ResponseBody
    public List<DocType> getCategoryDocs(@CookieValue("projectSessionId") String sessionId, @PathVariable("id") long id) {

        return categoryRepo.findOne(id).get().getDocTypes();

    }

    @RequestMapping("/getnotcategorydocs/{id}")
    @ResponseBody
    public List<DocType> getNotCategoryDocs(@CookieValue("projectSessionId") String sessionId, @PathVariable("id") long id) {
        Session session = sessionRepository.findOne(Long.parseLong(sessionId)).get();
        List<DocType> docTypes = docTypeRepo.findAll();
        docTypes.removeAll(categoryRepo.findOne(id).get().getDocTypes());
        return docTypes;
    }

    @RequestMapping("/doctypes")
    @ResponseBody
    public List<DocType> getDocTypes(@CookieValue("projectSessionId") String sessionId) {
        return docTypeRepo.findAll();
    }

    @RequestMapping("/topcategories")
    @ResponseBody
    public Page<Category> getTopCategories( @CookieValue(value = "lang", defaultValue = "1")int lang) {
        Page<Category> cats = categoryRepo.findByActiveAndVisible(true, true, constructPageSpecification(0, 6));
        cats.forEach(category -> category.setLang(lang));
        return cats;
    }


    private Pageable constructPageSpecification(int pageIndex,int size) {
        return new PageRequest(pageIndex, size);
    }


    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private DocTypeRepo docTypeRepo;
}
