package com.company;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.ArrayList;


abstract class Parser {

    public static ArrayList<Student> parseCSV(String name) throws Exception{

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withEscapeChar(',')
                .withIgnoreQuotations(true)
                .build();
        CSVReader reader = new CSVReaderBuilder(new FileReader(name))
                .withCSVParser(parser)
                .build();

        var nameOfCourse = name.split("\\.")[0];
        var result = new ArrayList<Student>();
        var themes = reader.readNext();
        var tasks = reader.readNext();
        var maxPoints = reader.readNext();

        String[] nextLine;
        while ((nextLine = reader.readNext())!= null){
            var fio = nextLine[0];
            var group = nextLine[1];
            var pointsPerCourse = nextLine[2];
            result.add(new Student(fio,group, new Course(nameOfCourse,GetListOfThemes(nextLine, themes,tasks,maxPoints), Integer.parseInt(pointsPerCourse))));
        }

        return result;
    }

    private static ArrayList<Theme> GetListOfThemes(String[] line, String[] themes, String[] tasks, String[] maxPoints){

        var result = new ArrayList<Theme>();
        String nameOfTheme = "";
        var score = 0;
        var listOfTasks = new ArrayList<Task>();
        var flag = false;

        for (var i=3;i<themes.length;i++){

            if(!themes[i].equals("")){
                flag = true;
            }
            else {
                flag = false;
            }

            if(flag && i==3){
                nameOfTheme = themes[i];
                score = Integer.parseInt(line[i]);
            }

            if(flag && i!=3){
                result.add(new Theme(nameOfTheme, listOfTasks, score));
                nameOfTheme = themes[i];
                score = Integer.parseInt(line[i]);
                listOfTasks = new ArrayList<Task>();

            }

            if (!flag){
                listOfTasks.add(new Task(tasks[i],Integer.parseInt(line[i]),Integer.parseInt(maxPoints[i])));
            }

        }

        result.add(new Theme(nameOfTheme, listOfTasks, score));

        return result;
    }

    public static ArrayList<Student> GetStudents(String name) throws Exception {
        var stds = Parser.parseCSV(name);
        var vkParseRes = VkApi.getVkIds();
        for (var i =0;i<stds.size();i++){
            if (i%5==0){
                Thread.sleep(1500);
            }
            var std = stds.get(i);
            if (vkParseRes.containsKey(std.getFio())){
                VkApi.setFieldsFromVk(vkParseRes.get(std.getFio()), std);
            }
        }
        return stds;
    }
}
