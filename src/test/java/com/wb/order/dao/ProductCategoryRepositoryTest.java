package com.wb.order.dao;

import com.wb.order.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository repository;

    @Test
    public void findById(){
        Optional<ProductCategory> optional = repository.findById(1);
        if (optional.isPresent()) {
            ProductCategory productCategory = optional.get();
            System.out.println(productCategory.toString());

        }
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    @Test
    public void update(){
        Optional<ProductCategory> optional = repository.findById(2);
        if (!optional.isPresent()) {

            System.out.println("操作失败");
        }
        ProductCategory productCategory = optional.get();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);
        ProductCategory save = repository.save(productCategory);
        Assert.assertNotNull(save);
    }

    @Test
    public void findByCategoryTypeIn(){
        List<Integer> list = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        for (ProductCategory productCategory:result){
            System.out.println(productCategory);
        }
    }
}