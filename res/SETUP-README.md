
## Mehul Inder Parekh & Heet Manish Kanani (Team  99627)

This is a portfolio management system that allows the user to create different types of portfolios and perform operations on them, such as buying multiple shares, selling shares, getting their values for a certain date, viewing their performance over time, viewing the total investment, and viewing portfolio composition. In addition to this, it also allows the user to save and load an existing portfolio. Apart from this, the application also provides statistics for the specific share. The project supports file formats: `.csv`.

> [IMPORTANT]
>
> Please note that: The user will require an internet connection for the first time when running the application. After adding the share, the share data will be stored in the `data` folder, and the user can add only the share that is present in the `data` folder without an internet connection. To access more shares, the user will require an internet connection. The user will need an internet connection at least once a day so that the shares are updated with the current day.
> The application supports all the Stocks that are available in the `stockData.csv` file.


1. Extract the `res.zip` file.

   Contents of `res` folder include
    - `StockManagementSystem.jar`
    - `stockData.csv`
    - `data` folder
    - `html` zip
    - `portfolios` folder
        - `flexible` folder
        - `inflexible` folder
        - `strategy` folder
    - `config.properties`
    - `classDiagram.png`
    - `DESIGN-README.md`
    - `README.md`
    - `gui.png`
    - `gui2.png`
    - `gui3.png`
    - `SETUP-README.md`

2. Click Ctrl+L. And type `cmd` and hit enter to open command line<br>

## Graphical User Interface
1. In the terminal, type `java -jar StockManagementSystem.jar gui`  to run the
   StockManagementSystem.jar
   file<br>
2. In order to create a flexible portfolio, click on `Create Portfolio` button and enter your
   desired name. e.g. p1
3. You can view stock tickers and their company name. In order to do that click on `View Stocks`
   button, and enter a prefix for e.g. **MS** and then click on search.
4. In order to buy a desired stock with desired quantity on a date, click on buy stock, and
   enter these values.<br>

   **Name** - nvidia
   **Quantity** - 30
   **Date** - 2024-03-08
5. Repeat for two more stocks. <br>
   **Name** - goldman
   **Quantity** - 15
   **Date** - 2019-10-15

   **Name** - verizon
   **Quantity** - 7
   **Date** - 2020-05-15

6. In order to see total value of the portfolio, click on `Total Value` and enter your desired
   date.<br>
   **Date** - 2024-01-18
7. Do same for another date.<br>
   **Date** - 2020-01-17
8. In order to see cost basis/ total investment of the portfolio for a date, click on `Total
   Investment` and enter desired date.<br>
   **Date** - 2024-01-18
9. Do the same for another date.<br>
   **Date** - 2024-03-20
10. In order to invest a fixed amount, click on `Invest Amount` and enter your desired values.
    **Amount** - 5000
    **Name** - Nasdaq
    **Percentage** - 50
    **Date** - 2024-04-02

    **Name** - Alphabet
    **Percentage** - 30
    **Date** - 2022-06-08

    **Name** - Apple
    **Percentage** - 20
    **Date** - 2023-11-09

11. Save the portfolio by clicking on 'Save Portfolio'.
12. You can view your portfolios by clicking on `List Portfolios`.
13. To load a specific portfolio, click on `Load Portfolio` and enter the name of the desired
    portfolio. e.g. p1 which we created earlier.
14. You can view the composition of your portfolio by clicking on `Total Composition`.
15. To sell some shares, click on `Sell Stock` and enter desired values.<br>
    **Name** - Verizon
    **Quantity** - 5
    **Date** - 2022-08-04

    **Name** - Apple
    **Quantity** - 5
    **Date** - 2024-03-08
16. You can view total value and investment same wise as described earlier.
17. Click on `Save Portfolio` to update the portfolio.
18. Click on `Create Portfolio` to create a new portfolio and enter desired name. e.g. p2.
19. Click on `Create Strategy` to have a dollar cost averaging strategy in your portfolio.<br>
    **Start Date** - 2024-01-08<br>
    **End Date** - 2026-12-01<br>
    **Period in days** - 40<br>
    **Amount** - 6000<br>
    **Name** -apple
    **Percentage** - 30

    **Name** - hubspot
    **Percentage** - 50

    **Name** - wayfair
    **Percentage** - 20

20. Click on `Save Portfolio` to save portfolio.
21. Now whenever, you load the portfolio, if you have not invested the desired amount till that
    date for the strategy, the application will buy those stocks.
22. You can load this portfolio by clicking on `Load Portfolio`. This will also load the
    strategy associated with this portfolio.
23. To perform analysis on a stock, click on `Stock Analysis` button
    <br>
    **Stock** - nvidia
24. To check daily price change, click on `Daily Price` button and enter the date.
    <br>
    **Date** - 2024-03-20
25. To check price change over time, click on `Period Price` button and enter the dates.
    **Start Date** - 2019-03-20
    **End Date** - 2024-03-20
26. To check X Day moving average, click on `Moving Average` button.

    **Start Date** - 2024-03-08

    **X** - 30
27. To check crossover dates , click on `Crossover` button.

    **Start Date** - 2023-01-03

    **End Date** - 2024-03-25

28. To check moving crossover dates , click on `Moving Crossover` button.

    **Start Date** - 2023-01-03

    **X** - 30

    **End Date** - 2024-03-22

    **Y** - 20
29. You can also load your own created portfolio file and load it.
30. Create `myPortfolio.csv` and add these values.<br>
    MSFT,2024-03-12,25.3,20.14,450,10,201.4,BUY

    GOOG,2024-03-12,34.09,23.13,500,33,763.29,BUY
31. You can perform all the operations mentioned above on this portfolio.
## Command Line
1. In the terminal, type `java -jar StockManagementSystem.jar text`  to run the
   StockManagementSystem.jar
   file in command line interface<br>

2. In order to create a flexible portfolio, enter 1 as your option and then enter 2 to choose
   normal portfolio,
   add your desired portfolio name.

3. Enter 2 in order to buy a desired stock with desired quantity. <br>
   **Name** - nvidia
   **Quantity** - 30
   **Date** - 2024-03-08
4. Repeat for two more stocks. <br>
   **Name** - goldman
   **Quantity** - 15
   **Date** - 2019-10-15

   **Name** - verizon
   **Quantity** - 7
   **Date** - 2020-05-15
5. In order to see total value of the portfolio, enter 4 and enter your desired date.<br>
   **Date** - 2024-01-18
6. Do same for another date.<br>
   **Date** - 2020-01-17
7. In order to see cost basis/ total investment of the portfolio for a date, enter 6 and enter
   desired date.<br>
   **Date** - 2024-01-18
8. Do the same for another date.<br>
   **Date** - 2024-03-20
9. Save the portfolio by entering 7. <br>
10. Load this portfolio by entering 2 and choosing flexible by entering 2 and enter the
    portfolio name.
11. View performance of the portfolio by entering 7, for a date range.<br>
    **Start Date** - 2020-10-15
    **End Date** - 2023-01-15<br>
    NOTE - It will take some time to load the graph ! Please wait.<br>
12. You can sell a particular stock on a specified date. Enter 3<br>
    **Name** - verizon
    **Quantity** - 3
    **Date** - 2021-05-15
13. You can view the total value, total composition and total investment as before.
14. In order to create a normal portfolio, enter 1 as your option and then enter 1 to choose
    normal portfolio, add your desired
    portfolio
    name.
15. Enter 2 in order to add a desired stock with desired quantity. <br>
    **Name** - tesla
    **Quantity** - 40
16. Repeat for two more stocks. <br>
    **Name** - alphabet
    **Quantity** - 40

    **Name** - goldman
    **Quantity** - 13
17. Save the portfolio by pressing 3.<br>
18. Create another normal portfolio with choice of your name.<br>
19. Add two more stocks. <br>\
    **Name** - nasdaq
    **Quantity** - 41\
    **Name** - amd
    **Quantity** - 9
20. Save this portfolio too and go back to the main menu.<br>
21. You can list all the saved portfolios by choosing 4.<br>
22. You can view a specific normal portfolio by entering 2 and choosing normal portfolio by
    entering 1. e.g. p1<br>
23. You can choose to view the total value or total composition of portfolio p1.<br>
    1. Total Value on date  2024-03-08<br>
    2. Total composition<br>
24. Repeat the above steps for portfolio p2.<br>
    1. Load Portfolio p2<br>
    2. Total Value on date  2024-03-08<br>
    3. Total composition<br>
25. You can create your own `.csv` file and load it. <br>
26. Create `myPortfolio.csv` in excel and add these values.
    MSFT,2024-03-12,25.3,20.14,450,10,201.4,BUY

    GOOG,2024-03-12,34.09,23.13,500,33,763.29,BUY
27. Place that file inside the `portfolios/inflexible` folder provided.<br>
28. Choose option 3 to import this file.Choose portfolio type as normal by entering 1. Enter
    the name of this file.<br>
29. You can see the total value on date  2024-03-08.<br>
30. You can see the total composition.<br>
31. You can also view all the shares that are available to add. Create a new portfolio of any type
    and
    then
    choose 1. <br>
    Choose ticker prefix - MS
32. You can view certain statistics of the stock by entering 4 and entering desired share name.<br>
    **Name** - nvidia
33. To check daily price change. Enter 1.<br>
    **Date** - 2024-03-20
34. To check price change over time. Enter 2.<br>
    **Starting Date** - 2019-03-20<br>
    **End Date** - 2024-03-20
35. To check X day moving average. Enter 3.<br>
    **Date** - 2024-03-08<br>
    **X** - 30
36. To check crossover dates. Enter 4.<br>
    **Starting Date** - 2023-01-03<br>
    **Ending Date** - 2024-03-25
37. To check moving crossover dates. Enter 5.<br>
    **Starting Date** - 2023-01-03<br>
    **X** - 30<br>
    **Ending Date** - 2024-03-22<br>
    **Y** - 20<br>
38. To view performance graph over time. Enter 6.<br>
    **Starting Date** - 2001-03-10<br>
    **Ending Date** - 2024-03-10
39. Go back to main menu by choosing 7 and then on pressing option 6 from the main menu will exit
    the application.<br>
