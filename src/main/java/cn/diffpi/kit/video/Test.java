package cn.diffpi.kit.video;//package util;
//
//import ch.qos.logback.core.db.dialect.DBUtil;
//
//import java.io.File;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Test {
//  public static void main(String[] args) {
//
//      open(findSame());
//
//  }
//  // 罗列相同文件的文件大小集合
//  public static List<Long> findSame() {
//      Connection connection = DBUtil.getConn();
//      List<Long> sizes = new ArrayList<>();
//      String sql = "SELECT size_b,count(id) as count FROM video_info GROUP BY size_b HAVING count > 1";
//      PreparedStatement preparedStatement = null;
//      try {
//          preparedStatement = connection.prepareStatement(sql);
//          ResultSet resultSet = preparedStatement.executeQuery();
//          while(resultSet.next()) {
//              sizes.add(resultSet.getLong("size_b"));
//          }
////          DBUtil.close();
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }
//      return sizes;
//  }
//
//
//
//  public static void open(List<Long> sizes) {
//      Connection connection = DBUtil.getConn();
//      String sql = "SELECT * FROM video_info WHERE size_b = ?";
//      PreparedStatement preparedStatement;
//      try {
//          preparedStatement = connection.prepareStatement(sql);
//          preparedStatement.setDouble(1, sizes.get(0));
//          ResultSet resultSet = preparedStatement.executeQuery();
//          while(resultSet.next()) {
//              String path = resultSet.getString("path");
//              double temp = resultSet.getLong("size_b");
//              File file = new File(path);
//              String fatherPath = file.getParent();
//              FileUtil.openFile(fatherPath);
////              FileUtil.openFile(path);
//              System.out.println(resultSet.getString("id") + " : " + temp);
//          }
////          DBUtil.close();
//      } catch (SQLException e) {
//          e.printStackTrace();
//      }
//  }
//}
