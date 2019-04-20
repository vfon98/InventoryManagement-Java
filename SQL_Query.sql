USE InventoryManager
GO
CREATE TABLE Category (
	[No] INT IDENTITY(1, 1),
	ID VARCHAR(10) PRIMARY KEY,
	[Name] NVARCHAR(50) NOT NULL,
	[Description] NVARCHAR(200)
)
GO
SELECT * FROM Category
INSERT INTO Category VALUES ('DM001', 'Tieu dung', 'Not')
TRUNCATE TABLE Category