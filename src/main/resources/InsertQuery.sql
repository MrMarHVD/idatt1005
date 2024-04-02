-- Allergener
INSERT INTO allergen(id, name) VALUES (1,'Nuts');
INSERT INTO allergen(id, name) VALUES (2,'Eggs');
INSERT INTO allergen(id, name) VALUES (3,'Gluten');
INSERT INTO allergen(id, name) VALUES (4,'Laktose');

-- Kategorier
INSERT INTO category(id, name) VALUES (1, 'Vegetable');
INSERT INTO category(id, name) VALUES (2, 'Fruit');
INSERT INTO category(id, name) VALUES (3, 'Protein');
INSERT INTO category(id, name) VALUES (4, 'Berry');
INSERT INTO category(id, name) VALUES (5, 'Dairy');
INSERT INTO category(id, name) VALUES (6, 'Bakery');
INSERT INTO category(id, name) VALUES (7, 'Condiment');
INSERT INTO category(id, name) VALUES (8, 'Spice');

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

