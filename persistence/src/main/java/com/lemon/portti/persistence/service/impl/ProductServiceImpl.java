package com.lemon.portti.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lemon.portti.persistence.entity.Product;
import com.lemon.portti.persistence.mapper.ProductMapper;
import com.lemon.portti.persistence.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
