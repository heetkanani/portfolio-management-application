package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import controller.Features;

/**
 * This is the Management Graphical User Interface View that extends the JFrame and implements
 * the Management GUI view.
 */
public class ManagementGUIViewImpl extends JFrame implements ManagementGUIView {
  private JPanel mainPanel;
  private JPanel mainMenuPanel;
  private JPanel subMenuPanel;
  private JPanel dynamicPanel;
  private JButton createPortfolioButton;
  private JButton loadPortfolioButton;
  private JButton importPortfolioButton;
  private JButton stockAnalysisButton;
  private JButton listPortfoliosButton;
  private JButton dollarCostStrategy;

  private JButton viewStocks;
  private JButton buyStock;
  private JButton sellStock;
  private JButton totalValue;
  private JButton totalComposition;
  private JButton investFixedAmount;
  private JButton totalInvestment;
  private JButton save;
  private JButton back;
  private JScrollPane scrollPane;
  private JButton dailyStockPriceChange;
  private JButton periodStockPriceChange;
  private JButton xDayMovingAverage;
  private JButton crossOver;
  private JButton movingCrossOver;
  private DefaultTableModel model;
  private JTextField portfolioNameField;

  private JButton generateButton(String buttonName, JPanel panel) {
    JButton button = new JButton(buttonName);
    button.setFont(getStyle());
    button.setPreferredSize(getButton());
    button.setBackground(Color.darkGray);
    button.setForeground(Color.white);
    button.setBorder(BorderFactory.createEmptyBorder());
    button.setFocusable(false);
    panel.add(button);
    return button;
  }

  private JLabel generateLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(getStyle());
    return label;
  }

  private Dimension getButton() {
    return new Dimension(140, 30);
  }

  private Font getStyle() {
    return new Font("Arial", Font.BOLD, 14);
  }

  private void invokeMainMenuEvents(Features features) {
    createPortfolioButton.addActionListener(e -> createPortfolioEvent(features));
    loadPortfolioButton.addActionListener(e -> loadPortfolioEvent(features));
    importPortfolioButton.addActionListener(e -> loadPortfolioEvent(features));
    listPortfoliosButton.addActionListener(e -> listPortfoliosEvents(features));
    stockAnalysisButton.addActionListener(e -> stockAnalysisEvents(features));
  }

  private void listPortfoliosEvents(Features features) {
    mainPanel.removeAll();
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "List all portfolios ");
    resetDynamicPanel();
    JPanel tableDataPanel = new JPanel(new FlowLayout());
    generateDataTable(features.getFlexiblePortfolios(), "Flexible Portfolios",
            tableDataPanel);
    generateDataTable(features.getAllStrategies(), "Strategies",
            tableDataPanel);
    dynamicPanel.add(tableDataPanel, BorderLayout.CENTER);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private JTextField generateTextField() {
    JTextField textField = new JTextField(20);
    textField.setFont(getStyle());
    return textField;
  }

  private void loadPortfolioEvent(Features features) {
    mainPanel.setBorder(null);
    MainMenuTextFieldPanel mainMenuTextFieldPanel = getPanel("Load Portfolio",
            "Enter the name of the portfolio: ",
            "Load");
    panelInitiator(mainMenuTextFieldPanel.fieldPanel, BorderLayout.CENTER);
    mainMenuTextFieldPanel.submit.addActionListener(e -> {
      boolean isPortfolioExists = validateLoadPortfolioName(features,
              mainMenuTextFieldPanel.errorLabel, portfolioNameField);
      if (isPortfolioExists) {
        createPortfolioMenuOptions(features);
        subMenuPanel.setVisible(true);
        mainMenuTextFieldPanel.fieldPanel.removeAll();
        features.loadPortfolio(portfolioNameField.getText(), mainMenuTextFieldPanel.errorLabel);
        mainMenuPanel.setVisible(false);
        JLabel label = generateLabel("");
        setSuccessMessage("Portfolio " + portfolioNameField.getText() + " loaded" +
                        " successfully ! ",
                label);
        mainPanel.add(label,
                BorderLayout.NORTH);
        revalidate();
        repaint();
      }
    });
  }


  private void panelInitiator(JPanel panel, String center) {
    mainPanel.removeAll();
    mainPanel.add(panel, center);
    mainPanel.repaint();
    mainPanel.revalidate();
  }

  private void createPortfolioEvent(Features features) {
    mainPanel.setBorder(null);
    MainMenuTextFieldPanel mainMenuTextFieldPanel = getPanel("Create Portfolio",
            "Enter the name of the portfolio: ",
            "Create");
    panelInitiator(mainMenuTextFieldPanel.fieldPanel, BorderLayout.CENTER);
    mainMenuTextFieldPanel.submit.addActionListener(e -> {
      boolean isPortfolioExists = validateCreatePortfolioName(features,
              mainMenuTextFieldPanel.errorLabel, portfolioNameField);
      if (isPortfolioExists) {
        features.createPortfolio(portfolioNameField.getText(), mainMenuTextFieldPanel.errorLabel);
        createPortfolioMenuOptions(features);
        subMenuPanel.setVisible(true);
        mainMenuTextFieldPanel.fieldPanel.removeAll();
        mainMenuPanel.setVisible(false);
        JLabel label = generateLabel("");
        setSuccessMessage("Portfolio " + portfolioNameField.getText() + " in creation! ",
                label);
        mainPanel.add(label,
                BorderLayout.NORTH);
        revalidate();
        repaint();
      }
    });
  }

  private void stockAnalysisEvents(Features features) {
    mainPanel.setBorder(null);
    MainMenuTextFieldPanel stockMainMenuTextFieldPanel = getPanel("Perform Stock Analysis",
            "Enter the name of " +
                    "the stock : ", "Analyse");
    panelInitiator(stockMainMenuTextFieldPanel.fieldPanel, BorderLayout.CENTER);
    stockMainMenuTextFieldPanel.submit.addActionListener(e -> {
      boolean isStockExists;
      isStockExists = validateShareName(features, stockMainMenuTextFieldPanel.errorLabel,
              stockMainMenuTextFieldPanel.textField);
      if (isStockExists) {
        stockAnalysisMenuOptions(features, stockMainMenuTextFieldPanel.textField.getText());
        subMenuPanel.setVisible(true);
        stockMainMenuTextFieldPanel.fieldPanel.removeAll();
        mainMenuPanel.setVisible(false);
        revalidate();
        repaint();
      }
    });
    revalidate();
  }

  private void dollarCostEvent(Features features) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "Dollar Cost Average Strategy");
    StartEndDayPanel startEndDayPanel = getStartEndDayPanel();
    JLabel infoLabel = generateLabel("If you don't want to specify end date. " +
            "Select the last date");
    infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
    startEndDayPanel.datePanel.add(infoLabel);
    JPanel periodPanel = new JPanel(new FlowLayout());
    JLabel periodLabel = generateLabel("");
    periodPanel.add(generateLabel("Enter period in days: "));
    JTextField periodField = generateTextField();
    periodPanel.add(periodField);
    periodPanel.add(periodLabel);
    startEndDayPanel.datePanel.add(periodPanel);
    TextFieldPanel amountInvestedPanel = getTextFieldPanel("Enter the amount to be invested ($):");
    startEndDayPanel.datePanel.add(amountInvestedPanel.panel);
    TextFieldPanel companyNamePanel = getTextFieldPanel("Enter the name of the company:");
    startEndDayPanel.datePanel.add(companyNamePanel.panel);
    TextFieldPanel givenPercentangePanel = getTextFieldPanel("Enter percentage of stock : ");
    startEndDayPanel.datePanel.add(givenPercentangePanel.panel);
    JPanel buttonPanel = new JPanel();
    JPanel percentagePanel = new JPanel();
    JLabel percentageLeftLabel = generateLabel("Percentage added : ");
    JLabel percentageLabel = generateLabel("0");
    percentagePanel.add(percentageLeftLabel);
    percentagePanel.add(percentageLabel);
    startEndDayPanel.datePanel.add(percentagePanel);
    JButton search = generateButton("Add", buttonPanel);
    startEndDayPanel.datePanel.add(buttonPanel);
    panelInitiator(startEndDayPanel.datePanel, BorderLayout.NORTH);
    DefaultTableModel tableModel = generateBatchBuyTable(new String[]{"Ticker", "Percentage",});
    List<Map<String, String>> list = new ArrayList<>();
    List<String> sharesByPercentage = new ArrayList<>();
    search.addActionListener(e -> {
      String companyName = companyNamePanel.textField.getText().trim();
      if (checkDollarCostDates(features, periodField, periodLabel, companyNamePanel,
              givenPercentangePanel, amountInvestedPanel, startEndDayPanel)) {
        createStrategy(features, amountInvestedPanel, periodField, startEndDayPanel, companyName,
                companyNamePanel, percentageLabel, givenPercentangePanel,
                list, sharesByPercentage, tableModel);
      }
      revalidate();
      repaint();
    });
    repaint();
    revalidate();
  }

  private void createStrategy(Features features, TextFieldPanel amountInvestedPanel,
                              JTextField periodField, StartEndDayPanel startEndDayPanel,
                              String companyName, TextFieldPanel companyNamePanel,
                              JLabel percentageLabel, TextFieldPanel givenPercentangePanel,
                              List<Map<String, String>> list, List<String> sharesByPercentage,
                              DefaultTableModel tableModel) {
    float amount = Float.parseFloat(amountInvestedPanel.textField.getText().trim());
    disableFields(periodField, amountInvestedPanel, startEndDayPanel);
    repaint();
    revalidate();
    LocalDate startDate =
            features.formatDates(startEndDayPanel.startingYearComboBox.getSelectedItem().
                            toString(),
                    startEndDayPanel.startingMonthComboBox.getSelectedItem().toString(),
                    startEndDayPanel.startingDayComboBox.getSelectedItem().toString());
    LocalDate endDate =
            features.formatDates(startEndDayPanel.endingYearComboBox.getSelectedItem().toString(),
                    startEndDayPanel.endingMonthComboBox.getSelectedItem().toString(),
                    startEndDayPanel.endingDayComboBox.getSelectedItem().toString());
    List<Map<String, String>> shareData = features.getShareData(companyName,
            companyNamePanel.labelError);
    if (Float.parseFloat(percentageLabel.getText()) +
            Float.parseFloat(givenPercentangePanel.textField.getText()) > 100) {
      setErrorMessage("Percentage can't be more than 100",
              givenPercentangePanel.labelError);
    } else {
      performDollarCostStrategy(features, startDate, companyName, shareData,
              companyNamePanel, givenPercentangePanel, amount, list, percentageLabel,
              periodField, sharesByPercentage, tableModel, startDate, endDate);
    }
    revalidate();
    repaint();
  }

  private void performDollarCostStrategy(Features features, LocalDate currentDate,
                                         String companyName, List<Map<String, String>> shareData,
                                         TextFieldPanel companyNamePanel,
                                         TextFieldPanel givenPercentangePanel,
                                         float amount, List<Map<String, String>> list,
                                         JLabel percentageLabel, JTextField periodField,
                                         List<String> sharesByPercentage,
                                         DefaultTableModel tableModel,
                                         LocalDate startDate, LocalDate endDate) {
    currentDate = getCurrentDate(features, currentDate, companyName, shareData,
            companyNamePanel, givenPercentangePanel, amount, list,
            percentageLabel, periodField);
    features.addShareToSharesList(sharesByPercentage,
            companyName + ":" + givenPercentangePanel.textField.getText().trim());
    percentageLabel.setText(String.valueOf(Float.parseFloat(percentageLabel.getText()) +
            Float.parseFloat(givenPercentangePanel.textField.getText().trim())));
    String[] row = new String[]{companyName, givenPercentangePanel.textField.getText().trim()};
    features.addRow(tableModel, row, companyName, tableModel.getRowCount(),
            givenPercentangePanel.textField.getText().trim());
    if (Float.parseFloat(percentageLabel.getText()) == 100) {
      features.createStrategy(sharesByPercentage, amount, startDate, endDate,
              currentDate, Integer.parseInt(periodField.getText().trim()), list,
              givenPercentangePanel.labelError);
      totalCompositionEvent(features);
    }
  }

  private LocalDate getCurrentDate(Features features, LocalDate currentDate, String companyName,
                                   List<Map<String, String>> shareData,
                                   TextFieldPanel companyNamePanel,
                                   TextFieldPanel givenPercentangePanel,
                                   float amount, List<Map<String, String>> list,
                                   JLabel percentageLabel, JTextField periodField) {
    while (currentDate.isBefore(LocalDate.now())) {
      Map<String, String> data = features.getDataForStrategy(currentDate, companyName,
              shareData, companyNamePanel.labelError);
      companyNamePanel.labelError.setText("");
      if (data != null) {
        data.put("percentage", givenPercentangePanel.textField.getText().trim());
        features.addToList(amount, list,
                percentageLabel, companyNamePanel.labelError, data);
      }
      if (data == null) {
        currentDate = currentDate.plusDays(1);
        continue;
      }
      currentDate = currentDate.plusDays(Long.parseLong(periodField.getText().trim()));
    }
    return currentDate;
  }

  private void disableFields(JTextField periodField, TextFieldPanel amountInvestedPanel,
                             StartEndDayPanel startEndDayPanel) {
    periodField.setEnabled(false);
    amountInvestedPanel.textField.setEnabled(false);
    startEndDayPanel.startingDayComboBox.setEnabled(false);
    startEndDayPanel.startingMonthComboBox.setEnabled(false);
    startEndDayPanel.startingYearComboBox.setEnabled(false);
    startEndDayPanel.endingDayComboBox.setEnabled(false);
    startEndDayPanel.endingMonthComboBox.setEnabled(false);
    startEndDayPanel.endingYearComboBox.setEnabled(false);
  }

  private boolean checkDollarCostDates(Features features, JTextField periodField,
                                       JLabel periodLabel, TextFieldPanel companyNamePanel,
                                       TextFieldPanel givenPercentangePanel,
                                       TextFieldPanel amountInvestedPanel,
                                       StartEndDayPanel startEndDayPanel) {
    boolean isDayValid = isValidDay(periodField, periodLabel);
    boolean isValidName = validateShareName(features, companyNamePanel.labelError,
            companyNamePanel.textField);
    boolean isValidQuantity = isValidNumField(features, givenPercentangePanel.labelError,
            givenPercentangePanel.textField);
    boolean isValidPercentage = isValidNumField(features,
            givenPercentangePanel.labelError,
            givenPercentangePanel.textField);
    boolean isValidPercent = false;
    if (isValidPercentage) {
      isValidPercent = isValidPercent(givenPercentangePanel.textField,
              givenPercentangePanel.labelError);
    }
    boolean isStartBeforeEnd = validateStartBeforeEnd(startEndDayPanel.startingYearComboBox,
            startEndDayPanel.startingMonthComboBox, startEndDayPanel.startingDayComboBox,
            startEndDayPanel.endingYearComboBox,
            startEndDayPanel.endingMonthComboBox, startEndDayPanel.endingDayComboBox, features,
            startEndDayPanel.startingDateLabel);
    boolean isStartDateValid = isValidDate(features, startEndDayPanel.startingDateLabel,
            startEndDayPanel.startingYearComboBox, startEndDayPanel.startingMonthComboBox,
            startEndDayPanel.startingDayComboBox);
    boolean isEndDateValid = isValidDate(features, startEndDayPanel.endingDateLabel,
            startEndDayPanel.endingYearComboBox,
            startEndDayPanel.endingMonthComboBox, startEndDayPanel.endingDayComboBox);
    boolean isAmountInvested = isValidAmt(features, amountInvestedPanel.labelError,
            amountInvestedPanel.textField);
    return isValidPercentage && isValidPercent && isStartDateValid && isEndDateValid && isDayValid
            && isStartBeforeEnd
            && isValidName && isValidQuantity && isAmountInvested;
  }

  private boolean isValidPercent(JTextField textFieldPanel, JLabel label) {
    if (textFieldPanel.getText().trim().isEmpty()) {
      setErrorMessage("Provide valid percentage", label);
      return false;
    }
    if (Integer.parseInt(textFieldPanel.getText().trim()) > 100) {
      setErrorMessage("Provide valid percentage", label);
      return false;
    }
    label.setText("");
    return true;
  }

  private void invokeSubMenuEvents(Features features) {
    viewStocks.addActionListener(e -> viewStocksEvent(features));
    buyStock.addActionListener(e -> buyStockEvents(features));
    sellStock.addActionListener(e -> sellStockEvent(features));
    totalValue.addActionListener(e -> totalValueEvent(features));
    investFixedAmount.addActionListener(e -> investFixedAmountEvent(features));
    dollarCostStrategy.addActionListener(e -> dollarCostEvent(features));
    totalComposition.addActionListener(e -> totalCompositionEvent(features));
    totalInvestment.addActionListener(e -> totalInvestmentEvent(features));
    save.addActionListener(e -> saveEvent(features));
    back.addActionListener(e -> backEvent(features));
  }

  private void investFixedAmountEvent(Features features) {
    InvestFixedAmountPanel investFixedAmountPanel = getInvestFixedAmountPanel();
    panelInitiator(investFixedAmountPanel.investAmountPanel.stockMenuPanel, BorderLayout.NORTH);
    DefaultTableModel tableModel = generateBatchBuyTable(new String[]{"Ticker",
        "Date", "Close", "Percentage"});
    List<Map<String, String>> list = new ArrayList<>();
    investFixedAmountPanel.add.addActionListener(e -> {
      if (checkFixedAmountValidations(features, investFixedAmountPanel)) {
        investFixedAmountPanel.investAmountPanel.percentageErrorLabel.setText("");
        investFixedAmountPanel.investAmountPanel.amtInvestedField.setEnabled(false);
        performInvestFixedAmount(features, investFixedAmountPanel, tableModel, list);
        revalidate();
      }
      revalidate();
      repaint();
    });
  }

  private void performInvestFixedAmount(Features features,
                                        InvestFixedAmountPanel investFixedAmountPanel,
                                        DefaultTableModel tableModel, List<Map<String,
          String>> list) {
    LocalDate timestamp = features.formatDates(investFixedAmountPanel.yearComboBox.
                    getSelectedItem().toString(),
            investFixedAmountPanel.monthComboBox.getSelectedItem().toString(),
            investFixedAmountPanel.dayComboBox.getSelectedItem().toString());
    String companyName = investFixedAmountPanel.investAmountPanel.companyNameField.
            getText().trim();
    float amount = Float.parseFloat(investFixedAmountPanel.investAmountPanel.amtInvestedField.
            getText().trim());
    Map<String, String> data = features.addDataToTable(tableModel, companyName,
            timestamp.toString(),
            investFixedAmountPanel.investAmountPanel.percentageField.getText().trim()
            , investFixedAmountPanel.percentageLabel, investFixedAmountPanel.
                    investAmountPanel.percentageErrorLabel);
    if (data != null) {
      features.addToList(amount, list,
              investFixedAmountPanel.percentageLabel, investFixedAmountPanel.
                      investAmountPanel.companyErrorLabel, data);
    }
    if (Float.parseFloat(investFixedAmountPanel.percentageLabel.getText()) == 100) {
      features.investFixedAmount(list, amount, investFixedAmountPanel.
              investAmountPanel.amtInvestedErrorLabel);
      totalCompositionEvent(features);
    }
  }

  private boolean checkFixedAmountValidations(Features features,
                                              InvestFixedAmountPanel investFixedAmountPanel) {
    boolean isValidName = validateShareName(features, investFixedAmountPanel.
                    investAmountPanel.companyErrorLabel,
            investFixedAmountPanel.investAmountPanel.companyNameField);
    boolean isValidPercentage = isValidNumField(features,
            investFixedAmountPanel.investAmountPanel.percentageErrorLabel,
            investFixedAmountPanel.investAmountPanel.percentageField);
    boolean isValidPercent = false;
    if (isValidPercentage) {
      isValidPercent =
              isValidPercent(investFixedAmountPanel.investAmountPanel.percentageField,
                      investFixedAmountPanel.investAmountPanel.percentageErrorLabel);
    }
    boolean isWorkingDay = validateShareOnDate(
            investFixedAmountPanel.investAmountPanel.companyNameField.getText().trim(), features,
            investFixedAmountPanel.dateLabel,
            investFixedAmountPanel.yearComboBox,
            investFixedAmountPanel.monthComboBox,
            investFixedAmountPanel.dayComboBox);
    boolean isAmountInvested = isValidAmt(features,
            investFixedAmountPanel.investAmountPanel.amtInvestedErrorLabel,
            investFixedAmountPanel.investAmountPanel.amtInvestedField);
    return isValidPercent && isValidPercentage && isValidName && isWorkingDay && isAmountInvested;
  }

  private InvestFixedAmountPanel getInvestFixedAmountPanel() {
    InvestAmountPanel investAmountPanel = getInvestAmountPanel();
    JComboBox<Integer> dayComboBox = generateDay();
    JComboBox<String> monthComboBox = generateMonth();
    JComboBox<Integer> yearComboBox = generateYear(LocalDate.now().getYear());
    JPanel datePanel = new JPanel(new FlowLayout());
    JLabel dateLabel = generateLabel("");
    generateDate(datePanel, dayComboBox, monthComboBox, yearComboBox, dateLabel,
            "Enter a date: ");
    datePanel.add(dateLabel);
    investAmountPanel.stockMenuPanel.add(datePanel);
    JPanel buttonPanel = new JPanel();
    JPanel percentagePanel = new JPanel();
    JLabel percentageLeftLabel = generateLabel("Percentage added : ");
    JLabel percentageLabel = generateLabel("0");
    percentagePanel.add(percentageLeftLabel);
    percentagePanel.add(percentageLabel);
    investAmountPanel.stockMenuPanel.add(percentagePanel);
    JButton add = generateButton("Add", buttonPanel);
    investAmountPanel.stockMenuPanel.add(buttonPanel);
    return new InvestFixedAmountPanel(investAmountPanel, dayComboBox, monthComboBox,
            yearComboBox, dateLabel, percentageLabel, add);
  }

  private static class InvestFixedAmountPanel {
    private final InvestAmountPanel investAmountPanel;
    private final JComboBox<Integer> dayComboBox;
    private final JComboBox<String> monthComboBox;
    private final JComboBox<Integer> yearComboBox;
    private final JLabel dateLabel;
    private final JLabel percentageLabel;
    private final JButton add;

    private InvestFixedAmountPanel(InvestAmountPanel investAmountPanel,
                                   JComboBox<Integer> dayComboBox,
                                   JComboBox<String> monthComboBox,
                                   JComboBox<Integer> yearComboBox,
                                   JLabel dateLabel,
                                   JLabel percentageLabel,
                                   JButton add) {
      this.investAmountPanel = investAmountPanel;
      this.dayComboBox = dayComboBox;
      this.monthComboBox = monthComboBox;
      this.yearComboBox = yearComboBox;
      this.dateLabel = dateLabel;
      this.percentageLabel = percentageLabel;
      this.add = add;
    }
  }

  private InvestAmountPanel getInvestAmountPanel() {
    mainPanel.setBorder(null);
    JPanel stockMenuPanel = new JPanel(new GridLayout(0, 1));
    setBorderTitle(mainPanel, "Invest Fixed Amount");
    TextFieldPanel amountInvested = getTextFieldPanel("Enter the amount to be invested ($):");
    stockMenuPanel.add(amountInvested.panel);
    TextFieldPanel companyName = getTextFieldPanel("Enter the name of the company:");
    stockMenuPanel.add(companyName.panel);
    JTextField percentageField = generateTextField();
    JLabel quantityLabel = generateLabel("Enter the percentage of the stock:");
    JLabel percentageErrorLabel = generateLabel("");
    JPanel percentageFieldPanel = new JPanel(new FlowLayout());
    percentageFieldPanel.add(quantityLabel);
    percentageFieldPanel.add(percentageField);
    percentageFieldPanel.add(percentageErrorLabel);
    stockMenuPanel.add(percentageFieldPanel);
    return new InvestAmountPanel(stockMenuPanel,
            amountInvested.textField, amountInvested.labelError, companyName.textField,
            companyName.labelError,
            percentageField, percentageErrorLabel);
  }

  private TextFieldPanel getTextFieldPanel(String message) {
    JTextField textField = generateTextField();
    JLabel amtInvested = generateLabel(message);
    JLabel labelError = generateLabel("");
    JPanel panel = new JPanel(new FlowLayout());
    panel.add(amtInvested);
    panel.add(textField);
    panel.add(labelError);
    return new TextFieldPanel(textField, labelError, panel);
  }

  private static class TextFieldPanel {
    private final JTextField textField;
    private final JLabel labelError;
    private final JPanel panel;

    private TextFieldPanel(JTextField textField, JLabel labelError, JPanel panel) {
      this.textField = textField;
      this.labelError = labelError;
      this.panel = panel;
    }
  }

  private static class InvestAmountPanel {
    private final JPanel stockMenuPanel;
    private final JTextField amtInvestedField;
    private final JLabel amtInvestedErrorLabel;
    private final JTextField companyNameField;
    private final JLabel companyErrorLabel;
    private final JTextField percentageField;
    private final JLabel percentageErrorLabel;

    private InvestAmountPanel(JPanel stockMenuPanel, JTextField amtInvestedField,
                              JLabel amtInvestedErrorLabel, JTextField companyNameField,
                              JLabel companyErrorLabel, JTextField percentageField,
                              JLabel percentageErrorLabel) {
      this.stockMenuPanel = stockMenuPanel;
      this.amtInvestedField = amtInvestedField;
      this.amtInvestedErrorLabel = amtInvestedErrorLabel;
      this.companyNameField = companyNameField;
      this.companyErrorLabel = companyErrorLabel;
      this.percentageField = percentageField;
      this.percentageErrorLabel = percentageErrorLabel;
    }
  }

  private JComboBox<Integer> generateYear(long endYear) {
    JComboBox<Integer> yearComboBox = new JComboBox<>();
    yearComboBox.setFont(getStyle());
    for (int i = 1999; i <= endYear; i++) {
      yearComboBox.addItem(i);
    }
    return yearComboBox;
  }

  private JComboBox<Integer> generateDay() {
    JComboBox<Integer> dayComboBox = new JComboBox<>();
    dayComboBox.setFont(getStyle());
    for (int i = 1; i <= 31; i++) {
      dayComboBox.addItem(i);
    }
    return dayComboBox;
  }

  @Override
  public DefaultTableModel generateBatchBuyTable(String[] columns) {
    if (scrollPane != null) {
      scrollPane.removeAll();
    }
    JPanel tablePanel = new JPanel(new FlowLayout());
    JPanel gridPanel = new JPanel(new GridLayout(0, 1));
    JPanel stockDataPanel = new JPanel(new FlowLayout());
    setTitle("Total Composition");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    model = new DefaultTableModel(new Object[][]{},
            columns) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    return getDefaultTableModel(tablePanel, gridPanel, stockDataPanel);
  }

  private boolean isValidAmt(Features features, JLabel errorLabel, JTextField amtField) {
    String amount = amtField.getText();
    if (amount.isEmpty()) {
      errorLabel.setText("Amount cannot be empty");
      errorLabel.setForeground(Color.RED);
      return false;
    }
    if (!amount.matches("^[1-9]\\d*$")) {
      errorLabel.setText("Please enter a valid amount.");
      errorLabel.setForeground(Color.RED);
      return false;
    }
    boolean isStock = features.checkQuantity(Integer.parseInt(amount));
    if (!isStock) {
      errorLabel.setText("Please enter a valid amount");
      errorLabel.setForeground(Color.RED);
      return false;
    }
    errorLabel.setText("");
    return true;
  }


  private void totalInvestmentEvent(Features features) {
    DatePanel singleDatePanel = getDatePanel("Total Investment", "Get investment");
    panelInitiator(singleDatePanel.panel, BorderLayout.NORTH);
    singleDatePanel.button.addActionListener(e -> {
      resetDynamicPanel();
      boolean isWorkingDay = features.isValidDate(singleDatePanel.
                      yearComboBox.getSelectedItem().toString(),
              singleDatePanel.monthComboBox.getSelectedItem().toString(),
              singleDatePanel.dayComboBox.getSelectedItem().toString());
      if (!isWorkingDay) {
        setErrorMessage("Provide a valid date.", singleDatePanel.errorLabel);
      }
      if (isWorkingDay) {
        float value = features.getTotalInvestmentOfPortfolio(features.
                formatDates(singleDatePanel.yearComboBox.getSelectedItem().toString(),
                        singleDatePanel.monthComboBox.getSelectedItem().toString(),
                        singleDatePanel.dayComboBox.getSelectedItem().toString()));
        JLabel stockLabel = new JLabel();
        setSuccessMessage("You invested $" + value + " till " +
                features.formatDates(singleDatePanel.yearComboBox.getSelectedItem().toString(),
                        singleDatePanel.monthComboBox.getSelectedItem().toString(),
                        singleDatePanel.dayComboBox.getSelectedItem().toString()), stockLabel);
        dynamicPanel.add(stockLabel, BorderLayout.NORTH);
        mainPanel.add(dynamicPanel, BorderLayout.CENTER);
      }
      revalidate();
      repaint();
    });

  }

  private void generateDate(JPanel datePanel, JComboBox<Integer> dayComboBox,
                            JComboBox<String> monthComboBox,
                            JComboBox<Integer> yearComboBox, JLabel dateLabel, String string) {
    datePanel.add(generateLabel(string));
    datePanel.add(generateLabel("Day:"));
    datePanel.add(dayComboBox);
    datePanel.add(generateLabel("Month:"));
    datePanel.add(monthComboBox);
    datePanel.add(generateLabel("Year:"));
    datePanel.add(yearComboBox);
    datePanel.add(dateLabel);
  }

  private void saveEvent(Features features) {
    JLabel stockLabel = new JLabel();
    mainPanel.removeAll();
    mainPanel.setBorder(null);
    dynamicPanel.removeAll();
    setBorderTitle(mainPanel, "Save Portfolio");
    dynamicPanel.add(stockLabel, BorderLayout.NORTH);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    boolean isSaved = features.savePortfolio(portfolioNameField.getText(), stockLabel);
    if (isSaved) {
      setSuccessMessage("Portfolio Saved Successfully! ", stockLabel);

    }
  }

  private void totalCompositionEvent(Features features) {
    mainPanel.removeAll();
    mainPanel.setBorder(null);
    dynamicPanel.removeAll();
    setBorderTitle(mainPanel, "Total Composition");
    JLabel label = generateLabel("");
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    dynamicPanel.add(label, BorderLayout.NORTH);
    repaint();
    revalidate();
    features.totalComposition(label);
  }

  @Override
  public void setErrorMessage(String message, JLabel label) {
    label.setText(message);
    label.setFont(getStyle());
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setForeground(Color.RED);
  }

  private void setSuccessMessage(String message, JLabel label) {
    label.setText(message);
    label.setFont(getStyle());
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setForeground(Color.GREEN);
  }

  private void totalValueEvent(Features features) {
    DatePanel singleDatePanel = getDatePanel("Total Value", "Get value");
    panelInitiator(singleDatePanel.panel, BorderLayout.NORTH);
    singleDatePanel.button.addActionListener(e -> {
      resetDynamicPanel();
      boolean isWorkingDay = features.isValidDate(singleDatePanel.
                      yearComboBox.getSelectedItem().toString(),
              singleDatePanel.monthComboBox.getSelectedItem().toString(),
              singleDatePanel.dayComboBox.getSelectedItem().toString());
      if (!isWorkingDay) {
        setErrorMessage("Provide a valid date.", singleDatePanel.errorLabel);
      }
      if (isWorkingDay) {
        singleDatePanel.errorLabel.setText("");
        float value = features.getValueOfPortfolio(features.formatDates(singleDatePanel.
                        yearComboBox.
                        getSelectedItem().toString(),
                singleDatePanel.monthComboBox.getSelectedItem().toString(),
                singleDatePanel.dayComboBox.getSelectedItem().toString()));
        JLabel stockLabel = new JLabel();
        setSuccessMessage("The value of your portfolio on " +
                features.formatDates(singleDatePanel.yearComboBox.getSelectedItem().toString(),
                        singleDatePanel.monthComboBox.getSelectedItem().toString(),
                        singleDatePanel.dayComboBox.getSelectedItem().toString()) +
                " is $" + value, stockLabel);
        dynamicPanel.add(stockLabel, BorderLayout.NORTH);
        mainPanel.add(dynamicPanel, BorderLayout.CENTER);
      }
      revalidate();
      repaint();
    });
  }

  private DatePanel getDatePanel(String title, String buttonName) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, title);
    JPanel panel = new JPanel(new GridLayout(0, 1));
    JComboBox<Integer> dayComboBox = generateDay();
    JComboBox<String> monthComboBox = generateMonth();
    JComboBox<Integer> yearComboBox = generateYear(LocalDate.now().getYear());
    JPanel datePanel = new JPanel();
    JLabel dateLabel = generateLabel("");
    generateDate(datePanel, dayComboBox, monthComboBox, yearComboBox, dateLabel,
            "Enter a date: ");
    JPanel buttonPanel = new JPanel();
    JButton button = generateButton(buttonName, buttonPanel);
    JLabel errorLabel = generateLabel("");
    panel.add(datePanel);
    panel.add(buttonPanel);
    panel.add(errorLabel);
    datePanel.setBounds(150, 50, 100, 100);
    return new DatePanel(dayComboBox, monthComboBox, yearComboBox, panel, button, errorLabel);
  }

  private static class DatePanel {
    private final JComboBox<Integer> dayComboBox;
    private final JComboBox<String> monthComboBox;
    private final JComboBox<Integer> yearComboBox;
    private final JPanel panel;
    private final JButton button;
    private final JLabel errorLabel;

    private DatePanel(JComboBox<Integer> dayComboBox, JComboBox<String> monthComboBox,
                      JComboBox<Integer> yearComboBox, JPanel panel, JButton button,
                      JLabel errorLabel) {
      this.dayComboBox = dayComboBox;
      this.monthComboBox = monthComboBox;
      this.yearComboBox = yearComboBox;
      this.panel = panel;
      this.button = button;
      this.errorLabel = errorLabel;
    }
  }

  private void sellStockEvent(Features features) {
    mainPanel.setBorder(null);
    BuySellPanel buySellPanel = getBuySellPanel("Sell", "Sell Stocks");
    JLabel compositionLabel = new JLabel();
    compositionLabel.setFont(getStyle());
    buySellPanel.button.setFont(getStyle());
    panelInitiator(buySellPanel.stockMenuPanel, BorderLayout.NORTH);
    features.totalComposition(compositionLabel);
    buySellPanel.button.addActionListener(e -> {
      String quantity = buySellPanel.quantityField.getText();
      String companyName = buySellPanel.companyNameField.getText().trim();
      boolean isWorkingDay = validateShareOnDate(companyName, features, buySellPanel.dateLabel,
              buySellPanel.yearComboBox, buySellPanel.monthComboBox,
              buySellPanel.dayComboBox);
      boolean isValidName = validateShareName(features, buySellPanel.companyErrorLabel,
              buySellPanel.companyNameField);
      boolean isValidQuantity = isValidNumField(features, buySellPanel.quantityErrorLabel,
              buySellPanel.quantityField);
      if (isWorkingDay && isValidName && isValidQuantity) {
        features.sellStock(companyName, quantity, features.formatDates(buySellPanel.yearComboBox.
                                getSelectedItem().toString(),
                        buySellPanel.monthComboBox.getSelectedItem().toString(),
                        buySellPanel.dayComboBox.getSelectedItem().toString()),
                buySellPanel.quantityErrorLabel);
        features.totalComposition(buySellPanel.dateLabel);
        revalidate();
        repaint();
      }
    });
  }

  private JComboBox<String> generateMonth() {
    JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"January", "February", "March",
        "April", "May", "June", "July", "August", "September", "October", "November", "December"});
    monthComboBox.setFont(getStyle());
    return monthComboBox;
  }

  private void displayMessage(String message, JLabel dateLabel) {
    dateLabel.setText(message);
    dateLabel.setFont(getStyle());
    dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
    dateLabel.setForeground(Color.RED);
  }

  private void buyStockEvents(Features features) {
    mainPanel.setBorder(null);
    BuySellPanel buySellPanel = getBuySellPanel("Buy", "Buy Stocks");
    panelInitiator(buySellPanel.stockMenuPanel, BorderLayout.NORTH);
    buySellPanel.button.addActionListener(e -> {
      String quantity = buySellPanel.quantityField.getText();
      String companyName = buySellPanel.companyNameField.getText().trim();
      boolean isValidName = validateShareName(features, buySellPanel.companyErrorLabel,
              buySellPanel.companyNameField);
      boolean isValidQuantity = isValidNumField(features, buySellPanel.quantityErrorLabel,
              buySellPanel.quantityField);
      boolean isWorkingDay = validateShareOnDate(companyName, features, buySellPanel.dateLabel,
              buySellPanel.yearComboBox, buySellPanel.monthComboBox,
              buySellPanel.dayComboBox);
      if (isWorkingDay && isValidName && isValidQuantity) {
        features.buyStock(companyName, quantity, features.formatDates(buySellPanel.yearComboBox.
                                getSelectedItem().toString(),
                        buySellPanel.monthComboBox.getSelectedItem().toString(),
                        buySellPanel.dayComboBox.getSelectedItem().toString()),
                buySellPanel.quantityErrorLabel);
        features.totalComposition(buySellPanel.dateLabel);
        revalidate();
        repaint();
      }
    });
  }

  private BuySellPanel getBuySellPanel(String buttonName, String title) {
    JPanel stockMenuPanel = new JPanel(new GridLayout(0, 1));
    setBorderTitle(mainPanel, title);
    JTextField companyNameField = generateTextField();
    JLabel nameLabel = generateLabel("Enter the name of the company:");
    JLabel companyErrorLabel = generateLabel("");
    JPanel companyNamePanel = new JPanel(new FlowLayout());
    companyNamePanel.add(nameLabel);
    companyNamePanel.add(companyNameField);
    companyNamePanel.add(companyErrorLabel);
    stockMenuPanel.add(companyNamePanel);
    JTextField quantityField = generateTextField();
    JLabel quantityLabel = generateLabel("Enter the quantity of the stock:");
    JLabel quantityErrorLabel = generateLabel("");
    JPanel quantityPanel = new JPanel(new FlowLayout());
    quantityPanel.add(quantityLabel);
    quantityPanel.add(quantityField);
    quantityPanel.add(quantityErrorLabel);
    stockMenuPanel.add(quantityPanel);
    JComboBox<Integer> dayComboBox = generateDay();
    JComboBox<String> monthComboBox = generateMonth();
    JComboBox<Integer> yearComboBox = generateYear(LocalDate.now().getYear());
    JPanel datePanel = new JPanel(new FlowLayout());
    JLabel dateLabel = new JLabel();
    dateLabel.setFont(getStyle());
    generateDate(datePanel, dayComboBox, monthComboBox, yearComboBox, dateLabel,
            "Enter a date: ");
    stockMenuPanel.add(datePanel);
    JPanel buttonPanel = new JPanel();
    JButton search = generateButton(buttonName, buttonPanel);
    stockMenuPanel.add(buttonPanel);
    return new BuySellPanel(stockMenuPanel, companyNameField,
            companyErrorLabel, quantityField, quantityErrorLabel, dayComboBox,
            monthComboBox, yearComboBox, dateLabel, search);
  }

  private static class BuySellPanel {
    private final JPanel stockMenuPanel;
    private final JTextField companyNameField;
    private final JLabel companyErrorLabel;
    private final JTextField quantityField;
    private final JLabel quantityErrorLabel;
    private final JComboBox<Integer> dayComboBox;
    private final JComboBox<String> monthComboBox;
    private final JComboBox<Integer> yearComboBox;
    private final JLabel dateLabel;
    private final JButton button;

    private BuySellPanel(JPanel stockMenuPanel, JTextField companyNameField,
                         JLabel companyErrorLabel, JTextField quantityField,
                         JLabel quantityErrorLabel, JComboBox<Integer> dayComboBox,
                         JComboBox<String> monthComboBox, JComboBox<Integer> yearComboBox,
                         JLabel dateLabel, JButton button) {
      this.stockMenuPanel = stockMenuPanel;
      this.companyNameField = companyNameField;
      this.companyErrorLabel = companyErrorLabel;
      this.quantityField = quantityField;
      this.quantityErrorLabel = quantityErrorLabel;
      this.dayComboBox = dayComboBox;
      this.monthComboBox = monthComboBox;
      this.yearComboBox = yearComboBox;
      this.dateLabel = dateLabel;
      this.button = button;
    }
  }

  private boolean isValidNumField(Features features, JLabel dateLabel, JTextField quantityField) {
    String quantity = quantityField.getText();
    if (quantity.isEmpty()) {
      dateLabel.setText("Field cannot be empty");
      dateLabel.setForeground(Color.RED);
      return false;
    }
    if (!quantity.matches("^[1-9]\\d*$")) {
      dateLabel.setText("Please enter a valid number");
      dateLabel.setForeground(Color.RED);
      return false;
    }
    boolean isStock = features.checkQuantity(Float.parseFloat(quantity));
    if (!isStock) {
      dateLabel.setText("Please enter a valid number");
      dateLabel.setForeground(Color.RED);
      return false;
    }
    dateLabel.setText("");
    return true;
  }

  private void setBorderTitle(JPanel panel, String title) {
    panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 30, 30, 30),
            BorderFactory.createTitledBorder("<html><h2>" + title + "</h2></html>")));
  }

  private void backEvent(Features features) {
    mainMenuPanel.setVisible(true);
    subMenuPanel.setVisible(false);
    mainPanel.removeAll();
    mainPanel.setBorder(null);
    mainPanel.setBorder(null);
    revalidate();
    repaint();
  }

  private void viewStocksEvent(Features features) {
    mainPanel.setBorder(null);
    MainMenuTextFieldPanel viewStockPanel = getPanel("View Stocks",
            "Search Ticker Name from A-Z:",
            "Search");

    panelInitiator(viewStockPanel.fieldPanel, BorderLayout.NORTH);
    viewStockPanel.submit.addActionListener(e -> {
      Map<String, String> stocks = features.viewAllStocks(viewStockPanel.textField.
                      getText().trim(),
              viewStockPanel.errorLabel);
      generateViewStocksTable(stocks);
      revalidate();
      repaint();
    });
    revalidate();
  }

  private void generateViewStocksTable(Map<String, String> stocks) {
    if (scrollPane != null) {
      scrollPane.removeAll();
    }
    JPanel tablePanel = new JPanel(new FlowLayout());
    JPanel gridPanel = new JPanel(new GridLayout(0, 1));
    JPanel stockDataPanel = new JPanel(new FlowLayout());
    setTitle("Stocks Table");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Object[][] data = new Object[stocks.size()][2];
    int i = 0;
    for (Map.Entry<String, String> entry : stocks.entrySet()) {
      data[i][0] = entry.getKey();
      data[i][1] = entry.getValue();
      i++;
    }
    String[] columnNames = {"Stock Symbol", "Stock Name"};
    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    JTable table = new JTable(model);
    table.setFont(getStyle());
    table.setRowHeight(30);
    scrollPane = new JScrollPane(table);
    scrollPane.setFont(getStyle());
    scrollPane.setPreferredSize(new Dimension(1000, 300));
    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setFont(getStyle());
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    stockDataPanel.add(tablePanel, BorderLayout.CENTER);
    gridPanel.add(stockDataPanel, new GridLayout(0, 1));
    dynamicPanel.removeAll();
    dynamicPanel.add(gridPanel, BorderLayout.CENTER);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
    setVisible(true);
  }


  private boolean validateCreatePortfolioName(Features features, JLabel portfolioLabel,
                                              JTextField portfolioNameField) {
    String portfolioName = portfolioNameField.getText().trim();
    if (portfolioName.isEmpty()) {
      portfolioLabel.setText("Portfolio Name cannot be empty");
      portfolioLabel.setForeground(Color.RED);
      return false;
    }
    boolean isPortfolio = features.checkPortfolio(portfolioName);
    if (isPortfolio) {
      portfolioLabel.setText("Portfolio " + portfolioName + " already exists.");
      portfolioLabel.setForeground(Color.RED);
      return false;
    } else {
      portfolioLabel.setText("Portfolio " + portfolioName + " created successfully!");
      portfolioLabel.setForeground(Color.GREEN);
      return true;
    }
  }

  private boolean validateLoadPortfolioName(Features features, JLabel portfolioLabel,
                                            JTextField portfolioNameField) {
    String portfolioName = portfolioNameField.getText().trim();
    if (portfolioName.isEmpty()) {
      portfolioLabel.setText("Portfolio Name cannot be empty");
      portfolioLabel.setForeground(Color.RED);
      return false;
    }
    boolean isPortfolio = features.checkPortfolio(portfolioName);
    if (isPortfolio) {
      portfolioLabel.setText("Portfolio " + portfolioName + " loaded successfully.");
      portfolioLabel.setForeground(Color.GREEN);
      return true;
    } else {
      portfolioLabel.setText("Portfolio " + portfolioName + " doesn't exists.");
      portfolioLabel.setForeground(Color.RED);
      return false;
    }
  }

  @Override
  public void addFeatures(Features features) {
    setTitle("Stock Management Application");
    setSize(2000, 1900);
    setLayout(new BorderLayout());
    setVisible(true);
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    mainPanel = new JPanel(new BorderLayout());
    mainMenuPanel = new JPanel();
    subMenuPanel = new JPanel(new FlowLayout());
    dynamicPanel = new JPanel(new BorderLayout());
    topPanel.add(mainMenuPanel);
    topPanel.add(subMenuPanel, BorderLayout.SOUTH);
    add(topPanel, BorderLayout.NORTH);
    add(mainPanel, BorderLayout.CENTER);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    JLabel welcomeLabel = new JLabel("WELCOME TO THE PORTFOLIO MANAGEMENT APPLICATION");
    welcomeLabel.setFont(getStyle());
    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    topPanel.add(welcomeLabel, BorderLayout.NORTH);
    createMainMenuOptions();
    createPortfolioMenuOptions(features);
    revalidate();
    repaint();
    setLayout(new BorderLayout());
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    this.invokeMainMenuEvents(features);
  }

  private void createMainMenuOptions() {
    createPortfolioButton = generateButton("Create Portfolio", mainMenuPanel);
    loadPortfolioButton = generateButton("Load Portfolio", mainMenuPanel);
    importPortfolioButton = generateButton("Import Portfolio", mainMenuPanel);
    stockAnalysisButton = generateButton("Stock Analysis", mainMenuPanel);
    listPortfoliosButton = generateButton("List Portfolios", mainMenuPanel);
  }

  @Override
  public DefaultTableModel generateTotalCompositionTable() {
    if (scrollPane != null) {
      scrollPane.removeAll();
    }
    JPanel tablePanel = new JPanel(new FlowLayout());
    JPanel gridPanel = new JPanel(new GridLayout(0, 1));
    JPanel stockDataPanel = new JPanel(new FlowLayout());
    setTitle("Total Composition");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    model = new DefaultTableModel(new Object[][]{},
            new String[]{"Ticker", "Date", "Open", "Close", "Volume", "Quantity", "Buy Value"}) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    return getDefaultTableModel(tablePanel, gridPanel, stockDataPanel);
  }

  private DefaultTableModel getDefaultTableModel(JPanel tablePanel, JPanel gridPanel,
                                                 JPanel stockDataPanel) {
    JTable table = new JTable(model);
    table.setFont(getStyle());
    table.setRowHeight(30);
    scrollPane = new JScrollPane(table);
    scrollPane.setFont(getStyle());
    scrollPane.setPreferredSize(new Dimension(1000, 300));
    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setFont(getStyle());
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    stockDataPanel.add(tablePanel, BorderLayout.CENTER);
    gridPanel.add(stockDataPanel, new GridLayout(0, 1));
    dynamicPanel.removeAll();
    dynamicPanel.add(gridPanel, BorderLayout.CENTER);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
    setVisible(true);
    return model;
  }

  @Override
  public void displayData(String share, DefaultTableModel model) {
    model.addRow(share.split(","));
  }

  private void createPortfolioMenuOptions(Features features) {
    mainPanel.removeAll();
    mainPanel.setBorder(null);
    subMenuPanel.removeAll();
    viewStocks = generateButton("View Stocks", subMenuPanel);
    buyStock = generateButton("Buy Stock", subMenuPanel);
    sellStock = generateButton("Sell Stock", subMenuPanel);
    totalValue = generateButton("Total Value", subMenuPanel);
    totalComposition = generateButton("Total Composition", subMenuPanel);
    totalInvestment = generateButton("Total Investment", subMenuPanel);
    investFixedAmount = generateButton("Invest  Amount", subMenuPanel);
    dollarCostStrategy = generateButton("Create Strategy", subMenuPanel);
    save = generateButton("Save Portfolio", subMenuPanel);
    back = generateButton("Back to Main Menu", subMenuPanel);
    subMenuPanel.setVisible(false);
    this.invokeSubMenuEvents(features);
  }

  private void stockAnalysisMenuOptions(Features features, String name) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "Perform Stock Analysis on stock " + name.toUpperCase());
    subMenuPanel.removeAll();
    dailyStockPriceChange = generateButton("Daily Price", subMenuPanel);
    periodStockPriceChange = generateButton("Period Price", subMenuPanel);
    xDayMovingAverage = generateButton("Moving Average", subMenuPanel);
    crossOver = generateButton("Crossover", subMenuPanel);
    movingCrossOver = generateButton("Moving crossover", subMenuPanel);
    subMenuPanel.add(back);
    this.invokeStockAnalysisEvents(features, name);
  }

  private boolean validateShareName(Features features, JLabel stockLabel,
                                    JTextField stockNameField) {
    try {
      String stockName = stockNameField.getText().trim();
      if (stockName.isEmpty()) {
        stockLabel.setText("Stock name cannot be empty");
        stockLabel.setForeground(Color.RED);
        return false;
      }
      boolean isStock = features.checkStock(stockName);
      if (!isStock) {
        stockLabel.setText(stockName.toUpperCase(Locale.ENGLISH) + " share doesn't exists.");
        stockLabel.setForeground(Color.RED);
        return false;
      }
    } catch (Exception e) {
      return false;
    }
    stockLabel.setText("");
    return true;
  }

  private void invokeStockAnalysisEvents(Features features, String stockName) {
    dailyStockPriceChange.addActionListener(e -> getDailyStockPriceEvent(features, stockName));
    periodStockPriceChange.addActionListener(e -> getPeriodStockPriceEvent(features, stockName));
    xDayMovingAverage.addActionListener(e -> getXDayMovingAverageEvent(features, stockName));
    crossOver.addActionListener(e -> getCrossoverEvent(features, stockName));
    movingCrossOver.addActionListener(e -> getMovingCrossoverEvent(features, stockName));
  }

  private void getMovingCrossoverEvent(Features features, String stockName) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "View  moving crossover on stock " + stockName.toUpperCase());
    StartEndDayPanel startEndDayPanel = getStartEndDayPanel();
    XDayYDayPanel xDayYDayPanel = getxDayYDayPanel();
    JPanel buttonPanel = new JPanel();
    JButton ok = generateButton("Get Crossover", buttonPanel);
    startEndDayPanel.datePanel.add(xDayYDayPanel.xDayPanel);
    startEndDayPanel.datePanel.add(xDayYDayPanel.yDayPanel);
    startEndDayPanel.datePanel.add(buttonPanel);
    startEndDayPanel.datePanel.setBounds(150, 50, 100, 100);
    panelInitiator(startEndDayPanel.datePanel, BorderLayout.NORTH);

    ok.addActionListener(e -> {
      resetDynamicPanel();
      boolean isValidXDay = isValidDay(xDayYDayPanel.xDaysField, xDayYDayPanel.xDayLabel);
      boolean isValidYDay = isValidDay(xDayYDayPanel.yDaysField, xDayYDayPanel.yDayLabel);
      if (checkValidDates(features, stockName, startEndDayPanel)
              && isValidXDay && isValidYDay) {
        performMovingCrossover(features, stockName, startEndDayPanel.startingYearComboBox,
                startEndDayPanel.startingMonthComboBox, startEndDayPanel.startingDayComboBox,
                startEndDayPanel.endingYearComboBox, startEndDayPanel.endingMonthComboBox,
                startEndDayPanel.endingDayComboBox, xDayYDayPanel);
      }
    });
  }

  private void performMovingCrossover(Features features, String stockName,
                                      JComboBox<Integer> startingYearComboBox,
                                      JComboBox<String> startingMonthComboBox,
                                      JComboBox<Integer> startingDayComboBox,
                                      JComboBox<Integer> endingYearComboBox,
                                      JComboBox<String> endingMonthComboBox,
                                      JComboBox<Integer> endingDayComboBox,
                                      XDayYDayPanel xDayYDayPanel) {
    LocalDate startDate =
            features.formatDates(startingYearComboBox.getSelectedItem().toString(),
                    startingMonthComboBox.getSelectedItem().toString(),
                    startingDayComboBox.getSelectedItem().toString());
    LocalDate endDate = features.formatDates(endingYearComboBox.getSelectedItem().toString(),
            endingMonthComboBox.getSelectedItem().toString(),
            endingDayComboBox.getSelectedItem().toString());

    Map<String, List<String>> stockValue = features.movingCrossovers(stockName,
            Integer.parseInt(xDayYDayPanel.xDaysField.getText()),
            Integer.parseInt(xDayYDayPanel.yDaysField.getText()),
            startDate,
            endDate);
    generateCrossoverTable(stockValue);
  }


  private XDayYDayPanel getxDayYDayPanel() {
    JPanel xDayPanel = new JPanel();
    xDayPanel.add(generateLabel("Enter X Days: "));
    JLabel xDayLabel = new JLabel();
    xDayLabel.setFont(getStyle());
    JTextField xDaysField = new JTextField(20);
    xDaysField.setFont(getStyle());
    xDayPanel.add(new JPanel().add(xDaysField));
    xDayPanel.add(xDayLabel);

    JPanel yDayPanel = new JPanel();
    yDayPanel.add(generateLabel("Enter Y Days: "));
    JLabel yDayLabel = new JLabel();
    yDayLabel.setFont(getStyle());
    JTextField yDaysField = new JTextField(20);
    yDaysField.setFont(getStyle());
    yDayPanel.add(new JPanel().add(yDaysField));
    yDayPanel.add(yDayLabel);
    XDayYDayPanel result = new XDayYDayPanel(xDayPanel, xDayLabel, xDaysField, yDayPanel,
            yDayLabel, yDaysField);
    return result;
  }

  private static class XDayYDayPanel {
    private final JPanel xDayPanel;
    private final JLabel xDayLabel;
    private final JTextField xDaysField;
    private final JPanel yDayPanel;
    private final JLabel yDayLabel;
    private final JTextField yDaysField;

    private XDayYDayPanel(JPanel xDayPanel, JLabel xDayLabel, JTextField xDaysField,
                          JPanel yDayPanel, JLabel yDayLabel, JTextField yDaysField) {
      this.xDayPanel = xDayPanel;
      this.xDayLabel = xDayLabel;
      this.xDaysField = xDaysField;
      this.yDayPanel = yDayPanel;
      this.yDayLabel = yDayLabel;
      this.yDaysField = yDaysField;
    }
  }


  private void getCrossoverEvent(Features features, String stockName) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "View crossover on stock " + stockName.toUpperCase());
    StartEndDayPanel startEndDayPanel = getStartEndDayPanel();
    JPanel buttonPanel = new JPanel();
    JButton ok = generateButton("Get Crossover", buttonPanel);
    startEndDayPanel.datePanel.add(buttonPanel);
    startEndDayPanel.datePanel.setBounds(150, 50, 100, 100);
    panelInitiator(startEndDayPanel.datePanel, BorderLayout.NORTH);
    ok.addActionListener(e -> {
      resetDynamicPanel();
      if (checkValidDates(features, stockName, startEndDayPanel)) {
        performCrossover(features, stockName, startEndDayPanel.startingYearComboBox,
                startEndDayPanel.startingMonthComboBox, startEndDayPanel.startingDayComboBox,
                startEndDayPanel.endingYearComboBox, startEndDayPanel.endingMonthComboBox,
                startEndDayPanel.endingDayComboBox);
      }
    });
  }

  private boolean checkValidDates(Features features, String stockName,
                                  StartEndDayPanel startEndDayPanel) {
    boolean isStartDateWorkingDay = validateShareOnDate(stockName, features,
            startEndDayPanel.startingDateLabel,
            startEndDayPanel.startingYearComboBox, startEndDayPanel.startingMonthComboBox,
            startEndDayPanel.startingDayComboBox);
    boolean isEndDateWorkingDay = validateShareOnDate(stockName, features,
            startEndDayPanel.endingDateLabel,
            startEndDayPanel.endingYearComboBox, startEndDayPanel.endingMonthComboBox,
            startEndDayPanel.endingDayComboBox);
    boolean isStartBeforeEnd = validateStartBeforeEnd(startEndDayPanel.startingYearComboBox,
            startEndDayPanel.startingMonthComboBox, startEndDayPanel.startingDayComboBox,
            startEndDayPanel.endingYearComboBox,
            startEndDayPanel.endingMonthComboBox, startEndDayPanel.endingDayComboBox, features,
            startEndDayPanel.errorLabel);

    return isStartBeforeEnd && isStartDateWorkingDay && isEndDateWorkingDay;

  }

  private void performCrossover(Features features, String stockName,
                                JComboBox<Integer> startingYearComboBox,
                                JComboBox<String> startingMonthComboBox,
                                JComboBox<Integer> startingDayComboBox,
                                JComboBox<Integer> endingYearComboBox,
                                JComboBox<String> endingMonthComboBox,
                                JComboBox<Integer> endingDayComboBox) {
    LocalDate startDate =
            features.formatDates(startingYearComboBox.getSelectedItem().toString(),
                    startingMonthComboBox.getSelectedItem().toString(),
                    startingDayComboBox.getSelectedItem().toString());
    LocalDate endDate = features.formatDates(endingYearComboBox.getSelectedItem().toString(),
            endingMonthComboBox.getSelectedItem().toString(),
            endingDayComboBox.getSelectedItem().toString());

    Map<String, List<String>> stockValue = features.crossovers(stockName, startDate, endDate);

    generateCrossoverTable(stockValue);
  }


  private void generateCrossoverTable(Map<String, List<String>> stockValue) {
    JPanel crossoverPanel = new JPanel(new BorderLayout());
    JLabel positiveCrossoverLabel = new JLabel();
    positiveCrossoverLabel.setHorizontalAlignment(SwingConstants.CENTER);
    positiveCrossoverLabel.setFont(getStyle());
    JLabel negativeCrossoverLabel = new JLabel();
    negativeCrossoverLabel.setHorizontalAlignment(SwingConstants.CENTER);
    negativeCrossoverLabel.setFont(getStyle());
    crossoverPanel.add(positiveCrossoverLabel, BorderLayout.NORTH);
    crossoverPanel.add(negativeCrossoverLabel, BorderLayout.SOUTH);
    JPanel tableDataPanel = new JPanel(new FlowLayout());
    crossoverValidation(stockValue, positiveCrossoverLabel, tableDataPanel,
            negativeCrossoverLabel);
    dynamicPanel.add(crossoverPanel, BorderLayout.NORTH);
    dynamicPanel.add(tableDataPanel, BorderLayout.CENTER);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private void crossoverValidation(Map<String, List<String>> stockValue,
                                   JLabel positiveCrossoverLabel, JPanel tableDataPanel,
                                   JLabel negativeCrossoverLabel) {
    if (stockValue.get("POSITIVES").isEmpty()
            && stockValue.get("NEGATIVES").isEmpty()) {
      positiveCrossoverLabel.setText("No crossover dates found for the period specified");
      positiveCrossoverLabel.setForeground(Color.RED);
    } else {
      if (stockValue.get("POSITIVES").isEmpty()) {
        positiveCrossoverLabel.setText("No crossover dates found for positive crossovers");
        positiveCrossoverLabel.setForeground(Color.RED);

      } else {
        generateDataTable(stockValue.get("POSITIVES"), "BUY ON THESE DATES",
                tableDataPanel);
      }
      if (stockValue.get("NEGATIVES").isEmpty()) {
        negativeCrossoverLabel.setText("No crossover dates found for negative crossovers");
        negativeCrossoverLabel.setForeground(Color.RED);
      } else {
        generateDataTable(stockValue.get("NEGATIVES"), "SELL ON THESE DATES",
                tableDataPanel);

      }
    }
  }

  private void generateDataTable(List<String> tableData, String title,
                                 JPanel tableDataPanel) {
    JPanel tablePanel = new JPanel(new FlowLayout());
    setTitle(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Object[][] data = new Object[tableData.size()][1];
    int i = 0;
    for (String date : tableData) {
      data[i][0] = date;
      i++;
    }
    String[] columnNames = {title};
    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    JTable table = new JTable(model);
    table.setRowHeight(30);
    table.setFont(getStyle());
    JTableHeader tableHeader = table.getTableHeader();
    tableHeader.setFont(getStyle());
    scrollPane = new JScrollPane(table);
    scrollPane.setFont(getStyle());
    tablePanel.add(scrollPane, BorderLayout.CENTER);
    tableDataPanel.add(tablePanel);
    mainPanel.add(dynamicPanel, BorderLayout.LINE_END);
    revalidate();
    repaint();
    setVisible(true);
  }

  private void getXDayMovingAverageEvent(Features features, String stockName) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "View X day moving average on stock " +
            stockName.toUpperCase());
    JComboBox<Integer> dayComboBox = generateDay();
    JComboBox<String> monthComboBox = generateMonth();
    JComboBox<Integer> yearComboBox = generateYear(LocalDate.now().getYear());
    JTextField daysField = generateTextField();
    JLabel dateLabel = generateLabel("");
    JLabel daysLabel = generateLabel("");
    JPanel xDayPanel = new JPanel(new GridLayout(0, 1));
    JPanel dayFieldPanel = new JPanel();
    JPanel datePanel = new JPanel();
    generateDate(datePanel, dayComboBox, monthComboBox, yearComboBox, dateLabel,
            "Enter a date: ");
    xDayPanel.add(datePanel);
    textField(dayFieldPanel, daysField, daysLabel, "Enter X Day : ");
    xDayPanel.add(dayFieldPanel);
    JPanel buttonPanel = new JPanel();
    JButton ok = generateButton("Get Average", buttonPanel);
    datePanel.setBounds(150, 50, 100, 100);
    xDayPanel.add(buttonPanel);
    panelInitiator(xDayPanel, BorderLayout.NORTH);
    ok.addActionListener(e -> {
      resetDynamicPanel();
      boolean isWorkingDay = validateShareOnDate(stockName, features, dateLabel,
              yearComboBox, monthComboBox,
              dayComboBox);
      boolean isValidDay = isValidDay(daysField, daysLabel);
      if (isWorkingDay && isValidDay) {
        float stockValue = features.xDayMovingAverage(stockName,
                Integer.parseInt(daysField.getText()),
                features.formatDates(yearComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString(),
                        dayComboBox.getSelectedItem().toString()));
        JLabel stockLabel = new JLabel();
        setSuccessMessage(daysField.getText() + " day moving average of " +
                stockName.toUpperCase(Locale.ENGLISH) + " is $"
                + stockValue, stockLabel);
        dynamicPanel.add(stockLabel, BorderLayout.NORTH);
        mainPanel.add(dynamicPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
      }
    });

  }

  private void textField(JPanel panel, JTextField textField, JLabel errorLabel,
                         String message) {
    panel.add(generateLabel(message));
    panel.add(textField);
    panel.add(errorLabel);
  }

  private void resetDynamicPanel() {
    dynamicPanel.removeAll();
    repaint();
    revalidate();
  }

  private boolean isValidDay(JTextField daysField, JLabel dateLabel) {
    if (!daysField.getText().matches("\\d+")) {
      dateLabel.setText("Enter a valid amount of days.");
      dateLabel.setForeground(Color.RED);
      return false;
    }
    if (daysField.getText().isEmpty() || Integer.parseInt(daysField.getText()) <= 0) {
      dateLabel.setText("Enter a valid amount of days.");
      dateLabel.setForeground(Color.RED);
      return false;
    }
    dateLabel.setText("");
    return true;
  }


  private void getPeriodStockPriceEvent(Features features, String stockName) {
    mainPanel.setBorder(null);
    setBorderTitle(mainPanel, "View period stock price trend on " + stockName.toUpperCase());
    StartEndDayPanel startEndDayPanel = getStartEndDayPanel();
    JPanel buttonPanel = new JPanel();
    JButton ok = generateButton("Get Price", buttonPanel);
    startEndDayPanel.datePanel.add(buttonPanel);
    startEndDayPanel.datePanel.setBounds(150, 50, 100, 100);
    panelInitiator(startEndDayPanel.datePanel, BorderLayout.NORTH);
    ok.addActionListener(e -> {
      resetDynamicPanel();
      if (checkValidDates(features, stockName, startEndDayPanel)) {
        performPeriodStockPrice(features, stockName, startEndDayPanel.startingYearComboBox,
                startEndDayPanel.startingMonthComboBox, startEndDayPanel.startingDayComboBox,
                startEndDayPanel.endingYearComboBox, startEndDayPanel.endingMonthComboBox,
                startEndDayPanel.endingDayComboBox, startEndDayPanel.startingDateLabel);
      }
    });
  }

  private StartEndDayPanel getStartEndDayPanel() {
    JComboBox<Integer> startingDayComboBox = generateDay();
    JComboBox<String> startingMonthComboBox = generateMonth();
    JComboBox<Integer> startingYearComboBox = generateYear(LocalDate.now().getYear());
    JComboBox<Integer> endingDayComboBox = generateDay();
    JComboBox<String> endingMonthComboBox = generateMonth();
    JComboBox<Integer> endingYearComboBox = generateYear(LocalDate.now().
            plusYears(100).getYear());
    JPanel datePanel = new JPanel(new GridLayout(0, 1));
    JLabel startingDateLabel = generateLabel("");
    JPanel startingDatePanel = new JPanel();
    generateDate(startingDatePanel, startingDayComboBox, startingMonthComboBox,
            startingYearComboBox,
            startingDateLabel, "Enter start date : ");
    JPanel endingDatePanel = new JPanel();
    JLabel endingDateLabel = generateLabel("");
    generateDate(endingDatePanel, endingDayComboBox, endingMonthComboBox, endingYearComboBox,
            endingDateLabel, "Enter end date : ");
    JLabel errorLabel = generateLabel("");
    errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
    datePanel.add(startingDatePanel, BorderLayout.NORTH);
    datePanel.add(endingDatePanel, BorderLayout.CENTER);
    datePanel.add(errorLabel);
    return new StartEndDayPanel(startingDayComboBox,
            startingMonthComboBox, startingYearComboBox, endingDayComboBox,
            endingMonthComboBox, endingYearComboBox, datePanel, startingDateLabel,
            endingDateLabel, errorLabel);
  }

  private static class StartEndDayPanel {
    private final JComboBox<Integer> startingDayComboBox;
    private final JComboBox<String> startingMonthComboBox;
    private final JComboBox<Integer> startingYearComboBox;
    private final JComboBox<Integer> endingDayComboBox;
    private final JComboBox<String> endingMonthComboBox;
    private final JComboBox<Integer> endingYearComboBox;
    private final JPanel datePanel;
    private final JLabel startingDateLabel;
    private final JLabel endingDateLabel;

    private final JLabel errorLabel;

    private StartEndDayPanel(JComboBox<Integer> startingDayComboBox,
                             JComboBox<String> startingMonthComboBox,
                             JComboBox<Integer> startingYearComboBox,
                             JComboBox<Integer> endingDayComboBox,
                             JComboBox<String> endingMonthComboBox,
                             JComboBox<Integer> endingYearComboBox,
                             JPanel datePanel, JLabel startingDateLabel,
                             JLabel endingDateLabel, JLabel errorLabel) {
      this.errorLabel = errorLabel;
      this.startingDayComboBox = startingDayComboBox;
      this.startingMonthComboBox = startingMonthComboBox;
      this.startingYearComboBox = startingYearComboBox;
      this.endingDayComboBox = endingDayComboBox;
      this.endingMonthComboBox = endingMonthComboBox;
      this.endingYearComboBox = endingYearComboBox;
      this.datePanel = datePanel;
      this.startingDateLabel = startingDateLabel;
      this.endingDateLabel = endingDateLabel;
    }
  }

  private void performPeriodStockPrice(Features features, String stockName,
                                       JComboBox<Integer> startingYearComboBox,
                                       JComboBox<String> startingMonthComboBox,
                                       JComboBox<Integer> startingDayComboBox,
                                       JComboBox<Integer> endingYearComboBox,
                                       JComboBox<String> endingMonthComboBox,
                                       JComboBox<Integer> endingDayComboBox,
                                       JLabel startingDateLabel) {
    LocalDate startDate =
            features.formatDates(startingYearComboBox.getSelectedItem().toString(),
                    startingMonthComboBox.getSelectedItem().toString(),
                    startingDayComboBox.getSelectedItem().toString());
    LocalDate endDate = features.formatDates(endingYearComboBox.getSelectedItem().toString(),
            endingMonthComboBox.getSelectedItem().toString(),
            endingDayComboBox.getSelectedItem().toString());
    float stockValue;
    stockValue = features.periodStockPriceChange(stockName, startDate, endDate, startingDateLabel);
    displayStockTrend(stockName, stockValue);
  }

  private boolean validateStartBeforeEnd(JComboBox<Integer> startingYear,
                                         JComboBox<String> startingMonth,
                                         JComboBox<Integer> startingDay,
                                         JComboBox<Integer> endingYear,
                                         JComboBox<String> endingMonth,
                                         JComboBox<Integer> endingDay, Features features,
                                         JLabel label) {
    if (features.isStartDateBeforeEndDate(startingYear.getSelectedItem().toString(),
            startingMonth.getSelectedItem().toString(),
            startingDay.getSelectedItem().toString(),
            endingYear.getSelectedItem().toString(),
            endingMonth.getSelectedItem().toString(),
            endingDay.getSelectedItem().toString())) {
      label.setText("");
      return true;
    } else {
      label.setText("Please provide a valid period");
      label.setForeground(Color.RED);
      return false;
    }
  }


  private void getDailyStockPriceEvent(Features features, String stockName) {
    DatePanel singleDatePanel = getDatePanel("View daily stock price trend on "
            + stockName.toUpperCase(), "Get Price");
    panelInitiator(singleDatePanel.panel, BorderLayout.NORTH);
    singleDatePanel.button.addActionListener(e -> {
      resetDynamicPanel();
      boolean isWorkingDay = validateShareOnDate(stockName, features, singleDatePanel.errorLabel,
              singleDatePanel.yearComboBox, singleDatePanel.monthComboBox,
              singleDatePanel.dayComboBox);
      if (isWorkingDay) {
        float stockValue = features.dailyStockPriceChange(stockName,
                features.formatDates(singleDatePanel.yearComboBox.getSelectedItem().toString(),
                        singleDatePanel.monthComboBox.getSelectedItem().toString(),
                        singleDatePanel.dayComboBox.getSelectedItem().toString()),
                singleDatePanel.errorLabel);
        displayStockTrend(stockName, stockValue);
      }
    });
    revalidate();
    repaint();
  }

  private void displayStockTrend(String stockName, float stockValue) {
    JLabel stockLabel = new JLabel();
    stockLabel.setHorizontalAlignment(SwingConstants.CENTER);
    stockLabel.setFont(getStyle());
    if (stockValue > 0) {
      setSuccessMessage(stockName.toUpperCase(Locale.ENGLISH) + " has gained $"
              + stockValue, stockLabel);
    } else if (stockValue < 0) {
      setErrorMessage(stockName.toUpperCase(Locale.ENGLISH) + " has lost $"
              + stockValue, stockLabel);
    } else {
      stockLabel.setText(stockName.toUpperCase(Locale.ENGLISH) + " has not gained nor lost");
    }
    dynamicPanel.add(stockLabel, BorderLayout.NORTH);
    mainPanel.add(dynamicPanel, BorderLayout.CENTER);
    revalidate();
    repaint();
  }

  private boolean validateShareOnDate(String stockName, Features features, JLabel label,
                                      JComboBox<Integer> year,
                                      JComboBox<String> month,
                                      JComboBox<Integer> day) {
    if (month == null || year == null || day == null) {
      label.setText("Please select a date");
      label.setForeground(Color.RED);
    }

    if (!isValidDate(features, label, year, month, day)) {
      return false;
    }

    boolean isValidOnDate = features.checkShareOnDate(year.getSelectedItem().toString(),
            month.getSelectedItem().toString(),
            day.getSelectedItem().toString(), stockName);
    if (isValidOnDate) {
      label.setText("");
      revalidate();
      repaint();
      return true;

    } else {
      label.setText("Share not available on this date. ");
      label.setForeground(Color.RED);
      revalidate();
      repaint();
      return false;
    }
  }

  private boolean isValidDate(Features features, JLabel label, JComboBox<Integer> year,
                              JComboBox<String> month, JComboBox<Integer> day) {
    if (!features.isValidDate(year.getSelectedItem().toString(),
            month.getSelectedItem().toString(),
            day.getSelectedItem().toString())) {
      setErrorMessage("Provide a valid date", label);
      repaint();
      revalidate();
      return false;
    }
    return true;
  }


  private MainMenuTextFieldPanel getPanel(String title, String fieldLabel, String buttonText) {
    portfolioNameField = generateTextField();
    setBorderTitle(mainPanel, title);
    JLabel portfolioLabel = generateLabel("");
    JPanel portfolioOperationPanel = new JPanel(new FlowLayout());
    JLabel nameLabel = generateLabel(fieldLabel);
    portfolioOperationPanel.add(nameLabel);
    portfolioOperationPanel.add(portfolioNameField);
    JButton submit = generateButton(buttonText, portfolioOperationPanel);
    portfolioOperationPanel.add(portfolioLabel);
    return new MainMenuTextFieldPanel(portfolioLabel, portfolioOperationPanel,
            submit, portfolioNameField);
  }

  private static class MainMenuTextFieldPanel {
    private final JLabel errorLabel;
    private final JTextField textField;
    private final JPanel fieldPanel;
    private final JButton submit;

    private MainMenuTextFieldPanel(JLabel errorLabel, JPanel fieldPanel,
                                   JButton submit, JTextField textField) {
      this.errorLabel = errorLabel;
      this.textField = textField;
      this.fieldPanel = fieldPanel;
      this.submit = submit;
    }
  }

}
