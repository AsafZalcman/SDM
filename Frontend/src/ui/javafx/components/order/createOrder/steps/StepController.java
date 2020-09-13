package ui.javafx.components.order.createOrder.steps;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class StepController {
     protected SimpleBooleanProperty m_StepComplete = new SimpleBooleanProperty(false);
     public void addBind(BooleanProperty propertyToBind)
     {
          propertyToBind.bind(m_StepComplete);
     }
     public abstract String getStepName();
     public abstract void onStepFinish();

}
