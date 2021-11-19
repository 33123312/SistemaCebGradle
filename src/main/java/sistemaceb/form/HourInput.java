package sistemaceb.form;

import SpecificViews.LinearHorizontalLayout;
import java.awt.*;

public class HourInput extends FormElement{
    limitedImput hourInput;
    limitedImput minutesInput;

    public HourInput(String title) {
        super(title);
        deployInputs();
    }

    private void deployInputs(){
        LinearHorizontalLayout layout = new LinearHorizontalLayout();
            layout.setOpaque(false);

        hourInput = createLimitedInput();
        minutesInput = createLimitedInput();

        layout.addElement(hourInput);
        layout.addElement(minutesInput);

        addErrorChecker(new ErrorChecker() {
            @Override
            public String checkForError(String response) {
                if (response.equals("forErr"))
                    return "Error: mas de 23 horas o 60 min";

                return "";
            }
        });

        addElement(layout);
    }

    @Override
    protected String getResponseConfig() {
        String hour = hourInput.getText();
        String minutes = minutesInput.getText();


        if (hour.isEmpty() || minutes.isEmpty())
            return super.getResponseConfig();

        if(Integer.parseInt(hour) > 23 || Integer.parseInt(hour) > 60)
            return "forErr";

        String completeHOur = hour + ":" + minutes + ":00";


        return completeHOur;
    }

    private LimitedNumberInput createLimitedInput() {
        LimitedNumberInput limitedNumberInput = new LimitedNumberInput();
            limitedNumberInput.setLimit(2);
            limitedNumberInput.OnlyAllowNumbers();
            limitedNumberInput.setFillsCeros(true);

        limitedNumberInput.setPreferredSize(new Dimension(30,0));
        return limitedNumberInput;
    }

    @Override
    public void setResponse(String txt) {
        super.setResponse(txt);
        trimHour(txt);

    }

    private void trimHour(String hour){
        char[] charArray = hour.toCharArray();
        String hourChar = new StringBuilder().append(charArray[0]).append(charArray[1]).toString();
        String minuteChar = new StringBuilder().append(charArray[3]).append(charArray[4]).toString();

        hourInput.setText(hourChar);
        minutesInput.setText(minuteChar);

    }

    @Override
    public void useDefval() {

    }
}
