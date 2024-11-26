DELIMITER $$
DROP PROCEDURE IF EXISTS loopSearchHistoryInsert $$
CREATE PROCEDURE loopSearchHistoryInsert()
BEGIN
    DECLARE idx INT DEFAULT 0;
    declare keyword VARCHAR(10);
    WHILE idx < 100000
        DO
            set @keyword = SUBSTR(MD5(RAND()), 1, 5);
            INSERT INTO search_history(keyword, member_id, created_at)
            VALUES (@keyword, 1, NOW());
            SET idx = idx + 1;
        END WHILE;
END$$
DELIMITER $$
CALL loopSearchHistoryInsert;