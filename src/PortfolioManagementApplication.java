import controller.Features;
import controller.GUIController;
import controller.PortfolioControllerImpl;
import view.ManagementGUIViewImpl;
import model.ManagementModel;
import model.ManagementModelImpl;
import view.ManagementGUIView;
import view.ManagementView;
import view.ManagementViewImpl;

/**
 * The class initializes the model, view and the controller. It calls the controller method.
 */
public class PortfolioManagementApplication {

  /**
   * The method runs the mvc program, based on the argument specified at runtime it will
   * display the Graphical User Interface or the Text Based view.
   *
   * @param args Unused command line arguments.
   */
  public static void main(String[] args) {
    try {
      ManagementModel model = new ManagementModelImpl();
      if (args.length >= 1 && args[0].trim().equalsIgnoreCase("text")) {
        ManagementView view = new ManagementViewImpl(System.in, System.out);
        PortfolioControllerImpl controller = new PortfolioControllerImpl(model, view);
        controller.run();
      } else if (args.length >= 1 && args[0].trim().equalsIgnoreCase("gui")) {
        ManagementGUIView view = new ManagementGUIViewImpl();
        Features controller = new GUIController(model);
        controller.setView(view);
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}
