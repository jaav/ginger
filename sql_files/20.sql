UPDATE Activity SET TotalParticipants = 2 WHERE TotalParticipants = 11;
UPDATE Activity SET TotalParticipants = 6 WHERE TotalParticipants = 2;
UPDATE Activity SET TotalParticipants = 17 WHERE TotalParticipants = 3;
UPDATE Activity SET TotalParticipants = 37 WHERE TotalParticipants = 4;
UPDATE Activity SET TotalParticipants = 75 WHERE TotalParticipants = 5;
UPDATE Activity SET TotalParticipants = 150 WHERE TotalParticipants = 6;
update Activity set LocationId_id = (LocationId_id - 1) where LocationId_id < 2887;
update VadGingerUser set passwordHash = 'NzcrOUN1Ky92VGxKNzcrOVdlKy92ZSsvdlZidnY3MVg3Nys5RCsrL3ZUND0=';
update VadGingerUser set role = 1 where userID in ('vadmge');
update VadGingerUser set role = 2 where userID in ('vadjro');
update VadGingerUser set role = 3 where userID in ('jefwau');

update ActivityType set id = 999 where Naam = 'Andere Actie';
update ActivityTypeJunction set activityTypeId_id = 999 where activityTypeId_id = 19;
update Evaluvation_Type set EvalType = 'Schriftelijk' where EvalType = 'Schriftel';

update ActivityType set id = 19 where id = 1000;
update ActivityType set id = 20 where id = 1001;
update ActivityType set id = 21 where id = 1003;
update ActivityType set ouder_id = 19 where ouder_id = 1000;