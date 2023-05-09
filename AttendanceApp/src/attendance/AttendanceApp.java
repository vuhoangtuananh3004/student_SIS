package attendance;

import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AttendanceApp {
	private StudentRoster studentRoster = new StudentRoster();
	private AttendanceLog swipeData = new AttendanceLog();
	private List<Student> student_in_swipe_data;
	private List<Student> student_in_roster;
	private List<Student> student_not_in_class;
	private List<Student> student_first_swipe_in;
	private List<Student> students_late_in_class;
	private List<Student> students_participate_days;
	public AttendanceApp() {
		
		
	}
	public void readRosterFile(String fileNameForRoster) {
		this.studentRoster = new StudentRoster();
		studentRoster.load_log(fileNameForRoster);
		student_in_roster = studentRoster.getStudents_roster();
	}
	public void readSwipeDataFile(String fileNameForSwipeData) {
		this.swipeData = new AttendanceLog();
		swipeData.load_log(fileNameForSwipeData);
		student_in_swipe_data = swipeData.getStudents_attendance();
	}
	/**
	 * List Student not in class
	 * @return list of student is missing.
	 */
	public List<Student> list_students_not_in_class() {
		student_not_in_class = new ArrayList<>();
		student_not_in_class.addAll(student_in_roster);
		student_not_in_class.removeAll(student_in_swipe_data);
		return student_not_in_class;

	}
	/**
	 * List all in and out for particular student
	 * @param student and return student index
	 */
	public int list_all_times_checking_in_and_out(Student student) {
		int index = student_in_swipe_data.indexOf(student);
		return index;
	}
	/**
	 * List of all students first swipe in
	 * @return List of Student first swipe
	 */
	public List<Student> list_all_times_checked_in() {
		student_first_swipe_in = new ArrayList<>();
		for (Student st: student_in_swipe_data) {
			if (!student_first_swipe_in.contains(st)) {
				student_first_swipe_in.add(st);
			}
		}
		return student_first_swipe_in;
	}
	/**
	 * List of student late to class
	 * @param time
	 * @param early_late 
	 * @return list student who late to class
	 */
	public List<Student> list_of_students_late_to_class(String time, String early_late) {
			students_late_in_class = new ArrayList<>();
		
			try {
				students_late_in_class.clear();
			 	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			 	String timeStr = "09:00:00";
		        String onTime = time;
		        LocalTime timeSetUp = LocalTime.parse(onTime, formatter);
			for (Student st: student_in_swipe_data) {
					timeStr = st.getLogs().get(0).getTime_check_in().trim();
					LocalTime timeSwipe = LocalTime.parse(timeStr, formatter);
			        int getTimeSetUp = timeSetUp.toSecondOfDay();
			        int getTimeSwipe = timeSwipe.toSecondOfDay();
			        int timeLate = getTimeSetUp - getTimeSwipe;
			        if (timeLate < 0 && early_late == "late") {
			        	System.out.println(timeLate);
			        	students_late_in_class.add(st);
			        }
			        if (timeLate > 0 && early_late == "early") {
			        	students_late_in_class.add(st);
			        }
				}
			}catch (Exception e) {
				students_late_in_class.addAll(student_in_swipe_data);
				System.out.println("TimeStamp should be in format hh:mm:ss");
			}

	        return students_late_in_class;
		
	}

	public List<Student> list_of_number_of_day_attendances(int days){
		students_participate_days = new ArrayList<>();	
		for (Student st: student_in_swipe_data) {
			if (st.getLogs().size() >= days) {
				students_participate_days.add(st);
			}
		}
		return students_participate_days;
	}
	public List<Student> get_first_student_to_enter(List<Student> temp){
		if (temp.isEmpty()) return
		student_first_swipe_in = new ArrayList<>();
		Student earliest = new Student();
		for (Student st: temp) {
			if (earliest.getLogs() == null) {
				earliest = st;
				continue;
			}	
			String timpString1 = earliest.getLogs().get(0).getTime_check_in().trim();
			LocalTime time1 = LocalTime.parse(timpString1);
			
			String timpString2 = st.getLogs().get(0).getTime_check_in().trim();
			LocalTime time2 = LocalTime.parse(timpString2);
			
			if (time1.getSecond() > time2.getSecond()) {
				earliest = st;
			}
			
		}
		student_first_swipe_in.add(earliest);
		return student_first_swipe_in;
	}
	public String countStudents() {
		String temp = String.valueOf(student_in_roster.size());
		return temp;
	}
	public String countRecords() {
		String temp = String.valueOf(student_in_swipe_data.size());
		return temp;
	}
	public List<Student> getStudent_in_roster() {
		return student_in_roster;
	}
	public List<Student> getStudent_in_swipe_data() {
		return student_in_swipe_data;
	}
	
}
