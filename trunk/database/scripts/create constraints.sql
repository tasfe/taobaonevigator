ALTER TABLE click_count ADD 
      CONSTRAINT click_count_FK_product_id
      FOREIGN    KEY (product_id)
      REFERENCES product(id)
      ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE promotion ADD 
      CONSTRAINT promotion_FK_product_id
      FOREIGN    KEY (product_id)
      REFERENCES product(id)
      ON DELETE CASCADE ON UPDATE CASCADE;
      
ALTER TABLE promotion ADD
      CONSTRAINT promotion_FK_catagory_id
      FOREIGN    KEY (catagory_id)
      REFERENCES catagory(id)
      ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE product ADD 
      CONSTRAINT product_FK_catagory_id
      FOREIGN    KEY (catagory_id)
      REFERENCES catagory(id)
      ON DELETE CASCADE ON UPDATE CASCADE;