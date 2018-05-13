package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.School;
import pl.edu.agh.ki.mwo.model.SchoolClass;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class StudentsController {

	@RequestMapping(value = "/Students")
	public String listStudent(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());

		return "studentsList";
	}

	@RequestMapping(value = "/AddStudent")
	public String displayAddStudentForm(Model model, HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());

		return "studentForm";
	}

	@RequestMapping(value = "/CreateStudent", method = RequestMethod.POST)
	public String createStudent(@RequestParam(value = "studentName", required = false) String name,
			@RequestParam(value = "studentSurname", required = false) String surname,
			@RequestParam(value = "studentPesel", required = false) String pesel,
			@RequestParam(value = "studentSchoolClass", required = false) String classId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		Student stud = new Student();
		stud.setName(name);
		stud.setSurname(surname);
		stud.setPesel(pesel);

		DatabaseConnector.getInstance().addStudent(stud, classId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Nowa student został dodany");

		return "studentsList";
	}

	@RequestMapping(value = "/DeleteStudent", method = RequestMethod.POST)
	public String deleteStudent(@RequestParam(value = "studentId", required = false) String studentId, Model model,
			HttpSession session) {
		if (session.getAttribute("userLogin") == null)
			return "redirect:/Login";

		DatabaseConnector.getInstance().deleteStudent(studentId);
		model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
		model.addAttribute("message", "Student został usunięty");

		return "studentsList";

	}
	
	
    @RequestMapping(value="/FillFormStudent")
   public String fillFormStudent(@RequestParam(value="studentId", required=false) String studentId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Student student = DatabaseConnector.getInstance().getStudent(studentId);
    	model.addAttribute("studentName", student.getName());
    	model.addAttribute("studentSurname", student.getSurname());
    	model.addAttribute("studentPesel", student.getPesel());
    	model.addAttribute("studentId",student.getId());
    	model.addAttribute("studentClass", DatabaseConnector.getInstance().getStudentClass(studentId));
    	model.addAttribute("studentClassId",  DatabaseConnector.getInstance().getStudentClassId(studentId));
    	model.addAttribute("schoolClasses", DatabaseConnector.getInstance().getSchoolClasses());
 

    	
        return "studentModifyForm";
    }
    
    @RequestMapping(value="/UpdateStudent", method=RequestMethod.POST)
    public String updateStudent(@RequestParam(value="studentName", required=false) String name,
    		@RequestParam(value="studentSurname", required=false) String surname,
    		@RequestParam(value="studentPesel", required=false) String pesel,
    		@RequestParam(value="schoolClassStudent",required=false)  String schoolClassId,
    		@RequestParam(value="studentId", required=false) String studentId,
    		
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	
    	Student student = DatabaseConnector.getInstance().getStudent(studentId);
    
    	student.setName(name);
    	student.setSurname(surname);
    	student.setPesel(pesel);
    	
    	   
    	DatabaseConnector.getInstance().addStudent(student, schoolClassId);
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Uczeń został zmieniony");
         	
    	return "studentsList";
}
	

}