package org.enset.spring_mvc_thymeleaf.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.enset.spring_mvc_thymeleaf.entities.Patient;
import org.enset.spring_mvc_thymeleaf.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0") int page,
                        @RequestParam(name="size",defaultValue = "4") int size,
                        @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatient=patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatient.getContent());
        model.addAttribute("pages",new int[pagePatient.getTotalPages()]);
        model.addAttribute("keyword",keyword);
        model.addAttribute("currentPage",page);
        return "patients";
    }

    @GetMapping("/admin/ajouterPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String afficherFormulaireAjout(Model model) {
        model.addAttribute("patient", new Patient());
        return "ajouterPatient";
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(Model model,Long id,String keyword,int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("currentPage",page);
        return "editPatient";
    }

    @PostMapping("/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,String keyword,int page) {
        if(bindingResult.hasErrors())
            return "ajouterPatient";
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id,String keyword,int page){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

}
