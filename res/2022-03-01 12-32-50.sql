-- MySQL dump 10.13  Distrib 8.0.28, for Linux (x86_64)
--
-- Host: localhost    Database: cebdatabase
-- ------------------------------------------------------
-- Server version	8.0.28-0ubuntu0.20.04.3

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Temporary view structure for view `alumno_bol_califa_charge_view`
--

DROP TABLE IF EXISTS `alumno_bol_califa_charge_view`;
/*!50001 DROP VIEW IF EXISTS `alumno_bol_califa_charge_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumno_bol_califa_charge_view` AS SELECT 
 1 AS `numero_lista`,
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `calificacion_clave`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `periodo`,
 1 AS `orden`,
 1 AS `evaluacion`,
 1 AS `calificacion`,
 1 AS `faltas`,
 1 AS `tipo_calificacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumno_califa_charge_view`
--

DROP TABLE IF EXISTS `alumno_califa_charge_view`;
/*!50001 DROP VIEW IF EXISTS `alumno_califa_charge_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumno_califa_charge_view` AS SELECT 
 1 AS `numero_lista`,
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `calificacion_clave`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `orden`,
 1 AS `periodo`,
 1 AS `tipo_calificacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumno_num_califa_charge_view`
--

DROP TABLE IF EXISTS `alumno_num_califa_charge_view`;
/*!50001 DROP VIEW IF EXISTS `alumno_num_califa_charge_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumno_num_califa_charge_view` AS SELECT 
 1 AS `numero_lista`,
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `calificacion_clave`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `periodo`,
 1 AS `orden`,
 1 AS `evaluacion`,
 1 AS `calificacion`,
 1 AS `faltas`,
 1 AS `tipo_calificacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumno_sem_califa_charge_view`
--

DROP TABLE IF EXISTS `alumno_sem_califa_charge_view`;
/*!50001 DROP VIEW IF EXISTS `alumno_sem_califa_charge_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumno_sem_califa_charge_view` AS SELECT 
 1 AS `numero_lista`,
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `calificacion_clave`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `periodo`,
 1 AS `calificacion`,
 1 AS `orden`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos` (
  `numero_control` varchar(6) NOT NULL,
  `apellido_paterno` varchar(45) NOT NULL,
  `apellido_materno` varchar(45) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `semestre` int NOT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  `sexo` varchar(1) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `CURP` varchar(18) NOT NULL,
  `email` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`numero_control`),
  KEY `semestre` (`semestre`),
  KEY `sexo_idx` (`sexo`),
  KEY `alumnos_ibfk_2` (`grupo`),
  CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`semestre`) REFERENCES `semestres` (`semestre`),
  CONSTRAINT `gruposf` FOREIGN KEY (`grupo`) REFERENCES `grupos` (`grupo`),
  CONSTRAINT `vvsfvw` FOREIGN KEY (`sexo`) REFERENCES `sexos` (`sexo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` VALUES ('200004','BURCIAGA','RODRIGUEZ','YARELI',1,NULL,'F','2005-12-15','BURY051215MCHRDRA0',NULL);
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumnos_contacto`
--

DROP TABLE IF EXISTS `alumnos_contacto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos_contacto` (
  `numero_control` varchar(6) NOT NULL,
  `domicilio` varchar(45) NOT NULL,
  `colonia` varchar(45) NOT NULL,
  `localidad` varchar(45) NOT NULL,
  `num_telefonico` varchar(10) NOT NULL,
  PRIMARY KEY (`numero_control`),
  CONSTRAINT `alumnos_contacto_ibfk_1` FOREIGN KEY (`numero_control`) REFERENCES `alumnos` (`numero_control`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos_contacto`
--

LOCK TABLES `alumnos_contacto` WRITE;
/*!40000 ALTER TABLE `alumnos_contacto` DISABLE KEYS */;
/*!40000 ALTER TABLE `alumnos_contacto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `alumnos_contacto_view_specialized`
--

DROP TABLE IF EXISTS `alumnos_contacto_view_specialized`;
/*!50001 DROP VIEW IF EXISTS `alumnos_contacto_view_specialized`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumnos_contacto_view_specialized` AS SELECT 
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `fecha_nacimiento`,
 1 AS `CURP`,
 1 AS `domicilio`,
 1 AS `num_telefonico`,
 1 AS `nombres`,
 1 AS `ocupacion`,
 1 AS `telefono`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumnos_sin_pass`
--

DROP TABLE IF EXISTS `alumnos_sin_pass`;
/*!50001 DROP VIEW IF EXISTS `alumnos_sin_pass`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumnos_sin_pass` AS SELECT 
 1 AS `numero_control`,
 1 AS `email`,
 1 AS `nombres`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumnos_view`
--

DROP TABLE IF EXISTS `alumnos_view`;
/*!50001 DROP VIEW IF EXISTS `alumnos_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumnos_view` AS SELECT 
 1 AS `numero_control`,
 1 AS `nombres`,
 1 AS `apellido_paterno`,
 1 AS `apellido_materno`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `sexo`,
 1 AS `fecha_nacimiento`,
 1 AS `CURP`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `alumnos_visible_view`
--

DROP TABLE IF EXISTS `alumnos_visible_view`;
/*!50001 DROP VIEW IF EXISTS `alumnos_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `alumnos_visible_view` AS SELECT 
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `sexo`,
 1 AS `fecha_nacimiento`,
 1 AS `CURP`,
 1 AS `numero_lista`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignaturas` (
  `grupo` varchar(3) NOT NULL,
  `materia` varchar(20) NOT NULL,
  `profesor` varchar(6) NOT NULL,
  `aula` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`grupo`,`materia`),
  KEY `aula` (`aula`),
  KEY `materia` (`profesor`,`materia`),
  CONSTRAINT `asignaturas_ibfk_2` FOREIGN KEY (`profesor`, `materia`) REFERENCES `materia_profesor` (`profesor`, `materia`),
  CONSTRAINT `asignaturas_ibfk_3` FOREIGN KEY (`aula`) REFERENCES `aulas` (`aula`),
  CONSTRAINT `grupoFK` FOREIGN KEY (`grupo`) REFERENCES `grupos` (`grupo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `asignaturas_horario_view`
--

DROP TABLE IF EXISTS `asignaturas_horario_view`;
/*!50001 DROP VIEW IF EXISTS `asignaturas_horario_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `asignaturas_horario_view` AS SELECT 
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `grupo`,
 1 AS `profesor`,
 1 AS `nombre_completo`,
 1 AS `hora`,
 1 AS `hora_c`,
 1 AS `dia`,
 1 AS `aula`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `asignaturas_visible_view`
--

DROP TABLE IF EXISTS `asignaturas_visible_view`;
/*!50001 DROP VIEW IF EXISTS `asignaturas_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `asignaturas_visible_view` AS SELECT 
 1 AS `grupo`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `profesor`,
 1 AS `nombre_completo`,
 1 AS `aula`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `aulas`
--

DROP TABLE IF EXISTS `aulas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aulas` (
  `aula` varchar(5) NOT NULL,
  PRIMARY KEY (`aula`),
  UNIQUE KEY `aula` (`aula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aulas`
--

LOCK TABLES `aulas` WRITE;
/*!40000 ALTER TABLE `aulas` DISABLE KEYS */;
INSERT INTO `aulas` VALUES ('b0'),('b1'),('b2'),('b3');
/*!40000 ALTER TABLE `aulas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bajas`
--

DROP TABLE IF EXISTS `bajas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bajas` (
  `numero_control` varchar(40) NOT NULL,
  PRIMARY KEY (`numero_control`),
  CONSTRAINT `vc` FOREIGN KEY (`numero_control`) REFERENCES `alumnos` (`numero_control`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bajas`
--

LOCK TABLES `bajas` WRITE;
/*!40000 ALTER TABLE `bajas` DISABLE KEYS */;
/*!40000 ALTER TABLE `bajas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bajas_periodo`
--

DROP TABLE IF EXISTS `bajas_periodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bajas_periodo` (
  `numero_control` varchar(40) NOT NULL,
  PRIMARY KEY (`numero_control`),
  CONSTRAINT `df` FOREIGN KEY (`numero_control`) REFERENCES `alumnos` (`numero_control`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bajas_periodo`
--

LOCK TABLES `bajas_periodo` WRITE;
/*!40000 ALTER TABLE `bajas_periodo` DISABLE KEYS */;
/*!40000 ALTER TABLE `bajas_periodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `bajas_visible_view`
--

DROP TABLE IF EXISTS `bajas_visible_view`;
/*!50001 DROP VIEW IF EXISTS `bajas_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `bajas_visible_view` AS SELECT 
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `sexo`,
 1 AS `fecha_nacimiento`,
 1 AS `CURP`,
 1 AS `numero_lista`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `calificaciones`
--

DROP TABLE IF EXISTS `calificaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones` (
  `calificacion_clave` int NOT NULL AUTO_INCREMENT,
  `clave_alumno` varchar(30) NOT NULL,
  `materia` varchar(20) DEFAULT NULL,
  `semestre` int DEFAULT NULL,
  `periodo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`calificacion_clave`),
  UNIQUE KEY `cosas` (`periodo`,`materia`,`clave_alumno`) /*!80000 INVISIBLE */,
  KEY `clave_alumno` (`clave_alumno`),
  KEY `materia` (`materia`),
  KEY `semestre` (`semestre`),
  KEY `periodo_idx` (`periodo`),
  CONSTRAINT `calificaciones_ibfk_1` FOREIGN KEY (`clave_alumno`) REFERENCES `alumnos` (`numero_control`),
  CONSTRAINT `calificaciones_ibfk_2` FOREIGN KEY (`materia`) REFERENCES `materias` (`clave_materia`),
  CONSTRAINT `calificaciones_ibfk_3` FOREIGN KEY (`semestre`) REFERENCES `semestres` (`semestre`),
  CONSTRAINT `periodo` FOREIGN KEY (`periodo`) REFERENCES `periodos` (`periodo`)
) ENGINE=InnoDB AUTO_INCREMENT=379 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones`
--

LOCK TABLES `calificaciones` WRITE;
/*!40000 ALTER TABLE `calificaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `calificaciones_booleanas`
--

DROP TABLE IF EXISTS `calificaciones_booleanas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones_booleanas` (
  `calificacion_clave` int DEFAULT NULL,
  `evaluacion` varchar(20) DEFAULT NULL,
  `calificacion` tinyint(1) DEFAULT NULL,
  `faltas` varchar(2) DEFAULT NULL,
  UNIQUE KEY `unique_index` (`calificacion_clave`,`evaluacion`),
  KEY `calificacion_clave` (`calificacion_clave`),
  KEY `evaluacion` (`evaluacion`),
  CONSTRAINT `calificaciones_booleanas_ibfk_1` FOREIGN KEY (`calificacion_clave`) REFERENCES `calificaciones` (`calificacion_clave`),
  CONSTRAINT `calificaciones_booleanas_ibfk_2` FOREIGN KEY (`evaluacion`) REFERENCES `evaluaciones` (`evaluacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones_booleanas`
--

LOCK TABLES `calificaciones_booleanas` WRITE;
/*!40000 ALTER TABLE `calificaciones_booleanas` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificaciones_booleanas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `calificaciones_booleanas_view`
--

DROP TABLE IF EXISTS `calificaciones_booleanas_view`;
/*!50001 DROP VIEW IF EXISTS `calificaciones_booleanas_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `calificaciones_booleanas_view` AS SELECT 
 1 AS `calificacion_clave`,
 1 AS `clave_alumno`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `tipo_calificacion`,
 1 AS `semestre`,
 1 AS `periodo`,
 1 AS `calificacion`,
 1 AS `evaluacion`,
 1 AS `faltas`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `calificaciones_numericas`
--

DROP TABLE IF EXISTS `calificaciones_numericas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones_numericas` (
  `calificacion_clave` int DEFAULT NULL,
  `evaluacion` varchar(20) DEFAULT NULL,
  `calificacion` double DEFAULT NULL,
  `faltas` varchar(2) DEFAULT NULL,
  UNIQUE KEY `unique_index` (`calificacion_clave`,`evaluacion`),
  UNIQUE KEY `cosaa` (`evaluacion`,`calificacion_clave`),
  KEY `calificacion_clave` (`calificacion_clave`),
  KEY `evaluacion` (`evaluacion`),
  CONSTRAINT `calificaciones_numericas_ibfk_1` FOREIGN KEY (`calificacion_clave`) REFERENCES `calificaciones` (`calificacion_clave`),
  CONSTRAINT `calificaciones_numericas_ibfk_2` FOREIGN KEY (`evaluacion`) REFERENCES `evaluaciones` (`evaluacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones_numericas`
--

LOCK TABLES `calificaciones_numericas` WRITE;
/*!40000 ALTER TABLE `calificaciones_numericas` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificaciones_numericas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `calificaciones_numericas_view`
--

DROP TABLE IF EXISTS `calificaciones_numericas_view`;
/*!50001 DROP VIEW IF EXISTS `calificaciones_numericas_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `calificaciones_numericas_view` AS SELECT 
 1 AS `calificacion_clave`,
 1 AS `clave_alumno`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `semestre`,
 1 AS `periodo`,
 1 AS `calificacion`,
 1 AS `evaluacion`,
 1 AS `faltas`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `calificaciones_semestrales`
--

DROP TABLE IF EXISTS `calificaciones_semestrales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones_semestrales` (
  `calificacion_clave` int DEFAULT NULL,
  `calificacion` double DEFAULT NULL,
  UNIQUE KEY `calificacion_clave_UNIQUE` (`calificacion_clave`),
  KEY `calificacion_clave` (`calificacion_clave`),
  CONSTRAINT `tf` FOREIGN KEY (`calificacion_clave`) REFERENCES `calificaciones` (`calificacion_clave`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones_semestrales`
--

LOCK TABLES `calificaciones_semestrales` WRITE;
/*!40000 ALTER TABLE `calificaciones_semestrales` DISABLE KEYS */;
/*!40000 ALTER TABLE `calificaciones_semestrales` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `calificaciones_semestrales_view`
--

DROP TABLE IF EXISTS `calificaciones_semestrales_view`;
/*!50001 DROP VIEW IF EXISTS `calificaciones_semestrales_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `calificaciones_semestrales_view` AS SELECT 
 1 AS `calificacion_clave`,
 1 AS `clave_alumno`,
 1 AS `semestre`,
 1 AS `periodo`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `calificacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `calificaciones_tipos`
--

DROP TABLE IF EXISTS `calificaciones_tipos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `calificaciones_tipos` (
  `tipo` varchar(45) NOT NULL,
  PRIMARY KEY (`tipo`),
  UNIQUE KEY `calificaciones-tipos_UNIQUE` (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `calificaciones_tipos`
--

LOCK TABLES `calificaciones_tipos` WRITE;
/*!40000 ALTER TABLE `calificaciones_tipos` DISABLE KEYS */;
INSERT INTO `calificaciones_tipos` VALUES ('A/NA'),('Numérica');
/*!40000 ALTER TABLE `calificaciones_tipos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `calificaciones_view`
--

DROP TABLE IF EXISTS `calificaciones_view`;
/*!50001 DROP VIEW IF EXISTS `calificaciones_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `calificaciones_view` AS SELECT 
 1 AS `calificacion_clave`,
 1 AS `clave_alumno`,
 1 AS `orden`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `semestre`,
 1 AS `tipo_calificacion`,
 1 AS `periodo`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `currentperiodo`
--

DROP TABLE IF EXISTS `currentperiodo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `currentperiodo` (
  `periodo` varchar(40) NOT NULL,
  PRIMARY KEY (`periodo`),
  CONSTRAINT `period` FOREIGN KEY (`periodo`) REFERENCES `periodos` (`periodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currentperiodo`
--

LOCK TABLES `currentperiodo` WRITE;
/*!40000 ALTER TABLE `currentperiodo` DISABLE KEYS */;
INSERT INTO `currentperiodo` VALUES ('v');
/*!40000 ALTER TABLE `currentperiodo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dias_clase`
--

DROP TABLE IF EXISTS `dias_clase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dias_clase` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `dia` (`dia`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dias_clase`
--

LOCK TABLES `dias_clase` WRITE;
/*!40000 ALTER TABLE `dias_clase` DISABLE KEYS */;
INSERT INTO `dias_clase` VALUES (4,'JUEVES'),(1,'LUNES'),(2,'MARTES'),(3,'MIÉRCOLES'),(5,'VIERNES');
/*!40000 ALTER TABLE `dias_clase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `dias_clase_view`
--

DROP TABLE IF EXISTS `dias_clase_view`;
/*!50001 DROP VIEW IF EXISTS `dias_clase_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `dias_clase_view` AS SELECT 
 1 AS `dia`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `evaluaciones`
--

DROP TABLE IF EXISTS `evaluaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluaciones` (
  `evaluacion` varchar(30) NOT NULL,
  PRIMARY KEY (`evaluacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluaciones`
--

LOCK TABLES `evaluaciones` WRITE;
/*!40000 ALTER TABLE `evaluaciones` DISABLE KEYS */;
INSERT INTO `evaluaciones` VALUES ('1ra'),('2ra'),('3ra');
/*!40000 ALTER TABLE `evaluaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `grupo_asignaturas`
--

DROP TABLE IF EXISTS `grupo_asignaturas`;
/*!50001 DROP VIEW IF EXISTS `grupo_asignaturas`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `grupo_asignaturas` AS SELECT 
 1 AS `grupo`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `profesor`,
 1 AS `nombre_completo`,
 1 AS `aula`,
 1 AS `dia`,
 1 AS `hora`,
 1 AS `hora_c`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `grupos`
--

DROP TABLE IF EXISTS `grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `grupos` (
  `semestre` int DEFAULT NULL,
  `grupo` varchar(3) NOT NULL,
  `turno` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`grupo`),
  KEY `semestre_idx` (`semestre`),
  KEY `turno_idx` (`turno`),
  CONSTRAINT `fd` FOREIGN KEY (`turno`) REFERENCES `turnos` (`turno`),
  CONSTRAINT `semestre_a` FOREIGN KEY (`semestre`) REFERENCES `semestres` (`semestre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos`
--

LOCK TABLES `grupos` WRITE;
/*!40000 ALTER TABLE `grupos` DISABLE KEYS */;
INSERT INTO `grupos` VALUES (3,'301','M'),(3,'302','M'),(3,'303','M'),(3,'304','M'),(3,'305','M'),(3,'331','M'),(3,'332','M'),(3,'333','M'),(3,'334','M'),(3,'335','M'),(5,'501','M'),(5,'502','M'),(5,'503','M'),(5,'504','M'),(5,'505','M'),(5,'531','M'),(5,'532','M'),(5,'533','M'),(5,'534','M'),(5,'535','M');
/*!40000 ALTER TABLE `grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `grupos_visible_view`
--

DROP TABLE IF EXISTS `grupos_visible_view`;
/*!50001 DROP VIEW IF EXISTS `grupos_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `grupos_visible_view` AS SELECT 
 1 AS `grupo`,
 1 AS `semestre`,
 1 AS `turno`,
 1 AS `alumnos`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `horarios`
--

DROP TABLE IF EXISTS `horarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horarios` (
  `dia` varchar(10) DEFAULT NULL,
  `hora` varchar(2) DEFAULT NULL,
  `materia` varchar(45) DEFAULT NULL,
  `grupo` varchar(3) DEFAULT NULL,
  KEY `hora` (`hora`),
  KEY `horarios_ibfk_2` (`dia`),
  KEY `materiia_idx` (`materia`),
  KEY `grupo_idx` (`grupo`),
  CONSTRAINT `grupo` FOREIGN KEY (`grupo`) REFERENCES `grupos` (`grupo`),
  CONSTRAINT `materiia` FOREIGN KEY (`materia`) REFERENCES `materias` (`clave_materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horarios`
--

LOCK TABLES `horarios` WRITE;
/*!40000 ALTER TABLE `horarios` DISABLE KEYS */;
/*!40000 ALTER TABLE `horarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `horarios_view`
--

DROP TABLE IF EXISTS `horarios_view`;
/*!50001 DROP VIEW IF EXISTS `horarios_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `horarios_view` AS SELECT 
 1 AS `grupo`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `dia`,
 1 AS `hora`,
 1 AS `hora_c`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `horas_clase`
--

DROP TABLE IF EXISTS `horas_clase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horas_clase` (
  `orden` varchar(2) NOT NULL,
  `turno` varchar(15) DEFAULT NULL,
  `inicio` time DEFAULT NULL,
  `fin` time DEFAULT NULL,
  PRIMARY KEY (`orden`),
  UNIQUE KEY `hora` (`orden`),
  KEY `turno_idx` (`turno`),
  CONSTRAINT `turno` FOREIGN KEY (`turno`) REFERENCES `turnos` (`turno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horas_clase`
--

LOCK TABLES `horas_clase` WRITE;
/*!40000 ALTER TABLE `horas_clase` DISABLE KEYS */;
INSERT INTO `horas_clase` VALUES ('1','M','07:30:00','08:20:00'),('2','M','08:20:00','09:10:00'),('3','M','09:35:00','10:25:00'),('4','M','10:25:00','11:15:00'),('5','M','11:30:00','12:20:00'),('6','V','12:20:00','13:10:00');
/*!40000 ALTER TABLE `horas_clase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `horas_clase_visible_view`
--

DROP TABLE IF EXISTS `horas_clase_visible_view`;
/*!50001 DROP VIEW IF EXISTS `horas_clase_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `horas_clase_visible_view` AS SELECT 
 1 AS `orden`,
 1 AS `turno`,
 1 AS `inicio`,
 1 AS `fin`,
 1 AS `hora`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `materia_profesor`
--

DROP TABLE IF EXISTS `materia_profesor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materia_profesor` (
  `materia` varchar(20) NOT NULL,
  `profesor` varchar(6) NOT NULL,
  PRIMARY KEY (`materia`,`profesor`),
  KEY `profesor` (`profesor`),
  CONSTRAINT `materia_profesor_ibfk_1` FOREIGN KEY (`materia`) REFERENCES `materias` (`clave_materia`),
  CONSTRAINT `materia_profesor_ibfk_2` FOREIGN KEY (`profesor`) REFERENCES `profesores` (`clave_profesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materia_profesor`
--

LOCK TABLES `materia_profesor` WRITE;
/*!40000 ALTER TABLE `materia_profesor` DISABLE KEYS */;
INSERT INTO `materia_profesor` VALUES ('ÉT1','ABA'),('ÉT1','ACO'),('IN1','AGRC'),('IN1','AIFN'),('LA1','AJM'),('LA1','ALRC'),('OR3','ALRC'),('AOR','AMLB'),('TU3','AMMR'),('PA1','ARM'),('TUI','ARM'),('OR3','ARME'),('LR1','AXFE'),('PA3','AXFE'),('BI1','BLRC'),('LR1','CAMG'),('TUI','CAPB'),('BI1','CCS'),('HM1','CGG'),('PA3','CGG'),('LA3','CNL'),('MEI','CNL'),('LA3','DGC'),('PA1','DGC'),('LI1','ECSR'),('MA1','ECSR'),('LI1','ELTC'),('MA1','ELTC'),('HM1','EYEG'),('MEI','EYEG'),('TU3','EYEG'),('FI1','FJGR'),('ROP','FJGR'),('FI1','GMGA'),('ROP','GMS'),('MA3','JCCC'),('ORI','JCCC'),('ORI','JICC'),('QU1','JICC'),('MA3','JLAB'),('QU1','LALP');
/*!40000 ALTER TABLE `materia_profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `materia_profesor_view`
--

DROP TABLE IF EXISTS `materia_profesor_view`;
/*!50001 DROP VIEW IF EXISTS `materia_profesor_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `materia_profesor_view` AS SELECT 
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `profesor`,
 1 AS `nombre_completo`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `materias`
--

DROP TABLE IF EXISTS `materias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materias` (
  `clave_materia` varchar(20) NOT NULL,
  `nombre_materia` varchar(100) DEFAULT NULL,
  `nombre_abr` varchar(70) DEFAULT NULL,
  `tipo_calificacion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`clave_materia`),
  UNIQUE KEY `materia_UNIQUE` (`clave_materia`),
  KEY `tipo_calificacion` (`tipo_calificacion`),
  CONSTRAINT `materias_ibfk_1` FOREIGN KEY (`tipo_calificacion`) REFERENCES `calificaciones_tipos` (`tipo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `materias`
--

LOCK TABLES `materias` WRITE;
/*!40000 ALTER TABLE `materias` DISABLE KEYS */;
INSERT INTO `materias` VALUES ('AEI','APLICACIONES ESPECIFICAS CON PROGRAMAS INTEGRADOS','APLIC.ESP C/P INT.',NULL),('AG1','APLICACIONES GRAFICAS CON PROGRAMAS INTEGRADOS I','APLIC. GRA P. INT 1',NULL),('AI','APRENDE A INTERVENIR','Aprende a Intervenir',NULL),('AOR','ADMINISTRACION DE ORGANIZACIONES','Ad. de Organización','Numérica'),('APP','APLICA LOS PRINCIPIOS DE EPIDEMIOLOGIA DE ENFERMEDADES','Apl. Pcios. Epidem.',NULL),('APS','APLICA EL SANEAMIENTO EN LA PREVENCION DE ENFERMEDADES','Ap. Saneamiento Bás.',NULL),('ASE','ANALIZA EL SISTEMA EDUCATIVO NACIONAL','Analiza Sis Educ Nac',NULL),('ASP','ANALIZA SISTEMA DE POLIZAS','Anal. Sist. Polizas','Numérica'),('ATC','APLICAR LA TEORIA DE COLOR EN REPRESENTACIONES GRAFICAS','Apl.Teoria del Color',NULL),('BAF','BASES ANATOMICAS Y FISIOLOGICAS Y EL PROCESO SALUD-ENFERMEDAD','B Anatómicas Fisiol.',NULL),('BD1','BASE DE DATOS I','BASE DE DATOS I',NULL),('BD2','BASE DE DATOS II','BASE DE DATOS II',NULL),('BI1','BIOLOGIA I','Biología I','Numérica'),('BI2','BIOLOGIA II','Biología II',NULL),('CAD','CALCULO DIFERENCIAL','Cálculo Diferencial',NULL),('CAI','CALCULO INTEGRAL','Cálculo Integral',NULL),('CC','ANALIZA EL ESTADO DE ORIGEN Y EL CAPITAL CONTABLE','Edo Capital Contable','Numérica'),('CC1','CIENCIAS DE LA COMUNICACION I','C.de la Cominucacion',NULL),('CDC','CONTABILIDAD DE COSTOS','Contabilidad Costos',NULL),('CGR','COMUNICACIÓN GRAFICA','Comunicación Grafica',NULL),('CII','CIENCIAS DE LA COMUNICACIÓN II','C. Comunicación II',NULL),('CIS','CIENCIAS DE LA SALUD II','Cs. de la Salud II',NULL),('CN','CALCULO DE NOMINA','Cálculo de Nómina',NULL),('CO1','CONTABILIDAD I','Contabilidad I',NULL),('CO2','CONTABILIDAD II','Contabilidad II',NULL),('CPG','CUIDADO DEL PACIENTE GERIATRICO','Cuid.Pac. Geriátrico',NULL),('CSI','CIENCIAS DE LA SALUD I','Ciencias Salud I',NULL),('CV','COMUNIDADES VIRTUALES','Comuni. Virtuales',NULL),('DB','DESARROLLAR BOCETOS','Desarr.Bocetos',NULL),('DCE','DESARROLLO Y CARACTERISTICAS DE DOCUMENTOS ELECTRONICOS','Des. Carac. Doc. Ele',NULL),('DDI','DISEŃO DIGITAL','Diseńo Digital',NULL),('DDP','DISEŃAR DISPLAY E ILUSTRACIONES PARA DUMMIES','D.Display e Ilst.',NULL),('DE2','DERECHO II','Derecho II',NULL),('DEE','DESARROLLO EMPRESARIAL','Des. Empresarial',NULL),('DEF','DETECTA FAC. DE RIESG. EN LA SALUD MATERNO INFANTIL','Det.Fac.r_sal. Mat I',NULL),('DEI','DERECHO I','Derecho I',NULL),('DFO','DISEŃAR FORMAS Y FIGURAS','Dis.Formas y Fig.',NULL),('DI1','DIBUJO I','Dibujo I',NULL),('DIA','DISEŃO DE UNA PROPUESTA DE INTERVENCION EN EL AULA','Diseńo Prop. Int. Au',NULL),('DIE','DISEŃO DE UNA PROPUESTA DE INTERVENCION ESCOLAR','Diseńo Prop. Int. Es',NULL),('DII','DIBUJO II','Dibujo II',NULL),('DMA','DIBUJAR MODELOS AL NATURAL','Dibujar Modelos',NULL),('DPR','DISEŃAR PROYECTOS DE DIFUSIÓN MASIVA','Dis. Proyectos',NULL),('DPS','DISEŃO DE PRODUCTO SUSTENTABLE','Dis.Prod Sustentable',NULL),('E2','ÉTICA II','Ética II',NULL),('EC1','ECONOMIA I','Economía I',NULL),('EC2','ECONOMIA II','Economía II',NULL),('ECI','REGISTAR OPERACIONES ESPECIALES Y CALCULAR EL INTERES','Op.Esp y Cal.Interes',NULL),('EDP','ELABORAR DOCUMENTOS EN PROCESADORES DE TEXTO','ELAB.DOC.PROC. TEXTO',NULL),('ELN','ELABORAR NOMINAS','Elaborar Nóminas',NULL),('ELS','ELABORAR LIQUIDACIONES DE SEGURO SOCIAL','Elab.Liq.seg. Soc.',NULL),('EMA','ECOLOGIA Y MEDIO AMBIENTE','Eco. y Med. Ambiente',NULL),('EPI','EPIDEMIOLOGIA','Epidemiología',NULL),('EPW','ELABORACION DE PAGINAS WEB','Elab. de Pag. Web',NULL),('ERF','ELABORAR ESTADOS FINANCIEROS','Elab.Edos. Financ.',NULL),('ESC','REALIZAR ESTUDIOS DE COMUNIDAD','Real. Est. Comunidad',NULL),('ESM','ESTRUCTURA SOCIOECONOMICA DE MEXICO','Esem',NULL),('ÉT1','ÉTICA I','Ética I','A/NA'),('ÉT2','ÉTICA II','Ética II',NULL),('EV1','ETICA Y VALORES I','Etica y Valores I',NULL),('EV2','ETICA Y VALORES II','Etica y Valores II',NULL),('FBD','FUNDAMENTOS BASICOS DEL DISEŃO','Fun. Básicos Diseńo','Numérica'),('FCC','ESTRUCTURAR FUNCIONES COMPLEJAS DEL CUERPO HUMANO','Fun. Complejas C.H.',NULL),('FCH','CONOCER ESTRUCTURAS Y FUNCIONES BASICAS DEL CUERPO HUMANO','Funciones Basicas CH',NULL),('FI1','FISICA I','Física I','Numérica'),('FI2','FISICA II','Física II',NULL),('FI3','FISICA III','Física III',NULL),('FIL','FILOSOFIA','Filosofía',NULL),('FSO','DIFERENCIAR LAS FUNCIONES DEL SISTEMA OPERATIVO','Funciones Sist. Op.',NULL),('GAT','GESTION DE ARCHIVOS DE TEXTO','Gstión de A. Texto','Numérica'),('GEO','GEOGRAFIA','Geografía',NULL),('GPE','GESTIONAR PROCESOS EMPRESARIALES','Gest. Proce. Empresa',NULL),('GTF','GESTIONAR TRAMITES FISCALES Y ADMINISTRATIVOS','G Tram. Fis. y Adm.',NULL),('H4','DETECTA F. DE R. EN LA SALUD MATERNO INFANTIL','Det.Fact salud infan',NULL),('HCA','HOJAS DE CALCULO APLICADO','Hojas Cál. Aplicado','Numérica'),('HCO','OPERAR HOJAS DE CALCULO','Op. Hojas Cálculo',NULL),('HM1','HISTORIA DE MEXICO I','Hist. de México I','Numérica'),('HM2','HISTORIA DE MEXICO II','Hist. de México II',NULL),('HUM','HISTORIA UNIVERSAL CONTEMPORANEA','Hist. Univ. Con.',NULL),('IA','INTERVENCION EN EL AULA','Intervención Aula',NULL),('ICS','INTRODUCCION A LAS CIENCIAS SOCIALES','Int. Cs. Sociales',NULL),('IE','INTERVENCION ESCOLAR','Intervención Esc.',NULL),('IEP','REALIZAR INTERVENCION EDUCATIVA EN PREESCOLAR','Int. Educ.Preescolar',NULL),('IES','INTERVENCION EDUCATIVA EN LA SECUNDARIA','Int. Educ.Secundaria',NULL),('IG2','INGLES II','Ingles II',NULL),('IM1','IMPUESTOS I','Impuestos I',NULL),('IM2','IMPUESTOS II','Impuestos II',NULL),('IN1','INFORMATICA I','Informática I','A/NA'),('IN2','INFORMATICA II','Informática II',NULL),('INP','INTERVENCION EDUCATIVA EN LA PRIMARIA','Int. Educ. Primaria',NULL),('INR','INTRODUCCION A LAS REDES','INT. A REDES',NULL),('INS','INTERVENCION EDUCATIVA EN SECUNDARIA','Int. Educ.Secundaria',NULL),('LA1','INGLES I','Ingles I','Numérica'),('LA2','LENGUA ADICIONAL AL ESPAŃOL II','Ingles II',NULL),('LA3','INGLES III','Ingles III','Numérica'),('LA4','INGLES IV','Ingles IV',NULL),('LCP','LOGICA COMPUTACIONAL Y PROGRAMACION','LOGICA COMP. PROG.',NULL),('LI1','LITERATURA I','Literatura I','Numérica'),('LI2','LITERATURA II','Literatura II',NULL),('LR1','TALLER DE LECTURA Y REDACCION I','T. Lect. y Red. I','Numérica'),('LR2','TALLER DE LECTURA Y REDACCION II','T. Lect. y Red. II',NULL),('MA1','MATEMATICAS I','Matemáticas I','Numérica'),('Ma2','Mataenaticas II','Mataenaticas II','Numérica'),('MA3','MATEMATICAS III','Matemáticas III','Numérica'),('MA4','MATEMATICAS IV','Matemáticas IV','Numérica'),('MEI','METODOLOGIA DE LA INVESTIGACION','Met. de la Inv.','Numérica'),('MRC','MANTENIMIENTO Y REDES DE COMPUTO','Manten. Redes Comp.',NULL),('MSU','INTERVENCION EDUCATIVA EN EL NIVEL MEDIO SUPERIOR','In.Ed.Medio Superior',NULL),('NIE','NOCIONES DE LA INTERVENCION EDUCATIVA','Nociones Int. Educ.',NULL),('NUT','NUTRICION','Nutrición',NULL),('OCC','REGISTRAR OPERACIONES DE CREDITO Y COBRANZA','Reg.Op.Credito y Cob',NULL),('OE1','ORIENTACION EDUCATIVA I','Orient. Educ. I',NULL),('OE2','ORIENTACION EDUCATIVA II','Orient. Educ. II',NULL),('OEC','OPERACION DEL EQUIPO DE COMPUTO','Oper.Eq. de Computo',NULL),('OHC','OPERAR LAS HERRAMIENTAS DE COMPUTO','Operar Herram. Com.',NULL),('OR2','ORIENTACION','Orientación II',NULL),('OR3','ORIENTACION III','Orientación III','A/NA'),('OR4','ORIENTACION IV','Orientación IV',NULL),('OR5','ORIENTACION V','Orientación V',NULL),('OR6','ORIENTACION VI','Orientación VI',NULL),('ORI','ORIENTACIÓN I','Orientación I','A/NA'),('P5','ACTIVIDADES PARAESCOLARES V','Paraescolares V',NULL),('PA1','PARAESCOLARES I','Paraescolares I','Numérica'),('PA2','PARAESCOLARES II','Paraescolares II',NULL),('PA3','PARAESCOLARES III','Paraescolares III','A/NA'),('PA4','PARAESCOLARES IV','Paraescolares IV',NULL),('PA5','PARAESCOLARES V','Paraescolares V',NULL),('PA6','PARAESCOLARES VI','Paraescolares VI',NULL),('PAM','PRODUCCION DE ANIMACIONES CON ELEMENTOS MULTIMEDIA','Prod.Animac.Ele.Mult',NULL),('PDM','PRODUCTOS DE DIFUSION MASIVA','Prod. de dif.masiva',NULL),('PEC','PRESERVAR EL EQUIPO DE COMPUTO','Preservar Eq.Computo',NULL),('PEI','PROGRAMAS DE EDICIÓN DE IMAGENES','Prog.Edición Grafica',NULL),('PG2','PSICOLOGIA II','PSICOLOGIA II',NULL),('PGG','UTILIZAR PROG. PARA ELABORAR PROP. GRAFICAS','Ut.Prog.P.Graficas',NULL),('PRE','INTERVENCION EDUCATIVA EN PREESCOLAR','Int. Ed. Preescolar',NULL),('PRO','PROGRAMACION','Programación',NULL),('PS1','PSICOLOGIA I','Psicologia I',NULL),('PS2','PSICOLOGIA II','Psicología II',NULL),('PSI','PSICOLOGIA I','Psicología I',NULL),('PSP','PARTICIPAR EN LA COMUNIDAD CON PROGRAMAS DE SALUD PUBLICA','Prog. de Salud Púb.',NULL),('PV','PROGRAMAS VECTORIALES','Programas Vectorial','Numérica'),('PWE','PAGINAS WEB','Páginas Web',NULL),('QU1','QUIMICA I','Química I','Numérica'),('QU2','QUIMICA II','Quimica II',NULL),('QU3','QUIMICA III','Química III',NULL),('RBO','REALIZAR BOCETOS UTILIZANDO DIBUJO NATURAL','Real. Bocetos',NULL),('RCC','REGISTRAR OPERACIONES DE CREDITO Y COBRANZA','Reg. Op. Credito Cob',NULL),('RDM','REGISTRO DE MERCANCIAS','Reg. de Mercancias',NULL),('RDR','REVISAR DOCUMENTACION PARA EL REGISTRO CONTABLE','R. Doctos. Reg. Con.',NULL),('REC','REALIZAR ESTUDIOS DE COMUNIDAD','Real. Estudios Com.',NULL),('RID','RESGUARDAR INF.Y ELAB. DOCTOS. ELECT.C/SOFTWARE DE APLICACION','Res.Inf.Doctos.Elec.',NULL),('RIN','APLICAR METODOLOGIA PARA REALIZAR LA INTERVENCION','Met.P/Real.Int. Ed.',NULL),('ROC','REGISTRAR CONTABLEMENTE OPERACIONES COMERCIALES','R. Conta/e. Op. Com.',NULL),('ROE','REALIZAR OPERACIONES EN FORMA ELECTRONICA','Real. Op. Forma Elec',NULL),('ROF','REALIZAR OPERACIONES EN FORMA ELECTRONICA','Rea.Op.F/Electronica',NULL),('ROP','REGISTRO DE OPERACIONES CONTABLES','Reg. Op. Contables','Numérica'),('RRC','REALIZAR REGISTRO CONTABLE','Realizar Reg. Conta',NULL),('RVN','REALIZA VALORACIONES NUTRICIONALES','Real.Val.Nutricional',NULL),('SEN','SISTEMA EDUCATIVO NACIONAL','Sistema Ed. Nacional',NULL),('SF1','TEMAS SELECTOS DE FISICA I','T. Selec. Física I',NULL),('SF2','TEMAS SELECTOS DE FISICA II','T. Selec. Física II',NULL),('SIN','SISTEMAS DE INFORMACION','Sis. de Información',NULL),('SP','SALUD PUBLICA','Salud Pública',NULL),('SQ1','TEMAS SELECTOS DE QUIMICA I','T. Selec. Química I',NULL),('SQ2','TEMAS SELECTOS DE QUIMICA II','T. Selec. Química II',NULL),('SSR','SALUD SEXUAL Y REPRODUCTIVA','Salud sexual y rep.',NULL),('TB2','TEMAS SELECTOS DE BIOLOGIA II','T. Sel. Biología II',NULL),('TC','TEORIA DEL COLOR','Teoria del Color',NULL),('TC1','TECNICAS CLINICAS I','Técnicas clínicas I',NULL),('TCL','TECNICAS CLINICAS II','Técnicas Clínicas II',NULL),('TCO','TEORIA DEL COLOR','Teoria del Color',NULL),('TIP','TIPOGRAFIA Y PRODUCTOS EDITORIALES','Tipografia y prod.ed',NULL),('TPE','TIPOGRAFIA Y PRODUCTOS EDITORIALES','Tipografia y Prod.Ed',NULL),('TSB','TEMAS SELECTOS DE BIOLOGIA I','T. Sel. Biología I',NULL),('TU2','TUTORIA II','Tutoría II',NULL),('TU3','TUTORIA III','Tutoría III','Numérica'),('TU4','TUTORIA IV','Tutoría IV',NULL),('TU5','TUTORIA V','Tutoría V',NULL),('TU6','TUTORIA VI','Tutoría VI',NULL),('TUI','TUTORIA I','Tutoría I','A/NA'),('UR','UTILIZAR RETORICA EN PROYECTO TIPOGRAFICO','Utilizar Retorica',NULL),('USD','UTILIZAR EL SOFTWARE DE DISEŃO','Util.Software Diseńo',NULL),('W83','ELABORAR HOJAS DE CALCULO','ELAB. H. DE CALCULO',NULL),('W84','ELABORAR PRESENTACIONES GRAFICAS','ELAB. P. GRAFICAS',NULL);
/*!40000 ALTER TABLE `materias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `materias_view`
--

DROP TABLE IF EXISTS `materias_view`;
/*!50001 DROP VIEW IF EXISTS `materias_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `materias_view` AS SELECT 
 1 AS `clave_materia`,
 1 AS `nombre_materia`,
 1 AS `nombre_abr`,
 1 AS `tipo_calificacion`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `periodos`
--

DROP TABLE IF EXISTS `periodos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `periodos` (
  `periodo` varchar(40) NOT NULL,
  `paridad` tinyint DEFAULT NULL,
  PRIMARY KEY (`periodo`),
  UNIQUE KEY `periodo_UNIQUE` (`periodo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `periodos`
--

LOCK TABLES `periodos` WRITE;
/*!40000 ALTER TABLE `periodos` DISABLE KEYS */;
INSERT INTO `periodos` VALUES ('Ago21-Dic21',1),('v',0);
/*!40000 ALTER TABLE `periodos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan_grupo`
--

DROP TABLE IF EXISTS `plan_grupo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plan_grupo` (
  `grupo` varchar(3) NOT NULL,
  `plan` varchar(20) NOT NULL,
  PRIMARY KEY (`grupo`,`plan`),
  UNIQUE KEY `grupo_UNIQUE` (`grupo`),
  KEY `plan` (`plan`),
  CONSTRAINT `plan_grupo_ibfk_1` FOREIGN KEY (`grupo`) REFERENCES `grupos` (`grupo`),
  CONSTRAINT `plan_grupo_ibfk_2` FOREIGN KEY (`plan`) REFERENCES `planes_estudio` (`clave_plan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan_grupo`
--

LOCK TABLES `plan_grupo` WRITE;
/*!40000 ALTER TABLE `plan_grupo` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_grupo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `plan_grupo_view`
--

DROP TABLE IF EXISTS `plan_grupo_view`;
/*!50001 DROP VIEW IF EXISTS `plan_grupo_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `plan_grupo_view` AS SELECT 
 1 AS `plan`,
 1 AS `descripcion`,
 1 AS `grupo`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `planes_estudio`
--

DROP TABLE IF EXISTS `planes_estudio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planes_estudio` (
  `clave_plan` varchar(20) NOT NULL,
  `descripcion` varchar(45) NOT NULL,
  `semestre` int DEFAULT NULL,
  PRIMARY KEY (`clave_plan`),
  KEY `semestre_idx` (`semestre`),
  CONSTRAINT `semestre` FOREIGN KEY (`semestre`) REFERENCES `semestres` (`semestre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planes_estudio`
--

LOCK TABLES `planes_estudio` WRITE;
/*!40000 ALTER TABLE `planes_estudio` DISABLE KEYS */;
INSERT INTO `planes_estudio` VALUES ('3A','AUXILIAR EDUCATIVO 2018',NULL),('3C','CONTABILIDAD 2018',NULL),('3D','DISEŃO 2018',NULL),('3H','HIGIENE Y SALUD 2018',NULL),('3I','INFORMATICA 2018',3),('4D','DISEŃO GRAFICO 2018',NULL),('4H','HIGIENE Y SALUD COMUNITARIA',NULL),('4I','TECNOLOGIAS DE LA INFORMACION Y LA COMUN',NULL),('5D','DISEŃO GRAFICO 2019',NULL),('5H','HIGIENE Y SALUD COMUNITARIA 5 SEM \'19',NULL),('5I','TECNOLOGIAS DE LA EDUCACION Y COM 5',NULL),('64','DISEŃO NP',NULL),('6H','HIGIENE Y SALUD COMUNITARIA PN',NULL),('6I','INFORMATICA PN',NULL),('A3','AUXILIAR EDUCATIVO TERCER SEMESTRE',NULL),('A4','AUXILIAR EDUCATIVO 4 SEMESTRE',NULL),('A5','AUXILIAR EDUCATIVO QUINTO SEMESTRE',NULL),('A6','AUXILIAR EDUCATIVO SEXTO SEMESTRE',NULL),('C4','CONTABILIDAD 4 SEM PN',NULL),('C5','CONTABILIDAD QUINTO SEM. \'19',NULL),('C6','CONTABILIDAD NP',NULL),('D4','DISEŃO 4',NULL),('D5','DISEŃO 5',NULL),('D6','DISEŃO 6',NULL),('DI','DISEŃO',NULL),('E4','INTERVENCION EN LA EDUCACION OBLIGATORIA',NULL),('E5','INTERV. EDUC. OBLIGATORIA 5 SEM \'19',NULL),('E6','INTERVENCION EDUCATIVA PN',NULL),('H3','HIGIENE Y SALUD TERCER SEMESTRE',NULL),('H4','HIGIENE Y SALUD 4TO. SEM.',NULL),('H5','HIGIENE Y SALUD QUINTO SEMESTRE',NULL),('H6','HIGIENE Y SALUD 6TO. SEM.',NULL),('I3','INFORMATICA  TERCER SEMESTRE',NULL),('I4','INFORMATICA 4 SEMESTRE',NULL),('I5','INFORMATICA QUINTO SEMESTRE',NULL),('I6','INFORMATICA 6TO. SEMESTRE',NULL),('PS','PRIMER SEMESTRE 2017',NULL),('S1','Primer',1),('S2','SEGUNDO SEMESTRE',NULL),('SS','SEGUNDO SEMESTRE 2017 PN',NULL),('V3','CONTABILIDAD TERCER SEMESTRE',NULL),('V4','CONTABILIDAD 4 SEMESTRE',NULL),('V5','CONTABILIDAD QUINTO SEMESTRE',NULL),('V6','CONTABILIDAD PARA 2DOS. Y 3ROS. AŃOS',NULL);
/*!40000 ALTER TABLE `planes_estudio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planes_estudio_materias`
--

DROP TABLE IF EXISTS `planes_estudio_materias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `planes_estudio_materias` (
  `materia` varchar(45) NOT NULL,
  `clave_plan` varchar(45) NOT NULL,
  `orden` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`materia`,`clave_plan`),
  KEY `materia_idx` (`materia`),
  KEY `clave_plan_idx` (`clave_plan`),
  CONSTRAINT `clave_plan` FOREIGN KEY (`clave_plan`) REFERENCES `planes_estudio` (`clave_plan`),
  CONSTRAINT `materia` FOREIGN KEY (`materia`) REFERENCES `materias` (`clave_materia`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planes_estudio_materias`
--

LOCK TABLES `planes_estudio_materias` WRITE;
/*!40000 ALTER TABLE `planes_estudio_materias` DISABLE KEYS */;
INSERT INTO `planes_estudio_materias` VALUES ('AI','A3',NULL),('AOR','3C',NULL),('APP','H6',NULL),('APS','H6',NULL),('ASE','A3',NULL),('ASP','V4',NULL),('ATC','D5',NULL),('BAF','3H',NULL),('BI1','3A',NULL),('BI1','3C',NULL),('BI1','3D',NULL),('BI1','3H',NULL),('BI1','3I',NULL),('BI1','A3',NULL),('BI1','DI',NULL),('BI1','H3',NULL),('BI1','I3',NULL),('BI1','V3',NULL),('BI2','4D',NULL),('BI2','4H',NULL),('BI2','4I',NULL),('BI2','A4',NULL),('BI2','C4',NULL),('BI2','D4',NULL),('BI2','E4',NULL),('BI2','H4',NULL),('BI2','I4',NULL),('BI2','V4',NULL),('CAD','5D',NULL),('CAD','5H',NULL),('CAD','5I',NULL),('CAD','C5',NULL),('CAD','D5',NULL),('CAD','H5',NULL),('CAD','I5',NULL),('CAD','V5',NULL),('CAI','64',NULL),('CAI','6H',NULL),('CAI','6I',NULL),('CAI','C6',NULL),('CAI','D6',NULL),('CAI','H6',NULL),('CAI','I6',NULL),('CAI','V6',NULL),('CC','V5',NULL),('CC1','5D',NULL),('CC1','D5',NULL),('CDC','C4',NULL),('CGR','5D',NULL),('CII','64',NULL),('CII','D6',NULL),('CIS','6H',NULL),('CIS','H6',NULL),('CN','C5',NULL),('CO1','5I',NULL),('CO1','I5',NULL),('CO2','6I',NULL),('CO2','I6',NULL),('CPG','6H',NULL),('CSI','5H',NULL),('CSI','H5',NULL),('CV','4I',NULL),('DB','DI',NULL),('DCE','I4',NULL),('DDI','6I',NULL),('DDP','D6',NULL),('DE2','C6',NULL),('DE2','V6',NULL),('DEE','C6',NULL),('DEF','H4',NULL),('DEI','C5',NULL),('DEI','V5',NULL),('DFO','D4',NULL),('DI1','5D',NULL),('DI1','D5',NULL),('DIA','A4',NULL),('DIE','A4',NULL),('DII','64',NULL),('DII','D6',NULL),('DMA','D4',NULL),('DPR','D6',NULL),('DPS','64',NULL),('EC1','5I',NULL),('EC1','A5',NULL),('EC1','C5',NULL),('EC1','E5',NULL),('EC1','I5',NULL),('EC1','V5',NULL),('EC2','6I',NULL),('EC2','A6',NULL),('EC2','C6',NULL),('EC2','E6',NULL),('EC2','I6',NULL),('EC2','V6',NULL),('ECI','V5',NULL),('EMA','64',NULL),('EMA','6H',NULL),('EMA','6I',NULL),('EMA','A6',NULL),('EMA','C6',NULL),('EMA','D6',NULL),('EMA','E6',NULL),('EMA','H6',NULL),('EMA','I6',NULL),('EMA','V6',NULL),('EPI','3H',NULL),('EPW','I6',NULL),('ERF','V4',NULL),('ESC','H5',NULL),('ESM','5D',NULL),('ESM','5H',NULL),('ESM','5I',NULL),('ESM','A4',NULL),('ESM','C5',NULL),('ESM','D4',NULL),('ESM','E5',NULL),('ESM','H4',NULL),('ESM','I4',NULL),('ESM','V4',NULL),('ÉT1','PS',NULL),('ÉT1','S1',NULL),('ÉT2','SS',NULL),('EV2','S2',NULL),('FBD','3D','1'),('FCC','H3',NULL),('FCH','H3',NULL),('FI1','3A',NULL),('FI1','3C',NULL),('FI1','3D',NULL),('FI1','3H',NULL),('FI1','3I','1'),('FI1','A3',NULL),('FI1','DI',NULL),('FI1','H3',NULL),('FI1','I3',NULL),('FI1','V3',NULL),('FI2','4D',NULL),('FI2','4H',NULL),('FI2','4I',NULL),('FI2','A4',NULL),('FI2','C4',NULL),('FI2','D4',NULL),('FI2','E4',NULL),('FI2','H4',NULL),('FI2','I4',NULL),('FI2','V4',NULL),('FIL','64',NULL),('FIL','6H',NULL),('FIL','6I',NULL),('FIL','A6',NULL),('FIL','C6',NULL),('FIL','D6',NULL),('FIL','E6',NULL),('FIL','H6',NULL),('FIL','I6',NULL),('FIL','V6',NULL),('FSO','I3',NULL),('GAT','3I',NULL),('GEO','5D',NULL),('GEO','5H',NULL),('GEO','5I',NULL),('GEO','A5',NULL),('GEO','C5',NULL),('GEO','D5',NULL),('GEO','E5',NULL),('GEO','H5',NULL),('GEO','I5',NULL),('GEO','V5',NULL),('GPE','V3',NULL),('HCA','3I',NULL),('HCO','I5',NULL),('HM1','3A',NULL),('HM1','3C',NULL),('HM1','3D','2'),('HM1','3H',NULL),('HM1','3I','2'),('HM1','S2',NULL),('HM2','4D',NULL),('HM2','4H',NULL),('HM2','4I',NULL),('HM2','A3',NULL),('HM2','C4',NULL),('HM2','DI',NULL),('HM2','E4',NULL),('HM2','H3',NULL),('HM2','I3',NULL),('HM2','V3',NULL),('HUM','64',NULL),('HUM','6H',NULL),('HUM','6I',NULL),('HUM','A5',NULL),('HUM','C6',NULL),('HUM','D5',NULL),('HUM','E6',NULL),('HUM','H5',NULL),('HUM','I5',NULL),('HUM','V5',NULL),('IA','E4',NULL),('ICS','SS',NULL),('IE','E4',NULL),('IEP','A5',NULL),('IES','A6',NULL),('IG2','SS',NULL),('IM1','C5',NULL),('IM2','C6',NULL),('IN1','PS',NULL),('IN1','S1',NULL),('IN2','S2',NULL),('IN2','SS',NULL),('INP','A6',NULL),('INP','E5',NULL),('INS','E6',NULL),('LA1','PS',NULL),('LA1','S1',NULL),('LA2','S2',NULL),('LA3','3A',NULL),('LA3','3C',NULL),('LA3','3D','3'),('LA3','3H',NULL),('LA3','3I',NULL),('LA3','A3',NULL),('LA3','DI',NULL),('LA3','H3',NULL),('LA3','I3',NULL),('LA3','V3',NULL),('LA4','4D',NULL),('LA4','4H',NULL),('LA4','4I',NULL),('LA4','A4',NULL),('LA4','C4',NULL),('LA4','D4',NULL),('LA4','E4',NULL),('LA4','H4',NULL),('LA4','I4',NULL),('LA4','V4',NULL),('LI1','3A',NULL),('LI1','3C',NULL),('LI1','3D',NULL),('LI1','3H',NULL),('LI1','3I',NULL),('LI1','A3',NULL),('LI1','DI',NULL),('LI1','H3',NULL),('LI1','I3',NULL),('LI1','V3',NULL),('LI2','4D',NULL),('LI2','4H',NULL),('LI2','4I',NULL),('LI2','A4',NULL),('LI2','C4',NULL),('LI2','D4',NULL),('LI2','E4',NULL),('LI2','H4',NULL),('LI2','I4',NULL),('LI2','V4',NULL),('LR1','PS',NULL),('LR1','S1',NULL),('LR2','S2',NULL),('LR2','SS',NULL),('MA1','PS',NULL),('MA1','S1',NULL),('MA2','S2',NULL),('MA2','SS',NULL),('MA3','3A',NULL),('MA3','3C',NULL),('MA3','3D',NULL),('MA3','3H',NULL),('MA3','3I',NULL),('MA3','A3',NULL),('MA3','DI',NULL),('MA3','H3',NULL),('MA3','I3',NULL),('MA3','V3',NULL),('MA4','4D',NULL),('MA4','4H',NULL),('MA4','4I',NULL),('MA4','A4',NULL),('MA4','C4',NULL),('MA4','D4',NULL),('MA4','E4',NULL),('MA4','H4',NULL),('MA4','I4',NULL),('MA4','V4',NULL),('MEI','A6',NULL),('MEI','D6',NULL),('MEI','H6',NULL),('MEI','I6',NULL),('MEI','PS',NULL),('MEI','S1',NULL),('MEI','V6',NULL),('MRC','4I',NULL),('MSU','E6',NULL),('NIE','3A',NULL),('NUT','4H',NULL),('OCC','V6',NULL),('OEC','I3',NULL),('OR2','SS',NULL),('OR3','3A',NULL),('OR3','3C',NULL),('OR3','3D',NULL),('OR3','3H',NULL),('OR3','3I',NULL),('OR4','4D',NULL),('OR4','4H',NULL),('OR4','4I',NULL),('OR4','C4',NULL),('OR4','E4',NULL),('OR4','I4',NULL),('OR5','5H',NULL),('OR5','5I',NULL),('OR5','A5',NULL),('OR5','C5',NULL),('OR5','D5',NULL),('OR5','E5',NULL),('OR5','H5',NULL),('OR5','I5',NULL),('OR5','V5',NULL),('OR6','64',NULL),('OR6','6H',NULL),('OR6','6I',NULL),('OR6','A6',NULL),('OR6','C6',NULL),('OR6','D6',NULL),('OR6','E6',NULL),('OR6','H6',NULL),('OR6','I6',NULL),('OR6','V6',NULL),('ORI','5D',NULL),('ORI','PS',NULL),('ORI','S1',NULL),('P5','5D',NULL),('P5','5H',NULL),('P5','5I',NULL),('P5','D5',NULL),('PA1','PS',NULL),('PA1','S1',NULL),('PA2','S2',NULL),('PA2','SS',NULL),('PA3','3A',NULL),('PA3','3C',NULL),('PA3','3D',NULL),('PA3','3H',NULL),('PA3','3I',NULL),('PA3','A3',NULL),('PA3','DI',NULL),('PA3','H3',NULL),('PA3','I3',NULL),('PA3','V3',NULL),('PA4','4D',NULL),('PA4','4H',NULL),('PA4','4I',NULL),('PA4','A4',NULL),('PA4','C4',NULL),('PA4','E4',NULL),('PA4','H4',NULL),('PA4','I4',NULL),('PA4','V4',NULL),('PA5','A5',NULL),('PA5','C5',NULL),('PA5','E5',NULL),('PA5','H5',NULL),('PA5','I5',NULL),('PA5','V5',NULL),('PA6','64',NULL),('PA6','6H',NULL),('PA6','6I',NULL),('PA6','A6',NULL),('PA6','C6',NULL),('PA6','D4',NULL),('PA6','D6',NULL),('PA6','E6',NULL),('PA6','H6',NULL),('PA6','I6',NULL),('PA6','V6',NULL),('PAM','I6',NULL),('PDM','64',NULL),('PEI','5D',NULL),('PGG','DI',NULL),('PRE','E5',NULL),('PRO','5I',NULL),('PS1','A5',NULL),('PS1','E5',NULL),('PS2','A6',NULL),('PS2','E6',NULL),('PSP','H5',NULL),('PV','3D',NULL),('PWE','6I',NULL),('QU1','PS',NULL),('QU1','S1',NULL),('QU2','S2',NULL),('QU2','SS',NULL),('RDM','C4',NULL),('RID','I4',NULL),('RIN','A5',NULL),('ROF','V6',NULL),('ROP','3C',NULL),('RRC','V3',NULL),('RVN','H4',NULL),('SEN','3A',NULL),('SF1','5D',NULL),('SF1','5H',NULL),('SF1','5I',NULL),('SF1','A5',NULL),('SF1','C5',NULL),('SF1','D5',NULL),('SF1','E5',NULL),('SF1','H5',NULL),('SF1','I5',NULL),('SF1','V5',NULL),('SF2','64',NULL),('SF2','6H',NULL),('SF2','6I',NULL),('SF2','A6',NULL),('SF2','C6',NULL),('SF2','D6',NULL),('SF2','E6',NULL),('SF2','H6',NULL),('SF2','I6',NULL),('SF2','V6',NULL),('SIN','5I',NULL),('SP','4H',NULL),('SQ1','A5',NULL),('SQ1','E5',NULL),('SQ2','A6',NULL),('SQ2','E6',NULL),('SSR','5H',NULL),('TB2','6H',NULL),('TB2','H6',NULL),('TC','4D',NULL),('TC1','5H',NULL),('TCL','6H',NULL),('TPE','4D',NULL),('TSB','5H',NULL),('TSB','H5',NULL),('TU2','SS',NULL),('TU3','3A',NULL),('TU3','3C',NULL),('TU3','3D',NULL),('TU3','3H',NULL),('TU3','3I',NULL),('TU4','4D',NULL),('TU4','4H',NULL),('TU4','4I',NULL),('TU4','C4',NULL),('TU4','E4',NULL),('TU5','5D',NULL),('TU5','5H',NULL),('TU5','5I',NULL),('TU5','A5',NULL),('TU5','C5',NULL),('TU5','D5',NULL),('TU5','E5',NULL),('TU5','H5',NULL),('TU5','I5',NULL),('TU5','V5',NULL),('TU6','64',NULL),('TU6','6H',NULL),('TU6','6I',NULL),('TU6','A6',NULL),('TU6','C6',NULL),('TU6','D6',NULL),('TU6','E6',NULL),('TU6','H6',NULL),('TU6','I6',NULL),('TU6','V6',NULL),('TUI','PS',NULL),('TUI','S1',NULL),('UR','D5',NULL),('USD','I5',NULL);
/*!40000 ALTER TABLE `planes_estudio_materias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `planes_estudio_materias_view`
--

DROP TABLE IF EXISTS `planes_estudio_materias_view`;
/*!50001 DROP VIEW IF EXISTS `planes_estudio_materias_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `planes_estudio_materias_view` AS SELECT 
 1 AS `clave_plan`,
 1 AS `materia`,
 1 AS `nombre_abr`,
 1 AS `tipo_calificacion`,
 1 AS `orden`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `planes_estudio_visible_view`
--

DROP TABLE IF EXISTS `planes_estudio_visible_view`;
/*!50001 DROP VIEW IF EXISTS `planes_estudio_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `planes_estudio_visible_view` AS SELECT 
 1 AS `clave_plan`,
 1 AS `descripcion`,
 1 AS `semestre`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `profesores`
--

DROP TABLE IF EXISTS `profesores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesores` (
  `apellido_materno` varchar(45) DEFAULT NULL,
  `apellido_paterno` varchar(45) DEFAULT NULL,
  `nombres` varchar(45) DEFAULT NULL,
  `clave_profesor` varchar(6) NOT NULL,
  `abr_profesion` varchar(10) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `domicilio` varchar(80) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `profesion` varchar(100) DEFAULT NULL,
  `CURP` varchar(18) NOT NULL,
  `PRESU` varchar(18) DEFAULT NULL,
  `email` varchar(85) DEFAULT NULL,
  PRIMARY KEY (`clave_profesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesores`
--

LOCK TABLES `profesores` WRITE;
/*!40000 ALTER TABLE `profesores` DISABLE KEYS */;
INSERT INTO `profesores` VALUES ('RUVALVABA','MARTINEZ','HUMBERTO ALEJANDRO','','HAMR','','C. BATALLA DE BACHIMBA 208','1982-01-01','','MARH820101HCHRVM07','',NULL),('AGUIRRE','BOLIVAR','ANTONIO','ABA','LIC.','(639)1014802','Calle 41 Sur 1618, Col. Linda Vista, Cd. Delicias, Chihuahua','1960-10-10','LICENCIADO EN PEDAGOGIA','BOAA601010HCHLGN01','1902E8829000191055',NULL),('ONTIVEROS','CHACON','ALBERTO','ACO','ING.','(639)1070385','C. 14.5 Nte. N° 131-A, Col. del Empleado, Cd. Delicias,Chih.','1959-01-30','INGENIERO AGRONOMO','CAOA590130HCHHNL04','1902E8973000191004',NULL),('CUEVAS','RIVERA','AIDDE GRACIELA','AGRC','C.P.','(639)1252343','Ave. Río San Pedro Nş 627, Fov. Las Huertas, Cd. Delicias, Chih.','1972-11-25','CONTADOR PUBLICO','RICA721125MDGVVD06','1902E8771000191051',NULL),('NEVAREZ','FIERRO','AMALIA ISABEL','AIFN','C.P.','(639)1076116','Calle Nectarinas 39, Fracc. las Huertas, Cd. Delicias, Chihuahua','1963-06-28','CONTADOR PUBLICO','FINA630628MCHRVM09','1902E8829000191117',NULL),('MOLINA','JIMENEZ','ADRIANA','AJM','QUIM.','(   )','Conocido Bellavista, Saucillo, Chih.','1980-11-30','QUIMICA BACTERIOLOGA, PARASITOLOGA','JIMA801130MCHMLD00','',NULL),('CHAVEZ','RODALLEGAS','ANA LAURA','ALRC','LIC.','(639)1354689','AVE. 18 PTE. 512 CD. DELICIAS,CHIH','1989-03-15','LIC. DERECHO','ROCA890315MCHDHN08','',NULL),('GOMEZ','MARTINEZ','ALFA','AMG','LIC.','(639)1102020','Ave. San Fco.del Oro Nş 1450, Fovissste II, Cd. Delicias, Chih.','1963-07-02','LIC. EN EDUCACION','MAGA630702MCHRML07','1902E8617060191046',NULL),('BARROTERAN','LOPEZ','ANA MARGARITA','AMLB','LIC.','(639)1293974','Ave. 10.5 Sur 4419 Fracc. Roma Delicias, Chih.','1989-11-23','LIC. ALTERACIONES DE LENGUAJE Y AUDICION','LOBA231189MCHPRN09','',NULL),('REYES','MORENO','ADRIANA MARISOL','AMMR','ING.','(621)1292324','Av. Puebla N° 105, Fracc. Las Arboledas, Cd. Saucillo, Chih.','1984-09-04','ING. INDUSTRIAL','MORA840904MCHRYD01','1902E8725000191083',NULL),('MORALES','RODRIGUEZ','ARTURO','ARM','LIC.ECON','(639)1047412','Ave. 26 Pte. 203, Col Magisterial, Del. CHIH.','1986-09-26','LIC. EN ECONOMIA','ROMA860926HCHDRR11','',NULL),('ENRIQUEZ','MONTES','ABEL ROBERTO','ARME','TEC.','(639)1190942','Av. 21 Pte. Nş 106 Col. Magisterial, Cd. Del., Chihuahua','1978-05-10','TECNICO FOLKLORISTA','MOEA780510HCHNNB06','6359E8619050720007',NULL),('ESCOBAR','FERNANDEZ','ADRIANA XITLALLI','AXFE','LIC.','(639)1195123','Ave. 15 Poniente # 615, Centro, Cd. Delicias, Chih.','1987-11-19','LIC. EN NUTRICION','FEEA871119MCHRSD04','1902E8617090191035','escaleracorrales845@gmail.com'),('COVARRUBIO','RODRIGUEZ','BRENDA LETICIA','BLRC','','(639)1194494','Conocido Estación Conchos','1982-10-19','','','',NULL),('GALLEGOS','MILLAN','CESAR ANTONIO','CAMG','Lic. Admin','(639)1238344','Av. Baja California N° 4, Rosales, Chih.','1971-04-29','LICENCIADO EN ADMINISTRACION','MIGC710429HCHLLS08','1902E8617120191078',NULL),('BRAVO','PRECIADO','CARLOS ALBERTO','CAPB','ING.','(639)1110935','Priv. Av. 1a. Sur 1007, Fracc. Delia, Del.','1990-09-04','ING. QUIMICO','PEBC900904HCHRRR09','',NULL),('SANTIAGO','CHARIS','CRISPIN','CCS','C.P.','6391247802','C. Dorados de Villa 1000, Tierra y Libertad, Del.','2086-04-11','CONTADOR PÚBLICO','CASC860411HCHHNR04','',NULL),('GUADERRAMA','GALLEGOS','CRISTINA','CGG','LIC.','(639)1220972','Av. Sonora Nte. # 408, Rosales, Chihuahua','1976-12-15','LIC. EN DERECHO','GAGC761215MCHLDR08','08QA01005000080003',NULL),('LOPEZ','NAVA','CLAUDIA CRISTINA','CNL','ING.','(639)1218860','C. Obsidiana # 24, Fracc. San Carlos, Cd. Delicias, Chih.','1983-09-17','ING. SISTEMAS','NALC830917MCHVPL08','',NULL),('CASAS','GRIMALDO','DANIEL','DGC','LIC.','(639)1070405','C.Jaime Medina Nş 828, Inf. Nuevo Delicias, Cd. Delicias, Chih.','1985-03-07','LIC. EN SISTEMAS COMPUTACIONALES','GICD850307HCHRSN09','1902E8617100191015',NULL),('CONTRERAS','GONZALEZ','DANIELA GRISSEL','DGGC','Lic.Cs.Com','(639)1120988','Av. Manuel Amaya N° 818, Col. Ignacio C. Enriquez, Cd.Delicias','1983-06-23','LIC. EN CIENCIAS DE LA COMUNICACION','GOCD830623MCHNNN08','',NULL),('MARTÖNEZ','LOMELÖ','DANIEL','DLM','Agronomo','(639)1132021','C. Luis Nevarez Acosta 611. Nvo. Delicias','1960-11-18','AGRONOMO','LOMD601118HCHMRN08','',NULL),('RODRIGUEZ','SORIANO','ERIKA CRISTINA','ECSR','Ing.Indus.','(639)1129004','Av. 12.5 Sur N° 4527, Col. Emiliano J. Laing, Cd. Delicias','1983-10-30','INGENIERO INDUSTRIAL','SORE831030MCHRDR04','1902E8617110191008',NULL),('CHAPARRO','TELLAECHE','EDNA LAURA','ELTC','LIC.','(614)1421287','MONTE ENCINO 11922, QUINTAS CAROLINAS, CHIH.','1984-07-22','LIC. CIENCIAS DE LA EDUCACION','TECE840722MCHLHD07','',NULL),('GLORIA','ESPINOZA','ELSA YUNUEN','EYEG','LIC.','(639)1230447','C. Morelos # 401, Meoqui, Chih.','1983-10-27','LIC. LETRAS ESPAŃOLAS','EIGE831027MCHSLL06','',NULL),('ROJAS','GODINEZ','FRANCISCO JAVIER','FJGR','Profr.','(639)1295546','Av. 7a. Pte. # 809, Centro, Cd. Delicias, Chihuahua','1954-03-13','PASANTE EN HISTORIA','GORF540313HSPDJR02','6359E8621160730001',NULL),('GONZALEZ','OCON','GERARDO ANTONIO','GAOG','LIC.','(639)1029175','C. del Arado Nş 35, Fracc. La Labor, Cd. Delicias, Chih.','1977-12-10','LIC. EN INFORMATICA','OOGG771210HCHCNR08','1902E8771000191050',NULL),('ALCALA','GARCIA','GERARDO MAURICIO','GMGA','ING.','(614)1258760','AVE. DEL PARQUE ORIENTE N° 221','1989-07-27','INGENIERO AEROESPACIAL','GAAG890727HCHRLR03','',NULL),('SANCHEZ','MARTINEZ','GERARDO','GMS','LIC.','(639)1177560','Calle 9 y media Poniente N°106','1989-04-15','LIC. EN EDUCACION FISICA','MASG890415HCHRNR04','',NULL),('TRILLO','TREVIZO','GRICEL ASTRID','GTT','LIC.','(639)1254186','Av. 11 Sur N° 810, Fracc. Los Angeles, Cd. Delicias, Chihuahua','1986-11-30','LICENCIATURA EN CIENCIAS DE LA COMUNICACION','TETG861130MCHRRR01','1902E8617080191032',NULL),('RUVALVABA','MARTINEZ','HUMBERTO ALEJANDRO','HAMR','','','C. BATALLA DE BACHIMBA 208','1982-01-01','','MARH820101HCHRVM07','',NULL),('ARZAGA','VAZQUEZ','HECTOR HASSAM','HHVA','ING.Aesp.','(639)1151195','C. Guadalupe Posada # 1422, Col. Fco. Villa','1996-07-06','ING. AEROESPACIAL','VAAH960706HCHSRC09','',NULL),('MADRID','RUIZ','ISMAEL','IRM','Ing.Indus.','(639)1108989','Av. 18 Pte. N° 108, Cd. Delicias, Chihuahua','1981-10-14','ING. INDUSTRIAL','RUMI811014HCHZDS03','1902E8771000191062',NULL),('PADILLA','BOLIVAR','JOSE ANTONIO','JABP','ING.','(639)5017359','C. 41 Sur N° 1618, Col. Linda Vista, Cd. Delicias, Chih.','1983-08-12','ING. CIVIL','BOPA830812HCHLDN06','1902E8629120191036',NULL),('MEDRANO','RICO','JORGE ARTURO','JARM','Biologo','(639)1186436','C. 11 Nte. N° 412, Fracc. El Pedregal II, Cd. Del.','1982-06-10','BIOLOGO','RIMJ820610HDGCDR00','1902E8617100191042',NULL),('CASTRO','CARBAJAL','JUAN CARLOS','JCCC','Obrero','(639)1364716','Calle Cipreses # 1117, Las Palmas; Delicias','1973-08-23','OBRERO','CACJ730823HCHRSN08','',NULL),('FERNANDEZ','RODRIGUEZ','JUAN CARLOS','JCRF','LIC.','(639)1129945','C. Aldama N° 113, Inf. Nvo. Delicias','1980-12-14','LIC. EN INFORMATICA','ROFJ801214HCHDRN06','',NULL),('CARBAJAL','CALLEROS','JUAN IGNACIO','JICC','LIC.','(639)3982212','Av. 18 Pte. 512,Centro,Cd. Delicias','1968-01-31','LIC. EN EDUCACION SECUNDARIA','CACJ680131HCHLRN01','E861703.0191064',NULL),('BREACH','ARMENTA','JOSE LUIS','JLAB','LIC.','','','2015-09-16','LIC. EDUCACION PRIMARIA','','',NULL),('HERRERA','ALMAGUER','JAVIER LUIS','JLAH','LIC.','(639)1070758','C. Elisa # 207 Sur, Fracc. Flores del Tepeyac, Cd. Delicias, Chih','1967-10-08','LICENCIADO EN ADMINISTRACION PUBLICA','AAHJ671008HCHLRV09','1902E8829000191054',NULL),('CARREON','CAMARGO','JOSE LUIS','JLCC','NUT','(639)1298518','C. 13 Pte. N° 121, Del Empleado','1990-03-19','NUTRIOLOGO','CACL900319HCHMRS06','',NULL),('ROMERO','MARTINEZ','JAIME','JMR','LIC.','(639)4749572','C.Rincón de N. Sra. # 2025, Fracc. El Rincón, Cd. Delicias','1965-05-12','LICENCIADO EN PEDAGOGIA','MARJ650512HCHRMM03','1902E8829000191042',NULL),('TORRES','RIOS','JESSICA','JRT','LIC.DER.','','AVE. 18.5 SUR 3208, LOS GIRASOLES DELICIAS, CHIH.','1991-06-19','DERECHO','RITJ910619MCHSRS03','',NULL),('PRIETO','LOPEZ','LUIS ALFONSO','LALP','ING.','(639)1193078','C. Ignacio C. Enriquez 815 Nvo. Del.','1987-08-21','ING. INDUSTRIAL','LOPL870821HCHPRS05','',NULL),('ESCAJEDA','HERNANDEZ','LUIS CARLOS','LCHE','LIC.','(639)1292349','Priv. Ave. 12 Sur 4406, Fracc. Roma, Cd. Delicias, Chihuahua','1965-12-25','LIC. EDUCACION SECUNDARIA AREA ESPAŃOL','HEEL651225HCHRSS04','1902E8825000191049',NULL),('BREACH','ARMENTA','JOSE LUIS','LIC.','JLAB','','','2015-09-16','LIC. EDUCACION PRIMARIA','','',NULL),('FIERRO','RUIZ','LILIANA IVON','LIRF','LIC.','(639)5018052','Conocido Ex-Hacienda, Cd. Delicias, Chih.','1991-11-18','LIC. ALTERACIONES AUDITIVAS Y LENGUAJE','RUFL911118MCHZRL03','',NULL),('CARRILLO','LOPEZ','LUCIA','LLC','LIC.','(639)1118101','C. Valle de Allende # 909, Saucillo, Chihuahua','1974-03-15','LICENCIADA EN INFORMATICA','LOCL740315MCHPRC02','1902E8829000191181',NULL),('HERRERA','MALDONADO','LUCRECIA','LMH','LIC.','(639)4705271','Calle Sonora 33, Fracc. Los Jazmines, Cd. Delicias, Chih.','1965-11-24','LIC. EN PSICOLOGIA','MAHL651124MCHLRC04','1902E8629120191035',NULL),('MARFIL','RAMIREZ','LEONELA','LRM','ING.','(639)1288089','Ave. Ing. Manuel Amaya 834, Col. Ignacio C. Enriquez, Delicias','1989-05-27','ING. EN ECOLOGIA','RAML890527MCHMRN09','',NULL),('DEL REAL','SILVA','LETICIA','LSDR','QUIM.','(639)1471859','Av. 11 Pte. 416, Centro, Delicias','1983-06-23','QUIMICA INDUSTRIAL','SIRL830623MCHLLT04','',NULL),('REYES','MEDRANO','MARTIN CARMELO','MCMR','ING.','(639)1101527','Calle 42 Sur 1618, Col. Linda Vista, Cd. Delicias, Chihuahua','1964-07-21','INGENIERO FITOTECNISTA','MERM640721HCHDYR05','1902E8929000191022',NULL),('RIVAS','CHAVARRIA','MAYRA','MCR','ING.','(639)1360940','','1989-05-09','ING. AGRONOMO FITOTECNISTA','CARM890509MCHHVY01','',NULL),('HERRERA','RIVERA','MARIA ELENA','MERH','LIC.','(639)1322787','Ave. 10a. Sur 601, Cd. Delicias, Chh.','1956-03-23','LIC. EN EDUCACION','','',NULL),('FLORES','GALLEGOS','MARCOS','MGF','M.C.','(639)1363571','C.27 Sur 1839, Col. Santo Nińo, Cd. Del.','1963-11-09','MASTER EN CIENCIAS EN HORTICULTURA Y AGRONEGOCIOS','GAFM631109HCHLLR00','1902E8873000191001',NULL),('SILVA','GALVAN','MANUEL','MGS','Lic.EdFis.','(639)1333418','Ave. 8a. Sur Nş 1115, Inf. Revolución, Cd. Delicias, Chih.','1986-03-31','LIC. EN EDUCACION FISICA','GASM860331HCHLLN06','1902E8617110191002',NULL),('SANCHEZ','MARTINEZ','MARIA LUISA','MLMS','ING.','(639)1248240','C. 30 Sur Nş 1422, Col. Ricardo F. Magón, Cd. Delicias, Chihuahua','1976-09-25','ING. SISTEMAS COMPUTACIONALES','MASL760925MCHRNS04','1902E8825000191034',NULL),('CARLOS','FLORES','MARIA MONCERRAT','MMFC','Psicologa','(639)1310565','Ave. 7a. Nte. N° 213, Centro, Delicias, Chih.','1989-10-05','PSICOLOGA','FOCM891005MCHLRN09','',NULL),('VAZQUEZ','CONTRERAS','MARTHA PATRICIA','MPCV','LIC.','(639)1251169','C. 7a. Nte. # 1, Fracc. Imperial, Cd. Delicias, Chihuahua','1970-02-10','LICENCIADA EN INFORMATICA','COVM700210MVHNZR02','1902E8619090720002',NULL),('HOLGUIN','JARAMILLO','NORMA ALEJANDRA','NAJH','ING.','(639)1317979','C. TUL No. 2022, FRACC. PASEOS DEL ALGODON, DEL. CHIH.','1989-12-15','ING. INDUSTRIAL','JAHN891215MCHRLR03','',NULL),('SIMENTAL','AGUILAR','NANCY','NAS','LIC.','(639)1381108','C. Chih. N° 511, Lazaro Cardenas','1989-03-26','LIC. INTERVENCION EDUCATIVA','AUSN890326MCHGMN09','',NULL),('REYNA','GINER','NANCY IMELDA','NIGR','LIC.','(639)1070076','Calle Armando Muńiz N°1608, Cumbres del Deporte','1988-06-29','LIC. EN EDUCACION','GIRN880629MCHNYN04','',NULL),('SILVA','GOMEZ','OSCAR DANIEL','ODGS','C.P.','(639)1147991','C. 11 Pte. N° 413, Sector Pte. Delicias, Chih.','1979-10-19','CONTADOR PUBLICO','GOSO791019HCHMLS07','',NULL),('RAMOS','GANDARA','OMAR','OGR','Lic.Espańo','(639)1158015','C. 11 # 38, Centro, Cd. Saucillo, Chihuahua','1980-03-11','LIC. EN ESPAŃOL','GARO800311HCHNMM04','1902E8617150191031',NULL),('GAMEZ','YAŃEZ','OSMAR','OYG','LIC.','(639)4674251','C. Moscatel 128, Los Vińedos, Delicias','1981-12-16','LIC. NUTRICION','YAGO811216HCHXMS09','',NULL),('PEŃA','ANAYA','PAOLA ALEJANDRA','PAAP','LIC.','(639)4674251','C. Moscatel # 128,Los Vińedos','1979-12-06','LIC. EN LETRAS ESPAŃOLAS','AAPP791206MCHNXL05','',NULL),('RODRIGUEZ','REDE','PEDRO','PRR','ING.','(639)1114265','C. Felipe Angeles 619 Sur, Div. Norte, Del.','1977-08-09','ING. ELECTROMECANICO','RERP770809HCHDDD04','',NULL),('MOLINA','JIMENEZ','ADRIANA','QUIM.','AJM','(   )','Conocido Bellavista, Saucillo, Chih.','1980-11-30','QUIMICA BACTERIOLOGA, PARASITOLOGA','JIMA801130MCHMLD00','',NULL),('VALENZUELA','BOLIVAR','ROBERTO ABELARDO','RABV','Ing.Ind.','(639)1073136','C. Fco. I Madero Ote. N° 28, Col. Benito Juarez,Cd. Delicias Chih','1967-07-05','INGENIERO INDUSTRIAL','BOVR670705HCHLLB04','1902E8617180191017',NULL),('SAENZ','RAMOS','ROSA MARIA','RMRS','QUIM.','(639)1613513','Av. de la Luz N° 103, Del. Residencial, Cd. Del.','1964-08-06','QUIMICO BROMATOLOGO','RASR640806MCHMNS03','1902E8617100191041',NULL),('BAEZA','GINER','SARAI','SGB','PSICOLOGA','(639)1178463','Ave. 12 Sur 4310, Fracc. Roma, Delicias, Chih.','1989-05-15','PSICOLOGA','GIBS890515MCHNZR01','',NULL),('GINEZ','AMAYA','TANIA ELIZABETH','TEAG','LIC.','(639)1029231','Priv. de Los Ciruelos # 28, Fracc. Las Huertas, Cd. Delicias','1987-11-24','LIC. EN ECONOMIA','AAGT871124MCHMNN09','1902E8617070191006',NULL),('RONQUILLO','MARTINEZ','VLADIMIR','VMR','ING.','(621)4750572','Av. 6a. N° 22, Col. Guadalupe, Cd. Saucillo, Chihuahua','1973-01-05','ING. ELECTROMECANICO','MARV730105HCHRNL06','1902E8825000191041',NULL),('LOPEZ','RODRIGUEZ','YERENDIRA','YRL','Lic.Musica','(639)5491757','Av. Mina Barroteran N° 1310, Fov. II, Cd. Delicias, Chihuahua','1986-03-31','LICENCIADA EN MUSICA','ROLY860331MCHDPR03','1902E8617080191030',NULL);
/*!40000 ALTER TABLE `profesores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesoresWebUsers`
--

DROP TABLE IF EXISTS `profesoresWebUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesoresWebUsers` (
  `clave_profesor` varchar(45) NOT NULL,
  `password` varchar(80) NOT NULL,
  PRIMARY KEY (`clave_profesor`),
  UNIQUE KEY `clave_profesor_UNIQUE` (`clave_profesor`),
  CONSTRAINT `profe` FOREIGN KEY (`clave_profesor`) REFERENCES `profesores` (`clave_profesor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesoresWebUsers`
--

LOCK TABLES `profesoresWebUsers` WRITE;
/*!40000 ALTER TABLE `profesoresWebUsers` DISABLE KEYS */;
INSERT INTO `profesoresWebUsers` VALUES ('AXFE','$2a$10$i87SajAiQtWTM2lmGuDJA.8pmQIhqOAethJnI0N7Mqtot8ASwk10u');
/*!40000 ALTER TABLE `profesoresWebUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `profesoresWebUsers_vieww`
--

DROP TABLE IF EXISTS `profesoresWebUsers_vieww`;
/*!50001 DROP VIEW IF EXISTS `profesoresWebUsers_vieww`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `profesoresWebUsers_vieww` AS SELECT 
 1 AS `clave_profesor`,
 1 AS `nombre_completo`,
 1 AS `email`,
 1 AS `password`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `profesores_sin_pass`
--

DROP TABLE IF EXISTS `profesores_sin_pass`;
/*!50001 DROP VIEW IF EXISTS `profesores_sin_pass`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `profesores_sin_pass` AS SELECT 
 1 AS `clave_profesor`,
 1 AS `email`,
 1 AS `nombres`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `profesores_view`
--

DROP TABLE IF EXISTS `profesores_view`;
/*!50001 DROP VIEW IF EXISTS `profesores_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `profesores_view` AS SELECT 
 1 AS `apellido_paterno`,
 1 AS `apellido_materno`,
 1 AS `nombres`,
 1 AS `clave_profesor`,
 1 AS `abr_profesion`,
 1 AS `telefono`,
 1 AS `domicilio`,
 1 AS `fecha_nacimiento`,
 1 AS `profesion`,
 1 AS `CURP`,
 1 AS `PRESU`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `profesores_visible_view`
--

DROP TABLE IF EXISTS `profesores_visible_view`;
/*!50001 DROP VIEW IF EXISTS `profesores_visible_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `profesores_visible_view` AS SELECT 
 1 AS `clave_profesor`,
 1 AS `nombre_completo`,
 1 AS `profesion`,
 1 AS `abr_profesion`,
 1 AS `CURP`,
 1 AS `telefono`,
 1 AS `domicilio`,
 1 AS `PRESU`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `semestres`
--

DROP TABLE IF EXISTS `semestres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semestres` (
  `semestre` int NOT NULL,
  PRIMARY KEY (`semestre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semestres`
--

LOCK TABLES `semestres` WRITE;
/*!40000 ALTER TABLE `semestres` DISABLE KEYS */;
INSERT INTO `semestres` VALUES (1),(2),(3),(4),(5),(6);
/*!40000 ALTER TABLE `semestres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `semestres_view`
--

DROP TABLE IF EXISTS `semestres_view`;
/*!50001 DROP VIEW IF EXISTS `semestres_view`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `semestres_view` AS SELECT 
 1 AS `semestre`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `sexos`
--

DROP TABLE IF EXISTS `sexos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sexos` (
  `sexo` varchar(10) NOT NULL,
  PRIMARY KEY (`sexo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sexos`
--

LOCK TABLES `sexos` WRITE;
/*!40000 ALTER TABLE `sexos` DISABLE KEYS */;
INSERT INTO `sexos` VALUES ('F'),('M');
/*!40000 ALTER TABLE `sexos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turnos`
--

DROP TABLE IF EXISTS `turnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turnos` (
  `turno` varchar(15) NOT NULL,
  PRIMARY KEY (`turno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turnos`
--

LOCK TABLES `turnos` WRITE;
/*!40000 ALTER TABLE `turnos` DISABLE KEYS */;
INSERT INTO `turnos` VALUES ('M'),('V');
/*!40000 ALTER TABLE `turnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutores`
--

DROP TABLE IF EXISTS `tutores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutores` (
  `numero_control` varchar(6) NOT NULL,
  `nombres` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `ocupacion` varchar(45) NOT NULL,
  KEY `numero_control_idx` (`numero_control`),
  CONSTRAINT `numero_control` FOREIGN KEY (`numero_control`) REFERENCES `alumnos` (`numero_control`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutores`
--

LOCK TABLES `tutores` WRITE;
/*!40000 ALTER TABLE `tutores` DISABLE KEYS */;
/*!40000 ALTER TABLE `tutores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webUsers`
--

DROP TABLE IF EXISTS `webUsers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `webUsers` (
  `numero_control` varchar(6) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`numero_control`),
  UNIQUE KEY `numero_control_UNIQUE` (`numero_control`),
  CONSTRAINT `alu` FOREIGN KEY (`numero_control`) REFERENCES `alumnos` (`numero_control`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webUsers`
--

LOCK TABLES `webUsers` WRITE;
/*!40000 ALTER TABLE `webUsers` DISABLE KEYS */;
/*!40000 ALTER TABLE `webUsers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `webUsers_vieww`
--

DROP TABLE IF EXISTS `webUsers_vieww`;
/*!50001 DROP VIEW IF EXISTS `webUsers_vieww`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `webUsers_vieww` AS SELECT 
 1 AS `numero_control`,
 1 AS `nombre_completo`,
 1 AS `grupo`,
 1 AS `password`,
 1 AS `email`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'cebdatabase'
--

--
-- Dumping routines for database 'cebdatabase'
--
/*!50003 DROP FUNCTION IF EXISTS `alumno_no_li` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` FUNCTION `alumno_no_li`(gpo varchar(3),numList varchar(6)) RETURNS int
    DETERMINISTIC
BEGIN
declare c int;
select numero_lista
	into c
from (
	select 
		numero_control,
		row_number() OVER `w` AS `numero_lista`
    from alumnos
		where grupo = gpo 
 window `w` AS (ORDER BY `cebdatabase`.`alumnos`.`apellido_paterno`) 
) as ordered_alu
 where numero_control = numList;

RETURN c;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `alumno_num` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` FUNCTION `alumno_num`(lisNum varchar(10),gpo varchar(3)) RETURNS int
    DETERMINISTIC
BEGIN
declare c int;
select 
	grupo_listado(gpo) AS `no_lista`
    into c
    from alumnos
    where numero_control = lisNum ;
RETURN c;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `grupo_alumnos_count` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` FUNCTION `grupo_alumnos_count`(grupo varchar(3)) RETURNS int
    DETERMINISTIC
BEGIN
	declare rowCount int;
	select count(*) into rowCount from alumnos 
		where
	alumnos.grupo = grupo;
RETURN rowCount;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `bol_calif_con_pro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `bol_calif_con_pro`(gpo varchar(40), materia varchar(40), periodo varchar(40), evaluacion varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_booleanas`.`evaluacion` AS `evaluacion`,
        `cebdatabase`.`calificaciones_booleanas`.`calificacion` AS `calificacion`,
        `cebdatabase`.`calificaciones_booleanas`.`faltas` AS `faltas`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = periodo
        LEFT JOIN `cebdatabase`.`calificaciones_booleanas` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_booleanas.`calificacion_clave`
            AND `cebdatabase`.`calificaciones_booleanas`.`evaluacion` = evaluacion
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;


END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `bol_calif_full_eval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `bol_calif_full_eval`(gpo varchar(40), materia varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_booleanas`.`evaluacion` AS `evaluacion`,
        `cebdatabase`.`calificaciones_booleanas`.`calificacion` AS `calificacion`,
        `cebdatabase`.`calificaciones_booleanas`.`faltas` AS `faltas`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = (select periodo from currentperiodo)
        LEFT JOIN `cebdatabase`.`calificaciones_booleanas` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_booleanas.`calificacion_clave`
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `delete_trash_from_califas` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `delete_trash_from_califas`()
BEGIN
    set sql_safe_updates = 0;
	delete from cebdatabase.calificaciones_numericas where isnull(faltas) and isnull(calificacion);
    delete from cebdatabase.calificaciones_booleanas where isnull(faltas) and isnull(calificacion);
    set sql_safe_updates = 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `num_calif_con_pro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `num_calif_con_pro`(gpo varchar(40), materia varchar(40), periodo varchar(40), evaluacion varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_numericas`.`evaluacion` AS `evaluacion`,
        `cebdatabase`.`calificaciones_numericas`.`calificacion` AS `calificacion`,
        `cebdatabase`.`calificaciones_numericas`.`faltas` AS `faltas`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = periodo
        LEFT JOIN `cebdatabase`.`calificaciones_numericas` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_numericas.`calificacion_clave`
            AND `cebdatabase`.`calificaciones_numericas`.`evaluacion` = evaluacion
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `num_calif_full_eval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `num_calif_full_eval`(gpo varchar(40), materia varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_numericas`.`evaluacion` AS `evaluacion`,
        `cebdatabase`.`calificaciones_numericas`.`calificacion` AS `calificacion`,
        `cebdatabase`.`calificaciones_numericas`.`faltas` AS `faltas`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = (select periodo from currentperiodo)
        LEFT JOIN `cebdatabase`.`calificaciones_numericas` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_numericas.`calificacion_clave`
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sem_calif_con_pro` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `sem_calif_con_pro`(gpo varchar(40), materia varchar(40), periodo varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_semestrales`.`calificacion` AS `calificacion`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = periodo
        LEFT JOIN `cebdatabase`.`calificaciones_semestrales` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_semestrales.`calificacion_clave`
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sem_calif_full_eval` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`remote`@`%` PROCEDURE `sem_calif_full_eval`(gpo varchar(40), materia varchar(40))
BEGIN
	    SELECT 
        numero_lista,
        `cebdatabase`.`alumnos_visible_view`.`numero_control` AS `numero_control`,
        `cebdatabase`.`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,
        `cebdatabase`.`alumnos_visible_view`.`grupo` AS `grupo`,
        `cebdatabase`.`calificaciones`.`calificacion_clave` AS `calificacion_clave`,
        `cebdatabase`.`calificaciones`.`materia` AS `materia`,
        `cebdatabase`.`calificaciones`.`periodo` AS `periodo`,
        `cebdatabase`.`alumnos_visible_view`.`semestre` AS `semestre`,
        `cebdatabase`.`calificaciones_semestrales`.`calificacion` AS `calificacion`
    FROM
        `cebdatabase`.`alumnos_visible_view`
        
        LEFT JOIN `cebdatabase`.`calificaciones` 
			ON `cebdatabase`.`alumnos_visible_view`.`numero_control` = `cebdatabase`.`calificaciones`.`clave_alumno`
			AND `cebdatabase`.`calificaciones`.`materia` = materia
			AND `cebdatabase`.`calificaciones`.`periodo` = (select periodo from currentperiodo)
        LEFT JOIN `cebdatabase`.`calificaciones_semestrales` 
			ON `cebdatabase`.`calificaciones`.`calificacion_clave` = `cebdatabase`.calificaciones_semestrales.`calificacion_clave`
		where `cebdatabase`.`alumnos_visible_view`.`grupo` = gpo;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `alumno_bol_califa_charge_view`
--

/*!50001 DROP VIEW IF EXISTS `alumno_bol_califa_charge_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `alumno_bol_califa_charge_view` AS select `alumno_califa_charge_view`.`numero_lista` AS `numero_lista`,`alumno_califa_charge_view`.`numero_control` AS `numero_control`,`alumno_califa_charge_view`.`nombre_completo` AS `nombre_completo`,`alumno_califa_charge_view`.`grupo` AS `grupo`,`alumno_califa_charge_view`.`semestre` AS `semestre`,`alumno_califa_charge_view`.`calificacion_clave` AS `calificacion_clave`,`alumno_califa_charge_view`.`materia` AS `materia`,`alumno_califa_charge_view`.`nombre_abr` AS `nombre_abr`,`alumno_califa_charge_view`.`periodo` AS `periodo`,`alumno_califa_charge_view`.`orden` AS `orden`,`calificaciones_booleanas_view`.`evaluacion` AS `evaluacion`,`calificaciones_booleanas_view`.`calificacion` AS `calificacion`,`calificaciones_booleanas_view`.`faltas` AS `faltas`,`calificaciones_booleanas_view`.`tipo_calificacion` AS `tipo_calificacion` from (`alumno_califa_charge_view` left join `calificaciones_booleanas_view` on((`alumno_califa_charge_view`.`calificacion_clave` = `calificaciones_booleanas_view`.`calificacion_clave`))) where (`calificaciones_booleanas_view`.`tipo_calificacion` = 'A/NA') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumno_califa_charge_view`
--

/*!50001 DROP VIEW IF EXISTS `alumno_califa_charge_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `alumno_califa_charge_view` AS select `alumnos_visible_view`.`numero_lista` AS `numero_lista`,`alumnos_visible_view`.`numero_control` AS `numero_control`,`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,`alumnos_visible_view`.`grupo` AS `grupo`,`alumnos_visible_view`.`semestre` AS `semestre`,`calificaciones_view`.`calificacion_clave` AS `calificacion_clave`,`calificaciones_view`.`materia` AS `materia`,`calificaciones_view`.`nombre_abr` AS `nombre_abr`,`calificaciones_view`.`orden` AS `orden`,`calificaciones_view`.`periodo` AS `periodo`,`calificaciones_view`.`tipo_calificacion` AS `tipo_calificacion` from (`alumnos_visible_view` left join `calificaciones_view` on((`alumnos_visible_view`.`numero_control` = `calificaciones_view`.`clave_alumno`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumno_num_califa_charge_view`
--

/*!50001 DROP VIEW IF EXISTS `alumno_num_califa_charge_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `alumno_num_califa_charge_view` AS select `alumno_califa_charge_view`.`numero_lista` AS `numero_lista`,`alumno_califa_charge_view`.`numero_control` AS `numero_control`,`alumno_califa_charge_view`.`nombre_completo` AS `nombre_completo`,`alumno_califa_charge_view`.`grupo` AS `grupo`,`alumno_califa_charge_view`.`semestre` AS `semestre`,`alumno_califa_charge_view`.`calificacion_clave` AS `calificacion_clave`,`alumno_califa_charge_view`.`materia` AS `materia`,`alumno_califa_charge_view`.`nombre_abr` AS `nombre_abr`,`alumno_califa_charge_view`.`periodo` AS `periodo`,`alumno_califa_charge_view`.`orden` AS `orden`,`calificaciones_numericas_view`.`evaluacion` AS `evaluacion`,`calificaciones_numericas_view`.`calificacion` AS `calificacion`,`calificaciones_numericas_view`.`faltas` AS `faltas`,`alumno_califa_charge_view`.`tipo_calificacion` AS `tipo_calificacion` from (`alumno_califa_charge_view` left join `calificaciones_numericas_view` on((`alumno_califa_charge_view`.`calificacion_clave` = `calificaciones_numericas_view`.`calificacion_clave`))) where (`alumno_califa_charge_view`.`tipo_calificacion` = 'Numérica') */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumno_sem_califa_charge_view`
--

/*!50001 DROP VIEW IF EXISTS `alumno_sem_califa_charge_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `alumno_sem_califa_charge_view` AS select `alumno_califa_charge_view`.`numero_lista` AS `numero_lista`,`alumno_califa_charge_view`.`numero_control` AS `numero_control`,`alumno_califa_charge_view`.`nombre_completo` AS `nombre_completo`,`alumno_califa_charge_view`.`grupo` AS `grupo`,`alumno_califa_charge_view`.`semestre` AS `semestre`,`alumno_califa_charge_view`.`calificacion_clave` AS `calificacion_clave`,`alumno_califa_charge_view`.`materia` AS `materia`,`alumno_califa_charge_view`.`nombre_abr` AS `nombre_abr`,`alumno_califa_charge_view`.`periodo` AS `periodo`,`calificaciones_semestrales`.`calificacion` AS `calificacion`,`alumno_califa_charge_view`.`orden` AS `orden` from (`alumno_califa_charge_view` left join `calificaciones_semestrales` on((`alumno_califa_charge_view`.`calificacion_clave` = `calificaciones_semestrales`.`calificacion_clave`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumnos_contacto_view_specialized`
--

/*!50001 DROP VIEW IF EXISTS `alumnos_contacto_view_specialized`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `alumnos_contacto_view_specialized` AS select `alumnos_contacto`.`numero_control` AS `numero_control`,`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,`alumnos_visible_view`.`fecha_nacimiento` AS `fecha_nacimiento`,`alumnos_visible_view`.`CURP` AS `CURP`,concat(`alumnos_contacto`.`colonia`,' ',`alumnos_contacto`.`domicilio`,', ',`alumnos_contacto`.`localidad`) AS `domicilio`,`alumnos_contacto`.`num_telefonico` AS `num_telefonico`,`tutores`.`nombres` AS `nombres`,`tutores`.`ocupacion` AS `ocupacion`,`tutores`.`telefono` AS `telefono` from ((`alumnos_contacto` left join `alumnos_visible_view` on((`alumnos_visible_view`.`numero_control` = `alumnos_contacto`.`numero_control`))) left join `tutores` on((`tutores`.`numero_control` = `alumnos_contacto`.`numero_control`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumnos_sin_pass`
--

/*!50001 DROP VIEW IF EXISTS `alumnos_sin_pass`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `alumnos_sin_pass` AS select `alumnos`.`numero_control` AS `numero_control`,`alumnos`.`email` AS `email`,`alumnos`.`nombres` AS `nombres` from (`alumnos` left join `webUsers` on((`alumnos`.`numero_control` = `webUsers`.`numero_control`))) where ((`webUsers`.`password` is null) and (`alumnos`.`email` is not null) and (`alumnos`.`email` <> '')) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumnos_view`
--

/*!50001 DROP VIEW IF EXISTS `alumnos_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `alumnos_view` AS select `alumnos`.`numero_control` AS `numero_control`,`alumnos`.`nombres` AS `nombres`,`alumnos`.`apellido_paterno` AS `apellido_paterno`,`alumnos`.`apellido_materno` AS `apellido_materno`,`alumnos`.`grupo` AS `grupo`,`alumnos`.`semestre` AS `semestre`,`alumnos`.`sexo` AS `sexo`,`alumnos`.`fecha_nacimiento` AS `fecha_nacimiento`,`alumnos`.`CURP` AS `CURP`,`alumnos`.`email` AS `email` from `alumnos` order by `alumnos`.`apellido_paterno` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `alumnos_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `alumnos_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `alumnos_visible_view` AS select `alumnos_view`.`numero_control` AS `numero_control`,concat(`alumnos_view`.`nombres`,' ',`alumnos_view`.`apellido_paterno`,' ',`alumnos_view`.`apellido_materno`) AS `nombre_completo`,`alumnos_view`.`grupo` AS `grupo`,`alumnos_view`.`semestre` AS `semestre`,`alumnos_view`.`sexo` AS `sexo`,`alumnos_view`.`fecha_nacimiento` AS `fecha_nacimiento`,`alumnos_view`.`CURP` AS `CURP`,`cebdatabase`.`alumno_no_li`(`alumnos_view`.`grupo`,`alumnos_view`.`numero_control`) AS `numero_lista`,`alumnos_view`.`email` AS `email` from `alumnos_view` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `asignaturas_horario_view`
--

/*!50001 DROP VIEW IF EXISTS `asignaturas_horario_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `asignaturas_horario_view` AS select `horarios_view`.`materia` AS `materia`,`asignaturas_visible_view`.`nombre_abr` AS `nombre_abr`,`horarios_view`.`grupo` AS `grupo`,`asignaturas_visible_view`.`profesor` AS `profesor`,`asignaturas_visible_view`.`nombre_completo` AS `nombre_completo`,`horarios_view`.`hora` AS `hora`,`horarios_view`.`hora_c` AS `hora_c`,`horarios_view`.`dia` AS `dia`,`asignaturas_visible_view`.`aula` AS `aula` from (`horarios_view` join `asignaturas_visible_view` on(((`horarios_view`.`grupo` = `asignaturas_visible_view`.`grupo`) and (`horarios_view`.`materia` = `asignaturas_visible_view`.`materia`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `asignaturas_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `asignaturas_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `asignaturas_visible_view` AS select `asignaturas`.`grupo` AS `grupo`,`asignaturas`.`materia` AS `materia`,`materias`.`nombre_abr` AS `nombre_abr`,`asignaturas`.`profesor` AS `profesor`,`profesores_visible_view`.`nombre_completo` AS `nombre_completo`,`asignaturas`.`aula` AS `aula` from ((`asignaturas` join `materias` on((`asignaturas`.`materia` = `materias`.`clave_materia`))) join `profesores_visible_view` on((`asignaturas`.`profesor` = `profesores_visible_view`.`clave_profesor`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `bajas_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `bajas_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `bajas_visible_view` AS select `alumnos_view`.`numero_control` AS `numero_control`,concat(`alumnos_view`.`nombres`,' ',`alumnos_view`.`apellido_paterno`,' ',`alumnos_view`.`apellido_materno`) AS `nombre_completo`,`alumnos_view`.`grupo` AS `grupo`,`alumnos_view`.`semestre` AS `semestre`,`alumnos_view`.`sexo` AS `sexo`,`alumnos_view`.`fecha_nacimiento` AS `fecha_nacimiento`,`alumnos_view`.`CURP` AS `CURP`,`cebdatabase`.`alumno_no_li`(`alumnos_view`.`grupo`,`alumnos_view`.`numero_control`) AS `numero_lista`,`alumnos_view`.`email` AS `email` from (`bajas` join `alumnos_view` on((`alumnos_view`.`numero_control` = `bajas`.`numero_control`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `calificaciones_booleanas_view`
--

/*!50001 DROP VIEW IF EXISTS `calificaciones_booleanas_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `calificaciones_booleanas_view` AS select `calificaciones_booleanas`.`calificacion_clave` AS `calificacion_clave`,`calificaciones_view`.`clave_alumno` AS `clave_alumno`,`calificaciones_view`.`materia` AS `materia`,`calificaciones_view`.`nombre_abr` AS `nombre_abr`,`calificaciones_view`.`tipo_calificacion` AS `tipo_calificacion`,`calificaciones_view`.`semestre` AS `semestre`,`calificaciones_view`.`periodo` AS `periodo`,`calificaciones_booleanas`.`calificacion` AS `calificacion`,`calificaciones_booleanas`.`evaluacion` AS `evaluacion`,`calificaciones_booleanas`.`faltas` AS `faltas` from (`calificaciones_booleanas` join `calificaciones_view` on((`calificaciones_booleanas`.`calificacion_clave` = `calificaciones_view`.`calificacion_clave`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `calificaciones_numericas_view`
--

/*!50001 DROP VIEW IF EXISTS `calificaciones_numericas_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `calificaciones_numericas_view` AS select `calificaciones_numericas`.`calificacion_clave` AS `calificacion_clave`,`calificaciones`.`clave_alumno` AS `clave_alumno`,`calificaciones`.`materia` AS `materia`,`calificaciones_view`.`nombre_abr` AS `nombre_abr`,`calificaciones`.`semestre` AS `semestre`,`calificaciones`.`periodo` AS `periodo`,`calificaciones_numericas`.`calificacion` AS `calificacion`,`calificaciones_numericas`.`evaluacion` AS `evaluacion`,`calificaciones_numericas`.`faltas` AS `faltas` from ((`calificaciones_numericas` join `calificaciones` on((`calificaciones_numericas`.`calificacion_clave` = `calificaciones`.`calificacion_clave`))) join `calificaciones_view` on((`calificaciones_numericas`.`calificacion_clave` = `calificaciones_view`.`calificacion_clave`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `calificaciones_semestrales_view`
--

/*!50001 DROP VIEW IF EXISTS `calificaciones_semestrales_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `calificaciones_semestrales_view` AS select `calificaciones_semestrales`.`calificacion_clave` AS `calificacion_clave`,`calificaciones`.`clave_alumno` AS `clave_alumno`,`calificaciones`.`semestre` AS `semestre`,`calificaciones`.`periodo` AS `periodo`,`calificaciones`.`materia` AS `materia`,`calificaciones_view`.`nombre_abr` AS `nombre_abr`,`calificaciones_semestrales`.`calificacion` AS `calificacion` from ((`calificaciones_semestrales` join `calificaciones` on((`calificaciones_semestrales`.`calificacion_clave` = `calificaciones`.`calificacion_clave`))) join `calificaciones_view` on((`calificaciones_semestrales`.`calificacion_clave` = `calificaciones_view`.`calificacion_clave`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `calificaciones_view`
--

/*!50001 DROP VIEW IF EXISTS `calificaciones_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `calificaciones_view` AS select `calificaciones`.`calificacion_clave` AS `calificacion_clave`,`calificaciones`.`clave_alumno` AS `clave_alumno`,`planes_estudio_materias`.`orden` AS `orden`,`calificaciones`.`materia` AS `materia`,`materias`.`nombre_abr` AS `nombre_abr`,`calificaciones`.`semestre` AS `semestre`,`materias`.`tipo_calificacion` AS `tipo_calificacion`,`calificaciones`.`periodo` AS `periodo` from ((((`calificaciones` join `materias` on((`calificaciones`.`materia` = `materias`.`clave_materia`))) join `alumnos` on((`calificaciones`.`clave_alumno` = `alumnos`.`numero_control`))) join `plan_grupo` on((`alumnos`.`grupo` = `plan_grupo`.`grupo`))) join `planes_estudio_materias` on(((`plan_grupo`.`plan` = `planes_estudio_materias`.`clave_plan`) and (`calificaciones`.`materia` = `planes_estudio_materias`.`materia`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `dias_clase_view`
--

/*!50001 DROP VIEW IF EXISTS `dias_clase_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `dias_clase_view` AS select `dias_clase`.`dia` AS `dia` from `dias_clase` order by `dias_clase`.`id` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `grupo_asignaturas`
--

/*!50001 DROP VIEW IF EXISTS `grupo_asignaturas`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `grupo_asignaturas` AS select `horarios_view`.`grupo` AS `grupo`,`horarios_view`.`materia` AS `materia`,`horarios_view`.`nombre_abr` AS `nombre_abr`,`asignaturas_visible_view`.`profesor` AS `profesor`,`asignaturas_visible_view`.`nombre_completo` AS `nombre_completo`,`asignaturas_visible_view`.`aula` AS `aula`,`horarios_view`.`dia` AS `dia`,`horarios_view`.`hora` AS `hora`,`horarios_view`.`hora_c` AS `hora_c` from (`horarios_view` join `asignaturas_visible_view` on(((`asignaturas_visible_view`.`grupo` = `horarios_view`.`grupo`) and (`horarios_view`.`materia` = `asignaturas_visible_view`.`materia`)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `grupos_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `grupos_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `grupos_visible_view` AS select `grupos`.`grupo` AS `grupo`,`grupos`.`semestre` AS `semestre`,`grupos`.`turno` AS `turno`,`GRUPO_ALUMNOS_COUNT`(`grupos`.`grupo`) AS `alumnos` from `grupos` order by `grupos`.`grupo` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `horarios_view`
--

/*!50001 DROP VIEW IF EXISTS `horarios_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `horarios_view` AS select `horarios`.`grupo` AS `grupo`,`materias`.`clave_materia` AS `materia`,`materias`.`nombre_abr` AS `nombre_abr`,`horarios`.`dia` AS `dia`,`horarios`.`hora` AS `hora`,`horas_clase_visible_view`.`hora` AS `hora_c` from ((`horarios` join `materias` on((`horarios`.`materia` = `materias`.`clave_materia`))) join `horas_clase_visible_view` on((`horarios`.`hora` = `horas_clase_visible_view`.`orden`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `horas_clase_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `horas_clase_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `horas_clase_visible_view` AS select `horas_clase`.`orden` AS `orden`,`horas_clase`.`turno` AS `turno`,time_format(`horas_clase`.`inicio`,'%H:%i') AS `inicio`,time_format(`horas_clase`.`fin`,'%H:%i') AS `fin`,concat(time_format(`horas_clase`.`inicio`,'%H:%i'),'-',time_format(`horas_clase`.`fin`,'%H:%i')) AS `hora` from `horas_clase` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `materia_profesor_view`
--

/*!50001 DROP VIEW IF EXISTS `materia_profesor_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `materia_profesor_view` AS select `materia_profesor`.`materia` AS `materia`,`materias`.`nombre_abr` AS `nombre_abr`,`materia_profesor`.`profesor` AS `profesor`,`profesores_visible_view`.`nombre_completo` AS `nombre_completo` from ((`materia_profesor` join `materias` on((`materias`.`clave_materia` = `materia_profesor`.`materia`))) join `profesores_visible_view` on((`profesores_visible_view`.`clave_profesor` = `materia_profesor`.`profesor`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `materias_view`
--

/*!50001 DROP VIEW IF EXISTS `materias_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `materias_view` AS select `materias`.`clave_materia` AS `clave_materia`,`materias`.`nombre_materia` AS `nombre_materia`,`materias`.`nombre_abr` AS `nombre_abr`,`materias`.`tipo_calificacion` AS `tipo_calificacion` from `materias` order by `materias`.`nombre_materia` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `plan_grupo_view`
--

/*!50001 DROP VIEW IF EXISTS `plan_grupo_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `plan_grupo_view` AS select `plan_grupo`.`plan` AS `plan`,`planes_estudio`.`descripcion` AS `descripcion`,`plan_grupo`.`grupo` AS `grupo` from (`plan_grupo` join `planes_estudio` on((`planes_estudio`.`clave_plan` = `plan_grupo`.`plan`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `planes_estudio_materias_view`
--

/*!50001 DROP VIEW IF EXISTS `planes_estudio_materias_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `planes_estudio_materias_view` AS select `planes_estudio_materias`.`clave_plan` AS `clave_plan`,`planes_estudio_materias`.`materia` AS `materia`,`materias`.`nombre_abr` AS `nombre_abr`,`materias`.`tipo_calificacion` AS `tipo_calificacion`,`planes_estudio_materias`.`orden` AS `orden` from (`planes_estudio_materias` join `materias` on((`planes_estudio_materias`.`materia` = `materias`.`clave_materia`))) order by `planes_estudio_materias`.`orden` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `planes_estudio_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `planes_estudio_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `planes_estudio_visible_view` AS select `planes_estudio`.`clave_plan` AS `clave_plan`,`planes_estudio`.`descripcion` AS `descripcion`,`planes_estudio`.`semestre` AS `semestre` from `planes_estudio` order by `planes_estudio`.`clave_plan` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `profesoresWebUsers_vieww`
--

/*!50001 DROP VIEW IF EXISTS `profesoresWebUsers_vieww`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `profesoresWebUsers_vieww` AS select `profesores_visible_view`.`clave_profesor` AS `clave_profesor`,`profesores_visible_view`.`nombre_completo` AS `nombre_completo`,`profesores_visible_view`.`email` AS `email`,`profesoresWebUsers`.`password` AS `password` from (`profesores_visible_view` join `profesoresWebUsers` on((`profesores_visible_view`.`clave_profesor` = `profesoresWebUsers`.`clave_profesor`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `profesores_sin_pass`
--

/*!50001 DROP VIEW IF EXISTS `profesores_sin_pass`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `profesores_sin_pass` AS select `profesores`.`clave_profesor` AS `clave_profesor`,`profesores`.`email` AS `email`,`profesores`.`nombres` AS `nombres` from (`profesores` left join `profesoresWebUsers` on((`profesores`.`clave_profesor` = `profesoresWebUsers`.`clave_profesor`))) where ((`profesoresWebUsers`.`password` is null) and (`profesores`.`email` is not null)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `profesores_view`
--

/*!50001 DROP VIEW IF EXISTS `profesores_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `profesores_view` AS select `profesores`.`apellido_paterno` AS `apellido_paterno`,`profesores`.`apellido_materno` AS `apellido_materno`,`profesores`.`nombres` AS `nombres`,`profesores`.`clave_profesor` AS `clave_profesor`,`profesores`.`abr_profesion` AS `abr_profesion`,`profesores`.`telefono` AS `telefono`,`profesores`.`domicilio` AS `domicilio`,`profesores`.`fecha_nacimiento` AS `fecha_nacimiento`,`profesores`.`profesion` AS `profesion`,`profesores`.`CURP` AS `CURP`,`profesores`.`PRESU` AS `PRESU`,`profesores`.`email` AS `email` from `profesores` order by `profesores`.`apellido_paterno` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `profesores_visible_view`
--

/*!50001 DROP VIEW IF EXISTS `profesores_visible_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `profesores_visible_view` AS select `profesores_view`.`clave_profesor` AS `clave_profesor`,concat(`profesores_view`.`nombres`,' ',`profesores_view`.`apellido_paterno`,' ',`profesores_view`.`apellido_materno`) AS `nombre_completo`,`profesores_view`.`profesion` AS `profesion`,`profesores_view`.`abr_profesion` AS `abr_profesion`,`profesores_view`.`CURP` AS `CURP`,`profesores_view`.`telefono` AS `telefono`,`profesores_view`.`domicilio` AS `domicilio`,`profesores_view`.`PRESU` AS `PRESU`,`profesores_view`.`email` AS `email` from `profesores_view` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `semestres_view`
--

/*!50001 DROP VIEW IF EXISTS `semestres_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `semestres_view` AS select `semestres`.`semestre` AS `semestre` from `semestres` order by `semestres`.`semestre` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `webUsers_vieww`
--

/*!50001 DROP VIEW IF EXISTS `webUsers_vieww`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`remote`@`%` SQL SECURITY DEFINER */
/*!50001 VIEW `webUsers_vieww` AS select `alumnos_visible_view`.`numero_control` AS `numero_control`,`alumnos_visible_view`.`nombre_completo` AS `nombre_completo`,`alumnos_visible_view`.`grupo` AS `grupo`,`webUsers`.`password` AS `password`,`alumnos_visible_view`.`email` AS `email` from (`alumnos_visible_view` join `webUsers` on((`alumnos_visible_view`.`numero_control` = `webUsers`.`numero_control`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-01 19:32:53
