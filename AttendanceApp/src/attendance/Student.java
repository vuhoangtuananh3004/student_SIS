package attendance;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student {
	private String lName;
	private String fName;
	private List<Log> logs;

	public Student() {
		
	}
	public Student(String lName, String fName) {
		this.fName = fName.trim();
		this.lName = lName.trim();
		this.logs = new ArrayList<Log>();
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public void addLog(String time, String date) {
		Log log = new Log();
		log.setDate_check_in(date);
		log.setTime_check_in(time);
		if (!same_date_in(date))
			logs.add(log);
		else {
			int index = logs.indexOf(log);
			Log getLog = logs.get(index);
			String oldTime = getLog.getTime_check_in();
			String newTime = time;
			getLog.setDate_check_out(date);
			if (isOldTimeGreaterThanNewTime(oldTime, newTime)) {
				getLog.setTime_check_in(newTime);
				getLog.setTime_check_out(oldTime);
			}else {
				getLog.setTime_check_out(newTime); 
			}
		}
	}

	private boolean same_date_in(String date) {
		Log log = new Log();
		log.setDate_check_in(date);
		if (logs.contains(log))
			return true;
		return false;
	}

	private boolean isOldTimeGreaterThanNewTime(String oldTime, String newTime) {
		DateFormat format = new SimpleDateFormat("HH:mm:ss");
		try {
			Date oldValue = format.parse(oldTime);
			Date newValue = format.parse(newTime);
			if (oldValue.getTime() > newValue.getTime())
				return true;
			return false;
		} catch (ParseException e) {
			return false;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Student))
			return false;
		Student that = (Student) o;
		return Objects.equals(fName, that.fName) && Objects.equals(lName, that.lName);
	}

//	@Override
//	public String toString() {
//		String result = this.lName + "," + this.fName + " ";
//		result += "[";
//		if (logs.isEmpty())
//			result += "list is empty";
//		else {
//			for (Log log : logs) {
//				result += '\'' + log.getTime_check_in() + "," + log.getDate_check_in() + '\'';
//				if (logs.indexOf(log) < logs.size() - 1)
//					result += ", ";
//			}
//			result += "]\n";
//		}
//		return result;
//	}

}
