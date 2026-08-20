DROP TABLE IF EXISTS character_battles;
DROP TABLE IF EXISTS characters;
DROP TABLE IF EXISTS battles;
DROP TABLE IF EXISTS crews;

CREATE TABLE crews (
    name       VARCHAR(100) NOT NULL,
    has_bounty BOOLEAN      NOT NULL,
    ship_name  VARCHAR(100) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE characters (
    character_id INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    age          INT          NOT NULL,
    appearance   VARCHAR(255) NOT NULL,
    powertype    VARCHAR(20)  NOT NULL,
    power        DOUBLE       NOT NULL,
    crew_name    VARCHAR(100),
    CONSTRAINT fk_character_crew FOREIGN KEY (crew_name) REFERENCES crews (name)
);

CREATE TABLE battles (
    battle_id INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(120) NOT NULL,
    location  VARCHAR(120) NOT NULL,
    fought_at TIMESTAMP    NOT NULL,
    winner    VARCHAR(100) NOT NULL
);

CREATE TABLE character_battles (
    character_id INT NOT NULL,
    battle_id    INT NOT NULL,
    PRIMARY KEY (character_id, battle_id),
    CONSTRAINT fk_cb_character FOREIGN KEY (character_id) REFERENCES characters (character_id) ON DELETE CASCADE,
    CONSTRAINT fk_cb_battle    FOREIGN KEY (battle_id)    REFERENCES battles (battle_id)       ON DELETE CASCADE
);
