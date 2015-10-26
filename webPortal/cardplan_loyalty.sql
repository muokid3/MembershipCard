-- phpMyAdmin SQL Dump
-- version 4.3.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Oct 08, 2015 at 04:28 AM
-- Server version: 5.5.42-37.1
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cardplan_loyalty`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(10) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `name`) VALUES
(36, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Administrator'),
(39, 'st34lth', 'e8a44c0d8faa57730a929e1931c509b1', 'St34lth Fr34k'),
(41, 'muoki', 'e7a6d3c2c04b2675046289a91e2de749', 'Dennis Muoki');

-- --------------------------------------------------------

--
-- Table structure for table `merchants`
--

CREATE TABLE IF NOT EXISTS `merchants` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `location` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `rate` int(10) NOT NULL DEFAULT '10',
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `merchants`
--

INSERT INTO `merchants` (`id`, `name`, `location`, `email`, `rate`, `password`) VALUES
(4, 'Cakes and Muffins', 'Ngong Road', 'cakes@muffins.com', 10, '848e462678fe13b9c3ae5c15d902ad15'),
(5, 'Garage Rest', 'Ngong Road', 'garage@rest.co', 10, '3824795e4e1fbf0f72f1cf99ee90d861'),
(6, 'Nakumatt', 'Junction', 'nakumatt@junction.com', 10, '5a7752a7a9ebc19cce08176ed8526985'),
(7, 'Petes Cafe', 'Bishop Magua Centre', 'pete@cafe.com', 10, '858d41c9e397b8fa34bb046d8055f276'),
(8, 'Steak N Ale', 'Garage', 'steak@ale.com', 10, '35e244830bde1d967298fb9a585854f7'),
(10, 'Paykind Inc', 'M:lab East Africa', 'rodgers@paykind.co', 10, 'fc09aaf0fe75af0e88206abbc2153eb7');

-- --------------------------------------------------------

--
-- Table structure for table `redemptions`
--

CREATE TABLE IF NOT EXISTS `redemptions` (
  `id` int(11) NOT NULL,
  `account` int(10) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `points` int(10) NOT NULL,
  `datetime` datetime NOT NULL,
  `merchName` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `redemptions`
--

INSERT INTO `redemptions` (`id`, `account`, `name`, `points`, `datetime`, `merchName`) VALUES
(8, 100101, 'Dennis Muoki', 5, '2015-09-23 14:21:06', 'Garage Rest'),
(9, 100105, 'New Card', 30, '2015-09-30 10:21:25', 'Cakes and Muffins'),
(10, 100106, 'Lillian Kanene', 5, '2015-09-30 11:35:42', 'Petes Cafe'),
(11, 100106, 'Lillian Kanene', 10, '2015-09-30 14:42:17', 'Petes Cafe'),
(12, 100107, 'sam me', 10, '2015-09-30 17:48:15', 'Nakumatt');

-- --------------------------------------------------------

--
-- Table structure for table `resetrequests`
--

CREATE TABLE IF NOT EXISTS `resetrequests` (
  `id` int(11) NOT NULL,
  `account` int(10) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `resetcode` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `active` int(10) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `resetrequests`
--

INSERT INTO `resetrequests` (`id`, `account`, `name`, `resetcode`, `active`) VALUES
(19, 100107, 'sam me', 'RC9987', 0),
(18, 100106, 'Lillian Kanene', 'RC8256', 0),
(17, 100101, 'Dennis Muoki', 'RC3904', 0);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) NOT NULL,
  `account` int(10) NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `amount` int(10) NOT NULL,
  `points` int(10) NOT NULL,
  `datetime` datetime NOT NULL,
  `merchName` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `transType` varchar(10) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `account`, `name`, `amount`, `points`, `datetime`, `merchName`, `transType`) VALUES
(14, 100104, 'Pay Kind', 500, 50, '2015-09-25 17:07:26', 'Nakumatt', 'Card'),
(13, 100101, 'Dennis Muoki', 100, 10, '2015-09-23 14:20:29', 'Garage Rest', 'Card'),
(15, 100105, 'New Card', 200, 20, '2015-09-30 10:20:23', 'Cakes and Muffins', 'Card'),
(16, 100106, 'Lillian Kanene', 100, 10, '2015-09-30 10:48:00', 'Petes Cafe', 'Card'),
(17, 100106, 'Lillian Kanene', 100, 10, '2015-09-30 11:04:16', 'Petes Cafe', 'Card'),
(18, 100106, 'Lillian Kanene', 100, 10, '2015-09-30 14:51:47', 'Petes Cafe', 'Card'),
(19, 100106, 'Lillian Kanene', 500, 50, '2015-09-30 15:53:43', 'Nakumatt', 'Card'),
(20, 100107, 'sam me', 1000, 100, '2015-09-30 17:44:24', 'Nakumatt', 'Card'),
(24, 100108, 'Charles Njaramba ', 500, 50, '2015-10-06 12:01:48', 'Nakumatt', 'Card'),
(25, 100108, 'Charles Njaramba ', 5000, 500, '2015-10-06 12:06:28', 'Nakumatt', 'Cash');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) NOT NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `account` int(10) NOT NULL,
  `pin` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `balance` int(10) NOT NULL,
  `points` int(10) NOT NULL,
  `active` int(10) NOT NULL DEFAULT '1',
  `merchName` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `phone`, `account`, `pin`, `balance`, `points`, `active`, `merchName`) VALUES
(2, 'Default', 'Default', '254713653112', 100100, '7653', 0, 0, 0, ' 	Cakes and Muffins'),
(31, 'Rodgers', 'rodgers@paykind.co', '+254726209286', 100103, '8869', 0, 0, 1, 'Nakumatt'),
(30, 'samuel masinde me', 'sam@paykind.co', '+254720114007', 100102, '3235', 200, 0, 1, ' 	Cakes and Muffins'),
(29, 'Dennis Muoki', 'muokid3@gmail.com', '+254713653112', 100101, '2222', 900, 5, 1, 'Steak N Ale'),
(32, 'Pay Kind', 'pay@kind.com', '+254738562651', 100104, '1111', 500, 50, 1, 'Petes Cafe'),
(33, 'New Card', 'new@new.com', '+254713653112', 100105, '8437', 800, 40, 1, 'Nakumatt'),
(34, 'Lillian Kanene', 'lilian@kanene.com', '+254713653112', 100106, '1111', 4200, 65, 1, 'Cakes and Muffins'),
(36, 'sam me', 'sam@paykind.co', '+254720114007', 100107, '1234', 0, 90, 1, 'Nakumatt'),
(40, 'mwizi', 'maniaxwillie@gmail.com', '+254726791019', 100109, '3623', 0, 0, 1, 'Nakumatt'),
(39, 'Charles Njaramba ', 'njarasdey@gmail.com', '+254712388201', 100108, '7015', 500, 550, 1, 'Nakumatt');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `username` (`username`), ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `merchants`
--
ALTER TABLE `merchants`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `redemptions`
--
ALTER TABLE `redemptions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `resetrequests`
--
ALTER TABLE `resetrequests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT for table `merchants`
--
ALTER TABLE `merchants`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `redemptions`
--
ALTER TABLE `redemptions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `resetrequests`
--
ALTER TABLE `resetrequests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=26;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=41;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
