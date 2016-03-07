drop table sun
CREATE TABLE sun (
       sunno                 NUMBER          NOT NULL  PRIMARY KEY ,
       wname                 VARCHAR(20)     NOT NULL,
       gug                 NUMBER DEFAULT 0  NOT NULL
                                   CHECK (gug BETWEEN 0 AND 100),
       eng                 NUMBER DEFAULT 0 NOT NULL
                                   CHECK (eng BETWEEN 0 AND 100),
       math                 NUMBER DEFAULT 0 NOT NULL
                                   CHECK (math BETWEEN 0 AND 100),
       total                 NUMBER DEFAULT 0 NOT NULL,
       avr                 NUMBER(3,1) DEFAULT 0 NOT NULL,
       credit                 CHAR(1) NOT NULL
                                   CHECK (credit IN ('A', 'B', 'C', 'D', 'F'))
);

CREATE TABLE depart (
       partno             VARCHAR(5) NOT NULL,
       partname               VARCHAR(20) NULL,
       PRIMARY KEY (partno)
);


CREATE TABLE employee (
       employeeno             CHAR(18) NOT NULL,
       mname                 CHAR(18) NULL,
       registno             CHAR(18) NULL,
       tel             CHAR(18) NULL,
       hp             CHAR(18) NULL,
       zipcode             CHAR(18) NULL,
       address                 CHAR(18) NULL,
       partno             VARCHAR(5) NOT NULL,
       bossno         CHAR(18) NULL,
       PRIMARY KEY (employee), 
       FOREIGN KEY (bossno)
                             REFERENCES employee, 
       FOREIGN KEY (partcode)
                             REFERENCES part
);


CREATE TABLE human  (
       height                   CHAR(18) NULL,
       weight               CHAR(18) NULL,
       viewleft               CHAR(18) NULL,
       viewright               CHAR(18) NULL,
       blood               CHAR(18) NULL,
       memberno             CHAR(18) NOT NULL,
       PRIMARY KEY (employeeno), 
       FOREIGN KEY (employeeno)
                             REFERENCES employee
);


CREATE TABLE member (
       memberid               CHAR(18) NOT NULL,
       registrationno             CHAR(18) NULL,
       tel             CHAR(18) NULL,
       hp             CHAR(18) NULL,
       zipcode             CHAR(18) NULL,
       address                 CHAR(18) NULL,
       mdate             CHAR(18) NULL,
       PRIMARY KEY (memberid)
);


CREATE TABLE product (
       productno             CHAR(18) NOT NULL,
       productname               CHAR(18) NOT NULL,
       cprice             CHAR(18) NULL,
       sprice             CHAR(18) NULL,
       squantity             CHAR(18) NULL,
       manufacture               CHAR(18) NULL,
       pdate             CHAR(18) NULL,
       PRIMARY KEY (productno)
);


CREATE TABLE req (
       reqno             CHAR(18) NOT NULL,
       memberno               CHAR(18) NOT NULL,
       productno             CHAR(18) NOT NULL,
       quantity             CHAR(18) NULL,
       total             CHAR(18) NULL,
       information            CHAR(18) NULL,
       PRIMARY KEY (reqno), 
       FOREIGN KEY (productno)
                             REFERENCES product, 
       FOREIGN KEY (memberid)
                             REFERENCES member
);


CREATE TABLE supply (
       supplyno             CHAR(18) NOT NULL,
       supplyname               CHAR(18) NULL,
       represent             CHAR(18) NULL,
       rtel                 CHAR(18) NULL,
       person               CHAR(18) NULL,
       ptel           CHAR(18) NULL,
       pzipcode             CHAR(18) NULL,
       paddress                 CHAR(18) NULL,
       PRIMARY KEY (supplyno)
);


CREATE TABLE enter (
       enterno             CHAR(18) NOT NULL,
       productno             CHAR(18) NOT NULL,
       supplyno             CHAR(18) NOT NULL,
       equantity             CHAR(18) NULL,
       edate             CHAR(18) NULL,
       eprice             CHAR(18) NULL,
       PRIMARY KEY (enterno), 
       FOREIGN KEY (supplyno)
                             REFERENCES supply, 
       FOREIGN KEY (productno)
                             REFERENCES product
);


CREATE TABLE productdiv (
       productdivno             CHAR(18) NOT NULL,
       productdivname               CHAR(18) NULL,
       highrankno             CHAR(18) NULL,
       PRIMARY KEY (productdivno), 
       FOREIGN KEY (highrankno)
                             REFERENCES productdiv
);



