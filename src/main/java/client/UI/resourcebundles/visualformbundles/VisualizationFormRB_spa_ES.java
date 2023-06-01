package client.UI.resourcebundles.visualformbundles;

import java.util.ListResourceBundle;

public class VisualizationFormRB_spa_ES extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"backToTheTableButton", "Volver a la tabla"},
                {"personHeightLabel", "Altura de la persona"},
                {"coordinateXLabel", "Coordenada X"},
                {"coordinateYLabel", "Coordenada Y"}
        };
        return contents;
    }
}
