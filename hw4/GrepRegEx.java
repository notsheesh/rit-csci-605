import java.util.Scanner;
import java.io.*;
/*
 * from Scanner.close() javadoc: If this scanner has not yet been closed then if
 * its underlying readable also implements the Closeable interface
 * then the readable's close method will be invoked.
 */
public class ScannerStdin {

    public static int soOften = 3;
    public static void readAndClose()	{
	Scanner aScanner  = new Scanner( System.in);
        for ( int index = 1; index < soOften; index ++ )	{
		System.out.print("(" + index + " )> ");
		if (  aScanner.hasNext() )	{
			System.out.println("(" + index + ") read: " + aScanner.nextLine());
		}
	}
	aScanner.close();
    }
    public static void readAndCloseInLoop()	{
        for ( int index = 1; index < soOften; index ++ )	{
		System.out.print("(" + index + " )> ");
		Scanner aScanner  = new Scanner( System.in);
		if (  aScanner.hasNext() )	{
			System.out.println("(" + index + ") read: " + aScanner.nextLine());
		}
		aScanner.close();
	}
    }
    public static void main( String[] args ) {
	// readAndClose();
	readAndCloseInLoop();
    }
}

