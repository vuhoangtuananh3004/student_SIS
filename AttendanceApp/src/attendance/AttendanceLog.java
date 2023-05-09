package attendance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AttendanceLog {
	private List<Student> students_attendance;
	private int numberOfLogs;
	public static final String PATH = "src/resources/";

	public AttendanceLog() {
		students_attendance = new ArrayList<Student>();
		numberOfLogs = 0;
	}

	public void load_log(String fileName) {
		String path_to_file = fileName;
		try {
			File file = new File(path_to_file);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] values = line.split(",");
				if (values.length < 2)
					continue;
				Student student = new Student(values[0], values[1]);
				if (!students_attendance.contains(student))
					students_attendance.add(student);
				if (values.length < 4) continue;
				String timeTemp = values[2];
				String dateTemp = values[3];

				int index = students_attendance.indexOf(student);
				students_attendance.get(index).addLog(timeTemp, dateTemp);
				numberOfLogs++;

			}
			scanner.close();

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + path_to_file);
		}
	}

	public void print_collection() {
		StringBuilder sb = new StringBuilder();
		sb.append("**" + " This is the entire Collection Data currently stored " + "**\n");
		if (students_attendance.isEmpty())
			System.out.println("List is empty");
		else {
			for (Student student : students_attendance) {
				sb.append(student.toString());
			}
		}
		String result = sb.toString();
		System.out.println(result);
	}
	
	public List<Student> getStudents_attendance() {
		return students_attendance;
	}

	public void setStudents_attendance(List<Student> students_attendance) {
		this.students_attendance = students_attendance;
	}

	public void print_count() {
		System.out.println("The number of logs read into collection are: " + numberOfLogs);
	}
}
