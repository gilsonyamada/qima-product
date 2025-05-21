-- Create sequence for category IDs
CREATE SEQUENCE category_seq START WITH 1 INCREMENT BY 1;

-- Create sequence for product IDs
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;

-- Create categories table
CREATE TABLE categories (
    id BIGINT PRIMARY KEY DEFAULT nextval('category_seq'),
    name VARCHAR(255) NOT NULL,
    parent_id BIGINT,
    CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories (id)
);

-- Create products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY DEFAULT nextval('product_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    available BOOLEAN NOT NULL,
    category_id BIGINT,
    category_path TEXT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories (id)
);
