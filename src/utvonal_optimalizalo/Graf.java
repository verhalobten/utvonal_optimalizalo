package utvonal_optimalizalo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Graf {
	static final String USER="admin";
	static final String PASS="LiveUP9*-+";
	static final String DB_URL="jdbc:mysql://liveupnation.ddns.net:3311/public";
	private static Connection CONN;
	private static Statement stmt;
	private static Statement stmt2;
	private static Statement stmt3;
	private static ResultSet rs0;
	private static ResultSet rs1;
	private static ResultSet rs2;
	static ArrayList<Integer> list = new ArrayList<>();
	static int[][] tomb;
	public static void main(String[] args) {
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CONN=DriverManager.getConnection(DB_URL, USER, PASS);
			stmt=CONN.createStatement();
			stmt2=CONN.createStatement();
			stmt3=CONN.createStatement();
			
			
			int szam=0;
			String query="select point_start, point_end from public.testdb";
			rs0=stmt.executeQuery(query);
			String query2;
			
			while(rs0.next()) 
			{
				szam=rs0.getInt("point_start");
				query2 ="select count(Point_start) from public.testdb where point_start="+szam+"";
				rs1=stmt2.executeQuery(query2);
				
				while(rs1.next()) 
				{
					if(rs1.getInt(1)==2)
					{
						elment(szam);
						
					}
					
				}
				szam=rs0.getInt("point_end");
				query2 ="select count(Point_start) from public.testdb where point_start="+szam+"";
				rs1=stmt2.executeQuery(query2);
				
				while(rs1.next()) 
				{
					if(rs1.getInt(1)==2)
					{
						elment(szam);
						
					}
					
				}
				
			}
			
			tomb = new int[5][list.size()];
			for (int i = 0; i < list.size(); i++) {
				String query3 ="select * from public.testdb where point_start="+list.get(i)+" or point_end="+list.get(i)+"";
				rs2=stmt3.executeQuery(query3);
				
				while (rs2.next()) {
					if (tomb[0][i]==0) {
						tomb[0][i]=list.get(i);
						if (rs2.getInt("point_start")!=list.get(i)) {
							tomb[1][i]=rs2.getInt("point_start");
						}
						if (rs2.getInt("point_end")!=list.get(i)) {
							tomb[1][i]=rs2.getInt("point_end");
						}
						tomb[2][i]=rs2.getInt("id");
						
					}
					
					
					if (rs2.getInt("point_start")!=list.get(i)) {
						tomb[3][i]=rs2.getInt("point_start");
					}
					if (rs2.getInt("point_end")!=list.get(i)) {
						tomb[3][i]=rs2.getInt("point_end");
					}
					tomb[4][i]=rs2.getInt("id");
					
				}
				
				
			}
			
			for (int j = 0; j < tomb.length; j++) {
				
				
				int id = tomb[j][2];
				int csere = tomb[j][1];
				int id2 = tomb[j][4];
				String query4="DELETE FROM `public`.`testdb` WHERE `id`='"+id+"'";
				String query5="UPDATE `public`.`testdb` SET `point_start`='"+csere+"' WHERE `id`='"+id2+"' and point_start="+tomb[j][0]+"";
				String query6="UPDATE `public`.`testdb` SET `point_end`='"+csere+"' WHERE `id`='"+id2+"' and point_end="+tomb[j][0]+"";
				stmt3.executeUpdate(query4);
				stmt3.executeUpdate(query5);
				stmt3.executeUpdate(query6);



				
				
		}
			
			
			
			
			
			
			stmt.close();
			stmt2.close();
			stmt3.close();
			rs0.close();
			rs1.close();
			rs2.close();
			CONN.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		

	}
	public static void elment(int szam) {
		boolean eleme=false;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)==szam) {
				eleme=true;
			}
		}
		if (eleme==false) {
			list.add(szam);
			
		}
		
	}
	

}
