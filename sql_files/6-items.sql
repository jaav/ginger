USE  [test14]
SET IDENTITY_INSERT  [test14].[dbo].[Items] ON
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (1,'Middelengebruik algemeen',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (2,'Alcohol',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (3,'Illegale drugs',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (4,'Medicatie',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (5,'Tabak',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (6,'Gokken',1)
INSERT INTO [dbo].[Items] ([id],[Naam], [IsActive]]) VALUES (7,'ICT-verslavingen (games, chatten, surfen, ...)',1)

SET IDENTITY_INSERT  [test14].[dbo].[Items] OFF
GO