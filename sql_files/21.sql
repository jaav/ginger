DELETE from VadGingerUser where role = 0;
DELETE from SectorActivityJunction;
DELETE from Organisaties;
DELETE from MaterialsInActivity;
DELETE FROM Locations where IsCluster = 1;
DELETE from ItemsInActivity;
DELETE from CityClusterJunction;
DELETE from Centrum where Naam NOT IN ('VAD', 'JEF', 'SPN');
DELETE from ActivityTypeJunction;
DELETE from ActivityTargets;
DELETE from ActivitySectors;
DELETE from ActivityEvaluvators;
DELETE from Activity;