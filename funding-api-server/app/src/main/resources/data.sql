INSERT INTO gifticon (expiration_period, price, created_at, last_modified_at, stock, name, brand, category, description, image_url, status)
VALUES
    (30, 5000, '2023-01-01 10:00:00', '2023-01-01 10:00:00', 100, 'Coffee Gifticon', 'Brand A', 'Food & Beverage', 'A coffee gifticon for Brand A', 'http://example.com/image1.jpg', 'ACTIVE'),
    (60, 10000, '2023-02-01 12:00:00', '2023-02-01 12:00:00', 50, 'Pizza Gifticon', 'Brand B', 'Food & Beverage', 'A pizza gifticon for Brand B', 'http://example.com/image2.jpg', 'ACTIVE'),
    (90, 20000, '2023-03-01 14:00:00', '2023-03-01 14:00:00', 75, 'Spa Gifticon', 'Brand C', 'Wellness', 'A spa gifticon for Brand C', 'http://example.com/image3.jpg', 'INACTIVE'),
    (120, 15000, '2023-04-01 16:00:00', '2023-04-01 16:00:00', 30, 'Movie Gifticon', 'Brand D', 'Entertainment', 'A movie gifticon for Brand D', 'http://example.com/image4.jpg', 'DELETED'),
    (45, 7500, '2023-05-01 18:00:00', '2023-05-01 18:00:00', 20, 'Ice Cream Gifticon', 'Brand E', 'Food & Beverage', 'An ice cream gifticon for Brand E', 'http://example.com/image5.jpg', 'ACTIVE');

/* 유저 10명 삽입 */
INSERT INTO users (birthdate, point, vendor, created_at, last_modified_at, email, nickname, password, profile_image_url, vendor_email, role) VALUES
 ('1980-01-01', 100, 'KAKAO', '2023-01-01 10:00:00', '2023-01-01 10:00:00', 'user1@example.com', 'nickname1', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile1.jpg', 'vendor1@example.com', 'ROLE_USER'),
 ('1981-02-02', 200, 'KAKAO', '2023-02-02 11:00:00', '2023-02-02 11:00:00', 'user2@example.com', 'nickname2', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile2.jpg', 'vendor2@example.com', 'ROLE_USER'),
 ('1982-03-03', 300, 'KAKAO', '2023-03-03 12:00:00', '2023-03-03 12:00:00', 'user3@example.com', 'nickname3', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile3.jpg', 'vendor3@example.com', 'ROLE_USER'),
 ('1983-04-04', 400, 'KAKAO', '2023-04-04 13:00:00', '2023-04-04 13:00:00', 'user4@example.com', 'nickname4', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile4.jpg', 'vendor4@example.com', 'ROLE_USER'),
 ('1984-05-05', 500, 'KAKAO', '2023-05-05 14:00:00', '2023-05-05 14:00:00', 'user5@example.com', 'nickname5', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile5.jpg', 'vendor5@example.com', 'ROLE_USER'),
 ('1985-06-06', 600, 'KAKAO', '2023-06-06 15:00:00', '2023-06-06 15:00:00', 'user6@example.com', 'nickname6', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile6.jpg', 'vendor6@example.com', 'ROLE_USER'),
 ('1986-07-07', 700, 'KAKAO', '2023-07-07 16:00:00', '2023-07-07 16:00:00', 'user7@example.com', 'nickname7', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile7.jpg', 'vendor7@example.com', 'ROLE_USER'),
 ('1987-08-08', 800, 'KAKAO', '2023-08-08 17:00:00', '2023-08-08 17:00:00', 'user8@example.com', 'nickname8', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile8.jpg', 'vendor8@example.com', 'ROLE_USER'),
 ('1988-09-09', 900, 'KAKAO', '2023-09-09 18:00:00', '2023-09-09 18:00:00', 'user9@example.com', 'nickname9', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile9.jpg', 'vendor9@example.com', 'ROLE_USER'),
 ('1989-10-10', 1000, 'KAKAO', '2023-10-10 19:00:00', '2023-10-10 19:00:00', 'user10@example.com', 'nickname10', '$2a$10$ByZcUIVkccXxA0TeHA6UjewG7Pht5zYK69hgwbTAbfi5bwCHrwDFa', 'http://example.com/profile10.jpg', 'vendor10@example.com', 'ROLE_ADMIN');
