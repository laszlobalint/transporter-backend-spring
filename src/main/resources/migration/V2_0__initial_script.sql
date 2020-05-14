-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               10.3.14-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Verzió:              9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for transporter
CREATE DATABASE IF NOT EXISTS `transporter` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_hungarian_ci */;
USE `transporter`;

-- Dumping structure for tábla transporter.booking
CREATE TABLE IF NOT EXISTS `booking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `departure_time` datetime(6) NOT NULL,
  `locationHungary` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `locationSerbia` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  `passenger_id` bigint(20) DEFAULT NULL,
  `transport_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKabxd6qpdfkp11yan46jj1td90` (`passenger_id`),
  KEY `FKct5ry25xkobf1ylgtiqti42ss` (`transport_id`),
  CONSTRAINT `FKabxd6qpdfkp11yan46jj1td90` FOREIGN KEY (`passenger_id`) REFERENCES `passenger` (`id`),
  CONSTRAINT `FKct5ry25xkobf1ylgtiqti42ss` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- Dumping data for table transporter.booking: ~6 rows (approximately)
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` (`id`, `departure_time`, `locationHungary`, `locationSerbia`, `passenger_id`, `transport_id`) VALUES
	(1, '2021-05-16 20:00:00.000000', 'GRINGOS_BUS_STOP', 'MARKET_LIDL', 1, 1),
	(4, '2021-09-11 11:00:00.000000', 'GRINGOS_BUS_STOP', 'POLICE_STATION', 2, 3),
	(8, '2021-03-05 10:30:00.000000', 'GRINGOS_BUS_STOP', 'RADANOVAC', 2, 2),
	(17, '2021-09-11 11:00:00.000000', 'MARKET_SMALL_TESCO', 'POLICE_STATION', 12, 3),
	(22, '2021-03-05 10:30:00.000000', 'GRINGOS_BUS_STOP', 'PALIC_WATERTOWER', 12, 2),
	(24, '2019-10-11 18:00:00.000000', 'ICERINK', 'MARKET_024', 2, 4);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;

-- Dumping structure for tábla transporter.flyway_schema_history
CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) COLLATE utf8_hungarian_ci DEFAULT NULL,
  `description` varchar(200) COLLATE utf8_hungarian_ci NOT NULL,
  `type` varchar(20) COLLATE utf8_hungarian_ci NOT NULL,
  `script` varchar(1000) COLLATE utf8_hungarian_ci NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- Dumping structure for tábla transporter.passenger
CREATE TABLE IF NOT EXISTS `passenger` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `booking_count` int(11) NOT NULL,
  `email` varchar(200) COLLATE utf8_hungarian_ci NOT NULL,
  `is_activated` bit(1) NOT NULL,
  `name` varchar(50) COLLATE utf8_hungarian_ci NOT NULL,
  `password` varchar(60) COLLATE utf8_hungarian_ci NOT NULL,
  `phone_number` varchar(100) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- Dumping data for table transporter.passenger: ~9 rows (approximately)
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
INSERT INTO `passenger` (`id`, `booking_count`, `email`, `is_activated`, `name`, `password`, `phone_number`) VALUES
	(1, 1, 'test@test.com', b'0', 'Test Passenger', '$2a$10$0qyR6duoUoX1H1PnNhBGhuglEfVLzHc1tWEtQaN0HLuDwpKMx5bGe', '+36-70-11111111'),
	(2, 2, 'gmail@gmail.com', b'1', 'Mr. John Doe', '$2a$10$N1szxulAmyNKq0vpGgOnK.d/D6fkYTtNs9U6cuXo6pNgoISpk36YO', '+36-70-5555555'),
	(5, 0, 'johndoe@hobby.local', b'0', 'Bálint', '$2a$10$6jqtLbEV9SKehmPVI867eeXvkLIVg1xg68mp00UAKRpvFOEhEmcQi', '+381111111'),
	(6, 0, 'valami@valami.com', b'0', 'Hejhó', '$2a$10$eax36WZ1J9as2DpjZ3CHduGPc0ZT8xdWL5LaKX3b.NXxCXl5qdpq2', '1234558'),
	(7, 0, 'testing@testing.krj', b'0', 'T-Systems', '$2a$10$/7PuBSsfB2qT1Yd.w7KNiuKMV34npYX1NbxSDH435r5CjmdWOLQSm', '+36706793041'),
	(8, 0, 'jane@hobby.local', b'0', 'Jane', '$2a$10$BYHQ4V1ItjOIVW2bIpRfn.oFIwUAssL/EjxBH3do38.xXHX7l2pPS', '+36706793041'),
	(9, 0, 'johhny@hobby.local', b'0', 'johhny', '$2a$10$MwPK0ivRfKTfLKwRsAnFrOmfDSjuUJw5MpL0eALWJR.DrXtgrElzu', '1312312323'),
	(11, 0, 'joska@hobby.local', b'0', 'Test Jóska', '$2a$10$MtdnpLgHA/gp3i4kDMiwDOzqLr/qxgdlmcTLutLm5fmf8JLxEJ0Ny', '+1234567'),
	(12, 2, 'laszlobalint.mrm@gmail.com', b'1', 'László Bálint', '$2a$10$QDjqXx57xrjpHw1xu0Z1EO.1GEonpob9uoNH6ZN8T2MnJx2Xb4S3S', '+381637693041');
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;

-- Dumping structure for tábla transporter.transport
CREATE TABLE IF NOT EXISTS `transport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `departure_time` datetime(6) NOT NULL,
  `free_seats` int(11) NOT NULL,
  `route` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_hungarian_ci;

-- Dumping data for table transporter.transport: ~4 rows (approximately)
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` (`id`, `departure_time`, `free_seats`, `route`) VALUES
	(1, '2021-05-16 20:00:00.000000', 3, 'FROM_HUNGARY_TO_SERBIA'),
	(2, '2021-03-05 10:30:00.000000', 3, 'FROM_SERBIA_TO_HUNGARY'),
	(3, '2021-09-11 11:00:00.000000', 2, 'FROM_HUNGARY_TO_SERBIA'),
	(4, '2019-10-11 18:00:00.000000', 3, 'FROM_SERBIA_TO_HUNGARY');
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
