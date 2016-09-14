package risk;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoveResult {
	private final boolean isSuccess;
	private final String reason;
	
	// Reason codes
	public static final String NO_TROOPS = "Not enough troops.";
	public static final String TROOPS_MOVED = "Troops moved.";
	
	public MoveResult(boolean isSuccess, String reason) {
		this.isSuccess = isSuccess;
		this.reason = reason;
	}
	
	/* Getters */
	
	/**
	 * @return the isSuccess
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

}
