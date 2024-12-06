package Frontend;

import javax.swing.*;
import java.awt.*;

public class CustomRender implements ListCellRenderer<JPanel> {

    @Override
    public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel value, int index, boolean isSelected, boolean cellHasFocus) {
        if(isSelected){
            value.setBackground(list.getSelectionBackground());
        }
        else{
            value.setBackground(list.getBackground());
        }
        return value;
    }
}
