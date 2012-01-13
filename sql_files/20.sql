UPDATE Activity SET TotalParticipants = 2 WHERE TotalParticipants = 11;
UPDATE Activity SET TotalParticipants = 6 WHERE TotalParticipants = 2;
UPDATE Activity SET TotalParticipants = 17 WHERE TotalParticipants = 3;
UPDATE Activity SET TotalParticipants = 37 WHERE TotalParticipants = 4;
UPDATE Activity SET TotalParticipants = 75 WHERE TotalParticipants = 5;
UPDATE Activity SET TotalParticipants = 150 WHERE TotalParticipants = 6;
update VadGingerUser set passwordHash = 'NzcrOUN1Ky92VGxKNzcrOVdlKy92ZSsvdlZidnY3MVg3Nys5RCsrL3ZUND0=';
update VadGingerUser set role = 1 where userID in ('vadmge');
update VadGingerUser set role = 3 where userID in ('vadjro');
update VadGingerUser set role = 3 where userID in ('jefwau');

update ActivityType set id = 999 where Naam = 'Andere Actie';
Update Items set Naam = 'Geen specifiek middel als item' where Naam = 'Middelengebruik algemeen';
update ActivityTypeJunction set activityTypeId_id = 999 where activityTypeId_id = 19;
update Evaluvation_Type set EvalType = 'Schriftelijk' where EvalType = 'Schriftel';

UPDATE Locations SET Naam=(REPLACE (Naam, '-$',''));
UPDATE Locations SET Naam=(REPLACE (Naam,'Regionaal O', 'RegiOOO'));
UPDATE Locations SET Naam=(REPLACE (Naam,'Regionaal', 'Regionaal (clusters)'));
UPDATE Locations SET Naam=(REPLACE (Naam,'RegiOOO', 'Regionaal O'));
