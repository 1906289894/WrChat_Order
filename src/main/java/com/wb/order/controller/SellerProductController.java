package com.wb.order.controller;

import com.wb.order.domain.ProductCategory;
import com.wb.order.domain.ProductInfo;
import com.wb.order.enums.ResultEnum;
import com.wb.order.exception.SellException;
import com.wb.order.form.ProductFrom;
import com.wb.order.sercice.CategoryService;
import com.wb.order.sercice.ProductService;
import com.wb.order.utils.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 卖家端商品
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 卖家端商品列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size) ;

        return new ModelAndView("/product/list",map);
    }

    @GetMapping("on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.PRODUCT_STATUS_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try {
            productService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.PRODUCT_STATUS_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }

    /**
     * 商品新增、修改功能页面
     * @param productId  非必传参数
     * @param map
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productIds,
                              Map<String,Object> map){

        if (!StringUtils.isEmpty(productIds)){
            //转换productId为Integer类型
            String[] split = productIds.split(",");
            String s = split[0]+split[1];
            Integer productId = Integer.valueOf(s);
            //修改
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有的类目
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categoryList",productCategoryList);
        return new ModelAndView("product/index",map);
    }

    @PostMapping("/save")
    @CacheEvict(cacheNames = "product" ,key = "123")
    public ModelAndView save(@Valid ProductFrom form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        //表单校验出现错误，则跳转到错误页面
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error");
        }

        ProductInfo productInfo = new ProductInfo();
        try {
            //数据复制

            if (!StringUtils.isEmpty(form.getProductId())){
                String productIds = form.getProductId();
                //转换productId为Integer类型
                String[] split = productIds.split(",");
                String s = split[0]+split[1];
                Integer productId = Integer.valueOf(s);
                //如果productId不为空，则说明是修改
                productInfo = productService.findOne(productId);
            }else {
                productInfo.setProductId(KeyUtils.gen2UnqueKey());
                productInfo.setProductStatus(0);
            }

            BeanUtils.copyProperties(form,productInfo);

            //保存
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("common/error");
        }

        map.put("msg",ResultEnum.PRODUCT_SAVE_SUCCESS.getMessage());
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success");
    }
}
