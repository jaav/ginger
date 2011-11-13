

INSERT INTO TargetType (id,Beschrijving) VALUES (1,'Intermediaire doelgroep');
INSERT INTO TargetType (id,Beschrijving) VALUES (5,'Uiteindelijke doelgroep');




INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (2, 1, 'Preventiewerkers');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (3, 1, 'Intermediairen');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (4, 1, 'Bestuursniveau');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (6, 5, 'Jongeren');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (7, 5, 'Familiale omgeving');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (8, 5, 'Volwassenen');
INSERT INTO AttendantType (id,targetTypeId_id,Naam) VALUES (9, 5, 'Andere');




INSERT INTO Evaluvators (id, Naam) VALUES (1, 'Jijzelf');
INSERT INTO Evaluvators (id, Naam) VALUES (2, 'Een externe persoon of organisatie');
INSERT INTO Evaluvators (id, Naam) VALUES (3, 'Beide');


INSERT INTO Evaluvation_Type (id,EvalType) VALUES (1,'Mondeling');
INSERT INTO Evaluvation_Type (id,EvalType) VALUES (2,'Schriftel');


