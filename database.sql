-- ==============================================================================
-- BÀI KIỂM TRA GIỮA KỲ: LẬP TRÌNH WEB
-- SINH VIÊN: HUỲNH TẤN ANH PHÁT - MSSV: 24162091 - ĐỀ 3
-- CSDL: ServletJpa
-- ==============================================================================

USE master;
GO

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'ServletJpa')
BEGIN
    CREATE DATABASE ServletJpa;
END
GO

USE ServletJpa;
GO

-- 1. CATEGORY
IF OBJECT_ID('dbo.Category', 'U') IS NOT NULL DROP TABLE dbo.Category;
CREATE TABLE dbo.Category (
    CategoryId INT IDENTITY(1,1) PRIMARY KEY,
    Categoryname NVARCHAR(100) NULL,
    Categorycode NVARCHAR(100) NULL,
    Images NVARCHAR(500) NULL,
    Status BIT DEFAULT 1
);
GO

-- 2. USERS
IF OBJECT_ID('dbo.Users', 'U') IS NOT NULL DROP TABLE dbo.Users;
CREATE TABLE dbo.Users (
    Username NVARCHAR(50) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NULL,
    Phone NVARCHAR(15) NULL,
    Fullname NVARCHAR(50) NULL,
    Email NVARCHAR(150) NULL,
    Admin BIT DEFAULT 0,
    Active BIT DEFAULT 1,
    Images NVARCHAR(500) NULL
);
GO

-- 3. VIDEOS
IF OBJECT_ID('dbo.Videos', 'U') IS NOT NULL DROP TABLE dbo.Videos;
CREATE TABLE dbo.Videos (
    VideoId NVARCHAR(50) NOT NULL PRIMARY KEY,
    Title NVARCHAR(200) NULL,
    Poster NVARCHAR(50) NULL,
    Views INT DEFAULT 0,
    Description NVARCHAR(500) NULL,
    Active BIT DEFAULT 1,
    Price DECIMAL(18,2) DEFAULT 150000 NULL,
    CategoryId INT NULL,
    CONSTRAINT FK_Videos_Category FOREIGN KEY (CategoryId) REFERENCES dbo.Category(CategoryId) ON DELETE SET NULL
);
GO

-- 4. FAVORITES
IF OBJECT_ID('dbo.Favorites', 'U') IS NOT NULL DROP TABLE dbo.Favorites;
CREATE TABLE dbo.Favorites (
    FavoriteId INT IDENTITY(1,1) PRIMARY KEY,
    LikedDate DATE DEFAULT GETDATE(),
    VideoId NVARCHAR(50) NULL,
    Username NVARCHAR(50) NULL,
    CONSTRAINT FK_Favorites_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE,
    CONSTRAINT FK_Favorites_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username) ON DELETE CASCADE
);
GO

-- 5. SHARES
IF OBJECT_ID('dbo.Shares', 'U') IS NOT NULL DROP TABLE dbo.Shares;
CREATE TABLE dbo.Shares (
    ShareId INT IDENTITY(1,1) PRIMARY KEY,
    Emails NVARCHAR(50) NULL,
    SharedDate DATE DEFAULT GETDATE(),
    Username NVARCHAR(50) NULL,
    VideoId NVARCHAR(50) NULL,
    CONSTRAINT FK_Shares_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username) ON DELETE CASCADE,
    CONSTRAINT FK_Shares_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE
);
GO

-- ==============================================================================
-- TEMPLATE TO TEST
-- ==============================================================================

-- Test accounts
INSERT INTO dbo.Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES
('admin', '123', '0912345678', N'Huỳnh Tấn Anh Phát', 'anhphat@gmail.com', 1, 1, 'admin.jpg'),
('user', '123', '0987654321', N'Nguyễn Văn A', 'user@gmail.com', 0, 1, 'user.jpg');

-- Check CategoryId tự tăng
INSERT INTO dbo.Category (Categoryname, Categorycode, Images, Status) VALUES
(N'Lập trình Web Servlet & JSP', 'WEB_SERVLET', 'web.png', 1),
(N'Kiến trúc JPA & Hibernate', 'JPA_ORM', 'orm.png', 1),
(N'Cơ sở dữ liệu SQL Server', 'DB_MSSQL', 'mssql.png', 1);

-- Video / Sản phẩm (Thêm nhiều bản ghi để test phân trang 6 video/trang)
INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, Price, CategoryId) VALUES
('V01', N'Lập trình Web với Servlet và JSP từ căn bản', 'video1.jpg', 1520, N'Hướng dẫn chi tiết cài đặt Servlet, JSP, JSTL, Sitemesh và mô hình MVC 3 lớp.', 1, 199000, 1),
('V02', N'Tích hợp JPA Hibernate trong Java Web', 'video2.jpg', 2340, N'Kết nối cơ sở dữ liệu SQL Server, định nghĩa Entity, EntityManager và DAO.', 1, 249000, 2),
('V03', N'Thiết kế CSDL quan hệ chuẩn hóa 3NF', 'video3.jpg', 890, N'Cách tạo bảng, quan hệ khóa ngoại và tự tăng ID trong MS SQL Server.', 1, 149000, 3),
('V04', N'Xây dựng bộ lọc SiteMesh Decorator 3', 'video4.jpg', 1200, N'Tối ưu hóa giao diện đa người dùng (Admin & User) với SiteMesh.', 1, 179000, 1),
('V05', N'Xác thực OTP qua Email và Session trong Java', 'video5.jpg', 3100, N'Bảo mật tài khoản với quy trình đăng ký kích hoạt mã OTP và Session.', 1, 299000, 1),
('V06', N'Tối ưu hóa truy vấn JPA với JPQL và Criteria', 'video6.jpg', 950, N'Các phương pháp truy vấn nâng cao và phân trang trong Hibernate ORM.', 1, 199000, 2),
('V07', N'Quản lý giao dịch Transaction trong JPA', 'video7.jpg', 1420, N'Sử dụng EntityManager.getTransaction() để đảm bảo tính toàn vẹn ACID.', 1, 219000, 2),
('V08', N'Triển khai ứng dụng Web Java lên Tomcat 10', 'video8.jpg', 1880, N'Cấu hình DataSource, file WAR và thiết lập môi trường Server.', 1, 159000, 1),
('V09', N'Thiết kế API RESTful với Servlet và Jackson', 'video9.jpg', 2500, N'Giao tiếp dữ liệu JSON giữa Frontend và Java Backend.', 1, 279000, 1),
('V10', N'Bảo mật ứng dụng Web chống SQL Injection và XSS', 'video10.jpg', 3200, N'Các kỹ thuật lập trình phòng thủ trong Servlet và JSP.', 1, 320000, 3);

-- Check FavoriteId tự tăng
INSERT INTO dbo.Favorites (LikedDate, VideoId, Username) VALUES
('2026-09-24', 'V01', 'admin'),
('2026-09-24', 'V02', 'admin'),
('2026-09-24', 'V01', 'user');

-- Check ShareId tự tăng
INSERT INTO dbo.Shares (Emails, SharedDate, Username, VideoId) VALUES
('friend1@gmail.com', '2026-09-24', 'admin', 'V01'),
('friend2@gmail.com', '2026-09-24', 'user', 'V02');
GO

-- Đảm bảo tương thích nếu bảng Videos đã tồn tại từ trước mà chưa có cột Price
IF OBJECT_ID('dbo.Videos', 'U') IS NOT NULL AND NOT EXISTS (SELECT * FROM sys.columns WHERE object_id = OBJECT_ID('dbo.Videos') AND name = 'Price')
BEGIN
    ALTER TABLE dbo.Videos ADD Price DECIMAL(18,2) DEFAULT 150000 NULL;
END
GO

