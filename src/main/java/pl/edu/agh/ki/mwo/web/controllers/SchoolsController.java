package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class SchoolsController {

    @RequestMapping(value="/Schools")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	
        return "schoolsList";    
    }
    
    @RequestMapping(value="/AddSchool")
    public String displayAddSchoolForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "schoolForm";    
    }

    @RequestMapping(value="/CreateSchool", method=RequestMethod.POST)
    public String createSchool(@RequestParam(value="schoolName", required=false) String name,
    		@RequestParam(value="schoolAddress", required=false) String address,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	School school = new School();
    	school.setName(name);
    	school.setAddress(address);
    	
    	DatabaseConnector.getInstance().addSchool(school);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Nowa szkoła została dodana");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/DeleteSchool", method=RequestMethod.POST)
    public String deleteSchool(@RequestParam(value="schoolId", required=false) String schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteSchool(schoolId);    	
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Szkoła została usunięta");
         	
    	return "schoolsList";
    }
    
    @RequestMapping(value="/FillFormSchool")
   public String fillFormSchool(@RequestParam(value="schoolId", required=false) Long schoolId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	School school = DatabaseConnector.getInstance().getSchool(schoolId);
    	model.addAttribute("schoolName", school.getName());
    	model.addAttribute("schoolAddress", school.getAddress());
    	model.addAttribute("schoolId", school.getId());
    	
        return "schoolModifyForm";
    }
    
    @RequestMapping(value="/UpdateSchool", method=RequestMethod.POST)
    public String updateSchool(@RequestParam(value="schoolName", required=false) String schoolName,
    		@RequestParam(value="schoolAddress", required=false) String schoolAddress,
    		@RequestParam(value="schoolId", required=false) Long schoolId,
    		
    		
    		Model model, HttpSession session)
    {   	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	School school = DatabaseConnector.getInstance().getSchool(schoolId);
    
    	school.setName(schoolName);
    	school.setAddress(schoolAddress);
    	
    	   
    	DatabaseConnector.getInstance().addSchool(school);
       	model.addAttribute("schools", DatabaseConnector.getInstance().getSchools());
    	model.addAttribute("message", "Szkoła została zmieniona");
         	
    	return "schoolsList";
}


}