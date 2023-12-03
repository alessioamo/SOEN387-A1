

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `orders` (
  `orderId` INTEGER PRIMARY KEY NOT NULL,
  `shippingAddress` varchar(200) DEFAULT NULL,
  `trackingNumber` int DEFAULT NULL,
  `productsInCart` json DEFAULT NULL,
  `datePlaced` date DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `totalCost` double DEFAULT NULL,
  CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
);

/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (12,NULL,NULL,'[{\"id\": 4, \"sku\": \"4\", \"name\": \"Romeo And Juliet\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 7, \"sku\": \"7\", \"name\": \"Gaming Mouse\", \"vendor\": \"Razor\", \"quantity\": 1}]','2023-11-07',1,107),(13,NULL,NULL,'[{\"id\": 4, \"sku\": \"4\", \"name\": \"Romeo And Juliet\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 7, \"sku\": \"7\", \"name\": \"Gaming Mouse\", \"vendor\": \"Razor\", \"quantity\": 1}, {\"id\": 8, \"sku\": \"8\", \"name\": \"Airpods\", \"vendor\": \"Apple\", \"quantity\": 1}]','2023-11-07',1,187),(14,NULL,NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"vendor\": \"Indigo\", \"quantity\": 3}]','2023-11-07',1,33),(15,NULL,NULL,NULL,NULL,1,NULL),(16,NULL,NULL,'[]','2023-11-07',2,0),(17,'Boisghur',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',2,23),(18,'aloha',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',2,23),(19,'sda',NULL,NULL,'2023-11-08',2,32),(20,'hjbj',NULL,NULL,'2023-11-08',2,44),(21,'aaa',NULL,'[{\"id\": 3, \"sku\": \"3\", \"name\": \"The Hunger Games\", \"price\": \"16.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 4, \"sku\": \"4\", \"name\": \"Romeo And Juliet\", \"price\": \"7.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',2,23),(22,'asdf',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',2,23),(23,'asdf',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',2,23),(24,'asdf',25,'[{\"id\": 25, \"sku\": \"25\", \"name\": \"ahhhh\", \"price\": \"0.0\", \"vendor\": \"null\", \"quantity\": 1}]','2023-11-08',1,0),(25,'test home',25,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 3, \"sku\": \"3\", \"name\": \"The Hunger Games\", \"price\": \"16.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 4, \"sku\": \"4\", \"name\": \"Romeo And Juliet\", \"price\": \"7.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',1,46),(26,'da',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 4, \"sku\": \"4\", \"name\": \"Romeo And Juliet\", \"price\": \"7.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 8, \"sku\": \"8\", \"name\": \"Airpods\", \"price\": \"80.0\", \"vendor\": \"Apple\", \"quantity\": 1}]','2023-11-08',1,98),(27,'dsf',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}, {\"id\": 3, \"sku\": \"3\", \"name\": \"The Hunger Games\", \"price\": \"16.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',1,39),(28,'123',NULL,'[{\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-08',1,12),(29,'job',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 3}]','2023-11-08',2,33),(35,'adsfvsdaadsfasdf',NULL,'[{\"id\": 3, \"sku\": \"3\", \"name\": \"The Hunger Games\", \"price\": \"16.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-21',2,16),(36,'baseball',NULL,'[{\"id\": 15, \"sku\": \"15\", \"name\": \"Baseball Bat\", \"price\": \"60.0\", \"vendor\": \"Sports Expert\", \"quantity\": 1}]','2023-11-21',2,60),(37,'famer',NULL,'[{\"id\": 7, \"sku\": \"7\", \"name\": \"Gaming Mouse\", \"price\": \"100.0\", \"vendor\": \"Razor\", \"quantity\": 1}]','2023-11-21',2,100),(38,'pdf',NULL,'[{\"id\": 3, \"sku\": \"3\", \"name\": \"The Hunger Games\", \"price\": \"16.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-21',2,16),(39,'dash',NULL,'[{\"id\": 2, \"sku\": \"2\", \"name\": \"To Kill A Mockingbird\", \"price\": \"12.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-23',2,12),(40,'fsdv',NULL,'[{\"id\": 1, \"sku\": \"1\", \"name\": \"The Catcher In The Rye\", \"price\": \"11.0\", \"vendor\": \"Indigo\", \"quantity\": 1}]','2023-11-23',0,11);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `products` (
  `id` INTEGER PRIMARY KEY NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `vendor` varchar(45) DEFAULT NULL,
  `urlSlug` varchar(100) DEFAULT NULL,
  `sku` varchar(45) NOT NULL
);

/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'The Catcher In The Rye','books',11,1,'the-catcher-in-the-rye.jpg','The Catcher in the Rye is an American novel by J. D. Salinger that was partially published in serial form 1945–46 before being novelized in 1951. Originally intended for adults, it is often read by adolescents for its themes of angst and alienation, and as a critique of superficiality in society.','Indigo','the-catcher-in-the-rye','1'),(2,'To Kill A Mockingbird','books',12,1,'to-kill-a-mockingbird.jpg','To Kill a Mockingbird is a novel by the American author Harper Lee. It was published in 1960 and was instantly successful. In the United States, it is widely read in high schools and middle schools.','Indigo','to-kill-a-mockingbird','2'),(3,'The Hunger Games','books',16,1,'the-hunger-games.jpg','In what was once North America, the Capitol of Panem maintains its hold on its 12 districts by forcing them each to select a boy and a girl, called Tributes, to compete in a nationally televised event called the Hunger Games. Every citizen must watch as the youths fight to the death until only one remains.','Indigo','the-hunger-games','3'),(4,'Romeo And Juliet','books',7,1,'romeo-and-juliet.jpg','Romeo and Juliet is a tragedy written by William Shakespeare early in his career about the romance between two Italian youths from feuding families.','Indigo','romeo-and-juliet','4'),(5,'Harry Potter And The Deathly Hollows','books',15,1,'harry-potter.jpg','Harry Potter and the Deathly Hallows is a fantasy novel written by British author J. K. Rowling and the seventh and final novel in the Harry Potter series.','Indigo','harry-potter-and-the-deathly-hollows','5'),(6,'Gaming Headset','electronics',80,1,'gaming-headset.jpg','A headset used for gaming, includes LED lighting.','Razor','gaming-headset','6'),(7,'Gaming Mouse','electronics',100,1,'gaming-mouse.jpg','A mouse used for gaming, includes LED lighting.','Razor','gaming-mouse','7'),(8,'Airpods','electronics',80,1,'airpods.jpg','Wireless earphones created by Apple.','Apple','airpods','8'),(9,'iPhone 13','electronics',700,1,'iphone-13.jpg','The new iPhone 13 from Apple.','Apple','iphone-13','9'),(10,'Macbook','electronics',1200,1,'macbook.jpg','The new Macbook from Apple.','Apple','macbook','10'),(11,'Soccer Ball','sports',30,1,'soccer-ball.jpg','A classic soccer ball. Pump not included.','Sports Expert','soccer-ball','11'),(12,'Soccer Cleats','sports',120,1,'soccer-cleats.jpg','High end leather soccer cleats.','Sports Expert','soccer-cleats','12'),(13,'Hockey Stick','sports',90,1,'hockey-stick.jpg','High quality wooden hockey stick.','Sports Expert','hockey-stick','13'),(14,'Football','sports',25,1,'football.jpg','A classic NFL style football. Pump not included.','Sports Expert','football','14'),(15,'Baseball Bat','sports',60,1,'baseball-bat.jpg','Sturdy intermediate baseball bat.','Sports Expert','baseball-bat','15'),(16,'Fossil Watch','jewelry',100,1,'fossil-watch.jpg','High-end watch from Fossil.','Fossil','fossil-watch','16'),(17,'Gold Earrings','jewelry',160,1,'gold-earrings.jpg','Gold stud earrings.','Pandora','gold-earrings','17'),(18,'Diamond Ring','jewelry',3000,1,'diamond-ring.jpg','Diamond ring, perfect for proposals.','Pandora','diamond-ring','18'),(19,'Pearl Necklace','jewelry',1150,1,'pearl-necklace.jpg','Hand-crafted pearl necklace.','Pandora','pearl-necklace','19'),(20,'Hoop Earrings','jewelry',85,1,'hoop-earrings.jpg','wwww','Pandora','hoop-earrings','20');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `users` (
  `id` INTEGER PRIMARY KEY NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `isAdmin` tinyint DEFAULT NULL
);

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'anon',NULL,0),(1,'admin','secret',1),(2,'aaa','aaaa',0),(4,'','boisghur',0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
