/*
              ---> Throwable <--- 
              |    (checked)     |
              |                  |
              |                  |
      ---> Exception           Error
      |    (checked)        (unchecked)
      |
RuntimeException
  (unchecked)

  Checked exceptions are compile time exceptions 
  Unchecked exceptions are the ones that compile cannot check and hence are runtime exceptions 

 * 
*/

// The statement in finally block will override the return in try block
public class testExceptions{
    public static void main(String args[]){
        testExceptions obj = new testExceptions();
        System.out.println(obj.foo());
    }
    
    int foo(){
        try{
            System.out.println("foo - try");
            return 3; // Will not return, will return 5 instead
        }
        catch (Exception e) {
            System.out.println("foo - catch");
            return 4;
        } 
        finally {
            System.out.println("foo - finally");
            return 5; // But if we comment this, it will return 3 
        }
        // return 6; Will throw error because can never reach this statement 
    }
}