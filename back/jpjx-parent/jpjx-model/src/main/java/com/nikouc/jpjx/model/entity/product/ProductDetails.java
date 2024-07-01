package com.nikouc.jpjx.model.entity.product;

import com.nikouc.jpjx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}