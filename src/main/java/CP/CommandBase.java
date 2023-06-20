package CP;

import org.apache.hc.core5.http.NotImplementedException;

public abstract class CommandBase {

	private int _retryCount = 0;

	public void Execute(boolean isWebApiCommand) {
		if (isWebApiCommand)
		{
			ExecuteUsingWebApi();
			return;
		}
		ExecuteUsingUi();
	}

	public <TCommand extends CommandBase> TCommand ExecuteAs(boolean isWebApiCommand) {
		TCommand command = (TCommand) this;
		command.Execute(isWebApiCommand);
		return command;
	}


	//
	// Summary:
		//     Runs TCommand 1 time as usual execution of command,
		//     in case of any exceptions tries to execute the same 
	//     command {retryCount} times again. 
	//     
	// Total count of possible executions = 1(main) + retryCount
	//
	// Parameters:
	//   retryCount:
	//     The retry count.
	//  
	// Returns:
	//     Command

	public <TCommand extends CommandBase> TCommand WithRetry(int retryCount) {
		_retryCount = retryCount;
		TCommand command = (TCommand) this;
		return command;
	}


	protected void ExecuteUsingWebApi() {
		throw new RuntimeException();
	}

	protected void ExecuteUsingUi() {
		throw new RuntimeException();
	}
}
