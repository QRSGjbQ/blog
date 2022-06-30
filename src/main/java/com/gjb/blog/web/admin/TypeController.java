package com.gjb.blog.web.admin;

import com.gjb.blog.po.Type;
import com.gjb.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //分页查询
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC)
                        Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }
    //添加
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
    @PostMapping("/types")
    public String post( Type type, RedirectAttributes redirectAttributes){
        Type type1=typeService.savaType(type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","添加失败");
        }else {
            redirectAttributes.addFlashAttribute("message","添加成功");
        }
        return "redirect:/admin/types";
    }
    //更新
    @PostMapping("/types/{id}")
    public String editPost( Type type, RedirectAttributes redirectAttributes,@PathVariable Long id){
        Type type1=typeService.updateType(id,type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }
    //删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
