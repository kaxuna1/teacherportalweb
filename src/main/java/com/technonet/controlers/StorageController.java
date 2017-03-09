package com.technonet.controlers;

import com.technonet.Repository.DocumentsRepo;
import com.technonet.Repository.SessionRepository;
import com.technonet.Repository.UserCategoryJoinRepo;
import com.technonet.Repository.UserRepository;
import com.technonet.model.Document;
import com.technonet.model.Session;
import com.technonet.model.User;
import com.technonet.model.UserCategoryJoin;
import com.technonet.staticData.MimeTypes;
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
import java.util.UUID;

/**
 * Created by kakha on 3/5/2017.
 */
@Controller
public class StorageController {
    @RequestMapping("upload/{id}")
    @ResponseBody
    public boolean uploadFile(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                              @PathVariable("id") long id,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("category") long category) {

        Session session=sessionRepository.findOne(sessionId);
        if(!PermisionChecks.FileManagement(session))
            return false;
        if(file.isEmpty()){
            return false;
        }else{
            try {

                User user = userRepository.findOne(id);
                UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(category);
                if(!user.getUserCategoryJoins().contains(userCategoryJoin)){
                    return false;
                }
                String originalName=file.getOriginalFilename();
                UUID uuid=UUID.randomUUID();
                Files.copy(file.getInputStream(), Paths.get(Variables.appDir+"/docs",uuid.toString()));

                Document doc=new Document(originalName,user,uuid.toString(), file.getContentType(),userCategoryJoin);
                documentsRepo.save(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @RequestMapping("upload")
    @ResponseBody
    public boolean uploadFileForMe(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                              @RequestParam("file") MultipartFile file,@RequestParam("category") long category) {

        Session session=sessionRepository.findOne(sessionId);
        if(!session.isIsactive())
            return false;
        if(file.isEmpty()){
            return false;
        }else{
            try {
                User user = session.getUser();
                UserCategoryJoin userCategoryJoin = userCategoryJoinRepo.findOne(category);
                if(!user.getUserCategoryJoins().contains(userCategoryJoin)){
                   return false;
                }
                String originalName=file.getOriginalFilename();
                UUID uuid=UUID.randomUUID();
                Files.copy(file.getInputStream(), Paths.get(Variables.appDir+"/docs",uuid.toString()));
                Document doc=new Document(originalName,user,uuid.toString(), file.getContentType(),userCategoryJoin);
                documentsRepo.save(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    @RequestMapping("listdocs/{id}")
    @ResponseBody
    public Page<Document> getDocs(@CookieValue(value = "projectSessionId", defaultValue = "0") long sessionId,
                                  @PathVariable("id") long id,@RequestParam("page") int page){
        Session session=sessionRepository.findOne(sessionId);
        if(session.isIsactive()&&PermisionChecks.isAdmin(session.getUser())){
            return documentsRepo.findByUserOrderByDateDesc(userRepository.findOne(id),constructPageSpecification(page));
        }else{
            return null;
        }
    }
    @RequestMapping("doc/{id}")
    @ResponseBody
    public void doc(HttpServletResponse response, @CookieValue("projectSessionId") long sessionId, @PathVariable("id") long id){
        Session session=sessionRepository.findOne(sessionId);
        Document doc=documentsRepo.findOne(id);
        if(session.isIsactive()&&(PermisionChecks.isAdmin(session.getUser())||doc.getUser().getId()==session.getUser().getId())){
            File file=new File(Variables.appDir+"/docs/"+doc.getFileName());
            Path path = Paths.get(Variables.appDir+"/docs/"+doc.getFileName());
            try {
                byte[] data = Files.readAllBytes(path);
                response.setContentType(doc.getExtension());
                response.setHeader("filename", doc.getName());
                response.setHeader("Content-disposition", "attachment; filename=" +  doc.getName());
                response.getOutputStream().write(data);
                response.flushBuffer();
                //return data;
            } catch (IOException e) {
                e.printStackTrace();
               // return null;
            }
        }//else //return null;



    }
    private Pageable constructPageSpecification(int pageIndex) {
        return new PageRequest(pageIndex, 10);
    }



    @Autowired
    private UserCategoryJoinRepo userCategoryJoinRepo;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentsRepo documentsRepo;
}
