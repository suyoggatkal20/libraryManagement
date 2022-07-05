CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  `contact1` varchar(45) NOT NULL,
  `contact2` varchar(45) DEFAULT NULL,
  `qualification` varchar(45) DEFAULT NULL,
  `permanent_address_id` bigint NOT NULL,
  `temporary_address_id` bigint NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `FK2tyqw1a1c51br1ot3xt28o0i3` (`permanent_address_id`),
  KEY `FK5jl8qbms184wwch8c5ot8yx3f` (`temporary_address_id`),
  CONSTRAINT `FK2tyqw1a1c51br1ot3xt28o0i3` FOREIGN KEY (`permanent_address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FK5jl8qbms184wwch8c5ot8yx3f` FOREIGN KEY (`temporary_address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  KEY `user_id_user_role_idx` (`user_id`),
  KEY `fk_role_id_user_role_idx` (`role_id`),
  CONSTRAINT `fk_role_id_user_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_id_user_role` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `code` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tokens` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `token` varchar(500) NOT NULL,
  `generation_time` timestamp NOT NULL,
  `expiration_time` timestamp NOT NULL,
  `is_valid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `new_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `token` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `accountType` varchar(50) NOT NULL,
  `expiry` date NOT NULL,
  `created_timestamp` varchar(45) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `issue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bookNo` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `issuerid` bigint NOT NULL,
  `issuedate` date NOT NULL,
  `returnbefore` date NOT NULL,
  `collectorid` bigint DEFAULT NULL,
  `returneddate` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `fine` (
  `issue_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `amount` int NOT NULL,
  `reason` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `bookcopy` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint NOT NULL,
  `purchase_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `auther` varchar(100) DEFAULT NULL,
  `publication` varchar(100) DEFAULT NULL,
  `prise` int NOT NULL,
  `ISBN` varchar(15) NOT NULL,
  `pages` int DEFAULT NULL,
  `publication_year` year DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line_1` varchar(100) NOT NULL,
  `address_line_2` varchar(100) DEFAULT NULL,
  `city` varchar(45) NOT NULL,
  `state` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `pin` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `transection_owner_id` bigint NOT NULL,
  `amount` int NOT NULL,
  `debit_or_credit` varchar(10) NOT NULL,
  `catagory` varchar(20) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
