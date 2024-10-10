
## Mehul Inder Parekh & Heet Manish Kanani (Team  99627)

This is a portfolio management system that allows the user to create different types of portfolios and perform operations on them, such as buying multiple shares, selling shares, getting their values for a certain date, viewing their performance over time, viewing the total investment, and viewing portfolio composition. In addition to this, it also allows the user to save and load an existing portfolio. Apart from this, the application also provides statistics for the specific share. The project supports file formats: `.csv`.

# Changes - Assignment 6
## FlexiblePortfolioModel Interface 
- The latest update introduces two additional features. One enables users to purchase shares based on a percentage of a fixed amount.
Additionally, the portfolio now accommodates a dollar-cost averaging strategy, empowering users 
  to devise investment plans over a specific timeframe. These enhancements were implemented to 
  align with the new functionalities outlined in the assignment. The buy and sell method in the 
  interface provide validation checks for share quantity provided to them. This change was done 
  to avoid negative share quantity.
## ManagementModel Interface
- The management model also includes support for the aforementioned two features.

## API Interface
- The modification establishes both an interface and an implementation for accessing the Alpha 
Vantage API. This adjustment was made to facilitate support for forthcoming APIs. By implementing an interface, it becomes possible to create multiple variations tailored to different APIs.

# Changes - Assignment 5
## PortfolioModel Interface.
- The update removes the ability to include stocks after the portfolio object has been instantiated. This alteration aims to restrict users from adding stocks to a portfolio after its creation.
- The portfolio now initializes with the shares provided during construction. This update moves the responsibility of loading user shares from the controller to the portfolio interface.
- Additionally, a new method has been added to the existing portfolio to retrieve its performance. This adjustment aligns with the new requirements introduced in the assignment.
- Furthermore, the calculation logic for the total portfolio value has been shifted from the management interface to the portfolio interface. This modification ensures that all operations related to the portfolio are handled within the Portfolio interface rather than in management.

## PortfolioManagementModel Interface
- In the PortfolioManagementModel Interface, the signature of the create portfolio method has been altered to accept a map of shares as a parameter. This adjustment ensures that the portfolio is created only after shares have been added.

## Share
- Within the Share entity, a new field labeled `Buy Value` has been introduced to compute the purchase value of the added share. This addition facilitates users in understanding the total value of their owned shares for a specified share.

## Portfolio Controller
- Updates in the Portfolio Controller involve the addition of new switch cases for handling new portfolio and share operations. These changes were implemented in accordance with the updated requirements outlined in the assignment.



# Project Structure

The project contains main folders:
1. **src**
    1. The directory includes the project's main code.
    2. The `src` contains three packages - model, view, controller and enums.
    3. It also stores the main application file where all three packages run simultaneously.
2. **test**
    1. The directory includes tests for model, view, controller, file IO and end to end integration test.
3. **res**
    1. The directory include `stockData.csv`,`portfolios`,`data`, `classDiagram`, `config.
       properties` and `StockManagementSystem.jar` executable jar.

# Design

The project follows Model, View and Controller (MVC) architecture where each component performs each role as follows:
1. Model: Handles different types of portfolios with different functionality. It handles the logic for calculating performance of portfolio, viewing total value, total investment and total composition. Additionally, it handles the logic of different statistics measures for the stocks.
2. View: Displays the data returned from the controller in an appropriate manner.
3. Controller: Manages the application logic, including input validations, as well as handling file reading and writing operations and delegates the request to model for operation and view to display data on the user console.

## Model

### Management 
The management interface is an expansion of the `PortfolioManagementModel` interface and includes the functionality for the application's features such as normal portfolios, flexible portfolios, and share statistics. This design uses the `Facade Design Pattern`, where operations are called on the Management interface which then calls one of three classes: `PortfolioModel`, `FlexiblePortfolioModel`, or `ShareStatistics`. The management interface forwards the methods to the correct objects and is the primary interface for interaction with the controller.
### PortfolioManagementModel 
The PortfolioManagementModel interface provides various methods such as creating a portfolio, getting total composition and total value. It also allows the user to add various stocks to their respective portfolio and get their value on a certain date as well as the total composition of the portfolio. The design makes use of `Facade Design Pattern` where the operations are  called on the  `PortfolioManagement` class  which will call the `Portfolio` class to perform the actual operations.  This design allows the controller to interact with the single interface.
### PortfolioModel 
The portfolio model provides a set of methods that are performed on the portfolio object and are called inside the management interface. The methods include computing the total composition of the current portfolio which returns a list of shares. Additionally, it allows for addition of new stocks to the portfolio. All these methods are called in the `PortfolioManagement` interface thus following the `Facade Design Pattern`. The shares of the portfolio are of `Share` class that store the relevant stock data and the object is created using builder design pattern.
### FlexiblePortfolioModel 
The interface extends the  features of `PortfolioModel` interface as well as provides a set of methods for flexible portfolio that allows the user to buy and sell stocks on a specified date after creating the portfolio. The interface also allows the user to view their total investment up to a specified date on the portfolio. The interface contains the methods to view the performance of the portfolio for a specified time range.
### ShareStatistics 
The interface is responsible for calculating various statistics for the a specific share. The interface provides features such as calculating whether the stock has gain or loose either for a day or for a time range, crossovers and moving crossovers. It also provides a way to view the performance of the stock for a time period.
### Share
This is a helper class that creates a Share object . The Share saves the following  fields  
`Ticker Symbol , Timestamp (Date) , Open Price , Close Price , Volume , Quantity, Buy value` .

### EmptyPortfolioException
This is a custom exception class that is thrown when a method is called before the object is created.

## View
### ManagementGUIView 
The interface serves as the graphical user interface (GUI) for the application, handling user inputs and outputs. Its primary role is to facilitate interaction with the user, offering support for GUI functionality.
### ManagementView 
The management view extends the portfolio view interface and is responsible for displaying the 
outputs for the new features such as new menu options and the bar graph which visualizes the 
performance of the portfolio. 
### PortfolioView 
The portfolio view is in charge of retrieving and showcasing both the inputs and outputs. Its duties involve gathering user inputs through the Scanner class and presenting them on screen. Furthermore, it is responsible for showing error messages and notifications to the user. The portfolio view also generates a graphical representation, such as a graph, that illustrates the performance of the portfolio or shares.

## Controller

### Features
The features interface acts as the controller responsible for communicating with the gui. Its main 
function is to manage the flow between the model and the GUI. These features assist in validating user input and executing operations by invoking the model's logic.
The interface still replicates the `PortfolioController` in managing the file io operation by 
calling the `FileOperation`.  

### PortfolioController
The portfolio controller is responsible for maintaining the flow of both model and view. The 
controller also handles the input validation for several model functions such as handling 
correct inputs from the user, validating the 
dates and non-negative and non-fractional quantities of the share. The controller performs the file read and write operations through `FileOperation` which helps in not changing the model whenever any file operation changes, hence delegating the request to `FileOperation`. The controller also handles the menu functionalities using switch statements and pointing the input to proper functions assigned.

### FileOperation
This class is responsible for reading and writing the files. The file operation methods return the correct formatted data that is used by model for further computation. The design choice helps the application to support for different file formats. Currently, it reads and writes `.csv` files. It also handles the AlphaVantage API call and returns the formatted data to use in model. The class also has the functionality to store the data that is returned by the API thus helping to get the historical data of the share and reducing the API calls for other methods.

## MVC Application
The application class stores the main method that initializes the model, view and controller object.