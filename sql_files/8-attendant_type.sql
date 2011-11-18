USE  [test14]
SET IDENTITY_INSERT  [test14].[dbo].[TargetType] ON
INSERT INTO [test14].[dbo].[TargetType] ([id],[Beschrijving], [IsActive]) VALUES (1,'Intermediaire doelgroep',1)
INSERT INTO [test14].[dbo].[TargetType] ([id],[Beschrijving], [IsActive]) VALUES (5,'Uiteindelijke doelgroep',1)

SET IDENTITY_INSERT  [test14].[dbo].[TargetType] OFF

SET IDENTITY_INSERT  [test14].[dbo].[AttendantType] ON
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (2, 1, 'Preventiewerkers')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (3, 1, 'Intermediairen')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (4, 1, 'Bestuursniveau')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (6, 5, 'Jongeren')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (7, 5, 'Familiale omgeving')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (8, 5, 'Volwassenen')
INSERT INTO [test14].[dbo].[AttendantType] ([id],[targetTypeId_id],[Naam]) VALUES (9, 5, 'Andere')


SET IDENTITY_INSERT  [test14].[dbo].[AttendantType] OFF
SET IDENTITY_INSERT [test14].[dbo].[Evaluvators] ON
INSERT INTO [test14].[dbo].[Evaluvators] ([id], [Naam],[IsActive]) VALUES (1, 'Jijzelf', 1)
INSERT INTO [test14].[dbo].[Evaluvators] ([id], [Naam], [IsActive]) VALUES (2, 'Een externe persoon of organisatie', 1)
INSERT INTO [test14].[dbo].[Evaluvators] ([id], [Naam], [IsActive]) VALUES (3, 'Beide', 1)
SET IDENTITY_INSERT [test14].[dbo].[Evaluvators] OFF
SET IDENTITY_INSERT [test14].[dbo].[Evaluvation_Type] ON
INSERT INTO [test14].[dbo].[Evaluvation_Type] ([id],[EvalType]) VALUES (1,'Mondeling')
INSERT INTO [test14].[dbo].[Evaluvation_Type] ([id],[EvalType]) VALUES (2,'Schriftel')
SET IDENTITY_INSERT [test14].[dbo].[Evaluvation_Type] OFF
GO