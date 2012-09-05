# --- Sample dataset

# --- !Ups

insert into client values ((select next value for client_seq),'MINI');
insert into client values ((select next value for client_seq),'NIKE');
insert into project values((select next value for project_seq),1,'MIN344545','Mini Mobile','Miles Gilder','London');
insert into project values((select next value for project_seq),2,'NIK355466','Game on World','Dave Robbins','London');
insert into resource values((select next value for resource_seq),1,'Bindiya','Jinnappa','TDM','Technology');
insert into resource values((select next value for resource_seq),2,'Bindiya','Jinnappa','TDM','Technology');
insert into resource values((select next value for resource_seq),1,'Andrew','Smith','TA','Technology');
insert into resource values((select next value for resource_seq),1,'Sebastian','Wolf','SWD','Technology');

