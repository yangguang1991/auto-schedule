package com.example.hive;

import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: kevin yang
 * @Description:
 * @Date: create in 2020/11/30 17:24
 */
public class HiveDao {

    @Data
    public static class Result {
        String routeid;
    }

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static List<Result> getResult() {
        try {
            Class.forName(driverName);
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection con = null;
        HashMap map = new HashMap();
        String sql;
        ResultSet res;
        List<Result> resultList = new ArrayList<>();
        try {
            long a = System.currentTimeMillis();
            System.out.println("a=" + a);
            con = DriverManager.getConnection("jdbc:hive2://cdh03-xin:10000/default", "hadoop", "hadoop");
            Statement stmt = con.createStatement();
            sql = " select  routeid  from tmp.auto_schedule_bus_plan_avg_spend_time ";
            System.out.println("running sql: " + sql);
            stmt.execute(sql);
            res = stmt.executeQuery(sql);
            while (res.next()) {
                System.out.println(res.getString(1));
                Result result = new Result();
                result.setRouteid(res.getString(1));
                resultList.add(result);
            }
            System.out.println("b=" + (System.currentTimeMillis() - a));
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static void main(String[] args) {
        getResult();
    }
}
