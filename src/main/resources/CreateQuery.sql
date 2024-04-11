CREATE TABLE IF NOT EXISTS allergen(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(60) NOT NULL
        );

CREATE TABLE IF NOT EXISTS category(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(60) NOT NULL
        );

CREATE TABLE IF NOT EXISTS ingredient(
        ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(60) NOT NULL,
        allergen_id INTEGER,
        category_id INTEGER,
        unit VARCHAR(60),
        CONSTRAINT allergen_fk FOREIGN KEY(allergen_id) REFERENCES allergen(id),
        CONSTRAINT category_fk FOREIGN KEY(category_id) REFERENCES category(id)
        );

CREATE TABLE IF NOT EXISTS recipe(
        recipe_id INTEGER PRIMARY KEY AUTOINCREMENT,
        name VARCHAR(60) NOT NULL,
        instruction TEXT
        );

CREATE TABLE IF NOT EXISTS recipe_ingredients(
        recipe_id INTEGER,
        ingredient_id INTEGER,
        quantity DECIMAL(10,2),
        CONSTRAINT RecipeIngredients_pk PRIMARY KEY (recipe_id, ingredient_id),
        CONSTRAINT recipe_fk FOREIGN KEY(recipe_id) REFERENCES recipe(recipe_id),
        CONSTRAINT ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)
        );

CREATE TABLE IF NOT EXISTS day(
        date DATE,
        recipe_id INTEGER,
        CONSTRAINT calendar_pk PRIMARY KEY (date),
        CONSTRAINT calendar_fk FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id)
        );

CREATE TABLE IF NOT EXISTS inventory_ingredient(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        ingredient_id INTEGER,
        quantity DECIMAL(10,2),
        CONSTRAINT inv_ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)
        );

CREATE TABLE IF NOT EXISTS shopping_list_items(
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        ingredient_id INTEGER,
        quantity DECIMAL(10,2),
        CONSTRAINT inv_ingredient_fk FOREIGN KEY(ingredient_id) REFERENCES ingredient(ingredient_id)
        );