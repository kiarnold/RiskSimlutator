package risk;

import java.util.Arrays;
import java.util.List;

public class MoveResult {
	private final boolean isSuccess;
	private final String reason;
	public static final List<String> reasons = Arrays.asList(
			"Not enough troops.");
	
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
