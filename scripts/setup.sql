--clean up
delete from documents_employment;
delete from salaries;
delete from bonus;
delete from bonus_type;
delete from users;
delete from roles;

insert into roles values('1', 'manager');
insert into roles values('2', 'employee');

insert into users(user_id,
                  username,
                  password,
                  address,
                  avatar_content,
                  avatar_name,
                  city,
                  county,
                  email,
                  first_name,
                  last_name, 
                  hd_current_year,
                  hd_last_year,
                  hd_received_current_year,
                  hire_date,
                  phone,
                  salary,
                  department_id,
                  job_id,
                  manager_id,
                  role_id
                  ) 
values (1, 'john.smith', 'password', 'Sillicon Valey', null, 'myavatar', 'California', 'California', 'john.smith@bigcorp.com',
        'John', 'Smith', 7, 7, 7,'02-03-2016','072183093', 52.78, null, null, null, 1);


insert into bonus_type values (1, 'Performance');
insert into bonus values(1,'Yes', '03-05-2017', '07-07-2017', 'Very good guy', 'yes', 234.56, 1, 1,1, 1);

insert into salaries values(1, 11000.98, '24-03-2018', 1);

insert into documents_employment values(1, null, 'Previous employment', 1);

commit;