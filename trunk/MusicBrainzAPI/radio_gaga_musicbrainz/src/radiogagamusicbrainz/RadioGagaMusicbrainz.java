package radiogagamusicbrainz;

import processing.core.PApplet;
import de.bezier.data.sql.*;

public class RadioGagaMusicbrainz extends PApplet {

	
	PostgreSQL pgsql;
	public String[] loadFiles(){
		String[] artistlist = loadStrings("artistlist");
		return artistlist;
	}
	
	public String makeQuery(String query, int type){
		String str;
		if(type == 1)
			//str = "select artist as a  from artist where gid = '" +
				//   query+"' limit 1 "
				  // "";
			str = "select artist.*,artist_name.name as aname, " +
			      "country.name as acountry, " +
			      "gender.name as agender, " +
			      "artist_type.name as atype, "+
			      "artist_meta.rating as rating, artist_meta.rating_count as rating_count "+
			      "from artist, artist_name,country,gender,artist_type,artist_meta " +
			      "where artist_name.id = artist.name and " +
			      "artist.gender = gender.id and "+
			      "artist.country = country.id and "+
			      "artist.type = artist_type.id and "+
			      "artist_meta.id = artist.id and "+
			      "gid ='"+query+"' "+
			      "limit 1";
		else
			str = "select * from artist where name = '"+query+"'";
		return str;
	}
	public void setup() {
		 size(200,200);
		 String user     = "soleo";
		 String pass     = "";
		 String database = "importtest";
		 String[] list = loadFiles();
		 String[] names;
		 String[] gid;
		 names = new String[list.length];
		 gid = new String[list.length];
		 for(int index = 0; index < list.length; index++)
		 {
			 String[] pieces = split(list[index], TAB);
			 names[index] = pieces[1];
			 gid[index] = pieces[0];
			 println(gid[index]);
		 }
		 int count = 0;
		 String[] file;
		 file = new String[list.length];
		 //println(list);
		 pgsql = new PostgreSQL( this, "192.168.142.147", database, user, pass );  
		   
		 if ( pgsql.connect() )
		 {
			 for (int index = 0; index<list.length; index++)
			 {
				 
				 if (!gid[index].isEmpty())
				 {
					 String str = makeQuery(gid[index], 1);
					 pgsql.query( str );
				     if ( pgsql.next() )
				     {
				    	file[count] = pgsql.getString("aname")+"\t"+pgsql.getString("gid")+"\t"+
		        		pgsql.getString("begin_date_year")+"/"+pgsql.getString("begin_date_month")+"/"+pgsql.getString("begin_date_day")+"\t"+
		        		pgsql.getString("end_date_year")+"/"+pgsql.getString("end_date_month")+"/"+pgsql.getString("end_date_day")+"\t"+
		        		pgsql.getString("atype")+"\t"+pgsql.getString("acountry")+"\t"+pgsql.getString("agender")+"\t"+
		        		pgsql.getString("comment")+"\t"+pgsql.getString("rating")+"\t"+pgsql.getString("rating_count");
				    	count ++;
				    	
				        println(pgsql.getString("aname")+"\t"+pgsql.getString("gid")+"\t"+
				        		pgsql.getString("begin_date_year")+"/"+pgsql.getString("begin_date_month")+"/"+pgsql.getString("begin_date_day")+"\t"+
				        		pgsql.getString("end_date_year")+"/"+pgsql.getString("end_date_month")+"/"+pgsql.getString("end_date_day")+"\t"+
				        		pgsql.getString("atype")+"\t"+pgsql.getString("acountry")+"\t"+pgsql.getString("agender")+"\t"+
				        		pgsql.getString("comment")+"\t"+pgsql.getString("rating")+"\t"+pgsql.getString("rating_count")
				        		);
				     }
				 }
				 else
				 {
					 // try use name to search for information
					 
				 } 
				 
				 
			 }
			 saveStrings("musicbrainz.tsv", file);
		 }
		 else
		 {
		    // yay, connection failed !
		 }
	}

	public void draw() {
		background(0);
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { radiogagamusicbrainz.RadioGagaMusicbrainz.class.getName() });
	}
}
