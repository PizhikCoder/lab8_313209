package client.UI.resourcebundles.visualformbundles;

import java.util.ListResourceBundle;

public class VisualizationFormRB_est_EST extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        Object[][] contents = {
                {"backToTheTableButton", "Tagasi tabelisse"},
                {"personHeightLabel", "Inimese pikkus"},
                {"coordinateXLabel", "X-koordinaat"},
                {"coordinateYLabel", "Y-koordinaat"}
        };
        return contents;
    }
}
