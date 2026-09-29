# La Dispensa

La Dispensa is an Android Smart Pantry Manager application developed in Java.

The application helps users keep track of ingredients available in their kitchen and discover recipes that can be prepared using those ingredients. The aim is to make meal planning simpler while encouraging users to make better use of food they already have.

## Features

- Add ingredients to a personal pantry
- Store ingredient name, category, quantity and unit
- Edit existing pantry ingredients
- Delete ingredients with confirmation
- Persistent pantry storage
- View recipes that match available pantry ingredients
- View recipe ingredients and preparation instructions
- Save user cooking preferences
- Vegetarian recipe preference
- Cooking experience preference
- Navigation between the main application screens

## Database

La Dispensa uses SQLite for local data storage.

SQLite was selected because it provides persistent structured storage directly on an Android device without requiring an internet connection or external database server.

The database stores pantry ingredients and recipe information. Pantry ingredient records include the ingredient name, category, quantity and unit.

## Recipe Matching

The application compares the ingredients stored in the user's pantry with the ingredients required by each recipe.

A recipe becomes available when all of its required ingredients are present in the pantry.

## User Preferences

La Dispensa uses SharedPreferences to store user settings such as:

- Vegetarian recipes only
- Cooking experience level

These preferences remain available after the application is closed and reopened.

## Technologies Used

- Java
- Android Studio
- XML
- SQLite
- SharedPreferences
- RecyclerView
- Git and GitHub

## How to Run the Application

1. Clone or download the La Dispensa repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync and download the required dependencies.
4. Connect an Android device with USB debugging enabled or start an Android emulator.
5. Build and run the application from Android Studio.
6. Add ingredients to the pantry and open the Recipes screen to view matching recipes.

## Project Purpose

La Dispensa was developed as a Smart Pantry Manager mobile application project. It demonstrates Android interface development, local data persistence, CRUD operations, recipe matching, user preferences and version control.

## Author

Mikaeel Maddocks