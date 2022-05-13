import java.sql.*;
import java.util.Scanner;
public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void Judge(int biao,String a){
        if(biao==1){
            System.out.println(a+"成功！");
        }
        else{
            System.out.println(a+"失败!");
        }
    }
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://stdu.database.windows.net:1433;"
                        + "database=my-machine-1;"
                        + "user=duan@stdu;"
                        + "password=796184@.cn;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;"
                        + "characterEncoding=utf8";

        try (Connection connection = DriverManager.getConnection(connectionUrl);) {
            System.out.println("欢迎使用学生管理系统，请选择您的操作:");
            System.out.println("1.增加\n2.删除\n3.修改\n4.查询\n5.退出");
            Scanner sc = new Scanner(System.in);
            PreparedStatement statement = null;
                int flag=0;
                int biao=0;
                String sql=new String();
            for(;flag==0;) {
                int a= Integer.parseInt(sc.next());
                switch (a) {
                    case 1:
                        System.out.println("你需要对哪个表进行操作？");
                        String str=sc.next();

                        if(str.equals("student")){
                            System.out.println("请输入学生的姓名，学号，性别，年龄和所在系");
                            String name = new String(sc.next()), num = new String(sc.next()), sex = new String(sc.next()), age = new String(sc.next()), dept = new String(sc.next());
                            sql="insert into student values('"+num+"',N'"+name+"',N'"+sex+"','"+age+"',N'"+dept+"')";
                            statement = connection.prepareStatement(sql);
                            biao=statement.executeUpdate();
                        }
                        else if(str.equals("course")){
                            System.out.println("请输入课程号，课程名，学分，学期：");
                            String cnum=new String(sc.next()),cname=new String(sc.next());
                            int credit= Integer.parseInt(sc.next()),semester= Integer.parseInt(sc.next());
                            sql="insert into course values('"+cnum+"',N'"+cname+"','"+credit+"','"+semester+"')";
                            statement = connection.prepareStatement(sql);
                            biao=statement.executeUpdate();
                        }
                        else if(str.equals("sc")){
                            System.out.println("请输入学生的学号，课程名和成绩");
                            String sno=new String(sc.next()),cname=new String(sc.next());
                            int grade=Integer.parseInt(sc.next());
                            int isExist=0; //检查sno，cno是否存在
                            connection.prepareStatement("select COUNT(*) from course").executeQuery().next();
                            ResultSet resExist=connection.prepareStatement("select count(*) from course").executeQuery();
                            ResultSet resExist3=connection.prepareStatement("select COUNT(*) from student").executeQuery();
                            ResultSet resExist1=connection.prepareStatement("select rtrim(cname) from course").executeQuery();
                            ResultSet resExist2=connection.prepareStatement("select rtrim(sno) from student").executeQuery();
                            resExist.next();
                            resExist1.next();
                            resExist2.next();
                            resExist3.next();
                            for(int i=1;i<=resExist.getInt(1);i++){
                                if(resExist1.getString(1).equals(cname)){
                                    for(int j=1;j<=resExist3.getInt(1);j++){
                                        if(resExist2.getString(1).equals(sno)){
                                            isExist=1;
                                        }
                                        resExist2.next();
                                    }
                                }
                                resExist1.next();
                            }
                            if(isExist==1) {
                                String sql1="select cno from course where rtrim(cname) = N'"+cname+"'";
                                ResultSet res2=connection.prepareStatement(sql1).executeQuery();
                                res2.next();
                                sql = "insert into sc values('" + sno + "','" +res2.getString(1)+ "','" + grade + "')";
                                statement = connection.prepareStatement(sql);
                                biao = statement.executeUpdate();
                            }
                            else{
                                System.out.println("不存在学号为"+sno+"的学生的"+cname+"课的成绩记录");
                            }
                        }
                        else{
                            System.out.println("输入错误，请重新输入！");
                        }
                        Judge(biao,"插入");
                        biao=0;
                        break;
                    case 2:
                        System.out.println("请输入要对哪个表进行操作：\n1.student表\n2.course表\n3.sc表");
                        int i=Integer.parseInt(sc.next());
                        switch(i){
                            case 1:
                                System.out.println("请输入要删除学生的学号");
                                sql="delete from student where sno= '"+sc.next()+" '";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"删除");
                                biao=0;
                                break;
                            case 2:
                                System.out.println("请输入要删除的课程名");
                                sql="delete from course where cno = '"+sc.next()+"'";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"删除");
                                biao=0;
                                break;
                            case 3:
                                System.out.println("请输入学生的学号、课程名");
                                sql="delete from sc where sno= '"+sc.next()+"' and cno = '"+sc.next()+"'";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"删除");
                                biao=0;
                                break;
                            default:
                                System.out.println("输入错误，请重新输入！");
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("请输入要对哪个表进行操作：\n1.student表\n2.course表\n3.sc表");
                        i=Integer.parseInt(sc.next());
                        switch(i){
                            case 1:
                                System.out.println("请输入要修改的学生的学号：");
                                String num=sc.next();
                                System.out.println("请输入学生的姓名，性别，年龄和所在系：");
                                sql="update student set sname = N'"+sc.next()+"'"+",ssex= N'"+sc.next()+"'"+",sage = '"+sc.next()+"'"+",sdept= N'"+sc.next()+"' where sno= '"+num+"'";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"修改");
                                biao=0;
                                break;
                            case 2:
                                System.out.println("请输入要修改的课程名");
                                String course=sc.next();
                                System.out.println("请输入课程号，学分，学期：");
                                sql="update course set cno = '"+sc.next()+"'"+", credit = '"+sc.next()+"', semester = '"+sc.next()+"' where rtrim(cname) = N'"+course+"'";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"修改");
                                biao=0;
                                break;
                            case 3:
                                System.out.println("请输入学生的学号、课程名");
                                num=sc.next();
                                course=sc.next();
                                System.out.println("请输入学生的成绩：");
                                sql="update sc set grade="+sc.next()+" where sno = '"+num+"' and cno= (select cno from course where rtrim(cname) = N'"+course+"')";
                                statement = connection.prepareStatement(sql);
                                biao=statement.executeUpdate();
                                Judge(biao,"修改");
                                biao=0;
                                break;
                            default:
                                System.out.println("输入错误，请重新输入！");
                                break;
                        }
                        break;
                    case 4:
                        System.out.println("请输入要对哪个表进行查询：\n1.student表\n2.course表\n3.sc表");
                        i=Integer.parseInt(sc.next());
                        if(i==1){
                            sql = "select * from student";
                            statement = connection.prepareStatement(sql);
                            ResultSet res = null;
                            res = statement.executeQuery();
                            System.out.println("学号     姓名 性别 年龄 所在系");
                            while (res.next()) {
                                System.out.println(res.getString(1) + ' ' + res.getString(2) + ' ' + res.getString(3) + ' ' + res.getInt(4) + ' ' + res.getString(5));
                            }
                            break;
                        }
                        else if(i==2){
                            sql = "select * from course";
                            statement = connection.prepareStatement(sql);
                            ResultSet res = null;
                            res = statement.executeQuery();
                            System.out.println("课程号  课程名 学分   学期");
                            while (res.next()) {
                                System.out.println(res.getString(1) + ' ' + res.getString(2) + ' ' + res.getString(3) + ' ' + res.getInt(4) );
                            }
                            break;
                        }
                        else if(i==3){
                            sql = "select * from sc";
                            statement = connection.prepareStatement(sql);
                            ResultSet res = null;
                            res = statement.executeQuery();
                            System.out.println("学号    课程号   成绩");
                            while (res.next()) {
                                System.out.println(res.getString(1) + ' ' + res.getString(2) + ' ' + res.getString(3));
                            }
                            break;
                        }
                        else{
                            System.out.println("输入错误，请重新输入！");
                        }
                    case 5:
                        flag=1;
                        System.out.println("程序即将退出，再见!");
                        break;
                    default:
                        System.out.println("错误，请重新输入：");
                        break;
                }
                if(a!=5)
                    System.out.println("请再次选择：\n1.增加\n2.删除\n3.修改\n4.查询\n5.退出");
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败,请检查您的网络！");
        }
    }
}