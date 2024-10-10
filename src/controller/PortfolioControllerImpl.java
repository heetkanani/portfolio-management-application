package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import enums.CommandLineEnum;
import model.ManagementModel;
import view.ManagementView;

import static controller.ManagementOptions.addStock;
import static controller.ManagementOptions.buyStock;
import static controller.ManagementOptions.calculateInvestFixedAmount;
import static controller.ManagementOptions.calculatedDollarCostAveraging;
import static controller.ManagementOptions.createFlexiblePortfolio;
import static controller.ManagementOptions.createPortfolio;
import static controller.ManagementOptions.getFlexiblePerformanceOfPortfolio;
import static controller.ManagementOptions.getInFlexiblePerformanceOfPortfolio;
import static controller.ManagementOptions.getPerformanceOfStock;
import static controller.ManagementOptions.getTotalComposition;
import static controller.ManagementOptions.getTotalCompositionOfFlexiblePortfolio;
import static controller.ManagementOptions.getTotalInvestment;
import static controller.ManagementOptions.getTotalValue;
import static controller.ManagementOptions.getTotalValueOfFlexiblePortfolio;
import static controller.ManagementOptions.listAllPortfolios;
import static controller.ManagementOptions.loadFlexiblePortfolio;
import static controller.ManagementOptions.loadPortfolio;
import static controller.ManagementOptions.savePortfolio;
import static controller.ManagementOptions.sellStock;
import static controller.ManagementOptions.stockCrossover;
import static controller.ManagementOptions.stockGainOrLossDay;
import static controller.ManagementOptions.stockGainOrLossPeriod;
import static controller.ManagementOptions.stockMovingCrossover;
import static controller.ManagementOptions.stockXDayMovingAverage;
import static controller.ManagementOptions.viewStocks;

/**
 * The controller of the application that implements the PortfolioController interface
 * and its methods, using the view and the model of the application.
 */
public class PortfolioControllerImpl implements PortfolioController {
  private final ManagementModel model;
  private final ManagementView view;
  private final List<Map<String, String>> sharesMap;
  private String portfolioName;
  private boolean isChoice;
  FileOperation file;
  List<String> investStrategies;

  /**
   * The constructor takes in the model, view and initializes isChoice, isSaved and shares.
   *
   * @param model ManagementModel model of the application.
   * @param view  FlexiblePortfolioView view of the application.
   */
  public PortfolioControllerImpl(ManagementModel model, ManagementView view) {
    this.model = model;
    this.view = view;
    this.isChoice = true;
    this.sharesMap = new ArrayList<>();
    file = new FileOperation();
    this.investStrategies = new ArrayList<>();
  }

  private void executePortfolioMenuOperations() throws IOException {
    String choice;
    PortfolioManagementEnum[] operations = PortfolioManagementEnum.values();
    try {
      while (isChoice) {
        this.view.portfolioMenuOptions();
        PortfolioManagementEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (PortfolioManagementEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
            break;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. " +
                  "Please select from the above menu options.");
          executePortfolioMenuOperations();
        }
        switch (selectedOperation) {
          case CREATE:
            executeChoosePortfolioTypeOptions();
            break;
          case LOAD:
            executeLoadPortfolioTypeOptions();
            break;
          case IMPORT:
            executeLoadPortfolioTypeOptions();
            break;
          case STOCK_TREND:
            executeCompanyNameOption();
            break;
          case LIST:
            listAllPortfolios(this.view, file);
            break;
          case EXIT:
            this.view.setInputMessage("Exiting the application...");
            isChoice = false;
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executePortfolioMenuOperations();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }


  private void executeLoadedPortfolioOptions() throws IOException {
    String choice;
    PortfolioLoadOperationsEnum[] operations = PortfolioLoadOperationsEnum.values();
    try {
      while (isChoice) {
        this.view.loadedPortfolioOptions();
        PortfolioLoadOperationsEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (PortfolioLoadOperationsEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
            break;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. " +
                  "Please select from the above menu options.");
          executeLoadedPortfolioOptions();
        }
        switch (selectedOperation) {
          case TOTAL_COMPOSITION:
            getTotalComposition(this.view, this.model);
            break;
          case TOTAL_VALUE:
            getTotalValue(this.view, file, this.model);
            break;
          case PERFORMANCE:
            getInFlexiblePerformanceOfPortfolio(this.view, file, this.model, portfolioName);
            break;
          case EXIT:
            executePortfolioMenuOperations();
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeLoadedPortfolioOptions();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  private void executeCompanyNameOption() throws IOException {
    this.view.setInputMessage("Enter the name of the company: ");
    String stockName = this.view.setInputString();
    try {
      file.getShareByCompanyName(stockName);
      executeStockTrendOptions(stockName);
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  private void executeStockTrendOptions(String stockName) throws IOException {
    String choice;
    StockTrendEnum[] operations = StockTrendEnum.values();
    try {
      while (isChoice) {
        this.view.stockTrendMenuOptions(stockName);
        StockTrendEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (StockTrendEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeStockTrendOptions(stockName);
        }
        switch (selectedOperation) {
          case GAIN_LOSE_DAY:
            stockGainOrLossDay(this.view, file, this.model, stockName);
            break;
          case GAIN_LOSE_PERIOD:
            stockGainOrLossPeriod(this.view, file, this.model, stockName);
            break;
          case X_DAY_MOVING_AVERAGE:
            stockXDayMovingAverage(this.view, file, this.model, stockName);
            break;
          case CROSSOVER:
            stockCrossover(this.view, file, this.model, stockName);
            break;
          case MOVING_CROSSOVER:
            stockMovingCrossover(this.view, file, this.model, stockName);
            break;
          case PERFORMANCE:
            getPerformanceOfStock(this.view, file, this.model, stockName);
            break;
          case EXIT:
            executePortfolioMenuOperations();
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeStockTrendOptions(stockName);
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
      executeStockTrendOptions(stockName);
    }
  }

  private void executeStockMenuOperations() throws IOException {
    String choice;
    PortfolioOperationsEnum[] operations = PortfolioOperationsEnum.values();
    try {
      while (isChoice) {
        this.view.stockMenuOptions();
        PortfolioOperationsEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (PortfolioOperationsEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeStockMenuOperations();
        }
        switch (selectedOperation) {
          case VIEW_ALL_STOCKS:
            viewStocks(this.view, file);
            break;
          case ADD:
            addStock(this.view, file, this.sharesMap);
            break;
          case SAVE:
            try {
              savePortfolio(portfolioName, sharesMap, this.view, file, this.model,
                      this.model.getStrategy(), PortfolioTypeEnum.NORMAL);
              sharesMap.clear();
              executePortfolioMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case EXIT:
            executePortfolioMenuOperations();
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeStockMenuOperations();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  private void executeChoosePortfolioTypeOptions() throws IOException {
    String choice;
    PortfolioTypeEnum[] operations = PortfolioTypeEnum.values();
    try {
      while (isChoice) {
        this.view.portfolioOptions();
        PortfolioTypeEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (PortfolioTypeEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeChoosePortfolioTypeOptions();
        }
        switch (selectedOperation) {
          case NORMAL:
            try {
              this.portfolioName = createPortfolio(this.view, this.portfolioName, file);
              executeStockMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case FLEXIBLE:
            try {
              this.portfolioName = createFlexiblePortfolio(this.view, file, this.model,
                      this.portfolioName);
              executeFlexibleStockMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeChoosePortfolioTypeOptions();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  private void executeFlexibleStockMenuOperations() throws IOException {
    String choice;
    FlexiblePortfolioOperationsEnum[] operations = FlexiblePortfolioOperationsEnum.values();
    try {
      while (isChoice) {
        this.view.flexiblePortfolioOptions();
        FlexiblePortfolioOperationsEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (FlexiblePortfolioOperationsEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeFlexibleStockMenuOperations();
        }
        switch (selectedOperation) {
          case VIEW_ALL_STOCKS:
            viewStocks(view, file);
            break;
          case BUY:
            buyStock(this.view, file, this.model, portfolioName);
            break;
          case SELL:
            sellStock(this.view, file, this.model);
            break;
          case TOTAL_VALUE:
            getTotalValueOfFlexiblePortfolio(this.view, file, this.model, portfolioName);
            break;
          case TOTAL_COMPOSITION:
            getTotalCompositionOfFlexiblePortfolio(this.view, this.model);
            break;
          case TOTAL_INVESTMENT:
            getTotalInvestment(this.view, file, this.model);
            break;
          case SAVE:
            try {
              savePortfolio(portfolioName, this.sharesMap, this.view, file,
                      this.model, this.model.getStrategy(), PortfolioTypeEnum.FLEXIBLE);
              executePortfolioMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case INVEST:
            try {
              calculateInvestFixedAmount(this.file, this.view, this.model);
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case STRATEGY:
            try {
              calculatedDollarCostAveraging(this.file, this.view, this.model);
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case EXIT:
            executePortfolioMenuOperations();
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeFlexibleStockMenuOperations();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }

  }

  private void executeLoadedFlexibleStockMenuOperations() throws IOException {
    String choice;
    FlexiblePortfolioLoadOperationsEnum[] operations = FlexiblePortfolioLoadOperationsEnum.values();
    try {
      while (isChoice) {
        this.view.loadedFlexiblePortfolioOptions();
        FlexiblePortfolioLoadOperationsEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (FlexiblePortfolioLoadOperationsEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeLoadedFlexibleStockMenuOperations();
        }
        switch (selectedOperation) {
          case VIEW_ALL_STOCKS:
            viewStocks(this.view, file);
            break;
          case BUY:
            buyStock(this.view, file, this.model, portfolioName);
            break;
          case SELL:
            sellStock(this.view, file, this.model);
            break;
          case TOTAL_VALUE:
            getTotalValueOfFlexiblePortfolio(this.view, file, this.model, portfolioName);
            break;
          case TOTAL_COMPOSITION:
            getTotalCompositionOfFlexiblePortfolio(this.view, this.model);
            break;
          case TOTAL_INVESTMENT:
            getTotalInvestment(this.view, file, this.model);
            break;
          case PERFORMANCE:
            getFlexiblePerformanceOfPortfolio(this.view, file, this.model, this.portfolioName);
            break;
          case SAVE:
            try {
              savePortfolio(portfolioName, this.sharesMap, this.view, file,
                      this.model, this.model.getStrategy(), PortfolioTypeEnum.FLEXIBLE);
              executePortfolioMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case INVEST:
            try {
              calculateInvestFixedAmount(this.file, this.view, this.model);
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case STRATEGY:
            try {
              calculatedDollarCostAveraging(this.file, this.view, this.model);
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case EXIT:
            executePortfolioMenuOperations();
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeLoadedFlexibleStockMenuOperations();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  private void executeLoadPortfolioTypeOptions() throws IOException {
    String choice;
    PortfolioTypeEnum[] operations = PortfolioTypeEnum.values();
    try {
      while (isChoice) {
        this.view.portfolioOptions();
        PortfolioTypeEnum selectedOperation = null;
        choice = this.view.setInputString();
        for (PortfolioTypeEnum operation : operations) {
          if (operation.getChoice().equals(choice)) {
            selectedOperation = operation;
          }
        }
        if (selectedOperation == null) {
          this.view.setErrorMessage("Invalid choice. Please select from the above menu options.");
          executeLoadPortfolioTypeOptions();
        }
        switch (selectedOperation) {
          case NORMAL:
            try {
              this.portfolioName = loadPortfolio(this.view, this.model, file,
                      "Enter the name of " +
                              "the " +
                              "portfolio: ");
              executeLoadedPortfolioOptions();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          case FLEXIBLE:
            try {
              this.portfolioName = loadFlexiblePortfolio(this.view, file, this.model,
                      "Enter your portfolio " +
                              "name: ");
              executeLoadedFlexibleStockMenuOperations();
            } catch (Exception e) {
              this.view.setErrorMessage(e.getMessage());
            }
            break;
          default:
            this.view.setErrorMessage("Invalid choice. " +
                    "Please select from the above menu options.");
            executeChoosePortfolioTypeOptions();
        }
      }
    } catch (Exception e) {
      this.view.setErrorMessage(e.getMessage());
    }
  }

  /**
   * The method runs the application.
   *
   * @throws IOException If an I/O occurs.
   */
  @Override
  public void run() throws IOException {
    this.view.setInputMessage(CommandLineEnum.BOLD.getStyle() +
            CommandLineEnum.ITALIC.getStyle() +
            "\n------ WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION ------\n"
            + CommandLineEnum.RESET.getStyle());
    executePortfolioMenuOperations();
  }

}
 