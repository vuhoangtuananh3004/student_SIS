package attendance;

import java.util.Objects;

public class Log {
	private String time_check_in;
	private String date_check_in;
	private String time_check_out;
	private String date_check_out;
	
	public Log() {
	}

	public String getTime_check_in() {
		return time_check_in;
	}

	public void setTime_check_in(String time_check_in) {
		this.time_check_in = time_check_in;
	}

	public String getDate_check_in() {
		return date_check_in;
	}

	public void setDate_check_in(String date_check_in) {
		this.date_check_in = date_check_in;
	}

	public String getTime_check_out() {
		return time_check_out;
	}

	public void setTime_check_out(String time_check_out) {
		this.time_check_out = time_check_out;
	}

	public String getDate_check_out() {
		return date_check_out;
	}

	public void setDate_check_out(String date_check_out) {
		this.date_check_out = date_check_out;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Log)) return false;
        Log that = (Log) o;
        return Objects.equals(date_check_in, that.date_check_in);
    }
	
	
}
