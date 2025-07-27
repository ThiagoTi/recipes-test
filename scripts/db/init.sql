BEGIN;

DROP INDEX IF EXISTS recipe_ingredients_recipe_id_idx;
DROP INDEX IF EXISTS recipe_ingredients_product_id_idx;
DROP TABLE IF EXISTS public.recipe_ingredients;
DROP TABLE IF EXISTS public.recipes;
DROP INDEX IF EXISTS cart_items_product_id_idx;
DROP INDEX IF EXISTS cart_items_cart_id_idx;
DROP TABLE IF EXISTS public.cart_items;
DROP TABLE IF EXISTS public.products;
DROP TABLE IF EXISTS public.carts;

CREATE TABLE public.carts (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    total_in_cents int NOT NULL
);

CREATE TABLE public.products (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_name TEXT NOT NULL CHECK (product_name <> ''),
    price_in_cents int NOT NULL
);

CREATE TABLE public.cart_items (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    cart_id int NOT NULL REFERENCES public.carts (id),
    product_id int NOT NULL REFERENCES public.products (id)
);
CREATE INDEX cart_items_cart_id_idx ON public.cart_items (cart_id);
CREATE INDEX cart_items_product_id_idx ON public.cart_items (product_id);

CREATE TABLE public.recipes (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_name TEXT NOT NULL CHECK (recipe_name <> '')
);

CREATE TABLE public.recipe_ingredients (
    id int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_id int NOT NULL REFERENCES public.recipes (id),
    product_id int NOT NULL REFERENCES public.products (id),
    quantity int NOT NULL
);
CREATE INDEX recipe_ingredients_recipe_id_idx ON public.recipe_ingredients (recipe_id);
CREATE INDEX recipe_ingredients_product_id_idx ON public.recipe_ingredients (product_id);
ALTER TABLE public.recipe_ingredients ADD CONSTRAINT recipe_ingredients_uk_recipe_product UNIQUE (recipe_id, product_id);


-- start the db insertions

INSERT INTO carts (total_in_cents) VALUES
(3501),
(10502),
(703),
(0);

INSERT INTO products (product_name, price_in_cents) VALUES
('Product 1', 1500),
('Product 2', 1000),
('Product 3', 1100),
('Product 4', 10000),
('Product 5', 1001),
('Product 6', 502),
('Product 7', 703);

INSERT INTO cart_items (cart_id, product_id) VALUES
(1, 1),
(1, 2),
(1, 5),
(2, 4),
(2, 6),
(3, 7);

INSERT INTO recipes (recipe_name) VALUES
('Recipe 1'),
('Recipe 2'),
('Recipe 3');

INSERT INTO recipe_ingredients (recipe_id, product_id, quantity) VALUES
(1, 1, 2),
(1, 2, 1),
(1, 3, 1),
(2, 1, 1),
(2, 2, 1),
(2, 3, 1),
(2, 4, 1),
(3, 1, 3),
(3, 5, 1),
(3, 6, 5);

COMMIT;