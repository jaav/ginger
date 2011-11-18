

INSERT INTO TargetType (id,Beschrijving, IsActive) VALUES (1,'Intermediaire doelgroep',1);
INSERT INTO TargetType (id,Beschrijving, IsActive) VALUES (5,'Uiteindelijke doelgroep',1);




INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (2, 1, 'Preventiewerkers');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (3, 1, 'Intermediairen');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (4, 1, 'Bestuursniveau');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (6, 5, 'Jongeren');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (7, 5, 'Familiale omgeving');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (8, 5, 'Volwassenen');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (9, 5, 'Andere');




INSERT INTO Evaluvators (id, Naam,IsActive) VALUES (1, 'Jijzelf', 1);
INSERT INTO Evaluvators (id, Naam, IsActive) VALUES (2, 'Een externe persoon of organisatie', 1);
INSERT INTO Evaluvators (id, Naam, IsActive) VALUES (3, 'Beide', 1);


INSERT INTO Evaluvation_Type (id,EvalType) VALUES (1,'Mondeling');
INSERT INTO Evaluvation_Type (id,EvalType) VALUES (2,'Schriftel');


