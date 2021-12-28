package com.company;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;

public abstract class Graph {
    public static void createGenderStatisticsGraph(ArrayList<Integer> counts) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Female", counts.get(0));
        dataset.setValue("Male", counts.get(1));
        JFreeChart chart = ChartFactory.createPieChart(
                "Gender statistics",
                dataset,
                true,
                true,
                false);

        int width = 640;
        int height = 480;
        File pieChart = new File( "genderStatistics.jpeg" );
        ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
    }

    public static void createCityStatisticsGraph(HashMap<String, Integer> dict) throws IOException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for( var i=0; i<dict.size(); i++){
            var key = (String)dict.keySet().toArray()[i];
            dataset.setValue(key,dict.get(key));
        }
        JFreeChart chart = ChartFactory.createPieChart(
                "City statistics",
                dataset,
                true,
                true,
                false);
        int width = 640;
        int height = 480;
        File pieChart = new File( "CityStatistics.jpeg" );
        ChartUtilities.saveChartAsJPEG( pieChart , chart , width , height );
    }

}
