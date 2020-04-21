package com.wb.order.controller;

import com.sun.xml.internal.bind.v2.TODO;
import com.wb.order.domain.ProductCategory;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.form.CategoryForm;
import com.wb.order.sercice.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){

        List<ProductCategory> productCategoryList = categoryService.findAll();

        map.put("categoryList",productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 新增、修改页面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",defaultValue = "-1" ) Integer categoryId ,
                              Map<String,Object> map){
        ProductCategory productCategory = null;
        //未传入categoryId,则是新增
        if(-1 == categoryId){
            productCategory = new ProductCategory();
            map.put("ProductCategory",productCategory);
            return new ModelAndView("category/index",map);

        }
        try {
            productCategory = categoryService.findOne(categoryId);
        }catch (NoSuchElementException e){
            map.put("msg", ResultEnum.CATEGORY_NOT_EXIST.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }catch (SellException e){
            map.put("msg", ResultEnum.CATEGORY_NOT_EXIST.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
        map.put("category",productCategory);
        return new ModelAndView("category/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        ProductCategory productCategory = new ProductCategory();
        try {
            if (form.getCategoryId() != null){
                //修改
                productCategory =  categoryService.findOne(form.getCategoryId());
            }

            BeanUtils.copyProperties(form,productCategory);
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }catch (NoSuchElementException e){
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.CATEGORY_SAVE_SUCCESS.getMessage());
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }

}
