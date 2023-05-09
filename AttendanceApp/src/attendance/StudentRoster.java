package attendance;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StudentRoster {
	public static final String PATH = "src/resources/";
	private List<Student> students_roster;

	public StudentRoster() {
		this.students_roster = new ArrayList<Student>();
	}
	public void load_log(String fileName) {
		String path_to_file = fileName;
		try {
			File file = new File(path_to_file);
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] values = line.split(",");
				if (values.length < 2) continue;
				Student  student = new Student(values[0], values[1]);
				if (!students_roster.contains(student)) students_roster.add(student);
		
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + path_to_file);

		}
		
	}
	public void print_collection() {
		StringBuilder sb = new StringBuilder();
		sb.append("****" + " Those students on roster " + "****\n");
		if (students_roster.isEmpty()) {
			sb.append("The list students are empty");
		} else {
			for (Student student: students_roster) {
				sb.append(student.getlName() +"," + student.getfName() + "\n");
			}
		}
		String result = sb.toString();
		System.out.println(result);
		
	}
	public void print_count() {
		System.out.println("The number of students read into collection are: " + students_roster.size());
	}
	public List<Student> getStudents_roster() {
		return students_roster;
	}
	public void setStudents_roster(List<Student> students_roster) {
		this.students_roster = students_roster;
	}
}
