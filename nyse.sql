-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Feb 20, 2025 at 01:23 AM
-- Server version: 8.0.40
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nyse`
--
CREATE DATABASE IF NOT EXISTS `nyse` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `nyse`;

-- --------------------------------------------------------

--
-- Table structure for table `debug`
--

DROP TABLE IF EXISTS `debug`;
CREATE TABLE `debug`
(
    `price` DOUBLE DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`
(
    `stock_id`                   VARCHAR(4)   NOT NULL,
    `company_name`               VARCHAR(100) NOT NULL,
    `price_current`              DOUBLE       NOT NULL,
    `price_closing`              DOUBLE       NOT NULL,
    `number_of_shares_available` BIGINT       NOT NULL,
    `shares_bought`              INT          NOT NULL DEFAULT '0',
    `number_of_shares_sold`      BIGINT       NOT NULL,
    `shares_sold`                INT          NOT NULL DEFAULT '0'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`stock_id`, `company_name`, `price_current`, `price_closing`, `number_of_shares_available`,
                     `shares_bought`, `number_of_shares_sold`, `shares_sold`)
VALUES ('AAC', 'AAC Holdings Inc', 0.01, 0, 100000, 0, 60000, 0),
       ('AADI', 'Aadi Bioscience Inc', 2.68, 2.68, 100000, 0, 60000, 0),
       ('AAL', 'American Airlines Group Inc', 16.26, 16.38, 100000, 0, 60000, 0),
       ('AIR', 'AAR Corp', 68.02, 68.01, 100000, 0, 60000, 0),
       ('AMC', 'AMC Entertainment Holdings Inc', 3.5, 3.33, 100000, 0, 60000, 0),
       ('AMCR', 'Amcor PLC', 9.95, 9.95, 100000, 0, 60000, 0),
       ('AOS', 'A O Smith Corp', 65.25, 64.84, 100000, 0, 60000, 0),
       ('ATEN', 'A10 Networks Inc', 20.99, 20.41, 100000, 0, 60000, 0),
       ('BABA', 'Alibaba Group Holding Ltd', 118.3, 115.82, 100000, 0, 60000, 0),
       ('BAC', 'Bank of America Corp', 46.2, 46.57, 100000, 0, 60000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `stock_holder`
--

DROP TABLE IF EXISTS `stock_holder`;
CREATE TABLE `stock_holder`
(
    `stock_holder_id` INT         NOT NULL,
    `name`            VARCHAR(50) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stock_holder`
--

INSERT INTO `stock_holder` (`stock_holder_id`, `name`)
VALUES (1, 'John Wayne'),
       (2, 'Mary Poppins'),
       (3, 'Paul Newman');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
CREATE TABLE `transactions`
(
    `transaction_id`  INT        NOT NULL,
    `stock_holder_id` INT        NOT NULL,
    `stock_id`        VARCHAR(4) NOT NULL,
    `qty`             INT        NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

--
-- Triggers `transactions`
--
DROP TRIGGER IF EXISTS `price_change`;
DELIMITER $$
CREATE TRIGGER `price_change`
    AFTER INSERT
    ON `transactions`
    FOR EACH ROW
BEGIN
    DECLARE running_bought INT;
    DECLARE running_sold INT;
    DECLARE stock_price DOUBLE;
    DECLARE stockID VARCHAR(4) DEFAULT NEW.stock_id;
    DECLARE qty INT DEFAULT NEW.qty;
    DECLARE multiple INT DEFAULT 0;


    SELECT stock.price_current, stock.shares_bought, stock.shares_sold
    FROM stock
    WHERE stockID = stock.stock_id
    INTO stock_price, running_bought, running_sold;

    IF (qty > 0) THEN
        SET running_bought = running_bought + qty;
    ELSE
        SET running_sold = running_sold + (-1 * NEW.qty);
    END IF;

    IF (running_bought > 100) THEN
        SET multiple = running_bought / 100;
        SET stock_price = stock_price + (1.0 * multiple);
        SET running_bought = running_bought - (100 * multiple);
    END IF;
    IF (running_sold > 100) THEN
        SET multiple = running_sold / 100;
        SET stock_price = stock_price - (1.0 * multiple);
        SET running_sold = running_sold - (100 * multiple);
    END IF;

    UPDATE stock
    SET stock.price_current = stock_price,
        stock.shares_bought = running_bought,
        stock.shares_sold   = running_sold
    WHERE stockID = stock.stock_id;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
    ADD PRIMARY KEY (`stock_id`);

--
-- Indexes for table `stock_holder`
--
ALTER TABLE `stock_holder`
    ADD PRIMARY KEY (`stock_holder_id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
    ADD PRIMARY KEY (`transaction_id`),
    ADD KEY `stock_id` (`stock_id`),
    ADD KEY `stock_holder_id` (`stock_holder_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `stock_holder`
--
ALTER TABLE `stock_holder`
    MODIFY `stock_holder_id` INT NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 1;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
    MODIFY `transaction_id` INT NOT NULL AUTO_INCREMENT,
    AUTO_INCREMENT = 1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transactions`
--
ALTER TABLE `transactions`
    ADD CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`stock_holder_id`) REFERENCES `stock_holder` (`stock_holder_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
