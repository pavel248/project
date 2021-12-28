package com.company;

public class Main
{
    public static void main(String[] args) throws Exception {
        DB.Conn();
        Graph.createGenderStatisticsGraph(DB.getGenderStatistics());
        Graph.createCityStatisticsGraph(DB.getCityStatistics());
        DB.CloseDB();




    }
}

