ALTER TABLE click_count DROP FOREIGN KEY click_count_FK_product_id;

ALTER TABLE promotion DROP FOREIGN KEY promotion_FK_product_id;

ALTER TABLE promotion DROP FOREIGN KEY promotion_FK_catagory_id;

ALTER TABLE product DROP FOREIGN KEY product_FK_catagory_id;

