CREATE TABLE IF NOT EXISTS public."User"
(
    id VARCHAR(255) PRIMARY KEY,
    "createdAt" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" TIMESTAMP,
    "deletedAt" TIMESTAMP,
    "firstName" VARCHAR(255),
    "lastName" VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);


