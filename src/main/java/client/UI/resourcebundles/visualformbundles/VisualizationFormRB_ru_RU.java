package client.UI.resourcebundles.visualformbundles;

import java.util.ListResourceBundle;

public class VisualizationFormRB_ru_RU extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"backToTheTableButton", "Вернуться к таблице"},
                {"personHeightLabel", "Рост человека"},
                {"coordinateXLabel", "Координата X"},
                {"coordinateYLabel", "Координата Y"}
        };
        return contents;
    }
}
