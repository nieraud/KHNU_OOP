CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS todos CASCADE;
CREATE TABLE todos (
  uniqueId UUID         NOT NULL      DEFAULT uuid_generate_v4() PRIMARY KEY,
  title    VARCHAR(300) NOT NULL,
  status   VARCHAR(10)  NOT NULL      DEFAULT 'ACTIVE',
  date     TIMESTAMP    NOT NULL      DEFAULT now()
);