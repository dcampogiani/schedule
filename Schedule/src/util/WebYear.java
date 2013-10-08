package util;

import java.util.ArrayList;

public class WebYear {

	private WebMonth[] months;


	public WebYear(){
		months= new WebMonth[12];

		months[0]= createMonth("Gennaio", 31);
		months[1]= createMonth("Febbraio", 28);
		months[2]= createMonth("Marzo", 31);
		months[3]= createMonth("Aprile", 30);
		months[4]= createMonth("Maggio", 31);
		months[5]= createMonth("Giugno", 30);
		months[6]= createMonth("Luglio", 31);
		months[7]= createMonth("Agosto", 31);
		months[8]= createMonth("Settembre", 30);
		months[9]= createMonth("Ottobre", 31);
		months[10]= createMonth("Novembre", 30);
		months[11]= createMonth("Dicembre", 31);

	}

	private WebMonth createMonth (String name, int durata){
		WebDay[] days = new WebDay[durata];

		for (int i =0;i<durata;i++){
			WebDay tempDay = new WebDay(i+1);
			days[i] = tempDay; 
		}

		WebMonth result = new WebMonth(days, name);
		return result;
	}

	public WebMonth[] getMonths() {
		return months;
	}

	public void setMonths(WebMonth[] months) {
		this.months = months;
	}

	public String getWebRappresentation(){
		String result="";

		result+="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
		result+="<html xmlns=\"http://www.w3.org/1999/xhtml\">";
		result+="<head>";
		result+="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
		result+="<title>Calendar</title>";


		//qui css
		result+="<style>";
		result+="*                   {margin:0;padding:0;}";
		result+="body                {font:1em/1.4 Verdana, Arial, Helvetica, sans-serif;}";
		result+="body *              {display:inline;}";
		result+="h1                  {text-align:center;padding:1em 1em 0;display:block;clear:both;margin-bottom:1em;}";
		result+="ol.calendar         {width:55em;margin:0 auto;display:block;clear:both;}";
		result+=".mydiv				{width: 100%;}";
		result+="li                  {list-style:none;}";
		result+="p.link              {text-align:center;display:block;}";
		result+="li li               {width:7em;height:7em;float:left;margin:.1em;border:1px solid #444;padding:.2em;overflow:auto;}";
		result+="li li p             {font-size:.7em;display:block;border-bottom:1px solid #ddd;}";
		result+="li li ol            {width:auto;}";
		result+="li li ul li,";
		result+="li li ol li         {font-size:.7em;display:block;height:auto;width:auto;margin:0;padding:.2em 0;float:none;border:0;border-bottom:1px solid #ddd;}";
		result+="li li.important       {border-color:#f00;}";
		result+="li#lastmonth li,";
		result+="li#nextmonth li     {background:#f5f5f5;border:1px solid #999;}";
		result+="</style>";

		//fine css

		result+="</head>";
		result+="<body>";

		for (WebMonth current:getMonths()){
			result+=current.getWebRappresentation();
		}

		result+="</body>";
		result+="</html>";


		return result;
	}

	public void addEvent(String doing, int day, int month, int fromH, int fromM, int toH, int toM, ArrayList<String> participants, String location, int endingDay, int endingMonth, int repeatingIntervall){

		WebMonth curMonth = getMonths()[month-1];
		WebDay curDay = curMonth.getDays()[day-1];
		WebEvent event = new WebEvent(fromH, fromM, toH, toM, doing, location, participants);
		curDay.getEvents().add(event);

		if (repeatingIntervall>0){// eventi ripetuti

			int cursorDay = day-1;
			int cursorMonth = month-1;
			boolean stop = false;

			while (!stop){

				int newIntervall=repeatingIntervall;
				while ( newIntervall>getMonths()[cursorMonth].getDays().length){
					newIntervall=repeatingIntervall-getMonths()[cursorMonth].getDays().length;
					cursorMonth++;
					if (cursorMonth>11){
						stop=true;
						break;
					}
				}

				if (cursorMonth>11){
					stop=true;
					break;
				}
				
				if (cursorDay+1+newIntervall<=getMonths()[cursorMonth].getDays().length){//ci sta ancora tutto nel mese

					if (cursorMonth+1>=endingMonth){ //ultimo mese

						if (cursorDay+1+newIntervall>=endingDay){ //superato il giorno limite
							stop=true;
							break;
						}

						else{ //aggiungi evento
							cursorDay+=newIntervall;
							getMonths()[cursorMonth].getDays()[cursorDay].getEvents().add(event);
						}

					}

					else { //mese ancora da coprire tutto

						cursorDay+=newIntervall;
						getMonths()[cursorMonth].getDays()[cursorDay].getEvents().add(event);	
					}

				}

				else {//si passa a mese successivo
					if (cursorMonth>=11){// siamo a dicembre, fine
						stop=true;
						break;
					}

					else { //non siamo a dicembre, calcolo offset

						int offset = getMonths()[cursorMonth].getDays().length - (cursorDay+1);
						cursorDay = newIntervall-offset-1;
						cursorMonth++;
						getMonths()[cursorMonth].getDays()[cursorDay].getEvents().add(event);

					}

				}
			}

		}

	}

}
