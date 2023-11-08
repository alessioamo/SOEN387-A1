package database_package_dao;

public class InvalidOrderIdException extends Exception{
	public InvalidOrderIdException(){}
	
	public InvalidOrderIdException(String message){
		super(message);
	}
}