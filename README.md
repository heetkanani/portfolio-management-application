
# Features
This is a portfolio management system that allows the user to create different types of portfolios and perform operations on them, such as buying multiple shares, selling shares, getting their values for a certain date, viewing their performance over time, viewing the total investment, and viewing portfolio composition. In addition to this, it also allows the user to save and load an existing portfolio. Apart from this, the application also provides statistics for the specific share. The project supports file formats: `.csv`.
## Features for Normal portfolios.

The listed features are available only on normal portfolios.

1. **Add share**
	- The application allows the user to add share of the latest date to their portfolio.
	- For the normal portfolio, until the portfolio is saved, the user can add as many shares as he wants. After saving the portfolio he won't be able to add any share.
	- The application throws error to the user whenever an invalid share quantity or invalid share is added by the user.

## Features for Flexible portfolios.

The listed features are only exclusive for the flexible portfolios.

1. **Total Investment**
	- The application allows the user to determine the cost basis or the total amount invested in the portfolio. This helps the user to keep track of their stocks investments.

2. **Buy Share**
	- In the flexible portfolio, the user can buy share on any specified date. This feature helps to user to buy any share and add it to their portfolio and given the mutable nature of the portfolio, user can buy latest stocks in their portfolios.

3. **Sell Share**
	- Unlike the normal portfolio, the application allows the user to sell their stocks on a specified date

4. **Invest fixed amount**
	- The application allows the user to invest a fixed amount in their portfolio with their 
	  shares determined by percentage of the amount they want to invest.

5. **Dollar Cost Averaging Strategy**
	- The application allows the user to specify a period between given dates and invest a fixed 
	  amount and with every period passed the share user specified would automatically be added
	  the portfolio.
## Features for both Normal and Flexible portfolios.

The listed features are common for both normal and flexible portfolios.

1. **Create Portfolio**
	- The application allows the user to create different types of portfolios NORMAL or FLEXIBLE with a given name.
	- The normal portfolio cannot be modified once created. Contrary to normal, flexible portfolios can be modified after creating.
	-  The user is expected to create the portfolio with unique name, failing to do so will prompt the application to throw an appropriate error to the user.

2. **View all shares**
	- The application allows the user to view list of shares available for them to add it to the portfolio. For ease of user, the user can view stocks either via letter or a prefix of the ticker symbol.

3. **Save Portfolio**
	- The application allows the user to save their portfolio.
	- For the normal portfolio, once saved, the user cannot add any new stocks to the portfolio. If the user tries to add stocks, the application prompts the user by throwing an error. After saving the portfolio, the user can load it and view its total value and composition.
	- For the flexible portfolio, once saved, the user can buy more stocks and even sell them. After saving, the user can buy, sell stocks, view total value and composition as well as view the performance of the portfolio.

4. **Total Value of the portfolio**
	- The application allows the user to get the total value of their portfolio for a date provided by the user.
	-  This will return the total value of the portfolio by getting the value of the shares present in the portfolio at a particular date, either by reading the saved files or calling the AlphaVantage endpoint. The total value can be seen in both types of portfolios.

5. **Total Composition of the portfolio**
	- The application allows the user to get the data of the shares present in the current portfolio.
	- The total composition can be viewed for both types of the portfolios.

6. **Performance of the portfolio**
	- The application allows the user to view the performance of the portfolio for a given time range. The feature visualizes the performance in form of a bar graph where the x axis represent the total value and y axis represents the time range either in days, months or years. The scale for y axis is determined based on the time range provided. For days, it plots the closing value at end of the day. For months, it plots the closing value at end of the month. For years, it plots the closing value at end of the year.
	- The user can view the performance after the portfolio is saved and loaded.

7. **Load Portfolio**
	- The application allows the user to load the existing portfolios saved on the machine and perform operations.
	- For the normal portfolio, the user can view its total composition and total value.
	- For the flexible portfolio, along with viewing its total composition and total value, the user can buy and sell stocks, view total investment and view the portfolio performance for a specified time period.

8. **Import File**
	-  The application allows the user to load their own portfolio file in `csv` format. However, the application performs proper validations to check that the values inside the portfolio are valid and are parseable by the application.

9. **List All Portfolios**  
   - The application allows the user to view all the available flexible and normal portfolios 
   they have.

## Stock Analysis features
The application provides the user, insights on a specific stock so that they can make correct 
buying and selling decisions. 
1. **Stock gain or lose for a day**
   - The application provides information on how much the stock has gained or lose on a 
	 particular given day.
   
2. **Stock gain or lose for a time period**
   - The application provides information on how much the stock has gained or lose for a 
	 particular given time period.
3. **Moving average for X days**
   - The application offers statistical measures of the moving average for a stock over a 
   specified number of days, denoted as X.
4. **Crossover**
   - The application furnishes information regarding the crossover of a specific stock within a designated timeframe, indicating potential 'BUY' or 'SELL' opportunities.
5. **Moving Crossover**
   - The application supplies data on the moving average crossover of a particular stock within a 
   specified time interval, highlighting potential 'BUY' or 'SELL' signals.
6. **Performance of Stock**
   -  The application allows the user to view the performance of the stock for a given time range. 
	  The feature visualizes the performance in form of a bar graph where the x axis represent the total value and y axis represents the time range either in days, months or years. The scale for y axis is determined based on the time range provided. For days, it plots the closing value at end of the day. For months, it plots the closing value at end of the month. For years, it plots the closing value at end of the year.
