package client.UI.resourcebundles.visualformbundles;

import java.util.ListResourceBundle;

public class VisualizationFormRB_cro_HRV extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"backToTheTableButton", "Povratak na tablicu"},
                {"personHeightLabel", "Visina osobe"},
                {"coordinateXLabel", "Koordinata X"},
                {"coordinateYLabel", "Koordinata Y"}
        };
        return contents;
    }
}
