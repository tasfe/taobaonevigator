CREATE TABLE USER_MASTER 
   (  "USER_MASTER_PK" NUMBER NOT NULL ENABLE,  --------table pk
      "USERNAME" VARCHAR2(30) unique ENABLE,   ---------username  unique
      "PASSWORD" VARCHAR2(200) NOT NULL ENABLE, ---------password  md5 code
      "FRISTNAME" VARCHAR2(30) NOT NULL ENABLE, ---------FRIST NAME
      "LASTNAME" VARCHAR2(30) NOT NULL ENABLE, ---------LAST TIME
      "EMAIL" VARCHAR2(200) NOT NULL ENABLE, ---------LAST TIME
      "PHONE" VARCHAR2(200) NOT NULL ENABLE, ---------LAST TIME
      "LOGINCOUNT" NUMBER NOT NULL ENABLE,  ------- the login times
      "SOFT_DELETE" CHAR(1) DEFAULT 'N' NOT NULL ENABLE, ---- soft delete
      "CREATED_DATE" TIMESTAMP (6) NOT NULL ENABLE, ---------create date
      "CREATED_BY" VARCHAR2(40) NOT NULL ENABLE, ---------create by
      "LAST_MODIFIED_DATE" TIMESTAMP (6),       ---------last modified date
      "LAST_MODIFIED_BY" VARCHAR2(40),          ---------last modified date
      "VERSION" NUMBER NOT NULL ENABLE, 
       CONSTRAINT "PK_USER_MASTER" PRIMARY KEY ("USER_MASTER_PK")
   );
