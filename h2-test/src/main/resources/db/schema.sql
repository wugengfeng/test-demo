-- 表不存在则创建，存在不删除
CREATE TABLE  IF NOT EXISTS h2_sys_user
(
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(40) NOT NULL,
  phone varchar(15) DEFAULT NULL,
  portrait_url varchar(150) ,
  email varchar(100) ,
  password varchar(255) ,
  is_admin enum('Y','N') ,
  password_strength char(2) ,
  pesonal_description varchar(100) ,
  enable tinyint(4) DEFAULT '1',
  create_time datetime ,
  update_time datetime,
  last_login_time datetime DEFAULT NULL,
  vx_user_id varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE  IF NOT EXISTS h2_all_listing
(
  id int(11) NOT NULL AUTO_INCREMENT,
  area_id int(11) DEFAULT NULL,
  site_id int(11) DEFAULT NULL,
  account_id int(11) DEFAULT NULL,
  seller_sku varchar(100)  DEFAULT 'N/A',
  asin varchar(100)  DEFAULT 'N/A',
  item_name text ,
  parent_asin varchar(100)  DEFAULT 'N/A',
  listing_id varchar(100)  DEFAULT 'N/A',
  open_date datetime DEFAULT NULL,
  price decimal(18,6) DEFAULT '0.000000',
  image_url text ,
  product_id varchar(100)  DEFAULT 'N/A',
  quantity int(5) DEFAULT NULL,
  status varchar(30)  DEFAULT 'N/A',
  create_date datetime DEFAULT NULL ,
  update_date datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
  area varchar(20)  DEFAULT 'N/A',
  account_name varchar(30)  DEFAULT 'N/A' ,
  site_name varchar(50)  DEFAULT NULL,
  parent_id int(11) DEFAULT NULL,
  type enum('CHILD','PARENT','NONE') DEFAULT NULL,
  data_digest varchar(100) DEFAULT NULL,
  auth_id varchar(50) DEFAULT NULL,
  company_sku varchar(200) DEFAULT NULL,
  fnsku varchar(200) DEFAULT NULL,
  sku_type enum('MUTI','SINGLE') DEFAULT NULL,
  product_name varchar(500) DEFAULT NULL,
  copy_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);