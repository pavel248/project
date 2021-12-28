package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class DB {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:project.s3db");

        System.out.println("База Подключена!");
    }

    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'students' ('student_id' INTEGER not null constraint student_pk primary  key autoincrement, 'fio' text,'gender' text, 'group' text, 'city' text, 'dateOfBirth' text);");
        statmt.execute("CREATE TABLE if not exists 'courses' ('course_id' INTEGER not null constraint course_pk primary  key autoincrement, 'name' text);");
        statmt.execute("CREATE TABLE if not exists 'course_student' ('student_id' INTEGER not null constraint course_student_fk references students, 'course_id' integer not null constraint course_student_fk references courses);");
        statmt.execute("CREATE TABLE if not exists 'themes' ('theme_id' INTEGER not null constraint theme_pk primary  key autoincrement, 'name' text);");
        statmt.execute("CREATE TABLE if not exists 'course_theme' ('theme_id' INTEGER not null constraint course_themes_fk references themes, 'course_id' integer not null constraint course_theme_fk references courses);");
        statmt.execute("CREATE TABLE if not exists 'theme_student' ('theme_id' INTEGER not null constraint theme_student_fk references themes, 'student_id' integer not null constraint theme_student_fk references students, 'score' integer );");
        statmt.execute("CREATE TABLE if not exists 'task_student' ('task_id' INTEGER not null constraint task_student_fk references tasks, 'student_id' integer not null constraint task_student_fk references students, 'score' integer );");
        statmt.execute("CREATE TABLE if not exists 'tasks' ('task_id' INTEGER not null constraint task_pk primary  key autoincrement, 'name' text);");
        statmt.execute("CREATE TABLE if not exists 'theme_task' ('theme_id' INTEGER not null constraint theme_task_fk references themes, 'task_id' integer not null constraint theme_task_fk references tasks);");


        System.out.println("Таблица создана или уже существует.");
    }

    public static void WriteCourseTables(Course course) throws SQLException {
        PreparedStatement ps2 = null;
        try {
            ps2 = conn.prepareStatement("insert or ignore into courses ('name') values (?)");
            ps2.setString(1, course.getNameOfCourse());
            ps2.executeUpdate();
            System.out.println("Доб курс");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ps2 = conn.prepareStatement("select course_id from courses where name=?");
            ps2.setString(1, course.getNameOfCourse());
            resSet = ps2.executeQuery();
            resSet.next();
            var courseId = resSet.getInt("course_id");

            for (var theme : course.getListOfThemes()) {
                ps2 = conn.prepareStatement("insert or ignore into themes('name') values (?)");
                ps2.setString(1, theme.getName());
                ps2.executeUpdate();
                System.out.println("Доб тема");

                ps2 = conn.prepareStatement("select theme_id from themes where name=?");
                ps2.setString(1, theme.getName());
                resSet = ps2.executeQuery();
                resSet.next();
                var themeId = resSet.getInt("theme_id");

                ps2 = conn.prepareStatement("insert into course_theme ('theme_id', 'course_id') values (?,?)");
                ps2.setInt(1, themeId);
                ps2.setInt(2, courseId);
                ps2.executeUpdate();
                System.out.println("Доб курс-тема");

                for (var task: theme.getListOfTasks()){
                    ps2 = conn.prepareStatement("insert or ignore into tasks('name') values (?)");
                    ps2.setString(1, task.getNameOfTask());
                    ps2.executeUpdate();
                    System.out.println("Доб задача");

                    ps2 = conn.prepareStatement("select task_id from tasks where name=?");
                    ps2.setString(1, task.getNameOfTask());
                    resSet = ps2.executeQuery();
                    resSet.next();
                    var taskId = resSet.getInt("task_id");

                    ps2 = conn.prepareStatement("insert into theme_task ('theme_id', 'task_id') values (?,?)");
                    ps2.setInt(1, themeId);
                    ps2.setInt(2, taskId);
                    ps2.executeUpdate();
                    System.out.println("Доб тема-задача");


                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void WriteStudentsScores(Student std) throws SQLException {
        PreparedStatement ps2 = null;
        var course = std.getListOfCourses().get(0);
        ps2 = conn.prepareStatement("select student_id from students where fio=?");
        ps2.setString(1, std.getFio());
        resSet = ps2.executeQuery();
        resSet.next();
        var stdId = resSet.getInt("student_id");

        ps2 = conn.prepareStatement("select course_id from courses where name=?");
        ps2.setString(1, course.getNameOfCourse());
        resSet = ps2.executeQuery();
        resSet.next();
        var courseId = resSet.getInt("course_id");

        ps2 = conn.prepareStatement("insert into course_student ('student_id', 'course_id') values (?,?)");
        ps2.setInt(1, stdId);
        ps2.setInt(2, courseId);
        ps2.executeUpdate();
        System.out.println("Доб курс-студент");

        for (var theme: course.getListOfThemes()){
            ps2 = conn.prepareStatement("select theme_id from themes where name=?");
            ps2.setString(1, theme.getName());
            resSet = ps2.executeQuery();
            resSet.next();
            var themeId = resSet.getInt("theme_id");

            ps2 = conn.prepareStatement("insert into theme_student('theme_id', 'student_id', 'score') values (?,?,?)");
            ps2.setInt(1, themeId);
            ps2.setInt(2, stdId);
            ps2.setInt(3, theme.getScore());
            ps2.executeUpdate();

            for (var task: theme.getListOfTasks()){
                ps2 = conn.prepareStatement("select task_id from tasks where name=?");
                ps2.setString(1, task.getNameOfTask());
                resSet = ps2.executeQuery();
                resSet.next();
                var taskId = resSet.getInt("task_id");

                ps2 = conn.prepareStatement("insert into task_student('task_id', 'student_id', 'score') values (?,?,?)");
                ps2.setInt(1, themeId);
                ps2.setInt(2, stdId);
                ps2.setInt(3, task.getScore());
                ps2.executeUpdate();
            }
        }


    }


    public static void WriteStudent(Student std) throws SQLException
    {
        PreparedStatement ps = conn.prepareStatement("insert into students ('fio','gender' , 'group', 'city', 'dateOfBirth') values (?,?,?,?,?)");
        ps.setString(1, std.getFio());
        ps.setString(2, std.getSex());
        ps.setString(3,std.getGroup());
        ps.setString(4, std.getCity());
        ps.setString(5,std.getDateOfBirth());
        ps.executeUpdate();
        System.out.println("Доб студент");
    }

    public static void WriteDB(ArrayList<Student> list) throws SQLException {
        var course = list.get(0).getListOfCourses().get(0);
        WriteCourseTables(course);
        for (var std: list){
            WriteStudent(std);
            WriteStudentsScores(std);
        }

    }

    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }


    public static ArrayList<Integer> getGenderStatistics () throws SQLException {
        PreparedStatement ps2 = null;
        ArrayList<Integer> result = new ArrayList<Integer>();
        ps2 = conn.prepareStatement("select gender, count (*) as count from students where (gender like \"female\")");
        resSet = ps2.executeQuery();
        var gender = resSet.getString("gender");
        var femaleCount = resSet.getInt("count");
        ps2 = conn.prepareStatement("select gender, count (*) as count from students where (gender like \"male\")");
        resSet = ps2.executeQuery();
        var maleCount = resSet.getInt("count");
        result.add(femaleCount);
        result.add(maleCount);
        return result;
    }

    public static HashMap<String, Integer> getCityStatistics() throws SQLException {
        PreparedStatement ps2 = null;
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        ps2 = conn.prepareStatement("select distinct city from students");
        ResultSet resSet2 = ps2.executeQuery();
        while(resSet2.next()) {
            var city = resSet2.getString("city");
            ps2 = conn.prepareStatement("select count(*) as count from students where city = ?");
            ps2.setString(1,city);
            resSet = ps2.executeQuery();
            var count = resSet.getInt("count");
            result.put(city, count);
        }
        return result;
    }

}
