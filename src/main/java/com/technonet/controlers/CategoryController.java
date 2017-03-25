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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by kakha on 3/7/2017.
 */
@Controller
public class CategoryController {
    @RequestMapping("/createcategory")
    @ResponseBody
    public JsonMessage createCategory(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @RequestParam(value = "name", required = true, defaultValue = "") String name) {
        Session session = sessionRepository.findOne(sessionId);
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
        Session session = sessionRepository.findOne(sessionId);
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
                                        @PathVariable("page") int page) {
        Session session = sessionRepository.findOne(sessionId);
        if (PermisionChecks.categoriesManagement(session)) {
            return categoryRepo.findByActiveOrderByNameAsc(true, constructPageSpecification(page));
        } else
            return null;
    }
    @RequestMapping("/categories")
    @ResponseBody
    public List<Category> getCategoriesAll(){
        return categoryRepo.findByActive(true);
    }

    @RequestMapping("uploadcategorylogo/{id}")
    @ResponseBody
    public boolean uploadCategoryLogo(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                      @PathVariable("id") long id,
                                      @RequestParam("file") MultipartFile file) {

        Session session = sessionRepository.findOne(sessionId);
        if (!PermisionChecks.categoriesManagement(session))
            return false;
        if (file.isEmpty()) {
            return false;
        } else {
            try {

                Category category = categoryRepo.findOne(id);
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
    public byte[] doc(HttpServletResponse response, @CookieValue("projectSessionId") long sessionId, @PathVariable("id") long id) {
        Session session = sessionRepository.findOne(sessionId);
        Category category = categoryRepo.findOne(id);
        Path path;

        if(category.getLogo()==null){
            path = Paths.get(Variables.appDir+"/images/categoryLogos/nologo.png");
        }else{
            path = Paths.get(Variables.appDir+"/images/categoryLogos/" + category.getLogo());
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

        Category category = categoryRepo.findOne(id);
        for (Long id1 : ids) {
            category.getDocTypes().add(docTypeRepo.findOne(id1));
        }
        categoryRepo.save(category);
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "წარმატებით");

    }

    @RequestMapping("/removecategorydoctype")
    @ResponseBody
    public JsonMessage removeUserPermissions(long id, @RequestParam(value = "ids") ArrayList<Long> ids) {

        Category category = categoryRepo.findOne(id);
        for (Long id1 : ids) {
            category.getDocTypes().remove(docTypeRepo.findOne(id1));
        }
        categoryRepo.save(category);
        return new JsonMessage(JsonReturnCodes.Ok.getCODE(), "წარმატებით");

    }

    @RequestMapping("/getcategorydocs/{id}")
    @ResponseBody
    public List<DocType> getCategoryDocs(@CookieValue("projectSessionId") String sessionId, @PathVariable("id") long id) {

        return categoryRepo.findOne(id).getDocTypes();

    }

    @RequestMapping("/getnotcategorydocs/{id}")
    @ResponseBody
    public List<DocType> getNotCategoryDocs(@CookieValue("projectSessionId") String sessionId, @PathVariable("id") long id) {
        Session session = sessionRepository.findOne(Long.parseLong(sessionId));
        List<DocType> docTypes = docTypeRepo.findAll();
        docTypes.removeAll(categoryRepo.findOne(id).getDocTypes());
        return docTypes;
    }

    @RequestMapping("/doctypes")
    @ResponseBody
    public List<DocType> getDocTypes(@CookieValue("projectSessionId") String sessionId) {
        return docTypeRepo.findAll();
    }



    private Pageable constructPageSpecification(int pageIndex) {
        return new PageRequest(pageIndex, 10);
    }


    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private DocTypeRepo docTypeRepo;
}
