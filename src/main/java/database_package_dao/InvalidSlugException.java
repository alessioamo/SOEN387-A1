package database_package_dao;

public class InvalidSlugException extends Exception{
	public InvalidSlugException(){}
	
	public InvalidSlugException(String message){
		super(message);
	}
}
