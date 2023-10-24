package database_package_dao;

public class InvalidSkuException extends Exception{
	public InvalidSkuException(){}
	
	public InvalidSkuException(String message){
		super(message);
	}
}
