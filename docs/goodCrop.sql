DELIMITER $$
DROP PROCEDURE IF EXISTS loopSearchHistoryInsert $$
CREATE PROCEDURE loopSearchHistoryInsert()
BEGIN
    DECLARE idx INT DEFAULT 0;
    declare keyword VARCHAR(10);
    WHILE idx < 10
        DO
            ## set @keyword = SUBSTR(MD5(RAND()), 1, 3);
            set @keyword = FLOOR(RAND()*100);
            INSERT INTO search_history(keyword, member_id, created_at)
            VALUES (@keyword, 1, NOW());
            SET idx = idx + 1;
        END WHILE;
END$$
DELIMITER $$
CALL loopSearchHistoryInsert;