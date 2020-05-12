package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main extends Application {
    final static HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    World world = new World();
    ArrayList<Country> mainChartData = new ArrayList<Country>();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
    XYChart.Series totalCasesSeries = new XYChart.Series();
    XYChart.Series newCasesSeries = new XYChart.Series();
    XYChart.Series totalRecoveriesSeries = new XYChart.Series();
    XYChart.Series newRecoveriesSeries = new XYChart.Series();
    XYChart.Series totalDeathsSeries = new XYChart.Series();
    XYChart.Series newDeathsSeries = new XYChart.Series();

    @Override
    public void start(Stage primaryStage) throws Exception{
        // HTTP Request to fetch covid country data
        HttpRequest req = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://api.covid19api.com/summary"))
                .build();

        // Bar Chart components for the main chart containing updated country data
        xAxis.setLabel("Country");
        yAxis.setLabel("Confirmed Cases");
        yAxis.setForceZeroInRange(false);
        totalCasesSeries.setName("Total");
        newCasesSeries.setName("New Cases");
        totalRecoveriesSeries.setName("Recoveries");
        newRecoveriesSeries.setName("New Recoveries");
        totalDeathsSeries.setName("Deaths");
        newDeathsSeries.setName("New Deaths");


        try {
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            // The API can't handle a lot of requests
            // Printing out the status code so that I know if the failure was due to
            // the api sending back a 429
            System.out.println(res.statusCode());

            // Parse the body using gson library
            JsonElement root = JsonParser.parseString(res.body());

            // Response should be an array of countries
            // We want to parse each country into it's own Country class
            // and add that Country to the World class
            root.getAsJsonObject().get("Countries").getAsJsonArray().forEach((country) -> {
                JsonObject c = country.getAsJsonObject();
                world.add(new Country(
                        // When we parse the name of the country, it is parsed
                        // including the double quotes. I don't want that.
                        c.get("Country").toString().replaceAll("\"", ""),
                        c.get("TotalConfirmed").getAsInt(),
                        c.get("NewConfirmed").getAsInt(),
                        c.get("TotalRecovered").getAsInt(),
                        c.get("NewRecovered").getAsInt(),
                        c.get("TotalDeaths").getAsInt(),
                        c.get("NewDeaths").getAsInt()
                ));
            });

            // Border pane is used to separate the different types of controls/charts
            BorderPane bp = new BorderPane();

            bp.setTop(setTitle());
            bp.setLeft(setOptions());
            bp.setCenter(setMainChart());
            bp.setBottom(setUpdateButton());
            bp.setRight(setRightPane());
            primaryStage.setTitle("Covid-19");
            primaryStage.setScene(new Scene(bp, 1280, 900));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This function initializes the scroll pane containing different options to be selected
     * to display in the main chart.
     *
     * @return ScrollPane sp
     */
    public ScrollPane setOptions() {
        ScrollPane sp = new ScrollPane();
        VBox vb = new VBox();
        world.getCountries().forEach((key, country) -> {
            CheckBox cb = new CheckBox(key);

            // Because I am Canadian, I want to just automatically view Canada's
            // data first thing.
            if (key.equals("Canada")) {
                cb.setSelected(true);
            }

            // When a country is selected, add it to the ArrayList of countries
            // to be added to the main chart
            // If it is deselected, remove it from the chart
            cb.setOnAction((e) -> {
                if (cb.isSelected()) {
                    mainChartData.add(world.getCountry(cb.getText()));
                } else {
                    mainChartData.remove(world.getCountry(cb.getText()));
                }

            });
            cb.setPadding(new Insets(5,5,5,5));
            vb.getChildren().add(cb);

        });

        sp.setContent(vb);
        return sp;
    }

    /**
     * Function that initializes the main chart
     *
     * @return VBox vb
     */
    public VBox setMainChart() {
        // I automatically want to add Canada to the chart for viewing
        mainChartData.add(world.getCountry("Canada"));

        this.addDataToChart(mainChartData, bc);

        VBox vb = new VBox(bc);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    /**
     * Initializes the top chart in the right pane with World data regarding total and new cases
     *
     * @return VBox vb
     */
    public VBox setWorldTotalChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);

        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Confirmed Cases");

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Total");

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("New");

        dataSeries1.getData().add(
                new XYChart.Data<String, Number>("World", world.getTotalConfirmed())
        );

        dataSeries2.getData().add(
                new XYChart.Data<String, Number>("World", world.getNewConfirmed())
        );

        bc.getData().addAll(dataSeries1, dataSeries2);

        VBox vb = new VBox(bc);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    /**
     * Initializes the middle chart in the right pane with World data regarding total and new recoveries
     *
     * @return VBox vb
     */
    public VBox setWorldRecoveriesChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);

        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Recovered Cases");

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Total");

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("New");

        dataSeries1.getData().add(
                new XYChart.Data<String, Number>("World", world.getTotalRecovered())
        );

        dataSeries2.getData().add(
                new XYChart.Data<String, Number>("World", world.getNewRecovered())
        );

        bc.getData().addAll(dataSeries1, dataSeries2);

        VBox vb = new VBox(bc);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    /**
     * Initializes the bottom chart in the right pane with World data regarding total and new deaths
     *
     * @return VBox vb
     */
    public VBox setWorldDeathsChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setForceZeroInRange(false);

        BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        bc.setTitle("Deaths");

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName("Total");

        XYChart.Series dataSeries2 = new XYChart.Series();
        dataSeries2.setName("New");

        dataSeries1.getData().add(
                new XYChart.Data<String, Number>("World", world.getTotalDeaths())
        );

        dataSeries2.getData().add(
                new XYChart.Data<String, Number>("World", world.getNewDeaths())
        );

        bc.getData().addAll(dataSeries1, dataSeries2);

        VBox vb = new VBox(bc);
        vb.setAlignment(Pos.CENTER);
        return vb;
    }

    /**
     * Simply sets the content of the right pane of the border pane
     *
     * @return VBox vb
     */
    public VBox setRightPane() {
        return new VBox(setWorldTotalChart(), setWorldRecoveriesChart(), setWorldDeathsChart());
    }

    /**
     * Creates an update button and wraps it in an HBox
     *
     * @return HBox hb
     */
    public HBox setUpdateButton() {
        Button btn = new Button("Update");

        // On click of the button, we want to add any selected countries to the main BarChart
        btn.setOnAction((e) -> {
            this.addDataToChart(mainChartData, bc);
        });

        HBox hb = new HBox(btn);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10, 10, 10, 10));
        return hb;
    }

    /**
     * Simply creates a new Label for the application title, and wraps it in an HBox
     *
     * @return HBox hb
     */
    public HBox setTitle() {
        Label lbl = new Label("Covid-19 Data");
        HBox hb = new HBox(lbl);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10, 10, 10, 10));
        return hb;
    }

    /**
     * Intended to add the data contained in an ArrayList to the provided BarChart
     *
     * @param c ArrayList
     * @param bc BarChart
     */
    public void addDataToChart(ArrayList<Country> c, BarChart<String, Number> bc) {
        // Clear all of the data from the BarChart first
        totalCasesSeries.getData().clear();
        newCasesSeries.getData().clear();
        bc.getData().clear();

        // Add all of the data to the BarChart
        c.forEach((country) -> {
            totalCasesSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getTotalConfirmed())
            );

            newCasesSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getNewConfirmed())
            );

            totalRecoveriesSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getTotalRecovered())
            );

            newRecoveriesSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getNewRecovered())
            );

            totalDeathsSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getTotalDeaths())
            );

            newDeathsSeries.getData().add(
                    new XYChart.Data<String, Number>(country.getName(), country.getNewDeaths())
            );
        });

        bc.getData().addAll(
                totalCasesSeries, newCasesSeries, totalRecoveriesSeries,
                newRecoveriesSeries, totalDeathsSeries, newDeathsSeries
        );
    }
}
