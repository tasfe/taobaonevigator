
insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (1, 'ACCEPT', 'Order is accepted', 'N', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (2, 'CHALLENGE', 'When order is challenged status of order is challenged', 'N', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (3, 'DENY', 'When order is denyed by system or by business rule status of an order will be DENY', 'N', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (4, 'NO_INFORMATION', 'When system do not have sufficient information to determine order status then it will be marked as NO_INFORMATION', 'N', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (5, 'CANCELLED', 'When customer cancel order,OMS will send Cancel xml to Fraud Engine ', 'N', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', '08-SEP-11 08.26.31.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (6, 'ACTIVE', 'When business rule is set to be active then this status will be used', 'N', '08-SEP-11 08.26.32.000000 PM', 'SYSTEM', '08-SEP-11 08.26.32.000000 PM', 'SYSTEM', 1);

insert into status_lk (STATUS_PK, NAME, DESCRIPTION, SOFT_DELETE, CREATED_DATE, CREATED_BY, LAST_MODIFIED_DATE, LAST_MODIFIED_BY, VERSION)
values (7, 'INACTIVE', 'When business rule is set to be inactive then this status will be used', 'N', '08-SEP-11 08.26.32.000000 PM', 'SYSTEM', '08-SEP-11 08.26.32.000000 PM', 'SYSTEM', 1);

