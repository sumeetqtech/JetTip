# JetTip
**JetTipApp** is a modern Android application built with Jetpack Compose that helps users calculate tips and split bills seamlessly. This lightweight app is ideal for quick calculations at restaurants or gatherings, ensuring fairness and transparency.

# Features
- Dynamic Tip Calculation: Calculate tip amounts using an adjustable percentage slider.
- Bill Splitting: Split the total bill among any number of people.
- Responsive Design: Real-time updates as inputs change, including split count or tip percentage.
- Modern UI: Designed with Jetpack Compose for a smooth and engaging user experience.

# Getting Started
Follow these instructions to set up and run the project locally.

# Prerequisites
- Android Studio (latest version recommended)
- Minimum SDK: 21
- Gradle version: Ensure compatibility with Jetpack Compose
- Kotlin: 1.9 or higher

# Installation
1. Clone the repository:
      `git clone https://github.com/yourusername/JetTipApp.git`
2. Open the project in Android Studio.
3. Sync Gradle to download dependencies.
4. Build and run the app on an emulator or a connected Android device.

# How to Use
1. Enter the Bill Amount: Input the total bill.
2. Adjust the Tip Percentage: Use the slider to select a tip percentage.
3. Split the Bill: Add or remove people using the split control buttons.
4. View Calculations:
      - Tip Amount: Displays the calculated tip.
      - Total Per Person: Displays the amount each person owes.

# Code Structure
`MainActivity.kt`: Entry point of the app containing the scaffold and state management.
`components/InputField.kt`: Reusable input field composable.
`widgets/RoundIconButton.kt`: Customizable buttons for incrementing and decrementing values.
`util/Utils.kt`: Contains helper functions like calculateTotalTip and calculateTotalPerPerson.

# Tech Stack
- Language: Kotlin
- UI Framework: Jetpack Compose
- Material Design: Material 3 Components
- Architecture: State management with remember and MutableState.

# Future Enhancements
- Dark Mode support.
- Save bill history for future reference.
- Localization for multiple languages.

# Contributing
Contributions are welcome! If you'd like to contribute, please fork the repository and create a pull request with your changes.
1. Fork the repo.
2. Create a branch for your feature: git checkout -b feature-name.
3. Commit your changes: git commit -m "Add a feature".
4. Push to your branch: git push origin feature-name.
5. Open a pull request.

# Contact
- Author: Sumeet Panchal
- Email: sumeetqtech@gmail.com
  

