package database_package_servlet;

public class NullPriceException extends Exception {
	public NullPriceException(){}
	
	public NullPriceException(String message){
		super(message);
	}
}
