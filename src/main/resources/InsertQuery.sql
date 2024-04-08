-- Allergener
INSERT INTO allergen(id, name) VALUES (1,'Nuts');
INSERT INTO allergen(id, name) VALUES (2,'Eggs');
INSERT INTO allergen(id, name) VALUES (3,'Gluten');
INSERT INTO allergen(id, name) VALUES (4,'Laktose');
INSERT INTO allergen(id, name) VALUES (5, 'Soy');
INSERT INTO allergen(id, name) VALUES (6, 'Shellfish');
INSERT INTO allergen(id, name) VALUES (7, 'Fish');
INSERT INTO allergen(id, name) VALUES (8, 'Sesame');

-- Kategorier
INSERT INTO category(id, name) VALUES (1, 'Vegetable');
INSERT INTO category(id, name) VALUES (2, 'Fruit');
INSERT INTO category(id, name) VALUES (3, 'Protein');
INSERT INTO category(id, name) VALUES (4, 'Berry');
INSERT INTO category(id, name) VALUES (5, 'Dairy');
INSERT INTO category(id, name) VALUES (6, 'Bakery');
INSERT INTO category(id, name) VALUES (7, 'Condiment');
INSERT INTO category(id, name) VALUES (8, 'Spice');
INSERT INTO category(id, name) VALUES (9, 'Grains');
INSERT INTO category(id, name) VALUES (10, 'Legumes');
INSERT INTO category(id, name) VALUES (11, 'Nuts and Seeds');

-- Ingredienser
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (1, 'Apple', NULL, 2, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (2, 'Orange', NULL, 2, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (3, 'Milk', 4, 5, 'dL');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (4, 'Beef', NULL, 3, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (5, 'Chicken', NULL, 3, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (6, 'Egg', 2, 3, 'qty');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (7, 'Butter', 4, 5, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (8, 'Flour', 3, 6, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (9, 'Bread', 3, 2, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (10, 'Salt', NULL, 8, 'g');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (11, 'Pepper', NULL, 8, 'g');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (12, 'Chillipepper', NULL, 8, 'g');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (13, 'Paprika', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (14, 'Cuecumber', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (15, 'Corn', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (16, 'Onion', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (17, 'Sweet Chilli Sauce', NULL, 7, 'dL');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (18, 'Pepperoni', NULL, 3, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (19, 'Cheese', NULL, 5, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (20, 'Pizza dough', 3, 6, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (21, 'Pizza sauce', NULL, 7, 'dL');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (22, 'Tomato', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (23, 'Lettuce', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit) VALUES (24, 'Potato', NULL, 1, 'kg');
INSERT INTO ingredient(ingredient_id, name, allergen_id, category_id, unit);
VALUES
    (25, 'Carrot', NULL, 1, 'kg'),
    (26, 'Broccoli', NULL, 1, 'kg'),
    (27, 'Spinach', NULL, 1, 'kg'),
    (28, 'Cauliflower', NULL, 1, 'kg'),
    (29, 'Celery', NULL, 1, 'kg'),
    (30, 'Bell Pepper', NULL, 1, 'kg'),
    (31, 'Zucchini', NULL, 1, 'kg'),
    (32, 'Asparagus', NULL, 1, 'kg'),
    (33, 'Mushroom', NULL, 1, 'kg'),
    (34, 'Lemon', NULL, 2, 'qty'),
    (35, 'Lime', NULL, 2, 'qty'),
    (36, 'Grapefruit', NULL, 2, 'qty'),
    (37, 'Pineapple', NULL, 2, 'qty'),
    (38, 'Banana', NULL, 2, 'qty'),
    (39, 'Pear', NULL, 2, 'qty'),
    (40, 'Peach', NULL, 2, 'qty'),
    (41, 'Plum', NULL, 2, 'qty'),
    (42, 'Grapes', NULL, 2, 'kg'),
    (43, 'Cherry', NULL, 2, 'kg'),
    (44, 'Blueberry', NULL, 4, 'kg'),
    (45, 'Raspberry', NULL, 4, 'kg'),
    (46, 'Strawberry', NULL, 4, 'kg'),
    (47, 'Blackberry', NULL, 4, 'kg'),
    (48, 'Yogurt', 4, 5, 'dL'),
    (49, 'Sour cream', 4, 5, 'dL'),
    (50, 'Cream cheese', 4, 5, 'g'),
    (51, 'Whipping cream', 4, 5, 'dL'),
    (52, 'Cottage cheese', 4, 5, 'g'),
    (53, 'Cheddar cheese', NULL, 5, 'kg'),
    (54, 'Mozzarella cheese', NULL, 5, 'kg'),
    (55, 'Parmesan cheese', NULL, 5, 'kg'),
    (56, 'Feta cheese', NULL, 5, 'kg'),
    (57, 'Swiss cheese', NULL, 5, 'kg'),
    (58, 'American cheese', NULL, 5, 'kg'),
    (59, 'Brie cheese', NULL, 5, 'kg'),
    (60, 'Camembert cheese', NULL, 5, 'kg'),
    (61, 'Almonds', 1, 11, 'kg'),
    (62, 'Cashews', 1, 11, 'kg'),
    (63, 'Peanuts', 1, 11, 'kg'),
    (64, 'Hazelnuts', 1, 11, 'kg'),
    (65, 'Walnuts', 1, 11, 'kg'),
    (66, 'Macadamia nuts', 1, 11, 'kg'),
    (67, 'Pistachios', 1, 11, 'kg'),
    (68, 'Sunflower seeds', NULL, 11, 'kg'),
    (69, 'Pumpkin seeds', NULL, 11, 'kg'),
    (70, 'Sesame seeds', 8, 11, 'kg'),
    (71, 'Flax seeds', NULL, 11, 'kg'),
    (72, 'Chia seeds', NULL, 11, 'kg'),
    (73, 'Quinoa', NULL, 9, 'kg'),
    (74, 'Brown rice', NULL, 9, 'kg'),
    (75, 'White rice', NULL, 9, 'kg'),
    (76, 'Barley', NULL, 9, 'kg'),
    (77, 'Oats', NULL, 9, 'kg'),
    (78, 'Salad', NULL, 1, 'kg'),
    (79, 'Cabbage', NULL, 1, 'kg'),
    (80, 'Kale', NULL, 1, 'kg'),
    (81, 'Arugula', NULL, 1, 'kg'),
    (82, 'Romaine lettuce', NULL, 1, 'kg'),
    (83, 'Iceberg lettuce', NULL, 1, 'kg'),
    (84, 'Endive', NULL, 1, 'kg'),
    (85, 'Radish', NULL, 1, 'kg'),
    (86, 'Beetroot', NULL, 1, 'kg'),
    (87, 'Parsnip', NULL, 1, 'kg'),
    (88, 'Turnip', NULL, 1, 'kg'),
    (89, 'Salmon', 7, 3, 'kg'),
    (90, 'Tuna', 7, 3, 'kg'),
    (91, 'Trout', 7, 3, 'kg'),
    (92, 'Halibut', 7, 3, 'kg'),
    (93, 'Cod', 7, 3, 'kg'),
    (94, 'Sardine', 7, 3, 'kg'),
    (95, 'Haddock', 7, 3, 'kg'),
    (96, 'Mackerel', 7, 3, 'kg'),
    (97, 'Herring', 7, 3, 'kg'),
    (98, 'Shrimp', 6, 3, 'kg'),
    (99, 'Crab', 6, 3, 'kg'),
    (100, 'Lobster', 6, 3, 'kg'),
    (101, 'Mussels', 6, 3, 'kg'),
    (102, 'Clams', 6, 3, 'kg'),
    (103, 'Oysters', 6, 3, 'kg'),
    (104, 'Scallops', 6, 3, 'kg'),
    (105, 'Octopus', 6, 3, 'kg'),
    (106, 'Squid', 6, 3, 'kg'),
    (107, 'Anchovy', 7, 3, 'kg'),
    (108, 'Caviar', 6, 3, 'kg'),
    (109, 'Tofu', NULL, 10, 'kg'),
    (110, 'Soybeans', 5, 10, 'kg'),
    (111, 'Mango', NULL, 2, 'qty'),
    (112, 'Catfish', 7, 3, 'kg'),
    (113, 'Ground beef', NULL, 3, 'kg'),
    (114, 'Ground chicken', NULL, 3, 'kg'),
    (115, 'Ground turkey', NULL, 3, 'kg'),
    (116, 'Veggie burger patties', NULL, 10, 'qty'),
    (117, 'Burger buns', NULL, 6, 'qty'),
    (118, 'Lettuce', NULL, 1, 'kg'),
    (119, 'Tomato', NULL, 1, 'kg'),
    (120, 'Onion', NULL, 1, 'kg'),
    (121, 'Pickles', NULL, 1, 'kg'),
    (122, 'Ketchup', NULL, 7, 'dL'),
    (123, 'Mustard', NULL, 7, 'dL'),
    (124, 'Mayonnaise', 2, 7, 'dL'),
    (125, 'Cheese slices', NULL, 5, 'qty'),
    (126, 'Bacon', NULL, 3, 'kg'),
    (127, 'Mushrooms', NULL, 1, 'kg'),
    (128, 'Pasta', NULL, 9, 'kg'),
    (129, 'Spaghetti', NULL, 9, 'kg'),
    (130, 'Meatballs', NULL, 3, 'kg'),
    (131, 'Porridge', NULL, 6, 'dL'),
    (132, 'Garlic', NULL, 1, 'kg'),
    (133, 'Garlic Bread', 3, 6, 'qty'),
    (134, 'Fries', NULL, 6,'kg');
-- Oppskrifter
INSERT INTO recipe(recipe_id, name, instruction) VALUES (1, 'Margherita Pizza', 'Classic Margherita Pizza with tomato sauce, mozzarella, and fresh basil. Roll out pizza dough, add sauce and toppings, and bake.');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (2, 'Classic Grilled Cheese Sandwich', 'A simple and delicious grilled cheese sandwich made with butter, bread, and melted cheese. Butter both sides of bread, add cheese, and grill until golden brown.');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (3, 'Chicken and Paprika Skewers', 'Flavorful Chicken and Paprika Skewers with marinated chicken and paprika. Skewer chicken and paprika alternately, grill, and serve.');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (4, 'Spicy Pepperoni Pizza', 'Spicy Pepperoni Pizza with tomato sauce, pepperoni, and chilli peppers. Roll out pizza dough, add sauce, toppings, and bake.');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (5, 'Egg and Onion Scramble', 'Quick and tasty Egg and Onion Scramble with scrambled eggs and sautéed onions. Scramble eggs, sauté onions, and mix together.');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (6, 'Recipe 6', 'Recipe 6 instruction');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (7, 'Recipe 7', 'Recipe 7 instruction');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (8, 'Recipe 8', 'Recipe 8 instruction');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (9, 'Recipe 9', 'Recipe 9 instruction');
INSERT INTO recipe(recipe_id, name, instruction) VALUES (10, 'Recipe 10', 'Recipe 10 instruction');

-- OppskriftsIngredienser
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 20, 1); -- Pizza Dough
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 21, 0.5); -- Pizza Sauce
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (1, 19, 100); -- Cheese
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 9, 2); -- Bread
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 7, 1); -- Butter
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (2, 19, 50); -- Cheese
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (3, 5, 300); -- Chicken
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (3, 13, 2); -- Paprika

INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 20, 1); -- Pizza Dough
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 21, 0.5); -- Pizza Sauce
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 18, 50); -- Pepperoni
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (4, 12, 2); -- Chilli Pepper
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (5, 6, 3); -- Eggs
INSERT INTO recipe_ingredients(recipe_id, ingredient_id, quantity) VALUES (5, 16, 1); -- Onion

-- Kallender
INSERT INTO day(date, recipe_id) VALUES ('2024-03-12', 3);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-13', 4);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-14', 2);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-15', 1);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-16', 4);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-17', 1);
INSERT INTO day(date, recipe_id) VALUES ('2024-03-18', 3);

-- Lager
INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (1, 3, 2.5); -- Melk
INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (2, 9, 1); -- Brød
INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (3, 15, 0.5); -- Agurk
INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (4, 16, 1); -- Mais
INSERT INTO inventory_ingredient(id, ingredient_id, quantity) VALUES (5, 17, 0.3); -- Sweet Chilli

