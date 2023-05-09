package application;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import attendance.AttendanceApp;
import attendance.Log;
import attendance.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MenuController {
	final String G = "****** Students missing in class ******";
	final String H = "****** List all swipe in and out for a student ******";
	final String I = "****** Check in times for all students who attended ******";
	final String J = "****** Students that arrived late ******";
	final String K = "get_first_student_to_enter";
	final String L = "********* Looking up Student Attendance Data *********";
	final String M = "****** Looking to see if Student was present on date ******";
	final String N = "****** Students present on this date ****";
	final String O = "**** Those present on date & before a time assigned ****";
	final String P = "**** Those who attended n classes ****";
	final String Q = "**** First student to enter on date ****";
	@FXML
    private Label note;
	@FXML
	private Button attBtn;
	@FXML
	private Button studentBtn;
	@FXML
	private Button quitBtn;
	@FXML
	private Button rosterBtn;
	@FXML
	private Button swipeBtn;
	@FXML
	private Label rosterLabel;
	@FXML
	private Label swipeLabel;
	@FXML
	private AnchorPane homeStackPane;
	@FXML
	private AnchorPane studentStackPane;
	@FXML
	private AnchorPane attStackPane;
	@FXML
	private TextField searchBox;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Label homeStudents;
	@FXML
	private Label homeSwipeRecords;
	@FXML
	private Label countStudents;
	@FXML
	private ListView<Student> listStudentsView;
	@FXML
	private ListView<Student> attendanceList;
	@FXML
	private Label sectionName;

	private List<Student> currentList = new ArrayList<>();

	private ObservableList<Student> students = FXCollections.observableArrayList();
	private ObservableList<Student> attendances = FXCollections.observableArrayList();

	AttendanceApp app = new AttendanceApp();

	Stage stage;
	private List<AnchorPane> aP = new ArrayList<>();

	public void initialize() {
		listStudentsView.setItems(students);
		attendanceList.setItems(attendances);

		searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
			switch (sectionName.getText()) {
			case G:
				currentList = search_by_student_name(newValue, app.list_students_not_in_class());
				note.setText("There are " + currentList.size() + " records");
				break;
			case H:
				currentList = search_by_student_name(newValue, app.getStudent_in_swipe_data());
				note.setText("There are " + currentList.size() + " records");
				break;
			case I:
				currentList = search_by_student_name(newValue, app.list_all_times_checked_in());
				note.setText("There are " + currentList.size() + " records");
				break;
			case J:
				currentList = app.list_of_students_late_to_class(newValue, "late");
				displayCustomViewForAttendanceRecords(currentList);
				note.setText("There are " + currentList.size() + " records");
				break;
			case L:
				currentList = app.getStudent_in_swipe_data();
				displayCustomViewForAttendanceRecords(currentList);
				note.setText("There are " + currentList.size() + " records");
				break;
			case M:
				currentList = search_by_student_name(newValue, app.getStudent_in_swipe_data());
				note.setText("There are " + currentList.size() + " records");
				break;
			case N:
				currentList = search_by_student_name(newValue, app.getStudent_in_swipe_data());
				note.setText("There are " + currentList.size() + " records");
				break;
			case O:
				currentList = app.list_of_students_late_to_class(newValue, "early");
				displayCustomViewForAttendanceRecords(currentList);
				note.setText("There are " + currentList.size() + " records");
				break;
			case P:
				try {
					int num = Integer.parseInt(newValue);
					currentList = app.list_of_number_of_day_attendances(num);
					note.setText("Those who attend at least " + num + " classes");
				} catch (Exception e) {
					System.out.println("Coudn't parse Int");
				}
				displayCustomViewForAttendanceRecords(currentList);
	
				break;
			default:
				break;
			}
		});

		quitBtn.setOnAction(Event -> {
			Platform.exit();
		});
		aP.add(homeStackPane);
		aP.add(studentStackPane);
		aP.add(attStackPane);
	}

	@FXML
	private void switchToStudentStackPane() {
		if (!studentBtn.isDisable()) {
			closeAllPane();
			studentStackPane.setVisible(true);
		}
	}

	@FXML
	private void switchToHomeStackPane() {
		closeAllPane();
		homeStackPane.setVisible(true);
	}

	@FXML
	private void switchToAttRecord() {
		if (!attBtn.isDisable()) {
			closeAllPane();
			attStackPane.setVisible(true);
			reset();
		}

	}

	private void closeAllPane() {
		for (AnchorPane ac : aP) {
			if (ac.isVisible()) {
				ac.setVisible(false);
			}
		}
	}

	@FXML
	private void selectFileRoster() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			app.readRosterFile(selectedFile.toString());
			homeStudents.setText(app.countStudents());
			countStudents.setText(app.countStudents());
			rosterBtn.setStyle(
					"-fx-background-radius: 20px; -fx-background-color: B3C99C; -fx-effect: dropshadow(gaussian, green, 10, 0, 0, 0, false, 5);");
			rosterLabel.setText(selectedFile.getName());
			studentBtn.setDisable(false);
			displayCustomViewForStudentsRoster(app.getStudent_in_roster());

		}
	}

	@FXML
	private void selectFileSwipe() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			app.readSwipeDataFile(selectedFile.toString());
			homeSwipeRecords.setText(app.countRecords());
			swipeBtn.setStyle(
					"-fx-background-radius: 20px; -fx-background-color: B3C99C; -fx-effect: dropshadow(gaussian, green, 10, 0, 0, 0, false, 5);");
			swipeLabel.setText(selectedFile.getName());
			attBtn.setDisable(false);
		}
	}

	private void displayCustomViewForStudentsRoster(List<Student> listStudent) {
		students.addAll(listStudent);
		listStudentsView.setCellFactory(param -> new ListCell<Student>() {
			@Override
			protected void updateItem(Student student, boolean empty) {
				super.updateItem(student, empty);

				if (empty || student == null) {
					setText(null);
				} else {
					setText(student.getlName() + " " + student.getfName());
				}
			}
		});
	}

	private void displayCustomViewForAttendanceRecords(List<Student> AttStudentsRecord) {
		List<String> list_show_check_out = new ArrayList<>(Arrays.asList(I, L, J, N, Q));
		List<String> list_show_check_in = new ArrayList<>(Arrays.asList(N, O, P,G));
		attendances.clear();
		attendances.addAll(AttStudentsRecord);
		if (attendances.isEmpty()) {
			Label placeholderLabel = new Label("No students record found.");
			attendanceList.setPlaceholder(placeholderLabel);
			return;
		}
		attendanceList.setCellFactory(param -> new ListCell<Student>() {
			@Override
			protected void updateItem(Student student, boolean empty) {
				super.updateItem(student, empty);

				if (empty || student == null) {
					setText(null);
				} else {
					String temp = student.getlName() + " " + student.getfName();
					if (!list_show_check_in.contains(sectionName.getText())) {
						temp += "[";
						for (Log log : student.getLogs()) {
							if (log.getDate_check_in() != null) {
								temp += "'";
								temp += (log.getTime_check_in() + ", " + log.getDate_check_in());
							}
							if (log.getDate_check_out() != null
									&& !list_show_check_out.contains(sectionName.getText())) {
								temp += ", " + (log.getTime_check_out() + ", " + log.getDate_check_out()) + "',";
							} else {
								temp += "'";
							}

						}
						temp += "]\n";
					}

					setText(temp);
				}
			}
		});
	}

	/**
	 * 
	 */
	@FXML
	void students_not_in_class(ActionEvent event) {
		reset();
		searchBox.setPromptText("Search by student name");
		sectionName.setText(G);
		currentList = app.list_students_not_in_class();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */

	@FXML
	void list_all_times_check_in_out(ActionEvent event) {
		reset();
		searchBox.setDisable(false);
		searchBox.setPromptText("Search by student name");
		sectionName.setText(H);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */
	@FXML
	void list_all_times_check_in(ActionEvent event) {
		reset();
		searchBox.setPromptText("Search by student name");
		sectionName.setText(I);
		currentList = app.list_all_times_checked_in();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */
	@FXML
	void list_students_late_to_class(ActionEvent event) {
		reset();
		datePicker.setDisable(false);
		searchBox.setDisable(false);
		sectionName.setText(J);
		searchBox.setPromptText("Timestamp format: hh:mm:ss");
		currentList = app.list_all_times_checked_in();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */
	@FXML
	void attendance_data_for_student(ActionEvent event) {
		reset();
		searchBox.setDisable(false);
		searchBox.setPromptText("Search by student name");
		sectionName.setText(L);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */
	@FXML
	void is_present(ActionEvent event) {
		reset();
		searchBox.setDisable(false);
		datePicker.setDisable(false);
		searchBox.setPromptText("Search by student name");
		sectionName.setText(M);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	/**
	 * 
	 */
	@FXML
	void handleDateSelected(ActionEvent event) {
		List<Student> temp = new ArrayList<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
		String dateString = datePicker.getValue().format(formatter);
		if (currentList.isEmpty())
			return;

		for (Student st : currentList) {
			Student tempStudent = new Student();
			tempStudent = st;
			for (Log log : tempStudent.getLogs()) {
				String str = log.getDate_check_in().trim();
				if (str.equals(dateString)) {
					tempStudent.getLogs().clear();
					tempStudent.getLogs().add(log);
					temp.add(tempStudent);
				}
			}
		}
		if (sectionName.getText() == Q) {

			temp = app.get_first_student_to_enter(temp);

		}
		displayCustomViewForAttendanceRecords(temp);
		String str ="";
		if (sectionName.getText().equals(Q)) str += "on " + dateString;
		note.setText("There are " + temp.size() + " records " + str);

	}

	/**
	 * 
	 */
	@FXML
	void list_all_students_checked_in(ActionEvent event) {
		reset();
		datePicker.setDisable(false);
		searchBox.setPromptText("Search by student name");
		sectionName.setText(N);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	@FXML
	void list_all_students_checked_in_before(ActionEvent event) {
		reset();
		searchBox.setDisable(false);
		datePicker.setDisable(false);
		searchBox.setPromptText("Timestamp format: hh:mm:ss");
		sectionName.setText(O);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
		note.setText("There are " + currentList.size() + " records");
	}

	@FXML
	void list_students_attendance_count(ActionEvent event) {
		reset();
		searchBox.setDisable(false);
		searchBox.setPromptText("Number of attendances date (integer)");
		sectionName.setText(P);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
	}

	@FXML
	void get_first_student_to_enter(ActionEvent event) {
		reset();
		datePicker.setDisable(false);
		sectionName.setText(Q);
		currentList = app.getStudent_in_swipe_data();
		displayCustomViewForAttendanceRecords(currentList);
	}

	List<Student> search_by_student_name(String name, List<Student> list_search_from) {
		List<Student> show = new ArrayList<>();
		show.addAll(list_search_from);
		name = name.toLowerCase();
		String[] words = name.split("\\s+");
		for (Student student : list_search_from) {
			String fname = student.getfName().toLowerCase();
			String lname = student.getlName().toLowerCase();
			if (!fname.contains(words[0]) && !lname.contains(words[0])) {
				int studentIndex = show.indexOf(student);
				show.remove(studentIndex);
			}
			if (words.length > 1) {
				System.out.println(words[1]);
				if (!fname.contains(words[1]) && !lname.contains(words[1])) {
					int studentIndex = show.indexOf(student);
					if (studentIndex != -1)
						show.remove(studentIndex);
				}
			}
		}
		displayCustomViewForAttendanceRecords(show);
		return show;
	}

	
	 void reset() {
	 searchBox.clear();
	 note.setText("");
	 searchBox.setDisable(true);
	 datePicker.setDisable(true);
	 }
}
