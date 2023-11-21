package database_package_dao;

public class InvalidPasscodeException extends Exception{
	public InvalidPasscodeException(){}
	
	public InvalidPasscodeException(String message){
		super(message);
	}
}
