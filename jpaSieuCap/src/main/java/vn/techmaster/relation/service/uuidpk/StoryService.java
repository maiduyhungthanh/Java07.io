package vn.techmaster.relation.service.uuidpk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

import vn.techmaster.relation.model.uuidpk.Story;
import vn.techmaster.relation.repository.uuidpk.StoryRepo;

@Service
public class StoryService {
    @Autowired
    private StoryRepo storyRepo;



    @Transactional
    public void generateStore() {
        Story story1 = new Story("Hello");
        storyRepo.save(story1);
        storyRepo.flush();
    }
    public List<Story> getStory() {
        
       return storyRepo.findAll();
    }
}