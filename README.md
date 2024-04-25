# Plate 1.0

## Description
Plate is a meal planning application which can be used for a variety of purposes. It provides a few central features including inventory, shopping list, cookbook and calendar most notably. By using each of these views in the application, the user can create, edit and discover new recipes, plan their dinners for the coming week and keep track of the ingredients they want or need to that end through the shopping list.

## Installation

### Prerequisites
Before running the program you need to have Maven and JavaFX installed on your machine. For information on how to install maven click here: https://maven.apache.org/install.html and for javafx click here: https://openjfx.io/openjfx-docs/#introduction

### Using Maven
With Maven and JavaFX installed, you can run the following command while in the project root directory to build the application:

```
mvn clean install compile
```
Then run this command to launch the application:
```
mvn javafx:run
```

### Using .JAR-file
Firstly, to package the application to a .JAR file, run the following command while in the root directory:
```
mvn clean package
```
Then, run the following command, replace /path/to/javafx-sdk/lib with the actual path to your JavaFX lib folder:
```
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.media,javafx.swing -jar target/idatt1005plate-1.0-SNAPSHOT-jar-with-dependencies.jar
```
## Usage

To use the application, make use of the toolbar at the top of the interface to switch between different views after launching. There are five main views immediately available: Home (Your Week), Cookbook, Inventory, Shopping List and Settings. The calendar in the home tab can be used to edit the recipes planned for each weekday as well as send missing ingredients to the shopping list. The cookbook shows a list of all the recipes currently available, the inventory shows all the ingredients you have at the moment, and the shopping list is just that: a shopping list. This version of the app only has two settings: Dark Mode and Vegetarian Mode. Dark Mode speaks for itself and Vegetarian Mode hides non-vegetarian ingredients and recipes. There's another main view available in the app as well which shows an overview of each recipe. It can be accessed by clicking on a recipe in the cookbook, or in the calendar. This is where you see detailed information about each recipe.

For more detailed information on usage with visuals, consult the wiki [here](https://gitlab.stud.idi.ntnu.no/idatt_1005_2024_group13/purchase-planner/-/wikis/User-Manual).

## Contributing
If you want to improve Plate, we'd be delighted to see you fork it and take it to the next level. :)

## Acknowledgements
We'd like to thank the lecturers in IDATT1005 for their insights and our student assistant Vegard for his incontrovertible dedication to our team meetings throughout the project runtime. Finally, we'd like to thank our parents for raising us to become proper software developers one day.

## Project status
The project is currently no longer under development, but maybe it will be improved someday in the future. Stay tuned for updates.