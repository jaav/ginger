

INSERT INTO ActivityType (id,Naam,IsActive) VALUES (1,'Overleg',1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (2,'Voorwaardenscheppend',1, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (3,'Uitwisseling en afstemming',1, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (4,'Concept- en materiaalontwikkeling',1, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (5,'Evaluatie',1, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (6,'Andere',1, 1)
INSERT INTO ActivityType (id,Naam,IsActive) VALUES (7,'Consult / advies',1)
INSERT INTO ActivityType (id,Naam,IsActive) VALUES (8,'Coaching',1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (9,'Beleidsontwikkeling lokaal',8, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (10,'Beleidsontwikkeling in een organisatie',8, 1)
INSERT INTO ActivityType (id,Naam,IsActive) VALUES (11,'Vorming',1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (12,'nformatieoverdracht (lesgeven, voordracht geven,…)',11, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (13,'Inspelen op attitude, via interactieve informatieoverdracht (geen training)',11, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (14,'Inoefenen van vaardigheden (training, train de trainer,…)',11, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (15,'Andere',11, 1)
INSERT INTO ActivityType (id,Naam,IsActive) VALUES (16,'Vroeginterventie',1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (17,'Aan groep',16, 1)
INSERT INTO ActivityType (id,Naam,ouder_id, IsActive) VALUES (18,'Aan persoon (incl. verwijzer of persoon uit de omgeving)',16, 1)
INSERT INTO ActivityType (id,Naam,IsActive) VALUES (19,'Andere actie',1)


GO