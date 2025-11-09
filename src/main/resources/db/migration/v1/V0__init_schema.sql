CREATE SCHEMA IF NOT EXISTS library;
CREATE SCHEMA IF NOT EXISTS library_history;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
SET search_path TO library,library_history,public;