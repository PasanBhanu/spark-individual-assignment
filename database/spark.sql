-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 18, 2020 at 03:12 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spark`
--

-- --------------------------------------------------------

--
-- Table structure for table `beds`
--

CREATE TABLE `beds` (
  `id` bigint(20) NOT NULL,
  `hospital_id` bigint(20) NOT NULL,
  `serial_no` varchar(191) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `beds`
--

INSERT INTO `beds` (`id`, `hospital_id`, `serial_no`) VALUES
(41, 5, NULL),
(42, 5, NULL),
(43, 5, NULL),
(44, 5, NULL),
(45, 5, NULL),
(46, 5, NULL),
(47, 5, NULL),
(48, 5, NULL),
(49, 5, NULL),
(50, 5, NULL),
(51, 6, 'f1877484-bbb3-4df0-8842-44ed63c5ce43'),
(52, 6, 'ed7b38bf-44a2-4c84-9326-dc9dd62d09c8'),
(53, 6, NULL),
(54, 6, NULL),
(55, 6, NULL),
(56, 6, NULL),
(57, 6, NULL),
(58, 6, NULL),
(59, 6, NULL),
(60, 6, NULL),
(61, 7, NULL),
(62, 7, NULL),
(63, 7, NULL),
(64, 7, NULL),
(65, 7, NULL),
(66, 7, NULL),
(67, 7, NULL),
(68, 7, NULL),
(69, 7, NULL),
(70, 7, NULL),
(71, 8, 'eb55fa36-7b67-44ca-b85e-079c5fda813a'),
(72, 8, NULL),
(73, 8, NULL),
(74, 8, NULL),
(75, 8, NULL),
(76, 8, NULL),
(77, 8, NULL),
(78, 8, NULL),
(79, 8, NULL),
(80, 8, NULL),
(81, 9, NULL),
(82, 9, NULL),
(83, 9, NULL),
(84, 9, NULL),
(85, 9, NULL),
(86, 9, NULL),
(87, 9, NULL),
(88, 9, NULL),
(89, 9, NULL),
(90, 9, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `doctors`
--

CREATE TABLE `doctors` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `hospital_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `doctors`
--

INSERT INTO `doctors` (`id`, `user_id`, `hospital_id`) VALUES
(1, 2, 5),
(3, 16, 5);

-- --------------------------------------------------------

--
-- Table structure for table `hospitals`
--

CREATE TABLE `hospitals` (
  `id` bigint(20) NOT NULL,
  `name` varchar(191) NOT NULL,
  `user_id` bigint(20) NOT NULL COMMENT 'Director',
  `district` int(11) NOT NULL,
  `geolocation_x` int(11) NOT NULL,
  `geolocation_y` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hospitals`
--

INSERT INTO `hospitals` (`id`, `name`, `user_id`, `district`, `geolocation_x`, `geolocation_y`) VALUES
(5, 'District 5 Hospital', 2, 5, 450, 400),
(6, 'District 4 Hospital', 2, 4, 150, 450),
(7, 'District 3 Hospital', 2, 3, 150, 250),
(8, 'District 1 Hospital', 2, 1, 200, 100),
(9, 'District 2 Hospital', 2, 2, 500, 100);

-- --------------------------------------------------------

--
-- Table structure for table `patients`
--

CREATE TABLE `patients` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `serial_no` varchar(191) DEFAULT NULL,
  `geolocation_x` int(11) NOT NULL,
  `geolocation_y` int(11) NOT NULL,
  `contact_number` varchar(191) NOT NULL,
  `district` int(11) NOT NULL,
  `decease_level` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `register_date` date NOT NULL,
  `admission_date` date DEFAULT NULL,
  `discharged_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `patients`
--

INSERT INTO `patients` (`id`, `user_id`, `serial_no`, `geolocation_x`, `geolocation_y`, `contact_number`, `district`, `decease_level`, `status`, `register_date`, `admission_date`, `discharged_date`) VALUES
(7, 7, 'ed7b38bf-44a2-4c84-9326-dc9dd62d09c8', 456, 456, '0715445904', 1, 0, 1, '2020-10-15', '2020-10-15', NULL),
(8, 8, 'f1877484-bbb3-4df0-8842-44ed63c5ce43', 123, 123, '0111234567', 1, 1, 1, '2020-10-15', '2020-10-15', NULL),
(9, 14, 'eb55fa36-7b67-44ca-b85e-079c5fda813a', 100, 50, '0715454904', 1, 0, 1, '2020-10-15', '2020-10-15', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(191) NOT NULL,
  `email` varchar(191) NOT NULL,
  `password` varchar(191) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`) VALUES
(1, 'MOH\r\n', 'moh@spark.lk', 'cGFzc3dvcmQ=', 1),
(2, 'Doctor', 'doctor@moh.lk', 'cGFzc3dvcmQ=', 2),
(7, 'Patient 1', 'patient1@spark.lk', 'MTIz', 0),
(8, 'Patient 2', 'patient2@spark.lk', 'MTIz', 0),
(13, 'pasanbguruge@gmail.com', 'pasanbguruge@33gmail.com', 'MTIz', 1),
(14, 'Patient', 'patient@spark.lk', 'cGFzc3dvcmQ=', 0),
(16, 'Doctor 1', 'doctor1@spark.lk', 'cGFzcw==', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `beds`
--
ALTER TABLE `beds`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hospital_id` (`hospital_id`);

--
-- Indexes for table `doctors`
--
ALTER TABLE `doctors`
  ADD PRIMARY KEY (`id`),
  ADD KEY `hospital_id` (`hospital_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `hospitals`
--
ALTER TABLE `hospitals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `serial_no` (`serial_no`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `beds`
--
ALTER TABLE `beds`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;

--
-- AUTO_INCREMENT for table `doctors`
--
ALTER TABLE `doctors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `hospitals`
--
ALTER TABLE `hospitals`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `patients`
--
ALTER TABLE `patients`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `beds`
--
ALTER TABLE `beds`
  ADD CONSTRAINT `beds_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospitals` (`id`);

--
-- Constraints for table `doctors`
--
ALTER TABLE `doctors`
  ADD CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`hospital_id`) REFERENCES `hospitals` (`id`),
  ADD CONSTRAINT `doctors_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `hospitals`
--
ALTER TABLE `hospitals`
  ADD CONSTRAINT `hospitals_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `patients`
--
ALTER TABLE `patients`
  ADD CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
